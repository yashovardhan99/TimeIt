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

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * The timer class is a utility class for creating and managing count down timers in android. This class provides basic features like : start, stop, pause and resume.
 * You must set a duration to use the timer. This duration must be a positive long integer for the timer to work.
 * You can also set a textView to auto-update every 0.1 second or use the OnTickListener interface to listen for clock update events.
 * Created by Yashovardhan99 on 24/12/18 as a part of TimeIt.
 *
 * @author Yashovardhan Dhanania
 * @version 1.2
 * @see Stopwatch
 * @since 1.2
 */
public class Timer {

    private long duration;
    private TextView textView;
    private Stopwatch stopwatch;
    private Timer.OnTickListener onTickListener;
    private boolean debugMode;

    /**
     * The default constructor used to create an instance of Timer. Duration is set to a default value of 0 which should be changed before calling start.
     *
     * @see #Timer(long)
     * @see #setDuration(long)
     * @since 1.2
     */
    @SuppressWarnings("WeakerAccess")
    public Timer() {
        stopwatch = new Stopwatch();
        textView = null;
        duration = 0;
        stopwatch.setOnTickListener(this::onTick);
        onTickListener = null;
        debugMode = false;
    }


    /**
     * Parameterized constructor used to initialize an instance of Timer with a specified duration
     *
     * @param duration the duration for which the timer should run in milliseconds
     * @see #Timer()
     * @since 1.2
     */
    public Timer(long duration) {
        this();
        this.duration = duration;
    }

    /**
     * Returns true if the timer has been started
     *
     * @return true if the timer has been started
     * @since 1.2
     */
    public boolean isStarted() {
        return stopwatch.isStarted();
    }

    /**
     * Returns true if the timer is paused
     *
     * @return true if the timer is paused
     * @since 1.2
     */
    public boolean isPaused() {
        return stopwatch.isPaused();
    }

    /**
     * Get the remaining time of the timer in milliseconds
     *
     * @return the remaining time of the timer in milliseconds
     * @since 1.2
     */
    public long getRemainingTime() {
        return duration - stopwatch.getElapsedTime();
    }

    /**
     * Get the start time in long
     *
     * @return the timestamp when the timer was started
     * @since 1.2
     */
    public long getStart() {
        return stopwatch.getStart();
    }

    /**
     * Fetch the set duration of the timer
     *
     * @return the set duration of the timer in milliseconds
     * @since 1.2
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Used to set the duration of the timer.
     * Note : Should only be used once before starting the timer or it may have unintended consequences.
     *
     * @param duration the duration in milliseconds for which you want to set the timer
     * @since 1.2
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    /** Returns true if debug mode is enabled
     * @return the current boolean status of debug mode
     * @since 1.2
     */
    @SuppressWarnings("unused")
    public boolean isDebugMode() {
        return debugMode;
    }

    /** Set debug mode to print debug log messages in the logcat
     * @param debugMode the boolean status to set the debug mode
     */
    @SuppressWarnings("unused")
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    /**
     * Used to set the textView which is auto-updated every clock tick.
     *
     * @param textView the textView to update every clock tick.
     * @since 1.2
     */
    public void setTextView(@Nullable TextView textView) {
        this.textView = textView;
    }

    /**
     * Used to set an OnTickListener to listen to clock events
     *
     * @param onTickListener an instance of Timer.OnTickListener
     * @see OnTickListener
     * @since 1.2
     */
    public void setOnTickListener(OnTickListener onTickListener) {
        this.onTickListener = onTickListener;
    }

    /**
     * Used to start the timer.
     * Should only be called if the timer is not already started.
     *
     * @since 1.2
     */
    public void start() {
        if (duration > 0)
            stopwatch.start();
        else
            throw new IllegalStateException("Duration not set");
    }

    /**
     * Used to stop the timer.
     * Should only be called if the timer is already started.
     *
     * @since 1.2
     */
    public void stop() {
        stopwatch.stop();
    }

    /**
     * Used to pause the timer.
     * Should only be called if the timer is currently running
     *
     * @since 1.2
     */
    public void pause() {
        stopwatch.pause();
    }

    /**
     * Used to resume a paused timer.
     * Should only be used after a pause call.
     *
     * @since 1.2
     */
    public void resume() {
        stopwatch.resume();
    }

    private void onTick(Stopwatch stopwatch) {

        if(debugMode)
            Log.d("TIMER","Elapsed : "+stopwatch.getElapsedTime()+"; Remaining : "+(duration-stopwatch.getElapsedTime()));

        if (onTickListener != null)
            onTickListener.onTick(this);

        if (stopwatch.getElapsedTime() >= duration) {
            textView.setText(Stopwatch.getFormattedTime(0));
            stopwatch.stop();

            if (onTickListener != null)
                onTickListener.onComplete(this);
        } else {
            if (textView != null)
                textView.setText(Stopwatch.getFormattedTime(duration - stopwatch.getElapsedTime()));
        }
    }

    /**
     * Listener interface to listen for important clock events with the timer.
     *
     * @since 1.2
     */
    public interface OnTickListener {
        /**
         * Called every clock cycle update
         *
         * @param timer the timer instance which has been updated
         * @since 1.2
         */
        void onTick(Timer timer);

        /**
         * Called when the timer has been completed (ran its full duration)
         *
         * @param timer the timer instance which has completed
         * @since 1.2
         */
        void onComplete(Timer timer);
    }
}
