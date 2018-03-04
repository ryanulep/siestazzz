package com.ucr.buzuka.siestazzz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ucr.buzuka.siestazzz.model.Journal;
import com.ucr.buzuka.siestazzz.model.JournalEntry;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Rick Boshae on 2/21/2018.
 */

public class JournalEntryFragment extends Fragment {

    private static final String ARG_JOURNAL_ENTRY_ID = "journal_entry_id"; // Used to attach the arguments bundle to a fragment.
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";

    private static final int REQUEST_SLEEP_DATE = 0;
    private static final int REQUEST_WAKE_DATE = 1;
    private static final int REQUEST_SLEEP_TIME = 2;
    private static final int REQUEST_WAKE_TIME = 3;

    private JournalEntry mJournalEntry;
    // private EditText mTitleField;
    private TextView mTitleField;
    private Button mSleepDateButton;
    private Button mWakeDateButton;
    private Button mSleepTimeButton;
    private Button mWakeTimeButton;
    private TextView mSleepDurationField;

    /**
     * Notes on public static JournalEntryFragment newInstance(UUID journalEntryId):
     * =========================================================================================================
     * To attach the arguments bundle to a fragment, you call Fragment.setArguments(Bundle). Attaching
     * arguments to a fragment must be done after the fragment is created but before it is added to an activity.
     * <p>
     * To hit this window. It is a common Android programming convection to add a static method named
     * newInstance() to the Fragment class. This method creates the fragment instance and bundles up and
     * sets it s arguments.
     */

    // Attaching arguments to a fragment
    public static JournalEntryFragment newInstance(UUID journalEntryId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_JOURNAL_ENTRY_ID, journalEntryId);

        JournalEntryFragment fragment = new JournalEntryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieving arguments.
        UUID journalEntryId = (UUID) getArguments().getSerializable(ARG_JOURNAL_ENTRY_ID);
        mJournalEntry = Journal.get(getActivity()).getJournalEntry(journalEntryId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journal_entry, container, false);

        mTitleField = (TextView) view.findViewById(R.id.journal_entry_date);
        mTitleField.setText(mJournalEntry.getWakeMonthAndDay());

        mSleepDurationField = (TextView) view.findViewById(R.id.journal_entry_sleep_duration_value);
        updateHoursSlept();

        mSleepDateButton = (Button) view.findViewById(R.id.journal_entry_sleep_date);
        updateSleepStartDate();

        mSleepDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mJournalEntry.getSleepDateAndTime());
                dialog.setTargetFragment(JournalEntryFragment.this, REQUEST_SLEEP_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mWakeDateButton = (Button) view.findViewById(R.id.journal_entry_wake_date);
        mWakeDateButton.setText(mJournalEntry.getNumericWakeDate());
        mWakeDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mJournalEntry.getWakeDateAndTime());
                dialog.setTargetFragment(JournalEntryFragment.this, REQUEST_WAKE_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mSleepTimeButton = (Button) view.findViewById(R.id.journal_entry_sleep_time);
        mSleepTimeButton.setText(mJournalEntry.getSleepHourAndMinute());
        mSleepTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mJournalEntry.getSleepDateAndTime());
                dialog.setTargetFragment(JournalEntryFragment.this, REQUEST_SLEEP_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });

        mWakeTimeButton = (Button) view.findViewById(R.id.journal_entry_wake_time);
        mWakeTimeButton.setText(mJournalEntry.getWakeHourAndMinute());
        mWakeTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mJournalEntry.getWakeDateAndTime());
                dialog.setTargetFragment(JournalEntryFragment.this, REQUEST_WAKE_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });

        return view;
    }

    /**
     * JournalEntry instances get modified in JournalEntryFragment and will need to be written out when
     * JournalEntryFragment is done. Adding an override to JournalFragment.onPause() updates Journal's
     * copy of the JournalEntry
     */
    @Override
    public void onPause() {
        super.onPause();

        Journal.get(getActivity()).updateJournalEntry(mJournalEntry);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_SLEEP_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mJournalEntry.setSleepDate(date);
            updateSleepStartDate();
            updateHoursSlept();

        }

        if (requestCode == REQUEST_WAKE_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mJournalEntry.setWakeDate(date);

            mWakeDateButton.setText(mJournalEntry.getNumericWakeDate());
            mTitleField.setText(mJournalEntry.getWakeMonthAndDay());
            updateHoursSlept();

        }

        if (requestCode == REQUEST_SLEEP_TIME) {
            Date time = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mJournalEntry.setSleepTime(time);

            mSleepTimeButton.setText(mJournalEntry.getSleepHourAndMinute());
            updateHoursSlept();
        }

        //TODO COMPLETE BELOW
        if (requestCode == REQUEST_WAKE_TIME) {
            Date time = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            mJournalEntry.setWakeTime(time);  // TODO Create Method.

            mWakeTimeButton.setText(mJournalEntry.getWakeHourAndMinute());
            updateHoursSlept();
        }

    }

    private void updateSleepStartDate() {
        mSleepDateButton.setText(mJournalEntry.getNumericSleepDate());
//        mWakeDateButton.setText(mJournalEntry.getNumericWakeDate());
    }

    private void updateHoursSlept() {
        mSleepDurationField.setText(String.valueOf(mJournalEntry.getHoursSlept()));
    }
}


// TODO: Wire up the rest of the UI

//        mTitleField = (EditText) view.findViewById(R.id.journal_entry_title);
//        mTitleField.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // This space intentionally left blank.
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                mJournalEntry.setTitle(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // This one too.
//            }
//        });