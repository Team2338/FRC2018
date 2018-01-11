package team.gif.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

public class CubeManipulator extends Subsystem {

    private static CubeManipulator instance = null;

    public static CubeManipulator getInstance() {
        if (instance == null) {
            instance = new CubeManipulator();
        }
        return instance;
    }

    @Override
    protected void initDefaultCommand() {
//        setDefaultCommand(new);
    }
}
