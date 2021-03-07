package com.weatherapp.videoapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.weatherapp.videoapplication.R;
import com.weatherapp.videoapplication.model.HistoryModel;
import com.weatherapp.videoapplication.model.VideoDataModel;
import com.weatherapp.videoapplication.network.Constant;
import com.weatherapp.videoapplication.room.DatabaseClient;
import com.weatherapp.videoapplication.room.dao.BookmarkModel;
import com.weatherapp.videoapplication.ui.activity.PlayvideoActivity3;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.TopViewHolder>{

    Context context;
    List<VideoDataModel.Datum> list;
    String image;

    public TopAdapter(Context context, List<VideoDataModel.Datum> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.top_item,parent,false);
        return new TopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopViewHolder holder, int position) {
        Log.d("dfghjkl", "onBindViewHolder: "+Constant.Image_URL+list.get(position).getImage());
        //Picasso.get().load(Constant.Image_URL+list.get(position).getImage()).into(holder.cover_image);
        holder.title.setText(list.get(position).getTitle());
        String s = list.get(position).getCreatedAt();
        String[] s2 = s.split("T");
        holder.time.setText(""+s2[0]);

        String vid = extractYTId(list.get(position).getLink());
         image = "https://img.youtube.com/vi/"+vid+"/0.jpg";

        Picasso.get().load(image).into(holder.cover_image);

        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addbookmark(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TopViewHolder extends RecyclerView.ViewHolder {

        ImageView cover_image,bookmark;
        TextView title,time;

        public TopViewHolder(@NonNull View itemView) {
            super(itemView);

            cover_image = itemView.findViewById(R.id.cover_image);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
            bookmark = itemView.findViewById(R.id.bookmark);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addhistory(getAdapterPosition());
                   // context.startActivity(new Intent(context, PlayVideoActivity2.class)
                    context.startActivity(new Intent(context, PlayvideoActivity3.class)
                    .putExtra("link",list.get(getAdapterPosition()).getLink()));
                }
            });

        }
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


    void addbookmark(int pos){

        class SaveTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {

                BookmarkModel myNotes = new BookmarkModel();
                int id = Math.toIntExact(list.get(pos).getId());
                myNotes.setId(id);
                myNotes.setTitle(list.get(pos).getTitle());
                myNotes.setImage(list.get(pos).getImage());
                myNotes.setLink(list.get(pos).getLink());

                //adding to database
                DatabaseClient.getInstance(context).getAppDatabase()
                        .getForBookmarkDao()
                        .insert(myNotes);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(context, "Saved to bookmark", Toast.LENGTH_SHORT).show();
            }
        }
        SaveTask st = new SaveTask();
        st.execute();
    }

    void addhistory(int pos){

        class SaveTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {

                HistoryModel myNotes = new HistoryModel();
                int id = Math.toIntExact(list.get(pos).getId());
                myNotes.setId(id);
                myNotes.setTitle(list.get(pos).getTitle());
                myNotes.setImage(list.get(pos).getImage());
                myNotes.setLink(list.get(pos).getLink());

                //adding to database
                DatabaseClient.getInstance(context).getAppDatabase()
                        .getHistoryDao()
                        .insert(myNotes);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //Toast.makeText(context, "Saved to bookmark", Toast.LENGTH_SHORT).show();
            }
        }
        SaveTask st = new SaveTask();
        st.execute();
    }
}
