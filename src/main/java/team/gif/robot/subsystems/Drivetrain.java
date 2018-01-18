package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import team.gif.lib.GIFDrive;
import team.gif.robot.RobotMap;
import team.gif.robot.commands.Drive;

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

//    private Solenoid shifter = new Solenoid(RobotMap.Drivetrain.SHIFTER_ID);

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

    public double getLeftCurrent() {
        return leftMaster.getOutputCurrent();
    }

    public double getRightCurrent() {
        return rightMaster.getOutputCurrent();
    }

//    public boolean checkSystem() {
//        System.out.println("Testing DRIVE.---------------------------------");
//        final double kCurrentThres = 0.5;
//        final double kRpmThres = 300;
//
//        leftMaster.set(ControlMode.PercentOutput, 0.0);
//        leftFollower.set(ControlMode.PercentOutput, 0.0);
//        rightMaster.set(ControlMode.PercentOutput, 0.0);
//        rightFollower.set(ControlMode.PercentOutput, 0.0);
//
//        leftMaster.set(ControlMode.PercentOutput, -0.5);
//        Timer.delay(4.0);
//        final double currentLeftMaster = leftMaster.getOutputCurrent();
//        final double rpmLeftMaster = leftMaster.getSelectedSensorVelocity(RobotMap.Drivetrain.LEFT_ENCODER_ID);
//        leftMaster.set(ControlMode.PercentOutput, 0.0);
//
//        Timer.delay(2.0);
//
//        leftFollower.set(ControlMode.PercentOutput, -0.5);
//        Timer.delay(4.0);
//        final double currentLeftFollower = leftFollower.getOutputCurrent();
//        final double rpmLeftFollower = leftMaster.getSelectedSensorVelocity(RobotMap.Drivetrain.LEFT_ENCODER_ID);
//        leftFollower.set(ControlMode.PercentOutput, 0.0);
//
//        Timer.delay(2.0);
//
//        rightMaster.set(ControlMode.PercentOutput, 0.5);
//        Timer.delay(4.0);
//        final double currentRightMaster = rightMaster.getOutputCurrent();
//        final double rpmRightMaster = rightMaster.getSelectedSensorVelocity(RobotMap.Drivetrain.RIGHT_ENCODER_ID);
//        rightMaster.set(ControlMode.PercentOutput, 0.0);
//
//        Timer.delay(2.0);
//
//        rightFollower.set(6.0f);
//        Timer.delay(4.0);
//        final double currentLeftSlave = rightFollower.getOutputCurrent();
//        final double rpmLeftSlave = rightMaster.getSpeed();
//        rightFollower.set(0.0);
//
//        leftMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
//        rightMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
//
//        leftFollower.changeControlMode(CANTalon.TalonControlMode.Follower);
//        leftFollower.set(Constants.kRightDriveMasterId);
//
//        rightFollower.changeControlMode(CANTalon.TalonControlMode.Follower);
//        rightFollower.set(Constants.kLeftDriveMasterId);
//
//        System.out.println("Drive Right Master Current: " + currentLeftMaster + " Drive Right Slave Current: "
//                + currentRightSlave);
//        System.out.println(
//                "Drive Left Master Current: " + currentLeftMaster + " Drive Left Slave Current: " + currentLeftSlave);
//        System.out.println("Drive RPM RMaster: " + rpmLeftMaster + " RSlave: " + rpmRightSlave + " LMaster: "
//                + rpmLeftMaster + " LSlave: " + rpmLeftSlave);
//
//        boolean failure = false;
//
//        if (currentLeftMaster < kCurrentThres) {
//            failure = true;
//            System.out.println("!!!!!!!!!!!!!!!!!! Drive Right Master Current Low !!!!!!!!!!");
//        }
//
//        if (currentRightSlave < kCurrentThres) {
//            failure = true;
//            System.out.println("!!!!!!!!!!!!!!!!!! Drive Right Slave Current Low !!!!!!!!!!");
//        }
//
//        if (currentLeftMaster < kCurrentThres) {
//            failure = true;
//            System.out.println("!!!!!!!!!!!!!!!!!! Drive Left Master Current Low !!!!!!!!!!");
//        }
//
//        if (currentLeftSlave < kCurrentThres) {
//            failure = true;
//            System.out.println("!!!!!!!!!!!!!!!!!! Drive Left Slave Current Low !!!!!!!!!!");
//        }
//
//        if (!Util.allCloseTo(Arrays.asList(currentLeftMaster, currentRightSlave), currentLeftMaster,
//                5.0)) {
//            failure = true;
//            System.out.println("!!!!!!!!!!!!!!!!!! Drive Right Currents Different !!!!!!!!!!");
//        }
//
//        if (!Util.allCloseTo(Arrays.asList(currentLeftMaster, currentLeftSlave), currentLeftSlave,
//                5.0)) {
//            failure = true;
//            System.out.println("!!!!!!!!!!!!!!!!!! Drive Left Currents Different !!!!!!!!!!!!!");
//        }
//
//        if (rpmLeftMaster < kRpmThres) {
//            failure = true;
//            System.out.println("!!!!!!!!!!!!!!!!!! Drive Right Master RPM Low !!!!!!!!!!!!!!!!!!!");
//        }
//
//        if (rpmRightSlave < kRpmThres) {
//            failure = true;
//            System.out.println("!!!!!!!!!!!!!!!!!! Drive Right Slave RPM Low !!!!!!!!!!!!!!!!!!!");
//        }
//
//        if (rpmLeftMaster < kRpmThres) {
//            failure = true;
//            System.out.println("!!!!!!!!!!!!!!!!!! Drive Left Master RPM Low !!!!!!!!!!!!!!!!!!!");
//        }
//
//        if (rpmLeftSlave < kRpmThres) {
//            failure = true;
//            System.out.println("!!!!!!!!!!!!!!!!!! Drive Left Slave RPM Low !!!!!!!!!!!!!!!!!!!");
//        }
//
//        if (!Util.allCloseTo(Arrays.asList(rpmLeftMaster, rpmRightSlave, rpmLeftMaster, rpmLeftSlave),
//                rpmLeftMaster, 250)) {
//            failure = true;
//            System.out.println("!!!!!!!!!!!!!!!!!!! Drive RPMs different !!!!!!!!!!!!!!!!!!!");
//        }
//
//        return !failure;
//    }

    protected void initDefaultCommand() {
        setDefaultCommand(new Drive());
    }
}
