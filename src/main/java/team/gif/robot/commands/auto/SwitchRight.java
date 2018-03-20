package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import team.gif.robot.Globals;
import team.gif.robot.commands.subsystem.arm.ArmEject;
import team.gif.robot.commands.subsystem.arm.ArmSetPosition;
import team.gif.robot.commands.subsystem.drivetrain.FollowPathForward;

import java.io.File;

public class SwitchRight extends CommandGroup {

    Trajectory righttoleftswitch = Pathfinder.readFromCSV(new File("/home/lvuser/righttoleftswitch.csv"));
    Trajectory righttorightswitch = Pathfinder.readFromCSV(new File("/home/lvuser/righttorightswitch.csv"));

    public SwitchRight(String gameData) {
        if (gameData.charAt(0) == 'L') {
            addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
            addSequential(new FollowPathForward(righttoleftswitch));
            addSequential(new ArmEject(0.75), 0.5);
        } else {
            addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
            addSequential(new FollowPathForward(righttorightswitch));
            addSequential(new ArmEject(0.75), 0.5);
        }
    }
}
