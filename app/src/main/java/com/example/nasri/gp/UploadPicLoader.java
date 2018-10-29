package com.example.nasri.gp;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by Bors on 9/11/2018.
 */

public class UploadPicLoader extends AsyncTaskLoader<FieldInformations> {

    private static final String LOG_TAG = EditFieldInfoLoder.class.getName();
    private String murl;

    public UploadPicLoader(Context context , String url) {
        super(context);
        murl = url ;
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
        FieldInformations login = UploadPicUtils.fetchfieldsData(murl);
        return login;
    }
}
