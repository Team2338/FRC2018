package team.gif.robot.subsystems;

public class Ramps {
    private static Ramps instance = null;

    public static Ramps getInstance() {
        if (instance == null) {
            instance = new Ramps();
        }
        return instance;
    }

    private Ramps() {

    }
}
