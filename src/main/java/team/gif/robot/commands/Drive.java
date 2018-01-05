package team.gif.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.OI;
import team.gif.robot.subsystems.Drivetrain;

public class Drive extends Command {

    public Drive () {
        requires(Drivetrain.getInstance());
    }

    protected void initialize() {

    }

    protected void execute() {
        double inputSpeed = OI.getInstance().driver.getY(GenericHID.Hand.kLeft);
        double inputRotation = OI.getInstance().driver.getX(GenericHID.Hand.kRight);
        boolean isQuickTurn = OI.getInstance().driver.getBumper(GenericHID.Hand.kRight);

        Drivetrain.getInstance().curvatureDrive(inputSpeed, inputRotation, isQuickTurn);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {

    }
}
