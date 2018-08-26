package com.example.nasri.gp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Bors on 8/14/2018.
 */

public class SendComlaintsLoader extends AsyncTaskLoader<ComplaintsInfo> {

    private static final String LOG_TAG = fieldsListLoader.class.getName();
    private String murl;
    public SendComlaintsLoader(Context context , String url) {
        super(context);
        murl = url;
    }

    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ComplaintsInfo loadInBackground() {
        if (murl == null) {
            return null;
        }
        ComplaintsInfo  complaints = SendComlaintsUtils.fetchfieldsData(murl);
        return complaints;
    }
}
