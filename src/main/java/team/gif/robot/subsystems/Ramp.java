package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import team.gif.lib.TalonSRXConfigurator;
import team.gif.robot.RobotMap;
import team.gif.robot.commands.system.DoNothing;

public class Ramp extends Subsystem{
    private static Ramp instance = null;

    public static Ramp getInstance() {
        if (instance == null) {
            instance = new Ramp();
        }
        return instance;
    }

    // Hardware
    private TalonSRX leftMaster = TalonSRXConfigurator.createDefaultTalon(RobotMap.Ramp.LEFT_MASTER_ID);
    private TalonSRX leftFollower = TalonSRXConfigurator.createFollowerTalon(RobotMap.Ramp.LEFT_FOLLOWER_ID, RobotMap.Ramp.LEFT_MASTER_ID);
    private TalonSRX rightMaster = TalonSRXConfigurator.createDefaultTalon(RobotMap.Ramp.RIGHT_MASTER_ID);
    private TalonSRX rightFollower = TalonSRXConfigurator.createFollowerTalon(RobotMap.Ramp.RIGHT_FOLLOWER_ID, RobotMap.Ramp.RIGHT_MASTER_ID);

    private Ramp() {

    }

    protected void initDefaultCommand() {
        setDefaultCommand(new DoNothing());
    }
}
