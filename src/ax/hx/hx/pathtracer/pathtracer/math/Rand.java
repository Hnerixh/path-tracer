package ax.hx.hx.pathtracer.pathtracer.math;

import java.util.Random;

/**
 * This is a simple random number generator. It can only generate doubles in [0,1[.
 * It is not thread safe, and usage of this class in multiple threads could lead to race conditions.
 * However, because we only care about the numbers being *somewhat* random, and *somewhat* evenly distributed,
 * we don't care about the race conditions that are guaranteed to happen almost all the time.
 */
public class Rand {
    private static long seed = (new Random()).nextInt();
    public static double rand(){
            seed = (seed * 32719L + 3L) % 2147483648L;
            return ((double) ((seed + 1) % 2147483648L))/(2147483648.0);
    }

}
