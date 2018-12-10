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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;

import androidx.annotation.Nullable;

/**
 * The Stopwatch class is used for creating and using a simple stopwatch with basic features like : start, pause, resume and split.
 * It allows you to send a TextView and automatically updates it every 0.1 seconds.
 * Threading on a separate thread is handled by the class itself. You just need to call appropriate methods to control the stopwatch.
 *
 * Created by Yashovardhan99 on 8/12/18 as a part of TimeIt.
 *
 * @author Yashovardhan Dhanania
 * @version 1.0
 * @see java.lang.Runnable
 */
public class Stopwatch implements Runnable {
    private final Object pauseLock;
    private LinkedList<Split> splits;
    private TextView textView;
    private long start, current, elapsedTime, lapTime;
    private boolean started, paused, logEnabled;

    /** The default constructor should be called to create an object to call functions accordingly. */
    public Stopwatch() {
        start = System.currentTimeMillis();
        current = System.currentTimeMillis();
        elapsedTime = 0;
        started = false;
        paused = false;
        logEnabled = false;
        pauseLock = new Object();
        splits = new LinkedList<>();
        textView = null;
        lapTime = 0;
    }

    /** Returns true if the stopwatch has started
     *
     * @return true if the stopwatch has been started by calling start(). False otherwise
     * */
    public boolean isStarted() {
        return started;
    }

    /** Returns true if the stopwatch is paused
     *
     * @return true if the stopwatch is paused. False otherwise
     * */
    public boolean isPaused() {
        return paused;
    }

    /** Gets the current elapsed time the stopwatch has been running for in milliseconds
     *
     * @return  the time in milliseconds the stopwatch has been running for.
     */
    public long getElapsedTime() {
        return elapsedTime;
    }


    /**
     * Returns the clock time (in milliseconds) when the stopwatch was started
     *
     * @return time when the stopwatch was started in milliseconds.
     */
    public long getStart() {
        return start;
    }

    /**
     * Set whether to print debug logs or not. If enabled, it will log each time the time is updated.
     *
     * @param debugMode desired debugging status
     */
    public void setDebugMode(boolean debugMode) {
        logEnabled = debugMode;
    }

    /**
     * Allows you to set a textView where the stopwatch time is displayed.
     * If not provided, or set to null, you need to manually display the time.
     *
     * @param textView the textView where you want to display the stopwatch time. Can be null.
     */
    public void setTextView(@Nullable TextView textView) {
        this.textView = textView;
    }

    /**
     * Starts the stopwatch at the current time. Cannot be called again without calling stop() first.
     *
     * @throws IllegalStateException if the stopwatch has already been started.
     * @see #stop()
     * @see #isStarted()
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
            Thread timer = new Thread(this);
            timer.start();
        }
    }

    /**
     * Stops the stopwatch. Stopwatch cannot be resumed from current time later.
     *
     * @throws IllegalStateException if stopwatch has not been started yet.
     * @see #start()
     * @see #isStarted()
     */
    public void stop() {
        if (!started)
            throw new IllegalStateException("Not Started");
        else {
            started = false;
            paused = false;
        }
    }

    /**
     * Pauses the stopwatch. Using this allows you to resume the stopwatch from the current state.
     * @throws IllegalStateException if stopwatch is already paused or not started yet.
     * @see #resume()
     * @see #isPaused()
     */
    public void pause() {
        if (paused)
            throw new IllegalStateException("Already Paused");
        else if(!started)
            throw new IllegalStateException("Not Started");
        else {
            paused = true;
        }
    }

    /**
     * Used to resume the stopwatch from the current time after being paused.
     *
     * @throws IllegalStateException if stopwatch is not paused or not started yet.
     * @see #pause()
     * @see #isPaused()
     */
    public void resume() {
        if (!paused)
            throw new IllegalStateException("Not Paused");
        else if(!started)
            throw new IllegalStateException("Not Started");
        else {
            paused = false;
            current = System.currentTimeMillis();
            synchronized (pauseLock) {
                pauseLock.notify();
            }
        }
    }

    /**
     * Creates a new split/lap at the current time. Can even be called when stopwatch is paused.
     *
     * @throws IllegalStateException if stopwatch is not started yet
     *
     * @see #getSplits()
     */
    public void split() {

        if(!started)
            throw new IllegalStateException("Not Started");
        Split split = new Split(elapsedTime, lapTime);
        lapTime = 0;
        if (logEnabled)
            Log.d("STOPWATCH", "split at " + split.getSplitTime() + ". Lap = " + split.getLapTime());
        splits.add(split);
    }

    /**
     * Get a list of all splits that have been created.
     * @return all splits created with split method.
     * @see Split
     */
    public LinkedList<Split> getSplits() {
        return splits;
    }

    /**
     * This is the main thread which runs the stopwatch.
     * Please DO NOT call this directly. Use the start() method instead.
     * @see #start
     */
    @Override
    public void run() {
        while (started) {
            if (paused) {
                try {
                    synchronized (pauseLock) {
                        if (logEnabled)
                            Log.d("STOPWATCH", "Paused");
                        pauseLock.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long time = System.currentTimeMillis();
            elapsedTime += time - current;
            lapTime += time - current;
            current = time;
            if (logEnabled) {
                Log.d("STOPWATCH", elapsedTime / 1000 + " seconds, " + elapsedTime % 1000 + " milliseconds");
            }

            if (textView != null) {
                final StringBuilder displayTime = new StringBuilder();
                int format; //0 - SS.ss, 1 - M:SS.ss. 2 - MM:SS, 3 - HH:MM:SS
                int seconds = (int) ((elapsedTime / 1000) % 60);
                int milliseconds = (int) ((elapsedTime % 1000)/10);
                int minutes = (int) (elapsedTime /(60 * 1000)%60);
                int hours = (int) (elapsedTime / (60 * 60 * 1000));
                if (minutes == 0)
                    format = 0;
                else if (minutes < 10)
                    format = 1;
                else if (hours == 0)
                    format = 2;
                else
                    format = 3;
                NumberFormat f = new DecimalFormat("00");

                switch (format) {
                    case 0:
                        displayTime.append(f.format(seconds));
                        displayTime.append('.');
                        displayTime.append(f.format(milliseconds));
                        break;
                    case 1:
                        displayTime.append(minutes).append(":").append(f.format(seconds)).append(".").append(f.format(milliseconds));
                        break;
                    case 2:
                        displayTime.append(f.format(minutes)).append(":").append(f.format(seconds));
                        break;
                    case 3:
                        displayTime.append(hours).append(":").append(f.format(minutes)).append(":").append(f.format(seconds));
                        break;
                }
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(displayTime.toString());
                    }
                });
            }
        }
    }
}