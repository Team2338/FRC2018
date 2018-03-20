package team.gif.robot.commands.system;

import edu.wpi.first.wpilibj.command.InstantCommand;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import team.gif.robot.Globals;

import java.io.File;

public class GeneratePaths extends InstantCommand {

    private Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
            Trajectory.Config.SAMPLES_HIGH, Globals.Drivetrain.TIME_STEP, Globals.Drivetrain.MAX_VELOCITY,
            Globals.Drivetrain.MAX_ACCELERATION, Globals.Drivetrain.MAX_JERK);
    private Trajectory.Config slowConfig = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
            Trajectory.Config.SAMPLES_HIGH, Globals.Drivetrain.TIME_STEP, 0.75,
            Globals.Drivetrain.MAX_ACCELERATION, Globals.Drivetrain.MAX_JERK);

    private String path = "/home/lvuser/";

// SWITCH AUTO PATHS

    private Waypoint[] LEFT_TO_LEFT_SWITCH = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(2.565, 0.135, 0),
            new Waypoint(3.772, -0.491, Pathfinder.d2r(-90))
    };

    private Waypoint[] LEFT_TO_RIGHT_SWITCH = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(3.772, 0, 0),
            new Waypoint(5.48, -1.151, Pathfinder.d2r(-90)),
            new Waypoint(5.48, -1.25, Pathfinder.d2r(-90)),
            new Waypoint(5.48, -3.25 + 0.2, Pathfinder.d2r(-90)),
            new Waypoint(4.57, -4.17 + 0.2, Pathfinder.d2r(-135))
    };

    private Waypoint[] CENTER_TO_LEFT_SWITCH = new Waypoint[] {
            new Waypoint(0, 0, 0.0),
            new Waypoint( 2.565, 1.5, 0.0)
    };

    private Waypoint[] CENTER_TO_RIGHT_SWITCH = new Waypoint[] {
            new Waypoint(0, 0, 0.0),
            new Waypoint(2.565, -1.243, 0.0)
    };

    private Waypoint[] RIGHT_TO_LEFT_SWITCH = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(3.772, 0, 0),
            new Waypoint(5.48, 1.151, Pathfinder.d2r(90)),
            new Waypoint(5.48, 1.25, Pathfinder.d2r(90)),
            new Waypoint(5.48, 3.25, Pathfinder.d2r(90)),
            new Waypoint(4.57, 4.17, Pathfinder.d2r(135))
    };

    private Waypoint[] RIGHT_TO_RIGHT_SWITCH = new Waypoint[] {
        new Waypoint(0, 0, 0),
        new Waypoint(2.565, -0.135, 0),
        new Waypoint(3.772, 0.491, Pathfinder.d2r(90))
    };

// SCALE AUTO PATHS

    private Waypoint[] LEFT_TO_LEFT_SCALE = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(4.26, 0.5, 0),
            new Waypoint(6.73, 0.5, 0),
            new Waypoint(7.73, -0.5, Pathfinder.d2r(-90))
    };

    private Waypoint[] LEFT_TO_RIGHT_SCALE = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(3.772, 0, 0),
            new Waypoint(5.48, -1.151, Pathfinder.d2r(-90)),
            new Waypoint(5.48, -1.25, Pathfinder.d2r(-90)),
            new Waypoint(5.48, -3.25 + 0.2, Pathfinder.d2r(-90))
    };

    private Waypoint[] RIGHT_TO_LEFT_SCALE = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(3.772, 0, 0),
            new Waypoint(5.48, 1.151, Pathfinder.d2r(90)),
            new Waypoint(5.48, 1.25, Pathfinder.d2r(90)),
            new Waypoint(5.48, 3.25, Pathfinder.d2r(90))

    };

    private Waypoint[] RIGHT_TO_RIGHT_SCALE = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(4.26, -0.5, 0),
            new Waypoint(6.73, -0.5, 0),
            new Waypoint(7.73, 0.5, Pathfinder.d2r(90))
    };

