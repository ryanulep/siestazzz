package com.ucr.buzuka.siestazzz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by rwbos on 1/23/2018.
 */

public class MainViewPageFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_main,null); // inflate is a function that converts a layout to a view.

        return inflater.inflate(R.layout.fragment_main,null); // inflate is a function that converts a layout to a view.


    }

//    protected void onCreate(Bundle savedInstanceState) {
//        Button button = findViewById(R.id.sleepButton);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Button Clicked",Toast.LENGTH_LONG).show();
//            }
//        });
//    }

}