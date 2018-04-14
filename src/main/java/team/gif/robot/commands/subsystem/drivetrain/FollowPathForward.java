package team.gif.robot.commands.subsystem.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import team.gif.lib.MiniPID;
import team.gif.lib.PIDCalculator;
import team.gif.robot.Globals;
import team.gif.robot.subsystems.Drivetrain;

public class FollowPathForward extends Command {

    private Drivetrain drivetrain = Drivetrain.getInstance();
    private TankModifier modifier;
    private EncoderFollower left;
    private EncoderFollower right;
//    private PIDCalculator turnPID;
    private MiniPID turnPID;
    private double angleError;

    public FollowPathForward(Trajectory trajectory) {
        requires(drivetrain);
        modifier = new TankModifier(trajectory).modify(Globals.Drivetrain.WHEELBASE_WIDTH_IN);
//        turnPID = new PIDCalculator(Globals.Drivetrain.TURN_P, Globals.Drivetrain.TURN_I, Globals.Drivetrain.TURN_D, 0.5);
        turnPID = new MiniPID(0.012, 0.0, 0.0);
        turnPID.setMaxIOutput(0.1);
        turnPID.setOutputLimits(1.0);
        turnPID.setDirection(false);
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

        leftOutput += Math.abs(leftOutput) >= 0.01 ? Math.copySign(Globals.Drivetrain.kInterceptLeftForward, leftOutput) : 0.0;
        rightOutput += Math.abs(rightOutput) >= 0.01 ? Math.copySign(Globals.Drivetrain.kInterceptRightForward, rightOutput) : 0.0;

        double gyroHeading = drivetrain.getHeading();
        double desiredHeading = Pathfinder.r2d(left.getHeading());

        double angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
        angleError = angleDifference;
        double turn = Globals.Drivetrain.gyroSensitivity * (-1.0/80.0) * angleDifference;
        turn = (-1.0/80.0) * angleDifference;
        turn = turnPID.getOutput(turn);
        turn = -turnPID.getOutput(gyroHeading, Pathfinder.boundHalfDegrees(desiredHeading));

        System.out.println("Current Heading: " + gyroHeading + ", Desired Heading: " + Pathfinder.boundHalfDegrees(desiredHeading));

        drivetrain.setLeft(leftOutput + turn);
        drivetrain.setRight(rightOutput - turn);
        System.out.println("Angle Error: " + angleError);
    }

    protected boolean isFinished() {
        return left.isFinished() && right.isFinished() && Math.abs(angleError) < 10.0;
    }

    protected void end() {
        drivetrain.setLeft(0.0);
        drivetrain.setRight(0.0);
    }
}
