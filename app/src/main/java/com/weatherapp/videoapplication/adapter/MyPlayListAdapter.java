package com.weatherapp.videoapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.weatherapp.videoapplication.R;
import com.weatherapp.videoapplication.model.HistoryModel;
import com.weatherapp.videoapplication.model.MylistModel;
import com.weatherapp.videoapplication.network.Constant;
import com.weatherapp.videoapplication.room.DatabaseClient;
import com.weatherapp.videoapplication.ui.activity.MyplaylistdataActivity;
import com.weatherapp.videoapplication.ui.activity.PlayvideoActivity3;
import com.weatherapp.videoapplication.ui.activity.SelectionActivity;

import java.util.List;

public class MyPlayListAdapter extends RecyclerView.Adapter<MyPlayListAdapter.TopViewHolder>{

    Context context;
    List<MylistModel> list;

    public MyPlayListAdapter(Context context, List<MylistModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.myplaylist_item,parent,false);
        return new TopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopViewHolder holder, int position) {

        holder.title.setText(list.get(position).getTitle());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletelist(list.get(position).getId1(),position);
            }
        });

        holder.add_playlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SelectionActivity.class)
                .putExtra("playlist",list.get(position).getTitle()));
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MyplaylistdataActivity.class)
                        .putExtra("playlist",list.get(position).getTitle()));
            }
        });

    }

    @Override
    public int getItemCount() {
          return list.size();
    }

    public class TopViewHolder extends RecyclerView.ViewHolder {

        ImageView poster_image,add_playlist,edit,delete;
        TextView count,title;

        public TopViewHolder(@NonNull View itemView) {
            super(itemView);

            poster_image = itemView.findViewById(R.id.poster_image);
            count = itemView.findViewById(R.id.count);
            title = itemView.findViewById(R.id.title);
            add_playlist = itemView.findViewById(R.id.add_playlist);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);


        }
    }

    void deletelist(int id,int pos){

        class SaveTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {

                //adding to database
                DatabaseClient.getInstance(context).getAppDatabase()
                        .getmylistDao()
                        .deleteById(id);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(context, "delete MyPlaylist", Toast.LENGTH_SHORT).show();
                list.remove(pos);
                notifyDataSetChanged();
            }
        }
        SaveTask st = new SaveTask();
        st.execute();
    }


}
