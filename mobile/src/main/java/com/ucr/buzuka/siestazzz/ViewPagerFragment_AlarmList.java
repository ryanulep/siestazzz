package com.ucr.buzuka.siestazzz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import com.ucr.buzuka.siestazzz.model.Alarm;
import com.ucr.buzuka.siestazzz.model.BellTower;
import java.text.SimpleDateFormat;
import java.util.List;

public class ViewPagerFragment_AlarmList extends Fragment {

  private RecyclerView mAlarmRecyclerView;
  private AlarmAdapter mAdapter;
  private FloatingActionButton mAddAlarm;


  @Override
  public View onCreateView(LayoutInflater inflater, final ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_alarms, container, false);

    mAlarmRecyclerView = (RecyclerView) view.findViewById(R.id.alarm_recycler_view);
    mAlarmRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    mAlarmRecyclerView.setAdapter(mAdapter);

    updateUI();

    mAddAlarm = (FloatingActionButton) view.findViewById(R.id.add_alarm_fab);
    mAddAlarm.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Alarm alarm = new Alarm();
        BellTower.get(getActivity()).addAlarm(alarm);
        Intent intent = AlarmPagerActivity.newIntent(getActivity(), alarm.getId());
        startActivity(intent);
      }
    });

    return view; // inflate is a function that converts a layout to a view.
  }

  @Override
  public void onResume() {
    super.onResume();
    updateUI();
  }

  @Override
  public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
    super.onViewStateRestored(savedInstanceState);
    updateUI();
  }

  @Override
  public void onAttachFragment(Fragment childFragment) {
    super.onAttachFragment(childFragment);
    updateUI();
  }

  /**
   * updateUI refreshes the recycler list.
   */
  private void updateUI() {
    BellTower bellTower = BellTower.get(getActivity());
    List<Alarm> alarms = bellTower.getAlarms();

    if (mAdapter == null) {
      mAdapter = new AlarmAdapter(alarms);
      mAlarmRecyclerView.setAdapter(mAdapter);
    } else {
      mAdapter.setAlarms(alarms); // Added to read from db
      mAdapter.notifyDataSetChanged();
    }
  }

  // Implementing a ViewHolder and an Adapter
  // ViewHolder
  private class AlarmHolder extends RecyclerView.ViewHolder implements OnClickListener {

    // Private variable used to bind to list_item_alarm
    private Alarm mAlarm;

    private TextView mTitleTextView;
    private TextView mInfoTextView;
    private Switch mIsAlarmActive;

    // In AlarmHolder constructor inflate list_item_alarm. Immediately pass it into super(...)
    public AlarmHolder(LayoutInflater inflater, ViewGroup parent) {
      super(inflater.inflate(R.layout.list_item_alarm, parent, false));

      //Set onCLickListener as the holder
      itemView.setOnClickListener(this);

      mTitleTextView = (TextView) itemView.findViewById(R.id.alarm_title);
      mInfoTextView = (TextView) itemView.findViewById(R.id.alarm_info);
      mIsAlarmActive = (Switch) itemView.findViewById(R.id.activeSwitch);

      mIsAlarmActive.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isActive) {
          mAlarm.setActive(isActive);
          Log.i("PagerFragment_AlarmList",
              "mAlarm.setActive(isActive) — get switch value " + isActive);
          Log.i("PagerFragment_AlarmList",
              "mAlarm.getActive() — get mAlarm value " + mAlarm.isActive());

          BellTower.get(getActivity()).updateAlarm(mAlarm);
        }
      });
    }

    @Override
    public void onClick(View view) {
      Intent intent = AlarmPagerActivity.newIntent(getActivity(), mAlarm.getId());
      startActivity(intent);
    }

    // bind is used to attach personal information to each list_item_alarm.
    public void bind(Alarm alarm) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d\nh:mm a");

      mAlarm = alarm;
      mTitleTextView.setText(mAlarm.getAlarmTitle());
      mInfoTextView.setText(dateFormat.format(mAlarm.getAlarmTime()));
      mIsAlarmActive.setChecked(mAlarm.isActive());
    }
  }  // End of AlarmHolder

  // The Adapter Part
  private class AlarmAdapter extends RecyclerView.Adapter<AlarmHolder> {

    private List<Alarm> mAlarms;

    public AlarmAdapter(List<Alarm> alarms) {
      mAlarms = alarms;
    }

    @Override
    public AlarmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
      return new AlarmHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(AlarmHolder holder, int position) {
      //Call bind on alarm to personalize the list_item_alarm
      Alarm alarm = mAlarms.get(position);
      holder.bind(alarm);
    }

    @Override
    public int getItemCount() {
      return mAlarms.size();
    }

    // Added to read from database
    public void setAlarms(List<Alarm> alarms) {
      mAlarms = alarms;
    }
  }
}
