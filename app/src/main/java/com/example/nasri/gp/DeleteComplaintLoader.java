package com.example.nasri.gp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bors on 9/3/2018.
 */

public class DeleteComplaintLoader extends android.support.v4.content.AsyncTaskLoader<List<ComplaintsInfo>> {

    private static final String LOG_TAG = DeleteComplaintLoader.class.getName();
    private String murl;
    public DeleteComplaintLoader(Context context  , String url) {
        super(context);
        murl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<ComplaintsInfo> loadInBackground() {
        if (murl == null) {
            return null;
        }
        List<ComplaintsInfo> login = DeleteComlaintUtils.fetchfieldsData(murl);
        return login;
    }
}
