package team.gif.robot.subsystems;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import team.gif.lib.TalonSRXConfigurator;
import team.gif.robot.Globals;
import team.gif.robot.RobotMap;
import team.gif.robot.commands.system.DoNothing;

public class Arm extends Subsystem {

    private static Arm instance = null;

    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }
        return instance;
    }

    // Hardware
    private TalonSRX left = TalonSRXConfigurator.createDefaultTalon(RobotMap.Arm.FRONT_LEFT_ID);
    private TalonSRX right = TalonSRXConfigurator.createDefaultTalon(RobotMap.Arm.FRONT_RIGHT_ID);
    private TalonSRX dart = TalonSRXConfigurator.createDefaultTalon(RobotMap.Arm.DART_ID);

    private Solenoid actuator = new Solenoid(RobotMap.Arm.ACTUATOR_SOLENOID_ID);

    private AnalogInput dartPot = new AnalogInput(RobotMap.Arm.DART_POT_ID);

    private Arm() {
        left.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        right.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        dart.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
        dart.configSetParameter(ParamEnum.eFeedbackNotContinuous, 1, 0x00, 0x00, 0x00);

        dart.config_kP(0, Globals.DART_P, 0);
        dart.config_kI(0, Globals.DART_I, 0);
        dart.config_kD(0, Globals.DART_D, 0);
    }

    public void setFront(double speed) {
        left.set(ControlMode.PercentOutput, speed);
        right.set(ControlMode.PercentOutput, speed);
    }

    public void open() {
        actuator.set(true);
    }

    public void close() {
        actuator.set(false);
    }

    public void setDartPosition(int position) {
        dart.set(ControlMode.Position, position);
    }

    protected void initDefaultCommand() {
//        setDefaultCommand(new DoNothing());
    }
}
