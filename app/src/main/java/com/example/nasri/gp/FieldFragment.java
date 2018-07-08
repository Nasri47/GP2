package com.example.nasri.gp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

public class FieldFragment extends Fragment{
    private ViewPager mSlideViewPager;
    private SliderAdapter sliderAdapter;

    private List<PeriodTimes> periodList = new ArrayList<>();
    private List<String> daysList = new ArrayList<>();
    private List<String> historyList = new ArrayList<>();
    private RecyclerView periodRecycler;
    private TimetableAdabter timetableAdabter;
    private TextView dayText ;
    private TextView historyText ;
    private LinearLayout innerDayHistoryLayout ;
    private ViewFlipper daysFlipper ;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_field, container, false);
        periodRecycler = (RecyclerView) rootView.findViewById(R.id.timetable_recycle_view);
        timetableAdabter = new TimetableAdabter(periodList);
        periodRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager periodLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        periodRecycler.setLayoutManager(periodLayout);
        periodRecycler.setItemAnimator(new DefaultItemAnimator());
        periodRecycler.setAdapter(timetableAdabter);
        mSlideViewPager = (ViewPager) rootView.findViewById(R.id.slideviewpager);
        sliderAdapter = new SliderAdapter(getContext());
        mSlideViewPager.setAdapter(sliderAdapter);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mSlideViewPager, true);

        ImageButton mUpdateDialog = rootView.findViewById(R.id.btnedit);
        mUpdateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                final View mView = getLayoutInflater().inflate(R.layout.activity_main, null);
                Button mUpdate = mView.findViewById(R.id.add_update);
                mUpdate.setText("Update");
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                mUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "add Successfully", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        //Date and history
        daysFlipper = (ViewFlipper)rootView.findViewById(R.id.days_flipper);
        daysList.add("SUN") ;
        daysList.add("MON") ;
        daysList.add("TUE") ;
        daysList.add("WED") ;
        daysList.add("THU") ;
        daysList.add("FRI") ;
        daysList.add("SAT") ;
        historyList.add("4/6") ;
        historyList.add("5/6") ;
        historyList.add("6/6") ;
        historyList.add("7/6") ;
        historyList.add("8/6") ;
        historyList.add("9/6") ;
        historyList.add("10/6") ;

        for(int i=0;i<daysList.size();i++)
        {
            innerDayHistoryLayout = new LinearLayout(getContext()) ;
            innerDayHistoryLayout.setOrientation(LinearLayout.VERTICAL);
            daysFlipper.addView(innerDayHistoryLayout);
            dayText = new TextView(getContext());
            dayText.setText(daysList.get(i));
            dayText.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
            historyText = new TextView(getContext());
            historyText.setText(historyList.get(i));
            historyText.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
            innerDayHistoryLayout.addView(dayText);
            innerDayHistoryLayout.addView(historyText);
        }

        //handling flipper

        ImageView leftArrowButton = (ImageView)rootView.findViewById(R.id.left_button);
        leftArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daysFlipper.showPrevious();
            }
        });
        ImageView rightArrowButton = (ImageView)rootView.findViewById(R.id.right_button);
        rightArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daysFlipper.showNext();
            }
        });
        getPeriod();
        getDate() ;
        return rootView;
    }


    private void getPeriod(){

        periodList.add(new PeriodTimes("04:00 04:30", "lutti", 1)) ;
        periodList.add(new PeriodTimes("04:30 05:00", "lutti",2));
        periodList.add(new PeriodTimes("05:00 05:30", "lutti", 3));
        periodList.add(new PeriodTimes("05:30 06:00", "lutti", 4));
        periodList.add(new PeriodTimes("06:00 06:30", "lutti", 1)) ;
        periodList.add(new PeriodTimes("06:30 07:00", "lutti",2));
        periodList.add(new PeriodTimes("07:00 07:30", "lutti", 3));
        periodList.add(new PeriodTimes("07:30 08:00", "lutti", 4));
        periodList.add(new PeriodTimes("08:00 08:30", "lutti", 1)) ;
        periodList.add(new PeriodTimes("08:30 09:00", "lutti",2));
        periodList.add(new PeriodTimes("09:00 09:30", "lutti", 3));
        periodList.add(new PeriodTimes("09:30 10:00", "lutti", 4));
        periodList.add(new PeriodTimes("10:00 10:30", "lutti", 1)) ;
        periodList.add(new PeriodTimes("10:30 11:00", "lutti",2));
        periodList.add(new PeriodTimes("11:00 11:30", "lutti", 3));
        periodList.add(new PeriodTimes("11:30 12:00", "lutti", 4));

        timetableAdabter.notifyDataSetChanged();
    }
    private void getDate(){


    }
    public void onLeftClicked(View view){

        LinearLayoutManager dateLayout = (LinearLayoutManager) periodRecycler.getLayoutManager();
        int firstVisibleItemIndex = dateLayout.findFirstCompletelyVisibleItemPosition();
        if (firstVisibleItemIndex > 0) {
            dateLayout.smoothScrollToPosition(periodRecycler,null,firstVisibleItemIndex-1);
        }

    }
    public void onRightClicked(View view){

        LinearLayoutManager dateLayout = (LinearLayoutManager) periodRecycler.getLayoutManager();
        int totalItemCount = periodRecycler.getAdapter().getItemCount();
        if (totalItemCount <= 0) return;
        int lastVisibleItemIndex = dateLayout.findLastVisibleItemPosition();
        periodRecycler.smoothScrollToPosition(dateLayout.findLastVisibleItemPosition()+1);
        if (lastVisibleItemIndex >= totalItemCount) return;
        dateLayout.smoothScrollToPosition(periodRecycler,null,lastVisibleItemIndex+1);


    }
    }

