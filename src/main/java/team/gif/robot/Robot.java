package team.gif.robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import team.gif.robot.subsystems.Drivetrain;

public class Robot extends IterativeRobot {

    private Drivetrain drivetrain = Drivetrain.getInstance();

    @Override
    public void robotInit() {
//        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
//        camera.setResolution(320, 240);
//        camera.setFPS(60);
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
        Scheduler.getInstance().run();
    }
    
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void testPeriodic() {

    }
}