package com.example.quytocngheo.myapplication.SupportClass;

/**
 * Created by quytocngheo on 5/21/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tient_000 on 11/9/2016.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> listData = new ArrayList<>();
    FragmentManager fragmentManager;

    private String tabTitles[] = new String[] { "Info", "Sounds", "Following","Followers" };


    public ViewPagerAdapter(FragmentManager fm,List<Fragment> listData) {
        super(fm);
        fragmentManager=fm;
        this.listData=listData;

    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return listData.get(position);
    }
    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


}

