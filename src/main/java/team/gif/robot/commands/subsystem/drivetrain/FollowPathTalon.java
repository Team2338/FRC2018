package team.gif.robot.commands.subsystem.drivetrain;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import team.gif.robot.Globals;
import team.gif.robot.RobotMap;
import team.gif.robot.subsystems.Drivetrain;

public class FollowPathTalon extends Command {

    private Drivetrain drivetrain = Drivetrain.getInstance();
    private TalonSRX leftTalon = drivetrain.getLeftMaster();
    private TalonSRX rightTalon = drivetrain.getRightMaster();
    private PigeonIMU pigeon = drivetrain.getPigeon();
    private Trajectory trajectory;
    private MotionProfileStatus leftStatus = new MotionProfileStatus();
    private MotionProfileStatus rightStatus = new MotionProfileStatus();

    class PeriodicRunnable implements Runnable {
        public void run() {
            leftTalon.processMotionProfileBuffer();
            rightTalon.processMotionProfileBuffer();
        }
    }
    Notifier notifier = new Notifier(new PeriodicRunnable());

    public FollowPathTalon(Trajectory trajectory) {
        requires(drivetrain);
        this.trajectory = trajectory;
        leftTalon.changeMotionControlFramePeriod(5);
        rightTalon.changeMotionControlFramePeriod(5);
        notifier.startPeriodic(0.005);
    }

    protected void initialize() {
        init();
        zeroSensors();
        reset();
        startFilling();
    }

    protected void execute() {
        leftTalon.getMotionProfileStatus(leftStatus);
        rightTalon.getMotionProfileStatus(rightStatus);

        rightTalon.set(ControlMode.MotionProfileArc, 1, DemandType.ArbitraryFeedForward, 0.1);
        leftTalon.follow(rightTalon, FollowerType.AuxOutput1);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {

    }

    private void reset() {
        leftTalon.clearMotionProfileTrajectories();
        rightTalon.clearMotionProfileTrajectories();
    }

    private TrajectoryPoint.TrajectoryDuration GetTrajectoryDuration(int durationMs) {
        /* create return value */
        TrajectoryPoint.TrajectoryDuration retval = TrajectoryPoint.TrajectoryDuration.Trajectory_Duration_0ms;
        /* convert duration to supported type */
        retval = retval.valueOf(durationMs);
        /* check that it is valid */
        if (retval.value != durationMs) {
            DriverStation.reportError("Trajectory Duration not supported - use configMotionProfileTrajectoryPeriod instead", false);
        }
        /* pass to caller */
        return retval;
    }

    private void startFilling() {
        TrajectoryPoint point = new TrajectoryPoint();
        int totalCount = trajectory.length();

        if (leftStatus.hasUnderrun) {
            System.out.println("Left Underrun");
            leftTalon.clearMotionProfileHasUnderrun(0);
        }

        if (rightStatus.hasUnderrun) {
            System.out.println("Right Underrun");
            rightTalon.clearMotionProfileHasUnderrun(0);
        }

        leftTalon.clearMotionProfileTrajectories();
        rightTalon.clearMotionProfileTrajectories();

        leftTalon.configMotionProfileTrajectoryPeriod(0, 0);
        rightTalon.configMotionProfileTrajectoryPeriod(0, 0);

        for (int i = 0; i < totalCount; i++) {
            point.position = trajectory.segments[i].position / (Globals.Drivetrain.WHEEL_DIAMETER_IN * Math.PI) * Globals.Drivetrain.TICKS_PER_REVOLUTION;
            point.velocity = trajectory.segments[i].velocity / (Globals.Drivetrain.WHEEL_DIAMETER_IN * Math.PI) * Globals.Drivetrain.TICKS_PER_REVOLUTION / 10.0;
            point.headingDeg = Pathfinder.r2d(trajectory.segments[i].heading);
            point.profileSlotSelect0 = 0;
            point.profileSlotSelect1 = 0;
            point.timeDur = GetTrajectoryDuration((int)trajectory.segments[i].dt);

            point.zeroPos = false;
            if (i == 0) { point.zeroPos = true; }

            point.isLastPoint = false;
            if ((i+1) == totalCount) { point.isLastPoint = true; }

            leftTalon.pushMotionProfileTrajectory(point);
            rightTalon.pushMotionProfileTrajectory(point);
        }

    }

    private void init() {
        rightTalon.configRemoteFeedbackFilter(RobotMap.Drivetrain.LEFT_MASTER_ID, RemoteSensorSource.TalonSRX_SelectedSensor, 0, 0);
        rightTalon.configRemoteFeedbackFilter(RobotMap.Drivetrain.LEFT_FOLLOWER_ID, RemoteSensorSource.Pigeon_Yaw, 1, 0);

        rightTalon.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.RemoteSensor0, 0);
        rightTalon.configSensorTerm(SensorTerm.Sum1, FeedbackDevice.CTRE_MagEncoder_Relative, 0);
        rightTalon.configSensorTerm(SensorTerm.Diff1, FeedbackDevice.RemoteSensor0, 0);
        rightTalon.configSensorTerm(SensorTerm.Diff0, FeedbackDevice.CTRE_MagEncoder_Relative, 0);

        rightTalon.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor1, 1, 0);
        rightTalon.configSelectedFeedbackCoefficient(3600 / 8192, 1, 0);

