package team.gif.robot.commands.subsystems.ramp;

import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.subsystems.Ramp;

public class DeployRamp extends Command {

    public DeployRamp() {
        requires(Ramp.getInstance());
    }

    protected void initialize() {

    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {

    }
}
