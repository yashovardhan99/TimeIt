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
import android.widget.TextView;

import com.yashovardhan99.timeit.Stopwatch;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView time;
    Stopwatch stopwatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stopwatch = new Stopwatch();
        stopwatch.setDebugMode(true);
        time = findViewById(R.id.time);
        stopwatch.setTextView(time);
    }

    @Override
    public void onClick(View v) {
        Log.d("ONCLICK", v.toString());
        switch (v.getId()){
            case R.id.start:
                if(!stopwatch.isStarted())
                    stopwatch.start();
                break;
            case R.id.stop:
                if(stopwatch.isStarted())
                    stopwatch.stop();
                break;
            case R.id.pause:
                if(stopwatch.isStarted() && !stopwatch.isPaused())
                    stopwatch.pause();
                break;
            case R.id.resume:
                if(stopwatch.isPaused())
                    stopwatch.resume();
                break;
        }
    }
}
