package com.example.nasri.gp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlayerActivity extends AppCompatActivity {
    private ViewPager mSlideViewPager;
    private SliderAdapter sliderAdapter;
    private Time openTime ;
    private Time closeTime ;

    private List<PeriodTimes> periodList = new ArrayList<>();
    private List<String> daysList = new ArrayList<>();
    private List<String> historyList = new ArrayList<>();
    private RecyclerView periodRecycler;
    private TimetableAdabter timetableAdabter;
    private TextView dayText;
    private TextView historyText;
    private LinearLayout innerDayHistoryLayout;
    private ViewFlipper daysFlipper;
    private Button reserveButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        periodRecycler = (RecyclerView) findViewById(R.id.timetable_recycle_view);
        reserveButton = (Button) findViewById(R.id.reserve_button);
        timetableAdabter = new TimetableAdabter(periodList , reserveButton);
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

        //Date and time
        openTime =  Time.valueOf("04:00:00");
        closeTime =  Time.valueOf("12:00:00");

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
            System.out.println("jffffffffffffffffffg   " + timetableAdabter.getSelected());
        if (timetableAdabter.getSelected() >= 2){
                reserveButton.setEnabled(true);
        }else{
                reserveButton.setEnabled(false);
        }
    }

    private void getPeriod() {

        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        cal2.setTime(closeTime);
        cal.setTime(openTime);
        while (!cal.equals(cal2)){
            String newTime = df.format(cal.getTime());
            cal.add(Calendar.MINUTE, 30);
            String newTim = df.format(cal.getTime());
            periodList.add(new PeriodTimes(newTime + " " + newTim , "lutti", 4));

        }

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