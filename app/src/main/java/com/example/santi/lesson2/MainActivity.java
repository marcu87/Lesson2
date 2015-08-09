package com.example.santi.lesson2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends Activity {

    private int randomAndroidColor, black;
    private long currentCountDownTime;
    private Handler        mainHandler;
    private CountDownTimer countDownTimerObj;
    private TextView       replay, gameOverLabel, countDownTimer, highScoreView, myCounter;
    private ImageView clock;

    private boolean gameEnd                          = false;
    private boolean clockIsVisible                   = false;
    private boolean periodicallyChangeManualActivate = true;
    private int     lastBackgroundColor              = 0;
    public  int     clickCounter                     = 0;
    private int     changeInterval                   = 500;
    private int     countDownInitialTime             = 10000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mainHandler = new Handler();
        black = getResources().getColor(R.color.black);

        myCounter = (TextView) findViewById(R.id.MyCounter);
        gameOverLabel = (TextView) findViewById(R.id.GameOver);
        countDownTimer = (TextView) findViewById(R.id.CountDownTimer);
        highScoreView = (TextView) findViewById(R.id.HighScore);
        replay = (TextView) findViewById(R.id.Replay);
        clock = (ImageView) findViewById(R.id.Clock);
        gameOverLabel.setVisibility(View.GONE);
        replay.setVisibility(View.GONE);

        final RelativeLayout root = (RelativeLayout) findViewById(R.id.main_layout);

        // set the highscore
        highScoreView.setText("" + getHighScore());

        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame();
                countDownTimer.setText("10");
            }
        });

        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTimeToCountDown(5000);
                hideClock();
                changeColorAndSumeUp();
            }
        });

        root.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (gameEnd == true) {
                    return;
                } else if (lastBackgroundColor == black) {
                    showGameOver();
                    countDownTimerObj.cancel();
                    return;
                }

                if (clickCounter == 0) {
                    startCountDown(countDownInitialTime);
                } else {
                    showClockOrNot();
                }

                changeColorAndSumeUp();
            }

        });
    }

    void changeColorAndSumeUp() {
        sumCounterUp();
        changeColor((clickCounter == 1));

        if (randomAndroidColor == black) {
            startPeriodicallyChange();
            hideClock();
        }
    }

    void addTimeToCountDown(long time) {
        countDownTimerObj.cancel();

        long countDownInitialTime = time + currentCountDownTime;

        startCountDown(countDownInitialTime);
    }

    void startCountDown(long time) {
        countDownTimerObj = new CountDownTimer(time, 500) {

            public void onTick(long millisUntilFinished) {
                countDownTimer.setText("" + millisUntilFinished / 1000);
                currentCountDownTime = millisUntilFinished ;
            }

            public void onFinish() {
                // countDownTimer.setText("time up!");
                showGameOver();
            }
        }.start();
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
        gameOverLabel.setVisibility(View.VISIBLE);
        replay.setVisibility(View.VISIBLE);
        gameEnd = true;
        hideClock();
    }

    void restartGame() {
        stopPeriodicallyChange();
        clickCounter = 0;
        myCounter.setText(String.valueOf(clickCounter));
        gameOverLabel.setVisibility(View.GONE);
        replay.setVisibility(View.GONE);
        gameEnd = false;
        changeColor(true);
    }

    void sumCounterUp() {
        clickCounter = clickCounter + 1;
        myCounter.setText(String.valueOf(clickCounter));

        saveHighScore(clickCounter);
    }

    void changeColor(boolean disableBlack) {
        final RelativeLayout root = (RelativeLayout) findViewById(R.id.main_layout);

        int[] androidColors = getResources().getIntArray(R.array.androidcolors);
        while (randomAndroidColor == lastBackgroundColor) {
            randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

            if (disableBlack == true && randomAndroidColor == black) {
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

    /*
    * this function is necessary for the calligraphy plugin
    */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    void saveHighScore(int highScore) {
        if (highScore <= getHighScore()) {
            return;
        }

        SharedPreferences        preferences  = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor sharedEditor = preferences.edit();

        sharedEditor.putInt("high_score", highScore);
        sharedEditor.commit();

        // set the highscore
        highScoreView.setText("" + getHighScore());
    }

    public int getHighScore() {
        SharedPreferences        preferences  = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor sharedEditor = preferences.edit();

        return preferences.getInt("high_score", 0);
    }

    Runnable periodicallyClock = new Runnable() {
        @Override
        public void run() {
            if (clockIsVisible) {
                clock.setVisibility(View.GONE);
                clockIsVisible = false;
            } else {
                clock.setVisibility(View.VISIBLE);
                clockIsVisible = true;
            }

            Random r              = new Random();
            int    changeInterval = r.nextInt(3000) + 1000;

            mainHandler.postDelayed(periodicallyClock, changeInterval);
        }
    };

    void showClockOrNot() {
        Random r            = new Random();
        int    randomNumber = r.nextInt(5);

        if (randomNumber == 1) {
            showClock();
        } else {
            hideClock();
        }
    }

    void showClock() {
        clockIsVisible = true;
        clock.setVisibility(View.VISIBLE);
    }

    void hideClock() {
        clockIsVisible = false;
        clock.setVisibility(View.GONE);
    }
}