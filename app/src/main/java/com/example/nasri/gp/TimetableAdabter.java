package com.example.nasri.gp;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

public class TimetableAdabter extends RecyclerView.Adapter<TimetableAdabter.MyViewHolder> {



    private List<PeriodTimes> periodTimes;
    private int selected = 0 ;
    private Button reserveButton ;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public Button title, dd, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (Button) view.findViewById(R.id.period_id);
            //reserveButton = (Button) view.findViewById(R.id.reserve_button);
        }
    }

    public TimetableAdabter(List<PeriodTimes> periodTimes , Button reserve , Context cont) {
        this.reserveButton = reserve ;
        this.periodTimes = periodTimes;
        this.context = cont ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_timetable_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        PeriodTimes period = periodTimes.get(position);
        holder.title.setText(period.getResearveTime());
        if (period.getStateColor() == 1){
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.title.isSelected()){
                        holder.title.setSelected(false);
                        selected--;
                        if (selected < 2){
                            reserveButton.setEnabled(false);
                        }
                    }else if (!holder.title.isSelected()){
                        holder.title.setSelected(true);
                        selected++;
                        if (selected >= 2){
                            reserveButton.setEnabled(true);
                        }
                    }
                    notifyDataSetChanged();
                }
            });
        }else if (period.getStateColor() == 2){
                holder.title.setBackground(ContextCompat.getDrawable(context, R.drawable.reservedperiod));
                holder.title.setClickable(false);
        }

    }

    @Override
    public int getItemCount() {
        return periodTimes.size();
    }
    public int getSelected(){return selected;}
}
