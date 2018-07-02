package com.example.nasri.gp;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<ListDetails> {

    public ListAdapter(Activity context, ArrayList<ListDetails> listDetails) {
        super(context, 0, listDetails);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        ListDetails details = getItem(position);

        TextView userName = (TextView) listItemView.findViewById(R.id.l_user_name);
        userName.setText(details.getmUserName());

        TextView phoneNumber = (TextView) listItemView.findViewById(R.id.l_phone_Number);
        phoneNumber.setText(details.getmPhoneNumber());

        TextView message = (TextView) listItemView.findViewById(R.id.l_message);
        message.setText(details.getmMessage());

        TextView firstText = (TextView) listItemView.findViewById(R.id.l_accept);
        TextView secondText = (TextView) listItemView.findViewById(R.id.l_decline);
        TextView thirdText = (TextView) listItemView.findViewById(R.id.l_delete);
        switch (details.getmType()) {
            case 1:
                firstText.setText("Accept");
                secondText.setText("Decline");
                thirdText.setText("Block");
                break;
            case 2:
                thirdText.setText("Delete");
                thirdText.setTextColor(Color.RED);
                firstText.setVisibility(View.GONE);
                secondText.setVisibility(View.GONE);
                break;
        }
        return listItemView;
    }
}
