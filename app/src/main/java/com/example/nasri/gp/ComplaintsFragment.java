package com.example.nasri.gp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ComplaintsFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ArrayList<ListDetails> complaintDetails = new ArrayList<ListDetails>();
        View rootView = inflater.inflate(R.layout.list_view, container, false);
        complaintDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 2));
        complaintDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 2));
        complaintDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 2));
        complaintDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 2));
        complaintDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 2));
        complaintDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 2));
        complaintDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 2));
        complaintDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 2));
        complaintDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 2));
        complaintDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 2));
        complaintDetails.add(new ListDetails("nasri", "0900244491", "want to reserve the field from 4pm to 6pm", 2));

        final ListAdapter listAdapter = new ListAdapter(getActivity(), complaintDetails);
        final ListView listView = (ListView) rootView.findViewById(R.id.list_view);
        listView.setAdapter(listAdapter);
        return rootView;
    }
}
