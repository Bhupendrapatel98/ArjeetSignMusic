package com.weatherapp.videoapplication.adapter;

import android.util.Log;

import com.weatherapp.videoapplication.model.VideoDataModel;
import com.weatherapp.videoapplication.network.Constant;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {

    List<VideoDataModel.Datum> list;

    public MainSliderAdapter(List<VideoDataModel.Datum> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {

        String vid = extractYTId(list.get(position).getLink());
        String u = "https://img.youtube.com/vi/"+vid+"/0.jpg";

                viewHolder.bindImageSlide(u);

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
