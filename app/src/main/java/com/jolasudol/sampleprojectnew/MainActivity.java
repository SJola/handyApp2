package com.jolasudol.sampleprojectnew;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.jolasudol.sampleprojectnew.adapters.MyPageAdapter;
import com.jolasudol.sampleprojectnew.fragments.BatteryFragment;
import com.jolasudol.sampleprojectnew.fragments.FlashlightFragment;
import com.jolasudol.sampleprojectnew.fragments.StopwatchFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int TAB_BATTERY_POSITION = 0;
    private static final int TAB_FLASHLIGHT_POSITION = 1;
    private static final int TAB_STOPWATCH_POSITION = 2;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private BatteryFragment batteryFragment;
    private FlashlightFragment flashlightFragment;
    private StopwatchFragment stopwatchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        addFragments();
        pageChangeListener();
    }

    private void pageChangeListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override

            public void onPageScrollStateChanged(int state) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .detach(batteryFragment)
                        .attach(batteryFragment)
                        .commit();
            }
        });
    }

    private void addFragments() {
        List<Fragment> fragments = new ArrayList<>();
        batteryFragment = new BatteryFragment();
        flashlightFragment = new FlashlightFragment();
        stopwatchFragment = new StopwatchFragment();
        fragments.add(batteryFragment);
        fragments.add(flashlightFragment);
        fragments.add(stopwatchFragment);
        MyPageAdapter adapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(TAB_BATTERY_POSITION)
                .setIcon(getDrawable(R.drawable.ic_battery_charging_full_black_24dp));

        tabLayout.getTabAt(TAB_FLASHLIGHT_POSITION)
                .setIcon(getDrawable(R.drawable.ic_lightbulb_outline_black_24dp));

        tabLayout.getTabAt(TAB_STOPWATCH_POSITION)
                .setIcon(getDrawable(R.drawable.ic_add_alarm_black_24dp));

    }

}