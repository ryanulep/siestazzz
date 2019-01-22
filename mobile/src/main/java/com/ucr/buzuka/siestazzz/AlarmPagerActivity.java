package com.ucr.buzuka.siestazzz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.ucr.buzuka.siestazzz.model.Alarm;
import com.ucr.buzuka.siestazzz.model.BellTower;
import java.util.List;
import java.util.UUID;

public class AlarmPagerActivity extends AppCompatActivity {

  private static final String EXTRA_ALARM_ID = "com.buzuka.android.siestazzz.alarm_id";
  private ViewPager mViewPager;
  private List<Alarm> mAlarms;

  public static Intent newIntent(Context packageContext, UUID alarmId) {
    Intent intent = new Intent(packageContext, AlarmPagerActivity.class);
    intent.putExtra(EXTRA_ALARM_ID, alarmId);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_alarm_pager);

    UUID alarmID = (UUID) getIntent().getSerializableExtra(EXTRA_ALARM_ID);

    mViewPager = (ViewPager) findViewById(R.id.alarm_view_pager);
    mAlarms = BellTower.get(this).getAlarms();
    final FragmentManager fragmentManager = getSupportFragmentManager();

    mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
      @Override
      public Fragment getItem(int position) {
        Alarm alarm = mAlarms.get(position);
        return AlarmDetailFragment.newInstance(alarm.getId());
      }

      @Override
      public int getCount() {
        return mAlarms.size();
      }
    });

    for (int i = 0; i < mAlarms.size(); ++i) {
      if (mAlarms.get(i).getId().equals(alarmID)) {
        mViewPager.setCurrentItem(i);
        break;
      }
    }
  }
}
