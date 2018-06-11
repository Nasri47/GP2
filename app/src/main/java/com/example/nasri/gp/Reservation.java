package com.example.nasri.gp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class Reservation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        ArrayList<ListDetails> reservationDetails = new ArrayList<ListDetails>();
        reservationDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",1));
        reservationDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",1));
        reservationDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",1));
        reservationDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",1));
        reservationDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",1));
        reservationDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",1));
        reservationDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",1));
        reservationDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",1));
        reservationDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",1));
        reservationDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",1));
        reservationDetails.add(new ListDetails("nasri","0900244491","want to reserve the field from 4pm to 6pm",1));

        ListAdapter listAdapter = new ListAdapter(this,reservationDetails);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(listAdapter);

    }
}
