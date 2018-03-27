package team.gif.lib;

import edu.wpi.first.wpilibj.Timer;

public class PIDCalculator {

    private final double kP;
    private final double kI;
    private final double kD;
    private double iAccum = 0;
    private final double iZone;
    private double lastError;
    private double lastTime;
    private double motorOutput;
    private boolean isFirstTime = false;

    public PIDCalculator(double kP, double kI, double kD) {
        this(kP, kI, kD, 0);
    }

    public PIDCalculator(double kP, double kI, double kD, double iZone) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.iZone = iZone;
        lastError = 0;
        lastTime = 0;
    }

    public void clearIAccum() {
        iAccum = 0;
    }

    // TODO: separate a calculate() and a get() method
    public double getOutput(double error) {
        double p_Out = kP * error;
        double i_Out;
        double d_Out;

        if (iAccum / error < 0) {
            iAccum = 0;
        }

        if (iZone == 0 || Math.abs(error) <= iZone) {
            iAccum += error * (Timer.getFPGATimestamp() - lastTime);
        } else {
            iAccum = 0;
        }

        i_Out = iAccum * kI;
        d_Out = (isFirstTime ? 0 : kD * ((error - lastError) / (Timer.getFPGATimestamp() - lastTime)));

        lastError = error;
        lastTime = Timer.getFPGATimestamp();

        motorOutput = p_Out + i_Out + d_Out;
        return motorOutput;
    }

}