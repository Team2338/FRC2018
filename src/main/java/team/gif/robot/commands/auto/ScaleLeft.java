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

public class ScaleLeft extends CommandGroup {

    private Trajectory lefttoleftscale = Pathfinder.readFromCSV(new File("/home/lvuser/lefttoleftscale.csv"));
    private Trajectory lefttorightscale = Pathfinder.readFromCSV(new File("/home/lvuser/lefttorightscale.csv"));

    protected void initialize() {
        Drivetrain.getInstance().resetEncoders();
    }

    public ScaleLeft(String gameData) {
        if (gameData.charAt(1) == 'L') {
            addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));
            addSequential(new FollowPathForward(lefttoleftscale));
            addSequential(new DrivetrainConstantPercent(-0.2, 3.0));
            addSequential(new ArmLaunchShort());
        } else {
            addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));
            addSequential(new FollowPathForward(lefttorightscale));
        }
    }
}
