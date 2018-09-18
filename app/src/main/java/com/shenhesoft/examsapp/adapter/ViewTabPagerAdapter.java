package com.shenhesoft.examsapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author 张继淮
 * @date 2017/9/29
 * @description TODO 优化tablayout标题栏问题 setupWithViewPager 文字消失  removealltab
 */

public class ViewTabPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> titleList;

    public ViewTabPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titleList) {
        super(fm);
        this.fragments = fragments;
        this.titleList = titleList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
