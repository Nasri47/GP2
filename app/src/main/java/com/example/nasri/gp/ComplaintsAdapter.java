package com.example.nasri.gp;

import android.app.LoaderManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bors on 8/12/2018.
 */

public class ComplaintsAdapter extends RecyclerView.Adapter<ComplaintsAdapter.MyViewHolder> {

    private List<ComplaintsInfo> complaintsInfos;
    private static Context context;
    private DeleteComlaint deleteComlaint ;
    private ComplaintsFragment complaintsFragment ;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.userName.setText(complaintsInfos.get(position).getUserName());
        holder.phoneNumber.setText(complaintsInfos.get(position).getUserPhone());
        holder.message.setText(complaintsInfos.get(position).getMasage());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complaintsFragment = new ComplaintsFragment();
                complaintsFragment.deletFlag = true ;
                deleteComlaint = new DeleteComlaint(complaintsInfos.get(position).getComplaintId());
                complaintsInfos.remove(position);
                    notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {return complaintsInfos.size();}
/*
    @NonNull
    @Override
    public Loader<ArrayList> onCreateLoader(int id, @Nullable Bundle args) {
        return new DeleteComplaintLoader(context , USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList> loader, ArrayList data) {
        Toast.makeText(context , "Complaint deleted", Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList> loader) {

    }

*/
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
/*
    public void startLoader(){
        complaintsFragment = new ComplaintsFragment();
        android.support.v4.app.LoaderManager loaderManager = complaintsFragment.loaderManager;
        loaderManager.initLoader(DELETE_COMPLAINT_LOADER_ID, null,  this);
        DELETE_COMPLAINT_LOADER_ID++;
    }
    */
}
