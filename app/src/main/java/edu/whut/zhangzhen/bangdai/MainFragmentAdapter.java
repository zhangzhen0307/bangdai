package edu.whut.zhangzhen.bangdai;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Acer on 2018/3/15.
 */

public class MainFragmentAdapter extends FragmentPagerAdapter {
    private List<String> titlelist;
    public MainFragmentAdapter(FragmentManager fm, List<String> list)
    {
        super(fm);
        titlelist=list;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return ShowFragment.NewInstance();
            case 1:return AddFragment.NewInstance();
            case 2:return ProcessingFragment.NewInstance();
            default:return MainFragment.NewInstance(titlelist.get(position));
        }
    }

    @Override
    public int getCount() {
        return titlelist.size();
    }
}
