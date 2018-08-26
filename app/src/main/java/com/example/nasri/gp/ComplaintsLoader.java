package com.example.nasri.gp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by Bors on 8/12/2018.
 */

public class ComplaintsLoader extends android.support.v4.content.AsyncTaskLoader<List<ComplaintsInfo>> {

    private static final String LOG_TAG = ComplaintsLoader.class.getName();
    private String murl;

    public ComplaintsLoader(@NonNull Context context , String url) {
        super(context);
        murl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<ComplaintsInfo> loadInBackground() {
        if (murl == null) {
            return null;
        }
        List<ComplaintsInfo> login = ComplaintUtils.fetchfieldsData(murl);
        return login;
    }
}
