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

    private Trajectory.Config fastConfig = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
            Trajectory.Config.SAMPLES_HIGH, Globals.Drivetrain.TIME_STEP, 36,
            36, Globals.Drivetrain.MAX_JERK);

    private String path = "/home/lvuser/";

// SWITCH AUTO PATHS

    private Waypoint[] LEFT_TO_LEFT_SWITCH = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(124.5, 0, 0),
            new Waypoint(148.5, -24, Pathfinder.d2r(-90))

    };

    private Waypoint[] RIGHT_TO_RIGHT_SWITCH = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(124.5, 0, 0),
            new Waypoint(148.5, 24, Pathfinder.d2r(90))
    };

    private Waypoint[] CENTER_TO_LEFT_SWITCH = new Waypoint[] {
            new Waypoint(0, 0, 0.0),
            new Waypoint( 101.0, 58.758, 0.0)
    };


    private Waypoint[] CENTER_TO_RIGHT_SWITCH = new Waypoint[] {
            new Waypoint(0, 0, 0.0),
            new Waypoint(99.0, -49.266, 0.0)
    };

    private Waypoint[] LEFT_SIDE_SWITCH_WALL_TO_CENTER = new Waypoint[] {
            new Waypoint(0, 0, 0.0),
            new Waypoint( 24.0, -24.0, Pathfinder.d2r(-45))
    };

    private Waypoint[] RIGHT_SIDE_SWITCH_WALL_TO_CENTER = new Waypoint[] {
            new Waypoint(0, 0, 0.0),
            new Waypoint( 24.0, 24.0, Pathfinder.d2r(45))
    };

// SCALE PATHS

    private Waypoint[] LEFT_TO_LEFT_SCALE = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(120, 14, 0),
            new Waypoint(249, 14, 0),
            new Waypoint(249 + 8.22, 14 - (11.625 - 8.22), Pathfinder.d2r(-45))
    };

    private Waypoint[] LEFT_TO_RIGHT_SCALE = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(174.375, 0, 0),
            new Waypoint(213.75, -39.375, Pathfinder.d2r(-90)),
            new Waypoint(213.75, -244 - 6, Pathfinder.d2r(-90)),
            new Waypoint(213.75 + 18, -244 - 18 - 6, 0),
            new Waypoint(213.75 + 18 + 22, -244 - 18 + 7 - 6, Pathfinder.d2r(45)),
//            new Waypoint(255 - 14, -242 - 12, Pathfinder.d2r(45))
    };

    private Waypoint[] RIGHT_TO_RIGHT_SCALE = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(120, -14, 0),
            new Waypoint(249, -14, 0),
            new Waypoint(249 + 8.22, -(14 - (11.625 - 8.22)), Pathfinder.d2r(45))
    };

    private Waypoint[] RIGHT_TO_LEFT_SCALE = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(174.375, 0, 0),
            new Waypoint(213.75, 39.375, Pathfinder.d2r(90)),
            new Waypoint(213.75, 244 + 6, Pathfinder.d2r(90)),
            new Waypoint(213.75 + 18, 244 + 18 + 6, 0),
            new Waypoint(213.75 + 18 + 22, 244 + 18 - 7 + 6, Pathfinder.d2r(-45)),
    };

