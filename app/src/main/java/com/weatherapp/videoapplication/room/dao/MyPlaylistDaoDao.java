package com.weatherapp.videoapplication.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.weatherapp.videoapplication.model.MyPlayListModel;

import java.util.List;

@Dao
public interface MyPlaylistDaoDao {

    @Query("SELECT * FROM MyPlayListModel")
    List<MyPlayListModel> getAll();

    @Query("SELECT * FROM MyPlayListModel WHERE playlistname = :playlistname")
    List<MyPlayListModel> getByName(String playlistname);

    @Insert
    void insert(MyPlayListModel myPlayListModel);

    @Query("DELETE FROM MyPlayListModel WHERE id = :id")
    void deleteById(int id);

    @Delete
    void delete(MyPlayListModel myPlayListModel);

    @Update
    void update(MyPlayListModel myPlayListModel);

}
