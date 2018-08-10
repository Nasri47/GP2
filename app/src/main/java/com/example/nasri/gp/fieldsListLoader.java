package com.example.nasri.gp;

/**
 * Created by Bors on 8/10/2018.
 */

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class fieldsListLoader extends AsyncTaskLoader<List<FieldItem>> {
    private static final String LOG_TAG = fieldsListLoader.class.getName();
    private String murl;
    public fieldsListLoader(Context context , String url) {
        super(context);
        murl = url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<FieldItem> loadInBackground() {

        if (murl == null) {
            return null;
        }
        List<FieldItem> fields = FieldsListUtils.fetchfieldsData(murl);
        return fields;
    }
}
