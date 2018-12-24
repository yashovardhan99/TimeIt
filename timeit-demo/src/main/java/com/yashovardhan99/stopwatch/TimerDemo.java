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

package com.yashovardhan99.stopwatch;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yashovardhan99.timeit.Timer;

import androidx.appcompat.app.AppCompatActivity;

public class TimerDemo extends AppCompatActivity implements Timer.OnTickListener {

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_demo);
        timer = new Timer(2 * 60 * 1000); //setting demo timer for 2 minutes
        timer.setTextView(findViewById(R.id.time));
        timer.setOnTickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                if (!timer.isStarted())
                    timer.start();
                break;
            case R.id.stop:
                if (timer.isStarted())
                    timer.stop();
                break;
            case R.id.pause:
                if (!timer.isPaused() && timer.isStarted())
                    timer.pause();
                break;
            case R.id.resume:
                if (timer.isPaused())
                    timer.resume();
                break;
        }
    }

    @Override
    public void onTick(Timer timer) {
        Log.d("TIMER", String.valueOf(timer.getRemainingTime()));
    }

    @Override
    public void onComplete(Timer timer) {
        Log.d("TIMER", "FINISHED");
    }
}
