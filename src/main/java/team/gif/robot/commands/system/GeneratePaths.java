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

    private String path = "/home/lvuser/";

// SWITCH AUTO PATHS

    private Waypoint[] LEFT_TO_LEFT_SWITCH = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(108.812, 0, 0),
            new Waypoint(148.5, -39.688, Pathfinder.d2r(-90))
    };

    private Waypoint[] LEFT_TO_RIGHT_SWITCH = new Waypoint[] {
            new Waypoint(0, 0,0),
            new Waypoint(185.499, 0, 0),
            new Waypoint(225.187, -39.688, Pathfinder.d2r(-90)),
            new Waypoint(225.187, -142.401, Pathfinder.d2r(-90)),
            new Waypoint(209.0, -158.588, Pathfinder.d2r(-180)),
            new Waypoint(196.0, -158.588, Pathfinder.d2r(-180))
    };

    private Waypoint[] CENTER_TO_LEFT_SWITCH = new Waypoint[] {
            new Waypoint(0, 0, 0.0),
            new Waypoint( 101.0, 58.758, 0.0)
    };

    private Waypoint[] CENTER_TO_RIGHT_SWITCH = new Waypoint[] {
            new Waypoint(0, 0, 0.0),
            new Waypoint(101.0, -49.266, 0.0)
    };

    private Waypoint[] RIGHT_TO_LEFT_SWITCH = new Waypoint[] {
            new Waypoint(0, 0,0),
            new Waypoint(185.499, 0, 0),
            new Waypoint(225.187, 39.688, Pathfinder.d2r(90)),
            new Waypoint(225.187, 142.401, Pathfinder.d2r(90)),
            new Waypoint(209.0, 158.588, Pathfinder.d2r(180)),
            new Waypoint(196.0, 158.588, Pathfinder.d2r(180))
    };

    private Waypoint[] RIGHT_TO_RIGHT_SWITCH = new Waypoint[] {
        new Waypoint(0, 0, 0),
        new Waypoint(108.812, 0, 0),
        new Waypoint(148.5, 39.688, Pathfinder.d2r(90))
    };

// SCALE PATHS

    private Waypoint[] LEFT_TO_LEFT_SCALE = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(261.592, 0, 0),
            new Waypoint(287.655, 26.063, Pathfinder.d2r(90))
    };

    private Waypoint[] LEFT_TO_RIGHT_SCALE = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(185.529, 0, 0),
            new Waypoint(225.217, -39.688, Pathfinder.d2r(-90)),
            new Waypoint(225.217, -193.188, Pathfinder.d2r(-90)),
            new Waypoint(287.655, -258.938, Pathfinder.d2r(-90))
    };

    private Waypoint[] RIGHT_TO_RIGHT_SCALE = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(261.592, 0, 0),
            new Waypoint(287.655, -26.063, Pathfinder.d2r(-90))
    };

    private Waypoint[] RIGHT_TO_LEFT_SCALE = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(185.529, 0, 0),
            new Waypoint(225.217, 39.688, Pathfinder.d2r(90)),
            new Waypoint(225.217, 193.188, Pathfinder.d2r(90)),
            new Waypoint(287.655, 258.938, Pathfinder.d2r(90))
    };

// SWITCH + SCALE PATHS

