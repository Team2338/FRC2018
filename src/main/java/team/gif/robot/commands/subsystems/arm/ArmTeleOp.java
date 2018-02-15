package team.gif.robot.commands.subsystems.arm;

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

    }

    protected void execute() {
        if (OI.getInstance().aux.getRawAxis(2) > 0.1) {
            arm.setFront(1.0);
        } else if (OI.getInstance().aux.getRawAxis(3) > 0.1) {
            arm.setFront(-1.0);
        } else {
            arm.setFront(0.0);
        }

        if (OI.getInstance().aux.getAButton() && arm.getEncoderPosition() < Globals.ARM_OPEN_SOFT_LIMIT) {
            arm.open();
        } else {
            arm.close();
        }

        OI.getInstance().aux.setRumble(GenericHID.RumbleType.kLeftRumble, 0.1 * arm.getLeftCurrent());
        OI.getInstance().aux.setRumble(GenericHID.RumbleType.kRightRumble, 0.1 * arm.getRightCurrent());

//        arm.setDartPercent(OI.getInstance().aux.getY(GenericHID.Hand.kRight));
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {

    }
}
