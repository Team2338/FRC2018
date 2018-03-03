package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import team.gif.lib.GameDataCommandGroup;
import team.gif.robot.Globals;
import team.gif.robot.commands.subsystem.drivetrain.DrivetrainFollowPath;

public class FrontSwitchRight extends GameDataCommandGroup {

    private Waypoint[] leftPoints = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(0.135, 2.565, 0),
            new Waypoint(-1.151, 5.480, Pathfinder.d2r(90.0)),
            new Waypoint(-4.720, 5.480, Pathfinder.d2r(90.0)),
            new Waypoint(-5.380, 3.772, Pathfinder.d2r(-90.0))
    };

    private Waypoint[] rightPoints = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(-1.411, 2.565, 0)
    };

    private Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH,
            Globals.Drivetrain.TIME_STEP, Globals.Drivetrain.MAX_VELOCITY, Globals.Drivetrain.MAX_ACCELERATION, Globals.Drivetrain.MAX_JERK);
    Trajectory leftPath = Pathfinder.generate(leftPoints, config);
    Trajectory rightPath = Pathfinder.generate(rightPoints, config);

    private String gameData;

    public FrontSwitchRight() {
        if (gameData.charAt(0) == 'L') {
            addSequential(new DrivetrainFollowPath(leftPath));
        } else {
            addSequential(new DrivetrainFollowPath(rightPath));
        }
    }

    public void setGameData(String gameData) {
        this.gameData = gameData;
    }
}
