package com.example.nasri.gp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

public class FieldsListAdapter extends RecyclerView.Adapter<FieldsListAdapter.MyViewHolder> implements Filterable {

    Context context;
    List<FieldItem> fieldItems, filterList;
    CustomFilter filter;
    public Bitmap imageBitmap ;

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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        FieldItem period = fieldItems.get(position);
        holder.fieldName.setText(period.getfieldName());
        holder.fieldLocation.setText(period.getfieldCity());
        String imgsUrl = "http://192.168.43.172/Admin/";
        new FieldsListAdapter.DownloadImageTask().execute(imgsUrl + fieldItems.get(position).getFieldImg());
        if (imageBitmap != null){
            holder.fieldImage.setImageBitmap(imageBitmap);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PlayerActivity.class);
                i.putExtra("fieldId", fieldItems.get(position).getFieldId());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {

        return fieldItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView fieldName, fieldLocation;
        public ImageView fieldImage ;

        public MyViewHolder(View view) {
            super(view);
            fieldName = (TextView) view.findViewById(R.id.field_name);
            fieldLocation = (TextView) view.findViewById(R.id.city_name);
            fieldImage = (ImageView) view.findViewById(R.id.field_pic);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void , Bitmap> {


        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            setImageBitmap(result);
        }
    }

    public void setImageBitmap(Bitmap b){
        imageBitmap = b ;
    }
}