// SWITCH + SCALE PATHS

    private Waypoint[] LEFT_SCALE_TO_LEFT_SWITCH_FROM_LEFT = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(12, -12, Pathfinder.d2r(-90)),
            new Waypoint(12, -48, Pathfinder.d2r(-90))
    };

    private Waypoint[] LEFT_SCALE_TO_LEFT_SWITCH_FROM_RIGHT = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(12, -12, Pathfinder.d2r(-90)),
            new Waypoint(12, -48, Pathfinder.d2r(-110))
    };

    private Waypoint[] RIGHT_SCALE_TO_RIGHT_SWITCH_FROM_LEFT = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(12, 12, Pathfinder.d2r(90)),
            new Waypoint(12, 48, Pathfinder.d2r(110))
    };

    private Waypoint[] RIGHT_SCALE_TO_RIGHT_SWITCH_FROM_RIGHT = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(12, 12, Pathfinder.d2r(90)),
            new Waypoint(12, 48, Pathfinder.d2r(90))
    };

    private Waypoint[] LEFT_SCALE_TO_SAFE_AREA = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(12, -12, Pathfinder.d2r(-90)),
            new Waypoint(0, -24, Pathfinder.d2r(-135)), // gets it facing starting wall
            new Waypoint(-60, -24-60, Pathfinder.d2r(-135)) // moves it forward toward wall to switch side
            // this always end up with an angle error of ~12. When disabled and enabled, it turns 180 and then it's fine.
            // Need to look into this.
    };

    private Waypoint[] RIGHT_SCALE_TO_SAFE_AREA = new Waypoint[] {
            // needs testing
            new Waypoint(0, 0, 0),
            new Waypoint(12, 12, Pathfinder.d2r(90)),
            new Waypoint(0, 24, Pathfinder.d2r(135)), // gets it facing starting wall
            new Waypoint(-60, 24+60, Pathfinder.d2r(135)) // moves it forward toward wall to switch side
            // See LEFT_SCALE_TO_SAFE_AREA comment
            // May end up with an angle error of ~12
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

    private Waypoint[] SIXTEEN_FEET = new Waypoint[] {
            new Waypoint(0, 0, 0),
            new Waypoint(192, 0, 0)
    };

    protected void execute() {
        System.out.println("Beginning path generation");
        System.out.println("Beginning: start to switch");

        Pathfinder.writeToCSV(new File(path + "lefttoleftswitch.csv"), Pathfinder.generate(LEFT_TO_LEFT_SWITCH, config));
        Pathfinder.writeToCSV(new File(path + "centertoleftswitch.csv"), Pathfinder.generate(CENTER_TO_LEFT_SWITCH, config));
        Pathfinder.writeToCSV(new File(path + "centertorightswitch.csv"), Pathfinder.generate(CENTER_TO_RIGHT_SWITCH, config));
        Pathfinder.writeToCSV(new File(path + "righttorightswitch.csv"), Pathfinder.generate(RIGHT_TO_RIGHT_SWITCH, config));
        Pathfinder.writeToCSV(new File(path + "leftsideswitchwalltocenter.csv"), Pathfinder.generate(LEFT_SIDE_SWITCH_WALL_TO_CENTER, config));
        Pathfinder.writeToCSV(new File(path + "rightsideswitchwalltocenter.csv"), Pathfinder.generate(RIGHT_SIDE_SWITCH_WALL_TO_CENTER, config));

        System.out.println("Finished: start to switch at: " + timeSinceInitialized() + "s");
        System.out.println("Beginning: start to scale");

        Pathfinder.writeToCSV(new File(path + "lefttoleftscale.csv"), Pathfinder.generate(LEFT_TO_LEFT_SCALE, config));
        Pathfinder.writeToCSV(new File(path + "lefttorightscale.csv"), Pathfinder.generate(LEFT_TO_RIGHT_SCALE, config));
        Pathfinder.writeToCSV(new File(path + "righttoleftscale.csv"), Pathfinder.generate(RIGHT_TO_LEFT_SCALE, config));
        Pathfinder.writeToCSV(new File(path + "righttorightscale.csv"), Pathfinder.generate(RIGHT_TO_RIGHT_SCALE, config));

        System.out.println("Finished: start to scale at: " + timeSinceInitialized() + "s");
        System.out.println("Beginning: scale to switch");

        Pathfinder.writeToCSV(new File(path + "leftscaletoleftswitchfromleft.csv"), Pathfinder.generate(LEFT_SCALE_TO_LEFT_SWITCH_FROM_LEFT, config));
        Pathfinder.writeToCSV(new File(path + "leftscaletoleftswitchfromright.csv"), Pathfinder.generate(LEFT_SCALE_TO_LEFT_SWITCH_FROM_RIGHT, config));
        Pathfinder.writeToCSV(new File(path + "rightscaletorightswitchfromleft.csv"), Pathfinder.generate(RIGHT_SCALE_TO_RIGHT_SWITCH_FROM_LEFT, config));
        Pathfinder.writeToCSV(new File(path + "rightscaletorightswitchfromright.csv"), Pathfinder.generate(RIGHT_SCALE_TO_RIGHT_SWITCH_FROM_RIGHT, config));
        Pathfinder.writeToCSV(new File(path + "LeftScaleToSafeArea.csv"), Pathfinder.generate(LEFT_SCALE_TO_SAFE_AREA, config));
        Pathfinder.writeToCSV(new File(path + "RightScaleToSafeArea.csv"), Pathfinder.generate(RIGHT_SCALE_TO_SAFE_AREA, config));


        System.out.println("Finished: scale to switch at: " + timeSinceInitialized() + "s");
        System.out.println("Beginning: multipurpose");

        Pathfinder.writeToCSV(new File(path + "threefeet.csv"), Pathfinder.generate(THREE_FEET, config));
        Pathfinder.writeToCSV(new File(path + "fivefeet.csv"), Pathfinder.generate(FIVE_FEET, config));
        Pathfinder.writeToCSV(new File(path + "eightfeet.csv"), Pathfinder.generate(EIGHT_FEET, config));
        Pathfinder.writeToCSV(new File(path + "tenfeet.csv"), Pathfinder.generate(TEN_FEET, config));
        Pathfinder.writeToCSV(new File(path + "sixteenfeet.csv"), Pathfinder.generate(SIXTEEN_FEET, config));

        System.out.println("Finished path generation in: " + timeSinceInitialized() + "s");
    }
}
