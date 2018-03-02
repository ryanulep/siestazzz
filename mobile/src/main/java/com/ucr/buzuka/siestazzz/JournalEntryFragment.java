package com.ucr.buzuka.siestazzz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ucr.buzuka.siestazzz.model.Journal;
import com.ucr.buzuka.siestazzz.model.JournalEntry;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Rick Boshae on 2/21/2018.
 */

public class JournalEntryFragment extends Fragment {

    private static final String ARG_JOURNAL_ENTRY_ID = "journal_entry_id"; // Used to attach the arguments bundle to a fragment.
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_WAKE_DATE = 1;

    private JournalEntry mJournalEntry;
    // private EditText mTitleField;
    private TextView mTitleField;
    private Button mSleepDateButton;
    private Button mWakeDateButton;
    private Button mSleepTimeButton;
    private Button mWakeTimeButton;

    /**
     * Notes on public static JournalEntryFragment newInstance(UUID journalEntryId):
     * =========================================================================================================
     * To attach the arguments bundle to a fragment, you call Fragment.setArguments(Bundle). Attaching
     * arguments to a fragment must be done after the fragment is created but before it is added to an activity.
     *
     * To hit this window. It is a common Android programming convection to add a static method named
     * newInstance() to the Fragment class. This method creates the fragment instance and bundles up and
     * sets it s arguments.
     *
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journal_entry, container, false);

        mTitleField = (TextView) view.findViewById(R.id.journal_entry_date);
        mTitleField.setText(mJournalEntry.getWakeMonthAndDay());

        mSleepDateButton = (Button) view.findViewById(R.id.journal_entry_sleep_date);
        updateSleepStartDate();
        mSleepDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mJournalEntry.getSleepDateAndTime());
                dialog.setTargetFragment(JournalEntryFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mWakeDateButton = (Button) view.findViewById(R.id.journal_entry_wake_date);
        mWakeDateButton.setText(mJournalEntry.getNumericWakeDate());
        mWakeDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mJournalEntry.getSleepDateAndTime());
                dialog.setTargetFragment(JournalEntryFragment.this, REQUEST_WAKE_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mSleepTimeButton = (Button) view.findViewById(R.id.journal_entry_sleep_time);
        mSleepTimeButton.setText(mJournalEntry.getSleepHourAndMinute());
        mSleepDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mJournalEntry.getSleepDateAndTime());
                dialog.setTargetFragment(JournalEntryFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

//        mSleepDateButton = (Button) view.findViewById(R.id.journal_entry_sleep_time);
//
//        mWakeDateButton = (Button) view.findViewById(R.id.journal_entry_wake_time);


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mJournalEntry.setSleepDate(date);

            // Auto set wake time to desired sleep time + Sleep time
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mJournalEntry.getSleepDateAndTime());
            calendar.add(Calendar.HOUR, mJournalEntry.getDesiredHoursOfSleep());

            Log.d("JournalEntryFragment", "Sleep time: " + mJournalEntry.getSleepDateAndTime());
            Log.d("JournalEntryFragment", "Wake time after add: " + calendar.getTime());
            mJournalEntry.setWakeDate(calendar.getTime());
            mWakeDateButton.setText(mJournalEntry.getNumericWakeDate());
            updateSleepStartDate();
        }

        if (requestCode == REQUEST_WAKE_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mJournalEntry.setWakeDate(date);

            mWakeDateButton.setText(mJournalEntry.getNumericWakeDate());
            updateSleepStartDate();
        }

    }

    private void updateSleepStartDate() {
        mSleepDateButton.setText(mJournalEntry.getNumericSleepDate());
      //  mWakeDateButton.setText(mJournalEntry.getNumericWakeDate());
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