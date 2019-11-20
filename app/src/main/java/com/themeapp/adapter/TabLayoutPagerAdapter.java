package com.themeapp.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class TabLayoutPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private List<String> title;

    public TabLayoutPagerAdapter(FragmentManager fm, List<Fragment> fragments,List<String> title) {
        super(fm);
        mFragments = fragments;
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);

    }
}
