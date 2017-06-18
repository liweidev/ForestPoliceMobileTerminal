package com.yhkj.yhsx.forestpolicemobileterminal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by qianhaohong on 2017/6/15.
 */

public class PublicSecurityFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public PublicSecurityFragmentPagerAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        fragments=fragmentList;
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
