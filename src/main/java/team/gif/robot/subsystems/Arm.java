package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import team.gif.robot.Globals;
import team.gif.robot.RobotMap;
import team.gif.robot.commands.system.DoNothing;

public class Arm extends Subsystem {

    private static Arm instance = null;

    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }
        return instance;
    }

    // Hardware
    private TalonSRX frontLeft = new TalonSRX(RobotMap.Arm.FRONT_LEFT_ID);
    private TalonSRX rearLeft = new TalonSRX(RobotMap.Arm.REAR_LEFT_ID);
    private TalonSRX frontRight = new TalonSRX(RobotMap.Arm.FRONT_RIGHT_ID);
    private TalonSRX rearRight = new TalonSRX(RobotMap.Arm.REAR_RIGHT_ID);
    private TalonSRX dart = new TalonSRX(RobotMap.Arm.DART_ID);

    private Solenoid leftPiston = new Solenoid(RobotMap.Arm.LEFT_SOLENOID_ID);
    private Solenoid rightPiston = new Solenoid(RobotMap.Arm.RIGHT_SOLENOID_ID);

    private Arm() {
        frontLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        frontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        dart.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

        frontLeft.config_kP(0, Globals.FRONT_P, 0);
        frontLeft.config_kI(0, Globals.FRONT_I, 0);
        frontLeft.config_kD(0, Globals.FRONT_D, 0);
        frontLeft.config_kF(0, Globals.FRONT_F, 0);
        frontRight.config_kP(0, Globals.FRONT_P, 0);
        frontRight.config_kI(0, Globals.FRONT_I, 0);
        frontRight.config_kD(0, Globals.FRONT_D, 0);
        frontRight.config_kF(0, Globals.FRONT_F, 0);
        dart.config_kP(0, Globals.DART_P, 0);
        dart.config_kI(0, Globals.DART_I, 0);
        dart.config_kD(0, Globals.DART_D, 0);

        frontLeft.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_100Ms, 0);
    }

    public void setFront(double speed) {
        frontLeft.set(ControlMode.PercentOutput, speed);
        frontRight.set(ControlMode.PercentOutput, speed);
    }

    public void setRear(double speed) {
        rearLeft.set(ControlMode.PercentOutput, speed);
        rearRight.set(ControlMode.PercentOutput, speed);
    }

    public void setFrontSpeed(double speed) {
        frontLeft.set(ControlMode.Velocity, speed);
        frontRight.set(ControlMode.Velocity, speed);
    }

    public void open() {
        leftPiston.set(true);
        rightPiston.set(false);
    }

    public void close() {
        leftPiston.set(false);
        rightPiston.set(false);
    }

    public void setDartPosition(double position) {
        dart.set(ControlMode.Position, position);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DoNothing());
    }
}