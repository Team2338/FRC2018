package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import team.gif.lib.GIFMath;
import team.gif.lib.TalonSRXConfigurator;
import team.gif.robot.Globals;
import team.gif.robot.RobotMap;
import team.gif.robot.commands.subsystem.arm.ArmTeleOp;

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
    private Solenoid leftPunch = new Solenoid(RobotMap.Arm.LEFT_PUNCH_ID);
    private Solenoid rightPunch = new Solenoid(RobotMap.Arm.RIGHT_PUNCH_ID);
    private AnalogInput dartPot = new AnalogInput(RobotMap.Arm.DART_POT_ID);
    private DigitalInput cubeSensor = new DigitalInput(RobotMap.Arm.CUBE_SENSOR_ID);

    private Arm() {
        dart.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        dart.setSensorPhase(true);
        dart.setInverted(false);
        dart.configForwardSoftLimitEnable(true, 0);
        dart.configReverseSoftLimitEnable(true, 0);
        dart.configForwardSoftLimitThreshold(Globals.ARM_UPPER_SOFT_LIMIT, 0);
        dart.configReverseSoftLimitThreshold(Globals.ARM_LOWER_SOFT_LIMIT, 0);
        dart.enableCurrentLimit(true);
        dart.configContinuousCurrentLimit(30, 0);
        dart.configPeakCurrentLimit(50, 0);
        dart.configPeakCurrentDuration(500, 0);
        dart.config_kP(0, Globals.DART_P, 0);
        dart.config_kI(0, Globals.DART_I, 0);
        dart.config_kD(0, Globals.DART_D, 0);
    }

    public void setIntakeSpeed(double speed) {
        left.set(ControlMode.PercentOutput, -speed);
        right.set(ControlMode.PercentOutput, speed);
    }

    public void setOpen(boolean open) {
        actuator.set(open);
    }
    public void setPunch(boolean punch) {
        leftPunch.set(punch);
        rightPunch.set(punch);
    }

    public void setDartSpeed(double speed) {
        dart.set(ControlMode.PercentOutput, speed);
    }

    public void setDartPosition(int position) {
        dart.set(ControlMode.Position, position);
    }

    public void tareDartPosition() {
        dart.setSelectedSensorPosition(GIFMath.map(dartPot.getValue(), Globals.ARM_POT_ZERO_POSITION, Globals.ARM_POT_ZERO_POSITION + 2500,
                0 , 450000), 0, 0);
    }

    public int getDartPotPosition() {
        return dartPot.getValue();
    }

    public int getDartEncoderPosition() {
        return dart.getSelectedSensorPosition(0);
    }

    public boolean hasCube() {
        return cubeSensor.get();
    }

    protected void initDefaultCommand() {
//        setDefaultCommand(new ArmTeleOp());
    }
}
