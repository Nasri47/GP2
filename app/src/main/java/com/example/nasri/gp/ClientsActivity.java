package com.example.nasri.gp;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Loader;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ClientsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<UserInfo>> {

    private List<UserInfo> userInfo = new ArrayList<UserInfo>();
    private static final String USGS_REQUEST_URL =
            "http://192.168.43.172/api/getclients?field_id=" + LoginInfo.getFieldId();
    private static final int FIELD_LIST_LOADER_ID = 1 ;
    SearchView fieldSearchView;
    ClientsAdapter clientsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(FIELD_LIST_LOADER_ID, null, this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.clients_recycler);
        fieldSearchView = (SearchView) findViewById(R.id.client_search);
        clientsAdapter = new ClientsAdapter(userInfo , this);
        RecyclerView.LayoutManager periodLayout = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(periodLayout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(clientsAdapter);

        //Search
        fieldSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                clientsAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    @Override
    public Loader<List<UserInfo>> onCreateLoader(int i, Bundle bundle) {
        return new ClientsLoader(this , USGS_REQUEST_URL) ;
    }

    @Override
    public void onLoadFinished(Loader<List<UserInfo>> loader, List<UserInfo> userInfos) {

        View loadingIndicator = findViewById(R.id.client_indecator);
        loadingIndicator.setVisibility(View.GONE);

        //emptyState.setVisibility(View.VISIBLE);
        userInfo.clear();
        if(userInfos != null && !userInfos.isEmpty()){
            userInfo.addAll(userInfos);
        }else {
            fieldSearchView.setVisibility(View.GONE);
            LinearLayout empty = (LinearLayout) findViewById(R.id.empty_client);
            empty.setVisibility(View.VISIBLE);
            TextView empteText = (TextView) findViewById(R.id.empty_client_text);
            empteText.setText("Nothing to show !");
        }

    }
    @Override
    public void onLoaderReset(Loader<List<UserInfo>> loader) {

    }
}