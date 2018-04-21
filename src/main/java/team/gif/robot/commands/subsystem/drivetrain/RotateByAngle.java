package team.gif.robot.commands.subsystem.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import team.gif.lib.MiniPID;
import team.gif.robot.subsystems.Drivetrain;

public class RotateByAngle extends Command {

    private Drivetrain drivetrain = Drivetrain.getInstance();
    private MiniPID turnPID;
    private double angDeg;
    private double angTarget;
    private int tolerableCount;
    private boolean isFinished;

    public RotateByAngle(double angDeg) {
        requires(drivetrain);
        this.angDeg = angDeg;
    }

    protected void initialize() {
        turnPID = new MiniPID(0.03, 0.3, 0.18);
        turnPID.setOutputLimits(0.8);
        turnPID.setMaxIOutput(0.1);
//        turnPID.setSetpointRange(30);
//        turnPID.setOutputRampRate(0.02);
        angTarget = drivetrain.getHeading() + angDeg;
    }

    protected void execute() {
        double gyroHeading = drivetrain.getHeading();
        double angleError = angTarget - gyroHeading;
        double turn = turnPID.getOutput(gyroHeading, angTarget);

        if (Math.abs(angleError) < 5) {
            tolerableCount++;
        } else {
            tolerableCount = 0;
        }

//        if (Math.abs(angleError - lastError) < 0.2) {
//            stuckCount++;
//        } else {
//            stuckCount = 0;
//        }
//
//        System.out.println(stuckCount);

        isFinished = tolerableCount > 15;

        drivetrain.setLeft(-turn);
        drivetrain.setRight(turn);

//        lastError = angleError;
    }

    protected boolean isFinished() {
        return isFinished;
//        return false;
    }

    protected void end() {
        drivetrain.setLeft(0.0);
        drivetrain.setRight(0.0);
    }
}
