package com.example.groupproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class StatePagerAdapter extends FragmentStatePagerAdapter {

    public final List<Fragment> mFragmentList= new ArrayList<>();
    public final List<String> mTitleList= new ArrayList<>();

    public StatePagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    public void addFragments(Fragment fragment, String title){

        mFragmentList.add(fragment);
        mTitleList.add(title);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
