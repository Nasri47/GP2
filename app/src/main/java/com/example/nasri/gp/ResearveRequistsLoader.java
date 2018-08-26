package com.example.nasri.gp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by Bors on 8/11/2018.
 */

public class ResearveRequistsLoader extends android.support.v4.content.AsyncTaskLoader<List<ResearvationsRequistsInfo>> {
    private static final String LOG_TAG = ResearveRequistsLoader.class.getName();
    private String murl;
    public ResearveRequistsLoader(Context context  , String url) {
        super(context);
        murl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<ResearvationsRequistsInfo> loadInBackground() {
        if (murl == null) {
            return null;
        }
        List<ResearvationsRequistsInfo> login = ResearveRequistsUtils.fetchfieldsData(murl);
        return login;
    }
}
