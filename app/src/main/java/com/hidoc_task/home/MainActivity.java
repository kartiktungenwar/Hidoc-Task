package com.hidoc_task.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.hidoc_task.R;
import com.hidoc_task.data.BaseActivity;

public class MainActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager pager;
    private ViewPagerAdapter adapter;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("News"));
        tabLayout.addTab(tabLayout.newTab().setText("Books"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(2);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(),MainActivity.this);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }
}