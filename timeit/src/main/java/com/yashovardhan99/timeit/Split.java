package com.yashovardhan99.timeit;

/**
 * Created by Yashovardhan99 on 8/12/18 as a part of TimeIt.
 * This is a utility class for the main Stopwatch class to help create and save splits and laps.
 * @author Yashovardhan Dhanania
 * @see Stopwatch
 * @see Stopwatch#split()
 */
public class Split {
    private long splitTime, lapTime;

    /**
     * @param splitTime the time in milliseconds for which stopwatch has been running
     * @param lapTime the time in milliseconds since the last split/lap
     */
    public Split(long splitTime, long lapTime){
        this.splitTime = splitTime;
        this.lapTime = lapTime;
    }

    /**
     * @return the time in milliseconds between this and the last split/lap
     * @see Stopwatch#split()
     */
    public long getLapTime() {
        return lapTime;
    }

    /**
     * @return the time in milliseconds since the stopwatch was running at the instant this split was created.
     * @see Stopwatch#split()
     */
    public long getSplitTime() {
        return splitTime;
    }
}