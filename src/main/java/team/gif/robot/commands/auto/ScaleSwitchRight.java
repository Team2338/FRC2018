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
import team.gif.robot.commands.subsystem.drivetrain.RotateDegrees;

import java.io.File;

public class ScaleSwitchRight extends CommandGroup {


    Trajectory RightToRightScale = Pathfinder.readFromCSV(new File("/home/lvuser/righttorightscale.csv"));
    Trajectory RightToLeftScale = Pathfinder.readFromCSV(new File("/home/lvuser/righttoleftscale.csv"));
    Trajectory LeftScaleToLeftSwitch = Pathfinder.readFromCSV(new File("/home/lvuser/leftscaletoleftswitchfromright.csv"));
    Trajectory RightScaleToRightSwitch = Pathfinder.readFromCSV(new File("/home/lvuser/rightscaletorightswitchfromright.csv"));
    Trajectory RightScaleToSafeArea = Pathfinder.readFromCSV(new File("/home/lvuser/RightScaleToSafeArea.csv"));
    Trajectory tenfeet = Pathfinder.readFromCSV(new File("/home/lvuser/tenfeet.csv"));

    public ScaleSwitchRight(String gameData, Robot.AutoSecondary autoSecondaryMode) {
        //
        // 2 modes - "Scale/Switch" and "Safe"
        //      Scale/Switch - normal mode for when we want to go to either side of the Scale
        //      Safe -  only go to Scale if Scale is on our side. This allows alliance partner
        //              to go to either side of the scale. Allows for us to be put
        //              in Scale mode and shoot on same side Scale, but do nothing if Scale
        //              is on opposite side

        //
        // Need to add a selection on dashboard to choose safe (to get out of the way
        // for teams like Roboteers) or switch (for most teams)
        //

        if (gameData.charAt(1) == 'R') {  // Right Scale
            // Since Scale is on Right and we're stating on Right, take the shot on the Scale regardless of mode
            // Move to Scale, turn, and shoot
            addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));
            addSequential(new FollowPathForward(RightToRightScale));
            addParallel(new ArmLaunchShort());
            addSequential(new WaitCommand(0.5));

            // Go to either the switch or the safe area
            if( autoSecondaryMode == Robot.AutoSecondary.SWITCH||
                autoSecondaryMode == Robot.AutoSecondary.DOUBLESWITCH) { // Scale/Switch mode - head toward the switch to pick up cube
                //  collect the corner cube of the switch
                addSequential(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
                addSequential(new FollowPathForward(RightScaleToRightSwitch));
                addSequential(new WaitCommand(0.25));
                addSequential(new CollectUntilCollect());
                addSequential(new ArmDumbCollect(), 0.25);
                addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
                addSequential(new DrivetrainConstantPercent(-0.2, 0.5));
                if (gameData.charAt(0) == 'R') { // Switch is on Right, drop in the cube
                    addSequential(new DrivetrainConstantPercent(0.2, 1));
                    addSequential(new ArmEject(0.5), 1);
                    addSequential(new DrivetrainConstantPercent(-0.2, 1));
                }
                // else {} // Switch is on Left, do nothing
            } else if (autoSecondaryMode == Robot.AutoSecondary.SCALE) {
                addSequential(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
//                addSequential(new FollowPathForward(RightScaleToRightSwitch));
                addSequential(new RotateDegrees(85));
                addSequential(new WaitCommand(0.25));
                addSequential(new DrivetrainConstantPercent(0.4, 0.9));
//                addSequential(new DrivetrainConstantPercent(0.3, 1.5));
                addSequential(new CollectUntilCollect());
                addSequential(new ArmDumbCollect(), 0.75);
                addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));
                addSequential(new DrivetrainConstantPercent(-0.3, 1.5));
                addSequential(new RotateDegrees(-85));
//                addSequential(new DrivetrainConstantPercent(-0.2, 0.5));
                addSequential(new WaitCommand(0.25));
                addSequential(new ArmLaunchShort());
            } else if ( autoSecondaryMode == Robot.AutoSecondary.SAFE ) { // go to safe area
                addSequential(new FollowPathForward(RightScaleToSafeArea));
            }
        } else { // Left scale
            if( autoSecondaryMode == Robot.AutoSecondary.SWITCH ||
                autoSecondaryMode == Robot.AutoSecondary.DOUBLESWITCH ||
                autoSecondaryMode == Robot.AutoSecondary.SCALE) { // Scale/Switch mode - go to left scale, shoot, and pickup corner cube regardless of which switch is ours

                // Move to Scale, turn, shoot, pickup corner cube
                addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));
                addSequential(new FollowPathForward(RightToLeftScale));
                addParallel(new ArmLaunchShort());
                addSequential(new WaitCommand(0.5));
                addSequential(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
                addSequential(new FollowPathForward(LeftScaleToLeftSwitch));
                addSequential(new WaitCommand(0.25));
                addSequential(new CollectUntilCollect());
                addSequential(new ArmDumbCollect(), 0.25);
                addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
                addSequential(new DrivetrainConstantPercent(-0.2, 0.5));
                if (gameData.charAt(0) == 'L') { // Switch is on Left, drop in the cube
                    addSequential(new DrivetrainConstantPercent(0.2, 1));
                    addSequential(new ArmEject(0.5), 1);
                    addSequential(new DrivetrainConstantPercent(-0.2, 1));
                }
            } else if ( autoSecondaryMode == Robot.AutoSecondary.SAFE ) { // safe mode - don't go to scale and just do mobility in order to stay out of way of alliance partner.
                addSequential(new FollowPathForward(tenfeet));
            }
        }
    }

    protected void end() {
        System.out.println("This auto took " + timeSinceInitialized() + " seconds.");
    }
}