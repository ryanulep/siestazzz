package com.ucr.buzuka.siestazzz;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ucr.buzuka.siestazzz.model.Journal;
import com.ucr.buzuka.siestazzz.model.JournalEntry;

import java.util.UUID;

/**
 * Created by Rick Boshae on 2/21/2018.
 */

public class JournalEntryFragment extends Fragment {

    private static final String ARG_JOURNAL_ENTRY_ID = "journal_entry_id"; // Used to attach the arguments bundle to a fragment.

    private JournalEntry mJournalEntry;
    // private EditText mTitleField;
    private TextView mDateField;

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

        mDateField = (TextView) view.findViewById(R.id.journal_entry_date);
        mDateField.setText(mJournalEntry.getDateMonthAndDay());

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

        return view;
    }
}
