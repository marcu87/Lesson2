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

    private TextView myText = null;
    int colorIsBlack,clickWhenBlack;
    private boolean killMe = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView myTextView = (TextView) findViewById(R.id.MyTextView);
        myTextView.setVisibility(View.GONE);
        final Chronometer mChronometer = (Chronometer) findViewById(R.id.chronometer);
        //final String[] values = getResources().getStringArray(R.array.colors_array);
        final RelativeLayout root = (RelativeLayout) findViewById(R.id.main_layout);
        colorIsBlack=0;
        clickWhenBlack=0;
        final int black = getResources().getColor(R.color.black);
        root.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                Runnable runnable = null;

                if (colorIsBlack == 1) {
                    clickWhenBlack=1;
                    mChronometer.setVisibility(View.GONE);
                    myTextView.setVisibility(View.VISIBLE);

                } else {

                    int[] androidColors = getResources().getIntArray(R.array.androidcolors);
                    int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
                    root.setBackgroundColor(randomAndroidColor);

                    mChronometer.setBase(SystemClock.elapsedRealtime());
                    mChronometer.start();

                    if (randomAndroidColor == black) {
                        colorIsBlack = 1;
                        handler = new Handler();

                        final Handler finalH = handler;
                        final Runnable finalR = runnable;
                        runnable = new Runnable() {
                            //private boolean killMe = false;

                            public void run() {
                                if(clickWhenBlack==1)
                                    return;
                                int[] androidColors = getResources().getIntArray(R.array.androidcolors);
                                int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
                                root.setBackgroundColor(randomAndroidColor);
                                colorIsBlack = 0;


                            }
                        };
                        handler.postDelayed(runnable, 3000);

                    }



                }

            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}