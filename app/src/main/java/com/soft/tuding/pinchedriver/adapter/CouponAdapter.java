package com.soft.tuding.pinchedriver.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.soft.tuding.pinchedriver.R;
import com.soft.tuding.pinchedriver.activity.CouponActivity;

public class CouponAdapter extends BaseAdapter {
    CouponActivity couponActivity;

    public CouponAdapter(CouponActivity couponActivity) {

        this.couponActivity = couponActivity;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = couponActivity.getLayoutInflater().inflate(R.layout.item_coupon, null, false);
        return convertView;
    }
}
