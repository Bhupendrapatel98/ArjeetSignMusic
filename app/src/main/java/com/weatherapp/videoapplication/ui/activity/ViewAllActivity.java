package com.weatherapp.videoapplication.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;

import com.weatherapp.videoapplication.R;
import com.weatherapp.videoapplication.adapter.AllVideosAdapter;
import com.weatherapp.videoapplication.model.VideoDataModel;
import com.weatherapp.videoapplication.network.Constant;
import com.weatherapp.videoapplication.network.GetRequest;
import com.weatherapp.videoapplication.network.RetrofitClint;
import com.weatherapp.videoapplication.utils.MyUtlls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllActivity extends AppCompatActivity {

    private static final String TAG = "ViewAllActivity";
    RecyclerView boll_recycler;
    AllVideosAdapter allVideosAdapter;
    public  boolean isscorlling = false;
    int page=1;
    LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        boll_recycler = findViewById(R.id.boll_recycler);

        mLinearLayoutManager = new LinearLayoutManager(ViewAllActivity.this,RecyclerView.VERTICAL,false);
        boll_recycler.setLayoutManager(mLinearLayoutManager);

        getAllVideos(page);

        boll_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

               //mLinearLayoutManager.getChildCount();//jitne visible hai unhe ese nikalte h
               // mLinearLayoutManager.getItemCount(); //adapter me total kitne hai
               // mLinearLayoutManager.findFirstVisibleItemPosition();//jitne item scroll kr chuka hai

                int total = mLinearLayoutManager.getItemCount();
                int firstVisibleItemCount = mLinearLayoutManager.findFirstVisibleItemPosition();
                int lastVisibleItemCount = mLinearLayoutManager.findLastVisibleItemPosition();

                if (!isscorlling) {
                    if (total > 0)
                        if ((total - 1) == lastVisibleItemCount) {
                            isscorlling = true;
                            getAllVideos(page);
                        }
                }
            }
        });
    }
    void getAllVideos(int pagei){

        RetrofitClint.getRetrofit(Constant.BASE_URL)
                .create(GetRequest.class)
                .getTopDatap(""+pagei)
                .enqueue(new Callback<VideoDataModel>() {
                    @Override
                    public void onResponse(Call<VideoDataModel> call, Response<VideoDataModel> response) {

                        Log.d(TAG, "onResponse: "+response);
                        Log.d(TAG, "onResponse: "+response.body());

                        if (pagei==1){
                            allVideosAdapter = new AllVideosAdapter(ViewAllActivity.this,response.body().getData());
                            boll_recycler.setAdapter(allVideosAdapter);
                        }else {
                            allVideosAdapter.addAllResult(response.body().getData());
                        }
                        isscorlling=false;
                        page++;
                    }

                    @Override
                    public void onFailure(Call<VideoDataModel> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.toString());
                    }
                });
    }

}