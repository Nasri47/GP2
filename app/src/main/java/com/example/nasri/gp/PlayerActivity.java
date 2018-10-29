package com.example.nasri.gp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.InputStream;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlayerActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<FieldInformations> {
    private ViewPager mSlideViewPager;
    private SliderAdapter sliderAdapter;
    private Time openTime ;
    private int count = 0 ;
    private int secCount = 0 ;
    private Bitmap firstimage ;
    private Bitmap secondImage ;
    public static FieldInformations fInformations = new FieldInformations();
    private Bitmap thirdImage ;
    public TabLayout tabLayout ;
    private List<String> imagesList ;
    public static Bitmap imageBitmap ;
    private Time closeTime ;
    private List<ResearvationsRequistsInfo> reserveList ;
    private List<PeriodTimes> periodList = new ArrayList<>();
    private List<String> daysList = new ArrayList<>();
    private List<String> compareDList = new ArrayList<>();
    private List<String> historyList = new ArrayList<>();
    private RecyclerView periodRecycler;
    private TimetableAdabter timetableAdabter;
    private TextView dayText;
    private TextView historyText;
    private TextView fieldName;
    private TextView fieldCity;
    private TextView fieldPhone;
    private TextView fieldPrice;
    private View period ;
    private Button changePic ;
    static int fieldId ;
    private LinearLayout innerDayHistoryLayout;
    private ViewFlipper daysFlipper;
    private Button reserveButton;
    private static final int FIELD_INFO_LOADER_ID = 1 ;
    private String USGS_REQUEST_URL ;
    int counterD = 0 ;


    Calendar historyCal1 ;
    SimpleDateFormat historyFormat1 ;
    String currentDay1 ;

    Handler handler = new Handler();
    Runnable refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        fieldName = (TextView) findViewById(R.id.field_name_player);
        fieldCity = (TextView) findViewById(R.id.field_location_player);
        fieldPhone = (TextView) findViewById(R.id.player_phone);
        fieldPrice = (TextView) findViewById(R.id.player_price);
        changePic = (Button) findViewById(R.id.change_pic) ;
        changePic.setVisibility(View.GONE);
        periodRecycler = (RecyclerView) findViewById(R.id.timetable_recycle_view);
        reserveButton = (Button) findViewById(R.id.reserve_butt);
        period = findViewById(R.id.time_table) ;
        timetableAdabter = new TimetableAdabter(periodList , reserveButton , this);
        periodRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager periodLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        periodRecycler.setLayoutManager(periodLayout);
        periodRecycler.setItemAnimator(new DefaultItemAnimator());
        periodRecycler.setAdapter(timetableAdabter);
        mSlideViewPager = (ViewPager) findViewById(R.id.slideviewpager);
        sliderAdapter = new SliderAdapter(this);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
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
        for(int i = 0; i< 7; i++){
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
        historyCal1 = Calendar.getInstance();
        historyCal1.getTime();
        historyFormat1 = new SimpleDateFormat("MM/dd");
        currentDay1 = historyFormat1.format(historyCal1.getTime()) ;

        for(int i = 0; i< 30; i++){
            compareDList.add(historyFormat1.format(historyCal1.getTime()));
            historyCal1.add(Calendar.DAY_OF_YEAR, 1);
        }

        if (timetableAdabter.getSelected() >= 2){
                reserveButton.setEnabled(true);
        }else{
                reserveButton.setEnabled(false);
        }
    }

    private void getPeriod() {
        periodList.clear();
        Calendar historyCal = Calendar.getInstance();
        historyCal.getTime();
        SimpleDateFormat historyFormat = new SimpleDateFormat("MM/dd");
        String currentDay = historyFormat.format(historyCal.getTime()) ;
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Calendar startCalc = Calendar.getInstance();
        Calendar endCalc = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        cal2.setTime(closeTime);
        cal.setTime(openTime);
        while (!cal.equals(cal2)) {
            String newTime = df.format(cal.getTime());
            for (int x = 0 ; x < reserveList.size() ; x++) {
                ////
                String reDay = reserveList.get(x).getResrveDate() ;
                Date date = new Date();
                DateFormat formatter = new SimpleDateFormat("M/d/yyyy");
                try {
                    String[] calend = reDay.split("-");
                    String finaDate = calend[1] + "/" +calend[2]+"/"+calend[0];
                    date = formatter.parse(finaDate);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                SimpleDateFormat newFormat = new SimpleDateFormat("MM/dd");
                String finalString = newFormat.format(date);
                if (currentDay.equals(finalString)) {
                /////
                Time reserveStart = Time.valueOf(reserveList.get(x).getReserveStart());
                Time reserveEnd = Time.valueOf(reserveList.get(x).getReserveEnd());
                startCalc.setTime(reserveStart);
                endCalc.setTime(reserveEnd);
                if (cal.equals(startCalc)) {
                    while (!cal.equals(endCalc)) {
                        cal.add(Calendar.MINUTE, 30);
                    }
                    String star = df.format(startCalc.getTime());
                    String end = df.format(endCalc.getTime());
                    periodList.add(new PeriodTimes(star + " " + end, 2));
                    newTime = df.format(endCalc.getTime());
                }
            }
            }
            cal.add(Calendar.MINUTE, 30);
            String newTim = df.format(cal.getTime());
            periodList.add(new PeriodTimes(newTime + " " + newTim , 1));

        }
        timetableAdabter.notifyDataSetChanged();
    }



    private void changeDay(String currentDay) {
        periodList.clear();
        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Calendar startCalc = Calendar.getInstance();
        Calendar endCalc = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        cal2.setTime(closeTime);
        cal.setTime(openTime);
        while (!cal.equals(cal2)) {
            String newTime = df.format(cal.getTime());
            for (int x = 0 ; x < reserveList.size() ; x++) {
                ////
                String reDay = reserveList.get(x).getResrveDate() ;
                Date date = new Date();
                DateFormat formatter = new SimpleDateFormat("M/d/yyyy");
                try {
                    String[] calend = reDay.split("-");
                    String finaDate = calend[1] + "/" +calend[2]+"/"+calend[0];
                    date = formatter.parse(finaDate);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                SimpleDateFormat newFormat = new SimpleDateFormat("MM/dd");
                String finalString = newFormat.format(date);
                System.out.println("cccccccccccccccccccc " + currentDay + " | " + finalString);
                if (currentDay.equals(finalString)) {
                    /////
                    Time reserveStart = Time.valueOf(reserveList.get(x).getReserveStart());
                    Time reserveEnd = Time.valueOf(reserveList.get(x).getReserveEnd());
                    startCalc.setTime(reserveStart);
                    endCalc.setTime(reserveEnd);
                    if (cal.equals(startCalc)) {
                        while (!cal.equals(endCalc)) {
                            cal.add(Calendar.MINUTE, 30);
                        }
                        String star = df.format(startCalc.getTime());
                        String end = df.format(endCalc.getTime());
                        periodList.add(new PeriodTimes(star + " " + end, 2));
                        newTime = df.format(endCalc.getTime());
                    }
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
        count = 0 ;
        if (fieldInformations != null) {
            fInformations = fieldInformations;
            fieldName.setText(fieldInformations.getFieldName());
            fieldCity.setText(fieldInformations.getFieldCity());
            String fPhone = "" ;
            String sPone = "" ;
            if (!fieldInformations.gettPhone().equals("")){
                fPhone = fieldInformations.gettPhone();
            }
            if (!fieldInformations.getsPone().equals("")){
                sPone = fieldInformations.getsPone();
            }
            fieldPhone.setText(fieldInformations.getOwnerPhone() + "\n" + fPhone + "\n" + sPone);
            imagesList = fieldInformations.getImages();
            fieldId = fieldInformations.getFieldId() ;
            String imgsUrl = "http://192.168.43.172/Admin/";
            if (imagesList.size() == 3){
                count = 3 ;
                new PlayerActivity.DownloadImageTask().execute(imgsUrl + imagesList.get(0));
                new PlayerActivity.DownloadImageTask().execute(imgsUrl + imagesList.get(1));
                new PlayerActivity.DownloadImageTask().execute(imgsUrl + imagesList.get(2));
            }else if (imagesList.size() == 2){
                count = 2 ;
                new PlayerActivity.DownloadImageTask().execute(imgsUrl + imagesList.get(0));
                new PlayerActivity.DownloadImageTask().execute(imgsUrl + imagesList.get(1));

            }else if (imagesList.size() == 1){
                count = 1 ;
                new PlayerActivity.DownloadImageTask().execute(imgsUrl + imagesList.get(0));
            }
            if (!fieldInformations.getOpenTime().equals("00:00:00") && !fieldInformations.getCloseTime().equals("00:00:00")){
                openTime = Time.valueOf(fieldInformations.getOpenTime());
                closeTime = Time.valueOf(fieldInformations.getCloseTime());
                reserveList = new ArrayList<>();
                reserveList = fieldInformations.getReserveInfo() ;
                getPeriod();
            }else {
                reserveButton.setVisibility(View.GONE);
                period.setVisibility(View.GONE);
                daysFlipper.setVisibility(View.GONE);
            }

            if (!fieldInformations.getHourePrice().equals(0)){
                fieldPrice.setText(fieldInformations.getHourePrice() + " SDG");
            }else {
                fieldPrice.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<FieldInformations> loader) {

    }

    private class DownloadImageTask extends AsyncTask<String, Void , Bitmap> {


        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            imageBitmap = result ;
            setImageBitmap(result);
        }
    }

    public void setImageBitmap(Bitmap b){
        if (count == 1){
            sliderAdapter = new SliderAdapter(this , b);
            mSlideViewPager.setAdapter(sliderAdapter);
            tabLayout.setupWithViewPager(mSlideViewPager, true);
        }else if (count == 2){
            if (secCount == 0){
                firstimage = b ;
                secCount++ ;
            }else if (secCount == 1){
                secondImage = b ;
                sliderAdapter = new SliderAdapter(this , firstimage , secondImage);
                mSlideViewPager.setAdapter(sliderAdapter);
                tabLayout.setupWithViewPager(mSlideViewPager, true);
            }
        }else if (count == 3){
            if (secCount == 0){
                firstimage = b ;
                secCount++ ;
            }else if (secCount == 1){
                secondImage = b ;
                secCount++ ;
            }else if (secCount == 2){
                thirdImage = b ;
                sliderAdapter = new SliderAdapter(this , firstimage , secondImage , thirdImage);
                mSlideViewPager.setAdapter(sliderAdapter);
                tabLayout.setupWithViewPager(mSlideViewPager, true);
            }
        }
    }

    public void nextDay(View v){
        if (counterD == 6){
            counterD = 0 ;
        }else {
            counterD += 1 ;
        }
        changeDay(compareDList.get(counterD));
        daysFlipper.showNext();
    }

    public void preDay(View v){
        if (counterD == 0){
            counterD = 6 ;
        }else {
            counterD -= 1 ;
        }
        changeDay(compareDList.get(counterD));
        daysFlipper.showPrevious();
    }
/*
    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {

            this.handler.postDelayed(m_Runnable, 5000);
        }

    };*/
}