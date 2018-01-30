package team.gif.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import team.gif.lib.AxisButton;
import team.gif.robot.commands.subsystems.arm.SetArmPosition;

public class OI {

    private static OI instance = null;

    public static OI getInstance()    {
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }

    public XboxController driver = new XboxController(RobotMap.OI.DRIVER_CONTROLLER_ID);
    public XboxController aux = new XboxController(RobotMap.OI.AUX_CONTROLLER_ID);

    JoystickButton dA = new JoystickButton(driver, 0);
    JoystickButton dB = new JoystickButton(driver, 1);
    JoystickButton dX = new JoystickButton(driver, 2);
    JoystickButton dY = new JoystickButton(driver, 3);
    JoystickButton dLB = new JoystickButton(driver, 4);
    JoystickButton dRB = new JoystickButton(driver, 5);
    JoystickButton dBack = new JoystickButton(driver, 6);
    JoystickButton dStart = new JoystickButton(driver, 7);
    AxisButton dLT = new AxisButton(driver, 2, 0.1);
    AxisButton dRT = new AxisButton(driver, 2, 0.1);

    JoystickButton aA = new JoystickButton(aux, 0);
    JoystickButton aB = new JoystickButton(aux, 1);
    JoystickButton aX = new JoystickButton(aux, 2);
    JoystickButton aY = new JoystickButton(aux, 3);
    JoystickButton aLB = new JoystickButton(aux, 4);
    JoystickButton aRB = new JoystickButton(aux, 5);
    JoystickButton aBack = new JoystickButton(aux, 6);
    JoystickButton aStart = new JoystickButton(aux, 7);
    AxisButton aLT = new AxisButton(aux, 2, 0.1);
    AxisButton aRT = new AxisButton(aux, 2, 0.1);

    private OI() {
        aA.whenPressed(new SetArmPosition(512));
    }
}
