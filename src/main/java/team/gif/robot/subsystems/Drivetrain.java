package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import team.gif.lib.GIFDrive;
import team.gif.robot.RobotMap;
import team.gif.robot.commands.subsystems.drivetrain.DrivetrainTeleOp;

public class Drivetrain extends Subsystem {

    private static Drivetrain instance = null;

    public static Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
        }
        return instance;
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
        drive.curvatureDrive(speed, rotation, isQuickTurn);
    }

    public boolean checkSystem() {
        System.out.println("Testing DRIVE.---------------------------------");
        final double CURRENT_THRESHOLD = 0.5;
        final double RPM_THRESHOLD = 300;

        leftMaster.set(ControlMode.PercentOutput, 0.0);
        leftFollower.set(ControlMode.PercentOutput, 0.0);
        rightMaster.set(ControlMode.PercentOutput, 0.0);
        rightFollower.set(ControlMode.PercentOutput, 0.0);

        leftMaster.set(ControlMode.PercentOutput, -0.5);
        Timer.delay(4.0);
        final double currentLeftMaster = leftMaster.getOutputCurrent();
        final double rpmLeftMaster = leftMaster.getSelectedSensorVelocity(0);
        leftMaster.set(ControlMode.PercentOutput, 0.0);

        Timer.delay(2.0);

        leftFollower.set(ControlMode.PercentOutput, -0.5);
        Timer.delay(4.0);
        final double currentLeftFollower = leftFollower.getOutputCurrent();
        final double rpmLeftFollower = leftFollower.getSelectedSensorVelocity(0);
        leftFollower.set(ControlMode.PercentOutput, 0.0);

        Timer.delay(2.0);

        rightMaster.set(ControlMode.PercentOutput, 0.5);
        Timer.delay(4.0);
        final double currentRightMaster = rightMaster.getOutputCurrent();
        final double rpmRightMaster = rightMaster.getSelectedSensorVelocity(0);
        rightMaster.set(ControlMode.PercentOutput, 0.0);

        Timer.delay(2.0);

        rightFollower.set(ControlMode.PercentOutput, 0.5);
        Timer.delay(4.0);
        final double currentRightFollower = rightFollower.getOutputCurrent();
        final double rpmRightFollower = rightFollower.getSelectedSensorVelocity(0);
        rightFollower.set(ControlMode.PercentOutput, 0.0);

        leftFollower.set(ControlMode.Follower, RobotMap.Drivetrain.LEFT_MASTER_ID);
        rightFollower.set(ControlMode.Follower, RobotMap.Drivetrain.RIGHT_MASTER_ID);

        System.out.println(
                "Left Drive Master Current: " + currentLeftMaster + " Left Drive Follower Current: " + currentLeftFollower);
        System.out.println("Right Drive Master Current: " + currentRightMaster + " Right Drive Follower Current: "
                + currentRightFollower);
        System.out.println("RPMs: LMaster: " + rpmLeftMaster + " LSlave: " + rpmLeftFollower + " RMaster: "
                + rpmRightMaster + " RSlave: " + rpmRightFollower);

        boolean failure = false;

        if (currentLeftMaster < CURRENT_THRESHOLD) {
            failure = true;
            System.out.println("!!!!!!!!!!!!!!!!!! Left Drive Master Current Low !!!!!!!!!!");
        }

        if (currentLeftFollower < CURRENT_THRESHOLD) {
            failure = true;
            System.out.println("!!!!!!!!!!!!!!!!!! Left Drive Follower Current Low !!!!!!!!!!");
        }

        if (currentRightMaster < CURRENT_THRESHOLD) {
            failure = true;
            System.out.println("!!!!!!!!!!!!!!!!!! Right Drive Master Current Low !!!!!!!!!!");
        }

        if (currentRightFollower < CURRENT_THRESHOLD) {
            failure = true;
            System.out.println("!!!!!!!!!!!!!!!!!! Right Drive Follower Current Low !!!!!!!!!!");
        }

        if (Math.abs(currentLeftMaster - currentLeftFollower) > 5.0) {
            failure = true;
            System.out.println("!!!!!!!!!!!!!!!!!! Left Drive Currents Different !!!!!!!!!!!!!");
        }

        if (Math.abs(currentRightMaster - currentRightFollower) > 5.0) {
            failure = true;
            System.out.println("!!!!!!!!!!!!!!!!!! Right Drive Currents Different !!!!!!!!!!");
        }

        if (rpmLeftMaster < RPM_THRESHOLD) {
            failure = true;
            System.out.println("!!!!!!!!!!!!!!!!!! Left Drive Master RPM Low !!!!!!!!!!!!!!!!!!!");
        }

        if (rpmLeftFollower < RPM_THRESHOLD) {
            failure = true;
            System.out.println("!!!!!!!!!!!!!!!!!! Left Drive Follower RPM Low !!!!!!!!!!!!!!!!!!!");
        }

        if (rpmRightMaster < RPM_THRESHOLD) {
            failure = true;
            System.out.println("!!!!!!!!!!!!!!!!!! Right Drive Master RPM Low !!!!!!!!!!!!!!!!!!!");
        }

        if (rpmRightFollower < RPM_THRESHOLD) {
            failure = true;
            System.out.println("!!!!!!!!!!!!!!!!!! Drive Right Follower RPM Low !!!!!!!!!!!!!!!!!!!");
        }

        if (Math.abs(rpmLeftMaster - rpmLeftFollower) > 250) {
            failure = true;
            System.out.println("!!!!!!!!!!!!!!!!!!! Left Drive RPMs different !!!!!!!!!!!!!!!!!!!");
        }

        if (Math.abs(rpmRightMaster - rpmRightFollower) > 250) {
            failure = true;
            System.out.println("!!!!!!!!!!!!!!!!!!! Right Drive RPMs different !!!!!!!!!!!!!!!!!!!");
        }

        return !failure;
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new DrivetrainTeleOp());
    }
}
