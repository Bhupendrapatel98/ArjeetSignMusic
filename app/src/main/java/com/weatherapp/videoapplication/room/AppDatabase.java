package com.weatherapp.videoapplication.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.weatherapp.videoapplication.model.HistoryModel;
import com.weatherapp.videoapplication.model.MyPlayListModel;
import com.weatherapp.videoapplication.model.MylistModel;
import com.weatherapp.videoapplication.room.dao.BookmarkDao;
import com.weatherapp.videoapplication.room.dao.BookmarkModel;
import com.weatherapp.videoapplication.room.dao.HistoryDao;
import com.weatherapp.videoapplication.room.dao.MyPlaylistDaoDao;
import com.weatherapp.videoapplication.room.dao.MylistDao;

@Database(entities = {BookmarkModel.class, HistoryModel.class, MylistModel.class, MyPlayListModel.class}, version = 1)

public abstract class AppDatabase  extends RoomDatabase {
    public abstract BookmarkDao getForBookmarkDao();
    public abstract HistoryDao getHistoryDao();
    public abstract MylistDao getmylistDao();
    public abstract MyPlaylistDaoDao myPlaylistDaoDao();
}
