package com.weatherapp.videoapplication.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.weatherapp.videoapplication.R;
import com.weatherapp.videoapplication.model.HistoryModel;
import com.weatherapp.videoapplication.model.MylistModel;
import com.weatherapp.videoapplication.room.DatabaseClient;

public class CreatePlaylistActivity extends AppCompatActivity {

    private static final String TAG = "CreatePlaylistActivity";
    EditText title_et;
    TextView title_tv;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_playlist);

        title_et = findViewById(R.id.title_et);
        title_tv = findViewById(R.id.title_tv);
        save = findViewById(R.id.save);

        title_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                title_tv.setText(s);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addmyList();
            }
        });
    }

    void addmyList(){

        class SaveTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {

                MylistModel myNotes = new MylistModel();
                myNotes.setTitle(title_et.getText().toString());

                //adding to database
                DatabaseClient.getInstance(CreatePlaylistActivity.this).getAppDatabase()
                        .getmylistDao()
                        .insert(myNotes);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //Toast.makeText(context, "Saved to bookmark", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        }
        SaveTask st = new SaveTask();
        st.execute();
    }

}