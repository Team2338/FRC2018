package team.gif.robot;

public class RobotMap {

    // Drivetrain IDs
    public static class Drivetrain {
        public static final int         LEFT_MASTER_ID = 0;
        public static final int LEFT_FOLLOWER_ID = 1;
        public static final int RIGHT_MASTER_ID = 2;
        public static final int RIGHT_FOLLOWER_ID = 3;
    }

    // Ramp IDs
    public static class Ramp {
        public static final int LEFT_MASTER_ID = 4;
        public static final int LEFT_FOLLOWER_ID = 5;
        public static final int RIGHT_MASTER_ID = 6;
        public static final int RIGHT_FOLLOWER_ID = 7;
        public static final int RELEASE_SOLENOID_ID = 1;
    }

    // Arm IDs
    public static class Arm {
        public static final int FRONT_LEFT_ID = 8;
        public static final int REAR_LEFT_ID = 9;
        public static final int FRONT_RIGHT_ID = 10;
        public static final int REAR_RIGHT_ID = 11;
        public static final int DART_ID = 12;
        public static final int ACTUATOR_SOLENOID_ID = 0;
        public static final int DART_POT_ID = 0;
    }

    public static class OI {
        // Operator Interface Ports
        public static final int DRIVER_CONTROLLER_ID = 0;
        public static final int AUX_CONTROLLER_ID = 1;
    }
}
