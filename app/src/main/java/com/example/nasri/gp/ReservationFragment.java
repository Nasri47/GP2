package com.example.nasri.gp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ReservationFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_view, container, false);
        ArrayList<ListDetails> reservationDetails = new ArrayList<ListDetails>();
        reservationDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 1));
        reservationDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 1));
        reservationDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 1));
        reservationDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 1));
        reservationDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 1));
        reservationDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 1));
        reservationDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 1));
        reservationDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 1));
        reservationDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 1));
        reservationDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 1));
        reservationDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 1));

        ListAdapter listAdapter = new ListAdapter(getActivity(), reservationDetails);
        ListView listView = (ListView) rootView.findViewById(R.id.list_view);
        listView.setAdapter(listAdapter);
        return rootView;
    }
}
