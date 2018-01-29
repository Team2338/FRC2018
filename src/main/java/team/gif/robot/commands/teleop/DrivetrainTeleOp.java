package team.gif.robot.commands.teleop;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team.gif.robot.OI;
import team.gif.robot.subsystems.Drivetrain;

public class DrivetrainTeleOp extends Command {

    public DrivetrainTeleOp() {
        requires(Drivetrain.getInstance());
    }

    protected void initialize() {

    }

    protected void execute() {
        double inputSpeed = OI.getInstance().driver.getY(GenericHID.Hand.kLeft);
        double inputRotation = OI.getInstance().driver.getX(GenericHID.Hand.kRight);
        boolean isQuickTurn = OI.getInstance().driver.getBumper(GenericHID.Hand.kRight);

        OI.getInstance().driver.setRumble(GenericHID.RumbleType.kLeftRumble, Math.abs(inputSpeed + inputRotation));
        OI.getInstance().driver.setRumble(GenericHID.RumbleType.kRightRumble, Math.abs(inputSpeed - inputRotation));

        Drivetrain.getInstance().curvatureDrive(inputSpeed, inputRotation, isQuickTurn);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {

    }
}
