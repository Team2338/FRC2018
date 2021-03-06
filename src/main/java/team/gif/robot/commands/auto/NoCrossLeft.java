package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import team.gif.robot.Globals;
import team.gif.robot.Robot;
import team.gif.robot.commands.subsystem.arm.*;
import team.gif.robot.commands.subsystem.drivetrain.DrivetrainConstantPercent;
import team.gif.robot.commands.subsystem.drivetrain.FollowPathForward;
import team.gif.robot.commands.subsystem.drivetrain.RotateByAngle;

import java.io.File;

public class NoCrossLeft extends CommandGroup {

    Trajectory LeftToLeftScale = Pathfinder.readFromCSV(new File("/home/lvuser/lefttoleftscale.csv"));
    Trajectory LeftScaleToLeftSwitch = Pathfinder.readFromCSV(new File("/home/lvuser/leftscaletoleftswitchfromleft.csv"));

    public NoCrossLeft(String gameData, Robot.AutoSecondary autoSecondaryMode ) {
        //
        // 2 modes - "Scale/Switch" and "Safe"
        //      Scale/Switch - normal mode for when we want to go to either side of the Scale
        //      Safe -  only go to Scale if Scale is on our side. This allows alliance partner
        //              to go to either side of the scale. Allows for us to be put
        //              in Scale mode and shoot on same side Scale, but do nothing if Scale
        //              is on opposite side

        //
        // Added a selection on dashboard to choose safe (to get out of the way
        // for teams like Roboteers) or switch (for most teams). Needs testing.
        //

        if (gameData.charAt(1) == 'L') {  // Left Scale
            if( autoSecondaryMode == Robot.AutoSecondary.TRIPLESCALE ) {
                // Triple Scale Right ... just go do that
                // since NO_CROSS was selected, set it to true so it only does right side
                addSequential(new TripleScaleLeft(gameData, true));
            } else {
                // Since Scale is on Left and we're stating on Left, take the shot on the Scale regardless of mode
                // Move to Scale, turn, and shoot
                addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));
                addSequential(new FollowPathForward(LeftToLeftScale));
                addParallel(new ArmLaunchShort());
                addSequential(new WaitCommand(0.5));

                // Go to either the switch or scale some more
                if ((autoSecondaryMode == Robot.AutoSecondary.SWITCH ||
                        autoSecondaryMode == Robot.AutoSecondary.DOUBLESWITCH) && gameData.charAt(0) == 'L') { // Scale/Switch mode - head toward the switch to pick up cube
                    //  collect the corner cube of the switch
                    addSequential(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
                    addSequential(new FollowPathForward(LeftScaleToLeftSwitch));
                    addSequential(new WaitCommand(0.25));
                    addSequential(new CollectUntilCollect());
                    addSequential(new ArmDumbCollect(), 0.25);
                    addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
                    addSequential(new DrivetrainConstantPercent(-0.2, 0.5));
                    addSequential(new DrivetrainConstantPercent(0.2, 1));
                    addSequential(new ArmEject(0.5), 1);
                    addSequential(new DrivetrainConstantPercent(-0.2, 1));
                    // else {} // Switch is on Right, do nothing
                } else {
                    addSequential(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
                    addSequential(new FollowPathForward(LeftScaleToLeftSwitch));
                    addSequential(new WaitCommand(0.25));
                    addSequential(new CollectUntilCollect());
                    addSequential(new ArmDumbCollect(), 0.75);
                    addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));
                    addSequential(new DrivetrainConstantPercent(-0.3, 1.5));
                    addSequential(new RotateByAngle(100));
                    addSequential(new DrivetrainConstantPercent(-0.2, 0.5));
                    addSequential(new WaitCommand(0.25));
                    addSequential(new ArmLaunchShort());
                }
            }
        } else { // Right scale so go to left switch
            System.out.println("No Cross from Left: Scale is on Right so switching to SwitchLeft path");
            addSequential(new SwitchLeft(gameData));
        }
    }

    protected void end() {
        System.out.println("This auto took " + timeSinceInitialized() + " seconds.");
    }
}