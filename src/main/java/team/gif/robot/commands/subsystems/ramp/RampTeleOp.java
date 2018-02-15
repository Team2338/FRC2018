package team.gif.robot.commands.subsystems.ramp;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.OI;
import team.gif.robot.subsystems.Ramp;

public class RampTeleOp extends Command {

    private Ramp ramp = Ramp.getInstance();

    public RampTeleOp() {
        requires(ramp);
    }

    protected void initialize() {

    }

    protected void execute() {
        ramp.set(OI.getInstance().aux.getY(GenericHID.Hand.kLeft));
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {

    }
}
