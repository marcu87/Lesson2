package com.example.santi.lesson2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends Activity {

    private Handler mainHandler;
    private int     randomAndroidColor;
    private int     black;
    int colorIsBlack, clickWhenBlack;
    private Chronometer mChronometer;
    private TextView    myTextView;
    public  TextView myCounter                        = null;
    private boolean  gameEnd                          = false;
    private boolean  periodicallyChangeManualActivate = true;
    private int      lastBackgroundColor              = 0;
    public  int      clickCounter                     = 0;
    private int      changeInterval                   = 500;
    private int      intervalNormalColor              = 500;
    private int      intervalBlackColor               = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainHandler = new Handler();
        black = getResources().getColor(R.color.black);

        myCounter = (TextView) findViewById(R.id.MyCounter);
        myTextView = (TextView) findViewById(R.id.MyTextView);
        myTextView.setVisibility(View.GONE);
        mChronometer = (Chronometer) findViewById(R.id.chronometer);
        mChronometer.setVisibility(View.GONE);

        final RelativeLayout root = (RelativeLayout) findViewById(R.id.main_layout);
        colorIsBlack = 0;
        clickWhenBlack = 0;


        root.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (gameEnd == true) {
                    restartGame();
                    return;
                } else if (lastBackgroundColor == black) {
                    showGameOver();
                    return;
                }

                changeColor(false);
                sumCounterUp();

                if (randomAndroidColor == black) {
                    startPeriodicallyChange();
                }
            }

        });
    }

    Runnable periodicallyChangeColor = new Runnable() {
        @Override
        public void run() {

            if (gameEnd == true) {
                return;
            }

            if (periodicallyChangeManualActivate == false) {
                changeColor(false);
                return;
            }
            periodicallyChangeManualActivate = false;

            mainHandler.postDelayed(periodicallyChangeColor, changeInterval);
        }
    };

    void startPeriodicallyChange() {
        periodicallyChangeManualActivate = true;
        periodicallyChangeColor.run();
    }

    void stopPeriodicallyChange() {
        mainHandler.removeCallbacks(periodicallyChangeColor);
    }

    void showGameOver() {
        myTextView.setVisibility(View.VISIBLE);
        gameEnd = true;
    }

    void restartGame() {
        stopPeriodicallyChange();
        clickCounter = 0;
        myCounter.setText(String.valueOf(clickCounter));
        myTextView.setVisibility(View.GONE);
        gameEnd = false;
        changeColor(true);
    }

    void sumCounterUp() {
        clickCounter = clickCounter + 1;
        myCounter.setText(String.valueOf(clickCounter));
    }

    void sumCounterDown() {
        clickCounter = clickCounter - 1;
        myCounter.setText(String.valueOf(clickCounter));
    }

    void changeColor(boolean disableBlack) {
        final RelativeLayout root = (RelativeLayout) findViewById(R.id.main_layout);

        int[] androidColors = getResources().getIntArray(R.array.androidcolors);
        while (randomAndroidColor == lastBackgroundColor) {
            randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

            if (disableBlack == true && randomAndroidColor == black){
                randomAndroidColor = lastBackgroundColor;
            }
        }
        root.setBackgroundColor(randomAndroidColor);
        lastBackgroundColor = randomAndroidColor;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}