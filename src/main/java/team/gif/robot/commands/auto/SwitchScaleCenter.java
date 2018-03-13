package team.gif.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import team.gif.robot.Globals;
import team.gif.robot.commands.subsystem.arm.ArmEject;
import team.gif.robot.commands.subsystem.drivetrain.DrivetrainFollowPath;
import team.gif.robot.commands.subsystem.drivetrain.DrivetrainFollowPathReverse;

import java.io.File;

public class SwitchScaleCenter extends CommandGroup {

    private Trajectory centertoleftswitch = Pathfinder.readFromCSV(new File("/home/lvuser/centertoleftswitch.csv"));
    private Trajectory centertorightswitch = Pathfinder.readFromCSV(new File("/home/lvuser/centertorightswitch.csv"));
    private Trajectory leftswitchtoleftscale = Pathfinder.readFromCSV(new File("/home/lvuser/leftswitchtoleftscale.csv"));
    private Trajectory leftswitchtorightscale = Pathfinder.readFromCSV(new File("/home/lvuser/leftswitchtorightscale.csv"));
    private Trajectory rightswitchtoleftscale = Pathfinder.readFromCSV(new File("/home/lvuser/rightswitchtoleftscale.csv"));
    private Trajectory rightswitchtorightscale = Pathfinder.readFromCSV(new File("/home/lvuser/rightswitchtorightscale.csv"));

    public SwitchScaleCenter(String gameData) {
        if (gameData.charAt(0) == 'L') {
            addSequential(new DrivetrainFollowPath(centertoleftswitch));
            addSequential(new ArmEject(0.75));
            if (gameData.charAt(1) == 'L') {
                addSequential(new DrivetrainFollowPathReverse(leftswitchtoleftscale));
            } else {
                addSequential(new DrivetrainFollowPathReverse(leftswitchtorightscale));
            }
        } else {
            addSequential(new DrivetrainFollowPath(centertorightswitch));
            addSequential(new ArmEject(0.75));
            if (gameData.charAt(1) == 'L') {
                addSequential(new DrivetrainFollowPathReverse(rightswitchtoleftscale));
            } else {
                addSequential(new DrivetrainFollowPathReverse(rightswitchtorightscale));
            }
        }
    }
}
