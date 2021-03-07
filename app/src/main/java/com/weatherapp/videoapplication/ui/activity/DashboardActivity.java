package com.weatherapp.videoapplication.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.weatherapp.videoapplication.BuildConfig;
import com.weatherapp.videoapplication.R;
import com.weatherapp.videoapplication.ui.fragment.BookMarkFragment;
import com.weatherapp.videoapplication.ui.fragment.HistoryFragment;
import com.weatherapp.videoapplication.ui.fragment.HomeFragment;
import com.weatherapp.videoapplication.ui.fragment.PlayListFragment;

public class DashboardActivity extends AppCompatActivity {

    FrameLayout frame;
    BottomNavigationView main_nav;
    NavigationView navigation;
    ImageView nav,search;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    void init(){
        frame = findViewById(R.id.frame);
        main_nav = findViewById(R.id.main_nav);
        navigation = findViewById(R.id.navigation);
        nav = findViewById(R.id.nav);
        drawer = findViewById(R.id.drawer);
        search = findViewById(R.id.search);

        replace(new HomeFragment());

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,SearchActivity.class));
            }
        });

        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.home:
                        replace(new HomeFragment(),"Home");
                        break;

                    case R.id.playlist:
                        replace(new PlayListFragment(),"PlayList");
                        break;


                    case R.id.bookmark:
                        replace(new BookMarkFragment(),"BookMark");
                        break;


                    case R.id.history:
                        replace(new HistoryFragment(),"Menu");
                        break;

                    case R.id.share:
                        share();
                        break;

                    case R.id.rate:
                        rate();
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        main_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.home:
                        replace(new HomeFragment(),"Home");
                       break;

                    case R.id.playlist:
                        replace(new PlayListFragment(),"PlayList");
                        break;


                    case R.id.bookmark:
                        replace(new BookMarkFragment(),"BookMark");
                        break;


                    case R.id.history:
                        replace(new HistoryFragment(),"Menu");
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    void replace(Fragment fragment){
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame,fragment);
        ft.commit();
    }

    void replace(Fragment fragment, String backstack) {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(backstack);
        ft.replace(R.id.frame, fragment);
        ft.commit();
    }

    void share(){
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Arjeet Singh Music");
            String shareMessage= "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        }

    void rate(){
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
                }
        }

}