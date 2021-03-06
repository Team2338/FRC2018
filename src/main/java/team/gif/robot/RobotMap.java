package team.gif.robot;

public abstract class RobotMap {

    // Drivetrain IDs
    public static class Drivetrain {
        public static final int LEFT_MASTER_ID = 0;
        public static final int LEFT_FOLLOWER_ID = 1;
        public static final int RIGHT_MASTER_ID = 2;
        public static final int RIGHT_FOLLOWER_ID = 3;
    }

    // Ramp IDs
    public static class Ramps {
        public static final int LEFT_MASTER_ID = 4;
        public static final int LEFT_FOLLOWER_ID = 5;
        public static final int RIGHT_MASTER_ID = 6;
        public static final int RIGHT_FOLLOWER_ID = 7;
        public static final int RELEASE_SERVO_ID = 9;
        public static final int LEFT_LIMIT_ID = 9;
        public static final int RIGHT_LIMIT_ID = 8;
    }

    // Arm IDs
    public static class Arm {
        public static final int LEFT_ID = 8;
        public static final int RIGHT_ID = 9;
        public static final int DART_ID = 10;
        public static final int ACTUATOR_SOLENOID_ID = 0; // 0 6
        public static final int LEFT_PUNCH_ID = 1; // 1 5
        public static final int RIGHT_PUNCH_ID = 7; // 2 4
        public static final int RETURN_ID = 6; // 3 7
        public static final int DART_POT_ID = 1; // P:1
        public static final int PRESSURE_SENSOR_ID = 2;
        public static final int CUBE_SENSOR_ID = 7;
    }

    // Operator Interface Ports
    public static class OI {
        public static final int DRIVE_CONTROLLER_ID = 0;
        public static final int AUX_CONTROLLER_ID = 1;
    }
}
