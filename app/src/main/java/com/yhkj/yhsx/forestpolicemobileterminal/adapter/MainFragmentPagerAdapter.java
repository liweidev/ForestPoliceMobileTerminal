package com.yhkj.yhsx.forestpolicemobileterminal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by liupeng on 2017/6/8.
 * 主页面ViewPager适配器
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public MainFragmentPagerAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        fragments = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
