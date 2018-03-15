package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import team.gif.robot.Globals;
import team.gif.robot.commands.subsystem.arm.ArmLaunchLong;
import team.gif.robot.commands.subsystem.arm.ArmLaunchShort;
import team.gif.robot.commands.subsystem.arm.ArmSetPosition;
import team.gif.robot.commands.subsystem.drivetrain.DrivetrainConstantPercent;
import team.gif.robot.commands.subsystem.drivetrain.DrivetrainFollowPath;
import team.gif.robot.subsystems.Drivetrain;

import java.io.File;

public class SwitchScaleLeft extends CommandGroup {

    private Trajectory leftPath = Pathfinder.readFromCSV(new File("/home/lvuser/lefttoleftscale.csv"));
//    private Trajectory rightPath = Pathfinder.readFromCSV(new File("/home/lvuser/something.csv"));

    protected void initialize() {
        Drivetrain.getInstance().resetEncoders();
    }

    public SwitchScaleLeft(String gameData) {
        if (gameData.charAt(0) == 'L') {
            addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));
            addSequential(new DrivetrainFollowPath(leftPath));
            addSequential(new DrivetrainConstantPercent(-0.2, 3.0));
//            addSequential(new DriveUntilCollision(-0.3));
            addSequential(new ArmLaunchShort());
        } else {
//            addSequential(new DrivetrainFollowPath(rightPath));
        }
    }
}
