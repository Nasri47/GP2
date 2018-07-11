package com.example.nasri.gp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
public class ReservationFragment extends Fragment  {
    public static Context reserveConte;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view, container, false);
        reserveConte = getContext() ;
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

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        ListAdapter listAdapter = new ListAdapter(reservationDetails , reserveConte);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(listAdapter);
        return rootView;
    }
}
