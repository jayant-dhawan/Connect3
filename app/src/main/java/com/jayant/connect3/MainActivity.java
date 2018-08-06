package com.jayant.connect3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0 = red and 1 = yellow
    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winingPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    boolean gameIsActive = true;

    public void dropIn(View view) {

        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (gameState[tappedCounter] == 2 && gameIsActive) {
            gameState[tappedCounter] = activePlayer;
            Log.i("Active Player", String.valueOf(gameState[tappedCounter]));
            counter.setTranslationY(-1000f);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.red);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);
            for (int[] winingPosition : winingPositions) {
                if (gameState[winingPosition[0]] == gameState[winingPosition[1]] && gameState[winingPosition[1]]
                        == gameState[winingPosition[2]] && gameState[winingPosition[0]] != 2) {
                    gameIsActive = false;
                    String winner = "Red";
                    Log.i("Winning Player", String.valueOf(gameState[winingPosition[0]]));
                    if (gameState[winingPosition[0]] == 1) {
                        winner = "Yellow";
                    }
                    TextView winnerMessage = findViewById(R.id.winnerMessage);
                    winnerMessage.setText(String.format("%s has Won!", winner));
                    LinearLayout WinnerMessage = findViewById(R.id.WinnerMessage);
                    WinnerMessage.setVisibility(View.VISIBLE);

                } else {
                    boolean gameIsOver = true;
                    for (int counterState : gameState) {
                        if (counterState == 2) gameIsOver = false;
                    }

                    if (gameIsOver) {
                        TextView winnerMessage = findViewById(R.id.winnerMessage);
                        winnerMessage.setText(R.string.it_is_draw);
                        LinearLayout WinnerMessage = findViewById(R.id.WinnerMessage);
                        WinnerMessage.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    public void playAgain(View view) {

        gameIsActive = true;
        LinearLayout layout = findViewById(R.id.WinnerMessage);
        layout.setVisibility(View.INVISIBLE);
        activePlayer = 0;

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i< gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}