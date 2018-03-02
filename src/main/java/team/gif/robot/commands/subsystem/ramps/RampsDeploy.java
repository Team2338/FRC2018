package team.gif.robot.commands.subsystem.ramps;

import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.subsystems.Ramps;

public class RampsDeploy extends Command {

    private Ramps ramps = Ramps.getInstance();

    public RampsDeploy() {
        super(1.0);
        requires(ramps);
    }

    protected void initialize() {
        ramps.deploy(true);
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
        ramps.deploy(false);
    }
}
