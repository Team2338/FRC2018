package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import team.gif.robot.RobotMap;
import team.gif.robot.commands.Drive;

public class Drivetrain extends Subsystem {

    private static Drivetrain instance = null;

    public static Drivetrain getInstance() {
        return instance == null ? new Drivetrain() : instance;
    }

    // Hardware
    TalonSRX leftMaster = new TalonSRX(RobotMap.Drivetrain.LEFT_MASTER_ID);
    TalonSRX leftFollower = new TalonSRX(RobotMap.Drivetrain.LEFT_FOLLOWER_ID);
    TalonSRX rightMaster = new TalonSRX(RobotMap.Drivetrain.RIGHT_MASTER_ID);
    TalonSRX rightFollower = new TalonSRX(RobotMap.Drivetrain.RIGHT_FOLLOWER_ID);

    private Drivetrain() {
        leftMaster.set(ControlMode.PercentOutput, 0);
        leftFollower.follow(leftMaster);
        rightMaster.set(ControlMode.PercentOutput, 0);
        rightFollower.follow(rightMaster);
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new Drive());
    }

}
