package team.gif.robot.commands.subsystem.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import team.gif.lib.PIDCalculator;
import team.gif.robot.Globals;
import team.gif.robot.subsystems.Drivetrain;

public class RotateDegrees extends Command {

    private Drivetrain drivetrain = Drivetrain.getInstance();
    private PIDCalculator turnPID;
    private double angDeg;
    private double angTarget;
    private boolean isFinished;
    private int tolerableCount;
    private int stuckCount;
    private double lastError = 0;

    public RotateDegrees(double angDeg) {
        requires(drivetrain);
        this.angDeg = angDeg;
        turnPID = new PIDCalculator(Globals.Drivetrain.ROTATE_P, Globals.Drivetrain.ROTATE_I,
                Globals.Drivetrain.ROTATE_D, Globals.Drivetrain.ROTATE_IZONE);
    }

    protected void initialize() {
        angTarget = drivetrain.getHeading() + angDeg;
        int i;
        turnPID.clearIAccum();
    }

    protected void execute() {
        double gyroHeading = drivetrain.getHeading();
        double angleError = Pathfinder.boundHalfDegrees(angTarget - gyroHeading);
        System.out.println("Rotate angle error: " + angleError);
        SmartDashboard.putNumber("Heading Error", angleError);
        double turn = turnPID.getOutput(angleError);

        if (Math.abs(angleError) < 5) {
            tolerableCount++;
        } else {
            tolerableCount = 0;
        }

        if (Math.abs(angleError - lastError) < 0.2) {
            stuckCount++;
        } else {
            stuckCount = 0;
        }

        System.out.println(stuckCount);

        isFinished = tolerableCount > 15 || stuckCount > 15;

        drivetrain.setLeft(-turn);
        drivetrain.setRight(turn);

        lastError = angleError;
    }

    protected boolean isFinished() {
        return isFinished;
    }

    protected void end() {
        drivetrain.setLeft(0.0);
        drivetrain.setRight(0.0);
    }
}
