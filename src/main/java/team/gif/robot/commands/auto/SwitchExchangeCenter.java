package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import team.gif.robot.Globals;
import team.gif.robot.commands.subsystem.drivetrain.DrivetrainFollowPath;

import java.io.File;

public class SwitchExchangeCenter extends CommandGroup {

    private Trajectory leftPath = Pathfinder.readFromCSV(new File("/home/lvuser/something.csv"));
    private Trajectory rightPath = Pathfinder.readFromCSV(new File("/home/lvuser/something.csv"));

    public SwitchExchangeCenter(String gameData) {
        if (gameData.charAt(0) == 'L') {
            addSequential(new DrivetrainFollowPath(leftPath));
        } else {
            addSequential(new DrivetrainFollowPath(rightPath));
        }
    }
}
