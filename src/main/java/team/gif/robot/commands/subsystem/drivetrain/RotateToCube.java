package team.gif.robot.commands.subsystem.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team.gif.lib.Limelight;
import team.gif.lib.MiniPID;
import team.gif.robot.subsystems.Drivetrain;

public class RotateToCube extends Command {

    private Drivetrain drivetrain = Drivetrain.getInstance();
    private Limelight limelight = Limelight.getInstance();
    private MiniPID turnPID;
    private double scanSpeed;
    private boolean isFinished;
    private int toleranceCounter;

    public RotateToCube(double scanSpeed) {
        requires(drivetrain);
        this.scanSpeed = scanSpeed;
    }

    protected void initialize() {
        turnPID = new MiniPID(0.03, 0.3, 0.18);
        turnPID.setOutputLimits(0.8);
        turnPID.setMaxIOutput(0.1);
    }

    protected void execute() {
        if (limelight.hasValidTarget()) {
            double turn = turnPID.getOutput(limelight.getXAngle(), 0);
            drivetrain.setLeft(turn);
            drivetrain.setRight(-turn);

            if (Math.abs(limelight.getXAngle()) < 5) {
                toleranceCounter++;
            } else {
                toleranceCounter = 0;
            }

        } else {
            drivetrain.setLeft(-scanSpeed);
            drivetrain.setRight(scanSpeed);
        }

        if (toleranceCounter > 10) {
            isFinished = true;
        }
    }

    protected boolean isFinished() {
        return isFinished;
    }

    protected void end() {
        drivetrain.setLeft(0.0);
        drivetrain.setRight(0.0);
    }
}
