package com.example.nasri.gp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.List;

public class FieldsListAdapter extends RecyclerView.Adapter<FieldsListAdapter.MyViewHolder> implements Filterable {

    Context context;
    List<FieldItem> fieldItems, filterList;
    CustomFilter filter;

    public FieldsListAdapter(List<FieldItem> fieldItems, Context context) {
        this.fieldItems = fieldItems;
        this.context = context;
        this.filterList = fieldItems;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter(filterList, this);
        }

        return filter;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_field_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FieldItem period = fieldItems.get(position);
        holder.fieldName.setText(period.getfieldName());
        holder.fieldLocation.setText(period.getfieldCity());
    }

    @Override
    public int getItemCount() {
        return fieldItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView fieldName, fieldLocation;

        public MyViewHolder(View view) {
            super(view);
            fieldName = (TextView) view.findViewById(R.id.field_name);
            fieldLocation = (TextView) view.findViewById(R.id.city_name);
        }
    }
}
