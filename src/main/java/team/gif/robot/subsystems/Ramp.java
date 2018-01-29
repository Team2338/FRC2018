package team.gif.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
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
    private TalonSRX leftMaster = new TalonSRX(RobotMap.Ramp.LEFT_MASTER_ID);
    private TalonSRX leftFollower = new TalonSRX(RobotMap.Ramp.LEFT_FOLLOWER_ID);
    private TalonSRX rightMaster = new TalonSRX(RobotMap.Ramp.RIGHT_MASTER_ID);
    private TalonSRX rightFollower = new TalonSRX(RobotMap.Ramp.RIGHT_FOLLOWER_ID);

    private Ramp() {

    }

    protected void initDefaultCommand() {
        setDefaultCommand(new DoNothing());
    }
}
