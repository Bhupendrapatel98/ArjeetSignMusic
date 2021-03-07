package com.weatherapp.videoapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.weatherapp.videoapplication.R;
import com.weatherapp.videoapplication.adapter.AllVideosAdapter;
import com.weatherapp.videoapplication.adapter.SearchVideosAdapter;
import com.weatherapp.videoapplication.model.SearchModel;
import com.weatherapp.videoapplication.model.VideoDataModel;
import com.weatherapp.videoapplication.network.Constant;
import com.weatherapp.videoapplication.network.GetRequest;
import com.weatherapp.videoapplication.network.RetrofitClint;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    EditText searct_et;
    RecyclerView boll_recycler;
    SearchVideosAdapter searchVideosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        boll_recycler = findViewById(R.id.boll_recycler);
        searct_et = findViewById(R.id.searct_et);

        searct_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: "+s);
                getsearchVideos(String.valueOf(s));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    void getsearchVideos(String s){

        RetrofitClint.getRetrofit(Constant.BASE_URL)
                .create(GetRequest.class)
                .getSearchData(s)
                .enqueue(new Callback<List<SearchModel>>() {
                    @Override
                    public void onResponse(Call<List<SearchModel>> call, Response<List<SearchModel>> response) {
                        Log.d(TAG, "onResponse: "+response);
                        Log.d(TAG, "onResponse: "+response.body());

                        searchVideosAdapter = new SearchVideosAdapter(SearchActivity.this,response.body());
                        LinearLayoutManager all_linearLayoutManager3 = new LinearLayoutManager(SearchActivity.this, RecyclerView.VERTICAL,false);
                        boll_recycler.setLayoutManager(all_linearLayoutManager3);
                        boll_recycler.setAdapter(searchVideosAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<SearchModel>> call, Throwable t) {

                    }
                });
    }
}