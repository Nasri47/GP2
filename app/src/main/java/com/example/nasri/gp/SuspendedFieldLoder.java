package com.example.nasri.gp;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by Bors on 8/31/2018.
 */

public class SuspendedFieldLoder extends AsyncTaskLoader<FieldInformations> {

    private static final String LOG_TAG = fieldsListLoader.class.getName();
    private String murl;

    public SuspendedFieldLoder(Context context , String url) {
        super(context);
        murl = url;
    }

    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public FieldInformations loadInBackground() {
        if (murl == null) {
            return null;
        }
        FieldInformations  complaints = SuspendedFiedUtils.fetchfieldsData(murl);
        return complaints;
    }
}
