package com.weatherapp.videoapplication.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weatherapp.videoapplication.R;
import com.weatherapp.videoapplication.adapter.BookmarkAdapter;
import com.weatherapp.videoapplication.adapter.HistoryAdapter;
import com.weatherapp.videoapplication.model.HistoryModel;
import com.weatherapp.videoapplication.room.DatabaseClient;
import com.weatherapp.videoapplication.room.dao.BookmarkModel;

import java.util.List;

public class HistoryFragment extends Fragment {

    RecyclerView history_recyclerview;
    HistoryAdapter historyAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_history, container, false);

        history_recyclerview = view.findViewById(R.id.history_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        history_recyclerview.setLayoutManager(linearLayoutManager);

        showHistory();

        return view;
    }

    public  void  showHistory(){

        class GetTasks extends AsyncTask<Void, Void, List<HistoryModel>> {

            @Override
            protected List<HistoryModel> doInBackground(Void... voids) {
                List<HistoryModel> taskList = DatabaseClient
                        .getInstance(getContext())
                        .getAppDatabase()
                        .getHistoryDao()
                        .getAll();
                return taskList;
            }

            @Override
            protected void onPostExecute(List<HistoryModel> tasks) {
                super.onPostExecute(tasks);
                historyAdapter = new HistoryAdapter(getContext(), tasks);
                history_recyclerview.setAdapter(historyAdapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

}