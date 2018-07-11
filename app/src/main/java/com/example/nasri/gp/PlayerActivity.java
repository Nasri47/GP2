package com.example.nasri.gp;

import android.content.Intent;
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
    private Button reserveButton;

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
        Calendar dateCal = Calendar.getInstance();
        dateCal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E");
        for(int i = 0; i< 6; i++){
            daysList.add(dateFormat.format(dateCal.getTime()));
            dateCal.add(Calendar.DAY_OF_YEAR, 1);
        }
        Calendar historyCal = Calendar.getInstance();
        historyCal.getTime();
        SimpleDateFormat historyFormat = new SimpleDateFormat("M/dd");
        for(int i = 0; i< 30; i++){
            historyList.add(historyFormat.format(historyCal.getTime()));
            historyCal.add(Calendar.DAY_OF_YEAR, 1);
        }
        daysFlipper = (ViewFlipper) findViewById(R.id.days_flipper);
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
    public void onClickReserve(View view){
        Intent log = new Intent(PlayerActivity.this, reserveRegester.class);
        startActivity(log);
    }
}