package team.gif.robot.commands.subsystem.drivetrain;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.OI;
import team.gif.robot.subsystems.Drivetrain;

public class DrivetrainTeleOp extends Command {

    private Drivetrain drivetrain = Drivetrain.getInstance();

    public DrivetrainTeleOp() {
        requires(drivetrain);
    }

    protected void execute() {
        double inputSpeed = -OI.getInstance().driver.getY(GenericHID.Hand.kLeft);
        double inputRotation = OI.getInstance().driver.getX(GenericHID.Hand.kRight);
        boolean isQuickTurn = OI.getInstance().driver.getRawAxis(3) > 0.1;

        drivetrain.curvatureDrive(inputSpeed, inputRotation, isQuickTurn);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        drivetrain.setLeft(0.0);
        drivetrain.setRight(0.0);
    }
}
