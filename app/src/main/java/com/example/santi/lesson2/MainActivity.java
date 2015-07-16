package com.example.santi.lesson2;


import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.View;
import android.widget.Chronometer;
import android.widget.RelativeLayout;

import java.util.Random;

public class MainActivity extends Activity {

    Chronometer focus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        focus = (Chronometer) findViewById(R.id.chronometer1);
        final String[] values = getResources().getStringArray(R.array.colors_array);
        final RelativeLayout root = (RelativeLayout) findViewById(R.id.main_layout);
        root.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String[] colors = {"#ff0000","#ffa500","#000000"};
                int idx = new Random().nextInt(colors.length);
                String random = (colors[idx]);
                View layout = findViewById( R.id.main_layout );
                layout.setBackgroundColor(R.color.black);

                focus.setBase(SystemClock.elapsedRealtime());
                focus.start();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}