package team.gif.robot.commands.subsystem.arm;

import edu.wpi.first.wpilibj.command.InstantCommand;
import team.gif.robot.Globals;
import team.gif.robot.subsystems.Arm;

public class TareDartPosition extends InstantCommand {

    private Arm arm = Arm.getInstance();

    protected void execute() {
        arm.tareDartPosition();
    }
}
