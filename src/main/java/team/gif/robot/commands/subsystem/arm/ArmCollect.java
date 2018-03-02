package team.gif.robot.commands.subsystem.arm;

import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.Globals;
import team.gif.robot.subsystems.Arm;

public class ArmCollect extends Command {

    private Arm arm = Arm.getInstance();

    public ArmCollect() {
        requires(arm);
    }

    protected void execute() {
        if (arm.hasCube()) {
            arm.setIntakeSpeed(0.5);
            if (arm.getDartEncoderPosition() < Globals.ARM_TRAVEL_POSITION - 300) {
                arm.setDartPosition(Globals.ARM_TRAVEL_POSITION);
            }
        } else {
            arm.setIntakeSpeed(1.0);
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        arm.setIntakeSpeed(0.0);
    }
}
