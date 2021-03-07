package com.weatherapp.videoapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.PictureInPictureParams;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;
import android.util.SparseArray;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.weatherapp.videoapplication.R;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingDeque;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YouTubeUriExtractor;
import at.huber.youtubeExtractor.YtFile;

public class PlayVideoActivity extends AppCompatActivity {

    PlayerView playerView;
    ProgressBar progressBar;
    ImageView btn_fullscreen;
    SimpleExoPlayer simpleExoPlayer;
    boolean flag=false;

   /*  String YOUTUBE_VIDEO_ID = "FOA9iyxsW_A";
     String BASE_URL = "https://www.youtube.com";
     String mYoutubeLink = BASE_URL+"/watch?v="+YOUTUBE_VIDEO_ID;*/
     String mYoutubeLink = "https://www.youtube.com/watch?v=YfMBb3xsSZk";
    // String mYoutubeLink ;
     Uri video;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        playerView = findViewById(R.id.player_view);
        progressBar = findViewById(R.id.progress_bar);
        btn_fullscreen = playerView.findViewById(R.id.bt_fullscreen);

       // mYoutubeLink = getIntent().getStringExtra("link");
       // Log.d("dfghjkfghj", "onCreate: "+mYoutubeLink);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
        params.width = params.MATCH_PARENT;
        params.height = (int) ( 200 * getApplicationContext().getResources().getDisplayMetrics().density);
        playerView.setLayoutParams(params);

       // extractYoutubeUrl();

        //Uri video = Uri.parse("https://file-examples-com.github.io/uploads/2017/04/file_example_MP4_480_1_5MG.mp4");
       // Uri video = Uri.parse("https://www.youtube.com/watch?v=IBr798ZSOx4");

        LoadControl loadControl = new DefaultLoadControl();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory());
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(PlayVideoActivity.this,trackSelector
        ,loadControl);

        DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory("exoplayer_video");
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        //you tube video extractor
        @SuppressLint("StaticFieldLeak")YouTubeUriExtractor ytEx = new YouTubeUriExtractor(this) {
            @Override
            public void onUrisAvailable(String videoId, String videoTitle, SparseArray<YtFile> ytFiles) {
                Log.d("dfghjklytr", "onUrisAvailable: "+videoId);
                Log.d("dfghjkytr", "onUrisAvailable: "+videoTitle);
                Log.d("xcvbnmtr", "onUrisAvailable: "+ytFiles);
                if (ytFiles != null) {
                    int itag = 18;
                    String downloadUrl = ytFiles.get(itag).getUrl();
                    Log.d("dfghjkxfgh", "onUrisAvailable: "+downloadUrl);
                      video = Uri.parse(downloadUrl);
                    //  Log.d("dfghjklrtyu", "onUrisAvailable: "+video);

                    MediaSource mediaSource = new ExtractorMediaSource(video,factory,extractorsFactory,null,null);

                    playerView.setPlayer(simpleExoPlayer);

                    playerView.setKeepScreenOn(true);
                    simpleExoPlayer.prepare(mediaSource);
                    simpleExoPlayer.setPlayWhenReady(true);
                }
            }

            @Override
            protected void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta videoMeta) {
                super.onExtractionComplete(ytFiles, videoMeta);
                Log.d("hsddjhgsjdfhsj", "onExtractionComplete: "+ytFiles);
            }
        };
        ytEx.execute(mYoutubeLink);

      /* MediaSource mediaSource = new ExtractorMediaSource(video,factory,extractorsFactory,null,null);

        playerView.setPlayer(simpleExoPlayer);

        playerView.setKeepScreenOn(true);
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);*/
        simpleExoPlayer.addListener(new Player.EventListener() {

            @Override
            public void onTimelineChanged(Timeline timeline, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }


            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState==Player.STATE_BUFFERING){
                    progressBar.setVisibility(View.VISIBLE);
                }else if(playbackState==Player.STATE_READY){
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {

            }
            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }
        });


        btn_fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                    btn_fullscreen.setImageDrawable(getResources()
                            .getDrawable(R.drawable.ic_baseline_fullscreen_24));

                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    if(getSupportActionBar() != null){
                        getSupportActionBar().show();
                    }

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = (int) ( 200 * getApplicationContext().getResources().getDisplayMetrics().density);
                    playerView.setLayoutParams(params);

                    flag = false;

                }else {
                    btn_fullscreen.setImageDrawable(getResources()
                            .getDrawable(R.drawable.ic_baseline_fullscreen_exit_24));

                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    if(getSupportActionBar() != null){
                        getSupportActionBar().hide();
                    }

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = params.MATCH_PARENT;
                    playerView.setLayoutParams(params);

                    flag = true;
                }
            }
        });
    }

    private void extractYoutubeUrl() {

        @SuppressLint("StaticFieldLeak")YouTubeUriExtractor ytEx = new YouTubeUriExtractor(this) {
            @Override
            public void onUrisAvailable(String videoId, String videoTitle, SparseArray<YtFile> ytFiles) {
                Log.d("dfghjkl", "onUrisAvailable: "+videoId);
                Log.d("dfghjk", "onUrisAvailable: "+videoTitle);
                Log.d("xcvbnm", "onUrisAvailable: "+ytFiles);
                if (ytFiles != null) {
                    int itag = 18;
                    String downloadUrl = ytFiles.get(itag).getUrl();
                    Log.d("dfghjkxfgh", "onUrisAvailable: "+downloadUrl);
                  //  video = Uri.parse(downloadUrl);
                  //  Log.d("dfghjklrtyu", "onUrisAvailable: "+video);
                }
            }
        };
        ytEx.execute(mYoutubeLink);
    }

    @Override
    protected void onPause() {
        super.onPause();
        simpleExoPlayer.setPlayWhenReady(false);
        simpleExoPlayer.getPlaybackState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.getPlaybackState();
    }

    @Override
    public void onBackPressed() {
        Display d = getWindowManager().getDefaultDisplay();
        Point p = new Point();
        d.getSize(p);
        int width = p.x;
        int height = p.y;

        Rational ratio = new Rational(width, height);
        PictureInPictureParams.Builder pip_Builder = new PictureInPictureParams.Builder();
        pip_Builder.setAspectRatio(ratio).build();
        enterPictureInPictureMode(pip_Builder.build());

    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {

        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (isInPictureInPictureMode) {

        } else {
            simpleExoPlayer.setPlayWhenReady(false);
            simpleExoPlayer.getPlaybackState();
        }
    }
}