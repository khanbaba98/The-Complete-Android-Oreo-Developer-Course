package com.khanbaba.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    EditText timeEditText;
    SeekBar timeControl;
    CountDownTimer myTimer;
    MediaPlayer mediaPlayer;
    int maxVal = 300, minVal = 30;
    boolean counterIsActive = true;

    public void resetTimer() {
        timeControl.setEnabled(true);
        timeControl.setProgress(minVal);
        startButton.setText("Go!");
        updateTimer(minVal);

    }
    public void startStopTimer(View view){
        if(counterIsActive){
            myTimer = new CountDownTimer(timeControl.getProgress() * 1000 + 100, 1000) {
                public void onTick(long millisecondsUntilDone) {
                    updateTimer((int) millisecondsUntilDone / 1000);
                }

                public void onFinish() {
                    mediaPlayer.start();
                    Toast.makeText(MainActivity.this, "We are done, no more counting", Toast.LENGTH_SHORT).show();
                    resetTimer();
                }
            }.start();
            timeControl.setEnabled(false);
            startButton.setText("Stop!");
            counterIsActive = false;
        } else {
            counterIsActive = true;
            mediaPlayer.stop();
            mediaPlayer.prepareAsync();
            resetTimer();
            myTimer.cancel();

        }

    }
    public void updateTimer(int secondsLeft) {
        String timeEdited = String.format("%02d:%02d", secondsLeft / 60, secondsLeft % 60);
        timeEditText.setText(timeEdited);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);

        startButton = findViewById(R.id.startButton);

        timeControl = findViewById(R.id.timeSeekBar);
        timeControl.setMax(maxVal);
        timeControl.setProgress(minVal);

        timeEditText = findViewById(R.id.timeEditText);

        resetTimer();

        timeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Seekbar Value: ", String.valueOf(progress));
                updateTimer(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}
