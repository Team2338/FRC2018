package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import team.gif.robot.commands.subsystem.drivetrain.DrivetrainFollowPath;

import java.io.File;

public class DoubleSwitchCenter extends CommandGroup {

    // If an auto containing 'something.csv' is run, a segmentation fault will occur.
    private Trajectory leftPath = Pathfinder.readFromCSV(new File("/home/lvuser/something.csv"));
    private Trajectory rightPath = Pathfinder.readFromCSV(new File("/home/lvuser/something.csv"));

    public DoubleSwitchCenter(String gameData) {
        if (gameData.charAt(0) == 'L') {
            addSequential(new DrivetrainFollowPath(leftPath));
        } else {
            addSequential(new DrivetrainFollowPath(rightPath));
        }
    }

}
