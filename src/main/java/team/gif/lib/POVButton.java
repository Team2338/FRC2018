package team.gif.lib;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

public class POVButton extends Button {


    public enum Direction {
        NORTH(0),
        NORTHEAST(45),
        EAST(90),
        SOUTHEAST(135),
        SOUTH(180),
        SOUTHWEST(225),
        WEST(270),
        NORTHWEST(315),
        NEUTRAL(-1);

        private final int degree;
        Direction(int degree) { this.degree = degree; }
    }

    private final GenericHID joystick;
    private final Direction direction;

    public POVButton(GenericHID joystick, Direction direction) {
        this.joystick = joystick;
        this.direction = direction;
    }

    public boolean get() {
        return joystick.getPOV() == direction.degree;
    }
}