package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;
import team.gif.robot.Globals;

public class LeftSwitch extends CommandGroup {

    private Waypoint[] leftPoints = new Waypoint[] {
        new Waypoint(0, 0, 0)
    };

    private Waypoint[] rightPoints = new Waypoint[] {
        new Waypoint(0, 0, 0)
    };

    private Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, Globals.TIME_STEP, Globals.MAX_VELOCITY, Globals.MAX_ACCELERATION, Globals.MAX_JERK);
    TankModifier leftPaths = new TankModifier(Pathfinder.generate(leftPoints, config)).modify(Globals.WHEELBASE_WIDTH_M);
    TankModifier rightPaths = new TankModifier(Pathfinder.generate(rightPoints, config)).modify(Globals.WHEELBASE_WIDTH_M);

    public LeftSwitch(String gameData) {
        if (gameData.charAt(1) == 'L') {

        } else {

        }
    }
}
