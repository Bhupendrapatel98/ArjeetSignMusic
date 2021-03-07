package com.weatherapp.videoapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.weatherapp.videoapplication.R;
import com.weatherapp.videoapplication.adapter.AllVideosAdapter;
import com.weatherapp.videoapplication.adapter.SelectedVideosAdapter;
import com.weatherapp.videoapplication.model.MyModel;
import com.weatherapp.videoapplication.model.MyPlayListModel;
import com.weatherapp.videoapplication.model.VideoDataModel;
import com.weatherapp.videoapplication.network.Constant;
import com.weatherapp.videoapplication.network.GetRequest;
import com.weatherapp.videoapplication.network.RetrofitClint;
import com.weatherapp.videoapplication.room.DatabaseClient;
import com.weatherapp.videoapplication.room.dao.BookmarkModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectionActivity extends AppCompatActivity {

    private static final String TAG = "SelectionActivity";
    RecyclerView selected_recycler;
    SelectedVideosAdapter selectedVideosAdapter;
    Button selected_save;
    private List<MyModel> myModels = new ArrayList<>();
  //  List<VideoDataModel> list = new ArrayList<>();
    String playlistname ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        playlistname = getIntent().getStringExtra("playlist");
        Log.d(TAG, "onCreate111111: "+playlistname);

        selected_recycler = findViewById(R.id.selected_recycler);
        selected_save = findViewById(R.id.selected_save);

        getAllVideos();

        selected_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               for (VideoDataModel.Datum videoDataModel : selectedVideosAdapter.checkedlist){
                   Log.d(TAG, "onClick23456789: ");
                   addmyplaylist(videoDataModel.getId(),videoDataModel.getTitle(),
                           videoDataModel.getImage(),videoDataModel.getLink(),videoDataModel.getCreatedAt(),
                           videoDataModel.getUpdatedAt());
                }
               selectedVideosAdapter.checkedlist.clear();
/*
                if (selectedVideosAdapter.checkedlist.size()>0){


                }*/
            }
        });

    }

    void getAllVideos(){

        RetrofitClint.getRetrofit(Constant.BASE_URL)
                .create(GetRequest.class)
                .getTopData()
                .enqueue(new Callback<VideoDataModel>() {
                    @Override
                    public void onResponse(Call<VideoDataModel> call, Response<VideoDataModel> response) {

                        Log.d(TAG, "onResponse: "+response);
                        Log.d(TAG, "onResponse: "+response.body());

                       // list.add(response.body());

                        selectedVideosAdapter = new SelectedVideosAdapter(SelectionActivity.this,response.body().getData());
                        LinearLayoutManager all_linearLayoutManager3 = new LinearLayoutManager(SelectionActivity.this,RecyclerView.VERTICAL,false);
                        selected_recycler.setLayoutManager(all_linearLayoutManager3);
                        selected_recycler.setAdapter(selectedVideosAdapter);
                        selectedVideosAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(Call<VideoDataModel> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.toString());
                    }
                });
    }

    void addmyplaylist(Long id,String title,String image,String link,String creteAt,String updateAt){

        class SaveTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {

                MyPlayListModel myPlayListModel = new MyPlayListModel();
                myPlayListModel.setId(Math.toIntExact(id));
                myPlayListModel.setPlaylistname(playlistname);
                myPlayListModel.setTitle(title);
                myPlayListModel.setImage(image);
                myPlayListModel.setLink(link);
                myPlayListModel.setCreatedAt(creteAt);

                //adding to database
                DatabaseClient.getInstance(SelectionActivity.this).getAppDatabase()
                        .myPlaylistDaoDao()
                        .insert(myPlayListModel);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(SelectionActivity.this, "Saved to playlist", Toast.LENGTH_SHORT).show();
            }
        }
        SaveTask st = new SaveTask();
        st.execute();
    }
}