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

import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;

import androidx.annotation.Nullable;

/**
 * The Stopwatch class is used for creating and using a simple stopwatch with basic features like : start, pause, resume and split.
 * It allows you to send a TextView and automatically updates it every 0.1 seconds (or as set by you).
 * You can also implement the custom OnTickListener to listen for time changes every time period.
 * Threading on a separate thread is handled by the class itself. You just need to call appropriate methods to control the stopwatch.
 * <p>
 * Created by Yashovardhan99 on 8/12/18 as a part of TimeIt.
 *
 * @author Yashovardhan Dhanania
 * @version 1.1
 * @see java.lang.Runnable
 */
public class Stopwatch {
    private LinkedList<Split> splits;
    private TextView textView;
    private long start, current, elapsedTime, lapTime;
    private boolean started, paused, logEnabled;
    private OnTickListener onTickListener;
    private long clockDelay;
    private Handler handler;

    /**
     * The runnable used to call the thread.
     * @since 1.1
     */
    private final Runnable runnable = this::run;

    /**
     * The default constructor should be called to create an object to call functions accordingly.
     * @since 1.0
     */
    public Stopwatch() {
        start = System.currentTimeMillis();
        current = System.currentTimeMillis();
        elapsedTime = 0;
        started = false;
        paused = false;
        logEnabled = false;
        splits = new LinkedList<>();
        textView = null;
        lapTime = 0;
        onTickListener = null;
        clockDelay = 100;
        handler = new Handler();
    }

    /**
     * Formats time in either of the following formats depending on time passed - SS.ss, MM:SS.ss, HH:MM:SS.
     *
     * @since 1.1
     * @param elapsedTime time in milliseconds which has to be formatted
     * @return formatted time in String form
     */
    protected static String getFormattedTime(long elapsedTime) {
        final StringBuilder displayTime = new StringBuilder();

        int milliseconds = (int) ((elapsedTime % 1000) / 10);
        int seconds = (int) ((elapsedTime / 1000) % 60);
        int minutes = (int) (elapsedTime / (60 * 1000) % 60);
        int hours = (int) (elapsedTime / (60 * 60 * 1000));

        NumberFormat f = new DecimalFormat("00");

        if (minutes == 0)
            displayTime.append(f.format(seconds)).append('.').append(f.format(milliseconds));

        else if (hours == 0)
            displayTime.append(f.format(minutes)).append(":").append(f.format(seconds)).append('.').append(f.format(milliseconds));

        else
            displayTime.append(hours).append(":").append(f.format(minutes)).append(":").append(f.format(seconds));

        return displayTime.toString();
    }

    /**
     * Returns true if the stopwatch has started
     *
     * @since 1.0
     * @return true if the stopwatch has been started by calling start(). False otherwise
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Returns true if the stopwatch is paused
     *
     * @return true if the stopwatch is paused. False otherwise
     * @since 1.0
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Gets the current elapsed time the stopwatch has been running for in milliseconds
     *
     * @return the time in milliseconds the stopwatch has been running for.
     * @since 1.0
     */
    public long getElapsedTime() {
        return elapsedTime;
    }

    /**
     * Returns the clock time (in milliseconds) when the stopwatch was started
     *
     * @return time when the stopwatch was started in milliseconds.
     * @since 1.0
     */
    public long getStart() {
        return start;
    }

    /**
     * Get a list of all splits that have been created.
     *
     * @return all splits created with split method.
     * @see Split
     * @since 1.0
     */
    public LinkedList<Split> getSplits() {
        return splits;
    }

    /**
     * Returns the currently set clock delay
     *
     * @return currently set clock delay in milliseconds (default: 100ms)
     * @since 1.0
     */
    public long getClockDelay() {
        return clockDelay;
    }

    /**
     * Set a custom clock delay to increase/decrease update frequency.
     * Clock delay is the delay in between each successive clock update.
     *
     * @param clockDelay clock delay in milliseconds (default : 100ms)
     * @see Thread#sleep(long)
     * @since 1.0
     */
    public void setClockDelay(long clockDelay) {
        this.clockDelay = clockDelay;
    }

    /**
     * Set whether to print debug logs or not. If enabled, it will log each time the time is updated.
     *
     * @param debugMode desired debugging status
     * @since 1.0
     */
    public void setDebugMode(boolean debugMode) {
        logEnabled = debugMode;
    }

