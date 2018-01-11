package team.gif.robot.subsystems;

public class Ramp {
    private static Ramp instance = null;

    public static Ramp getInstance() {
        if (instance == null) {
            instance = new Ramp();
        }
        return instance;
    }

    private Ramp() {

    }
}
