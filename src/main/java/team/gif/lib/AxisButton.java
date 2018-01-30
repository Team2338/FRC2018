package team.gif.lib;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

public class AxisButton extends Button {

    private final GenericHID joystick;
    private final int axisNumber;
    private final double limit;

    public AxisButton(GenericHID joystick, int axisNumber, double limit) {
        this.joystick = joystick;
        this.axisNumber = axisNumber;
        this.limit = limit;
    }

    public boolean get() { return joystick.getRawAxis(axisNumber) > limit; }
}