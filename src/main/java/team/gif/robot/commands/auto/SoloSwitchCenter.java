package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import team.gif.robot.Globals;
import team.gif.robot.commands.subsystem.arm.ArmEject;
import team.gif.robot.commands.subsystem.arm.ArmSetPosition;
import team.gif.robot.commands.subsystem.drivetrain.DrivetrainFollowPath;

import java.io.File;

public class SoloSwitchCenter extends CommandGroup{

    Trajectory leftPath = Pathfinder.readFromCSV(new File("/home/lvuser/centertoleftswitch.csv"));
    Trajectory rightPath = Pathfinder.readFromCSV(new File("/home/lvuser/centertorightswitch.csv"));

    public SoloSwitchCenter(String gameData) {
        if (gameData.charAt(0) == 'L') {
            addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
            addSequential(new DrivetrainFollowPath(leftPath));
            addSequential(new ArmEject(0.75), 0.5);
        } else {
            addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
            addSequential(new DrivetrainFollowPath(rightPath));
            addSequential(new ArmEject(0.75), 0.5);
        }
    }
}
