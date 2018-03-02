package team.gif.robot.commands.subsystem.arm;

import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.subsystems.Arm;

public class ArmLaunch extends Command {

    private Arm arm = Arm.getInstance();

    public ArmLaunch() {
        super(0.5);
        requires(arm);
    }

    protected void initialize() {

    }

    protected void execute() {
        arm.setIntakeSpeed(-1.0);
        arm.setPunch(true);
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
        arm.setIntakeSpeed(0);
        arm.setPunch(false);
    }
}
