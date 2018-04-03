package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import team.gif.robot.Globals;
import team.gif.robot.commands.subsystem.arm.ArmEject;
import team.gif.robot.commands.subsystem.arm.ArmSetPosition;
import team.gif.robot.commands.subsystem.drivetrain.DrivetrainConstantPercent;
import team.gif.robot.commands.subsystem.drivetrain.FollowPathForward;

import java.io.File;

public class SwitchRight extends CommandGroup {

    Trajectory righttorightswitch = Pathfinder.readFromCSV(new File("/home/lvuser/righttorightswitch.csv"));
    Trajectory tenfeet = Pathfinder.readFromCSV(new File("/home/lvuser/tenfeet.csv"));

    public SwitchRight(String gameData) {
        if (gameData.charAt(0) == 'R') {
            addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
            addSequential(new FollowPathForward(righttorightswitch));
            addSequential(new ArmEject(0.75), 0.5);
            addSequential(new DrivetrainConstantPercent(-0.3, 0.5));

            // TODO: turn and come back. Maybe pick up cube.

        } else { // 'L' Left switch selected. Stay out fo way and just do mobility.
            addSequential(new FollowPathForward(tenfeet));

            // TODO: drive backwards and reposition. Maybe pickup cube.

            // removed wrap around switch to avoid collision
            // addParallel(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
            // addSequential(new FollowPathForward(righttoleftswitch));
            // addSequential(new ArmEject(0.75), 0.5);
        }
    }
}
