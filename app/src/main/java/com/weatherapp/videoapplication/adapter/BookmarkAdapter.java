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
import com.weatherapp.videoapplication.model.VideoDataModel;
import com.weatherapp.videoapplication.network.Constant;
import com.weatherapp.videoapplication.network.onClickInterface;
import com.weatherapp.videoapplication.room.DatabaseClient;
import com.weatherapp.videoapplication.room.dao.BookmarkModel;
import com.weatherapp.videoapplication.ui.activity.PlayvideoActivity3;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.TopViewHolder>{


    Context context;
    List<BookmarkModel> list;

    public BookmarkAdapter(Context context, List<BookmarkModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.book_item,parent,false);
        return new TopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopViewHolder holder, int position) {

      //  Picasso.get().load(Constant.Image_URL+list.get(position).getImage()).into(holder.cover_image);
        holder.title.setText(list.get(position).getTitle());
       /* String s = list.get(position).getCreatedAt();
        String[] s2 = s.split("T");
        holder.time.setText(""+s2[0]);*/

        String vid = extractYTId(list.get(position).getLink());
        String u = "https://img.youtube.com/vi/"+vid+"/0.jpg";
        Log.d("dfghjk466464", "onBindViewHolder: "+u);

        Picasso.get().load(u).into(holder.cover_image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PlayvideoActivity3.class)
                        .putExtra("link",list.get(position).getLink()));
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletebookmark(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
          return list.size();
    }

    public class TopViewHolder extends RecyclerView.ViewHolder {

        ImageView cover_image,delete;
        TextView title,time;

        public TopViewHolder(@NonNull View itemView) {
            super(itemView);

            cover_image = itemView.findViewById(R.id.cover_image);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
            delete = itemView.findViewById(R.id.delete);
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

    void deletebookmark(int pos){

        class SaveTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {

                //adding to database
                DatabaseClient.getInstance(context).getAppDatabase()
                        .getForBookmarkDao()
                        .deleteById(list.get(pos).getId());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(context, "delete to bookmark", Toast.LENGTH_SHORT).show();
                list.remove(pos);
                notifyDataSetChanged();
            }
        }
        SaveTask st = new SaveTask();
        st.execute();
    }
}
