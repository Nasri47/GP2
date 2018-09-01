package com.example.nasri.gp;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by Bors on 8/30/2018.
 */

public class ChangePassLoader extends AsyncTaskLoader<ChangePassRespons> {

    private static final String LOG_TAG = EditFieldInfoLoder.class.getName();
    private String murl;

    public ChangePassLoader(Context context , String url) {
        super(context);
        murl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ChangePassRespons loadInBackground() {
        if (murl == null) {
            return null;
        }
        ChangePassRespons login = ChangePassUtils.fetchfieldsData(murl);
        return login;
    }
}
