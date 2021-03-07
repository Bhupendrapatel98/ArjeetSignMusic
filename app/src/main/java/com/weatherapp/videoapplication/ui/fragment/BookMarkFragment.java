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
import com.weatherapp.videoapplication.room.DatabaseClient;
import com.weatherapp.videoapplication.room.dao.BookmarkModel;

import java.util.List;

public class BookMarkFragment extends Fragment {

    RecyclerView bookmark_recyclerview;
    BookmarkAdapter bookmarkAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_book_mark, container, false);

        bookmark_recyclerview = view.findViewById(R.id.bookmark_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        bookmark_recyclerview.setLayoutManager(linearLayoutManager);

        showBookmark();
        return view;
    }

    public  void  showBookmark(){

        class GetTasks extends AsyncTask<Void, Void, List<BookmarkModel>> {

            @Override
            protected List<BookmarkModel> doInBackground(Void... voids) {
                List<BookmarkModel> taskList = DatabaseClient
                        .getInstance(getContext())
                        .getAppDatabase()
                        .getForBookmarkDao()
                        .getAll();
                return taskList;
            }

            @Override
            protected void onPostExecute(List<BookmarkModel> tasks) {
                super.onPostExecute(tasks);
                bookmarkAdapter = new BookmarkAdapter(getContext(), tasks);
                bookmark_recyclerview.setAdapter(bookmarkAdapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }
}