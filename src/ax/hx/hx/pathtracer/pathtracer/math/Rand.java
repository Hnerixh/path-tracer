package ax.hx.hx.pathtracer.pathtracer.math;

/**
 * This is a simple random number generator. It can only generate doubles in [0,1[.
 * It is not thread safe, and usage of this class in multiple threads could lead to race conditions.
 * However, because we only care about the numbers being *somewhat* random, and *somewhat* evenly distributed,
 * we don't care about the race conditions that are guaranteed to happen almost all the time.
 */
public class Rand {
    private static long seed = 42;
    // The multiplier and incrementation are the same as in glibc.
    //
    // Also, because a call only only results in one addition,
    // one multiplication, one modulo and one division
    // this should be rather fast.
    public static double rand(){
            seed = (seed * 1103515245 + 12345) % 2147483648L;
            return ((double) (seed))/2147483648.0;
    }
}
