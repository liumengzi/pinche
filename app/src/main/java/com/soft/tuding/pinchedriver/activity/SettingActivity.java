package com.soft.tuding.pinchedriver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.soft.tuding.pinchedriver.R;

import butterknife.Bind;

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.account_and_security_rl)
    RelativeLayout account_and_security_rl;

    @Override
    protected int setContent() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        account_and_security_rl.setOnClickListener(this);
    }

    @Override
    protected void setViews() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_and_security_rl:
                startActivity(new Intent(this, AccountAndSecurityActivity.class));
                break;
        }
    }
}
