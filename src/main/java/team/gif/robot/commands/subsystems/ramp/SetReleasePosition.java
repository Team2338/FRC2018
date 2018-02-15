package team.gif.robot.commands.subsystems.ramp;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team.gif.robot.subsystems.Ramp;

public class SetReleasePosition extends Command {

    private Ramp ramp = Ramp.getInstance();


    public SetReleasePosition() {
        SmartDashboard.putNumber("Servo Position", 0.5);
    }

    protected void initialize() {
        ramp.setReleasePosition(SmartDashboard.getNumber("Servo Position", 0.5));
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {

    }
}
