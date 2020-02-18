package forfun.sandbox.uwns.shared.math;

import java.util.Random;
import org.dyn4j.geometry.Vector2;

public class Mathext {

    private static final Random randomizer = new Random();

    public static double doubleRandomRange(double min, double max) {
        return (max - min) * randomizer.nextDouble() + min;
    }

    public static Vector2 doubleRandomCircle(double radius) {
        double distance = Math.sqrt(doubleRandomRange(0.0, 1.0)) * radius;
        double theta = doubleRandomRange(0, 2 * Math.PI);
        double x = Math.cos(theta) * distance;
        double y = Math.sin(theta) * distance;
        return new Vector2(x, y);
    }

}
