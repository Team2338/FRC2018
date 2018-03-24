package team.gif.robot;

public class Globals {

    public static class Drivetrain {
        // Drivetrain Constants
        public static final double DEFAULT_QUICK_STOP_THRESHOLD = 0.2;
        public static final double DEFAULT_QUICK_STOP_ALPHA = 0.1;
        public static final double MAX_OUTPUT = 1.0;
        public static final double DEFAULT_DEADBAND = 0.02;


        // Drivetrain Physical Constants
        public static final double WHEELBASE_WIDTH_IN = 23.25;
        public static final double WHEEL_DIAMETER_IN = 6.08; // P: 6.08
        public static final int TICKS_PER_REVOLUTION = 4096;

        // Path Generation
        public static final double TIME_STEP = 0.02; // s
        public static final double MAX_VELOCITY = 1.5; // m/s
        public static final double MAX_ACCELERATION = 1.0; // m/s/s
        public static final double MAX_JERK = 60.0; // m/s/s/s

        // Path Following
        public static final double DRIVE_P = 1.0; // P: 1.0
        public static final double DRIVE_I = 0.0;
        public static final double DRIVE_D = 0.0;
        public static final double TURN_P = 0.0;
        public static final double TURN_I = 0.0;
        public static final double TURN_D = 0.0;
        public static final double kInterceptLeftForward = 0.1; // P: 0.1
        public static final double kInterceptRightForward = 0.1; // P: 0.1
        public static final double kInterceptLeftReverse = 0.1;
        public static final double kInterceptRightReverse = 0.1;
        public static final double kVLeftForward = 1.0/30.0/WHEEL_DIAMETER_IN; // C: 1.0/4.7 P: 1.0/4.8
        public static final double kVRightForward = 1.0/30.0/WHEEL_DIAMETER_IN; // C: 1.0/4.7 P: 1.0/4.5
        public static final double kVLeftReverse = 1.0/30.0/WHEEL_DIAMETER_IN;
        public static final double kVRightReverse = 1.0/30.0/WHEEL_DIAMETER_IN;
        public static final double kALeftForward = 1.4/12.0; // C: 1.4/12.0 P: 1.4/12.0
        public static final double kARightForward = 1.4/12.0; // C: 1.4/12.0 P: 1.5/12.0
        public static final double kALeftReverse = 1.4/12.0;
        public static final double kARightReverse = 1.4/12.0;
        public static final double gyroSensitivity = 1.2; // P: 0.8
    }

    public static class Ramps {
        // Ramp Constants
        public static final double SERVO_IDLE_POSITION = 0.85; // 0.85
        public static final double SERVO_RELEASE_POSITION = 0.55; // 0.65
    }

    public static class Arm {
        // Arm Constants
        public static final double DART_P = 0.2;
        public static final double DART_I = 0.0;
        public static final double DART_D = 0.0;
        public static final int ARM_UPPER_SOFT_LIMIT = 560000;
        public static final int ARM_OPEN_SOFT_LIMIT = 302000;
        public static final int ARM_LOWER_SOFT_LIMIT = -45000;
        public static final int ARM_START_POSITION = 551000;
        public static final int ARM_SWITCH_POSITION = 300000;
        public static final int ARM_SECOND_POSITION = 120000;
        public static final int ARM_TRAVEL_POSITION = 0;
        public static final int ARM_COLLECT_POSITION = -45000;
        public static final int ARM_POT_ZERO_POSITION = 420; // C = 390; P = 630;
    }

}
