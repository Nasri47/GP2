package com.example.nasri.gp;


import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.List;

public class ClientsLoader extends AsyncTaskLoader<List<UserInfo>> {
    private static final String LOG_TAG = fieldsListLoader.class.getName();
    private String murl;
    public ClientsLoader(Context context , String url) {
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
        List<UserInfo> fields = ClientsUtils.fetchfieldsData(murl);
        return fields;
    }
}
