package team.gif.robot.commands.subsystem.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import team.gif.robot.Globals;
import team.gif.robot.subsystems.Drivetrain;

public class FollowPathForward extends Command {

    private Drivetrain drivetrain = Drivetrain.getInstance();
    private TankModifier modifier;
    private EncoderFollower left;
    private EncoderFollower right;
    private double angleError;

    public FollowPathForward(Trajectory trajectory) {
        requires(drivetrain);
        modifier = new TankModifier(trajectory).modify(Globals.Drivetrain.WHEELBASE_WIDTH_IN);
    }

    protected void initialize() {
        drivetrain.resetEncoders();
        drivetrain.resetGyro();

        left = new EncoderFollower(modifier.getLeftTrajectory());
        right = new EncoderFollower(modifier.getRightTrajectory());

        left.configureEncoder(0, Globals.Drivetrain.TICKS_PER_REVOLUTION,
                Globals.Drivetrain.WHEEL_DIAMETER_IN);
        right.configureEncoder(0, Globals.Drivetrain.TICKS_PER_REVOLUTION,
                Globals.Drivetrain.WHEEL_DIAMETER_IN);

        left.configurePIDVA(Globals.Drivetrain.DRIVE_P, Globals.Drivetrain.DRIVE_I,
                Globals.Drivetrain.DRIVE_D, Globals.Drivetrain.kVLeftForward, Globals.Drivetrain.kALeftForward);
        right.configurePIDVA(Globals.Drivetrain.DRIVE_P, Globals.Drivetrain.DRIVE_I,
                Globals.Drivetrain.DRIVE_D, Globals.Drivetrain.kVRightForward, Globals.Drivetrain.kARightForward);
    }

    protected void execute() {
        double leftOutput = left.calculate(drivetrain.getLeftEncPosition());
        double rightOutput = right.calculate(drivetrain.getRightEncPosition());

        leftOutput += leftOutput >= 0.01 ? Math.copySign(Globals.Drivetrain.kInterceptLeftForward, leftOutput) : 0.0;
        rightOutput += rightOutput >= 0.01 ? Math.copySign(Globals.Drivetrain.kInterceptRightForward, rightOutput) : 0.0;

        double gyroHeading = drivetrain.getHeading();
        double desiredHeading = Pathfinder.r2d(left.getHeading());

        double angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
        angleError = angleDifference;
        double turn = Globals.Drivetrain.gyroSensitivity * (-1.0/80.0) * angleDifference;
//        turn = 0.0;

        drivetrain.setLeft(leftOutput + turn);
        drivetrain.setRight(rightOutput - turn);
    }

    protected boolean isFinished() {
        return left.isFinished() && right.isFinished() && angleError < 5.0;
    }

    protected void end() {
        drivetrain.setLeft(0.0);
        drivetrain.setRight(0.0);
    }
}