//    private Waypoint[] LEFT_TO_LEFT_BACK_SWITCH = new Waypoint[] {
//            new Waypoint(0, 0,0),
//            new Waypoint(185.499, 0, 0),
//            new Waypoint(225.187, -39.688, Pathfinder.d2r(-90)),
//            new Waypoint(225.187, -58.101, Pathfinder.d2r(-90)),
//            new Waypoint(209.0, -74.288, Pathfinder.d2r(-180)),
//            new Waypoint(196.0, -74.288, Pathfinder.d2r(-180))
//    };
//
//    private Waypoint[] LEFT_TO_RIGHT_BACK_SWITCH = new Waypoint[] {
//            new Waypoint(0, 0,0),
//            new Waypoint(185.499, 0, 0),
//            new Waypoint(225.187, -39.688, Pathfinder.d2r(-90)),
//            new Waypoint(225.187, -142.401, Pathfinder.d2r(-90)),
//            new Waypoint(209.0, -158.588, Pathfinder.d2r(-180)),
//            new Waypoint(196.0, -158.588, Pathfinder.d2r(-180))
//    };
//
//    private Waypoint[] RIGHT_TO_LEFT_BACK_SWITCH = new Waypoint[] {
//            new Waypoint(0, 0,0),
//            new Waypoint(185.499, 0, 0),
//            new Waypoint(225.187, 39.688, Pathfinder.d2r(90)),
//            new Waypoint(225.187, 142.401, Pathfinder.d2r(90)),
//            new Waypoint(209.0, 158.588, Pathfinder.d2r(180)),
//            new Waypoint(196.0, 158.588, Pathfinder.d2r(180))
//    };
//
//    private Waypoint[] RIGHT_TO_RIGHT_BACK_SWITCH = new Waypoint[] {
//            new Waypoint(0, 0,0),
//            new Waypoint(185.499, 0, 0),
//            new Waypoint(225.187, 39.688, Pathfinder.d2r(90)),
//            new Waypoint(225.187, 58.101, Pathfinder.d2r(90)),
//            new Waypoint(209.0, 74.288, Pathfinder.d2r(180)),
//            new Waypoint(196.0, 74.288, Pathfinder.d2r(180))
//    };

    private Waypoint[] LEFT_SCALE_TO_LEFT_SWITCH = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(26.063, -26.063, Pathfinder.d2r(-90)),
            new Waypoint(72.25, -78.655, Pathfinder.d2r(-90))
    };

    private Waypoint[] LEFT_SCALE_TO_RIGHT_SWITCH = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(65.751, -62.438, 0),
            new Waypoint(196.553, -62.438, 0),
            new Waypoint(212.75, -78.655, Pathfinder.d2r(-90))
    };

    private Waypoint[] RIGHT_SCALE_TO_LEFT_SWITCH = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(65.751, 62.438, 0),
            new Waypoint(196.553, 62.438, 0),
            new Waypoint(212.75, 78.655, Pathfinder.d2r(90))
    };

    private Waypoint[] RIGHT_SCALE_TO_RIGHT_SWITCH = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(26.063, 26.063, Pathfinder.d2r(90)),
            new Waypoint(72.25, 78.655, Pathfinder.d2r(90))
    };

// MULTIPURPOSE PATHS

    private Waypoint[] THREE_FEET = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(36, 0, 0)
    };

    private Waypoint[] FIVE_FEET = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(60, 0, 0)
    };

    private Waypoint[] EIGHT_FEET = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(96, 0, 0)
    };

    private Waypoint[] TEN_FEET = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(120, 0, 0)
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
        System.out.println("Beginning: scale to switch");

        Pathfinder.writeToCSV(new File(path + "leftscaletoleftswitch.csv"), Pathfinder.generate(LEFT_SCALE_TO_LEFT_SWITCH, config));
        Pathfinder.writeToCSV(new File(path + "leftscaletorightswitch.csv"), Pathfinder.generate(LEFT_SCALE_TO_RIGHT_SWITCH, config));
        Pathfinder.writeToCSV(new File(path + "rightscaletoleftswitch.csv"), Pathfinder.generate(RIGHT_SCALE_TO_LEFT_SWITCH, config));
        Pathfinder.writeToCSV(new File(path + "rightscaletorightswitch.csv"), Pathfinder.generate(RIGHT_SCALE_TO_RIGHT_SWITCH, config));


        System.out.println("Finished: scale to switch at: " + timeSinceInitialized() + "s");
        System.out.println("Beginning: multipurpose");

        Pathfinder.writeToCSV(new File(path + "threefeet.csv"), Pathfinder.generate(THREE_FEET, config));
        Pathfinder.writeToCSV(new File(path + "fivefeet.csv"), Pathfinder.generate(FIVE_FEET, config));
        Pathfinder.writeToCSV(new File(path + "eightfeet.csv"), Pathfinder.generate(EIGHT_FEET, config));
        Pathfinder.writeToCSV(new File(path + "tenfeet.csv"), Pathfinder.generate(TEN_FEET, config));

        System.out.println("Finished path generation in: " + timeSinceInitialized() + "s");
    }
}
