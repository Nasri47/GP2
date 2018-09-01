package com.example.nasri.gp;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by Bors on 8/29/2018.
 */

public class CloseFieldLoder extends AsyncTaskLoader<FieldInformations> {
    private static final String LOG_TAG = CloseFieldLoder.class.getName();
    private String murl;
    public CloseFieldLoder(Context context , String url) {
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
        FieldInformations login = CloseFieldUtils.fetchfieldsData(murl);
        return login;
    }
}
