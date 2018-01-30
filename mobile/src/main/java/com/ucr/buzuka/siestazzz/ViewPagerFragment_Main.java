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
 * Created by Rick Boshae on 1/23/2018.
 */

public class ViewPagerFragment_Main extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        // Add Listeners here.
        Button sleep_button = (Button) rootView.findViewById(R.id.sleepButton);

        sleep_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getContext() is used to replace 'MainActivity.this'
                Toast.makeText(getContext(), "Button Clicked",Toast.LENGTH_LONG).show();
            }
        });
        return rootView;
    }


}