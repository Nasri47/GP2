package com.example.nasri.gp;

import android.content.Context;

import java.util.List;

/**
 * Created by Bors on 9/3/2018.
 */

public class ReserveResponseLoader extends android.support.v4.content.AsyncTaskLoader<List<ResearvationsRequistsInfo>> {

    private static final String LOG_TAG = ReserveResponseLoader.class.getName();
    private String murl;
    public ReserveResponseLoader(Context context  , String url) {
        super(context);
        murl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<ResearvationsRequistsInfo> loadInBackground() {
        if (murl == null) {
            return null;
        }
        List<ResearvationsRequistsInfo> login = ReseveResponseUtils.fetchfieldsData(murl);
        return login;
    }
}
