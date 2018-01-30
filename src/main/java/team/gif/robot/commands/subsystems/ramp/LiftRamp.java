package team.gif.robot.commands.subsystems.ramp;

import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.subsystems.Ramp;

public class LiftRamp extends Command {

    public LiftRamp() {
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
