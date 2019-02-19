package com.khanbaba.timerdemo;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new CountDownTimer(10000,1000){
            public void onTick(long millisecondsUntilDone){
                Toast.makeText(MainActivity.this, "Seconds left: "+ String.valueOf((millisecondsUntilDone / 1000)+1), Toast.LENGTH_SHORT).show();

            }
            public void onFinish(){
                Toast.makeText(MainActivity.this, "We are done, no more counting", Toast.LENGTH_SHORT).show();
            }
        }.start();

        /*
        final Handler handler = new Handler();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                Log.i("Hello there ","A second has passed!");

                handler.postDelayed(this, 1000);
            }
        };

        handler.post(run);
        */
    }
}
