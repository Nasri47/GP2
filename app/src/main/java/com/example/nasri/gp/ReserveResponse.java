package com.example.nasri.gp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Bors on 9/6/2018.
 */
@SuppressLint("ValidFragment")
public class ReserveResponse extends ReservationFragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<ResearvationsRequistsInfo>> {

    private String USGS_REQUEST_URL;
    private static int DELETE_COMPLAINT_LOADER_ID = 6 ;
    public static int response ;
    private ReservationFragment reservationFragment ;
    android.support.v4.app.LoaderManager loder ;
    Context context  ;


    public ReserveResponse (int reserveId , int resType){
        reservationFragment = new ReservationFragment();
        loder = reservationFragment.reserveLoader;
        context = reservationFragment.getContext();
        USGS_REQUEST_URL =
                "http://192.168.43.172/api/reserveresponse?reserve_id=" + reserveId + "&res_type=" + resType;
        startLoader();
    }

    @Override
    public Loader<List<ResearvationsRequistsInfo>> onCreateLoader(int id, @Nullable Bundle args) {
        return new ReserveResponseLoader(reservationFragment.reserveConte , USGS_REQUEST_URL);
    }

    public void onLoadFinished(@NonNull Loader<ResearvationsRequistsInfo> loader, FieldInformations data) {
        Toast.makeText(context , "Complaint deleted", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<ResearvationsRequistsInfo>> loader) {

    }


    public void startLoader(){
        reservationFragment = new ReservationFragment();
        android.support.v4.app.LoaderManager loaderManager = reservationFragment.reserveLoader;
        loaderManager.initLoader(DELETE_COMPLAINT_LOADER_ID, null,  this);
        DELETE_COMPLAINT_LOADER_ID++;
    }
}
