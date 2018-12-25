package com.soft.tuding.pinchedriver.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.soft.tuding.pinchedriver.R;
import com.soft.tuding.pinchedriver.adapter.DriverRecordAdapter;

import butterknife.Bind;

public class DriverRecordActivity extends BaseActivity {
    @Bind(R.id.driver_record_lv)
    ListView driver_record_lv;

    @Override
    protected int setContent() {
        return R.layout.activity_driver_record;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        DriverRecordAdapter driverRecordAdapter = new DriverRecordAdapter(this);
        driver_record_lv.setAdapter(driverRecordAdapter);
    }

    @Override
    protected void setViews() {

    }
}
