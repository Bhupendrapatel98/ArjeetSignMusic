package com.weatherapp.videoapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.weatherapp.videoapplication.ui.fragment.ArjeetPlayLisyFragment;
import com.weatherapp.videoapplication.ui.fragment.MyPlayListFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ArjeetPlayLisyFragment();

            case 1:
                return new MyPlayListFragment();

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Arjeet";
            case 1:
                return "My PlayList";

            default:return null;
        }
    }
}
