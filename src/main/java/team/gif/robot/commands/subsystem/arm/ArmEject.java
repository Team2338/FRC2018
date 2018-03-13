package team.gif.robot.commands.subsystem.arm;

import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.subsystems.Arm;

public class ArmEject extends Command {

    private Arm arm = Arm.getInstance();
    private double speed;

    public ArmEject(double speed) {
        requires(arm);
        this.speed = -speed;
    }

    protected void execute() {
        arm.setIntakePercent(speed);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        arm.setIntakePercent(0.0);
    }
}
