package com.soft.tuding.pinchedriver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.soft.tuding.pinchedriver.R;

import butterknife.Bind;

public class AccountAndSecurityActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.set_phone_number_rl)
    RelativeLayout set_phone_number_rl;
    @Bind(R.id.login_password_rl)
    RelativeLayout login_password_rl;

    @Override
    protected int setContent() {
        return R.layout.activity_account_and_security;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        set_phone_number_rl.setOnClickListener(this);
        login_password_rl.setOnClickListener(this);
    }

    @Override
    protected void setViews() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_phone_number_rl:
                startActivity(new Intent(this, SetPhoneActivity.class));
                break;
            case R.id.login_password_rl:
                startActivity(new Intent(this, SetLoginPasswordActivity.class));
                break;
        }

    }
}
