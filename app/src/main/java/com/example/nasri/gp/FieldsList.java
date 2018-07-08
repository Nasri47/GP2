package com.example.nasri.gp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class FieldsList extends AppCompatActivity {

    private List<FieldItem> fieldItems = new ArrayList<>();
    private RecyclerView fieldsRecycler;
    private FieldsListAdapter fieldsAdapter;
    private Toolbar myToolbar ;
    SearchView fieldSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fields_list);

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

        getPeriod() ;

    }

    private void getPeriod(){

        fieldItems.add(new FieldItem("nasri", "Khartoum"));
        fieldItems.add(new FieldItem("boors", "Khartoum"));
        fieldItems.add(new FieldItem("bakri", "Khartoum"));
        fieldItems.add(new FieldItem("salah", "Khartoum"));
        fieldItems.add(new FieldItem("mnag", "Khartoum"));
        fieldItems.add(new FieldItem("snoop", "Khartoum"));
        fieldItems.add(new FieldItem("buga", "Khartoum"));
        fieldItems.add(new FieldItem("bino", "Khartoum"));
        fieldItems.add(new FieldItem("Almajd", "Khartoum")) ;
        fieldItems.add(new FieldItem("Almajd", "Khartoum")) ;
        fieldItems.add(new FieldItem("Almajd", "Khartoum")) ;
        fieldItems.add(new FieldItem("Almajd", "Khartoum")) ;
        fieldItems.add(new FieldItem("Almajd", "Khartoum")) ;
        fieldItems.add(new FieldItem("Almajd", "Khartoum")) ;
        fieldItems.add(new FieldItem("Almajd", "Khartoum")) ;
        fieldItems.add(new FieldItem("Almajd", "Khartoum")) ;
        fieldItems.add(new FieldItem("Almajd", "Khartoum")) ;
        fieldItems.add(new FieldItem("Almajd", "Khartoum")) ;
        fieldItems.add(new FieldItem("Almajd", "Khartoum")) ;

        fieldsAdapter.notifyDataSetChanged();
    }
}


