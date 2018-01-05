package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import team.gif.lib.GIFDrive;
import team.gif.robot.RobotMap;
import team.gif.robot.commands.Drive;

public class Drivetrain extends Subsystem {

    private static Drivetrain instance = null;

    public static Drivetrain getInstance() {
        return instance == null ? new Drivetrain() : instance;
    }

    // Hardware
    private TalonSRX leftMaster = new TalonSRX(RobotMap.Drivetrain.LEFT_MASTER_ID);
    private TalonSRX leftFollower = new TalonSRX(RobotMap.Drivetrain.LEFT_FOLLOWER_ID);
    private TalonSRX rightMaster = new TalonSRX(RobotMap.Drivetrain.RIGHT_MASTER_ID);
    private TalonSRX rightFollower = new TalonSRX(RobotMap.Drivetrain.RIGHT_FOLLOWER_ID);

    private GIFDrive drive = new GIFDrive(leftMaster, rightMaster);

    private Drivetrain() {
        leftMaster.set(ControlMode.PercentOutput, 0);
        leftFollower.follow(leftMaster);
        rightMaster.set(ControlMode.PercentOutput, 0);
        rightFollower.follow(rightMaster);
    }

    public void curvatureDrive(double speed, double rotation, boolean isQuickTurn) {
        curvatureDrive(speed, rotation, isQuickTurn);
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new Drive());
    }

}
