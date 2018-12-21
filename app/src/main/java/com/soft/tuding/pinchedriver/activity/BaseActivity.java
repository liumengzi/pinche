package com.soft.tuding.pinchedriver.activity;
/**
 * 活动基类
 * Created by lichao on 17/5/2.
 */

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.soft.tuding.pinchedriver.util.ToastUtils;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    protected Context context;
    // public LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // AppManager.getAppManager().addActivity(this);
        //   loadingDialog = new LoadingDialog(this);
        context = getApplicationContext();
        setContentView(setContent());
        ButterKnife.bind(this);
        initViews(savedInstanceState);
        //设置沉浸式状态栏
        setSystemBarTransparent();
        setViews();
        //隐藏toolbar标题
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void showToast(String message) {
        ToastUtils.showToast(this, message);
    }

    private void setSystemBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // LOLLIPOP解决方案
            getWindow().getDecorView().setSystemUiVisibility(View
                    .SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(0xffffffff);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT <
                Build.VERSION_CODES.LOLLIPOP) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup contentView = (ViewGroup)
                    this.findViewById(android.R.id.content);
            View statusBarView = contentView.getChildAt(0);
            //改变颜色时避免重复添加statusBarView
            if (statusBarView != null && statusBarView.getMeasuredHeight() == getStatusBarHeight
                    ()) {
                statusBarView.setBackgroundColor(0xff999999);
                return;
            }
            statusBarView = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, getStatusBarHeight());
            statusBarView.setBackgroundColor(0xff999999);
            contentView.addView(statusBarView, lp);
        }

    }

    public int getStatusBarHeight() {
        // 获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            int statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            return statusBarHeight;
        }
        return 0;
    }

    protected abstract int setContent();

    /**
     * 初始化控件
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 设置控件
     */
    protected abstract void setViews();

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity&从堆栈中移除
        // AppManager.getAppManager().finishActivity(this);
        ButterKnife.unbind(this);
    }
}
