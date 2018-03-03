package team.gif.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team.gif.lib.GameDataCommandGroup;
import team.gif.robot.commands.system.DoNothing;
import team.gif.robot.commands.auto.*;
import team.gif.robot.subsystems.Arm;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.Ramps;

public class Robot extends TimedRobot {

    private enum StartPosition {
        LEFT, CENTER, RIGHT
    }

    private enum Strategy {
        DO_NOTHING, MOBILITY, FRONT_SWITCH, DOUBLE_SWITCH, SWITCH_SCALE, SWITCH_EXCHANGE
    }

    private SendableChooser<StartPosition> startPositionChooser;
    private SendableChooser<Strategy> strategyChooser;
    private StartPosition startPosition;
    private Strategy strategy;
    private GameDataCommandGroup auto;
    private String gameData = "";

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

        strategyChooser.addDefault("Do Nothing", Strategy.DO_NOTHING);
        strategyChooser.addObject("Mobility", Strategy.MOBILITY);
        strategyChooser.addObject("Front Switch", Strategy.FRONT_SWITCH);
        strategyChooser.addObject("Double Switch", Strategy.DOUBLE_SWITCH);
        strategyChooser.addObject("Switch & Scale", Strategy.SWITCH_SCALE);
        strategyChooser.addObject("Switch & Exchange", Strategy.SWITCH_EXCHANGE);

        SmartDashboard.putData("Strategy", strategyChooser);
        SmartDashboard.putData("Start Position", startPositionChooser);
    }

    public void disabledInit() {
        init();
    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
        update();

        if (startPositionChooser.getSelected() != startPosition || strategyChooser.getSelected() != strategy) {
            if (strategy == Strategy.DO_NOTHING) {
                auto = new DoNothing();
            } else if (strategy == Strategy.MOBILITY) {
                if (startPosition == StartPosition.LEFT) {
                    auto = new MobilityLeft();
                } else if (startPosition == StartPosition.CENTER) {
                    auto = new MobilityCenter();
                } else if (startPosition == StartPosition.RIGHT){
                    auto = new MobilityRight();
                }
            } else if (strategy == Strategy.FRONT_SWITCH) {
                if (startPosition == StartPosition.LEFT) {
                    auto = new FrontSwitchLeft();
                } else if (startPosition == StartPosition.CENTER) {
                    auto = new FrontSwitchCenter();
                } else if (startPosition == StartPosition.RIGHT){
                    auto = new FrontSwitchRight();
                }
            } else if (strategy == Strategy.DOUBLE_SWITCH) {
                if (startPosition == StartPosition.LEFT) {
                    auto = new DoubleSwitchLeft();
                } else if (startPosition == StartPosition.CENTER) {
                    auto = new DoubleSwitchCenter();
                } else if (startPosition == StartPosition.RIGHT){
                    auto = new DoubleSwitchRight();
                }
            } else if (strategy == Strategy.SWITCH_SCALE) {
                if (startPosition == StartPosition.LEFT) {
                    auto = new SwitchScaleLeft();
                } else if (startPosition == StartPosition.CENTER) {
                    auto = new SwitchScaleCenter();
                } else if (startPosition == StartPosition.RIGHT){
                    auto = new SwitchScaleRight();
                }
            } else if (strategy == Strategy.SWITCH_EXCHANGE) {
                if (startPosition == StartPosition.LEFT) {
                    auto = new SwitchExchangeLeft();
                } else if (startPosition == StartPosition.CENTER) {
                    auto = new SwitchExchangeCenter();
                } else if (startPosition == StartPosition.RIGHT){
                    auto = new SwitchExchangeRight();
                }
            }
        }

        SmartDashboard.putString("Selected Auto", auto.getName());

        startPosition = startPositionChooser.getSelected();
        strategy = strategyChooser.getSelected();
    }

    public void autonomousInit() {
        init();
        do { gameData = DriverStation.getInstance().getGameSpecificMessage(); } while (gameData.length() < 3);
        auto.setGameData(gameData);

        boolean closeLeft = gameData.charAt(0) == 'L';
        boolean midLeft = gameData.charAt(1) == 'L';
        boolean farLeft = gameData.charAt(2) == 'L';
        SmartDashboard.putBoolean("Close Left", closeLeft);
        SmartDashboard.putBoolean("Close Right", !closeLeft);
        SmartDashboard.putBoolean("Mid Left", midLeft);
        SmartDashboard.putBoolean("Mid Right", !midLeft);
        SmartDashboard.putBoolean("Far Left", farLeft);
        SmartDashboard.putBoolean("Far Right", !farLeft);

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

    public String getGameData() {
        return DriverStation.getInstance().getGameSpecificMessage();
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