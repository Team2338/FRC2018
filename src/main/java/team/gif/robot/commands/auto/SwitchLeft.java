package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import team.gif.robot.Globals;
import team.gif.robot.commands.subsystem.arm.ArmEject;
import team.gif.robot.commands.subsystem.arm.ArmSetPosition;
import team.gif.robot.commands.subsystem.drivetrain.DrivetrainConstantPercent;
import team.gif.robot.commands.subsystem.drivetrain.FollowPathForward;

import java.io.File;

public class SwitchLeft extends CommandGroup {

    Trajectory lefttoleftswitch = Pathfinder.readFromCSV(new File("/home/lvuser/lefttoleftswitch.csv"));
    Trajectory tenfeet = Pathfinder.readFromCSV(new File("/home/lvuser/tenfeet.csv"));

    public SwitchLeft(String gameData) {

        if (gameData.charAt(0) == 'L') {
            addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
            addSequential(new FollowPathForward(lefttoleftswitch));
            addSequential(new ArmEject(0.75), 0.5);
            addSequential(new DrivetrainConstantPercent(-0.3, 0.5));

            // TODO: turn and come back. Maybe pick up cube.

        } else { // 'R' Right switch selected. Stay out fo way and just do mobility.
            addSequential(new FollowPathForward(tenfeet));

            // TODO: drive backwards and reposition. Maybe pickup cube.
        }
    }
}