// SWITCH + SCALE PATHS

    private Waypoint[] LEFT_TO_LEFT_BACK_SWITCH = new Waypoint[] {
            new Waypoint(0, 0,0),
            new Waypoint(185.499, 0, 0),
            new Waypoint(225.187, -39.688, Pathfinder.d2r(-90)),
            new Waypoint(225.187, -58.101, Pathfinder.d2r(-90)),
            new Waypoint(209.0, -74.288, Pathfinder.d2r(-180)),
            new Waypoint(196.0, -74.288, Pathfinder.d2r(-180))
    };

    private Waypoint[] LEFT_TO_RIGHT_BACK_SWITCH = new Waypoint[] {
            new Waypoint(0, 0,0),
            new Waypoint(185.499, 0, 0),
            new Waypoint(225.187, -39.688, Pathfinder.d2r(-90)),
            new Waypoint(225.187, -142.401, Pathfinder.d2r(-90)),
            new Waypoint(209.0, -158.588, Pathfinder.d2r(-180)),
            new Waypoint(196.0, -158.588, Pathfinder.d2r(-180))
    };

    private Waypoint[] RIGHT_TO_LEFT_BACK_SWITCH = new Waypoint[] {
            new Waypoint(0, 0,0),
            new Waypoint(185.499, 0, 0),
            new Waypoint(225.187, 39.688, Pathfinder.d2r(90)),
            new Waypoint(225.187, 142.401, Pathfinder.d2r(90)),
            new Waypoint(209.0, 158.588, Pathfinder.d2r(180)),
            new Waypoint(196.0, 158.588, Pathfinder.d2r(180))
    };

    private Waypoint[] RIGHT_TO_RIGHT_BACK_SWITCH = new Waypoint[] {
            new Waypoint(0, 0,0),
            new Waypoint(185.499, 0, 0),
            new Waypoint(225.187, 39.688, Pathfinder.d2r(90)),
            new Waypoint(225.187, 58.101, Pathfinder.d2r(90)),
            new Waypoint(209.0, 74.288, Pathfinder.d2r(180)),
            new Waypoint(196.0, 74.288, Pathfinder.d2r(180))
    };

    private Waypoint[] LEFT_BACK_SWITCH_TO_LEFT_SCALE = new Waypoint[] {
            new Waypoint(0, 0, 0)
    };

    private Waypoint[] LEFT_SCALE_TO_RIGHT_BACK_SWITCH = new Waypoint[] {
            new Waypoint(0, 0, 0)
    };

    private Waypoint[] RIGHT_SCALE_TO_LEFT_BACK_SWITCH = new Waypoint[] {
            new Waypoint(0, 0, 0)
    };

    private Waypoint[] RIGHT_BACK_SWITCH_TO_LEFT_SCALE = new Waypoint[] {
            new Waypoint(0, 0, 0)
    };

    private Waypoint[] RIGHT_BACK_SWITCH_TO_RIGHT_SCALE = new Waypoint[] {
            new Waypoint(0, 0, 0)
    };

// MULTIPURPOSE PATHS

    private Waypoint[] ONE_METER = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(1, 0, 0)
    };

    private Waypoint[] TWO_METER = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(2, 0, 0)
    };

    private Waypoint[] THREE_METER = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(3, 0, 0)
    };

    private Waypoint[] FIVE_METER = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(5, 0, 0)
    };

    protected void execute() {
        System.out.println("Beginning path generation");
        System.out.println("Beginning: start to switch");

        Pathfinder.writeToCSV(new File(path + "lefttoleftswitch.csv"), Pathfinder.generate(LEFT_TO_LEFT_SWITCH, config));
        Pathfinder.writeToCSV(new File(path + "lefttorightswitch.csv"), Pathfinder.generate(LEFT_TO_RIGHT_SWITCH, config));
        Pathfinder.writeToCSV(new File(path + "centertoleftswitch.csv"), Pathfinder.generate(CENTER_TO_LEFT_SWITCH, config));
        Pathfinder.writeToCSV(new File(path + "centertorightswitch.csv"), Pathfinder.generate(CENTER_TO_RIGHT_SWITCH, config));
        Pathfinder.writeToCSV(new File(path + "righttoleftswitch.csv"), Pathfinder.generate(RIGHT_TO_LEFT_SWITCH, config));
        Pathfinder.writeToCSV(new File(path + "righttorightswitch.csv"), Pathfinder.generate(RIGHT_TO_RIGHT_SWITCH, config));

        System.out.println("Finished: start to switch at: " + timeSinceInitialized() + "s");
        System.out.println("Beginning: start to scale");

        Pathfinder.writeToCSV(new File(path + "lefttoleftscale.csv"), Pathfinder.generate(LEFT_TO_LEFT_SCALE, config));
        Pathfinder.writeToCSV(new File(path + "lefttorightscale.csv"), Pathfinder.generate(LEFT_TO_RIGHT_SCALE, config));
        Pathfinder.writeToCSV(new File(path + "righttoleftscale.csv"), Pathfinder.generate(RIGHT_TO_LEFT_SCALE, config));
        Pathfinder.writeToCSV(new File(path + "righttorightscale.csv"), Pathfinder.generate(RIGHT_TO_RIGHT_SCALE, config));

        System.out.println("Finished: start to scale at: " + timeSinceInitialized() + "s");
        System.out.println("Beginning: switch to scale");

        Pathfinder.writeToCSV(new File(path + "leftswitchtoleftscale.csv"), Pathfinder.generate(LEFT_BACK_SWITCH_TO_LEFT_SCALE, config));
        Pathfinder.writeToCSV(new File(path + "rightswitchtoleftscale.csv"), Pathfinder.generate(RIGHT_BACK_SWITCH_TO_LEFT_SCALE, config));
        Pathfinder.writeToCSV(new File(path + "rightswitchtorightscale.csv"), Pathfinder.generate(RIGHT_BACK_SWITCH_TO_RIGHT_SCALE, config));

        System.out.println("Finished: switch to scale at: " + timeSinceInitialized() + "s");
        System.out.println("Beginning: multipurpose");

        Pathfinder.writeToCSV(new File(path + "onemeter.csv"), Pathfinder.generate(ONE_METER, config));
        Pathfinder.writeToCSV(new File(path + "twometer.csv"), Pathfinder.generate(TWO_METER, config));
        Pathfinder.writeToCSV(new File(path + "threemeter.csv"), Pathfinder.generate(THREE_METER, config));
        Pathfinder.writeToCSV(new File(path + "fivemeter.csv"), Pathfinder.generate(FIVE_METER, config));
        Pathfinder.writeToCSV(new File(path + "fivemeterslow.csv"), Pathfinder.generate(FIVE_METER, slowConfig));

        System.out.println("Finished path generation in: " + timeSinceInitialized() + "s");
    }
}
