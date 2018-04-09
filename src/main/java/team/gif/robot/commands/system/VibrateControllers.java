package team.gif.robot.commands.system;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import team.gif.robot.OI;

public class VibrateControllers extends Command {

    public VibrateControllers(double duration) {
        super(duration);
    }

    protected void initialize() {
        OI.getInstance().rumble(OI.getInstance().driver, true);
        OI.getInstance().rumble(OI.getInstance().aux, true);
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
        OI.getInstance().rumble(OI.getInstance().driver, false);
        OI.getInstance().rumble(OI.getInstance().aux, false);
    }
}
