package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import team.gif.lib.TalonSRXConfigurator;
import team.gif.robot.RobotMap;
import team.gif.robot.commands.subsystems.ramp.DeployRamp;
import team.gif.robot.commands.subsystems.ramp.RampTeleOp;

public class Ramp extends Subsystem{
    private static Ramp instance = null;

    public static Ramp getInstance() {
        if (instance == null) {
            instance = new Ramp();
        }
        return instance;
    }

    // Hardware
    private TalonSRX leftMaster = TalonSRXConfigurator.createDefaultTalon(RobotMap.Ramp.LEFT_MASTER_ID);
    private TalonSRX leftFollower = TalonSRXConfigurator.createFollowerTalon(RobotMap.Ramp.LEFT_FOLLOWER_ID, RobotMap.Ramp.LEFT_MASTER_ID);
    private TalonSRX rightMaster = TalonSRXConfigurator.createDefaultTalon(RobotMap.Ramp.RIGHT_MASTER_ID);
    private TalonSRX rightFollower = TalonSRXConfigurator.createFollowerTalon(RobotMap.Ramp.RIGHT_FOLLOWER_ID, RobotMap.Ramp.RIGHT_MASTER_ID);

    private Servo release = new Servo(RobotMap.Ramp.RELEASE_SERVO_ID);

    private Ramp() {
        leftMaster.setInverted(false);
        leftFollower.setInverted(true);
        rightMaster.setInverted(true);
        rightFollower.setInverted(false);

        deployRamps(false);
    }

    public void deployRamps(boolean deploy) {
        release.set(deploy ? 0.75 : 0.25);
    }

    public void setReleasePosition(double position) {
        release.set(position);
    }

    public void set(double speed) {
        leftMaster.set(ControlMode.PercentOutput, speed);
        leftFollower.set(ControlMode.PercentOutput, speed);
    }

    protected void initDefaultCommand() {
//        setDefaultCommand(new RampTeleOp());
    }
}
