package com.example.nasri.gp;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Loader;
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

import java.util.ArrayList;
import java.util.List;

//
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class FieldsList extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<FieldItem>> {

    private List<FieldItem> fieldItems = new ArrayList<>();
    private RecyclerView fieldsRecycler;
    private FieldsListAdapter fieldsAdapter;
    private Toolbar myToolbar ;
    SearchView fieldSearchView;
    private static final String USGS_REQUEST_URL =
            "http://192.168.43.172/api/fieldslist/";
    private static final int FIELD_LIST_LOADER_ID = 1 ;
    private static final String LOG_TAG = FieldsList.class.getName();
    private ProgressDialog processDialog;
    private JSONArray restulJsonArray;
    private int success = 0;
    private ImageView emptyState ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fields_list);
        //emptyState = (ImageView) findViewById(R.id.empty) ;
        fieldSearchView = (SearchView) findViewById(R.id.mSearch);
        fieldsRecycler = (RecyclerView) findViewById(R.id.fields_list_recycler);

        fieldsAdapter = new FieldsListAdapter(fieldItems , this);
        fieldsRecycler.setHasFixedSize(true);
        RecyclerView.LayoutManager periodLayout = new LinearLayoutManager(getApplicationContext());
        fieldsRecycler.setLayoutManager(periodLayout);
        fieldsRecycler.setItemAnimator(new DefaultItemAnimator());
        fieldsRecycler.setAdapter(fieldsAdapter);
        //Search
        fieldSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                fieldsAdapter.getFilter().filter(query);
                return false;
            }
        });

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(FIELD_LIST_LOADER_ID, null, this);

        //getPeriod() ;

    }

    @Override
    public Loader<List<FieldItem>> onCreateLoader(int i, Bundle bundle) {
        return new fieldsListLoader(this , USGS_REQUEST_URL) ;
    }

    @Override
    public void onLoadFinished(Loader<List<FieldItem>> loader, List<FieldItem> fieldItem) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        //emptyState.setVisibility(View.VISIBLE);
        fieldItems.clear();
        if(fieldItem != null && !fieldItem.isEmpty()){
            fieldItems.addAll(fieldItem);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<FieldItem>> loader) {
        fieldItems.clear();
    }

    private void getPeriod() {

        fieldItems.add(new FieldItem("nasri", "Khartoum"));
        fieldItems.add(new FieldItem("boors", "Khartoum"));
        fieldItems.add(new FieldItem("bakri", "Khartoum"));
        fieldItems.add(new FieldItem("salah", "Khartoum"));
        fieldItems.add(new FieldItem("mnag", "Khartoum"));
        fieldItems.add(new FieldItem("snoop", "Khartoum"));
        fieldItems.add(new FieldItem("buga", "Khartoum"));
        fieldItems.add(new FieldItem("bino", "Khartoum"));
        fieldItems.add(new FieldItem("Almajd", "Khartoum"));
        fieldItems.add(new FieldItem("Almajd", "Khartoum"));
        fieldItems.add(new FieldItem("Almajd", "Khartoum"));
        fieldItems.add(new FieldItem("Almajd", "Khartoum"));
        fieldItems.add(new FieldItem("Almajd", "Khartoum"));
        fieldItems.add(new FieldItem("Almajd", "Khartoum"));
        fieldItems.add(new FieldItem("Almajd", "Khartoum"));
        fieldItems.add(new FieldItem("Almajd", "Khartoum"));
        fieldItems.add(new FieldItem("Almajd", "Khartoum"));
        fieldItems.add(new FieldItem("Almajd", "Khartoum"));
        fieldItems.add(new FieldItem("Almajd", "Khartoum"));

        fieldsAdapter.notifyDataSetChanged();
    }
}


