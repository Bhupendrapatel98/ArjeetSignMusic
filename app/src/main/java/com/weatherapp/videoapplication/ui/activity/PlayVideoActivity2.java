package com.weatherapp.videoapplication.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.exoplayer2.ui.PlayerControlView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.PlayerUiController;
import com.weatherapp.videoapplication.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.http.Url;

public class PlayVideoActivity2 extends AppCompatActivity {

    private static final String TAG = "PlayVideoActivity2";
    String video_id;
    YouTubePlayerView youTubePlayerView;
    PlayerUiController playerUiController;
    ImageView next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video2);

        next = findViewById(R.id.next);
         youTubePlayerView = findViewById(R.id.youtube_player_view);
         getLifecycle().addObserver(youTubePlayerView);

        String url = getIntent().getStringExtra("link");
        video_id = extractYTId(url);

        YouTubePlayerTracker tracker = new YouTubePlayerTracker();
        tracker.getState();
        tracker.getCurrentSecond();
        tracker.getVideoDuration();
        tracker.getVideoId();


       youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
           @Override
           public void onYouTubePlayerEnterFullScreen() {
               setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
           }

           @Override
           public void onYouTubePlayerExitFullScreen() {
               setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
           }
       });

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                youTubePlayer.loadVideo(video_id, 0);
            }

            @Override
            public void onPlaybackQualityChange(YouTubePlayer youTubePlayer, PlayerConstants.PlaybackQuality playbackQuality) {
                super.onPlaybackQualityChange(youTubePlayer, playbackQuality);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        youTubePlayerView.release();
    }

}