package com.ucr.buzuka.siestazzz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ucr.buzuka.siestazzz.model.Journal;
import com.ucr.buzuka.siestazzz.model.JournalEntry;

import java.util.List;

/**
 * Created by Rick Boshae on 1/23/2018.
 */

public class ViewPagerFragment_JournalEntryList extends Fragment {
    private RecyclerView mJournalEntryRecyclerView;
    private JournalEntryAdapter mAdapter;
    private FloatingActionButton mAddJournalEntry;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Create a view to inflate.
        View view  = inflater.inflate(R.layout.fragment_journal_entry_list, container, false);
            // inflate is a function that converts a layout to a view.

        // Wire this class to the UI recycler view.
        mJournalEntryRecyclerView = (RecyclerView) view.findViewById(R.id.journal_recycler_view);
        mJournalEntryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        mAddJournalEntry = (FloatingActionButton) view.findViewById(R.id.add_journal_entry_fab);
        mAddJournalEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JournalEntry journalEntry = new JournalEntry();
                Journal.get(getActivity()).addJournalEntry(journalEntry);
                Intent intent = JournalEntryPagerActivity.newIntent(getActivity(), journalEntry.getId());
                startActivity(intent);
                //updateUI();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    /**
     * updateUI refreshes the recycler list.
     */
    public void updateUI(){
        Journal journal = Journal.get(getActivity());
        List<JournalEntry> journalEntries = journal.getJournalEntries();

        if (mAdapter == null) {
            mAdapter = new JournalEntryAdapter(journalEntries);
            mJournalEntryRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setJournalEntries(journalEntries); // Added to read from db
            mAdapter.notifyDataSetChanged();
        }
    }

    // View Holder
    private class JournalEntryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private JournalEntry mJournalEntry;

        private TextView mJournalEntryDateTextView;
        private TextView mJournalEntrySleepTextView;
        private TextView mJournalEntryWakeTextView;
        private TextView mJournalEntryHoursSleptTextView;
        private TextView mJournalEntryDebtTextView;


        // Inflate the list item and immediately pass it to the super ViewHolders constructor.
        public JournalEntryHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_joural_entry, parent, false));

            itemView.setOnClickListener(this);

            mJournalEntryDateTextView = (TextView) itemView.findViewById(R.id.journal_entry_date);
            mJournalEntrySleepTextView = (TextView) itemView.findViewById(R.id.journal_entry_sleep);
            mJournalEntryWakeTextView = (TextView) itemView.findViewById(R.id.journal_entry_wake);
            mJournalEntryHoursSleptTextView = (TextView) itemView.findViewById(R.id.journal_entry_hours_slept);
            mJournalEntryDebtTextView = (TextView) itemView.findViewById(R.id.journal_entry_hours_in_debt);

        }

        @Override
        public void onClick(View v) {
            Intent intent = JournalEntryPagerActivity.newIntent(getActivity(), mJournalEntry.getId());
            startActivity(intent);
        }

        public void bind (JournalEntry journalEntry) {

            mJournalEntry = journalEntry;
            mJournalEntryDateTextView.setText(mJournalEntry.getWakeMonthAndDay());
            mJournalEntrySleepTextView.setText(mJournalEntry.getSleepHourAndMinute());
            mJournalEntryWakeTextView.setText(mJournalEntry.getWakeHourAndMinute());
            mJournalEntryHoursSleptTextView.setText(String.valueOf(mJournalEntry.getHoursSlept())); // if a method returns int you must cast to string
            mJournalEntryDebtTextView.setText(String.valueOf(mJournalEntry.getSleepDebt()));

        }
    } // End of JournalEntryHolder

    // View Adapter
    private class JournalEntryAdapter extends RecyclerView.Adapter<JournalEntryHolder> {

        private List<JournalEntry> mJournalEntries;

        public JournalEntryAdapter(List<JournalEntry> journalEntries) {
            mJournalEntries = journalEntries;
        }

        @Override
        public JournalEntryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new JournalEntryHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(JournalEntryHolder holder, int position) {
            JournalEntry journalEntry = mJournalEntries.get(position);
            holder.bind(journalEntry);
        }

        @Override
        public int getItemCount() {
            return mJournalEntries.size();
        }

        // Added to read from database
        public void setJournalEntries(List<JournalEntry> journalEntries) {
            mJournalEntries = journalEntries;
        }
    }
}
