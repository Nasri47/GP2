package com.example.nasri.gp;

import android.content.AsyncTaskLoader;
import android.content.Context;
/**
 * Created by Bors on 8/16/2018.
 */

public class AddFieldLoader extends AsyncTaskLoader<FieldInformations> {
    private static final String LOG_TAG = fieldsListLoader.class.getName();
    private String murl;
    public AddFieldLoader(Context context , String url) {
        super(context);
        murl = url;
        System.out.println(murl);
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
        FieldInformations fields = AddFieldUtils.fetchfieldsData(murl);
        return fields;
    }
}