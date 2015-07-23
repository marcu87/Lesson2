package com.example.santi.lesson2;


import android.app.Activity;
import android.os.Bundle;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView myTextView = (TextView) findViewById(R.id.MyTextView);
        myTextView.setVisibility(View.GONE);
        focus = (Chronometer) findViewById(R.id.chronometer1);
        final String[] values = getResources().getStringArray(R.array.colors_array);
        final RelativeLayout root = (RelativeLayout) findViewById(R.id.main_layout);
        root.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int[] androidColors = getResources().getIntArray(R.array.androidcolors);
                int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
                root.setBackgroundColor(randomAndroidColor);

                focus.setBase(SystemClock.elapsedRealtime());
                focus.start();

                int black = getResources().getColor(R.color.black);
                if(randomAndroidColor==black){
                    focus.setVisibility(View.GONE);
                    TextView myTextView = (TextView) findViewById(R.id.MyTextView);
                    myTextView.setVisibility(View.VISIBLE);
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