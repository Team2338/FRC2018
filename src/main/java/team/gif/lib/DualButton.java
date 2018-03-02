package team.gif.lib;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

public class DualButton extends Button {

    private final GenericHID joystick;
    private final int firstButtonNumber;
    private final int secondButtonNumber;

    public DualButton(GenericHID joystick, int firstButtonNumber, int secondButtonNumber) {
        this.joystick = joystick;
        this.firstButtonNumber = firstButtonNumber;
        this.secondButtonNumber = secondButtonNumber;
    }

    public boolean get() {
        return joystick.getRawButton(firstButtonNumber) && joystick.getRawButton(secondButtonNumber);
    }
}