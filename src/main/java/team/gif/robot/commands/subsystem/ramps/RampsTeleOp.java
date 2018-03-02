package team.gif.robot.commands.subsystem.ramps;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.OI;
import team.gif.robot.subsystems.Ramps;

public class RampsTeleOp extends Command {

    private Ramps ramps = Ramps.getInstance();

    public RampsTeleOp() {
        requires(ramps);
    }

    protected void initialize() {

    }

    protected void execute() {
        if (ramps.areDeployed()) {
            if (OI.getInstance().aux.getBumper(GenericHID.Hand.kRight)){
                ramps.setLeft(-OI.getInstance().aux.getRawAxis(3));
            } else if (OI.getInstance().aux.getRawAxis(3) > 0.8 && !ramps.getLeftLimit()) {
                ramps.setLeft(1.0);
            } else {
                ramps.setLeft(0.0);
            }

            if (OI.getInstance().aux.getBumper(GenericHID.Hand.kLeft)){
                ramps.setRight(-OI.getInstance().aux.getRawAxis(2));
            } else if (OI.getInstance().aux.getRawAxis(2) > 0.8 && !ramps.getRightLimit()) {
                ramps.setRight(1.0);
            } else {
                ramps.setRight(0.0);
            }
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        ramps.setLeft(0.0);
        ramps.setRight(0.0);
    }
}
