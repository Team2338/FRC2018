package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import team.gif.robot.Globals;
import team.gif.robot.commands.subsystem.arm.*;
import team.gif.robot.commands.subsystem.drivetrain.*;

import java.io.File;

public class TripleScaleRight extends CommandGroup {

    Trajectory RightToRightScale = Pathfinder.readFromCSV(new File("/home/lvuser/righttorightscale.csv"));
    Trajectory RightToLeftScale = Pathfinder.readFromCSV(new File("/home/lvuser/righttoleftscale.csv"));
    private double[] angle = new double[1];

    public TripleScaleRight(String gameData) {
        
//        addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));

        if (gameData.charAt(1) == 'L') {
            addSequential(new FollowPathForward(RightToLeftScale));
        } else {
            addSequential(new FollowPathForward(RightToRightScale));
            addSequential(new ArmLaunchShort());
            addParallel(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
            addSequential(new RotateToAngle(140 ));
            addSequential(new DriveAtAngle(0.4, 140 , 0.9, false ));
            addSequential(new CollectUntilCollect());
            addSequential(new ArmDumbCollect(), 0.25);
            addSequential(new ArmSetPosition(Globals.Arm.ARM_START_POSITION)); // Confirmed Working
            addSequential(new DriveAtAngle(-0.4, 140, 1.3, false));
            addSequential(new RotateToAngle(50));
            addSequential(new ArmLaunchShort());
            addSequential(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
            addSequential(new RotateToAngle(128));
            addSequential(new DriveAtAngle(0.4, 128, 1.3, false));
            addSequential(new CollectUntilCollect());
            addSequential(new ArmDumbCollect(), 0.3);
            addSequential(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));
            addSequential(new DriveAtAngle(-0.4, 128, 1.5, false));
            addSequential(new RotateToAngle(70));
            addSequential(new ArmLaunchShort());

// test code
/*            addSequential(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
            addSequential(new DriveAtAngle(0.4, 0, 2, false ));
            addSequential(new CollectUntilCollect());
            addSequential(new ArmDumbCollect(), 0.25);
            addSequential(new ArmSetPosition(Globals.Arm.ARM_START_POSITION)); // Confirmed Working
            addSequential(new DriveAtAngle(-0.4, 0, 2, true)); */

        }
    }

    protected void end(){
        System.out.println("This auto took " + timeSinceInitialized() + " seconds.");
    }
}
