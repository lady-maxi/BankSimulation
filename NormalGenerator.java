import java.util.Random;

/**
 * Générateur de variables aléatoires de loi normale.
 */
public class NormalGenerator {
    private static final Random rand = new Random();

    public static double generate(double mean, double stdDev) {
        return mean + stdDev * rand.nextGaussian();
    }
}