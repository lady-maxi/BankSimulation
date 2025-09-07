import java.util.Random;

/**
 * Générateur de variables aléatoires de loi lognormale.
 */
public class LognormalGenerator {
    private static final Random rand = new Random();

    public static double generate(double mean, double stdDev) {
    if (mean <= 0 || stdDev <= 0) {
        return 0.0;
    }
    double variance = stdDev * stdDev;
    double mu = Math.log(mean) - 0.5 * Math.log(1 + variance / (mean * mean));
    double sigma = Math.sqrt(Math.log(1 + variance / (mean * mean)));
    return Math.exp(mu + sigma * rand.nextGaussian());
}
}