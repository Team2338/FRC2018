package team.gif.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team.gif.lib.MotorLogger;
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
        DO_NOTHING, SWITCH, DOUBLE_SWITCH, SWITCH_SCALE, SCALE_SWITCH, MOBILITY
    }

    public static MotorLogger logger = new MotorLogger();

    private SendableChooser<StartPosition> startPositionChooser;
    private SendableChooser<Strategy> strategyChooser;
    private Command auto;
    private String gameData;

    private Drivetrain drivetrain = Drivetrain.getInstance();
    private Arm arm = Arm.getInstance();
    private Ramps ramps = Ramps.getInstance();
    private OI oi = OI.getInstance();

    public void robotInit() {
        init();
        startPositionChooser = new SendableChooser<>();
        strategyChooser = new SendableChooser<>();

        startPositionChooser.addDefault("Left", StartPosition.LEFT);
        startPositionChooser.addObject("Center", StartPosition.CENTER);
        startPositionChooser.addObject("Right", StartPosition.RIGHT);

        strategyChooser.addDefault("Do Nothing", Strategy.DO_NOTHING);
        strategyChooser.addObject("Switch", Strategy.SWITCH);
        strategyChooser.addObject("Double Switch", Strategy.DOUBLE_SWITCH);
        strategyChooser.addObject("Switch to Scale", Strategy.SWITCH_SCALE);
        strategyChooser.addObject("Scale to Switch", Strategy.SCALE_SWITCH);
        strategyChooser.addObject("Mobility", Strategy.MOBILITY);

        SmartDashboard.putData("Strategy", strategyChooser);
        SmartDashboard.putData("Start Position", startPositionChooser);
    }

    public void robotPeriodic() {
        update();
    }

    public void disabledInit() {
        init();
    }

    public void disabledPeriodic() {

    }

    public void autonomousInit() {
        init();
        gameData = getGameData();

        Strategy strategy = strategyChooser.getSelected();
        StartPosition startPosition =  startPositionChooser.getSelected();

        if (strategy == Strategy.DO_NOTHING) {
            auto = new DoNothing();
        } else if (strategy == Strategy.SWITCH) {
            if (startPosition == StartPosition.LEFT) {
                auto = new SoloSwitchLeft(gameData);
            } else if (startPosition == StartPosition.CENTER) {
                auto = new SoloSwitchCenter(gameData);
            } else if (startPosition == StartPosition.RIGHT){
                auto = new SoloSwitchRight(gameData);
            }
        } else if (strategy == Strategy.DOUBLE_SWITCH) {
            if (startPosition == StartPosition.LEFT) {
                auto = new DoubleSwitchLeft(gameData);
            } else if (startPosition == StartPosition.CENTER) {
                auto = new DoubleSwitchCenter(gameData);
            } else if (startPosition == StartPosition.RIGHT){
                auto = new DoubleSwitchRight(gameData);
            }
        } else if (strategy == Strategy.SWITCH_SCALE) {
            if (startPosition == StartPosition.LEFT) {
                auto = new SwitchScaleLeft(gameData);
            } else if (startPosition == StartPosition.CENTER) {
                auto = new SwitchScaleCenter(gameData);
            } else if (startPosition == StartPosition.RIGHT){
                auto = new SwitchScaleRight(gameData);
            }
        }  else if (strategy == Strategy.SCALE_SWITCH) {
            if (startPosition == StartPosition.LEFT) {
                auto = new ScaleSwitchLeft(gameData);
            } else if (startPosition == StartPosition.CENTER) {
                auto = new ScaleSwitchCenter(gameData);
            } else if (startPosition == StartPosition.RIGHT){
                auto = new ScaleSwitchRight(gameData);
            }
        } else if (strategy == Strategy.MOBILITY) {
            auto = new Mobility(gameData);
        }

        if (gameData == null) { auto = new DoNothing(); }

        if (auto != null) auto.start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        if (auto != null) auto.cancel();
        init();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();

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
    }

    public double getMatchTime() {
        return DriverStation.getInstance().getMatchTime();
    }

    public String getGameData() {
        return DriverStation.getInstance().getGameSpecificMessage();
    }

    public void init() {
        arm.tareDartPosition();
        drivetrain.resetGyro();
        drivetrain.resetEncoders();
    }

    public void update() {
        SmartDashboard.putNumber("Dart Encoder", arm.getDartEncoderPosition());
        SmartDashboard.putNumber("Dart Potentiometer", arm.getDartPotPosition());

        SmartDashboard.putNumber("Drive Heading", drivetrain.getHeading());
        SmartDashboard.putNumber("Left Position", drivetrain.getLeftEncPosition());
        SmartDashboard.putNumber("Right Position", drivetrain.getRightEncPosition());
        SmartDashboard.putNumber("Left Velocity (mps)", drivetrain.getLeftEncVelociy() * 10 / 4096 * 0.484);
        SmartDashboard.putNumber("Right Velocity (mps)", drivetrain.getRightEncVelocity()  * 10 / 4096 * 0.484);

        SmartDashboard.putBoolean("Left Ramp Limit", ramps.getLeftLimit());
        SmartDashboard.putBoolean("Right Ramp Limit", ramps.getRightLimit());

        SmartDashboard.putBoolean("Cube", arm.hasCube());
    }
}