package com.weatherapp.videoapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.weatherapp.videoapplication.R;
import com.weatherapp.videoapplication.model.MyModel;
import com.weatherapp.videoapplication.model.VideoDataModel;
import com.weatherapp.videoapplication.network.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectedVideosAdapter extends RecyclerView.Adapter<SelectedVideosAdapter.TopViewHolder>{

    Context context;
    List<VideoDataModel.Datum> list;
    public static List<VideoDataModel.Datum> checkedlist = new ArrayList<>();


    public SelectedVideosAdapter(Context context, List<VideoDataModel.Datum> list) {
        this.context = context;
        this.list = list;
    }
    

    @NonNull
    @Override
    public TopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.select_item,parent,false);
        return new TopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopViewHolder holder, int position) {

       // Picasso.get().load(Constant.Image_URL+list.get(position).getImage()).into(holder.cover_image);
        holder.title.setText(list.get(position).getTitle());
        String s = list.get(position).getCreatedAt();
        String[] s2 = s.split("T");
        holder.time.setText(""+s2[0]);

        String vid = extractYTId(list.get(position).getLink());
        String u = "https://img.youtube.com/vi/"+vid+"/0.jpg";
        Log.d("dfghjk466464", "onBindViewHolder: "+u);

        Picasso.get().load(u).into(holder.cover_image);

        holder.setItemclick(new ItemClicklistener() {
            @Override
            public void onItemClick(View v, int pos) {
                CheckBox chk = (CheckBox) v;

                if (chk.isChecked()){
                    checkedlist.add(list.get(pos));
                }else  if (!chk.isChecked()){
                    checkedlist.remove(list.get(pos));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
          return list.size();
    }

    public static class TopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView cover_image;
        TextView title, time;
        CheckBox checkbox;
        ItemClicklistener itemClicklistener;

        public TopViewHolder(@NonNull View itemView) {
            super(itemView);

            cover_image = itemView.findViewById(R.id.cover_image);
            title = itemView.findViewById(R.id.title);
            time = itemView.findViewById(R.id.time);
            checkbox = itemView.findViewById(R.id.checkbox);

            checkbox.setOnClickListener(this);
        }
        public void setItemclick(ItemClicklistener ic){
            this.itemClicklistener=ic;
        }


        @Override
        public void onClick(View v) {
            this.itemClicklistener.onItemClick(v,getLayoutPosition());
        }
    }
    public interface ItemClicklistener{
        void onItemClick(View v,int pos);
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
