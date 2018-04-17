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
    private double angleToCube;
    private double initAngle;
    private boolean isFinished;
    private boolean hasSeenCube;
    private int toleranceCounter;

    public RotateToCube(double scanSpeed) {
        requires(drivetrain);
        this.scanSpeed = scanSpeed;
    }

    protected void initialize() {
        turnPID = new MiniPID(0.03, 0.3, 0.18);
        turnPID.setOutputLimits(0.8);
        turnPID.setMaxIOutput(0.1);
        initAngle = drivetrain.getHeading();
    }

    protected void execute() {
        if (!hasSeenCube) {
            if (limelight.hasValidTarget()) {
                hasSeenCube = true;
                angleToCube = limelight.getXAngle();
            } else {
                drivetrain.setLeft(-scanSpeed);
                drivetrain.setRight(scanSpeed);
            }
        } else {
            double turn = turnPID.getOutput(drivetrain.getHeading() - initAngle, angleToCube);
            drivetrain.setLeft(turn);
            drivetrain.setRight(-turn);

            if (Math.abs(angleToCube - (drivetrain.getHeading() - initAngle)) < 5) {
                toleranceCounter++;
            } else {
                toleranceCounter = 0;
            }
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
