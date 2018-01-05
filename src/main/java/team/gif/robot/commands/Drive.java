package team.gif.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.subsystems.Drivetrain;

public class Drive extends Command {

    public Drive () {
        requires(Drivetrain.getInstance());
    }

    protected void initialize() {

    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {

    }
}
