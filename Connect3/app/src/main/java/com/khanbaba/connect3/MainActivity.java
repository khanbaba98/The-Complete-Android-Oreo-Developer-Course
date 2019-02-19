package com.khanbaba.connect3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //0: yellow, 1: red, 2: empty
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    int activePlayer = 0;

    int gameSpace = 8;

    boolean gameActive = true;

    public void dropIn(View view) {
        gameSpace = 8;

        ImageView counter = (ImageView) view;

        Log.i("Tag ", counter.getTag().toString());

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        for(int i=0 ; i<gameState.length ; i++) {
            if (gameState[i] != 2)
                gameSpace--;
        }

        Log.i("Gspace ",String.valueOf(gameSpace));

        if (gameState[tappedCounter] == 2 && gameActive) {

            gameState[tappedCounter] = activePlayer;

            counter.setTranslationY(-1500);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1500).rotation(3600).setDuration(300);

            String winner = "";

            Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

            TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);

            TextView yellowCounterTextView = (TextView) findViewById(R.id.yellowCounterTextView);

            int yellowCount = Integer.parseInt(yellowCounterTextView.getText().toString());

            TextView redCounterTextView = (TextView) findViewById(R.id.redCounterTextView);

            int redCount = Integer.parseInt(redCounterTextView.getText().toString());

            for (int[] winningPosition : winningPositions) {

                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {

                    gameActive = false;
                    if (activePlayer == 1) {
                        winner = "Yellow";
                        yellowCount++;
                        yellowCounterTextView.setText(String.valueOf(yellowCount));
                    }
                    else {
                        winner = "Red";
                        redCount++;
                        redCounterTextView.setText(String.valueOf(redCount));
                    }

                    winnerTextView.setText(winner + " has won!");

                    winnerTextView.setVisibility(View.VISIBLE);

                    playAgainButton.setVisibility(View.VISIBLE);


                }

            }
        }

        if(gameSpace==0 && gameActive) {
            gameActive = false;

            Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

            TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);

            winnerTextView.setText("Tie");

            winnerTextView.setVisibility(View.VISIBLE);

            playAgainButton.setVisibility(View.VISIBLE);

            counter.setTranslationY(-1500);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1500).rotation(3600).setDuration(300);
        }

    }

    public void playAgain(View view) {

        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);

        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);

        TextView yellowCounterTextView = (TextView) findViewById(R.id.yellowCounterTextView);

        TextView redCounterTextView = (TextView) findViewById(R.id.redCounterTextView);

        winnerTextView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);

        android.support.v7.widget.GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {

            ImageView counter = (ImageView) gridLayout.getChildAt(i);

            counter.setImageDrawable(null);
        }

        for (int j=0;j<gameState.length;j++){

            gameState[j]=2;
        }

        activePlayer = 0;

        gameActive = true;

        gameSpace =8;

    }

    public void resetScore(View view){
        TextView yellowCounterTextView = (TextView) findViewById(R.id.yellowCounterTextView);
        TextView redCounterTextView = (TextView) findViewById(R.id.redCounterTextView);
        yellowCounterTextView.setText("0");
        redCounterTextView.setText("0");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
