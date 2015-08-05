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

    public TextView myCounter = null;
    int colorIsBlack, clickWhenBlack;
    private boolean killMe = false;
    private int lastBackgroundColor = 0;
    private int randomAndroidColor;
    public int clickCounter = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myCounter = (TextView) findViewById(R.id.MyCounter);
        final TextView myTextView = (TextView) findViewById(R.id.MyTextView);
        myTextView.setVisibility(View.GONE);
        final Chronometer mChronometer = (Chronometer) findViewById(R.id.chronometer);
        //final String[] values = getResources().getStringArray(R.array.colors_array);
        final RelativeLayout root = (RelativeLayout) findViewById(R.id.main_layout);
        colorIsBlack = 0;
        clickWhenBlack = 0;
        final int black = getResources().getColor(R.color.black);
        root.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                Runnable runnable = null;

                if (colorIsBlack == 1) {
                    clickWhenBlack = 1;
                    mChronometer.setVisibility(View.GONE);
                    myTextView.setVisibility(View.VISIBLE);

                } else {
                    changeColor();

                    clickCounter = clickCounter + 1;
                    myCounter.setText(String.valueOf(clickCounter));

                    mChronometer.setBase(SystemClock.elapsedRealtime());
                    mChronometer.start();

                    if (randomAndroidColor == black) {
                        colorIsBlack = 1;
                        handler = new Handler();

                        runnable = new Runnable() {
                            public void run() {
                                if (clickWhenBlack == 1) {
                                    return;
                                }

                                changeColor();
                                colorIsBlack = 0;

                                mChronometer.setBase(SystemClock.elapsedRealtime());
                                mChronometer.start();
                            }
                        };
                        handler.postDelayed(runnable, 500);
                    }
                }
            }

        });
    }

    public boolean changeColor() {
        final RelativeLayout root = (RelativeLayout) findViewById(R.id.main_layout);

        int[] androidColors = getResources().getIntArray(R.array.androidcolors);
        while (randomAndroidColor == lastBackgroundColor) {
            randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        }
        root.setBackgroundColor(randomAndroidColor);
        lastBackgroundColor = randomAndroidColor;

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}