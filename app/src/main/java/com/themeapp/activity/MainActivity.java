package com.themeapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;


import com.google.android.material.tabs.TabLayout;
import com.themeapp.R;
import com.themeapp.adapter.TabLayoutPagerAdapter;
import com.themeapp.fragment.HomeFragment;
import com.themeapp.fragment.OtherFragment;
import com.themeapp.fragment.RecordFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tablayout;
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tablayout = this.findViewById(R.id.tablayout);
        viewpager = this.findViewById(R.id.viewpager);
        initView();
    }

    private void initView() {
        List<String> stringlist = new ArrayList<>();
        stringlist.add("HOME");
        stringlist.add("HISTORY");
        stringlist.add("RESEARCH");
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new RecordFragment());
        fragmentList.add(new OtherFragment());
        TabLayoutPagerAdapter tabLayoutPagerAdapter = new TabLayoutPagerAdapter(getSupportFragmentManager(), fragmentList, stringlist);
        viewpager.setAdapter(tabLayoutPagerAdapter);
        tablayout.setupWithViewPager(viewpager);
    }
}
