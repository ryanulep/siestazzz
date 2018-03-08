package com.ucr.buzuka.siestazzz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import java.util.Date;
import java.util.UUID;

public class AlarmDetailFragment extends Fragment {
    private static final String ARG_ALARM_ID = "alarm_id"; // Goes with bundle args for memory
    private static final String DIALOG_TIME = "DialogTime";

    private static final int REQUEST_ALARM_TIME = 5;

    private Alarm mAlarm;
    private EditText mTitleField;
    private TextView mAlarmTime;
    private Switch mIsAlarmActive;
    private Button mCloseButton;
    private Button mDeleteAlarmButton;
    private boolean markedForDeletion = false;
    private Button mSetAlarmButton;

    /**
     * Notes on public static AlarmFragment newInstance(UUID alarmId):
     * =========================================================================================================
     * To attach the arguments bundle to a fragment, you call Fragment.setArguments(Bundle). Attaching
     * arguments to a fragment must be done after the fragment is created but before it is added to an activity.
     * <p>
     * To hit this window. It is a common Android programming convection to add a static method named
     * newInstance() to the Fragment class. This method creates the fragment instance and bundles up and
     * sets it s arguments.
     */

    // Attaching arguments to a fragment
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

        mSetAlarmButton = (Button) v.findViewById(R.id.set_alarm_button);

        mSetAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mAlarm.getAlarmTime());
                dialog.setTargetFragment(AlarmDetailFragment.this, REQUEST_ALARM_TIME);
                dialog.show(manager, DIALOG_TIME);

            }
        });

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
                Log.i("AlarmDetailFragment", "mAlarm.setActive(isActive) — get switch value " + isActive);
            }
        });

        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        mDeleteAlarmButton = v.findViewById(R.id.alarm_delete_button);
        mDeleteAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markedForDeletion = true;
                BellTower.get(getActivity()).deleteAlarm(mAlarm);
                getActivity().finish();
            }
        });

        return v;
    }

    /**
     * JournalEntry instances get modified in JournalEntryFragment and will need to be written out when
     * JournalEntryFragment is done. Adding an override to JournalFragment.onPause() updates Journal's
     * copy of the JournalEntry
     */
    @Override
    public void onPause() {
        super.onPause();
        if(!markedForDeletion){
            BellTower.get(getActivity()).updateAlarm(mAlarm);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_ALARM_TIME) {
            Date time = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            //mAlarm.setAlarmTime(time);
            mAlarm.setAlarmTime(getContext(), time);
            // TODO: Update alarm time view.
        }

    }
}
