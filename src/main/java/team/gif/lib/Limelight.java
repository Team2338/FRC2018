package team.gif.lib;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import math;

public class Limelight {

    private static Limelight instance = null;

    public static Limelight getInstance() {
        if (instance == null) {
            instance = new Limelight();
        }
        return instance;
    }

    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tv = table.getEntry("tv");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");
    NetworkTableEntry ts = table.getEntry("ts");
    NetworkTableEntry tl = table.getEntry("tl");
/* tv Whether the limelight has any valid targets (0 or 1)
tx Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
ty Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
ta Target Area (0% of image to 100% of image)
ts Skew or rotation (-90 degrees to 0 degrees)
tl The pipeline’s latency contribution (ms) Add at least 11ms for image capture latency. */
    public enum LEDMode {
        ON(0),
        OFF(1),
        BLINK(2);

        private final int mode;
        LEDMode(int mode) {
            this.mode = mode;
        }
    }

    public enum CAMMode {
        VISION(0),
        DRIVER(1);

        private final int mode;
        CAMMode(int mode) {
            this.mode = mode;
        }
    }

    public enum StreamMode {
        STANDARD(0),
        PIPMAIN(1),
        PIPSECONDARY(2);

        private final int mode;
        StreamMode(int mode) {
            this.mode = mode;
        }
    }

    public enum SnapshotMode {
        OFF(0),
        TWOPERSEC(1);

        private final int mode;
        SnapshotMode(int mode) {
            this.mode = mode;
        }
    }

    private Limelight() {

    }

    public boolean hasValidTarget() {
        return tv.getDouble(0) == 1;
    }

    public double getXAngle() {
        return tx.getDouble(0);
    }

    public double getYAngle() {
        return ty.getDouble(0);
    }

    public double getArea() {
        return ta.getDouble(0);
    }

    public double getSkew() {
        return ts.getDouble(0);
    }

    public double getPipelineLatencyMs() {
        return tl.getDouble(0);
    }

    public void setLEDMode(LEDMode mode) {
        table.getEntry("ledMode").setNumber(mode.mode);
    }

    public void setCAMMode(CAMMode mode) {
        table.getEntry("camMode").setNumber(mode.mode);
    }

    public void setPipeline(int index) {
        table.getEntry("pipeline").setNumber(index);
    }

    public void setStreamMode(StreamMode mode) {
        table.getEntry("stream").setNumber(mode.mode);
    }

    public void setSnapshotMode(SnapshotMode mode) {
        table.getEntry("snapshot").setNumber(mode.mode);
    }

    //testing methods
    public double getdistance(){
        double limelightvisionbotheight = 14.5;
        double powerCubeHeight = 11.0;
        double limelightVertBotAngle =0.0;
        double yOffsetAngle = ty.hashcode();
        double distance = (limelightvisionbotheight - powerCubeHeight)/(Math.tan(limelightVertBotAngle + yOffsetAngle));
        return(distance);
    }

}
