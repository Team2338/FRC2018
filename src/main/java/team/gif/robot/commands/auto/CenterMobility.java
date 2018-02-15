package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Waypoint;

public class CenterMobility extends CommandGroup {

    private Waypoint[] center = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(0, 1, 0)
    };

    public CenterMobility(String gameData) {
        if (gameData.charAt(1) == 'L') {

        } else {

        }
    }
}
