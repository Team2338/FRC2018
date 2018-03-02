package team.gif.robot;

public class Globals {

    // Drivetrain Constants
    public static final double DEFAULT_QUICK_STOP_THRESHOLD = 0.2;
    public static final double DEFAULT_QUICK_STOP_ALPHA = 0.1;
    public static final double MAX_OUTPUT = 1.0;
    public static final double DEFAULT_DEADBAND = 0.02;
    public static final double kV = 0.0;
    public static final double kA = 0.0;

    // Drivetrain Physical Constants
    public static final double WHEELBASE_WIDTH_IN = 23.25;
    public static final double WHEELBASE_WIDTH_M = WHEELBASE_WIDTH_IN * 0.0254;
    public static final double WHEEL_DIAMETER_IN = 6.05;
    public static final double WHEEL_DIAMETER_M = WHEEL_DIAMETER_IN * 0.0254;
    public static final int TICKS_PER_REVOLUTION = 4096;

    // Drivetrain Motion Profiling
    public static final double DRIVE_P = 0.1;
    public static final double DRIVE_I = 0.0;
    public static final double DRIVE_D = 0.0;
    public static final double TIME_STEP = 0.05; // s
    public static final double MAX_VELOCITY = 1.0; // m/s
    public static final double MAX_ACCELERATION = 1.0; // m/s/s
    public static final double MAX_JERK = 60.0; // m/s/s/s

    // Arm Constants
    public static final double DART_P = 0.2;
    public static final double DART_I = 0.0;
    public static final double DART_D = 0.0;

    public static final int ARM_UPPER_SOFT_LIMIT = 510000;
    public static final int ARM_OPEN_SOFT_LIMIT = 302000;
    public static final int ARM_LOWER_SOFT_LIMIT = -45000;

    public static final int ARM_START_POSITION = 480000;
    public static final int ARM_SWITCH_POSITION = 300000;
    public static final int ARM_SECOND_POSITION = 120000;
    public static final int ARM_TRAVEL_POSITION = 0;
    public static final int ARM_COLLECT_POSITION = -45000;

    public static final int ARM_POT_ZERO_POSITION = 630; // C = 390; P = 630;

    // Ramp Constants
    public static final double SERVO_IDLE_POSITION = 0.85;
    public static final double SERVO_RELEASE_POSITION = 0.65;
}
