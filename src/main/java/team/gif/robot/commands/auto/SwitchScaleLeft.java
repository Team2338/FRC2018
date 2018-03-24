package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import team.gif.robot.Globals;
import team.gif.robot.commands.subsystem.arm.ArmLaunchShort;
import team.gif.robot.commands.subsystem.arm.ArmSetPosition;
import team.gif.robot.commands.subsystem.drivetrain.FollowPathForward;
import team.gif.robot.commands.subsystem.drivetrain.FollowPathReverse;

import java.io.File;

public class SwitchScaleLeft extends CommandGroup{

    Trajectory lefttoleftscale = Pathfinder.readFromCSV(new File("/home/lvuser/lefttoleftscale.csv"));
    Trajectory lefttorightscale = Pathfinder.readFromCSV(new File("/home/lvuser/lefttorightscale.csv"));
    Trajectory leftscaletoleftswitch = Pathfinder.readFromCSV(new File("/home/lvuser/leftscaletoleftswitch.csv"));
    Trajectory leftscaletorightswitch = Pathfinder.readFromCSV(new File("/home/lvuser/leftscaletorightswitch.csv"));
    Trajectory rightscaletoleftswitch = Pathfinder.readFromCSV(new File("/home/lvuser/rightscaletoleftswitch.csv"));
    Trajectory rightscaletorightswitch = Pathfinder.readFromCSV(new File("/home/lvuser/rightscaletorightswitch.csv"));

    public SwitchScaleLeft(String gameData) {
        if (gameData.charAt(1) == 'L') {
            addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));
            addSequential(new FollowPathReverse(lefttoleftscale));
            addSequential(new ArmLaunchShort());
            addParallel(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
            if (gameData.charAt(0) == 'L') {
                addParallel(new FollowPathForward(leftscaletoleftswitch));
            } else {
                addParallel(new FollowPathForward(leftscaletorightswitch));
            }
        } else {
            addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));
            addSequential(new FollowPathReverse(lefttorightscale));
            addSequential(new ArmLaunchShort());
            addParallel(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
            if (gameData.charAt(0) == 'L') {
                addParallel(new FollowPathForward(rightscaletoleftswitch));
            } else {
                addParallel(new FollowPathForward(rightscaletorightswitch));
            }
        }
    }
}
