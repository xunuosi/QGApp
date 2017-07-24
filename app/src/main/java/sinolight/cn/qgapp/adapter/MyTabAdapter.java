package sinolight.cn.qgapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xns on 2017/7/24.
 * 选项卡中使用的Adapter
 */

public class MyTabAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTitles;

    public MyTabAdapter(FragmentManager fm, List<Fragment> fragments,List<String> titles) {
        super(fm);
        mFragments = new ArrayList<>();
        mFragments.addAll(fragments);
        mTitles = new ArrayList<>();
        mTitles.addAll(titles);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
