package com.ucr.buzuka.siestazzz;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Rick Boshae on 1/23/2018.
 */

public class ViewPagerFragment_JournalEntryList extends Fragment {
    private RecyclerView mJournalEntryRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Create a view to inflate.
        View view  = inflater.inflate(R.layout.fragment_journal,container, false); // inflate is a function that converts a layout to a view.

        // Wire this class to the UI recycler view.
        mJournalEntryRecyclerView = (RecyclerView) view.findViewById(R.id.journal_recycler_view);
        mJournalEntryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;

    }
}
