package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import team.gif.lib.TalonSRXConfigurator;
import team.gif.robot.Globals;
import team.gif.robot.RobotMap;

public class Ramps extends Subsystem {

    private static Ramps instance = null;

    public static Ramps getInstance() {
        if (instance == null) {
            instance = new Ramps();
        }
        return instance;
    }

    // Hardware
    private TalonSRX leftMaster = TalonSRXConfigurator.createDefaultTalon(RobotMap.Ramps.LEFT_MASTER_ID);
    private TalonSRX leftFollower = TalonSRXConfigurator.createFollowerTalon(RobotMap.Ramps.LEFT_FOLLOWER_ID, RobotMap.Ramps.LEFT_MASTER_ID);
    private TalonSRX rightMaster = TalonSRXConfigurator.createDefaultTalon(RobotMap.Ramps.RIGHT_MASTER_ID);
    private TalonSRX rightFollower = TalonSRXConfigurator.createFollowerTalon(RobotMap.Ramps.RIGHT_FOLLOWER_ID, RobotMap.Ramps.RIGHT_MASTER_ID);
    private Servo release = new Servo(RobotMap.Ramps.RELEASE_SERVO_ID);

    private DigitalInput leftLimit = new DigitalInput(RobotMap.Ramps.LEFT_LIMIT_ID);
    private DigitalInput rightLimit = new DigitalInput(RobotMap.Ramps.RIGHT_LIMIT_ID);

    private boolean deployed = false;

    private Ramps() {
        leftMaster.setInverted(true);
        leftFollower.setInverted(false);
        rightMaster.setInverted(true);
        rightFollower.setInverted(false);

        release.set(Globals.Ramps.SERVO_IDLE_POSITION);
    }

    public void deploy(boolean deploy) {
        release.set(deploy ? Globals.Ramps.SERVO_RELEASE_POSITION : Globals.Ramps.SERVO_IDLE_POSITION);
        deployed = true;
    }

    public void setLeft(double percent) {
        leftMaster.set(ControlMode.PercentOutput, percent);
        leftFollower.set(ControlMode.PercentOutput, percent);
    }

    public void setRight(double percent) {
        rightMaster.set(ControlMode.PercentOutput, percent);
        rightFollower.set(ControlMode.PercentOutput, percent);
    }

    public boolean areDeployed() {
        return deployed;
    }

    public boolean getLeftLimit() {
        return !leftLimit.get();
    }

    public boolean getRightLimit() {
        return !rightLimit.get();
    }

    protected void initDefaultCommand() {
    }
}
