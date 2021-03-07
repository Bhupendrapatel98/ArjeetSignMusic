package com.weatherapp.videoapplication.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.weatherapp.videoapplication.R;
import com.weatherapp.videoapplication.adapter.HistoryAdapter;
import com.weatherapp.videoapplication.adapter.MyPlayListAdapter;
import com.weatherapp.videoapplication.model.HistoryModel;
import com.weatherapp.videoapplication.model.MylistModel;
import com.weatherapp.videoapplication.room.DatabaseClient;
import com.weatherapp.videoapplication.ui.activity.CreatePlaylistActivity;

import java.util.List;

public class MyPlayListFragment extends Fragment {

    FloatingActionButton create_playlist;
    RecyclerView myplaylist_recycler;
    MyPlayListAdapter myPlayListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_play_list, container, false);

        create_playlist = view.findViewById(R.id.create_playlist);
        myplaylist_recycler = view.findViewById(R.id.myplaylist_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        myplaylist_recycler.setLayoutManager(linearLayoutManager);

        showlist();

        create_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CreatePlaylistActivity.class));
            }
        });

        return view;
    }

    public  void  showlist(){

        class GetTasks extends AsyncTask<Void, Void, List<MylistModel>> {

            @Override
            protected List<MylistModel> doInBackground(Void... voids) {
                List<MylistModel> taskList = DatabaseClient
                        .getInstance(getContext())
                        .getAppDatabase()
                        .getmylistDao()
                        .getAll();
                return taskList;
            }

            @Override
            protected void onPostExecute(List<MylistModel> tasks) {
                super.onPostExecute(tasks);
                myPlayListAdapter = new MyPlayListAdapter(getContext(), tasks);
                myplaylist_recycler.setAdapter(myPlayListAdapter);
                myPlayListAdapter.notifyDataSetChanged();
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        showlist();
    }
}