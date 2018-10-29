package com.example.nasri.gp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.view.ViewPager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FieldFragment extends Fragment implements LoaderManager.LoaderCallbacks<FieldInformations> {
    private ViewPager mSlideViewPager;
    private SliderAdapter sliderAdapter;
    private Time openTime;
    private Time closeTime;
    public static int fieldId ;
    int counterD = 0 ;
    private List<ResearvationsRequistsInfo> reserveList ;
    private List<String> compareDList = new ArrayList<>();
    private List<String> imagesList ;
    private View period ;
    private Button changePic ;
    private List<PeriodTimes> periodList = new ArrayList<>();
    private List<String> daysList = new ArrayList<>();
    private List<String> historyList = new ArrayList<>();
    private RecyclerView periodRecycler;
    private TimetableAdabter timetableAdabter;
    private TextView dayText;
    private TextView historyText;
    private TextView fieldName ;
    private TextView fieldCity ;
    private TextView price ;
    private TextView phone ;
    private LinearLayout innerDayHistoryLayout;
    public static String fieldN ;
    public static Bitmap imageBitmap ;
    public static String fieldP ;
    public static String fieldS ;
    public static String fieldC ;
    public static String fieldON ;
    public static String fieldPN ;
    public static String fieldOT ;
    public static String fieldCT ;
    public static int suspend ;
    private Bitmap firstimage ;
    private Bitmap secondImage ;
    private Bitmap thirdImage ;
    public static String suspendResons ;
    private ViewFlipper daysFlipper;
    private ImageButton theLeft;
    private ImageButton theRight;
    private Button reserveButton;
    public static Context prfileContext;
    public static Context cont;
    private static final int FIELD_INFO_LOADER_ID = 1 ;
    private String USGS_REQUEST_URL ;
    public ImageView test ;
    public TabLayout tabLayout ;
    private int count = 0 ;
    private int secCount = 0 ;

    Calendar historyCal1 ;
    SimpleDateFormat historyFormat1 ;
    String currentDay1 ;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_field, container, false);

        changePic = (Button) rootView.findViewById(R.id.change_pic) ;
        test = (ImageView) rootView.findViewById(R.id.test) ;
        USGS_REQUEST_URL = "http://192.168.43.172/api/getfieldbyid?field_id=" + LoginInfo.getFieldId() ;
        prfileContext = getActivity();
        theLeft = (ImageButton)  rootView.findViewById(R.id.left_buttons) ;
        theRight = (ImageButton) rootView.findViewById(R.id.right_buttons) ;
        cont = getContext();
        periodRecycler = (RecyclerView) rootView.findViewById(R.id.timetable_recycle_view);
        reserveButton = (Button) rootView.findViewById(R.id.reserve_button);
        timetableAdabter = new TimetableAdabter(periodList, reserveButton , prfileContext);
        fieldName = (TextView) rootView.findViewById(R.id.field_name);
        fieldCity = (TextView) rootView.findViewById(R.id.field_city);
        price = (TextView) rootView.findViewById(R.id.price);
        phone = (TextView) rootView.findViewById(R.id.phone);
        period = rootView.findViewById(R.id.owner_period) ;
        periodRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager periodLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        periodRecycler.setLayoutManager(periodLayout);
        periodRecycler.setItemAnimator(new DefaultItemAnimator());
        periodRecycler.setAdapter(timetableAdabter);
        mSlideViewPager = (ViewPager) rootView.findViewById(R.id.slideviewpager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(FIELD_INFO_LOADER_ID, null,  this);
        TextView mUpdateDialog = rootView.findViewById(R.id.btnedit);
        changePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log = new Intent(getContext() , UpdatePics.class);
                startActivity(log);
            }
        });

        mUpdateDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log = new Intent(getContext() , EditFieldInfo.class);
                startActivity(log);
            }
        });

        //Date and history
        daysFlipper = (ViewFlipper) rootView.findViewById(R.id.days_flipper);
        //Date and history
        Calendar dateCal = Calendar.getInstance();
        dateCal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E");
        for (int i = 0; i < 7; i++) {
            daysList.add(dateFormat.format(dateCal.getTime()));
            dateCal.add(Calendar.DAY_OF_YEAR, 1);
        }
        Calendar historyCal = Calendar.getInstance();
        historyCal.getTime();
        SimpleDateFormat historyFormat = new SimpleDateFormat("M/dd");
        for (int i = 0; i < 30; i++) {
            historyList.add(historyFormat.format(historyCal.getTime()));
            historyCal.add(Calendar.DAY_OF_YEAR, 1);
        }

        for (int i = 0; i < daysList.size(); i++) {
            innerDayHistoryLayout = new LinearLayout(getContext());
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


        historyCal1 = Calendar.getInstance();
        historyCal1.getTime();
        historyFormat1 = new SimpleDateFormat("MM/dd");
        currentDay1 = historyFormat1.format(historyCal1.getTime()) ;

        for(int i = 0; i< 30; i++){
            compareDList.add(historyFormat1.format(historyCal1.getTime()));
            historyCal1.add(Calendar.DAY_OF_YEAR, 1);
        }
        Button reserveBt = (Button) rootView.findViewById(R.id.reserve_button);
        reserveBt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent log = new Intent(getContext() , reserveRegester.class);
                startActivity(log);
            }
        });

        theLeft.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (counterD == 0) {
                    counterD = 6;
                } else {
                    counterD -= 1;
                }
                changeDay(compareDList.get(counterD));
                daysFlipper.showPrevious();
            }
        });

        theRight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (counterD == 6) {
                    counterD = 0;
                } else {
                    counterD += 1;
                }
                changeDay(compareDList.get(counterD));
                daysFlipper.showNext();
            }
        });
        return rootView;
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
        Calendar dayCalc = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        SimpleDateFormat daysFormat = new SimpleDateFormat("MM/dd");
        periodList.clear();
        cal2.setTime(closeTime);
        cal.setTime(openTime);
        while (!cal.equals(cal2)) {
            String newTime = df.format(cal.getTime());
            for (int x = 0 ; x < reserveList.size() ; x++) {
                ///
                ////
                String reDay = reserveList.get(x).getResrveDate();
                Date date = new Date();
                DateFormat formatter = new SimpleDateFormat("M/d/yyyy");
                try {
                    String[] calend = reDay.split("-");
                    String finaDate = calend[1] + "/" + calend[2] + "/" + calend[0];
                    date = formatter.parse(finaDate);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                SimpleDateFormat newFormat = new SimpleDateFormat("MM/dd");
                String finalString = newFormat.format(date);
                if (currentDay.equals(finalString)) {

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

    @Override
    public Loader<FieldInformations> onCreateLoader(int i, Bundle bundle) {
        return new FieldInfoLoader(prfileContext , USGS_REQUEST_URL) ;
    }

    @Override
    public void onLoadFinished(Loader<FieldInformations> loader, FieldInformations fieldInformations) {
        count = 0 ;
        if (fieldInformations != null) {
                fieldId = fieldInformations.getFieldId() ;
                fieldName.setText(fieldInformations.getFieldName());
                fieldCity.setText(fieldInformations.getFieldCity());
                price.setText(fieldInformations.getHourePrice() + " SDG");
                phone.setText(fieldInformations.getOwnerPhone());
                fieldN = fieldInformations.getFieldName() ;
                fieldON = fieldInformations.getOwnerName() ;
                fieldP = fieldInformations.getHourePrice() ;
                fieldS = fieldInformations.getFieldSize() ;
                fieldC = fieldInformations.getFieldCity() ;
                fieldOT = fieldInformations.getOpenTime() ;
                fieldCT = fieldInformations.getCloseTime() ;
                fieldPN = fieldInformations.getOwnerPhone() ;
                suspend = fieldInformations.getSuspendState() ;
                imagesList = fieldInformations.getImages();
                String imgsUrl = "http://192.168.43.172/Admin/";
                if (imagesList.size() >= 3){
                    count = 3 ;
                        new DownloadImageTask().execute(imgsUrl + imagesList.get(0));
                        new DownloadImageTask().execute(imgsUrl + imagesList.get(1));
                        new DownloadImageTask().execute(imgsUrl + imagesList.get(2));
                }else if (imagesList.size() == 2){
                    count = 2 ;
                    new DownloadImageTask().execute(imgsUrl + imagesList.get(0));
                    new DownloadImageTask().execute(imgsUrl + imagesList.get(1));

                }else if (imagesList.size() == 1){
                    count = 1 ;
                    new DownloadImageTask().execute(imgsUrl + imagesList.get(0));
                }

            if (!fieldInformations.getOpenTime().equals("00:00:00") && !fieldInformations.getCloseTime().equals("00:00:00")){
                    openTime = Time.valueOf(fieldInformations.getOpenTime());
                    closeTime = Time.valueOf(fieldInformations.getCloseTime());
                    reserveList = new ArrayList<>();
                    reserveList.clear();
                    reserveList = fieldInformations.getReserveInfo() ;
                    getPeriod();
                }else {
                    reserveButton.setVisibility(View.GONE);
                    period.setVisibility(View.GONE);
                    daysFlipper.setVisibility(View.GONE);
                }
        }
    }

    @Override
    public void onLoaderReset(Loader<FieldInformations> loader) {periodList.clear();}

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
            sliderAdapter = new SliderAdapter(cont , b);
            mSlideViewPager.setAdapter(sliderAdapter);
            tabLayout.setupWithViewPager(mSlideViewPager, true);
        }else if (count == 2){
            if (secCount == 0){
                firstimage = b ;
                secCount++ ;
            }else if (secCount == 1){
                secondImage = b ;
                sliderAdapter = new SliderAdapter(cont , firstimage , secondImage);
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
                sliderAdapter = new SliderAdapter(cont , firstimage , secondImage , thirdImage);
                mSlideViewPager.setAdapter(sliderAdapter);
                tabLayout.setupWithViewPager(mSlideViewPager, true);
            }
        }
    }

    public Bitmap getImageBitmap(){
        return imageBitmap;
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

    public void preDay(View v){
        if (counterD == 0){
            counterD = 6 ;
        }else {
            counterD -= 1 ;
        }
        changeDay(compareDList.get(counterD));
        daysFlipper.showPrevious();
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
}