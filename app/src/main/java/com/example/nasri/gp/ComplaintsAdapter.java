package com.example.nasri.gp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import java.util.List;

/**
 * Created by Bors on 8/12/2018.
 */

public class ComplaintsAdapter extends RecyclerView.Adapter<ComplaintsAdapter.MyViewHolder> {

    private List<ComplaintsInfo> complaintsInfos;
    private static Context context;

    public ComplaintsAdapter(List<ComplaintsInfo> listDetails) {
        this.complaintsInfos = listDetails;
    }

    public ComplaintsAdapter(List<ComplaintsInfo> listDetails , Context conte) {
        this.complaintsInfos = listDetails;
        this.context = conte ;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ComplaintsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ComplaintsInfo list = complaintsInfos.get(position);

        holder.userName.setText(list.getUserName());
        holder.phoneNumber.setText(list.getUserPhone());
        holder.message.setText(list.getMasage());
    }

    @Override
    public int getItemCount() {return complaintsInfos.size();}

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, phoneNumber, message, accept, decline, delete;

        public MyViewHolder(View view) {
            super(view);
            userName = view.findViewById(R.id.l_user_name);
            phoneNumber = view.findViewById(R.id.l_phone_Number);
            message = view.findViewById(R.id.l_message);
            decline = view.findViewById(R.id.l_decline);
            accept = view.findViewById(R.id.l_accept);
            delete = view.findViewById(R.id.l_delete);
            delete.setText("Delete");
            accept.setVisibility(View.GONE);
            decline.setVisibility(View.GONE);
        }
    }
}
