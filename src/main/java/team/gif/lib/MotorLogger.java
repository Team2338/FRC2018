package team.gif.lib;

import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author PatrickUbelhor
 * @version 05/03/2017
 *
 * CONNOR: Make sure the logging loop doesn't record data faster than the writing loop can flush it.
 *         Make sure "DELAY_TIME" is slower than the time it takes to flush a buffer. Might need to add
 *           in your own print statements for time-stamping
 *         Make sure the buffers are large enough to prevent thrashing
 *
 * TODO: Should I use synchronized statements, or customized timing using the isWriting AtomicBoolean?
 *      AtomicBoolean will make it run faster, since it won't have to constantly acquire the locks for the buffer
 *      However, need to be VERY careful that the threads don't interleave operations
 *
 * TODO: Can I speed up write times by using the buffer feature of DecimalFormat?
 *
 */
public final class MotorLogger extends Thread {
	
	private static final int BUFFER_SIZE    = 1024 * 128; // Bytes -> 128 KB
	private static final int DELAY_TIME     = 50; // Milliseconds
	private static final char DELIMITER     = ',';
	private static final String FILE_PATH   = "/U/LogFiles/";
	private static final String FILE_NAME   = "motorLogs";
	private static final String FILE_EXT    = ".csv";
	private static final String METADATA_CHANNELS   = "Init_Time,CAN_ID,Speed,Bus_Voltage,Ouput_Voltage,Error,Delta_Time";
	private static final String METADATA_UNITS      = "ns,const,ticks/s,volts,volts,ticks,ns";
	private static final DecimalFormat df = new DecimalFormat("####0.000");
	private static final LinkedList<TalonSRX> queue = new LinkedList<>();
	
	private AtomicBoolean keepRunning = new AtomicBoolean(true);
	private AtomicBoolean isWriting = new AtomicBoolean(false);
	private ByteBuffer primaryBuffer = ByteBuffer.allocate(BUFFER_SIZE);
	private ByteBuffer secondaryBuffer = ByteBuffer.allocate(BUFFER_SIZE);
	private DataWriter writer;
	
