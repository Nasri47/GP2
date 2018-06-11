package com.example.nasri.gp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class Complaints extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        ArrayList<ListDetails> complaintDetails = new ArrayList<ListDetails>();
        complaintDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",2));
        complaintDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",2));
        complaintDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",2));
        complaintDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",2));
        complaintDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",2));
        complaintDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",2));
        complaintDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",2));
        complaintDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",2));
        complaintDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",2));
        complaintDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",2));
        complaintDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",2));
        ListAdapter listAdapter = new ListAdapter(this,complaintDetails);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(listAdapter);
    }
}
