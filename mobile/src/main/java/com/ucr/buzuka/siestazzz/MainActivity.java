package com.ucr.buzuka.siestazzz;


import static android.widget.Toast.makeText;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.TabLayoutOnPageChangeListener;
import android.support.design.widget.TabLayout.ViewPagerOnTabSelectedListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.ucr.buzuka.siestazzz.model.Alarm;
import com.ucr.buzuka.siestazzz.model.BellTower;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

  private static final int REQUEST_PERMISSION_WRITE = 1;
  //private static final String FILE_NAME = "readout.txt";
  /**
   * The {@link PagerAdapter} that will provide fragments for each of the sections. We use a {@link
   * FragmentPagerAdapter} derivative, which will keep every loaded fragment in memory. If this
   * becomes too memory intensive, it may be best to switch to a {@link FragmentStatePagerAdapter}.
   */
  private SectionsPagerAdapter mSectionsPagerAdapter;

  /**
   * The {@link ViewPager} that will host the section contents.
   */
  private ViewPager mViewPager;
  private boolean permissionGranted;
  public static final String GLOBAL_PREFS = "global_preferences";

  //notification channel id
  private String CHANNEL_ID = UUID.randomUUID().toString();
  private int notificationId = 123;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    checkPermissions();

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);                           // Unclear about what this does.

    // Create the adapter that will return a fragment for each of the three
    // primary sections of the activity.
    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

    // Set up the ViewPager with the sections adapter.
    mViewPager = findViewById(R.id.container);
    mViewPager.setAdapter(mSectionsPagerAdapter);

    TabLayout tabLayout = findViewById(R.id.tabs);

    mViewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(tabLayout));
    tabLayout.addOnTabSelectedListener(new ViewPagerOnTabSelectedListener(mViewPager));
  }  // End of OnCreate

  public void GoToSleep(View view) {
    Intent intent = new Intent(this, SleepSessionActivity.class);
    makeText(getApplicationContext(), "Start Sleep!", Toast.LENGTH_SHORT).show();
    startActivity(intent);
  }

  public void SetAlarm(View view) {
    Alarm alarm = new Alarm();
    BellTower.get(this).addAlarm(alarm);
    Intent intent = AlarmPagerActivity.newIntent(this, alarm.getId());
    startActivity(intent);
  }

  /* Checks if external storage is available for read and write */
  public boolean isExternalStorageWritable() {
    String state = Environment.getExternalStorageState();
    return Environment.MEDIA_MOUNTED.equals(state);
  }

  /* Checks if external storage is available to at least read */
  public boolean isExternalStorageReadable() {
    String state = Environment.getExternalStorageState();
    return (Environment.MEDIA_MOUNTED.equals(state) ||
        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state));
  }


  // Initiate request for permissions.
  private boolean checkPermissions() {

    if (!isExternalStorageReadable() || !isExternalStorageWritable()) {
      makeText(this, "This app only works on devices with usable external storage",
          Toast.LENGTH_SHORT).show();
      return false;
    }

    int permissionCheck = ContextCompat.checkSelfPermission(this,
        Manifest.permission.WRITE_EXTERNAL_STORAGE);
    int audioCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
    if ((permissionCheck != PackageManager.PERMISSION_GRANTED)
        || audioCheck != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this,
          new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
              Manifest.permission.RECORD_AUDIO},
          REQUEST_PERMISSION_WRITE);
      return false;
    } else {
      return true;
    }
  }

  // Handle permissions result
  @Override
  public void onRequestPermissionsResult(int requestCode,
      @NonNull String permissions[],
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case REQUEST_PERMISSION_WRITE:
        if (grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          permissionGranted = true;
          makeText(this, "External storage permission granted",
              Toast.LENGTH_SHORT).show();
        } else {
          makeText(this, "You must grant permission!", Toast.LENGTH_SHORT).show();
        }
        break;
    }
  }

  public void SmartAlarmClick(View view) {
    final Button btn = (Button) findViewById(R.id.smart_alarm_button);
    //create a shared preference for the toggle
    SharedPreferences.Editor editor = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE).edit();

    if (btn.getBackground().getConstantState() == getResources()
        .getDrawable(R.drawable.smartalarm_disabled).getConstantState()) {
      btn.setBackground(getDrawable(R.drawable.smartalarm_enabled));
      //set toggle to true
      editor.putString(String.valueOf(Snooze2Activity.SMART_ALARM), "true");
      Log.i("MainActivity", "Smart alarm on!");
    } else {
      btn.setBackground(getDrawable(R.drawable.smartalarm_disabled));
      //set toggle to false
      editor.putString(String.valueOf(Snooze2Activity.SMART_ALARM), "false");
      Log.i("MainActivity", "Smart alarm off!");
    }
    //apply shared preference
    editor.apply();
  }

  public void TrackMovementClicked(View view) {
    final Button btn = (Button) findViewById(R.id.track_movement_button);
    //create a shared preference for the toggle
    SharedPreferences.Editor editor = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE).edit();

    if (btn.getBackground().getConstantState() == getResources()
        .getDrawable(R.drawable.trackmovement_disabled).getConstantState()) {
      btn.setBackground(getDrawable(R.drawable.trackmovement_enabled));
      //set toggle to true
      editor.putString(SleepSessionActivity.SENSOR_ACCEL, "true");
      Log.i("MainActivity", "Toggled sensor");
      makeText(this, "Accelerometer on!", Toast.LENGTH_SHORT).show();
    } else {
      btn.setBackground(getDrawable(R.drawable.trackmovement_disabled));
      //set toggle to true
      editor.putString(SleepSessionActivity.SENSOR_ACCEL, "false");
      Log.i("MainActivity", "Un-toggled sensor");
      makeText(this, "Accelerometer off!", Toast.LENGTH_SHORT).show();
    }
    //apply shared preference
    editor.apply();
  }

  public void RecordSoundClicked(View view) {
    final Button btn = (Button) findViewById(R.id.record_sound_button);
    //create a shared preference for the toggle
    SharedPreferences.Editor editor = getSharedPreferences(GLOBAL_PREFS, MODE_PRIVATE).edit();

    if (btn.getBackground().getConstantState() == getResources()
        .getDrawable(R.drawable.recordsound_disabled).getConstantState()) {
      btn.setBackground(getDrawable(R.drawable.recordsound_enabled));
      //set toggle to true
      editor.putString(SleepSessionActivity.SENSOR_AUDIO, "true");
      Log.i("MainActivity", "Toggled audio");
      makeText(this, "Audio on!", Toast.LENGTH_SHORT).show();
    } else {
      btn.setBackground(getDrawable(R.drawable.recordsound_disabled));
      //set toggle to true
      editor.putString(SleepSessionActivity.SENSOR_AUDIO, "false");
      Log.i("MainActivity", "Un-Toggled audio");
      makeText(this, "Audio off!", Toast.LENGTH_SHORT).show();
    }
    //apply shared preference
    editor.apply();
  }

  /**
   * A placeholder fragment containing a simple view.
   */
  public static class PlaceholderFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment() {
    }
  }

  /**
   * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the
   * sections/tabs/pages.
   */
  public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      /* The page that we want to display in a ViewPage
       getItem is called to instantiate the fragment for the given page.
       Return a PlaceholderFragment (defined as a static inner class below).
       return PlaceholderFragment.newInstance(position + 1); */

      switch (position) {
        case 0:
          return new ViewPagerFragment_Main();
        case 1:
          return new ViewPagerFragment_JournalEntryList();
        case 2:
          return new ViewPagerFragment_AlarmList();
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
