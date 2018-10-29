package com.example.nasri.gp;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SliderAdapter extends PagerAdapter {

    Bitmap firstImg ;
    Bitmap secondImg ;
    Bitmap thirdImg ;
    public Bitmap[] images ;
    /*
    public int[] slideImages = {
            R.mipmap.first,
            R.mipmap.second,
            R.mipmap.third
    };
    */
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }
    public SliderAdapter(Context context , Bitmap first) {
        this.context = context;
        firstImg = first ;
        images = new Bitmap[1];
        images[0] = firstImg ;
    }
    public SliderAdapter(Context context , Bitmap first , Bitmap second) {
        this.context = context;
        firstImg = first ;
        secondImg = second ;
        images = new Bitmap[2];
        images[0] = firstImg ;
        images[1] = secondImg ;
    }
    public SliderAdapter(Context context , Bitmap first , Bitmap second , Bitmap third) {
        this.context = context;
        firstImg = first ;
        secondImg = second ;
        thirdImg = third ;
        images = new Bitmap[3];
        images[0] = firstImg ;
        images[1] = secondImg ;
        images[2] = thirdImg ;
    }
    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);
        ImageView slideImageView = (ImageView) view.findViewById(R.id.imageView);
            slideImageView.setImageBitmap(images[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}