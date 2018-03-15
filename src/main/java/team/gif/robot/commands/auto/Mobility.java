package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import team.gif.robot.commands.subsystem.drivetrain.DrivetrainFollowPath;

import java.io.File;

public class Mobility extends CommandGroup {

    // If an auto containing 'something.csv' is run, a segmentation fault will occur.
    private Trajectory threemeter = Pathfinder.readFromCSV(new File("/home/lvuser/threemeter.csv"));

    public Mobility(String gameData) {
        addSequential(new DrivetrainFollowPath(threemeter));
    }

}
