package com.weatherapp.videoapplication.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.weatherapp.videoapplication.model.HistoryModel;

import java.util.List;

@Dao
public interface HistoryDao {

    @Query("SELECT * FROM HistoryModel")
    List<HistoryModel> getAll();

    @Insert
    void insert(HistoryModel historyModel);

    @Query("DELETE FROM HistoryModel WHERE id = :id")
    void deleteById(int id);

    @Delete
    void delete(HistoryModel historyModel);

    @Update
    void update(HistoryModel historyModel);

}
