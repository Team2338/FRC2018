package team.gif.robot.commands.subsystems.arm;

import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.subsystems.Arm;

public class Open extends Command {

    public Open() {
        requires(Arm.getInstance());
    }

    protected void initialize() {
        Arm.getInstance().open();
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Arm.getInstance().close();
    }
}
