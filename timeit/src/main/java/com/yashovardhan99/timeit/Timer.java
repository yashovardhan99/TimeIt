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

import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * Created by Yashovardhan99 on 24/12/18 as a part of TimeIt.
 */
public class Timer {

    private long duration;
    private TextView textView;
    private Stopwatch stopwatch;


    public Timer() {
        stopwatch = new Stopwatch();
        textView = null;
        duration = 0;
        stopwatch.setOnTickListener(this::onTick);
    }

    public Timer(long duration) {
        this();
        this.duration = duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isStarted() {
        return stopwatch.isStarted();
    }

    public boolean isPaused() {
        return stopwatch.isPaused();
    }

    public long getRemainingTime() {
        return duration - stopwatch.getElapsedTime();
    }

    public long getStart() {
        return stopwatch.getStart();
    }

    public void setTextView(@Nullable TextView textView) {
        this.textView = textView;
    }

    public void start() {
        if (duration > 0)
            stopwatch.start();
        else
            throw new IllegalStateException("Duration not set");
    }

    public void stop() {
        stopwatch.stop();
    }

    public void pause() {
        stopwatch.pause();
    }

    public void resume() {
        stopwatch.resume();
    }

    private void onTick(Stopwatch stopwatch) {
        if (stopwatch.getElapsedTime() >= duration) {
            textView.setText(Stopwatch.getFormattedTime(0));
            stopwatch.stop();
        } else
            textView.setText(Stopwatch.getFormattedTime(duration - stopwatch.getElapsedTime()));
    }
}
