package com.example.nasri.gp;

        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;
        import java.util.List;

public class ReservesListAdapter extends RecyclerView.Adapter<ReservesListAdapter.MyViewHolder> {



    private List<ReservesInfo> reservesInfos;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, userPhone, startTime , endTime;
        public Button block , delete ;

        public MyViewHolder(View view) {
            super(view);
            userName = (Button) view.findViewById(R.id.period_id);
        }
    }

    public ReservesListAdapter(List<ReservesInfo> reservesInfos) {
        this.reservesInfos = reservesInfos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_reserves_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ReservesInfo info = reservesInfos.get(position);
        holder.userName.setText(info.getUserName());
        holder.userPhone.setText(info.getUserPhoneNumber());
        holder.startTime.setText(info.getStartTime());
        holder.endTime.setText(info.getEndTime());
        holder.block.setText("Block");
        holder.delete.setText("Delete");
    }

    @Override
    public int getItemCount() {
        return reservesInfos.size();
    }
}
