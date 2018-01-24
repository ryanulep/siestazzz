package com.ucr.buzuka.siestazzz;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar(); // or getActionBar()
        actionBar.hide();

        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mViewPager.setAdapter(new MyViewPageAdapter(getSupportFragmentManager()));
    }
}
