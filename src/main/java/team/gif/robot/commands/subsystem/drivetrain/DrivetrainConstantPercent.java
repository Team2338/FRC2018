package team.gif.robot.commands.subsystem.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
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
        drivetrain.setLeft(percent);
        drivetrain.setRight(percent);
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
        drivetrain.setLeft(0.0);
        drivetrain.setRight(0.0);
    }
}
