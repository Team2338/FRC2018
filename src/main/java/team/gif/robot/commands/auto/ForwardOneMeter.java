package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import team.gif.robot.Globals;
import team.gif.robot.commands.subsystem.drivetrain.DrivetrainFollowPath;

public class ForwardOneMeter extends CommandGroup {

    private Waypoint[] points = new Waypoint[] {
            new Waypoint(0, 0, 0),               // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
            new Waypoint(0, 1, 0)      // Waypoint @ x=-2, y=-2, exit angle=0 radians             // Waypoint @ x=0, y=0,   exit angle=0 radians
    };

    private Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH,
            Globals.TIME_STEP, Globals.MAX_VELOCITY, Globals.MAX_ACCELERATION, Globals.MAX_JERK);
    Trajectory leftPath = Pathfinder.generate(points, config);

    public ForwardOneMeter(String gameData) {
        if (gameData.charAt(1) == 'L') {
            addSequential(new DrivetrainFollowPath(leftPath));
        } else {
            addSequential(new DrivetrainFollowPath(leftPath));
        }
    }
}
