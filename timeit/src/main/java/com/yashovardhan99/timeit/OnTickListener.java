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
 * Interface to listen for tick events every time clock is updated. Useful for scenarios where you want to do more than update a textField based on time.
 * Created by Yashovardhan99 on 10/12/18 as a part of TimeIt.
 * @author Yashovardhan Dhanania
 * @version 1.0
 * @see Stopwatch
 */
public interface OnTickListener {
    /**
     * Called every time the clock 'ticks'. The stopwatch ticks after a delay of 100ms (or as specified).
     *
     * @param stopwatch Reference to the currently calling stopwatch.
     */
    void onTick(Stopwatch stopwatch);
}
