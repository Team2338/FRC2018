package team.gif.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team.gif.lib.AxisButton;
import team.gif.lib.DualButton;
import team.gif.lib.POVButton;
import team.gif.robot.commands.auto.ForwardOneMeter;
import team.gif.robot.commands.subsystem.arm.*;
import team.gif.robot.commands.subsystem.ramps.RampsDeploy;
import team.gif.robot.commands.subsystem.ramps.RampsLift;

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

    JoystickButton dA = new JoystickButton(driver, 1);
    JoystickButton dB = new JoystickButton(driver, 2);
    JoystickButton dX = new JoystickButton(driver, 3);
    JoystickButton dY = new JoystickButton(driver, 4);
    JoystickButton dLB = new JoystickButton(driver, 5);
    JoystickButton dRB = new JoystickButton(driver, 6);
    JoystickButton dBack = new JoystickButton(driver, 7);
    JoystickButton dStart = new JoystickButton(driver, 8);
    AxisButton dLT = new AxisButton(driver, 2, 0.1);
    AxisButton dRT = new AxisButton(driver, 3, 0.1);

    JoystickButton aA = new JoystickButton(aux, 1);
    JoystickButton aB = new JoystickButton(aux, 2);
    JoystickButton aX = new JoystickButton(aux, 3);
    JoystickButton aY = new JoystickButton(aux, 4);
    JoystickButton aLB = new JoystickButton(aux, 5);
    JoystickButton aRB = new JoystickButton(aux, 6);
    JoystickButton aBack = new JoystickButton(aux, 7);
    JoystickButton aStart = new JoystickButton(aux, 8);
    AxisButton aLT = new AxisButton(aux, 2, 0.1);
    AxisButton aRT = new AxisButton(aux, 3, 0.1);

    DualButton menuButons = new DualButton(aux, 7, 8);

    POVButton dpadLeft = new POVButton(aux, POVButton.Direction.WEST);
    POVButton dpadUp = new POVButton(aux, POVButton.Direction.NORTH);
    POVButton dpadRight = new POVButton(aux, POVButton.Direction.EAST);
    POVButton dpadDown = new POVButton(aux, POVButton.Direction.SOUTH);

    private OI() {

        // Arm Positions
        dLT.whenPressed(new ArmSetPosition(Globals.ARM_SWITCH_POSITION));
        dLB.whenPressed(new ArmSetPosition(Globals.ARM_SECOND_POSITION));
        dRB.whenPressed(new ArmSetPosition(Globals.ARM_COLLECT_POSITION));

        // Arm Functions
        aLB.whileHeld(new ArmEject());
        aRB.whileHeld(new ArmCollect());
        aA.whenPressed(new ArmOpen(true));
        aA.whenReleased(new ArmOpen(false));
        aB.whenPressed(new ArmLaunch());

        // Ramp Stuff
        menuButons.whenPressed(new RampsDeploy());
        aLT.whileHeld(new RampsLift(RampsLift.RampSide.RIGHT)); // Hold Y to invert
        aRT.whileHeld(new RampsLift(RampsLift.RampSide.LEFT)); // Hold Y to invert

        SmartDashboard.putData("Arm: Start", new ArmSetPosition(Globals.ARM_START_POSITION));
        SmartDashboard.putData("Arm: Switch", new ArmSetPosition(Globals.ARM_SWITCH_POSITION));
        SmartDashboard.putData("Arm: Second", new ArmSetPosition(Globals.ARM_SECOND_POSITION));
        SmartDashboard.putData("Arm: Travel", new ArmSetPosition(Globals.ARM_TRAVEL_POSITION));
        SmartDashboard.putData("Arm: Collect", new ArmSetPosition(Globals.ARM_COLLECT_POSITION));
        SmartDashboard.putData("Ramps: Deploy", new RampsDeploy());

        SmartDashboard.putData("Forward One Meter", new ForwardOneMeter("LRL"));
    }

    public void rumble(XboxController controller, boolean rumble) {
        if (rumble) {
            controller.setRumble(GenericHID.RumbleType.kLeftRumble, 1.0);
            controller.setRumble(GenericHID.RumbleType.kRightRumble, 1.0);
        } else {
            controller.setRumble(GenericHID.RumbleType.kLeftRumble, 0.0);
            controller.setRumble(GenericHID.RumbleType.kRightRumble, 0.0);
        }
    }
}
