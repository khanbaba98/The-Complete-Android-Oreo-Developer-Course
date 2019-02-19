package com.khanbaba.braintrainer;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button goButton, button, playAgainButton;
    CountDownTimer myTimer;
    TextView timerTextView, scoreTextView, questionTextView, resultTextView;
    int answer, answerPosition, wins, playCount;
    GridLayout gridLayout;
    Random rand = new Random();
    ConstraintLayout gameLayout;
    boolean gameFinished = false;

    public int generateRandomNumber(int bound) {
        int value;
        value = rand.nextInt(bound);
        return value;
    }
    public void updateScore(){
        String score = String.format("%d/%d",wins,playCount);
        scoreTextView.setText(score);
        if(gameFinished) {
            float winsTemp = (float) wins, playCountTemp = (float) playCount;
            if((int)playCountTemp!=0) {
                float percentage = (winsTemp / playCountTemp) * 100;
                if (percentage % 1 == 0) {
                    resultTextView.setText(String.format("%.0f", percentage) + "% Correct");
                } else {
                    resultTextView.setText(String.format("%.1f", percentage) + "% Correct");
                }
            } else {
                resultTextView.setText("0% Correct");
            }
        }
    }
    public void checkAnswer(View view){
        Button button = (Button) view;
        Log.i("Button", button.getTag().toString()+" has been pressed!");
        int buttonValue = Integer.parseInt(((Button) view).getText().toString());
        if(buttonValue == answer){
            resultTextView.setText("Correct!");
            wins++;
        } else {
            resultTextView.setText("Wrong!");
        }
        playCount++;
        updateScore();
        setQuestion();
    }
    public void setQuestion(){
        int x = generateRandomNumber(101);
        int y = generateRandomNumber(101);
        answer = x+y;
        answerPosition = generateRandomNumber(4);
        String expression = String.format("%d + %d",x,y);
        questionTextView.setText(expression);
        for(int i = 0; i<=3 ; i++){
            int randomNumber = generateRandomNumber(101);
            int id = getResources().getIdentifier("button"+i, "id", getPackageName());
            button = findViewById(id);
            if(i == answerPosition) {
                button.setText(String.valueOf(answer));
            } else {
                while(randomNumber != answer){
                    button.setText(String.valueOf(randomNumber));
                    break;
                }
            }
        }
    }
    public void startTimer() {
        myTimer = new CountDownTimer(30100,1000+10) {
            @Override
            public void onTick(long millisUntilFinished) {
                String timeEdited = String.format("%ds", (int) millisUntilFinished / 1000);
                timerTextView.setText(timeEdited);
            }

            @Override
            public void onFinish() {
                gameFinished = true;
                updateScore();
                playAgainButton.setVisibility(View.VISIBLE);
                gridLayout.setEnabled(false);
                buttonEnablerDisabler();

            }
        }.start();
    }
    public void startGame (View view){
        gameLayout.setVisibility(View.VISIBLE);
        goButton.setVisibility(View.INVISIBLE);
        startTimer();
        setQuestion();
    }
    public void buttonEnablerDisabler() {
        for(int i = 0; i<=3 ; i++) {
            int id = getResources().getIdentifier("button" + i, "id", getPackageName());
            button = findViewById(id);
            button.setEnabled(!gameFinished);
        }
    }
    public void resetGame (View view){
        gameFinished = false;
        buttonEnablerDisabler();
        gridLayout.setEnabled(true);
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);
        wins = 0;
        playCount = 0;
        startTimer();
        setQuestion();
        updateScore();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameLayout = findViewById(R.id.gameLayout);
        gridLayout = findViewById(R.id.gridLayout);
        goButton = findViewById(R.id.goButton);
        playAgainButton = findViewById(R.id.playAgainButton);
        timerTextView = findViewById(R.id.timerTextView);
        questionTextView = findViewById(R.id.questionTextView);
        resultTextView = findViewById(R.id.resultTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
    }
}
