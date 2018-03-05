package com.ucr.buzuka.siestazzz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.ucr.buzuka.siestazzz.model.Alarm;
import com.ucr.buzuka.siestazzz.model.BellTower;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class AlarmDetailFragment extends Fragment {
    private static final String ARG_ALARM_ID = "alarm_id"; // Goes with bundle args for memory

    private Alarm mAlarm;
    private EditText mTitleField;
    private TextView mAlarmTime;
    private Switch mIsAlarmActive;
    private Button mCloseButton;

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

        mTitleField = (EditText) v.findViewById(R.id.alarm_title);
        mAlarmTime = (TextView) v.findViewById(R.id.alarm_time);
        mIsAlarmActive = (Switch) v.findViewById(R.id.alarm_active);
        mCloseButton = (Button) v.findViewById(R.id.closebutton);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d\nh:mm a");

        mTitleField.setText(mAlarm.getAlarmTitle());
        mAlarmTime.setText(dateFormat.format(mAlarm.getAlarmTime()));
        mIsAlarmActive.setChecked(mAlarm.isActive());

        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mAlarm.setAlarmTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This space intentionally left blank
            }
        });

        mIsAlarmActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isActive) {
                mAlarm.setActive(isActive);
            }
        });

        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return v;
    }
}