    /**
     * Allows you to set a textView where the stopwatch time is displayed.
     * If not provided, or set to null, you need to manually display the time.
     *
     * @param textView the textView where you want to display the stopwatch time. Can be null.
     * @since 1.0
     */
    public void setTextView(@Nullable TextView textView) {
        this.textView = textView;
    }

    /**
     * Set an OnTickListener to listen for clock changes.
     *
     * @param onTickListener a reference to the interface implementation.
     * @since 1.0
     */
    public void setOnTickListener(OnTickListener onTickListener) {
        this.onTickListener = onTickListener;
    }

    /**
     * Starts the stopwatch at the current time. Cannot be called again without calling stop() first.
     *
     * @throws IllegalStateException if the stopwatch has already been started.
     * @see #stop()
     * @see #isStarted()
     * @since 1.0
     */
    public void start() {
        if (started)
            throw new IllegalStateException("Already Started");
        else {
            started = true;
            paused = false;
            start = System.currentTimeMillis();
            current = System.currentTimeMillis();
            lapTime = 0;
            elapsedTime = 0;
            splits.clear();
            handler.post(runnable);
        }
    }

    /**
     * Stops the stopwatch. Stopwatch cannot be resumed from current time later.
     *
     * @throws IllegalStateException if stopwatch has not been started yet.
     * @see #start()
     * @see #isStarted()
     * @since 1.0
     */
    public void stop() {
        if (!started)
            throw new IllegalStateException("Not Started");
        else {
            updateElapsed(System.currentTimeMillis());
            started = false;
            paused = false;
            handler.removeCallbacks(runnable);
        }
    }

    /**
     * Pauses the stopwatch. Using this allows you to resume the stopwatch from the current state.
     *
     * @throws IllegalStateException if stopwatch is already paused or not started yet.
     * @see #resume()
     * @see #isPaused()
     * @since 1.0
     */
    public void pause() {
        if (paused)
            throw new IllegalStateException("Already Paused");
        else if (!started)
            throw new IllegalStateException("Not Started");
        else {
            updateElapsed(System.currentTimeMillis());
            paused = true;
            handler.removeCallbacks(runnable);
        }
    }

    /**
     * Used to resume the stopwatch from the current time after being paused.
     *
     * @throws IllegalStateException if stopwatch is not paused or not started yet.
     * @see #pause()
     * @see #isPaused()
     * @since 1.0
     */
    public void resume() {
        if (!paused)
            throw new IllegalStateException("Not Paused");
        else if (!started)
            throw new IllegalStateException("Not Started");
        else {
            paused = false;
            current = System.currentTimeMillis();
            handler.post(runnable);
        }
    }

    /**
     * Creates a new split/lap at the current time. Can even be called when stopwatch is paused.
     *
     * @throws IllegalStateException if stopwatch is not started yet
     * @see #getSplits()
     * @since 1.0
     */
    public void split() {

        if (!started)
            throw new IllegalStateException("Not Started");
        Split split = new Split(elapsedTime, lapTime);
        lapTime = 0;
        if (logEnabled)
            Log.d("STOPWATCH", "split at " + split.getSplitTime() + ". Lap = " + split.getLapTime());
        splits.add(split);
    }

    /**
     * Updates the time in elapsed and lap time and then updates the current time.
     *
     * @since 1.1
     * @param time Current time in millis. Passing any other value may result in odd behaviour
     */
    private void updateElapsed(long time) {
        elapsedTime += time - current;
        lapTime += time - current;
        current = time;
    }


    /**
     * The main thread responsible for updating and displaying the time
     * @since 1.1
     */
    private void run() {
        if (!started || paused) {
            handler.removeCallbacks(runnable);
            return;
        }
        updateElapsed(System.currentTimeMillis());
        handler.postDelayed(runnable,clockDelay);

        if (logEnabled)
            Log.d("STOPWATCH", elapsedTime / 1000 + " seconds, " + elapsedTime % 1000 + " milliseconds");

        if (onTickListener != null)
            onTickListener.onTick(this);

        if (textView != null) {
            String displayTime = getFormattedTime(elapsedTime);
            textView.setText(displayTime);
        }
    }
}