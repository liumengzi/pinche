package com.soft.tuding.pinchedriver.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.widget.ListView;

import com.soft.tuding.pinchedriver.R;
import com.soft.tuding.pinchedriver.adapter.CouponAdapter;

import butterknife.Bind;

public class CouponActivity extends BaseActivity {
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.coupon_lv)
    ListView coupon_lv;

    @Override
    protected int setContent() {
        return R.layout.activity_coupon;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tabLayout.addTab(tabLayout.newTab().setText("未使用"));
        tabLayout.addTab(tabLayout.newTab().setText("已使用"));
        CouponAdapter couponAdapter = new CouponAdapter(this);
        coupon_lv.setAdapter(couponAdapter);
    }

    @Override
    protected void setViews() {

    }
}
