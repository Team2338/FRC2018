package team.gif.robot.commands.subsystem.arm;

import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.Globals;
import team.gif.robot.subsystems.Arm;

public class ArmDumbCollect extends Command {

    private Arm arm = Arm.getInstance();

    public ArmDumbCollect() {
        requires(arm);
    }

    protected void execute() {
        if (arm.hasCube()) {
            arm.setIntakePercent(0.5);
            if (arm.getDartEncoderPosition() < Globals.Arm.ARM_TRAVEL_POSITION - 300) {
                arm.setDartPosition(Globals.Arm.ARM_TRAVEL_POSITION);
            }
        } else {
            arm.setIntakePercent(1.0);
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        arm.setIntakePercent(0.0);
    }
}
