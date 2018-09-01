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

public class ComplaintsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<ComplaintsInfo>> {
    public static Context complintConte;
    private static final int COMPLAINTS_LOADER_ID = 1 ;
    LinearLayout emptyLayout ;
    TextView emptyText ;
    View loadingIndicator ;
    private String USGS_REQUEST_URL ;
    private List<ComplaintsInfo> complaintDetails = new ArrayList<ComplaintsInfo>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view, container, false);
        emptyLayout = (LinearLayout)  rootView.findViewById(R.id.empty_reserve);
        emptyText = (TextView) rootView.findViewById(R.id.empty_text_reserve);
        loadingIndicator = rootView.findViewById(R.id.loading_indicator);
        complintConte = getContext() ;
        USGS_REQUEST_URL = "http://192.168.43.172/api/getcomplaints?field_id=" + LoginInfo.getFieldId() ;
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(COMPLAINTS_LOADER_ID, null,  this);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        ComplaintsAdapter reservationAdapter = new ComplaintsAdapter(complaintDetails , complintConte);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(reservationAdapter);

        return rootView;
    }

    @NonNull
    @Override
    public Loader<List<ComplaintsInfo>> onCreateLoader(int id, @Nullable Bundle args) {
        return new ComplaintsLoader(complintConte , USGS_REQUEST_URL) ;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<ComplaintsInfo>> loader, List<ComplaintsInfo> data) {
        loadingIndicator.setVisibility(View.GONE);
        complaintDetails.clear();
        if(data != null && !data.isEmpty()){
            complaintDetails.addAll(data);
        }else{
            if (complaintDetails.isEmpty()) {
                emptyLayout.setVisibility(View.VISIBLE);
                emptyText.setText("Nothing to show !");
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<ComplaintsInfo>> loader) {}
}
