package team.gif.robot.commands.subsystems.ramp;

import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.subsystems.Ramp;

public class DeployRamp extends Command {

    private Ramp ramp = Ramp.getInstance();
    private boolean deploy;

    public DeployRamp(boolean deploy) {
        requires(Ramp.getInstance());
        this.deploy = deploy;
    }

    protected void initialize() {
        ramp.deployRamps(deploy);
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {

    }
}
