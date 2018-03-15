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
        DO_NOTHING, SWITCH, SCALE, MOBILITY
    }

//    public static MotorLogger logger = new MotorLogger();

    private SendableChooser<StartPosition> startPositionChooser;
    private SendableChooser<Strategy> strategyChooser;
    private Command auto;
    private String gameData = "";

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
        strategyChooser.addObject("Scale", Strategy.SCALE);
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

        while (gameData.equals("")) {
            if (getGameData() != null) {
                gameData = getGameData();
            }
        }

        Strategy strategy = strategyChooser.getSelected();
        StartPosition startPosition =  startPositionChooser.getSelected();

        if (strategy == Strategy.DO_NOTHING) {
            auto = new DoNothing();
        } else if (strategy == Strategy.SWITCH) {
            if (startPosition == StartPosition.LEFT) {
                auto = new SwitchLeft(gameData);
            } else if (startPosition == StartPosition.CENTER) {
                auto = new SwitchCenter(gameData);
            } else if (startPosition == StartPosition.RIGHT){
                auto = new SwitchRight(gameData);
            }
        } else if (strategy == Strategy.SCALE) {
            if (startPosition == StartPosition.LEFT) {
                auto = new ScaleLeft(gameData);
            } else if (startPosition == StartPosition.CENTER) {
                auto = new SwitchCenter(gameData);
            } else if (startPosition == StartPosition.RIGHT){
                auto = new ScaleRight(gameData);
            }
        } else if (strategy == Strategy.MOBILITY) {
            if (startPosition == StartPosition.LEFT) {
                auto = new Mobility(gameData);
            } else if (startPosition == StartPosition.CENTER) {
                auto = new MobilityCenter(gameData);
            } else if (startPosition == StartPosition.RIGHT){
                auto = new Mobility(gameData);
            }
        }

        if (gameData.equals("")) { auto = new DoNothing(); }

        if (auto != null) auto.start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        if (drivetrain.getLeftEncVelociy()*10/4096*Globals.Drivetrain.WHEEL_DIAMETER_M*Math.PI > 2.5 ||
                drivetrain.getRightEncVelocity()*10/4096*Globals.Drivetrain.WHEEL_DIAMETER_M*Math.PI > 2.5) {
            auto.cancel();
        }
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
        SmartDashboard.putNumber("Left Velocity (mps)", drivetrain.getLeftEncVelociy() * 10 / 4096 * Globals.Drivetrain.WHEEL_DIAMETER_M * Math.PI);
        SmartDashboard.putNumber("Right Velocity (mps)", drivetrain.getRightEncVelocity()  * 10 / 4096 * Globals.Drivetrain.WHEEL_DIAMETER_M * Math.PI);

        SmartDashboard.putBoolean("Left Ramp Limit", ramps.getLeftLimit());
        SmartDashboard.putBoolean("Right Ramp Limit", ramps.getRightLimit());

        SmartDashboard.putNumber("Distance (m)", (drivetrain.getLeftEncPosition()+drivetrain.getRightEncPosition())/2/4096*Globals.Drivetrain.WHEEL_DIAMETER_M * Math.PI);

        SmartDashboard.putBoolean("Cube", arm.hasCube());
    }
}