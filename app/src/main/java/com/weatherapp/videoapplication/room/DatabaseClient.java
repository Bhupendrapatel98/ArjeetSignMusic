package com.weatherapp.videoapplication.room;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    private Context context;
    private static DatabaseClient Instance;
    private AppDatabase appDatabase;

    public DatabaseClient(Context context) {
        this.context = context;
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "arjjetDatabasenew").build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (Instance == null) {
            Instance = new DatabaseClient(mCtx);
        }
        return Instance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

}
