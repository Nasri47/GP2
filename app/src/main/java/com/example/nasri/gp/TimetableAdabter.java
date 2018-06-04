package com.example.nasri.gp;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

public class TimetableAdabter extends RecyclerView.Adapter<TimetableAdabter.MyViewHolder> {



    private List<PeriodTimes> periodTimes;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public Button title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (Button) view.findViewById(R.id.period_id);
        }
    }

    public TimetableAdabter(List<PeriodTimes> periodTimes) {
        this.periodTimes = periodTimes;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_timetable_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PeriodTimes movie = periodTimes.get(position);
        holder.title.setText(movie.getStartTime());
    }

    @Override
    public int getItemCount() {
        return periodTimes.size();
    }
}
