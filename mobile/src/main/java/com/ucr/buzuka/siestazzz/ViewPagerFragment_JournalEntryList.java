package com.ucr.buzuka.siestazzz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Rick Boshae on 1/23/2018.
 */

public class ViewPagerFragment_JournalEntryList extends Fragment {

    private RecyclerView mJournalEntryRecyclerView;
    private JournalEntryAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Create a view to inflate.
        View view  = inflater.inflate(R.layout.fragment_journal,container, false); // inflate is a function that converts a layout to a view.

        // Wire this class to the UI recycler view.
        mJournalEntryRecyclerView = (RecyclerView) view.findViewById(R.id.journal_recycler_view);
        mJournalEntryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;

    }

    public void updateUI(){
        Journal journal = Journal.get(getActivity());
        List<JournalEntry> journalEntries = journal.getJournalEntries();

        mAdapter = new JournalEntryAdapter(journalEntries);
        mJournalEntryRecyclerView.setAdapter(mAdapter);
    }

    // View Holder
    private class JournalEntryHolder extends RecyclerView.ViewHolder {
        // Inflate the list item and immediately pass it to the super ViewHolders constructor.
        public JournalEntryHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_joural_entry, parent, false));
        }
    }

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

        }

        @Override
        public int getItemCount() {
            return mJournalEntries.size();
        }
    }
}
