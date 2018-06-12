package com.example.nasri.gp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CategoryAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FieldFragment();
        } else if (position == 1) {
            return new ReservationFragment();
        } else {
            return new ComplaintsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.field);
        } else if (position == 1) {
            return mContext.getString(R.string.reservation);
        } else {
            return mContext.getString(R.string.complaints);
        }
    }

}
