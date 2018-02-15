package team.gif.lib;

public class GIFMath {

    /**
     * Limit motor values to the -1.0 to +1.0 range.
     */
    public static double limit(double value) {
        if (value > 1.0) {
            return 1.0;
        }
        if (value < -1.0) {
            return -1.0;
        }
        return value;
    }

    /**
     * Returns 0.0 if the given value is within the specified range around zero. The remaining range
     * between the deadband and 1.0 is scaled from 0.0 to 1.0.
     *
     * @param value    value to clip
     * @param deadband range around zero
     */
    public static double applyDeadband(double value, double deadband) {
        if (Math.abs(value) > deadband) {
            if (value > 0.0) {
                return (value - deadband) / (1.0 - deadband);
            } else {
                return (value + deadband) / (1.0 - deadband);
            }
        } else {
            return 0.0;
        }
    }

    /**
     * Re-maps a number from one range to another. That is, a value of {@code inMin} would get mapped to {@code outMin},
     * a value of {@code inMax} to {@code outMax}, values in-between to values in-between, etc.
     *
     * <p>Does not constrain values to within the range, because out-of-range values are sometimes intended and useful.
     * Note that the "lower bounds" of either range may be larger or smaller than the "upper bounds" so the
     * {@link #map(int, int, int, int, int)} function may be used to reverse a range of numbers, for example:
     *
     * <pre><code>
     *     y = map(x, 1, 50, 50, 1);
     * </code></pre>
     *
     * <p>The function also handles negative numbers well, so that this example
     *
     * <pre><code>
     *     y = map(x, 1, 50, 50, -100);
     * </code></pre>
     *
     * <p>is also valid and works well.
     *
     * <p>The {@link #map(int, int, int, int, int)} function uses integer math so will not generate fractions, when
     * the math might indicate that it should do so. Fractional remainders are truncated,
     * and are not rounded or averaged.
     *
     * @param value     the number to map
     * @param inMin     the lower bound of the value's current range
     * @param inMax     the upper bound of the value's current range
     * @param outMin    the lower bound of the value's target range
     * @param outMax    the upper bound of the value's target range
     * @return the mapped value
     */
    public static int map(int value, int inMin, int inMax, int outMin, int outMax) {
        return (value - inMin) * (outMax - outMin) / (inMax - inMin) + outMin;
    }
}
