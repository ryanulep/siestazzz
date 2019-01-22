package com.ucr.buzuka.siestazzz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.ucr.buzuka.siestazzz.model.Journal;
import com.ucr.buzuka.siestazzz.model.JournalEntry;
import com.ucr.buzuka.siestazzz.util.DataExtract;
import java.util.Date;
import java.util.UUID;

public class JournalEntryFragment extends Fragment {

  private static final String TAG = "JournalEntryFragment";

  private static final String ARG_JOURNAL_ENTRY_ID = "journal_entry_id"; // Used to attach the
  // arguments bundle to a fragment.
  private static final String DIALOG_DATE = "DialogDate";
  private static final String DIALOG_TIME = "DialogTime";

  private static final int REQUEST_SLEEP_DATE = 0;
  private static final int REQUEST_WAKE_DATE = 1;
  private static final int REQUEST_SLEEP_TIME = 2;
  private static final int REQUEST_WAKE_TIME = 3;

  private JournalEntry mJournalEntry;
  private TextView mTitleField;
  private Button mSleepDateButton;
  private Button mWakeDateButton;
  private Button mSleepTimeButton;
  private Button mWakeTimeButton;
  private Button mCloseButton;
  private TextView mSleepDurationField;
  private EditText mSleepNotes;
  private Button mSleepRecordingPlayBackButton;
  private Button mDeleteThisJournalEntry;
  private boolean markedForDeletion = false; // Used to delete journalEntry OnPause

  /**
   * Notes on public static JournalEntryFragment newInstance(UUID journalEntryId):
   *
   * To attach the arguments bundle to a fragment, you call Fragment.setArguments(Bundle). Attaching
   * arguments to a fragment must be done after the fragment is created but before it is added to an
   * activity.
   * <p>
   * To hit this window. It is a common Android programming convection to add a static method named
   * newInstance() to the Fragment class. This method creates the fragment instance and bundles up
   * and sets it s arguments.
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
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_journal_entry, container, false);
    GraphView graph = (GraphView) view.findViewById(R.id.graph);
    // DONE: FIX Crashing if series and series2 is null.
    if (mJournalEntry.getSoundDataPath() != null) {
      LineGraphSeries<DataPoint> series;
      LineGraphSeries<DataPoint> series2;
      DataPoint[] sound = DataExtract.prepareVolumeData(mJournalEntry.getSoundDataPath());
      if (sound != null) {
        Log.d("CONVERT", "Sound!=null" + sound);
        series = new LineGraphSeries<>(sound);
        graph.addSeries(series);
        graph.getViewport().setMaxX(series.getHighestValueX());
        graph.getViewport().setMinX(0);
      }

      DataPoint[] motion = DataExtract.prepareSpeedData(mJournalEntry.getSoundDataPath());
      if (motion != null) {
        series2 = new LineGraphSeries<>(motion);
        series2.setColor(Color.GREEN);
        graph.addSeries(series2);
        graph.getViewport().setMaxX(series2.getHighestValueX());
        graph.getViewport().setMinX(0);
      }
    }
    mSleepNotes = (EditText) view.findViewById(R.id.journal_entry_notes);
    mSleepNotes.setText(mJournalEntry.getSleepNotes());
    mSleepNotes.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        mJournalEntry.setSleepNotes(s.toString());
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });

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
        DatePickerFragment dialog = DatePickerFragment
            .newInstance(mJournalEntry.getSleepDateAndTime());
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
        DatePickerFragment dialog = DatePickerFragment
            .newInstance(mJournalEntry.getWakeDateAndTime());
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
        TimePickerFragment dialog = TimePickerFragment
            .newInstance(mJournalEntry.getSleepDateAndTime());
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
        TimePickerFragment dialog = TimePickerFragment
            .newInstance(mJournalEntry.getWakeDateAndTime());
        dialog.setTargetFragment(JournalEntryFragment.this, REQUEST_WAKE_TIME);
        dialog.show(manager, DIALOG_TIME);
      }
    });

    mCloseButton = (Button) view.findViewById(R.id.closebutton);
    mCloseButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        getActivity().onBackPressed();
      }
    });

    mDeleteThisJournalEntry = (Button) view.findViewById(R.id.journal_entry_delete_button);
    mDeleteThisJournalEntry.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        markedForDeletion = true;
        Journal.get(getActivity()).deleteJournalEntry(mJournalEntry);
        getActivity().finish();
      }
    });

    mSleepRecordingPlayBackButton = (Button) view.findViewById(R.id.sleep_session_play_button);
    mSleepRecordingPlayBackButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        //set up MediaPlayer
        MediaPlayer mp = new MediaPlayer();

        try {
          String path = mJournalEntry.getSoundDataPath() + "/recording.3gp";
          mp.setDataSource(path);
          mp.prepare();
          mp.start();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });

    return view;
  }

  /**
   * JournalEntry instances get modified in JournalEntryFragment and will need to be written out
   * when JournalEntryFragment is done. Adding an override to JournalFragment.onPause() updates
   * Journal's copy of the JournalEntry
   */
  @Override
  public void onPause() {
    super.onPause();

    if (!markedForDeletion) {
      Journal.get(getActivity()).updateJournalEntry(mJournalEntry);
    }
    Log.d(TAG, "onPause");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy");
  }

  @Override
  public void onStop() {
    super.onStop();
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

    if (requestCode == REQUEST_WAKE_TIME) {
      Date time = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
      mJournalEntry.setWakeTime(time);
      mWakeTimeButton.setText(mJournalEntry.getWakeHourAndMinute());
      updateHoursSlept();
    }
  }

  private void updateSleepStartDate() {
    mSleepDateButton.setText(mJournalEntry.getNumericSleepDate());
  }

  private void updateHoursSlept() {
    mSleepDurationField.setText(String.valueOf(mJournalEntry.getHoursSlept()));
  }
}