	/**
	 * Adds a motor to the logging queue.
	 * NOTE: Currently, the time it takes to log a motor is too long
	 *      to support more than one motor.
	 *
	 * @param motor The motor to add to the queue
	 */
	public static void addMotor(TalonSRX motor) {
		queue.add(motor);
	}
	
	
	/**
	 * Safely kills the logging thread by allowing the last update
	 * cycle to finish before thread termination.
	 */
	public void end() {
		keepRunning.set(false);
	}
	
	
	@Override
	public void run() {
		
		// Counts the number of existing log files for this file name
		int counter = 0;
		File logDir = new File(FILE_PATH);
		if (logDir.list() != null) {
			for (String s : logDir.list()) {
				if (s.contains(FILE_NAME)) {
					counter++;
				}
			}
		}
		
		// Appends the metadata to the top of the file
		try (FileWriter fw = new FileWriter(FILE_PATH + FILE_NAME + counter + FILE_EXT, true)) {
			fw.append(METADATA_CHANNELS);
			fw.append(METADATA_UNITS);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		 * Synchronizes the CAN update frequency with this thread's logging frequency
		 * NOTE: if you don't log from a certain status frame, DO NOT speed up its framerate
		 *		in fact, you should even slow it down if it's unused to reduce CAN usage
		 *
		 * IF YOU NEED TO UPDATE PID CONSTANTS ON THE FLY, MAKE SURE YOU RESET THE APPROPRIATE FRAMERATES
		 * IN THE SUBSYSTEMS THEMSELVES, OR YOUR BEHAVIOR WILL CHANGE WHEN YOU STOP LOGGING
		 *
		 */
		for (TalonSRX motor : queue) {
//			motor.setStatusFrameRateMs(CANTalon.StatusFrameRate.General, DELAY_TIME);
//			motor.setStatusFrameRateMs(CANTalon.StatusFrameRate.AnalogTempVbat, DELAY_TIME);
//			motor.setStatusFrameRateMs(CANTalon.StatusFrameRate.Feedback, DELAY_TIME);
			motor.setStatusFramePeriod(StatusFrame.Status_1_General, DELAY_TIME, 0);
			motor.setStatusFramePeriod(StatusFrame.Status_4_AinTempVbat, DELAY_TIME, 0);
			motor.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, DELAY_TIME, 0);
//			motor.setStatusFrameRateMs(CANTalon.StatusFrameRate.PulseWidth, DELAY_TIME);
//			motor.setStatusFrameRateMs(CANTalon.StatusFrameRate.QuadEncoder, DELAY_TIME);
		}
		
		// Creates and starts the thread to clear the buffers
		writer = new DataWriter(FILE_PATH + FILE_NAME + counter + FILE_EXT);
		writer.start();
		
		
		// Main logging loop
		while (keepRunning.get()) {
			
			long initTime = System.nanoTime();
			
			for (TalonSRX motor : queue) {
				long subInitTime = System.nanoTime();
				putInBuffer(Long.toString(subInitTime), DELIMITER);
				putInBuffer(Integer.toString(motor.getDeviceID()), DELIMITER);
				putInBuffer(df.format(motor.getSelectedSensorVelocity(0)), DELIMITER);
				putInBuffer(df.format(motor.getBusVoltage()), DELIMITER);
				putInBuffer(df.format(motor.getMotorOutputVoltage()), DELIMITER);
				putInBuffer(df.format(motor.getClosedLoopError(0)), DELIMITER);
				putInBuffer(Long.toString(System.nanoTime() - subInitTime), '\n');
			}
			
			try {
				long time = DELAY_TIME - ((System.nanoTime() - initTime) / 1000000);
				Thread.sleep(time > 0 ? time : 0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// Loop has ended, so finish printing the buffers before closing
		while (isWriting.get()) {
			System.out.println("Waiting to flush primary buffer...");
			
			try {
				Thread.sleep(DELAY_TIME / 2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		writer.flushBuffer(primaryBuffer, false); // All data should be flushed, kills the DataWriter thread
	}
	
	/**
	 * Puts a string into a buffer, followed by the delimiter
	 *
	 * @param data The string to put into the buffer
	 * @param delimiter The delimiter to concatenate to the string
	 */
	private void putInBuffer(String data, char delimiter) {
		
		if (primaryBuffer.remaining() >= data.length() + 1) {
			
			primaryBuffer.put(data.getBytes());
			primaryBuffer.put((byte) delimiter);
			
		} else {
			
			while (isWriting.get()) {
				System.err.println("LOGGER IS BLOCKING --- BOTH BUFFERS ARE FULL");
				
				try {
					Thread.sleep(DELAY_TIME / 2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// Swap the buffers
			ByteBuffer temp = primaryBuffer;
			primaryBuffer = secondaryBuffer;
			secondaryBuffer = temp;
			
			// Flush the buffer
			writer.flushBuffer(secondaryBuffer, true);
			
			// Try again
			putInBuffer(data, delimiter);
		}
		
	}
	
	
	/**
	 * This is a Thread that flushes buffers to a file
	 */
	private final class DataWriter extends Thread {
		
		private final Object lock = new Object();
		private final String file;
		private ByteBuffer targetBuffer = null;
		private boolean repeat = true;
		
		private DataWriter(String file) {
			this.file = file;
		}
		
		@Override
		public void run() {
			
			try (FileOutputStream fos = new FileOutputStream(file);
			     FileChannel fc = fos.getChannel()) {
				
				while (true) {
					
					if (targetBuffer != null) {
						
						targetBuffer.flip();
						fc.write(targetBuffer);
						targetBuffer.clear();
						
						targetBuffer = null;
						isWriting.set(false);
					}
					
					if (!repeat) break;
					
					synchronized (lock) {
						lock.wait();
					}
				}
				
				
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		
		private void flushBuffer(ByteBuffer buffer, boolean repeat) {
			isWriting.set(true);
			this.targetBuffer = buffer;
			this.repeat = repeat;
				
			synchronized (lock) {
				lock.notifyAll();
			}
		}
		
	}
	
}
