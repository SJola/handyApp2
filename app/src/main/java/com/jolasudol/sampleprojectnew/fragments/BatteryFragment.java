package com.jolasudol.sampleprojectnew.fragments;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jolasudol.sampleprojectnew.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BatteryFragment extends Fragment {

    private Context context;
    //    private TextView textView;
    private boolean isCharging;
    private boolean usbCharge;
    private boolean acCharge;
    private int level;
    //    private ImageView imageView;
    @BindView(R.id.batteryLevelTV)
    TextView batteryTV;
    @BindView(R.id.infoAboutBattery)
    TextView batteryInfo;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.infoAboutCharging)
    TextView batteryCharging;

    public BatteryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_battery, container, false);
        ButterKnife.bind(this, view);

        batteryLevel();
        setBatteryLevelText();
        configureBatteryInformation();
        configureProgressBarInformation();
        setInformationAboutCharging();
        return view;
    }

    private void setBatteryLevelText() {
        batteryTV.setText("Your battery level is " + String.valueOf(level) + " " + "%");
    }

    private void configureProgressBarInformation() {
        progressBar.setProgress(level);
    }

    private void configureBatteryInformation() {
        if (level >= 0 && level <= 25) {
            batteryInfo.setText("Your battery is on low level. Please input charger..");
        } else if (level >= 26 && level <= 50) {
            batteryInfo.setText("Your battery is on stable level. Not need recharge..");
        } else if (level >= 51 && level <= 75) {
            batteryInfo.setText("Your battery is on good level. Enjoy to use telephone..");
        } else if (level >= 76 && level <= 100) {
            batteryInfo.setText("Are you recharge battery today? The level of battery is nice..");
        }
    }

    private void setInformationAboutCharging() {
        if (isCharging) {
            batteryCharging.setText("You are charging battery");
            if (usbCharge) {
                batteryCharging.setText("You are charging battery with usb..");
            } else if (acCharge) {
                batteryCharging.setText("You are charging battery with ac..");
            }
        } else {
            batteryCharging.setText("You are not charging battery..");
        }

    }

    private void batteryLevel() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
    }
}


//
//
//        batteryLevel();
//        setBatteryLevelText();
//        configureBatteryInformation();
//        configureProgressBarInformation();
//        setInformationAboutCharging();
//
////        textView = (TextView) view.findViewById(R.id.battery_text);
////        textView.setText(String.valueOf(getBatteryPercentage(context)) + " " + "%");
//        return view;
//    }
//
//    public int getBatteryPercentage(Context context) {
//        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//        Intent batteryStatus = context.registerReceiver(null, ifilter);
//
//        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
//
//        float batteryPct = level / (float) scale;
//        return (int) (batteryPct * 100);
//
//    }
//
//    private void range() {
//        if (batteryLevel < 25) {
//            imageView.setImageResource(R.id.);
//
//        }
//    }
//}
