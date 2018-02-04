package team.gif.robot.commands.subsystems.arm;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import team.gif.robot.subsystems.Arm;

public class Open extends InstantCommand {

    public Open() {
        requires(Arm.getInstance());
    }

    protected void initialize() {
        Arm.getInstance().open();
    }

    protected void end() {
        Arm.getInstance().close();
    }
}
