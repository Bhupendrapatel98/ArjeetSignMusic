package com.weatherapp.videoapplication.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.weatherapp.videoapplication.model.HistoryModel;
import com.weatherapp.videoapplication.model.MylistModel;

import java.util.List;

@Dao
public interface MylistDao {

    @Query("SELECT * FROM MylistModel")
    List<MylistModel> getAll();

    @Insert
    void insert(MylistModel historyModel);

    @Query("DELETE FROM MylistModel WHERE id1 = :id")
    void deleteById(int id);

    @Delete
    void delete(MylistModel historyModel);

    @Update
    void update(MylistModel historyModel);


}
