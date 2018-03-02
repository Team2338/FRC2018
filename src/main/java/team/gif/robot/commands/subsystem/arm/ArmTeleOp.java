package team.gif.robot.commands.subsystem.arm;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.Globals;
import team.gif.robot.OI;
import team.gif.robot.subsystems.Arm;

public class ArmTeleOp extends Command {

    private Arm arm = Arm.getInstance();

    public ArmTeleOp() {
        requires(arm);
    }

    protected void initialize() {
        arm.tareDartPosition();
    }

    protected void execute() {
        if (OI.getInstance().aux.getBumper(GenericHID.Hand.kRight) && !arm.hasCube()) {
            if (arm.hasCube()) {
                arm.setIntakeSpeed(0.5);
            } else {
                arm.setIntakeSpeed(1.0);
            }
        } else if (OI.getInstance().aux.getBumper(GenericHID.Hand.kLeft)) {
            arm.setIntakeSpeed(-1.0);
        } else {
            arm.setIntakeSpeed(0.0);
        }

        if (arm.hasCube() && arm.getDartEncoderPosition() < Globals.ARM_TRAVEL_POSITION - 300) {
            arm.setDartPosition(Globals.ARM_TRAVEL_POSITION);
        }

        if ((OI.getInstance().aux.getAButton()) && arm.getDartEncoderPosition() < Globals.ARM_OPEN_SOFT_LIMIT) {
            arm.setOpen(true);
        } else {
            arm.setOpen(false);
        }

//        arm.setDart(-OI.getInstance().aux.getY(GenericHID.Hand.kLeft));
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {

    }
}
