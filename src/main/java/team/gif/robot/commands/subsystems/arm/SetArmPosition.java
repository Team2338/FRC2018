package team.gif.robot.commands.subsystems.arm;

import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.subsystems.Arm;

public class SetArmPosition extends Command {

    private int position;

    public SetArmPosition(int position) {
        this.position = position;
        requires(Arm.getInstance());
    }

    protected void initialize() {
        Arm.getInstance().setDartPosition(position);
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {

    }
}
