package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import team.gif.lib.GameDataCommandGroup;
import team.gif.robot.Globals;
import team.gif.robot.commands.subsystem.drivetrain.DrivetrainFollowPath;

public class FrontSwitchCenter extends CommandGroup {

    private Waypoint[] leftPoints = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint( -1.653, 2.565, 0)
    };

    private Waypoint[] rightPoints = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(1.395, 2.565, 0)
    };

    private Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH,
            Globals.Drivetrain.TIME_STEP, Globals.Drivetrain.MAX_VELOCITY, Globals.Drivetrain.MAX_ACCELERATION, Globals.Drivetrain.MAX_JERK);
    Trajectory leftPath = Pathfinder.generate(leftPoints, config);
    Trajectory rightPath = Pathfinder.generate(rightPoints, config);

    public FrontSwitchCenter() {
        if (gameData.charAt(0) == 'L') {
            addSequential(new DrivetrainFollowPath(leftPath));
        } else {
            addSequential(new DrivetrainFollowPath(rightPath));
        }
    }

}
