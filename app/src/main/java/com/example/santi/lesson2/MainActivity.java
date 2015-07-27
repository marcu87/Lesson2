package com.example.santi.lesson2;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.View;
import android.widget.Chronometer;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {

    Chronometer focus;
    private TextView myText = null;
    int b1,b2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView myTextView = (TextView) findViewById(R.id.MyTextView);
        myTextView.setVisibility(View.GONE);
        focus = (Chronometer) findViewById(R.id.chronometer1);
        final String[] values = getResources().getStringArray(R.array.colors_array);
        final RelativeLayout root = (RelativeLayout) findViewById(R.id.main_layout);
        b1=0;
        b2=0;
        final int black = getResources().getColor(R.color.black);
        root.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Handler h = new Handler();
                Runnable r = null;

                if (b1 == 1) {
                    b2=1;
                    focus.setVisibility(View.GONE);
                    myTextView.setVisibility(View.VISIBLE);

                } else {
                    int[] androidColors = getResources().getIntArray(R.array.androidcolors);
                    int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
                    root.setBackgroundColor(randomAndroidColor);

                    focus.setBase(SystemClock.elapsedRealtime());
                    focus.start();

                    if (randomAndroidColor == black) {
                        b1 = 1;
                        h = new Handler();

                        final Handler finalH = h;
                        r = new Runnable() {

                            public void run() {
                                int[] androidColors = getResources().getIntArray(R.array.androidcolors);
                                int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
                                root.setBackgroundColor(randomAndroidColor);
                                b1 = 0;
                                if(b2==1){
                                  finalH.removeCallbacksAndMessages(null);
                                }
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