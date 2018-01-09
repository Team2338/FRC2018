package team.gif.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import team.gif.robot.subsystems.Drivetrain;

public class Robot extends IterativeRobot {

    private Drivetrain drivetrain = Drivetrain.getInstance();

    @Override
    public void robotInit() {
        UsbCamera fisheye = CameraServer.getInstance().startAutomaticCapture();
        fisheye.setResolution(1280, 720);
        fisheye.setFPS(60);
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void autonomousInit() {

    }

    @Override
    public void teleopInit() {

    }

    @Override
    public void testInit() {

    }


    @Override
    public void disabledPeriodic() {

    }
    
    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopPeriodic() {

    }

    @Override
    public void testPeriodic() {

    }
}