package team.gif.robot.commands.subsystem.ramps;

import edu.wpi.first.wpilibj.command.Command;
import team.gif.robot.OI;
import team.gif.robot.subsystems.Ramps;

public class RampsLift extends Command {

    private Ramps ramps = Ramps.getInstance();

    public enum RampSide {
        LEFT,
        RIGHT
    }

    private RampSide side;

    public RampsLift(RampSide side) {
        this.side = side;
    }

    protected void initialize() {

    }

    protected void execute() {
        double speed = 1.0;
        if (ramps.areDeployed()) {
            if (side.equals(RampSide.LEFT)) {
                if (OI.getInstance().aux.getXButton()) {
                    ramps.setLeft(-1.0);
                } else {
                    ramps.setLeft(ramps.getLeftLimit() ? 0 : speed);
                }
            } else {
                if (OI.getInstance().aux.getXButton()) {
                    ramps.setRight(-1.0);
                } else {
                    ramps.setRight(ramps.getRightLimit() ? 0 : speed);
                }
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
