package com.example.nasri.gp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bors on 9/3/2018.
 */

@SuppressLint("ValidFragment")
public class DeleteComlaint extends ComplaintsFragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<ComplaintsInfo>>{

    private String USGS_REQUEST_URL;
    private static int DELETE_COMPLAINT_LOADER_ID = 6 ;
    public static int response ;
    private ComplaintsFragment complaintsFragment ;
    android.support.v4.app.LoaderManager loder ;
    Context context  ;
    public DeleteComlaint (int complaintId){
        complaintsFragment = new ComplaintsFragment();
        loder = complaintsFragment.loaderManager;
        context = complaintsFragment.getContext();
        USGS_REQUEST_URL =
                "http://192.168.43.172/api/deletecomplaints?comp_id=" + complaintId;
        startLoader();
    }

    @Override
    public Loader<List<ComplaintsInfo>> onCreateLoader(int id, @Nullable Bundle args) {
        return new DeleteComplaintLoader(complaintsFragment.complintConte , USGS_REQUEST_URL);
    }

    public void onLoadFinished(@NonNull Loader<ComplaintsInfo> loader, FieldInformations data) {
        Toast.makeText(context , "Complaint deleted", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<ComplaintsInfo>> loader) {

    }


    public void startLoader(){
        complaintsFragment = new ComplaintsFragment();
        android.support.v4.app.LoaderManager loaderManager = complaintsFragment.loaderManager;
        loaderManager.initLoader(DELETE_COMPLAINT_LOADER_ID, null,  this);
        DELETE_COMPLAINT_LOADER_ID++;
    }
}


/* response = complaintsInfo.getComlaintResponse() ;
            if (complaintsInfo.getComlaintResponse() == 1){
                Toast.makeText(ComplaintsFragment.getComplintContext() , "Complaint deleted", Toast.LENGTH_LONG)
                        .show();
            }else {
                Toast.makeText(ComplaintsFragment.getComplintContext() , "Something went wrong try again later", Toast.LENGTH_LONG)
                        .show();
            }*/