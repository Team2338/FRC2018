package team.gif.lib;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.MotorSafety;

public class TalonSRXConfigurator {

    public static class Configuration {
        public LimitSwitchNormal LIMIT_SWITCH_NORMALLY_OPEN = LimitSwitchNormal.NormallyOpen;
        public double MAX_OUTPUT_VOLTAGE = 12;
        public double NOMINAL_OUTPUT = 0;
        public double PEAK_OUTPUT = 1;
        public NeutralMode NEUTRAL_MODE = NeutralMode.Coast;
        public boolean ENABLE_CURRENT_LIMIT = false;
        public boolean ENABLE_SOFT_LIMIT = false;
        public int CURRENT_LIMIT = 0;
        public double EXPIRATION_TIMEOUT_SECONDS = MotorSafety.DEFAULT_SAFETY_EXPIRATION;
        public double FORWARD_SOFT_LIMIT = 0;
        public boolean INVERTED = false;
        public double NOMINAL_CLOSED_LOOP_VOLTAGE = 12;
        public double REVERSE_SOFT_LIMIT = 0;
        public boolean SAFETY_ENABLED = false;

        public int CONTROL_FRAME_PERIOD_MS = 5;
        public int MOTION_CONTROL_FRAME_PERIOD_MS = 100;
        public int GENERAL_STATUS_FRAME_RATE_MS = 5;
        public int FEEDBACK_STATUS_FRAME_RATE_MS = 100;
        public int QUAD_ENCODER_STATUS_FRAME_RATE_MS = 100;
        public int ANALOG_TEMP_VBAT_STATUS_FRAME_RATE_MS = 100;
        public int PULSE_WIDTH_STATUS_FRAME_RATE_MS = 100;

        public VelocityMeasPeriod VELOCITY_MEASUREMENT_PERIOD = VelocityMeasPeriod.Period_100Ms;
        public int VELOCITY_MEASUREMENT_ROLLING_AVERAGE_WINDOW = 64;

        public double VOLTAGE_COMPENSATION_RAMP_RATE = 0;
        public double VOLTAGE_RAMP_RATE = 0;
    }

    private static final Configuration DEFAULT_CONFIGURATION = new Configuration();
    private static final Configuration FOLLOWER_CONFIGURATION = new Configuration();

    static {
        FOLLOWER_CONFIGURATION.CONTROL_FRAME_PERIOD_MS = 1000;
        FOLLOWER_CONFIGURATION.MOTION_CONTROL_FRAME_PERIOD_MS = 1000;
        FOLLOWER_CONFIGURATION.GENERAL_STATUS_FRAME_RATE_MS = 1000;
        FOLLOWER_CONFIGURATION.FEEDBACK_STATUS_FRAME_RATE_MS = 1000;
        FOLLOWER_CONFIGURATION.QUAD_ENCODER_STATUS_FRAME_RATE_MS = 1000;
        FOLLOWER_CONFIGURATION.ANALOG_TEMP_VBAT_STATUS_FRAME_RATE_MS = 1000;
        FOLLOWER_CONFIGURATION.PULSE_WIDTH_STATUS_FRAME_RATE_MS = 1000;
    }

    public static TalonSRX createDefaultTalon(int id) {
        return createTalon(id, DEFAULT_CONFIGURATION);
    }

    public static TalonSRX createFollowerTalon(int id, int masterid) {
        final TalonSRX talon = createTalon(id, FOLLOWER_CONFIGURATION);
        talon.set(ControlMode.Follower, masterid);
        return talon;
    }

    public static TalonSRX createTalon(int id, Configuration config) {
        TalonSRX talon = new TalonSRX(id);
        talon.setControlFramePeriod(ControlFrame.Control_3_General, config.CONTROL_FRAME_PERIOD_MS);
        talon.changeMotionControlFramePeriod(config.MOTION_CONTROL_FRAME_PERIOD_MS);
        talon.setIntegralAccumulator(0, 0, 0);
        talon.clearMotionProfileHasUnderrun(0);
        talon.clearMotionProfileTrajectories();
        talon.clearStickyFaults(0);
        talon.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, config.LIMIT_SWITCH_NORMALLY_OPEN, 0);
        talon.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, config.LIMIT_SWITCH_NORMALLY_OPEN, 0);
        talon.enableVoltageCompensation(true);
        talon.configVoltageCompSaturation(config.MAX_OUTPUT_VOLTAGE, 0);
        talon.configNominalOutputForward(config.NOMINAL_OUTPUT, 0);
        talon.configNominalOutputReverse(-config.NOMINAL_OUTPUT, 0);
        talon.configPeakOutputForward(config.PEAK_OUTPUT, 0);
        talon.configPeakOutputReverse(-config.PEAK_OUTPUT, 0);
        talon.setNeutralMode(config.NEUTRAL_MODE);
        talon.enableCurrentLimit(config.ENABLE_CURRENT_LIMIT);
        talon.configForwardSoftLimitEnable(config.ENABLE_SOFT_LIMIT, 0);
        talon.configReverseSoftLimitEnable(config.ENABLE_SOFT_LIMIT, 0);
        talon.setInverted(config.INVERTED);

        return talon;
    }
}