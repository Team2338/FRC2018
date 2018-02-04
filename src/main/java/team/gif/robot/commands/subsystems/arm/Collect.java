package team.gif.robot.commands.subsystems.arm;

import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.subsystems.Arm;

public class Collect extends Command {

    public Collect() {
        requires(Arm.getInstance());
    }

    protected void initialize() {

    }

    protected void execute() {
        Arm.getInstance().setFront(0.5);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Arm.getInstance().setFront(0);
    }
}
