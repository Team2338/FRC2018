package team.gif.robot.commands.subsystem.drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;

import java.io.File;

public class CharacterizeDrivetrain extends CommandGroup {

    public CharacterizeDrivetrain() {
        addSequential(new DrivetrainConstantPercent(0.2, 3));
        addSequential(new WaitCommand(0.5));
        addSequential(new DrivetrainConstantPercent(-0.2, 3));
        addSequential(new WaitCommand(0.5));
        addSequential(new DrivetrainConstantPercent(0.3, 3));
        addSequential(new WaitCommand(0.5));
        addSequential(new DrivetrainConstantPercent(-0.3, 3));
        addSequential(new WaitCommand(0.5));
        addSequential(new DrivetrainConstantPercent(0.4, 3));
        addSequential(new WaitCommand(0.5));
        addSequential(new DrivetrainConstantPercent(-0.4, 3));
        addSequential(new WaitCommand(0.5));
        addSequential(new DrivetrainConstantPercent(0.45, 3));
        addSequential(new WaitCommand(0.5));
        addSequential(new DrivetrainConstantPercent(-0.45, 3));
        addSequential(new WaitCommand(0.5));
        addSequential(new DrivetrainConstantPercent(0.5, 3));
        addSequential(new WaitCommand(0.5));
        addSequential(new DrivetrainConstantPercent(-0.5, 3));
        addSequential(new WaitCommand(0.5));
    }

}
