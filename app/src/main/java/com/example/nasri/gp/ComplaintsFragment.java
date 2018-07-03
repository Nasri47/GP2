package com.example.nasri.gp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ComplaintsFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ArrayList<ListDetails> complaintDetails = new ArrayList<ListDetails>();
        View rootView = inflater.inflate(R.layout.recycler_view, container, false);
        complaintDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 2));
        complaintDetails.add(new ListDetails("boors", "0900244491", "want to reserve the field from 4pm to 6pm", 2));
        complaintDetails.add(new ListDetails("snoop", "0900244491", "want to reserve the field from 4pm to 6pm", 2));
        complaintDetails.add(new ListDetails("bino", "0900244491", "want to reserve the field from 4pm to 6pm", 2));
        complaintDetails.add(new ListDetails("mnga", "0900244491", "want to reserve the field from 4pm to 6pm", 2));
        complaintDetails.add(new ListDetails("bakri", "0900244491", "want to reserve the field from 4pm to 6pm", 2));
        complaintDetails.add(new ListDetails("salah", "0900244491", "want to reserve the field from 4pm to 6pm", 2));
        complaintDetails.add(new ListDetails("buga", "0900244491", "want to reserve the field from 4pm to 6pm", 2));
        complaintDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 2));
        complaintDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 2));
        complaintDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 2));


        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        ListAdapter listAdapter = new ListAdapter(complaintDetails);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(listAdapter);
        return rootView;
    }
}
