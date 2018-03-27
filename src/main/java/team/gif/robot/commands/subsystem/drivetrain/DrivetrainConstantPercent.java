package team.gif.robot.commands.subsystem.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import team.gif.robot.Globals;
import team.gif.robot.Robot;
import team.gif.robot.subsystems.Drivetrain;

public class DrivetrainConstantPercent extends Command {

    private Drivetrain drivetrain = Drivetrain.getInstance();
    private double percent;

    public DrivetrainConstantPercent(double percent, double seconds) {
        super(seconds);
        requires(drivetrain);
        this.percent = percent;
    }

    protected void initialize() {
        drivetrain.resetGyro();
    }

    protected void execute() {
        double gyroHeading = drivetrain.getHeading();
        double angleDifference = Pathfinder.boundHalfDegrees(0 - gyroHeading);
        double turn = 1.0 * (-1.0/80.0) * angleDifference;
//        turn = 0;

        drivetrain.setLeft(percent + turn);
        drivetrain.setRight(percent - turn);
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
        drivetrain.setLeft(0.0);
        drivetrain.setRight(0.0);
    }
}
