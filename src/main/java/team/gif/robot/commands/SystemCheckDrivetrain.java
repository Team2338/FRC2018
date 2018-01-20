package team.gif.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team.gif.robot.OI;
import team.gif.robot.subsystems.Drivetrain;

public class SystemCheckDrivetrain extends Command {

    public SystemCheckDrivetrain () {
        requires(Drivetrain.getInstance());
    }

    protected void initialize() {
        boolean passed = Drivetrain.getInstance().checkSystem();
        System.out.println("Drivetrain OK: " + passed);
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {

    }
}
