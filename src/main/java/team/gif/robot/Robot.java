package team.gif.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//a import team.gif.lib.Limelight;
import team.gif.lib.Limelight;
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
        NOTHING, SWITCH, SCALE, MOBILITY, NO_CROSS
    }

    public enum AutoSecondary {
        NOTHING, SWITCH, SAFE, DOUBLESWITCH, SCALE
    }


//    public static MotorLogger logger = new MotorLogger();

    private SendableChooser<StartPosition> startPositionChooser;
    private SendableChooser<Strategy> strategyChooser;
    private SendableChooser<AutoSecondary> autoSecondaryChooser;
    private Command auto;
    private String gameData = "";

    private Drivetrain drivetrain = Drivetrain.getInstance();
    private Arm arm = Arm.getInstance();
    private Ramps ramps = Ramps.getInstance();
    private OI oi = OI.getInstance();
    private Limelight limelight = Limelight.getInstance();

    public void robotInit() {
        init();
        startPositionChooser = new SendableChooser<>();
        strategyChooser = new SendableChooser<>();
        autoSecondaryChooser = new SendableChooser<>();

        startPositionChooser.addDefault("Left", StartPosition.LEFT);
        startPositionChooser.addObject("Center", StartPosition.CENTER);
        startPositionChooser.addObject("Right", StartPosition.RIGHT);

        strategyChooser.addDefault("Nothing", Strategy.NOTHING);
        strategyChooser.addObject("Switch", Strategy.SWITCH);
        strategyChooser.addObject("Scale", Strategy.SCALE);
        strategyChooser.addObject("Mobility", Strategy.MOBILITY);
        strategyChooser.addObject("No Cross", Strategy.NO_CROSS);

        autoSecondaryChooser.addDefault("Nothing", AutoSecondary.NOTHING);
        autoSecondaryChooser.addObject("Scale->Switch", AutoSecondary.SWITCH);
        autoSecondaryChooser.addObject("Scale->Safe", AutoSecondary.SAFE);
        autoSecondaryChooser.addObject("Switch->Double Cube", AutoSecondary.DOUBLESWITCH);
        autoSecondaryChooser.addObject("Scale->Scale", AutoSecondary.SCALE);

        SmartDashboard.putData("Switch Safe Double", autoSecondaryChooser);
        SmartDashboard.putData("Strategy", strategyChooser);
        SmartDashboard.putData("Start Position", startPositionChooser);

        SmartDashboard.putNumber("Angle Error", 0);
        SmartDashboard.putNumber("Rotate P", 0);
    }

    public void robotPeriodic() {
        update();
    }

    public void disabledInit() {
        init();
    }

    public void disabledPeriodic() {
        limelight.setLEDMode(Limelight.LEDMode.OFF);
    }

    public void autonomousInit() {
        init();

//        while (gameData.equals("")) {
//            if (getGameData() != null) {
                gameData = getGameData();
//            }
//        }

        Strategy strategy = strategyChooser.getSelected();
        StartPosition startPosition =  startPositionChooser.getSelected();
        AutoSecondary autoSecondaryMode = autoSecondaryChooser.getSelected();

        if (strategy == Strategy.NOTHING) {
            auto = new DoNothing();
        } else if (strategy == Strategy.SWITCH) {
            if (startPosition == StartPosition.LEFT) {
                auto = new SwitchLeft(gameData);
            } else if (startPosition == StartPosition.CENTER) {
                auto = new SwitchCenter(gameData, autoSecondaryMode);
            } else if (startPosition == StartPosition.RIGHT){
                auto = new SwitchRight(gameData);
            }
        } else if (strategy == Strategy.SCALE) {
            if (startPosition == StartPosition.LEFT) {
                auto = new ScaleSwitchLeft(gameData, autoSecondaryMode);
            } else if (startPosition == StartPosition.CENTER) {
                auto = new SwitchCenter(gameData, autoSecondaryMode);
            } else if (startPosition == StartPosition.RIGHT){
                auto = new ScaleSwitchRight(gameData, autoSecondaryMode);
            }
        } else if (strategy == Strategy.MOBILITY) {
            if (startPosition == StartPosition.LEFT) {
                auto = new Mobility(gameData);
            } else if (startPosition == StartPosition.CENTER) {
                auto = new MobilityCenter(gameData);
            } else if (startPosition == StartPosition.RIGHT){
                auto = new Mobility(gameData);
            }
        } else if (strategy == Strategy.NO_CROSS) {
            if (startPosition == StartPosition.LEFT) {
                auto = new NoCrossLeft(gameData, autoSecondaryMode);
            } else if (startPosition == StartPosition.CENTER) {
                auto = new SwitchCenter(gameData, autoSecondaryMode);
            } else if (startPosition == StartPosition.RIGHT){
                auto = new NoCrossRight(gameData, autoSecondaryMode);
            }
        }

        if (gameData.equals("")) { auto = new DoNothing(); }

        if (auto != null) auto.start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
//        if ((drivetrain.getLeftEncVelociy()*10/4096*Globals.Drivetrain.WHEEL_DIAMETER_M*Math.PI +
//                drivetrain.getRightEncVelocity()*10/4096*Globals.Drivetrain.WHEEL_DIAMETER_M*Math.PI)/2 > 2.5) {
//            auto.cancel();
//        }
    }

    public void teleopInit() {
        if (auto != null) auto.cancel();
        init();
        limelight.setCAMMode(Limelight.CAMMode.VISION);
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

        if (OI.getInstance().aX.get()) {
            limelight.setLEDMode(Limelight.LEDMode.BLINK);
        } else {
            limelight.setLEDMode(Limelight.LEDMode.OFF);
        }

        if (OI.getInstance().dpadDown.get()) {
            arm.setPunchReturn(true);
        }

//        SmartDashboard.putNumber("Left Velocity (rps)", drivetrain.getLeftEncVelociy()*10/4096);
//        SmartDashboard.putNumber("Right Velocity (rps)", drivetrain.getRightEncVelocity()*10/4096);
//        SmartDashboard.putNumber("Left Output", drivetrain.getLeftMaster().getMotorOutputPercent());
//        SmartDashboard.putNumber("Right Output", drivetrain.getLeftMaster().getMotorOutputPercent());
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
//        SmartDashboard.putNumber("Left Position", drivetrain.getLeftEncPosition());
//        SmartDashboard.putNumber("Right Position", drivetrain.getRightEncPosition());

        SmartDashboard.putBoolean("Left Ramp Limit", ramps.getLeftLimit());
        SmartDashboard.putBoolean("Right Ramp Limit", ramps.getRightLimit());

        SmartDashboard.putNumber("Left Velocity (rps)", drivetrain.getLeftEncVelociy()/4096*10);
        SmartDashboard.putNumber("Right Velocity (rps)", drivetrain.getRightEncVelocity()/4096*10);

//        SmartDashboard.putNumber("Distance (in)", (drivetrain.getLeftEncPosition()+drivetrain.getRightEncPosition())/2/4096*Globals.Drivetrain.WHEEL_DIAMETER_IN * Math.PI);

        SmartDashboard.putBoolean("Cube", arm.hasCube());

//        SmartDashboard.putBoolean("Cube Vision", Limelight.getInstance().hasValidTarget());
    }
}