package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import team.gif.robot.Globals;
import team.gif.robot.commands.subsystem.arm.ArmLaunchShort;
import team.gif.robot.commands.subsystem.arm.ArmSetPosition;
import team.gif.robot.commands.subsystem.drivetrain.DrivetrainConstantPercent;
import team.gif.robot.commands.subsystem.drivetrain.FollowPathForward;
import team.gif.robot.subsystems.Drivetrain;

import java.io.File;

public class ScaleRight extends CommandGroup {

    private Trajectory righttorightscale = Pathfinder.readFromCSV(new File("/home/lvuser/righttorightscale.csv"));
    private Trajectory righttoleftscale = Pathfinder.readFromCSV(new File("/home/lvuser/righttoleftscale.csv"));

    protected void initialize() {
        Drivetrain.getInstance().resetEncoders();
    }

    public ScaleRight(String gameData) {
        if (gameData.charAt(1) == 'L') {
            addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));
            addSequential(new FollowPathForward(righttoleftscale));
        } else {
            addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));
            addSequential(new FollowPathForward(righttorightscale));
            addSequential(new DrivetrainConstantPercent(-0.2, 3.0));
            addSequential(new ArmLaunchShort());
        }
    }
}
