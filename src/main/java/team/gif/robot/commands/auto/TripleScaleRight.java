package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import team.gif.robot.Globals;
import team.gif.robot.commands.subsystem.arm.ArmDumbCollect;
import team.gif.robot.commands.subsystem.arm.ArmLaunchShort;
import team.gif.robot.commands.subsystem.arm.ArmSetPosition;
import team.gif.robot.commands.subsystem.arm.CollectUntilCollect;
import team.gif.robot.commands.subsystem.drivetrain.*;

import java.io.File;

public class TripleScaleRight extends CommandGroup {

    Trajectory RightToRightScale = Pathfinder.readFromCSV(new File("/home/lvuser/righttorightscale.csv"));
    Trajectory RightToLeftScale = Pathfinder.readFromCSV(new File("/home/lvuser/righttoleftscale.csv"));
    private double[] angle = new double[1];

    public TripleScaleRight(String gameData) {
        
        addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));

        if (gameData.charAt(1) == 'L') {
            addSequential(new FollowPathForward(RightToLeftScale));
        } else {
            addSequential(new FollowPathForward(RightToRightScale));
            addSequential(new ArmLaunchShort());
            addParallel(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
            addSequential(new RotateToAngle(140));
//            addSequential(new RotateToCube(0.5));
//            addSequential(new RecordAngle(angle));
            addSequential(new DriveAtAngle(0.4, 140, 0.9));
            addSequential(new CollectUntilCollect());
            addSequential(new ArmDumbCollect(), 0.25);
            addSequential(new ArmSetPosition(Globals.Arm.ARM_START_POSITION)); // Confirmed Working
            addSequential(new DriveAtAngle(-0.4, 140, 1.3));
            addSequential(new RotateToAngle(45));
            addSequential(new ArmLaunchShort());
            addSequential(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
            addSequential(new RotateToAngle(140));
//            addSequential(new RotateToCube(0.5));
//            addSequential(new RecordAngle(angle));
            addSequential(new DriveAtAngle(0.4, 140, 0.9));
            addSequential(new CollectUntilCollect());
            addSequential(new ArmDumbCollect(), 0.25);
            addSequential(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));
            addSequential(new DriveAtAngle(-0.4, 140, 1.3));
            addSequential(new RotateToAngle(45));
            addSequential(new ArmLaunchShort());
        }
    }

    protected void end(){

    }
}
