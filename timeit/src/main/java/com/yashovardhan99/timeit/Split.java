/*
 *    Copyright 2018  Yashovardhan Dhanania
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

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