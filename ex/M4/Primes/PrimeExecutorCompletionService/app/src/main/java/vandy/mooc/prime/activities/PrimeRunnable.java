package vandy.mooc.prime.activities;

import android.util.Log;

import java.util.function.Function;

import vandy.mooc.prime.utils.Memoizer;

/**
 * Uses a brute-force algorithm to determine if a given number is
 * prime or not.
 */
public class PrimeRunnable
       implements Runnable {
    /**
     * Debugging tag used by the Android logger.
     */
    private final String TAG =
        getClass().getSimpleName();

    /**
     * A reference to the MainActivity. 
     */
    private final MainActivity mActivity;

    /** 
     * Number to evaluate for "primality".
     */
    private final long mPrimeCandidate;

    /**
     * This method provides a brute-force determination of whether
     * number @a n is prime.  Returns 0 if it is prime, or the
     * smallest factor if it is not prime.
     */
    private static final Function<Long, Long> sPrimeChecker =
            new Function<Long, Long>() {
                public Long apply(Long n) {
                    if (n > 3)
                        for (long factor = 2;
                             factor <= n / 2;
                             ++factor)
                            if (n / factor * factor == n)
                                return factor;

                    return 0L;
                }
            };
    /**
     * Cache used to store prime computations.
     */
    private static final Function<Long, Long> mCache =
            new Memoizer<>(sPrimeChecker);

    /**
     * Constructor initializes the fields.
     */
    public PrimeRunnable(MainActivity activity,
                         long primeCandidate) {
        mActivity = activity;
        mPrimeCandidate = primeCandidate;
    }
    
    /**
     * Hook method that determines if a given number is prime.
     */
    public void run() {
        // Determine if mPrimeCandidate is prime or not.
        long smallestFactor = mCache.apply(mPrimeCandidate); 

        if (smallestFactor != 0)
            mActivity.println(""
                              + mPrimeCandidate
                              + " is not prime with smallest factor "
                              + smallestFactor);
        else
            mActivity.println(""
                              + mPrimeCandidate
                              + " is prime");

        // Tell the activity we're done.
        mActivity.done();
    }
}
