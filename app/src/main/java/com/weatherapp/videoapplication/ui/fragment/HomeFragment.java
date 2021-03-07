package com.weatherapp.videoapplication.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;
import com.weatherapp.videoapplication.R;
import com.weatherapp.videoapplication.adapter.AllAdapter;
import com.weatherapp.videoapplication.adapter.AllVideosAdapter;
import com.weatherapp.videoapplication.adapter.MainSliderAdapter;
import com.weatherapp.videoapplication.adapter.TopAdapter;
import com.weatherapp.videoapplication.model.VideoDataModel;
import com.weatherapp.videoapplication.network.Constant;
import com.weatherapp.videoapplication.network.GetRequest;
import com.weatherapp.videoapplication.network.RetrofitClint;
import com.weatherapp.videoapplication.room.DatabaseClient;
import com.weatherapp.videoapplication.room.dao.BookmarkModel;
import com.weatherapp.videoapplication.ui.activity.PlayvideoActivity3;
import com.weatherapp.videoapplication.ui.activity.ViewAllActivity;
import com.weatherapp.videoapplication.utils.PicassoImageLoadingService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.Slider;
import ss.com.bannerslider.event.OnSlideClickListener;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
     Slider banner_slider;
    RecyclerView top_recycler,recent_recycler,All_recycler;
    TopAdapter adapter;
    AllVideosAdapter allVideosAdapter;
    NestedScrollView scroll_layout;
    AVLoadingIndicatorView avi;
    TextView view_all,recent_all,all_more;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Slider.init(new PicassoImageLoadingService(getContext()));

        All_recycler = view.findViewById(R.id.All_recycler);
        recent_recycler = view.findViewById(R.id.recent_recycler);
        top_recycler = view.findViewById(R.id.top_recycler);
        banner_slider = view.findViewById(R.id.banner_slider1);
        scroll_layout = view.findViewById(R.id.scroll_layout);
        avi = view.findViewById(R.id.avi);
        view_all = view.findViewById(R.id.view_all);
        recent_all = view.findViewById(R.id.recent_all);
        all_more = view.findViewById(R.id.all_more);

        view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ViewAllActivity.class));
            }
        });

        recent_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ViewAllActivity.class));
            }
        });

        all_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ViewAllActivity.class));
            }
        });

        //start loader
        startAnim();

        getBannerslider();
        getTopVideos();
        getRecentVideos();
        getAllVideos();


        return view;

    }

    void getBannerslider(){

        RetrofitClint.getRetrofit(Constant.BASE_URL)
                .create(GetRequest.class)
                .getRecentData()
                .enqueue(new Callback<VideoDataModel>() {
                    @Override
                    public void onResponse(Call<VideoDataModel> call, Response<VideoDataModel> response) {

                        Log.d(TAG, "onResponse: "+response);
                        Log.d(TAG, "onResponse: "+response.body());

                        banner_slider.setAdapter(new MainSliderAdapter(response.body().getData()));

                        banner_slider.setOnSlideClickListener(new OnSlideClickListener() {
                            @Override
                            public void onSlideClick(int position) {
                                startActivity(new Intent(getContext(), PlayvideoActivity3.class)
                                   .putExtra("link",response.body().getData().get(position).getLink()));
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<VideoDataModel> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.toString());
                    }
                });
    }

    void getTopVideos(){

        RetrofitClint.getRetrofit(Constant.BASE_URL)
                .create(GetRequest.class)
                .getTopData()
                .enqueue(new Callback<VideoDataModel>() {
                    @Override
                    public void onResponse(Call<VideoDataModel> call, Response<VideoDataModel> response) {

                        Log.d(TAG, "onResponse: "+response);
                        Log.d(TAG, "onResponse: "+response.body());

                        adapter = new TopAdapter(getContext(),response.body().getData());
                        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                        top_recycler.setLayoutManager(linearLayoutManager1);
                        top_recycler.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<VideoDataModel> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.toString());
                    }
                });
    }

    void getRecentVideos(){

        RetrofitClint.getRetrofit(Constant.BASE_URL)
                .create(GetRequest.class)
                .getRecentData()
                .enqueue(new Callback<VideoDataModel>() {
                    @Override
                    public void onResponse(Call<VideoDataModel> call, Response<VideoDataModel> response) {

                        Log.d(TAG, "onResponse: "+response);
                        Log.d(TAG, "onResponse: "+response.body());

                        adapter = new TopAdapter(getContext(),response.body().getData());
                        LinearLayoutManager recent_linearLayoutManager2 = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                        recent_recycler.setLayoutManager(recent_linearLayoutManager2);
                        recent_recycler.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<VideoDataModel> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.toString());
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


                        //close loader
                        stopAnim();

                        scroll_layout.setVisibility(View.VISIBLE);

                        allVideosAdapter = new AllVideosAdapter(getContext(),response.body().getData());
                        LinearLayoutManager all_linearLayoutManager3 = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                        All_recycler.setLayoutManager(all_linearLayoutManager3);
                        All_recycler.setAdapter(allVideosAdapter);
                    }

                    @Override
                    public void onFailure(Call<VideoDataModel> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.toString());
                    }
                });
    }

    void startAnim(){
        avi.show();
    }

    void stopAnim(){
        avi.hide();
    }

}