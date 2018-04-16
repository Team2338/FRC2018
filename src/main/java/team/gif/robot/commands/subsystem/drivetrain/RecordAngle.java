package team.gif.robot.commands.subsystem.drivetrain;

import edu.wpi.first.wpilibj.command.InstantCommand;
import team.gif.robot.RobotMap;
import team.gif.robot.subsystems.Drivetrain;

public class RecordAngle extends InstantCommand {

    private Drivetrain drivetrain = Drivetrain.getInstance();

    public RecordAngle (double[] angle) {
        try {
            angle[0] = drivetrain.getHeading();
        } catch (NullPointerException e) {

        }
    }
}
