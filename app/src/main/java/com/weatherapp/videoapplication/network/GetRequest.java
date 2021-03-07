package com.weatherapp.videoapplication.network;

import com.weatherapp.videoapplication.model.SearchModel;
import com.weatherapp.videoapplication.model.VideoDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetRequest {

    @GET("get_data")
    Call<VideoDataModel> getTopData();

    @GET("get_recent")
    Call<VideoDataModel> getRecentData();

    @GET("search_data")
    Call<List<SearchModel>> getSearchData(@Query("title") String title);

    @GET("get_data")
    Call<VideoDataModel> getTopDatap(@Query("page") String page);

}
