package team.gif.robot;

public class Globals {

    // Drivetrain Constants
    public static final double DEFAULT_QUICK_STOP_THRESHOLD = 0.2;
    public static final double DEFAULT_QUICK_STOP_ALPHA = 0.1;
    public static final double MAX_OUTPUT = 1.0;
    public static final double DEFAULT_DEADBAND = 0.02;

    // Drivetrain Physical Constants
    public static final double WHEELBASE_WIDTH_IN = 0.5;
    public static final double WHEELBASE_WIDTH_M = WHEELBASE_WIDTH_IN * 0.0254;
    public static final int TICKS_PER_REVOLUTION = 4096;
    public static final double WHEEL_DIAMETER_IN = 6;
    public static final double WHEEL_DIAMETER_M = WHEEL_DIAMETER_IN * 0.0254;

    // Drivetrain Motion Profiling
    public static final double DRIVE_P = 1.0;
    public static final double DRIVE_I = 0.0;
    public static final double DRIVE_D = 0.0;
    public static final double TIME_STEP = 0.05; // s
    public static final double MAX_VELOCITY = 1.7; // m/s
    public static final double MAX_ACCELERATION = 2.0; // m/s/s
    public static final double MAX_JERK = 60.0; // m/s/s/s

    // Arm Constants
    public static final double DART_P = 1;
    public static final double DART_I = 0.0;
    public static final double DART_D = 20;

    public static final int ARM_OPEN_SOFT_LIMIT = 302000;
}
