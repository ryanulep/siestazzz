package com.ucr.buzuka.siestazzz;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);                           // Unclear about what this does.

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }  // End of OnCreate



    public void GoToSleep(View view) {
        Intent intent = new Intent(this, SleepSessionActivity.class);
        Toast.makeText(getApplicationContext(), "Start Sleep!", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void SetAlarm(View view) {
        // Tell MainActivity to switch to ListAlarmsActivity
        Intent intent = new Intent(this, SetAlarm.class);
        startActivity(intent);
    }

    public void SmartAlarmClick(View view) {
        final Button btn = (Button) findViewById(R.id.smart_alarm_button);

        if (btn.getBackground().getConstantState() == getResources().getDrawable(R.drawable.smartalarm_disabled).getConstantState()) {
            btn.setBackground(getDrawable(R.drawable.smartalarm_enabled));
        }
        else {
            btn.setBackground(getDrawable(R.drawable.smartalarm_disabled));
        }
    }

    public void TrackMovementClicked(View view) {
        final Button btn = (Button) findViewById(R.id.track_movement_button);

        if (btn.getBackground().getConstantState() == getResources().getDrawable(R.drawable.trackmovement_disabled).getConstantState()) {
            btn.setBackground(getDrawable(R.drawable.trackmovement_enabled));
        }
        else {
            btn.setBackground(getDrawable(R.drawable.trackmovement_disabled));
        }
    }

    public void RecordSoundClicked(View view) {
        final Button btn = (Button) findViewById(R.id.record_sound_button);

        if (btn.getBackground().getConstantState() == getResources().getDrawable(R.drawable.recordsound_disabled).getConstantState()) {
            btn.setBackground(getDrawable(R.drawable.recordsound_enabled));
        }
        else {
            btn.setBackground(getDrawable(R.drawable.recordsound_disabled));
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

//        /**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         *
//         * Not currently  being used however I think this would be a good
//         * use for passing data between sections.
//         */
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();                       // Unclear what Bundle is doing
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }

//        @Override
//        public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
//            final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            // Some fuzzy stuff here. Refer to template but in essence i want to add interface here.
//
//            Button sleep_button = (Button) rootView.findViewById(R.id.sleepButton);
//
//            sleep_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    // getContext() is used to replace 'MainActivity.this'
//                    Toast.makeText(getContext(), "Button Clicked",Toast.LENGTH_LONG).show();
//                }
//            });
//        return rootView;
//        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) { super(fm); }

        @Override
        public Fragment getItem(int position) { // The page that we want to display in a ViewPage
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);

            switch (position) {
                case 0:
                    return new ViewPagerFragment_Main();
                case 1:
                    return new ViewPagerFragment_Journal();
                case 2 :
                    return new ViewPagerFragment_Alarms();

                //Later Case 3 may be added to display app info.
//                case 3:
//                    return new InfoViewPagerFragment();
                default:
                    return new ViewPagerFragment_Main();
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

}
