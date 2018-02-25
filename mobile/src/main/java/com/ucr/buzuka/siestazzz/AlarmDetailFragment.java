package com.ucr.buzuka.siestazzz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.ucr.buzuka.siestazzz.model.Alarm;
import com.ucr.buzuka.siestazzz.model.BellTower;

import java.util.UUID;

public class AlarmDetailFragment extends Fragment {
    private static final String ARG_ALARM_ID = "alarm_id"; // Goes with bundle args for memory

    private Alarm mAlarm;
    private EditText mTitleField;
    private CheckBox mIsAlarmActive;

    // TODO: Add additional UI implementations

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AlarmDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlarmDetailFragment newInstance(UUID alarmId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ALARM_ID, alarmId);

        AlarmDetailFragment fragment = new AlarmDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieving arguments.
        UUID alarmId = (UUID) getArguments().getSerializable(ARG_ALARM_ID);
        mAlarm = BellTower.get(getActivity()).getAlarm(alarmId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_alarm_detail, container, false);
        // TODO: Wire up the fragment to the UI
        mTitleField = (EditText) v.findViewById(R.id.alarm_title);
        mIsAlarmActive = (CheckBox) v.findViewById(R.id.alarm_active);

        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mAlarm.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This space intentionally left blank
            }
        });

        mIsAlarmActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isActive) {
                mIsAlarmActive.setActivated(isActive);
            }
        });

        return v;
    }
}
