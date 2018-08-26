package com.example.nasri.gp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
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

public class PlayerActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<FieldInformations> {
    private ViewPager mSlideViewPager;
    private SliderAdapter sliderAdapter;
    private Time openTime ;
    private Time closeTime ;
    private List<ResearvationsRequistsInfo> reserveList ;
    private List<PeriodTimes> periodList = new ArrayList<>();
    private List<String> daysList = new ArrayList<>();
    private List<String> historyList = new ArrayList<>();
    private RecyclerView periodRecycler;
    private TimetableAdabter timetableAdabter;
    private TextView dayText;
    private TextView historyText;
    private TextView fieldName;
    private TextView fieldCity;
    private TextView fieldPhone;
    private TextView fieldPrice;
    static int fieldId ;
    private LinearLayout innerDayHistoryLayout;
    private ViewFlipper daysFlipper;
    private Button reserveButton;
    private static final int FIELD_INFO_LOADER_ID = 1 ;
    private String USGS_REQUEST_URL ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        fieldName = (TextView) findViewById(R.id.field_name_player);
        fieldCity = (TextView) findViewById(R.id.field_location_player);
        fieldPhone = (TextView) findViewById(R.id.player_phone);
        fieldPrice = (TextView) findViewById(R.id.player_price);
        periodRecycler = (RecyclerView) findViewById(R.id.timetable_recycle_view);
        reserveButton = (Button) findViewById(R.id.reserve_button);
        timetableAdabter = new TimetableAdabter(periodList , reserveButton , this);
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
        Bundle bundle = getIntent().getExtras();
        int fieldId = bundle.getInt("fieldId");
        USGS_REQUEST_URL = "http://192.168.43.172/api/getfieldbyid?field_id=" + fieldId ;
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(FIELD_INFO_LOADER_ID, null, this);
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
        if (timetableAdabter.getSelected() >= 2){
                reserveButton.setEnabled(true);
        }else{
                reserveButton.setEnabled(false);
        }
    }

    private void getPeriod() {
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Calendar startCalc = Calendar.getInstance();
        Calendar endCalc = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        cal2.setTime(closeTime);
        cal.setTime(openTime);
        while (!cal.equals(cal2)) {
            String newTime = df.format(cal.getTime());
            for (int x = 0 ; x < reserveList.size() ; x++){
                Time reserveStart = Time.valueOf(reserveList.get(x).getReserveStart());
                Time reserveEnd = Time.valueOf(reserveList.get(x).getReserveEnd());
                startCalc.setTime(reserveStart);
                endCalc.setTime(reserveEnd);
                if (cal.equals(startCalc)){
                    while (!cal.equals(endCalc)){
                        cal.add(Calendar.MINUTE, 30);
                    }
                    String star = df.format(startCalc.getTime());
                    String end = df.format(endCalc.getTime());
                    periodList.add(new PeriodTimes(star + " " + end , 2));
                    newTime = df.format(endCalc.getTime());
                }
            }
            cal.add(Calendar.MINUTE, 30);
            String newTim = df.format(cal.getTime());
            periodList.add(new PeriodTimes(newTime + " " + newTim , 1));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.complaint_button, menu);
        menu.add(1 , 1 , 1 , "Complaints");
        menu.add(2 , 2 , 2 , "Contact us");
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == 1) {
            Intent log = new Intent(PlayerActivity.this, SendComplaint.class);
            startActivity(log);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<FieldInformations> onCreateLoader(int i, Bundle bundle) {
        return new PlayerFieldLoder(this , USGS_REQUEST_URL) ;
    }

    @Override
    public void onLoadFinished(Loader<FieldInformations> loader, FieldInformations fieldInformations) {
        if (fieldInformations != null) {
            fieldName.setText(fieldInformations.getFieldName());
            fieldCity.setText(fieldInformations.getFieldCity());
            fieldPrice.setText(fieldInformations.getHourePrice());
            fieldPhone.setText(fieldInformations.getOwnerPhone());
            openTime = Time.valueOf(fieldInformations.getOpenTime());
            closeTime = Time.valueOf(fieldInformations.getCloseTime());
            fieldId = fieldInformations.getFieldId() ;
            reserveList = new ArrayList<>();
            reserveList = fieldInformations.getReserveInfo() ;
            getPeriod();
        }
    }

    @Override
    public void onLoaderReset(Loader<FieldInformations> loader) {

    }
/*
    @NonNull
    @Override
    public Loader<FieldInformations> onCreateLoader(int id, @Nullable Bundle args) {
        return new FieldInfoLoader(this , USGS_REQUEST_URL) ;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<FieldInformations> loader, FieldInformations data) {
        if (data != null) {
            fieldName.setText(data.getFieldName());
            fieldCity.setText(data.getFieldCity());
            fieldPrice.setText(data.getHourePrice());
            fieldPhone.setText(data.getOwnerPhone());
            openTime = Time.valueOf(data.getOpenTime());
            closeTime = Time.valueOf(data.getCloseTime());
            reserveList = new ArrayList<>();
            reserveList = data.getReserveInfo() ;
            getPeriod();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<FieldInformations> loader) {}*/
}