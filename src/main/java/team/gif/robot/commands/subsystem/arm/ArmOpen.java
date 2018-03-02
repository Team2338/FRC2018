package team.gif.robot.commands.subsystem.arm;

import edu.wpi.first.wpilibj.command.InstantCommand;
import team.gif.robot.subsystems.Arm;

public class ArmOpen extends InstantCommand {

    private Arm arm = Arm.getInstance();

    private boolean open;

    public ArmOpen(boolean open) {
        this.open = open;
    }

    protected void execute() {
        arm.setOpen(open);
    }
}
