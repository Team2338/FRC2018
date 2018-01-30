package team.gif.robot.commands.subsystems.arm;

import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.subsystems.Arm;

public class Eject extends Command {

    private double speed;

    public Eject(double speed) {
        requires(Arm.getInstance());
        this.speed = speed;
    }

    protected void initialize() {
        Arm.getInstance().setFrontSpeed(speed);
    }

    protected void execute() {
        if (Arm.getInstance().inSpeedTolerance()) {
            Arm.getInstance().setRear(0.5);
        }
    }

    protected boolean isFinished() {
        return !Arm.getInstance().getLimitSwitchStatus();
    }

    protected void end() {
        Arm.getInstance().setFront(0);
        Arm.getInstance().setRear(0);
    }
}
