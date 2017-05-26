package com.example.quytocngheo.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quytocngheo.myapplication.Fragments.AboutFragment;
import com.example.quytocngheo.myapplication.Fragments.AboutFragment_;
import com.example.quytocngheo.myapplication.Fragments.FoodListFragment;
import com.example.quytocngheo.myapplication.Fragments.FoodListFragment_;
import com.example.quytocngheo.myapplication.Fragments.MapFragment;
import com.example.quytocngheo.myapplication.Fragments.MapFragment_;
import com.example.quytocngheo.myapplication.Fragments.SocialFragment;
import com.example.quytocngheo.myapplication.Fragments.SocialFragment_;
import com.example.quytocngheo.myapplication.SupportClass.ViewPagerAdapter;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_home)
public class HomeActivity extends BaseActivity {

    @ViewById(R.id.activity_home_sliding_tabs)
    TabLayout tabLayout;

    @ViewById(R.id.activity_home_sliding_viewpager)
    ViewPager viewPager;

    ViewPagerAdapter viewPagerAdapter;

    @Override
    void init() {
        final List<Integer> idView = new ArrayList<>();
        idView.add( R.drawable.selector_tab1);
        idView.add( R.drawable.selector_tab2);
        idView.add( R.drawable.selector_tab3);
        idView.add( R.drawable.selector_tab4);

        AboutFragment aboutFragment = new AboutFragment_();
        FoodListFragment foodListFragment = new FoodListFragment_();
        MapFragment mapFragment = new MapFragment_();
        SocialFragment socialFragment = new SocialFragment_();

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(mapFragment);
        fragmentList.add(foodListFragment);
        fragmentList.add(socialFragment);
        fragmentList.add(aboutFragment);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setOnTabSelectedListener( new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                View view = tab.getCustomView();
                TextView tv = (TextView) view.findViewById(R.id.tab_layout_tv_notification);
                tv.setVisibility(View.GONE);
            }
        });
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            View view = LayoutInflater.from(this).inflate(R.layout.tab_layout, null);
            FrameLayout fr= (FrameLayout) view.findViewById(R.id.tab_layout_fr);
            ImageView iv = (ImageView) view.findViewById(R.id.tab_layout_imageView);
            iv.setImageResource(idView.get(i));
            tab.setCustomView(fr);
        }
    }
}
