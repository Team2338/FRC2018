package team.gif.robot.commands.subsystem.arm;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import team.gif.robot.Globals;
import team.gif.robot.commands.subsystem.drivetrain.DrivetrainConstantPercent;
import team.gif.robot.commands.subsystem.drivetrain.FollowPathForward;
import team.gif.robot.commands.subsystem.drivetrain.FollowPathReverse;
import team.gif.robot.subsystems.Arm;
import team.gif.robot.subsystems.Drivetrain;

import java.io.File;

public class CollectUntilCollect extends Command {

    private Arm arm = Arm.getInstance();
    private Drivetrain drivetrain = Drivetrain.getInstance();
    private double initAngle;

    public CollectUntilCollect() {
        requires(arm);
        Globals.driveExtTime = 0;
    }

    protected void initialize() {
//        drivetrain.resetGyro();
//        arm.setOpen(true);
        initAngle = drivetrain.getHeading();
    }

    protected void execute() {
        if (arm.hasCube()) {
            arm.setIntakePercent(0.5);
            if (arm.getDartEncoderPosition() < Globals.Arm.ARM_TRAVEL_POSITION - 300) {
                arm.setDartPosition(Globals.Arm.ARM_TRAVEL_POSITION);
            }
        } else {
            arm.setIntakePercent(1.0);
        }

        double gyroHeading = drivetrain.getHeading();
        double angleDifference = Pathfinder.boundHalfDegrees(initAngle - gyroHeading);
        double turn = 1.5 * (-1.0/80.0) * angleDifference;

        drivetrain.setLeft(Globals.Drivetrain.collectUntilCollectSpeed + turn);
        drivetrain.setRight(Globals.Drivetrain.collectUntilCollectSpeed - turn);
    }

    protected boolean isFinished() {
        return arm.hasCube();
    }

    protected void end() {
        arm.setIntakePercent(0.0);
        drivetrain.setLeft(0.0);
        drivetrain.setRight(0.0);
        arm.setOpen(false);
        Globals.driveExtTime = timeSinceInitialized();
//        System.out.println("Drive Time: " + Globals.driveExtTime);
    }
}
