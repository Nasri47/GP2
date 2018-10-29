package com.example.nasri.gp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Bors on 10/23/2018.
 */

public class BlockUserLoader extends AsyncTaskLoader<List<UserInfo>> {
    private static final String LOG_TAG = fieldsListLoader.class.getName();
    private String murl;
    public BlockUserLoader(Context context , String url) {
        super(context);
        murl = url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<UserInfo> loadInBackground() {

        if (murl == null) {
            return null;
        }
        List<UserInfo> fields = BlockUserUtils.fetchfieldsData(murl);
        return fields;
    }
}