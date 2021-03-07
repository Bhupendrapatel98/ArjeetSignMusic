package com.weatherapp.videoapplication.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weatherapp.videoapplication.R;
import com.weatherapp.videoapplication.adapter.AllVideosAdapter;
import com.weatherapp.videoapplication.model.VideoDataModel;
import com.weatherapp.videoapplication.network.Constant;
import com.weatherapp.videoapplication.network.GetRequest;
import com.weatherapp.videoapplication.network.RetrofitClint;
import com.weatherapp.videoapplication.ui.activity.BollywoodActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArjeetPlayLisyFragment extends Fragment {

    private static final String TAG = "ArjeetPlayLisyFragment";
    CardView bollywood,coke,arjeet;
    ImageView poster_image,poster_image2,poster_image3;
    TextView count1,count2,count3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_arjeet_play_lisy, container, false);

        bollywood = view.findViewById(R.id.bollywood);
        coke = view.findViewById(R.id.coke);
        arjeet = view.findViewById(R.id.arjeet);
        poster_image = view.findViewById(R.id.poster_image1);
        poster_image2 = view.findViewById(R.id.poster_image2);
        poster_image3 = view.findViewById(R.id.poster_image3);
        count1 = view.findViewById(R.id.count1);
        count2 = view.findViewById(R.id.count2);
        count3 = view.findViewById(R.id.count3);

        getAllVideos();

        bollywood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BollywoodActivity.class));
            }
        });

        coke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BollywoodActivity.class));
            }
        });

        arjeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BollywoodActivity.class));
            }
        });

        return view;
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

                        String vid = extractYTId(response.body().getData().get(0).getLink());
                        String u = "https://img.youtube.com/vi/"+vid+"/0.jpg";

                        Picasso.get().load(u).into(poster_image);
                        Picasso.get().load(u).into(poster_image2);
                        Picasso.get().load(u).into(poster_image3);

/*
                        Picasso.get().
                                load(Constant.Image_URL+response.body().getData()
                                 .get(0).getImage())
                                .into(poster_image);

                        Picasso.get().
                                load(Constant.Image_URL+response.body().getData()
                                        .get(0).getImage())
                                .into(poster_image2);

                        Picasso.get().
                                load(Constant.Image_URL+response.body().getData()
                                        .get(0).getImage())
                                .into(poster_image3);*/

                        count1.setText(""+response.body().getData().size());
                        count2.setText(""+response.body().getData().size());
                        count3.setText(""+response.body().getData().size());

                    }

                    @Override
                    public void onFailure(Call<VideoDataModel> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.toString());
                    }
                });
    }

    public String extractYTId(String ytUrl) {
        String vId = null;
        Pattern pattern = Pattern.compile("^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ytUrl);
        if (matcher.matches()){
            vId = matcher.group(1);
        }
        return vId;
    }
}