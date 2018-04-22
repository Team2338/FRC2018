package team.gif.robot.commands.subsystem.arm;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import team.gif.robot.Globals;
import team.gif.robot.subsystems.Arm;

public class ArmSetPosition extends Command {

    private Arm arm = Arm.getInstance();
    private int position;

    public ArmSetPosition(int position) {
//        requires(arm);
        this.position = position;
    }

    protected void execute() {
        arm.setDartPosition(position);
        if (arm.hasCube() && this.position < Globals.Arm.ARM_TRAVEL_POSITION) {
            arm.setDartPosition(Globals.Arm.ARM_TRAVEL_POSITION);
        }
    }

    @Override
    protected boolean isFinished() {
        return arm.movementFinished();
    }
}
