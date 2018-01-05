package team.gif.robot;

import edu.wpi.first.wpilibj.XboxController;

public class OI {

    private static OI instance = null;

    public static OI getInstance() {
        return instance == null ? new OI() : instance;
    }

    public XboxController driver;
    public XboxController aux;

    private OI() {
        driver = new XboxController(RobotMap.OI.DRIVER_CONTROLLER_PORT);
        aux = new XboxController(RobotMap.OI.AUX_CONTROLLER_PORT);
    }

}
