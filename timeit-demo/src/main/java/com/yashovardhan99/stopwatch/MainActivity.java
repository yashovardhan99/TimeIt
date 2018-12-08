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
