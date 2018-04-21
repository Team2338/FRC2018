package team.gif.robot.commands.subsystem.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import team.gif.robot.Globals;
import team.gif.robot.subsystems.Drivetrain;

public class DriveAtAngle extends Command {

    private Drivetrain drivetrain = Drivetrain.getInstance();
    private double percent;
    private double angle;
    private boolean useExternalTime;
    private double driveTime;

    public DriveAtAngle(double percent, double targetAngle, double seconds, boolean useExtTime) {
        super(seconds);
        requires(drivetrain);
        this.percent = percent;
        angle = targetAngle;
        useExternalTime = useExtTime;
        driveTime = seconds;
    }

    protected void initialize() {
        System.out.println("Init Heading: " + angle);
    }

    protected void execute() {
        double gyroHeading = drivetrain.getHeading();
        double angleDifference = Pathfinder.boundHalfDegrees(angle - gyroHeading);
//        System.out.println("Angle Difference" + angleDifference);
        double turn;

        turn = 1.0 * (-1.0 / 80.0) * angleDifference;

        drivetrain.setLeft(percent + turn);
        drivetrain.setRight(percent - turn);
    }

    protected boolean isFinished() {
        double totalTime = driveTime + (Math.abs(Globals.Drivetrain.collectUntilCollectSpeed/percent)*Globals.driveExtTime);

//        System.out.println("driveTime: " + driveTime + " Add Time:" + (Math.abs(Globals.Drivetrain.collectUntilCollectSpeed/percent)*Globals.driveExtTime));
//        System.out.println("TotalTime: " + totalTime);
//        System.out.println("RunTime: " + timeSinceInitialized() );
        if(useExternalTime) {
            return (timeSinceInitialized() > totalTime) ;
        }
        else {
            return isTimedOut();
        }
    }

    protected void end() {
        drivetrain.setLeft(0.0);
        drivetrain.setRight(0.0);
    }
}
