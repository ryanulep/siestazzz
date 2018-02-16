package com.ucr.buzuka.siestazzz;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;

/**
 * Created by Rick Boshae on 1/23/2018.
 */

public class ViewPagerFragment_AlarmList extends Fragment {

    private RecyclerView mAlarmRecyclerView;
    private AlarmAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_alarms,container, false);

        mAlarmRecyclerView = (RecyclerView) rootView.findViewById(R.id.alarm_recycler_view);
        mAlarmRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return rootView; // inflate is a function that converts a layout to a view.
    }

    private void updateUI() {
        BellTower bellTower = BellTower.get(getActivity());
        List<Alarm> alarms = bellTower.getAlarms();

        mAdapter = new AlarmAdapter(alarms);
        mAlarmRecyclerView.setAdapter(mAdapter);
    }

    // Implementing a ViewHolder and an Adapter
    // The ViewHolder Part
    private class AlarmHolder extends RecyclerView.ViewHolder {
        // In AlarmHolder constructor inflate list_item_alarm. Immediately pass it into super(...)
        public AlarmHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_alarm, parent, false));
        }
    }

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

        }

        @Override
        public int getItemCount() {
            return mAlarms.size();
        }
    }
}
