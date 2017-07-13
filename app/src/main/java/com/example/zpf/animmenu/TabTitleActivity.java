package com.example.zpf.animmenu;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import adapter.MainPagerAdapter;
import fragment.LiveFragment1;
import fragment.LiveFragment2;
import fragment.LiveFragment3;

@ContentView(R.layout.activity_tab_title)
public class TabTitleActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener {

    @ViewInject(R.id.rg_tab)
    private RadioGroup radioGroup;

    private int[] rbId;

    @ViewInject(R.id.bvp_tab)
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initRadioGroup();

        initViewPager();
    }

    private void initRadioGroup() {

        radioGroup.setOnCheckedChangeListener(this);
        int count = radioGroup.getChildCount();
        rbId = new int[count];

        for (int i = 0; i < count; i++) {

            rbId[i] = radioGroup.getChildAt(i).getId();
        }
    }

    private void initViewPager() {

        LiveFragment1 fragment1 = new LiveFragment1();
        LiveFragment2 fragment2 = new LiveFragment2();
        LiveFragment3 fragment3 = new LiveFragment3();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);

        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

        for (int n = 0; n < rbId.length; n++) {

            if (i == rbId[n])
                viewPager.setCurrentItem(n);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        Log.i("====", "position: " + position + "\npositionOffset: " + positionOffset);
    }

    @Override
    public void onPageSelected(int position) {

        ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setScrollAlpha() {


    }

}
