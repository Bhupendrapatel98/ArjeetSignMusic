package com.weatherapp.videoapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.PictureInPictureParams;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;
import android.view.Display;
import android.view.View;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.weatherapp.videoapplication.R;
import com.weatherapp.videoapplication.adapter.AllAdapter;
import com.weatherapp.videoapplication.adapter.TopAdapter;
import com.weatherapp.videoapplication.model.VideoDataModel;
import com.weatherapp.videoapplication.network.Constant;
import com.weatherapp.videoapplication.network.GetRequest;
import com.weatherapp.videoapplication.network.RetrofitClint;
import com.weatherapp.videoapplication.network.onClickInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//download zipp file https://developers.google.com/youtube/android/player/downloads
//2. unzip and open lib and copy .jar file
//3. open android->project->app->libs paste
//4. right clik on jar file and click add as librery

public class PlayvideoActivity3 extends YouTubeBaseActivity implements onClickInterface{

    private static final String TAG = "PlayvideoActivity3";
    String video_id;
    RecyclerView player_recycler;
    AllAdapter adapter;
    YouTubePlayerView youTubePlayerView;
    public String url;
    private  YouTubePlayer mYouTubePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playvideo3);

        player_recycler = findViewById(R.id.player_recycler);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);

        url = getIntent().getStringExtra("link");
        Log.d(TAG, "onCreate11: "+url);

        startPlayer(url);
        getTopVideos();
    }

    @Override
    public void setClick(String link) {
        Log.d(TAG, "setClickrt: "+link);
        url = link;
        Log.d(TAG, "setClick: "+url);

        video_id = extractYTId(url);
        mYouTubePlayer.loadVideo(video_id);

    }

    void startPlayer(String link_url){

        video_id = extractYTId(link_url);

        youTubePlayerView.initialize(Constant.API_KEY,
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        //only load the video but not play
                        // youTubePlayer.cueVideo("BadBAMnPX0I");

                        mYouTubePlayer = youTubePlayer;

                        //Playing videos involves passing along the YouTube video key (do not include the full URL):
                       youTubePlayer.loadVideo(video_id);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                        Log.d(TAG, "onInitializationFailure: "+youTubeInitializationResult);
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

                        adapter = new AllAdapter(PlayvideoActivity3.this,response.body().getData(),PlayvideoActivity3.this::setClick);
                        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(PlayvideoActivity3.this,RecyclerView.VERTICAL,false);
                        player_recycler.setLayoutManager(linearLayoutManager1);
                        player_recycler.setAdapter(adapter);

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