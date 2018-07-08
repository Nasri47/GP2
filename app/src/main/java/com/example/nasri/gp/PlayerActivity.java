package com.example.nasri.gp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity {
    private ViewPager mSlideViewPager;
    private SliderAdapter sliderAdapter;

    private List<PeriodTimes> periodList = new ArrayList<>();
    private List<String> daysList = new ArrayList<>();
    private List<String> historyList = new ArrayList<>();
    private RecyclerView periodRecycler;
    private TimetableAdabter timetableAdabter;
    private TextView dayText;
    private TextView historyText;
    private LinearLayout innerDayHistoryLayout;
    private ViewFlipper daysFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        periodRecycler = (RecyclerView) findViewById(R.id.timetable_recycle_view);
        timetableAdabter = new TimetableAdabter(periodList);
        periodRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager periodLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        periodRecycler.setLayoutManager(periodLayout);
        periodRecycler.setItemAnimator(new DefaultItemAnimator());
        periodRecycler.setAdapter(timetableAdabter);
        mSlideViewPager = (ViewPager) findViewById(R.id.slideviewpager);
        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mSlideViewPager, true);
        TextView fieldNamePlayer = (TextView) findViewById(R.id.field_name_player);
        TextView fieldLocationPlayer = (TextView) findViewById(R.id.field_location_player);
        Bundle bundle = getIntent().getExtras();
        String fieldName = bundle.getString("fieldName");
        String fieldLocation = bundle.getString("fieldlocation");
        fieldNamePlayer.setText(fieldName);
        fieldLocationPlayer.setText(fieldLocation);

        //Date and history
        daysFlipper = (ViewFlipper) findViewById(R.id.days_flipper);
        daysList.add("SUN");
        daysList.add("MON");
        daysList.add("TUE");
        daysList.add("WED");
        daysList.add("THU");
        daysList.add("FRI");
        daysList.add("SAT");
        historyList.add("4/6");
        historyList.add("5/6");
        historyList.add("6/6");
        historyList.add("7/6");
        historyList.add("8/6");
        historyList.add("9/6");
        historyList.add("10/6");

        for (int i = 0; i < daysList.size(); i++) {
            innerDayHistoryLayout = new LinearLayout(this);
            innerDayHistoryLayout.setOrientation(LinearLayout.VERTICAL);
            daysFlipper.addView(innerDayHistoryLayout);
            dayText = new TextView(this);
            dayText.setText(daysList.get(i));
            dayText.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
            historyText = new TextView(this);
            historyText.setText(historyList.get(i));
            historyText.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
            innerDayHistoryLayout.addView(dayText);
            innerDayHistoryLayout.addView(historyText);
        }

        //handling flipper

        ImageView leftArrowButton = (ImageView) findViewById(R.id.left_button);
        leftArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daysFlipper.showPrevious();
            }
        });
        ImageView rightArrowButton = (ImageView) findViewById(R.id.right_button);
        rightArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daysFlipper.showNext();
            }
        });
        getPeriod();
        getDate();
    }

    private void getPeriod() {

        periodList.add(new PeriodTimes("04:00 04:30", "lutti", 1));
        periodList.add(new PeriodTimes("04:30 05:00", "lutti", 2));
        periodList.add(new PeriodTimes("05:00 05:30", "lutti", 3));
        periodList.add(new PeriodTimes("05:30 06:00", "lutti", 4));
        periodList.add(new PeriodTimes("06:00 06:30", "lutti", 1));
        periodList.add(new PeriodTimes("06:30 07:00", "lutti", 2));
        periodList.add(new PeriodTimes("07:00 07:30", "lutti", 3));
        periodList.add(new PeriodTimes("07:30 08:00", "lutti", 4));
        periodList.add(new PeriodTimes("08:00 08:30", "lutti", 1));
        periodList.add(new PeriodTimes("08:30 09:00", "lutti", 2));
        periodList.add(new PeriodTimes("09:00 09:30", "lutti", 3));
        periodList.add(new PeriodTimes("09:30 10:00", "lutti", 4));
        periodList.add(new PeriodTimes("10:00 10:30", "lutti", 1));
        periodList.add(new PeriodTimes("10:30 11:00", "lutti", 2));
        periodList.add(new PeriodTimes("11:00 11:30", "lutti", 3));
        periodList.add(new PeriodTimes("11:30 12:00", "lutti", 4));

        timetableAdabter.notifyDataSetChanged();
    }

    private void getDate() {


    }

    public void onLeftClicked(View view) {

        LinearLayoutManager dateLayout = (LinearLayoutManager) periodRecycler.getLayoutManager();
        int firstVisibleItemIndex = dateLayout.findFirstCompletelyVisibleItemPosition();
        if (firstVisibleItemIndex > 0) {
            dateLayout.smoothScrollToPosition(periodRecycler, null, firstVisibleItemIndex - 1);
        }

    }

    public void onRightClicked(View view) {

        LinearLayoutManager dateLayout = (LinearLayoutManager) periodRecycler.getLayoutManager();
        int totalItemCount = periodRecycler.getAdapter().getItemCount();
        if (totalItemCount <= 0) return;
        int lastVisibleItemIndex = dateLayout.findLastVisibleItemPosition();
        periodRecycler.smoothScrollToPosition(dateLayout.findLastVisibleItemPosition() + 1);
        if (lastVisibleItemIndex >= totalItemCount) return;
        dateLayout.smoothScrollToPosition(periodRecycler, null, lastVisibleItemIndex + 1);


    }
}