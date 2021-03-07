package com.weatherapp.videoapplication.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookmarkDao {

    @Query("SELECT * FROM BookmarkModel")
    List<BookmarkModel> getAll();

    @Insert
    void insert(BookmarkModel myNotes);

    @Query("DELETE FROM BookmarkModel WHERE id = :id")
    void deleteById(int id);

    @Delete
    void delete(BookmarkModel myNotes);

    @Update
    void update(BookmarkModel myNotes);

}
