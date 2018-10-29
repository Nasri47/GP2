package com.example.nasri.gp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Bors on 10/23/2018.
 */

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.MyViewHolder> implements Filterable {

    Context context;
    List<UserInfo> fieldItems, filterList;
    ClientSearch filter;

    public ClientsAdapter(List<UserInfo> fieldItems, Context context) {
        this.fieldItems = fieldItems;
        this.context = context;
        this.filterList = fieldItems;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new ClientSearch(filterList, this);
        }
        return filter;
    }
    @Override
    public ClientsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ClientsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ClientsAdapter.MyViewHolder holder, final int position) {
        UserInfo period = fieldItems.get(position);
        holder.uName.setText(period.getUserName());
        holder.uPhone.setText(period.getPhone());
        holder.messages.setText("From " + period.getReserveBegg() + " to " + period.getReserveEn());
        holder.accept.setVisibility(View.GONE);
        holder.block.setText("Block");
        holder.deletes.setVisibility(View.GONE);
        //holder.fieldName.setText(period.getfieldName());
        //holder.fieldLocation.setText(period.getfieldCity());
        holder.block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Blocking_user.class);
                i.putExtra("user_id", fieldItems.get(position).getId());
                i.putExtra("user_name", fieldItems.get(position).getUserName());
                i.putExtra("user_phone", fieldItems.get(position).getPhone());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {

        return fieldItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView uName, uPhone , messages , deletes , block , accept;

        public MyViewHolder(View view) {
            super(view);
            uName = (TextView) view.findViewById(R.id.l_user_name);
            uPhone = (TextView) view.findViewById(R.id.l_user_name);
            messages = (TextView) view.findViewById(R.id.l_message);
            deletes = (TextView) view.findViewById(R.id.l_delete);
            block = (TextView) view.findViewById(R.id.l_decline);
            accept = (TextView) view.findViewById(R.id.l_accept);

        }
    }
}
