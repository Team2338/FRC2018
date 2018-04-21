package team.gif.robot.commands.subsystem.arm;

import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.subsystems.Arm;

public class RotateCube extends Command {

    private Arm arm = Arm.getInstance();
    private double direction;

    public RotateCube(double direction) {
        requires(arm);
        this.direction = direction;
    }

    protected void execute() {
        if (direction > 0) {
            arm.setLeftPercent(direction);
            arm.setRightPercent(-direction/2);
        } else {
            arm.setLeftPercent(direction/2);
            arm.setRightPercent(-direction);
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        arm.setIntakePercent(0.0);
    }
}
