package team.gif.robot.commands.subsystem.arm;

import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.Globals;
import team.gif.robot.subsystems.Arm;

public class ArmLaunchShort extends Command {

    private Arm arm = Arm.getInstance();
    private double pressure;

    public ArmLaunchShort() {
        super(1);
        requires(arm);
    }

    protected void initialize() {
        pressure = arm.getEstimatedPressure();
        System.out.println("Init Pressure: " + pressure);
    }

    protected void execute() {
        if (arm.getDartEncoderPosition() > Globals.Arm.ARM_OPEN_SOFT_LIMIT) {
            if (timeSinceInitialized() > 0.14) {
                arm.setPunchReturn(true);
                arm.setPunch(false);
                arm.setIntakePercent(0.0);
            } else {
                arm.setPunchReturn(false);
                arm.setIntakePercent(-1.0);
                arm.setPunch(true);
            }
        }
    }

    protected boolean isFinished() {
        return isTimedOut() || arm.getDartEncoderPosition() < Globals.Arm.ARM_OPEN_SOFT_LIMIT;
    }

    protected void end() {
        arm.setIntakePercent(0);
        arm.setPunch(false);
        arm.setPunchReturn(false);
        System.out.println("End Pressure: " + arm.getEstimatedPressure());
        System.out.println("This action consumed " + (pressure - arm.getEstimatedPressure()) + " psi");
    }
}
