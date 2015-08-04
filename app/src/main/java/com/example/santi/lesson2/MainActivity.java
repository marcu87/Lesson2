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
    int b1,b2,b3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView myTextView = (TextView) findViewById(R.id.MyTextView);
        myTextView.setVisibility(View.GONE);
        final Chronometer mChronometer = (Chronometer) findViewById(R.id.chronometer);
        //final String[] values = getResources().getStringArray(R.array.colors_array);
        final RelativeLayout root = (RelativeLayout) findViewById(R.id.main_layout);
        b1=0;
        b2=0;
        b3=0;
        final int black = getResources().getColor(R.color.black);
        root.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Handler h = new Handler();
                Runnable r = null;

                if (b1 == 1) {
                    b2=1;
                    mChronometer.setVisibility(View.GONE);
                    myTextView.setVisibility(View.VISIBLE);

                } else {
                    int[] androidColors = getResources().getIntArray(R.array.androidcolors);
                    int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
                    root.setBackgroundColor(randomAndroidColor);

                    mChronometer.setBase(SystemClock.elapsedRealtime());
                    mChronometer.start();

                    if (randomAndroidColor == black) {
                        b1 = 1;
                        h = new Handler();

                        final Handler finalH = h;
                        final Runnable finalR = r;
                        r = new Runnable() {
                            private boolean killMe = false;

                            public void run() {
                                if(killMe)
                                    return;
                                int[] androidColors = getResources().getIntArray(R.array.androidcolors);
                                int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
                                root.setBackgroundColor(randomAndroidColor);
                                b1 = 0;


                            }
                            private void killRunnable()
                            {
                                if(b2==1)
                                    killMe = true;
                            }


                        };
                        h.postDelayed(r, 3000);

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