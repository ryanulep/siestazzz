package com.ucr.buzuka.siestazzz;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Chain this method to the onClick of "start" button
    protected void startSleep(View view){
        Intent intent = new Intent(this, SleepSessionActivity.class);
        Toast.makeText(getApplicationContext(), "Start Sleep!", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}
