package com.example.nasri.gp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    private List<ListDetails> listDetails;
    private static Context context;


    public ListAdapter(List<ListDetails> listDetails) {
        this.listDetails = listDetails;
    }
    public ListAdapter(List<ListDetails> listDetails , Context conte) {
        this.listDetails = listDetails;
        this.context = conte ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.MyViewHolder holder, final int position) {
        final ListDetails list = listDetails.get(position);

        holder.userName.setText(list.getmUserName());
        holder.phoneNumber.setText(list.getmPhoneNumber());
        holder.message.setText(list.getmMessage());
        switch (list.getmType()) {
            case 1:
                holder.accept.setText("Accept");
                holder.decline.setText("Decline");
                holder.delete.setText("Block");
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent intent;
                        System.out.println("jjjjjjjjjjjjjj "+context+" llllllllllll");
                        intent =  new Intent(context, Blocking_user.class);
                        context.startActivity(intent);
                        notifyDataSetChanged();
                    }
                });
                break;
            case 2:
                holder.delete.setText("Delete");
                holder.delete.setTextColor(Color.RED);
                holder.accept.setVisibility(View.GONE);
                holder.decline.setVisibility(View.GONE);
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listDetails.remove(position);
                        notifyDataSetChanged();
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, phoneNumber, message, accept, decline, delete;

        public MyViewHolder(View view) {
            super(view);
            userName = view.findViewById(R.id.l_user_name);
            phoneNumber = view.findViewById(R.id.l_phone_Number);
            message = view.findViewById(R.id.l_message);
            accept = view.findViewById(R.id.l_accept);
            decline = view.findViewById(R.id.l_decline);
            delete = view.findViewById(R.id.l_delete);
        }
    }


//    public ListAdapter(Activity context, ArrayList<ListDetails> listDetails) {
//        super(context, 0, listDetails);
//    }
//
//    public View getView(final int position, View convertView, ViewGroup parent) {
//
//        View listItemView = convertView;
//        if (listItemView == null) {
//            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
//        }
//
//        final ListDetails details = getItem(position);
//
//        TextView userName = (TextView) listItemView.findViewById(R.id.l_user_name);
//        userName.setText(details.getmUserName());
//
//        TextView phoneNumber = (TextView) listItemView.findViewById(R.id.l_phone_Number);
//        phoneNumber.setText(details.getmPhoneNumber());
//
//        TextView message = (TextView) listItemView.findViewById(R.id.l_message);
//        message.setText(details.getmMessage());
//
//        TextView firstText = (TextView) listItemView.findViewById(R.id.l_accept);
//        TextView secondText = (TextView) listItemView.findViewById(R.id.l_decline);
//        TextView thirdText = (TextView) listItemView.findViewById(R.id.l_delete);
//
//        switch (details.getmType()) {
//            case 1:
//                firstText.setText("Accept");
//                secondText.setText("Decline");
//                thirdText.setText("Block");
//                break;
//            case 2:
//                thirdText.setText("Delete");
//                thirdText.setTextColor(Color.RED);
//                firstText.setVisibility(View.GONE);
//                secondText.setVisibility(View.GONE);
//                thirdText.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        remove(getItem(position));
//                        notifyDataSetChanged();
//                        notifyDataSetInvalidated();
//                    }
//                });
//                break;
//        }
//        return listItemView;
//    }

}
