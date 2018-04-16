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
        public static final double WHEEL_DIAMETER_IN = 6.17; // P: 6.08
        public static final int TICKS_PER_REVOLUTION = 4096;

        // Path Generation
        public static final double TIME_STEP = 0.02; // s
        public static final double MAX_VELOCITY = 72; // m/s
        public static final double MAX_ACCELERATION = 72; // m/s/s
        public static final double MAX_JERK = 1000; // m/s/s/s

        // Path Following
        public static final double DRIVE_P = 0.01; // P: 1.0
        public static final double DRIVE_I = 0.0;
        public static final double DRIVE_D = 0.0;
        public static final double TURN_P = 0.85;
        public static final double TURN_I = 0.002;
        public static final double TURN_D = 0.0;
        public static final double ROTATE_P = 0.03;
        public static final double ROTATE_I = 0.3;
        public static final double ROTATE_D = 0.18;
        public static final double kInterceptLeftForward = 0.091811; // P: 0.077646
        public static final double kInterceptRightForward = 0.091811; // P: 0.078561
        public static final double kInterceptLeftReverse = 0.06409; // P: 0.06409
        public static final double kInterceptRightReverse = 0.061209; // P: 0.061209
        public static final double kVLeftForward = 1.0/(9.75*WHEEL_DIAMETER_IN*Math.PI); // C: 1.0/4.7 P: 9.95
        public static final double kVRightForward = 1.0/(9.75*WHEEL_DIAMETER_IN*Math.PI); // C: 1.0/4.7 P: 9.15
        public static final double kVLeftReverse = 1.0/(9.7*WHEEL_DIAMETER_IN*Math.PI); // P: 9.7
        public static final double kVRightReverse = 1.0/(9.0*WHEEL_DIAMETER_IN*Math.PI); // P: 9.0
        public static final double kALeftForward = 1/(50*WHEEL_DIAMETER_IN*Math.PI); // C: 1.4/12.0 P: 50
        public static final double kARightForward = 1/(50*WHEEL_DIAMETER_IN*Math.PI); // C: 1.4/12.0 P: 40
        public static final double kALeftReverse = 1/(30*WHEEL_DIAMETER_IN*Math.PI); // P: 30
        public static final double kARightReverse = 1/(30*WHEEL_DIAMETER_IN*Math.PI); // P: 30
        public static final double gyroSensitivity = 1.2; // P: 2
    }

    public static class Ramps {
        // Ramp Constants
        public static final double SERVO_IDLE_POSITION = 0.85; // 0.85
        public static final double SERVO_RELEASE_POSITION = 0.55; // 0.65
    }

    public static class Arm {
        // Arm Constants
        public static final double DART_P = 0.2;
        public static final double DART_I = 0.000;
        public static final double DART_D = 0.0;
        public static final int ARM_UPPER_SOFT_LIMIT = 432500;
        public static final int ARM_OPEN_SOFT_LIMIT = 238000;
        public static final int ARM_LOWER_SOFT_LIMIT = -25700; // -45000
        public static final int ARM_START_POSITION = 432500;
        public static final int ARM_SWITCH_POSITION = 220000;
        public static final int ARM_SECOND_POSITION = 110000;
        public static final int ARM_HIT_CUBE_POSITION = 35000;
        public static final int ARM_TRAVEL_POSITION = 0;
        public static final int ARM_COLLECT_POSITION = -25700; // -45000
        public static final int ARM_POT_ZERO_POSITION = 494; // C = 402; P = 494;
    }
}
