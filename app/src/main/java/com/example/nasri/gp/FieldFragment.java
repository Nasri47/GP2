package com.example.nasri.gp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.view.ViewPager;
import android.support.v4.content.Loader;
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
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FieldFragment extends Fragment implements LoaderManager.LoaderCallbacks<FieldInformations>{
    private ViewPager mSlideViewPager;
    private SliderAdapter sliderAdapter;
    private Time openTime;
    private Time closeTime;
    private List<ResearvationsRequistsInfo> reserveList ;

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
    private ViewFlipper daysFlipper;
    private Button reserveButton;
    private Context prfileContext;
    private static final int FIELD_INFO_LOADER_ID = 1 ;
    private String USGS_REQUEST_URL ;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_field, container, false);

        USGS_REQUEST_URL = "http://192.168.43.172/api/getfieldbyid?field_id=" + LoginInfo.getFieldId() ;
        prfileContext = getActivity();
        periodRecycler = (RecyclerView) rootView.findViewById(R.id.timetable_recycle_view);
        reserveButton = (Button) rootView.findViewById(R.id.reserve_button);
        timetableAdabter = new TimetableAdabter(periodList, reserveButton , prfileContext);
        fieldName = (TextView) rootView.findViewById(R.id.field_name);
        fieldCity = (TextView) rootView.findViewById(R.id.field_city);
        price = (TextView) rootView.findViewById(R.id.price);
        phone = (TextView) rootView.findViewById(R.id.phone);
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
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(FIELD_INFO_LOADER_ID, null,  this);
        ImageButton mUpdateDialog = rootView.findViewById(R.id.btnedit);
        //Date and history
        daysFlipper = (ViewFlipper) rootView.findViewById(R.id.days_flipper);
        //Date and history
        Calendar dateCal = Calendar.getInstance();
        dateCal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E");
        for (int i = 0; i < 6; i++) {
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

        ImageView leftArrowButton = (ImageView) rootView.findViewById(R.id.left_button);
        leftArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daysFlipper.showPrevious();
            }
        });
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
        ImageView rightArrowButton = (ImageView) rootView.findViewById(R.id.right_button);
        rightArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daysFlipper.showNext();
            }
        });
        return rootView;
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

    @Override
    public Loader<FieldInformations> onCreateLoader(int i, Bundle bundle) {
        return new FieldInfoLoader(prfileContext , USGS_REQUEST_URL) ;
    }

    @Override
    public void onLoadFinished(Loader<FieldInformations> loader, FieldInformations fieldInformations) {
        if (fieldInformations != null) {
            fieldName.setText(fieldInformations.getFieldName());
            fieldCity.setText(fieldInformations.getFieldCity());
            price.setText(fieldInformations.getHourePrice());
            phone.setText(fieldInformations.getOwnerPhone());
            openTime = Time.valueOf(fieldInformations.getOpenTime());
            closeTime = Time.valueOf(fieldInformations.getCloseTime());
            reserveList = new ArrayList<>();
            reserveList = fieldInformations.getReserveInfo() ;
            getPeriod();
        }
    }

    @Override
    public void onLoaderReset(Loader<FieldInformations> loader) {}
}

