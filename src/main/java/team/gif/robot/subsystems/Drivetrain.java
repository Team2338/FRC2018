package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import team.gif.lib.GIFDrive;
import team.gif.lib.MotorLogger;
import team.gif.lib.TalonSRXConfigurator;
import team.gif.robot.Robot;
import team.gif.robot.RobotMap;
import team.gif.robot.commands.subsystem.drivetrain.DrivetrainTeleOp;

public class Drivetrain extends Subsystem {

    private static Drivetrain instance = null;

    public static Drivetrain getInstance() {
        if (instance == null) {
            instance = new Drivetrain();
        }
        return instance;
    }

    // Hardware
    private TalonSRX leftMaster = TalonSRXConfigurator.createDefaultTalon(RobotMap.Drivetrain.LEFT_MASTER_ID);
    private TalonSRX leftFollower = TalonSRXConfigurator.createFollowerTalon(RobotMap.Drivetrain.LEFT_FOLLOWER_ID, RobotMap.Drivetrain.LEFT_MASTER_ID);
    private TalonSRX rightMaster = TalonSRXConfigurator.createDefaultTalon(RobotMap.Drivetrain.RIGHT_MASTER_ID);
    private TalonSRX rightFollower = TalonSRXConfigurator.createFollowerTalon(RobotMap.Drivetrain.RIGHT_FOLLOWER_ID, RobotMap.Drivetrain.RIGHT_MASTER_ID);
    private PigeonIMU pigeon = new PigeonIMU(leftFollower);

    private GIFDrive drive = new GIFDrive(leftMaster, rightMaster);

//    private MotorLogger logger = Robot.logger;

    private Drivetrain() {
        leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        rightMaster.setInverted(true);
        rightFollower.setInverted(true);
        rightMaster.setSensorPhase(true);
        leftMaster.setSensorPhase(true);
//        leftMaster.configOpenloopRamp(0.25, 0);
//        leftFollower.configOpenloopRamp(0.25, 0);
//        rightMaster.configOpenloopRamp(0.25, 0);
//        rightFollower.configOpenloopRamp(0.25, 0);

        MotorLogger.addMotor(leftMaster);
        MotorLogger.addMotor(rightMaster);
        pigeon.enterCalibrationMode(PigeonIMU.CalibrationMode.Unknown, 100);
    }

    public void curvatureDrive(double speed, double rotation, boolean isQuickTurn) {
        drive.curvatureDrive(speed, rotation, isQuickTurn);
    }

    public void setLeft(double percent) {
        leftMaster.set(ControlMode.PercentOutput, percent);
    }

    public void setRight(double percent) {
        rightMaster.set(ControlMode.PercentOutput, percent);
    }

    public void resetGyro() {
        pigeon.setFusedHeading(0, 0);
    }

    public void resetEncoders() {
        leftMaster.setSelectedSensorPosition(0, 0, 0);
        rightMaster.setSelectedSensorPosition(0, 0, 0);
    }

//    public void startLogger() {
//        logger.run();
//    }
//
//    public void stopLogger() {
//        logger.end();
//    }


    public TalonSRX getLeftMaster() {
        return leftMaster;
    }

    public TalonSRX getRightMaster() {
        return rightMaster;
    }

    public PigeonIMU getPigeon() {
        return pigeon;
    }

    public int getLeftEncPosition() {
        return leftMaster.getSelectedSensorPosition(0);
    }

    public int getRightEncPosition() {
        return rightMaster.getSelectedSensorPosition(0);
    }

    public double getLeftEncVelociy() {
        return leftMaster.getSelectedSensorVelocity(0);
    }

    public double getRightEncVelocity() {
        return rightMaster.getSelectedSensorVelocity(0);
    }

    public double getLeftCurrent() {
        return leftMaster.getOutputCurrent();
    }

    public double getRightCurrent() {
        return rightMaster.getOutputCurrent();
    }

    public PigeonIMU.GeneralStatus getPigeonGeneralStatus() {
        PigeonIMU.GeneralStatus genStatus = new PigeonIMU.GeneralStatus();
        pigeon.getGeneralStatus(genStatus);
        return genStatus;
    }

    public PigeonIMU.FusionStatus getPigeonFusionStatus() {
        PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
        pigeon.getFusedHeading(fusionStatus);
        return fusionStatus;
    }

    public double getHeading() {
        getPigeonGeneralStatus();
        return getPigeonFusionStatus().heading;
//        double[] xyz_dps = new double[3];
//        pigeon.getRawGyro(xyz_dps);
//        pigeon.getAccumGyro(xyz_dps);
//        return xyz_dps[1];
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

        if ((rpmLeftMaster + rpmLeftFollower) / 2 - (rpmRightMaster + rpmRightFollower) / 2 > 250) {
            failure = true;
            System.out.println("!!!!!!!!!!!!!!!!!!! Drive RPMs different !!!!!!!!!!!!!!!!!!!");
        }

        return !failure;
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new DrivetrainTeleOp());
    }
}
