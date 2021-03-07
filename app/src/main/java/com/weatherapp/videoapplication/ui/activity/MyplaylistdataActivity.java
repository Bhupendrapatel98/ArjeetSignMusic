package com.weatherapp.videoapplication.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.weatherapp.videoapplication.R;
import com.weatherapp.videoapplication.adapter.AllVideosAdapter;
import com.weatherapp.videoapplication.adapter.HistoryAdapter;
import com.weatherapp.videoapplication.adapter.MyplaylistDataAdapter;
import com.weatherapp.videoapplication.model.HistoryModel;
import com.weatherapp.videoapplication.model.MyPlayListModel;
import com.weatherapp.videoapplication.model.MylistModel;
import com.weatherapp.videoapplication.model.VideoDataModel;
import com.weatherapp.videoapplication.network.Constant;
import com.weatherapp.videoapplication.network.GetRequest;
import com.weatherapp.videoapplication.network.RetrofitClint;
import com.weatherapp.videoapplication.room.DatabaseClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyplaylistdataActivity extends AppCompatActivity {

    private static final String TAG = "BollywoodActivity";
      RecyclerView boll_recycler;
    MyplaylistDataAdapter myplaylistDataAdapter;
    String playlistname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bollywood);

        playlistname = getIntent().getStringExtra("playlist");

         boll_recycler = findViewById(R.id.boll_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        boll_recycler.setLayoutManager(linearLayoutManager);

        showMyplaylistdata(playlistname);;
    }

    public  void  showMyplaylistdata(String playlistname){

        Log.d(TAG, "showMyplaylistdata: "+playlistname);

        class GetTasks extends AsyncTask<Void, Void, List<MyPlayListModel>> {

            @Override
            protected List<MyPlayListModel> doInBackground(Void... voids) {
                List<MyPlayListModel> taskList = DatabaseClient
                        .getInstance(MyplaylistdataActivity.this)
                        .getAppDatabase()
                        .myPlaylistDaoDao()
                        .getByName(playlistname);
                return taskList;
            }

            @Override
            protected void onPostExecute(List<MyPlayListModel> tasks) {
                super.onPostExecute(tasks);
                myplaylistDataAdapter = new MyplaylistDataAdapter(MyplaylistdataActivity.this, tasks);
                boll_recycler.setAdapter(myplaylistDataAdapter);
                myplaylistDataAdapter.notifyDataSetChanged();
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }


}