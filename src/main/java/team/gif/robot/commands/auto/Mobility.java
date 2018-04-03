package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import team.gif.robot.commands.subsystem.drivetrain.FollowPathForward;

import java.io.File;

public class Mobility extends CommandGroup {

    private Trajectory threemeter = Pathfinder.readFromCSV(new File("/home/lvuser/tenfeet.csv"));

    public Mobility(String gameData) {

        addSequential(new FollowPathForward(threemeter));

        // TODO: Maybe turn around and also pickup cube
    }

}
