package com.example.slr2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.slr2.fragments.stationdetails;
import com.example.slr2.fragments.timetable;
import com.example.slr2.fragments.Traindetails;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Traindetails();
            case 1:
                return new stationdetails();
            case 2:
                return new timetable();
            default:
                return new Traindetails();

        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
