package com.ucr.buzuka.siestazzz.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.ucr.buzuka.siestazzz.model.SensorReadout;
import java.util.List;

/**
 * Created by jakex on 2/25/2018. Room entity insertion and querying
 */

@Dao
public interface SensorReadoutDao {

  @Insert
  void insertAll(List<SensorReadout> items);

  @Insert
  void insertAll(SensorReadout... items);

  /**
   * get count
   */
  @Query("SELECT COUNT(*) from sensorreadout")
  int countItems();

  /**
   * get all item
   */
  @Query("SELECT * FROM sensorreadout ORDER BY sessionID")
  List<SensorReadout> getAll();

  /**
   * get one specific session
   */
  @Query("SELECT * FROM sensorreadout WHERE sessionID = :sessionID")
  List<SensorReadout> findById(String sessionID);

  /**
   * clear table
   */
  @Query("DELETE FROM sensorreadout")
  void clearTable();
}
