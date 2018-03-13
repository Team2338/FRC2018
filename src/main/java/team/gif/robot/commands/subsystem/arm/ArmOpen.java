package team.gif.robot.commands.subsystem.arm;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import team.gif.robot.Globals;
import team.gif.robot.subsystems.Arm;

public class ArmOpen extends Command {

    private Arm arm = Arm.getInstance();

    protected void execute() {
        if (arm.getDartEncoderPosition() < Globals.Arm.ARM_OPEN_SOFT_LIMIT) {
            arm.setOpen(true);
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        arm.setOpen(false);
    }
}
