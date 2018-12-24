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

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yashovardhan99.timeit.OnTickListener;
import com.yashovardhan99.timeit.Split;
import com.yashovardhan99.timeit.Stopwatch;

import java.util.LinkedList;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnTickListener {

    TextView time, splitLog;
    Stopwatch stopwatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stopwatch = new Stopwatch();
        stopwatch.setDebugMode(true);
        time = findViewById(R.id.time);
        stopwatch.setTextView(time);
        splitLog = findViewById(R.id.split_log);
        stopwatch.setOnTickListener(this);
        stopwatch.setClockDelay(50);
    }

    @Override
    public void onClick(View v) {
        Log.d("ONCLICK", v.toString());
        switch (v.getId()) {
            case R.id.start:
                if (!stopwatch.isStarted()) {
                    stopwatch.start();
                    splitLog.setText(null);
                }
                break;
            case R.id.stop:
                if (stopwatch.isStarted())
                    stopwatch.stop();
                break;
            case R.id.pause:
                if (stopwatch.isStarted() && !stopwatch.isPaused())
                    stopwatch.pause();
                break;
            case R.id.resume:
                if (stopwatch.isPaused())
                    stopwatch.resume();
                break;
            case R.id.split:
                if (stopwatch.isStarted())
                    stopwatch.split();
                StringBuilder stringBuilder = new StringBuilder();
                LinkedList<Split> splits = stopwatch.getSplits();
                for (Split split : splits) {
                    long lapTime = split.getLapTime();
                    long splitTime = split.getSplitTime();
                    stringBuilder.append(lapTime).append(" <- Lap ").append(splits.indexOf(split)).append(" Split -> ").append(splitTime).append("\n");
                }
                splitLog.setText(stringBuilder.toString());
                ((ScrollView) (findViewById(R.id.split_scroller))).fullScroll(View.FOCUS_DOWN);
                break;
            case R.id.switch_to_timer:
                Intent intent = new Intent(this,TimerDemo.class);
                startActivity(intent);
        }
    }

    @Override
    public void onTick(Stopwatch stopwatch) {
        Log.d("MAINACTIVITYLISTENER", String.valueOf(stopwatch.getElapsedTime()));
    }
}
