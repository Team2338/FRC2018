package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import team.gif.robot.Globals;
import team.gif.robot.Robot;
import team.gif.robot.commands.subsystem.arm.*;
import team.gif.robot.commands.subsystem.drivetrain.*;

import java.io.File;

public class SwitchCenter extends CommandGroup{
// Start at center and go either "L" or "R" based on the FMS Switch ownership (first character)

    Trajectory centertoleftswitch = Pathfinder.readFromCSV(new File("/home/lvuser/centertoleftswitch.csv"));
    Trajectory centertorightswitch = Pathfinder.readFromCSV(new File("/home/lvuser/centertorightswitch.csv"));
    Trajectory leftsideswitchwalltocenter = Pathfinder.readFromCSV(new File("/home/lvuser/leftsideswitchwalltocenter.csv"));
    Trajectory rightsideswitchwalltocenter = Pathfinder.readFromCSV(new File("/home/lvuser/rightsideswitchwalltocenter.csv"));

    public SwitchCenter(String gameData, Robot.AutoSecondary autoSecondaryMode) {
        if (gameData.charAt(0) == 'L') {
            addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
            addSequential(new FollowPathForward(centertoleftswitch));
            addSequential(new ArmEject(0.75), 0.5);


            // place second cube if selected
            if (autoSecondaryMode == Robot.AutoSecondary.DOUBLESWITCH) {
                addSequential(new DrivetrainConstantPercent(-0.3, 2.5));

                // collect head cube from stack
                addParallel(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
                addSequential(new FollowPathForward(leftsideswitchwalltocenter));
                addSequential(new WaitCommand(0.25));
                addSequential(new CollectUntilCollect());
                addSequential(new ArmDumbCollect(), 0.5);
                addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
                addSequential(new DrivetrainConstantPercent(-0.3, 1.9));
                addSequential(new WaitCommand(0.25));
                //                addSequential(new FollowPathForward(SecondaryRightSwitch));
                addSequential(new RotateByAngle(40));
                addSequential(new DrivetrainConstantPercent(0.5, 1.2));
                addSequential(new ArmEject(0.5), 0.5);
                addSequential(new WaitCommand(0.25));
                addSequential(new DrivetrainConstantPercent(-0.3, 2.5));
            } else if (autoSecondaryMode == Robot.AutoSecondary.TRIPLESWITCH) {
                addSequential(new DriveAtAngle(-0.6, 0, 0.75, false));
                addParallel(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
                addSequential(new RotateToAngle(-45));
                addSequential(new DriveAtAngle(0.6, -45, 0.3, false));
                addSequential(new CollectUntilCollect());
//                addSequential(new RotateCube(0.5), 0.5);
                addSequential(new ArmDumbCollect(), 0.25);
                addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
                addSequential(new DriveAtAngle(-0.6, -45, 0.55, false));
                addSequential(new RotateToAngle(0));
                addSequential(new DriveAtAngle(0.6, 0, 0.78, false));
                addSequential(new WaitCommand(0.25));
                addSequential(new ArmEject(0.75), 0.25);
                addParallel(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
                addSequential(new DriveAtAngle(-0.6, 0, 0.625, false));
                addSequential(new RotateToAngle(-45));
                addSequential(new DriveAtAngle(0.6, -45, 0.25, false));
                addSequential(new CollectUntilCollect());
//                addSequential(new RotateCube(0.5), 0.5);
                addSequential(new ArmDumbCollect(), 0.25);
                addSequential(new DriveAtAngle(-0.6, -45, 0.5, false));
                addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
                addSequential(new RotateToAngle(0));
                addSequential(new DriveAtAngle(0.6, 0, 0.625, false));
                addSequential(new WaitCommand(0.25));
                addSequential(new ArmEject(0.5), 0.25);
            }

        } else { // 'R'
            addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
            addSequential(new FollowPathForward(centertorightswitch));
            addSequential(new ArmEject(0.75), 0.5);

            // place second cube if selected
            if (autoSecondaryMode == Robot.AutoSecondary.DOUBLESWITCH) {
                addSequential(new DrivetrainConstantPercent(-0.325, 2.0));

                // collect head cube from stack
                addParallel(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
                addSequential(new FollowPathForward(rightsideswitchwalltocenter));
                addSequential(new WaitCommand(0.25));
                addSequential(new CollectUntilCollect());
                addSequential(new ArmDumbCollect(), 0.5);
                addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
                addSequential(new DrivetrainConstantPercent(-0.3, 1.9));
                addSequential(new WaitCommand(0.25));
                //                addSequential(new FollowPathForward(SecondaryRightSwitch));
                addSequential(new RotateByAngle(-40));
                addSequential(new DrivetrainConstantPercent(0.5, 1.2));
                addSequential(new ArmEject(0.5), 0.5);
                addSequential(new WaitCommand(0.25));
                addSequential(new DrivetrainConstantPercent(-0.3, 2.5));
            } else if (autoSecondaryMode == Robot.AutoSecondary.TRIPLESWITCH) {
                addSequential(new DriveAtAngle(-0.6, 0, 0.75, false));
                addParallel(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
                addSequential(new RotateToAngle(45));
                addSequential(new DriveAtAngle(0.6, 45, 0.3, false));
                addSequential(new CollectUntilCollect());
//                addSequential(new RotateCube(0.5), 0.5);
                addSequential(new ArmDumbCollect(), 0.25);
                addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
                addSequential(new DriveAtAngle(-0.6, 45, 0.55, false));
                addSequential(new RotateToAngle(0));
                addSequential(new DriveAtAngle(0.6, 0, 0.78, false));
                addSequential(new WaitCommand(0.25));
                addSequential(new ArmEject(0.75), 0.25);
                addParallel(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
                addSequential(new DriveAtAngle(-0.6, 0, 0.625, false));
                addSequential(new RotateToAngle(45));
                addSequential(new DriveAtAngle(0.6, 45, 0.25, false));
                addSequential(new CollectUntilCollect());
//                addSequential(new RotateCube(0.5), 0.5);
                addSequential(new ArmDumbCollect(), 0.25);
                addSequential(new DriveAtAngle(-0.6, 45, 0.5, false));
                addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
                addSequential(new RotateToAngle(0));
                addSequential(new DriveAtAngle(0.6, 0, 0.625, false));
                addSequential(new WaitCommand(0.25));
                addSequential(new ArmEject(0.5), 0.25);
            }

        }
    }

    protected void end() {
        System.out.println("This auto took " + timeSinceInitialized() + " seconds.");
    }
}
