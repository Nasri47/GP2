package com.example.nasri.gp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.MyViewHolder> {
    private List<ResearvationsRequistsInfo> listDetails;
    private static Context context;


    public ReservationAdapter(List<ResearvationsRequistsInfo> listDetails) {
        this.listDetails = listDetails;
    }
    public ReservationAdapter(List<ResearvationsRequistsInfo> listDetails , Context conte) {
        this.listDetails = listDetails;
        this.context = conte ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationAdapter.MyViewHolder holder, final int position) {
        final ResearvationsRequistsInfo list = listDetails.get(position);

        holder.userName.setText(list.getUserName());
        holder.phoneNumber.setText(list.getUserPhone());
        holder.message.setText("Want to reserve your field from "+list.getReserveStart() + " to " + list.getReserveEnd());
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
            delete.setText("Delete");
        }
    }


//    public ReservationAdapter(Activity context, ArrayList<ListDetails> listDetails) {
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
