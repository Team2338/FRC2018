package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import team.gif.robot.Globals;
import team.gif.robot.Robot;
import team.gif.robot.commands.subsystem.arm.*;
import team.gif.robot.commands.subsystem.drivetrain.*;

import java.io.File;

public class ScaleSwitchLeft extends CommandGroup {

    Trajectory LeftToLeftScale = Pathfinder.readFromCSV(new File("/home/lvuser/lefttoleftscale.csv"));
    Trajectory LeftToRightScale = Pathfinder.readFromCSV(new File("/home/lvuser/lefttorightscale.csv"));
    Trajectory LeftScaleToLeftSwitch = Pathfinder.readFromCSV(new File("/home/lvuser/leftscaletoleftswitchfromleft.csv"));
    Trajectory RightScaleToRightSwitch = Pathfinder.readFromCSV(new File("/home/lvuser/rightscaletorightswitchfromleft.csv"));
    Trajectory LeftScaleToSafeArea = Pathfinder.readFromCSV(new File("/home/lvuser/LeftScaleToSafeArea.csv"));
    Trajectory tenfeet = Pathfinder.readFromCSV(new File("/home/lvuser/tenfeet.csv"));

    public ScaleSwitchLeft(String gameData, Robot.AutoSecondary autoSecondaryMode ) {
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
            if( autoSecondaryMode == Robot.AutoSecondary.TRIPLESCALE) {
                // Triple Scale Left ... just go do that
                // if NO_CROSS were selected, it would not come into this function, so set to false
                addSequential(new TripleScaleLeft(gameData, false));
            }
            else {
                // Since Scale is on Left and we're stating on Left, take the shot on the Scale regardless of mode
                // Move to Scale, turn, and shoot
                addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));
                addSequential(new FollowPathForward(LeftToLeftScale));
                addParallel(new ArmLaunchShort());
                addSequential(new WaitCommand(0.5));

                // Go to either the switch or the safe area
                if (autoSecondaryMode == Robot.AutoSecondary.SWITCH ||
                        autoSecondaryMode == Robot.AutoSecondary.DOUBLESWITCH) { // Scale/Switch mode - head toward the switch to pick up cube
                    //  collect the corner cube of the switch
                    addParallel(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));

                    // rotate and pick up corner cube
                    addSequential(new RotateToAngle(-140));
//            addSequential(new DriveAtAngle(0.4, 137, 0.9, false ));
                    addSequential(new DriveAtAngle(0.6, -140, 0.6, false ));
                    addSequential(new CollectUntilCollect());
                    addSequential(new RotateCube(0.65), 0.4);
                    addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
                    addSequential(new DrivetrainConstantPercent(-0.2, 0.5));
                    if (gameData.charAt(0) == 'L') { // Switch is on Left, drop in the cube
                        addSequential(new DrivetrainConstantPercent(0.2, 1));
                        addSequential(new ArmEject(0.5), 1);
                        addSequential(new DrivetrainConstantPercent(-0.2, 1));
                    }
                    // else {} // Switch is on Right, do nothing
                } else if (autoSecondaryMode == Robot.AutoSecondary.SCALE) {
                    addSequential(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
//                addSequential(new FollowPathForward(LeftScaleToLeftSwitch));
                    addSequential(new RotateByAngle(-85));
                    addSequential(new WaitCommand(0.25));
                    addSequential(new DrivetrainConstantPercent(0.4, 0.9));
//
                    addSequential(new CollectUntilCollect());
                    addSequential(new ArmDumbCollect(), 0.75);
                    addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));
                    addSequential(new DrivetrainConstantPercent(-0.3, 1.5));
                    addSequential(new RotateByAngle(85));
//                addSequential(new DrivetrainConstantPercent(-0.2, 0.5));
                    addSequential(new WaitCommand(0.25));
                    addSequential(new ArmLaunchShort());
                } else if (autoSecondaryMode == Robot.AutoSecondary.SAFE) { // go to safe area
                    addSequential(new FollowPathForward(LeftScaleToSafeArea));
                }
            }
        } else { // Right scale
            if (autoSecondaryMode == Robot.AutoSecondary.SWITCH ||
                autoSecondaryMode == Robot.AutoSecondary.DOUBLESWITCH ||
                autoSecondaryMode == Robot.AutoSecondary.SCALE) { // Scale/Switch mode - go to Right scale, shoot, and pickup corner cube regardless of which switch is ours

                double delayStart = SmartDashboard.getNumber("Cross Scale Delay (sec)",0);
                if( delayStart > 0){
                    System.out.println("\n\nDelay start of " + delayStart + " seconds.\n\n");
                }
                addSequential(new WaitCommand( delayStart ));

                // Move to Scale, turn, shoot, pickup corner cube
                addParallel(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));
                addSequential(new FollowPathForward(LeftToRightScale));
                addParallel(new ArmLaunchShort());
                addSequential(new WaitCommand(0.4));
                addSequential(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
//                addSequential(new FollowPathForward(RightScaleToRightSwitch));
                addSequential(new RotateByAngle(100));
                addSequential(new WaitCommand(0.25));
                addSequential(new DrivetrainConstantPercent(0.4, 0.9));
                addSequential(new CollectUntilCollect());
                addSequential(new ArmDumbCollect(), 0.25);
                addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
                addSequential(new DrivetrainConstantPercent(-0.2, 0.5));
                if (gameData.charAt(0) == 'R') { // Switch is on Right, drop in the cube
                    addSequential(new DrivetrainConstantPercent(0.2, 1));
                    addSequential(new ArmEject(0.5), 1);
                    addSequential(new DrivetrainConstantPercent(-0.2, 1));
                }
            } else if ( autoSecondaryMode == Robot.AutoSecondary.SAFE ) { // safe mode - don't go to scale and just do mobility in order to stay out of way of alliance partner.
                addSequential(new FollowPathForward(tenfeet));
            } else if (autoSecondaryMode == Robot.AutoSecondary.TRIPLESCALE){
                addSequential(new TripleScaleLeft(gameData, false));
            }
        }
    }

    protected void end() {
        System.out.println("This auto took " + timeSinceInitialized() + " seconds.");
    }
}
