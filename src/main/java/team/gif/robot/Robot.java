package team.gif.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team.gif.lib.GIFMath;
import team.gif.robot.commands.system.DoNothing;
import team.gif.robot.commands.auto.*;
import team.gif.robot.subsystems.Arm;
import team.gif.robot.subsystems.Drivetrain;
import team.gif.robot.subsystems.Ramp;

public class Robot extends IterativeRobot {

    private enum StartPosition {
        LEFT, CENTER, RIGHT
    }

    private enum Strategy {
        MOBILITY, SWITCH, NULL_ZONE, DO_NOTHING
    }

    private StartPosition startPosition = StartPosition.LEFT;
    private Strategy strategy = Strategy.MOBILITY;
    private SendableChooser<StartPosition> startPositionChooser;
    private SendableChooser<Strategy> strategyChooser;
    private Command auto;
    private String gameData;

    private Drivetrain drivetrain = Drivetrain.getInstance();
    private Arm arm = Arm.getInstance();
    private Ramp ramp = Ramp.getInstance();

    @Override
    public void robotInit() {
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
        arm.tareDartPosition();
    }

    public void disabledPeriodic() {
        update();
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        startPosition = startPositionChooser.getSelected();
        strategy = strategyChooser.getSelected();
        gameData = DriverStation.getInstance().getGameSpecificMessage();
        DriverStation.Alliance alliance = DriverStation.getInstance().getAlliance();
        boolean backLeft = gameData.charAt(2) == 'L';
        boolean midLeft = gameData.charAt(1) == 'L';
        boolean frontLeft = gameData.charAt(0) == 'L';
        SmartDashboard.putBoolean("Back Left", backLeft && alliance.equals(DriverStation.Alliance.Blue));
        SmartDashboard.putBoolean("Back Right", !backLeft && alliance.equals(DriverStation.Alliance.Blue));
        SmartDashboard.putBoolean("Mid Left", midLeft && alliance.equals(DriverStation.Alliance.Blue));
        SmartDashboard.putBoolean("Mid Right", !midLeft && alliance.equals(DriverStation.Alliance.Blue));
        SmartDashboard.putBoolean("Front Left", frontLeft && alliance.equals(DriverStation.Alliance.Blue));
        SmartDashboard.putBoolean("Front Right", !frontLeft && alliance.equals(DriverStation.Alliance.Blue));
        arm.tareDartPosition();

        if (strategy == Strategy.MOBILITY) {
            if (startPosition == StartPosition.LEFT) {
                auto = new LeftMobility(gameData);
                SmartDashboard.putString("Status", "LeftMobility");
            } else if (startPosition == StartPosition.CENTER) {
                auto = new CenterMobility(gameData);
                SmartDashboard.putString("Status", "CenterMobility");
            } else {
                auto = new RightMobility(gameData);
                SmartDashboard.putString("Status", "RightMobility");
            }
        } else if (strategy == Strategy.SWITCH) {
            if (startPosition == StartPosition.LEFT) {
                auto = new LeftSwitch(gameData);
                SmartDashboard.putString("Status", "LeftSwitch");
            } else if (startPosition == StartPosition.CENTER) {
                auto = new CenterSwitch(gameData);
                SmartDashboard.putString("Status", "CenterSwitch");
            } else {
                auto = new RightSwitch(gameData);
                SmartDashboard.putString("Status", "RightSwitch");
            }
        } else if (strategy == Strategy.NULL_ZONE) {
            if (startPosition == StartPosition.LEFT) {
                auto = new LeftNull(gameData);
                SmartDashboard.putString("Status", "LeftNull");
            } else if (startPosition == StartPosition.CENTER) {
                auto = new CenterNull(gameData);
                SmartDashboard.putString("Status", "CenterNull");
            } else {
                auto = new RightNull(gameData);
                SmartDashboard.putString("Status", "RightNull");
            }
        } else {
            auto = new DoNothing();
            SmartDashboard.putString("Status", "Do Nothing");
        }

        if (auto != null) auto.start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        update();
    }

    public void teleopInit() {
        arm.tareDartPosition();
        if (auto != null) auto.cancel();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        update();
    }

    public void testInit() {

    }

    public void testPeriodic() {

    }

    public void update() {
        SmartDashboard.putNumber("Voltage", RobotController.getInputVoltage());
        SmartDashboard.putNumber("Dart Encoder", arm.getEncoderPosition());
        SmartDashboard.putNumber("Dart Potentiometer", arm.getPotPosition());

//        if (Math.abs(arm.getEncoderPosition() - GIFMath.map(arm.getPotPosition(), 600, 3200, 0, 468000)) > 1000)
//            arm.tareDartPosition();
    }
}