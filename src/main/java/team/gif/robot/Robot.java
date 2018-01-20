package team.gif.robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import team.gif.robot.commands.auto.*;
import team.gif.robot.subsystems.Drivetrain;

public class Robot extends IterativeRobot {

    private enum StartPosition {
        LEFT, CENTER, RIGHT
    }

    private enum Strategy {
        MOBILITY, SWITCH, NULLZONE, DO_NOTHING
    }

    private StartPosition startPosition = StartPosition.LEFT;
    private Strategy strategy = Strategy.MOBILITY;
    private SendableChooser<StartPosition> startPositionChooser;
    private SendableChooser<Strategy> strategyChooser;
    private Command auto;
    private String gameData;

    private Drivetrain drivetrain = Drivetrain.getInstance();

    @Override
    public void robotInit() {
        startPositionChooser = new SendableChooser();
        strategyChooser = new SendableChooser();

        startPositionChooser.addDefault("Left", StartPosition.LEFT);
        startPositionChooser.addObject("Center", StartPosition.CENTER);
        startPositionChooser.addObject("Right", StartPosition.RIGHT);
        SmartDashboard.putData(startPositionChooser);

        strategyChooser.addDefault("Mobility", Strategy.MOBILITY);
        strategyChooser.addObject("Switch", Strategy.SWITCH);
        strategyChooser.addObject("NullZone", Strategy.NULLZONE);
        SmartDashboard.putData(strategyChooser);

        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);
        Waypoint[] points = new Waypoint[] {
                new Waypoint(-4, -1, Pathfinder.d2r(-45)),
                new Waypoint(-2, -2, 0),
                new Waypoint(0, 0, 0)
        };

        Trajectory trajectory = Pathfinder.generate(points, config);
    }

    public void disabledInit() {

    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        startPosition = startPositionChooser.getSelected();
        strategy = strategyChooser.getSelected();
        gameData = DriverStation.getInstance().getGameSpecificMessage();

        switch(strategy) {
            case MOBILITY:
                switch(startPosition) {
                    case LEFT:
                        auto = new LeftMobility(gameData);
                    case CENTER:
                        auto = new CenterMobility(gameData);
                    case RIGHT:
                        auto = new RightMobility(gameData);
                }
            case SWITCH:
                switch(startPosition) {
                    case LEFT:
                        auto = new LeftSwitch(gameData);
                    case CENTER:
                        auto = new CenterSwitch(gameData);
                    case RIGHT:
                        auto = new RightSwitch(gameData);
                }
            case NULLZONE:
                switch(startPosition) {
                    case LEFT:
                        auto = new LeftNull(gameData);
                    case CENTER:
                        auto = new CenterNull(gameData);
                    case RIGHT:
                        auto = new RightNull(gameData);
                }
            case DO_NOTHING:
                auto = new DoNothing();
        }

        if (auto != null) auto.start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        if (auto != null) auto.cancel();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    public void testInit() {

    }

    public void testPeriodic() {

    }
}