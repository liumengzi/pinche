package com.soft.tuding.pinchedriver.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.soft.tuding.pinchedriver.R;
import com.soft.tuding.pinchedriver.activity.DriverRecordActivity;

public class DriverRecordAdapter extends BaseAdapter {
    DriverRecordActivity driverRecordActivity;

    public DriverRecordAdapter(DriverRecordActivity driverRecordActivity) {

        this.driverRecordActivity = driverRecordActivity;
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
        convertView = driverRecordActivity.getLayoutInflater().inflate(R.layout.item_driver_record, null, false);
        return convertView;
    }
}