        rightTalon.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20, 0);
        rightTalon.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20, 0);
        rightTalon.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20, 0);
        rightTalon.setStatusFramePeriod(StatusFrame.Status_10_Targets, 20, 0);
        /* speed up the left since we are polling it's sensor */
        leftTalon.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5, 0);

        leftTalon.configNeutralDeadband(0.001, 0);
        rightTalon.configNeutralDeadband(0.001, 0);

        leftTalon.configPeakOutputForward(+1.0, 0);
        leftTalon.configPeakOutputReverse(-1.0, 0);
        rightTalon.configPeakOutputForward(+1.0, 0);
        rightTalon.configPeakOutputReverse(-1.0, 0);

        rightTalon.config_kP(0, 0.1, 0);
        rightTalon.config_kI(0, 0.0, 0);
        rightTalon.config_kD(0, 0.0, 0);
        rightTalon.config_kF(0, 0.0, 0);
        rightTalon.config_IntegralZone(0, 100, 0);

        rightTalon.config_kP(1, 2.0, 0);
        rightTalon.config_kI(1, 0.0, 0);
        rightTalon.config_kD(1, 4.0, 0);
        rightTalon.config_kF(1, 0.0, 0);
        rightTalon.config_IntegralZone(0, 200, 0);

        rightTalon.config_kP(2, 0.1, 0);
        rightTalon.config_kI(2, 0.0, 0);
        rightTalon.config_kD(2, 20.0, 0);
        rightTalon.config_kF(2, 1023.0/6800.0, 0);
        rightTalon.config_IntegralZone(0, 300, 0);

        rightTalon.config_kP(3, 1.0, 0);
        rightTalon.config_kI(3, 0.0, 0);
        rightTalon.config_kD(3, 0.0, 0);
        rightTalon.config_kF(3, 1023.0/6800.0, 0);
        rightTalon.config_IntegralZone(0, 400, 0);

        rightTalon.configSetParameter(ParamEnum.ePIDLoopPeriod, 1, 0x00, 0, 0);
        rightTalon.configSetParameter(ParamEnum.ePIDLoopPeriod, 1, 0x00, 1, 0);

        rightTalon.configAuxPIDPolarity(false, 0);

    }

    private void zeroSensors() {
        leftTalon.setSelectedSensorPosition(0, 0, 0);
        rightTalon.setSelectedSensorPosition(0, 0, 0);
        pigeon.setYaw(0, 0);
        pigeon.setAccumZAngle(0, 0);
    }

}
