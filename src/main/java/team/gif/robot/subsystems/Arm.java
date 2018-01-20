package team.gif.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem {

    private static Arm instance = null;

    public static Arm getInstance() {
        if (instance == null) {
            instance = new Arm();
        }
        return instance;
    }

    @Override
    protected void initDefaultCommand() {
//        setDefaultCommand(new);
    }
}
