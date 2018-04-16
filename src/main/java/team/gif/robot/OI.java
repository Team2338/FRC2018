package team.gif.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team.gif.lib.AxisButton;
import team.gif.lib.DualButton;
import team.gif.lib.POVButton;
import team.gif.robot.commands.subsystem.arm.*;
import team.gif.robot.commands.subsystem.drivetrain.*;
import team.gif.robot.commands.subsystem.ramps.RampsDeploy;
import team.gif.robot.commands.subsystem.ramps.RampsLift;
import team.gif.robot.commands.system.GeneratePaths;

public class OI {

    private static OI instance = null;

    public static OI getInstance()    {
        if (instance == null) {
            instance = new OI();
        }
        return instance;
    }

    public XboxController driver = new XboxController(RobotMap.OI.DRIVE_CONTROLLER_ID);
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

//        dA.whenPressed(new FollowPathForward(Pathfinder.readFromCSV(new File("/home/lvuser/twometer.csv"))));
//        dA.whenPressed(new FollowPathForward(Pathfinder.readFromCSV(new File("/home/lvuser/fivemeter.csv"))));
//        dB.whenPressed(new FollowPathForward(Pathfinder.readFromCSV(new File("/home/lvuser/lefttorightscale.csv"))));
//        dA.whenPressed(new FollowPathForward(Pathfinder.readFromCSV(new File("/home/lvuser/leftscaletoleftswitch.csv"))));
//-        dB.whenPressed(new SwitchCenter("LLL"));
//-        dB.whenPressed(new SwitchScaleLeft("LLL"));

        //List of of paths
//1        dB.whenPressed(new ScaleSwitchLeft("LLL", Robot.AutoSecondary.SWITCH )); // 1
//2.1        dB.whenPressed(new ScaleSwitchLeft("RRR", Robot.AutoSecondary.SWITCH )); // 2.1
//2.2        dB.whenPressed(new ScaleSwitchLeft("LRR", Robot.AutoSecondary.SWITCH )); // 2.2
//3.1        dB.whenPressed(new ScaleSwitchLeft("LLL", Robot.AutoSecondary.SAFE   )); // 3.1
//3.2        dB.whenPressed(new ScaleSwitchLeft("RLR", Robot.AutoSecondary.SAFE   )); // 3.2
//4        dB.whenPressed(new ScaleSwitchLeft("RLR", Robot.AutoSecondary.SWITCH )); // 4

//5       dB.whenPressed(new ScaleSwitchRight("RRR", Robot.AutoSecondary.SWITCH )); // 5
//6.1        dB.whenPressed(new ScaleSwitchRight("LLL", Robot.AutoSecondary.SWITCH )); // 6.1
//6.2        dB.whenPressed(new ScaleSwitchRight("RLL", Robot.AutoSecondary.SWITCH )); // 6.2
//7.1        dB.whenPressed(new ScaleSwitchRight("RRR", Robot.AutoSecondary.SAFE   )); // 7.1
//7.2        dB.whenPressed(new ScaleSwitchRight("LRL", Robot.AutoSecondary.SAFE   )); // 7.2
//8        dB.whenPressed(new ScaleSwitchRight("LRL", Robot.AutoSecondary.SWITCH )); // 8

// 9.1        dB.whenPressed(new SwitchCenter("LLL")); //9.1
// 9.2        dB.whenPressed(new SwitchCenter("LRL")); //9.2
//10.1        dB.whenPressed(new SwitchCenter("RRR")); //10.1
//10.2        dB.whenPressed(new SwitchCenter("RLR")); //10.2

//11.1        dB.whenPressed(new SwitchLeft("LLL")); //11.1
//11.2        dB.whenPressed(new SwitchLeft("LRL")); //11.2
//12.1        dB.whenPressed(new SwitchLeft("RRR")); //12.1
//12.2        dB.whenPressed(new SwitchLeft("RLR")); //12.2

//13.1        dB.whenPressed(new SwitchRight("RRR")); //13.1
//13.2        dB.whenPressed(new SwitchRight("RLR")); //13.2
//14.1        dB.whenPressed(new SwitchRight("LLL")); //14.1
//14.2        dB.whenPressed(new SwitchRight("LRL")); //14.2

//        dB.whenPressed(new SwitchCenter("LLL", Robot.AutoSecondary.DOUBLESWITCH)); //15
//        dB.whenPressed(new SwitchCenter("RRR", Robot.AutoSecondary.DOUBLESWITCH)); //16

//        dA.whenPressed(new RotateByAngle(90));

//        dX.whenPressed(new GeneratePaths());
//        dX.whileHeld(new DrivetrainConstantPercent(0.2, 12));
//        dA.whileHeld(new DrivetrainConstantPercent(0.3, 12));
//        dB.whileHeld(new DrivetrainConstantPercent(0.4, 12));

        SmartDashboard.putData(new GeneratePaths());
        SmartDashboard.putData(new CharacterizeDrivetrain());
        SmartDashboard.putData(new DrivetrainRampVoltage(8));

        // Arm Positions
        dLT.whenPressed(new ArmSetPosition(Globals.Arm.ARM_SWITCH_POSITION));
        dLB.whenPressed(new ArmSetPosition(Globals.Arm.ARM_SECOND_POSITION));
        dRB.whenPressed(new ArmSetPosition(Globals.Arm.ARM_COLLECT_POSITION));
        dB.whenPressed(new ArmSetPosition(Globals.Arm.ARM_HIT_CUBE_POSITION));
        dY.whenPressed(new ArmSetPosition(Globals.Arm.ARM_START_POSITION));

        // Arm Functions
        aLB.whileHeld(new ArmEject(1.0));
        aRB.whileHeld(new ArmCollect());
        aA.whileHeld(new ArmOpen());
        aB.whenPressed(new ArmLaunchShort());
        aY.whenPressed(new ArmLaunchLong());

        // Ramp Stuff
        menuButons.whenPressed(new RampsDeploy());
        aLT.whileHeld(new RampsLift(RampsLift.RampSide.RIGHT)); // Hold X to invert
        aRT.whileHeld(new RampsLift(RampsLift.RampSide.LEFT)); // Hold X to invert

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
