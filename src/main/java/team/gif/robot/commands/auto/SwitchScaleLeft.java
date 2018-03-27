package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import team.gif.robot.Globals;
import team.gif.robot.commands.subsystem.arm.*;
import team.gif.robot.commands.subsystem.drivetrain.DrivetrainConstantPercent;
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
            addSequential(new FollowPathForward(lefttoleftscale));
            addParallel(new ArmLaunchShort());
            addSequential(new WaitCommand(0.5));
            addSequential(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
            if (gameData.charAt(0) == 'L') {
                addSequential(new FollowPathForward(leftscaletoleftswitch));
            } else {
                addSequential(new FollowPathForward(leftscaletorightswitch));
            }
            addSequential(new CollectUntilCollect());
            addSequential(new ArmDumbCollect(), 0.25);
            addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
            addSequential(new DrivetrainConstantPercent(-0.2, 0.5));
            addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
            addSequential(new DrivetrainConstantPercent(0.2, 1));
            addSequential(new ArmEject(0.5), 1);
            addSequential(new DrivetrainConstantPercent(-0.2, 1));
        } else {
            addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));
            addSequential(new FollowPathForward(lefttorightscale));
            addParallel(new ArmLaunchShort());
            addSequential(new WaitCommand(0.5));
            addSequential(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
            addSequential(new FollowPathForward(rightscaletorightswitch));
            addSequential(new CollectUntilCollect());
            addSequential(new ArmDumbCollect(), 0.25);
            addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
            addSequential(new DrivetrainConstantPercent(-0.2, 0.5));
//            addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
//            addSequential(new DrivetrainConstantPercent(0.2, 1));
//            addSequential(new ArmEject(0.5), 1);
//            addSequential(new DrivetrainConstantPercent(-0.2, 1));
        }
    }

    protected void end() {
        System.out.println("This auto took " + timeSinceInitialized() + " seconds.");
    }
}
