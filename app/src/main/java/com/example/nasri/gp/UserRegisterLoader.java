package com.example.nasri.gp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Bors on 10/26/2018.
 */


public class UserRegisterLoader extends AsyncTaskLoader<UserInfo> {
    private static final String LOG_TAG = fieldsListLoader.class.getName();
    private String murl;
    public UserRegisterLoader(Context context , String url) {
        super(context);
        murl = url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public UserInfo loadInBackground() {

        if (murl == null) {
            return null;
        }
        UserInfo fields = UserRegisterUtils.fetchfieldsData(murl);
        return fields;
    }
}

