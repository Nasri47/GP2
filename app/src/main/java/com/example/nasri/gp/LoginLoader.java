package com.example.nasri.gp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;
/**
 * Created by Bors on 8/10/2018.
 */

public class LoginLoader extends AsyncTaskLoader<LoginInfo> {

    private static final String LOG_TAG = fieldsListLoader.class.getName();
    private String murl;
    public LoginLoader(Context context , String url) {
        super(context);
        murl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public LoginInfo loadInBackground() {
        if (murl == null) {
            return null;
        }
        LoginInfo login = OwnerLoginUtils.fetchfieldsData(murl);
        return login;
    }
}
