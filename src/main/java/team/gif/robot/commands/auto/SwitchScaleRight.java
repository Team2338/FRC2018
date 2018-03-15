package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import team.gif.robot.Globals;
import team.gif.robot.commands.subsystem.arm.ArmLaunchShort;
import team.gif.robot.commands.subsystem.arm.ArmSetPosition;
import team.gif.robot.commands.subsystem.drivetrain.DrivetrainConstantPercent;
import team.gif.robot.commands.subsystem.drivetrain.DrivetrainFollowPath;
import team.gif.robot.subsystems.Drivetrain;

import java.io.File;

public class SwitchScaleRight extends CommandGroup {

    private Trajectory righttorightscale = Pathfinder.readFromCSV(new File("/home/lvuser/righttorightscale.csv"));
//    private Trajectory rightPath = Pathfinder.readFromCSV(new File("/home/lvuser/something.csv"));

    protected void initialize() {
        Drivetrain.getInstance().resetEncoders();
    }

    public SwitchScaleRight(String gameData) {
        if (gameData.charAt(0) == 'L') {

        } else {
            addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));
            addSequential(new DrivetrainFollowPath(righttorightscale));
            addSequential(new DrivetrainConstantPercent(-0.2, 3.0));
            addSequential(new ArmLaunchShort());
        }
    }
}
