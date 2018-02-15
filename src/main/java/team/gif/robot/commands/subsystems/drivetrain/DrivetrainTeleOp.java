package team.gif.robot.commands.subsystems.drivetrain;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.OI;
import team.gif.robot.subsystems.Drivetrain;

public class DrivetrainTeleOp extends Command {

    private Drivetrain drivetrain = Drivetrain.getInstance();

    public DrivetrainTeleOp() {
        requires(drivetrain);
    }

    protected void initialize() {

    }

    protected void execute() {
        double inputSpeed = -OI.getInstance().driver.getY(GenericHID.Hand.kLeft);
        double inputRotation = OI.getInstance().driver.getX(GenericHID.Hand.kRight);
        boolean isQuickTurn = OI.getInstance().driver.getBumper(GenericHID.Hand.kRight);

        OI.getInstance().driver.setRumble(GenericHID.RumbleType.kLeftRumble, Math.abs(inputSpeed));
        OI.getInstance().driver.setRumble(GenericHID.RumbleType.kRightRumble, Math.abs(inputSpeed));

        drivetrain.curvatureDrive(inputSpeed, inputRotation, isQuickTurn);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {

    }
}
