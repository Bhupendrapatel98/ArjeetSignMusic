package com.weatherapp.videoapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.weatherapp.videoapplication.R;
import com.weatherapp.videoapplication.adapter.AllVideosAdapter;
import com.weatherapp.videoapplication.model.VideoDataModel;
import com.weatherapp.videoapplication.network.Constant;
import com.weatherapp.videoapplication.network.GetRequest;
import com.weatherapp.videoapplication.network.RetrofitClint;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BollywoodActivity extends AppCompatActivity {

    private static final String TAG = "BollywoodActivity";
      RecyclerView boll_recycler;
    AllVideosAdapter allVideosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bollywood);

         boll_recycler = findViewById(R.id.boll_recycler);

       getAllVideos();
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

                        allVideosAdapter = new AllVideosAdapter(BollywoodActivity.this,response.body().getData());
                        LinearLayoutManager all_linearLayoutManager3 = new LinearLayoutManager(BollywoodActivity.this,RecyclerView.VERTICAL,false);
                        boll_recycler.setLayoutManager(all_linearLayoutManager3);
                        boll_recycler.setAdapter(allVideosAdapter);
                    }

                    @Override
                    public void onFailure(Call<VideoDataModel> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.toString());
                    }
                });
    }

}