package team.gif.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team.gif.robot.commands.system.DoNothing;
import team.gif.robot.commands.auto.*;
import team.gif.robot.subsystems.Arm;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.Ramps;

public class Robot extends IterativeRobot {

    private enum StartPosition {
        LEFT, CENTER, RIGHT
    }

    private enum Strategy {
        MOBILITY, SWITCH, NULL_ZONE, DO_NOTHING
    }

    private SendableChooser<StartPosition> startPositionChooser;
    private SendableChooser<Strategy> strategyChooser;
    private Command auto;
    private String gameData;

    private Drivetrain drivetrain = Drivetrain.getInstance();
    private Arm arm = Arm.getInstance();
    private Ramps ramps = Ramps.getInstance();

    @Override
    public void robotInit() {
        init();

        startPositionChooser = new SendableChooser<>();
        strategyChooser = new SendableChooser<>();

        startPositionChooser.addDefault("Left", StartPosition.LEFT);
        startPositionChooser.addObject("Center", StartPosition.CENTER);
        startPositionChooser.addObject("Right", StartPosition.RIGHT);

        strategyChooser.addDefault("Mobility", Strategy.MOBILITY);
        strategyChooser.addObject("Switch", Strategy.SWITCH);
        strategyChooser.addObject("Null Zone", Strategy.NULL_ZONE);
        strategyChooser.addObject("Do Nothing", Strategy.DO_NOTHING);

        SmartDashboard.putData("Strategy", strategyChooser);
        SmartDashboard.putData("Start Position", startPositionChooser);
    }

    public void disabledInit() {
        init();
    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
        update();
    }

    public void autonomousInit() {
        init();
        StartPosition startPosition = startPositionChooser.getSelected();
        Strategy strategy = strategyChooser.getSelected();
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        boolean closeLeft = gameData.charAt(0) == 'L';
        boolean midLeft = gameData.charAt(1) == 'L';
        boolean farLeft = gameData.charAt(2) == 'L';
        SmartDashboard.putBoolean("Close Left", closeLeft);
        SmartDashboard.putBoolean("Close Right", !closeLeft);
        SmartDashboard.putBoolean("Mid Left", midLeft);
        SmartDashboard.putBoolean("Mid Right", !midLeft);
        SmartDashboard.putBoolean("Far Left", farLeft);
        SmartDashboard.putBoolean("Far Right", !farLeft);

        if (strategy == Strategy.MOBILITY) {
            if (startPosition == StartPosition.LEFT) {
                auto = new LeftMobility(gameData);
            } else if (startPosition == StartPosition.CENTER) {
                auto = new CenterMobility(gameData);
            } else if (startPosition == StartPosition.CENTER){
                auto = new RightMobility(gameData);
            }
        } else if (strategy == Strategy.SWITCH) {
            if (startPosition == StartPosition.LEFT) {
                auto = new LeftSwitch(gameData);
            } else if (startPosition == StartPosition.CENTER) {
                auto = new CenterSwitch(gameData);
            } else {
                auto = new RightSwitch(gameData);
            }
        } else if (strategy == Strategy.NULL_ZONE) {
            if (startPosition == StartPosition.LEFT) {
                auto = new LeftNull(gameData);
            } else if (startPosition == StartPosition.CENTER) {
                auto = new CenterNull(gameData);
            } else {
                auto = new RightNull(gameData);
            }
        } else if (strategy == Strategy.DO_NOTHING){
            auto = new DoNothing();
        }

        if (gameData == null) { auto = new DoNothing(); }

        if (auto != null) auto.start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        update();
    }

    public void teleopInit() {
        init();
        if (auto != null) auto.cancel();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        update();

        if (getMatchTime() <= 30.0 && getMatchTime() > 29.5) {
            OI.getInstance().rumble(OI.getInstance().driver, true);
            OI.getInstance().rumble(OI.getInstance().aux, true);
        } else if (getMatchTime() <= 29.0 && getMatchTime() > 28.5) {
            OI.getInstance().rumble(OI.getInstance().driver, true);
            OI.getInstance().rumble(OI.getInstance().aux, true);
        } else {
            OI.getInstance().rumble(OI.getInstance().driver, false);
            OI.getInstance().rumble(OI.getInstance().aux, false);
        }
    }

    public void testInit() {
        init();
    }

    public void testPeriodic() {
        Scheduler.getInstance().run();
        update();
    }

    public double getMatchTime() {
        return DriverStation.getInstance().getMatchTime();
    }

    public void init() {
        arm.tareDartPosition();
    }

    public void update() {
        SmartDashboard.putNumber("Dart Encoder", arm.getDartEncoderPosition());
        SmartDashboard.putNumber("Dart Potentiometer", arm.getDartPotPosition());
        SmartDashboard.putNumber("Heading", drivetrain.getHeading());

        SmartDashboard.putNumber("Left Drive", drivetrain.getLeftEncPosition());
        SmartDashboard.putNumber("Right Drive", drivetrain.getRightEncPosition());

        SmartDashboard.putBoolean("Arm Prox", arm.hasCube());

        System.out.println("Left Limit Status: " + ramps.getLeftLimit());
        System.out.println("Right Limit Status: " + ramps.getRightLimit());
    }
}