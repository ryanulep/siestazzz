package com.ucr.buzuka.siestazzz;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar(); // or getActionBar()
        if (actionBar != null) { actionBar.hide(); }

        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mViewPager.setAdapter(new MyViewPageAdapter(getSupportFragmentManager()));

        Button button = findViewById(R.id.sleepButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Button Clicked",Toast.LENGTH_LONG).show();
            }
        });
    }
}
