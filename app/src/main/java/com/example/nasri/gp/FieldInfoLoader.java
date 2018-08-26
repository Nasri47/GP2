package com.example.nasri.gp;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by Bors on 8/11/2018.
 */

public class FieldInfoLoader extends android.support.v4.content.AsyncTaskLoader<FieldInformations> {
    private static final String LOG_TAG = FieldInfoLoader.class.getName();
    private String murl;
    public FieldInfoLoader(Context context  , String url) {
        super(context);
        murl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public FieldInformations loadInBackground() {
        if (murl == null) {
            return null;
        }
        FieldInformations login = FieldInfoUtils.fetchfieldsData(murl);
        return login;
    }
}
