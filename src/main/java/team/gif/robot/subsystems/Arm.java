package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import team.gif.lib.GIFMath;
import team.gif.lib.TalonSRXConfigurator;
import team.gif.robot.Globals;
import team.gif.robot.RobotMap;
import team.gif.robot.commands.subsystems.arm.ArmTeleOp;

public class Arm extends Subsystem {

    private static Arm instance = null;

    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }
        return instance;
    }

    // Hardware
    private TalonSRX left = TalonSRXConfigurator.createDefaultTalon(RobotMap.Arm.LEFT_ID);
    private TalonSRX right = TalonSRXConfigurator.createDefaultTalon(RobotMap.Arm.RIGHT_ID);
    private TalonSRX dart = TalonSRXConfigurator.createDefaultTalon(RobotMap.Arm.DART_ID);

    private Solenoid actuator = new Solenoid(RobotMap.Arm.ACTUATOR_SOLENOID_ID);

    private AnalogInput dartPot = new AnalogInput(RobotMap.Arm.DART_POT_ID);

    private Arm() {
        dart.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        dart.setSensorPhase(false);
        dart.setInverted(true);
        dart.configForwardSoftLimitEnable(true, 0);
        dart.configReverseSoftLimitEnable(true, 0);
        dart.configForwardSoftLimitThreshold(510000, 0);
        dart.configReverseSoftLimitThreshold(-70000, 0);
        dart.enableCurrentLimit(true);
        dart.configContinuousCurrentLimit(20, 0);
        dart.configPeakCurrentLimit(0, 0);
        dart.configPeakCurrentDuration(0, 0);

        dart.config_kP(0, Globals.DART_P, 0);
        dart.config_kI(0, Globals.DART_I, 0);
        dart.config_kD(0, Globals.DART_D, 0);
    }

    public void setFront(double speed) {
        left.set(ControlMode.PercentOutput, -speed);
        right.set(ControlMode.PercentOutput, speed);
    }

    public void open() {
        actuator.set(true);
    }

    public void close() {
        actuator.set(false);
    }

    public void setDartPosition(int position) {
        dart.set(ControlMode.Position, position);
    }

    public void tareDartPosition() {
        dart.setSelectedSensorPosition(GIFMath.map(dartPot.getValue(), 600, 3200, 0 , 468000), 0, 0);
    }

    public int getPotPosition() {
        return dartPot.getValue();
    }

    public int getEncoderPosition() {
        return dart.getSelectedSensorPosition(0);
    }

    public double getLeftCurrent() {
        return left.getOutputCurrent();
    }

    public double getRightCurrent() {
        return right.getOutputCurrent();
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new ArmTeleOp());
    }
}
