package se.liu.ida.tddd78.henhe459.pathtracer.pathtracer.math;

/**
 * This is a simple random number generator. It can only generate doubles in [0,1[.
 * It is not thread safe, and usage of this class in multiple threads could/should lead to race conditions.
 * However, because we only care about the numbers being *somewhat* random, and *somewhat* evenly distributed
 * we don't care about the race conditions that are guaranteed to happen almost all the time.
 */
public final class RandomGen
{
    // Use randomized number for seed.
    // http://xkcd.com/221/
    private static long seed = 4; // IDEA ANTIWARNING seed might be short, but it is actually the best name for this variable.

    private static final long MOD = 2147483648L; // Modulo
    private static final double DOUBLE_MOD = 1 / 2147483648.0; // Modulo in double // IDEA ANTIWARNING that's not a magic number...
    private static final long MULTIPLIER = 1103515245L; // Multiplication factor
    private static final long OFFSET = 12345L; // Incrementation size
    // The multiplier and incrementation are the same as in glibc.
    // However, this implementation uses all the bits of the result,
    // instead of dismissing the most significant two bits, as glibc does.
    public static double rand(){
            seed = (seed * MULTIPLIER + OFFSET) % MOD;
            return seed * DOUBLE_MOD;
    }
    private RandomGen() {}
}
