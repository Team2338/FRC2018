package team.gif.robot.commands.subsystem.arm;

import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.subsystems.Arm;

public class ArmEject extends Command {

    private Arm arm = Arm.getInstance();

    public ArmEject() {
        requires(arm);
    }

    protected void execute() {
        arm.setIntakeSpeed(-1.0);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        arm.setIntakeSpeed(0.0);
    }
}
