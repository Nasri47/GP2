package com.example.nasri.gp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReservationFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<ResearvationsRequistsInfo>> {
    public static Context reserveConte;
    LinearLayout emptyLayout ;
    TextView emptyText ;
    View loadingIndicator ;
    private static final int RESERVE_REQUEST_LOADER_ID = 1 ;
    private String USGS_REQUEST_URL ;
    private List<ResearvationsRequistsInfo> reservationDetails = new ArrayList<ResearvationsRequistsInfo>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view, container, false);
        reserveConte = getContext() ;
        emptyLayout = (LinearLayout)  rootView.findViewById(R.id.empty_reserve);
        emptyText = (TextView) rootView.findViewById(R.id.empty_text_reserve);
        loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        USGS_REQUEST_URL = "http://192.168.43.172/api/getresearvations?field_id=" + LoginInfo.getFieldId() ;
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(RESERVE_REQUEST_LOADER_ID, null,  this);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        ReservationAdapter reservationAdapter = new ReservationAdapter(reservationDetails , reserveConte);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(reservationAdapter);
        return rootView;
    }

    @NonNull
    @Override
    public Loader<List<ResearvationsRequistsInfo>> onCreateLoader(int id, @Nullable Bundle args) {
        return new ResearveRequistsLoader(reserveConte , USGS_REQUEST_URL) ;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<ResearvationsRequistsInfo>> loader, List<ResearvationsRequistsInfo> data) {
        loadingIndicator.setVisibility(View.GONE);
        reservationDetails.clear();
        if(data != null && !data.isEmpty()){
            reservationDetails.addAll(data);
        }else{
            emptyLayout.setVisibility(View.VISIBLE);
            emptyText.setText("Nothing to show !");
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<ResearvationsRequistsInfo>> loader) {

    }
}
