package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;
import team.gif.robot.Globals;
import team.gif.robot.subsystems.Drivetrain;

public class FollowPath extends Command {

    Drivetrain drivetrain = Drivetrain.getInstance();
    TankModifier modifier;
    EncoderFollower left;
    EncoderFollower right;

    public FollowPath(Trajectory trajectory) {
        requires(drivetrain);
        modifier = new TankModifier(trajectory).modify(Globals.WHEELBASE_WIDTH_M);
    }

    protected void initialize() {
        left = new EncoderFollower(modifier.getLeftTrajectory());
        right = new EncoderFollower(modifier.getRightTrajectory());

        left.configureEncoder(drivetrain.getLeftEncPosition(), Globals.TICKS_PER_REVOLUTION, Globals.WHEEL_DIAMETER_M);
        right.configureEncoder(drivetrain.getRightEncPosition(), Globals.TICKS_PER_REVOLUTION, Globals.WHEEL_DIAMETER_M);

        left.configurePIDVA(Globals.DRIVE_P, Globals.DRIVE_I, Globals.DRIVE_D, 1 / Globals.MAX_VELOCITY, 0.0);
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {

    }
}
