package com.jolasudol.sampleprojectnew.fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jolasudol.sampleprojectnew.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StopwatchFragment extends Fragment {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.button)
    Button start;
    @BindView(R.id.button2)
    Button pause;
    @BindView(R.id.button3)
    Button reset;
    @BindView(R.id.button4)
    Button lap;
    @BindView(R.id.listview1)
    ListView listView;

    private Context context;
    private long millisecondTime;
    private long startTime;
    private long timeBuff;
    private long updateTime = 0L;
    private Handler handler;
    private int seconds;
    private int minutes;

    private int milliSeconds;

    private String[] listElements = new String[]{};
    private List<String> listElementsArrayList;
    private ArrayAdapter<String> adapter;

    public StopwatchFragment() {
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

        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        ButterKnife.bind(this, view);

        handler = new Handler();

        listElementsArrayList = new ArrayList<String>(Arrays.asList(listElements));

        adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1,
                listElementsArrayList
        );

        listView.setAdapter(adapter);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);

                reset.setEnabled(false);

            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                timeBuff += millisecondTime;

                handler.removeCallbacks(runnable);

                reset.setEnabled(true);

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                millisecondTime = 0L;
                startTime = 0L;
                timeBuff = 0L;
                updateTime = 0L;
                seconds = 0;
                minutes = 0;
                milliSeconds = 0;

                textView.setText("00:00:00");

                listElementsArrayList.clear();

                adapter.notifyDataSetChanged();
            }
        });

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listElementsArrayList.add(textView.getText().toString());

                adapter.notifyDataSetChanged();

            }
        });


        return view;
    }

    public Runnable runnable = new Runnable() {

        public void run() {

            millisecondTime = SystemClock.uptimeMillis() - startTime;

            updateTime = timeBuff + millisecondTime;

            seconds = (int) (updateTime / 1000);

            minutes = seconds / 60;

            seconds = seconds % 60;

            milliSeconds = (int) (updateTime % 1000);

            textView.setText("" + minutes + ":"
                    + String.format("%02d", seconds) + ":"
                    + String.format("%03d", milliSeconds));

            handler.postDelayed(this, 0);
        }

    };


}




























