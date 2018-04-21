package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import team.gif.robot.Globals;
import team.gif.robot.commands.subsystem.arm.*;
import team.gif.robot.commands.subsystem.drivetrain.*;
import team.gif.robot.subsystems.Arm;

import java.io.File;

public class TripleScaleRight extends CommandGroup {

    Trajectory RightToRightScale = Pathfinder.readFromCSV(new File("/home/lvuser/righttorightscale.csv"));
    Trajectory RightToLeftScale = Pathfinder.readFromCSV(new File("/home/lvuser/righttoleftscale.csv"));
    private double[] angle = new double[1];

    public TripleScaleRight(String gameData) {
        
//        addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));

        if (gameData.charAt(1) == 'L') {
            addSequential(new FollowPathForward(RightToLeftScale));
            addParallel(new ArmLaunchShort());
            addSequential(new WaitCommand(0.2));
            addParallel(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
            addSequential(new RotateToAngle(-142));
            addSequential(new DriveAtAngle(0.4, -142, 0.9, false ));
            addSequential(new CollectUntilCollect());
            addSequential(new RotateCube(0.5), 0.5);
//            addSequential(new ArmDumbCollect(), 0.25);
            addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION)); // Confirmed Working
            addSequential(new DriveAtAngle(-0.4, -142 , 1.2, false));
            addSequential(new RotateToAngle(-60));
            addParallel(new ArmLaunchShort());
            addSequential(new WaitCommand(0.2));
//            addSequential(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
//            addSequential(new RotateToAngle(-133));
//            addSequential(new DriveAtAngle(0.4, -133, 1.3, false));
//            addSequential(new CollectUntilCollect());
////            addSequential(new RotateCube(-0.5), 0.5);
//            addSequential(new RotateCube(1.0), 0.5);
//            addSequential(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));
//            addSequential(new DriveAtAngle(-0.4, -130, 1.3, false));
//            addSequential(new RotateToAngle(-60));
//            addSequential(new ArmLaunchShort());
        } else {
            addSequential(new FollowPathForward(RightToRightScale));
            addParallel(new ArmLaunchLong());
            addSequential(new WaitCommand(0.2));
            addParallel(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
            addSequential(new RotateToAngle(140));
//            addSequential(new DriveAtAngle(0.4, 137, 0.9, false ));
            addSequential(new DriveAtAngle(0.6, 140, 0.6, false ));
            addSequential(new CollectUntilCollect());
            addSequential(new RotateCube(-0.65), 0.4);
//            addSequential(new ArmDumbCollect(), 0.25);
            addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION)); // Confirmed Working
//            addSequential(new DriveAtAngle(-0.4, 137 , 1.2, false));
            addSequential(new DriveAtAngle(-0.6, 140 , 0.65, false));
            addSequential(new RotateToAngle(60));
            addParallel(new ArmLaunchShort());
            addSequential(new WaitCommand(0.2));
            addSequential(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
            addSequential(new RotateToAngle(130));
//            addSequential(new DriveAtAngle(0.4, 128, 1.3, false));
            addSequential(new DriveAtAngle(0.6, 130, 0.8, false));
            addSequential(new CollectUntilCollect());
//            addSequential(new RotateCube(-0.5), 0.5);
            addSequential(new ArmDumbCollect(), 0.3);
            addSequential(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));
//            addSequential(new DriveAtAngle(-0.4, 124, 1.5, false));
            addSequential(new DriveAtAngle(-0.6, 130, 1.1, false));
            addSequential(new RotateToAngle(60));
            addParallel(new ArmLaunchShort());
            addSequential(new WaitCommand(0.12));


// test code
/*            addSequential(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
            addSequential(new DriveAtAngle(0.4, 0, 2, false ));
            addSequential(new CollectUntilCollect());
            addSequential(new ArmDumbCollect(), 0.25);
            addSequential(new ArmSetPosition(Globals.Arm.ARM_START_POSITION)); // Confirmed Working
            addSequential(new DriveAtAngle(-0.4, 0, 2, true)); */

        }
    }

    protected void initialize() {
        Arm.getInstance().setCompressor(false);
    }

    protected void end(){
        Arm.getInstance().setCompressor(true);
        System.out.println("This auto took " + timeSinceInitialized() + " seconds.");
    }
}
