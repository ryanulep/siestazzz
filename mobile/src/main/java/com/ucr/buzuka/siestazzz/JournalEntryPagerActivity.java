package com.ucr.buzuka.siestazzz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.ucr.buzuka.siestazzz.model.Journal;
import com.ucr.buzuka.siestazzz.model.JournalEntry;
import java.util.List;
import java.util.UUID;

/**
 * Created by Rick Boshae on 2/21/2018.
 *
 * JournalEntryPagerActivity.java
 * ========================================================================
 * JournalEntryPagerActivity.java allows the user to swipe left or right to view the next or
 * previous journal entry.
 */

public class JournalEntryPagerActivity extends AppCompatActivity {

  private static final String EXTRA_JOURNAL_ENTRY_ID = "com.buzuka.android.siestazzz"
      + ".journal_entry_id";

  private ViewPager mViewPager;
  private List<JournalEntry> mJournalEntries;

  // return appropriate journal entry intent.
  public static Intent newIntent(Context packageContext, UUID journalEntryId) {
    Intent intent = new Intent(packageContext, JournalEntryPagerActivity.class);
    intent.putExtra(EXTRA_JOURNAL_ENTRY_ID, journalEntryId);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_journal_entry_pager);

    UUID journalEntryId = (UUID) getIntent().getSerializableExtra(EXTRA_JOURNAL_ENTRY_ID);

    mViewPager = (ViewPager) findViewById(R.id.journal_entry_view_pager);

    mJournalEntries = Journal.get(this).getJournalEntries();
    FragmentManager fragmentManager = getSupportFragmentManager();
    mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
      @Override
      public Fragment getItem(int position) {
        JournalEntry journalEntry = mJournalEntries.get(position);
        return JournalEntryFragment.newInstance(journalEntry.getId());
      }

      @Override
      public int getCount() {
        return mJournalEntries.size();
      }
    });

    for (int i = 0; i < mJournalEntries.size(); ++i) {
      if (mJournalEntries.get(i).getId().equals(journalEntryId)) {
        mViewPager.setCurrentItem(i);
        break;
      }
    }
  }
}
