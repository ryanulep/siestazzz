package com.ucr.buzuka.siestazzz.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.ucr.buzuka.siestazzz.model.Session;
import java.util.List;

/**
 * Created by jakex on 2/26/2018. Session table for storing each session
 */
@Dao
public interface SessionDao {

  @Insert
  void insertAll(List<Session> item);

  @Insert
  void insertAll(Session... items);

  @Query("SELECT * FROM session")
  List<Session> getAll();
}
