package team.gif.robot.commands.subsystem.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import team.gif.robot.subsystems.Drivetrain;

public class DrivetrainRampVoltage extends Command {

    private Drivetrain drivetrain = Drivetrain.getInstance();
    private double percent;

    public DrivetrainRampVoltage(double seconds) {
        super(seconds);
        requires(drivetrain);
    }

    protected void initialize() {
        percent = 0.1;
    }

    protected void execute() {
        double gyroHeading = drivetrain.getHeading();
        double angleDifference = Pathfinder.boundHalfDegrees(0 - gyroHeading);
        double turn = 0.8 * (-1.0/80.0) * angleDifference;

        drivetrain.setLeft(percent + turn);
        drivetrain.setRight(percent - turn);
        percent += 0.1/100;
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
        drivetrain.setLeft(0.0);
        drivetrain.setRight(0.0);
    }
}
