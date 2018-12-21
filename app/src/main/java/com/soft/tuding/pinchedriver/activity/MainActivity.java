package com.soft.tuding.pinchedriver.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.soft.tuding.pinchedriver.R;
import com.soft.tuding.pinchedriver.util.AmapLocationUtils;

import java.util.List;

import butterknife.Bind;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements EasyPermissions
        .PermissionCallbacks, AmapLocationUtils.LocateCallBack, View.OnClickListener {
    @Bind(R.id.map)
    MapView mMapView;
    @Bind(R.id.tv_cancle_order)
    TextView tvCancleOrder;
    @Bind(R.id.tv_arrive_startpoint)
    TextView tvArriveStartpoint;
    @Bind(R.id.ll_receiver_dispatcher)
    LinearLayout llReceiverDispatcher;
    @Bind(R.id.tv_start_receiverorder)
    TextView tvStartReceiverorder;
    private AmapLocationUtils amapLocationUtils;
    private AMap map;
    private boolean isExit;

    @Override
    protected int setContent() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
        map = mMapView.getMap();
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setLogoBottomMargin(-100);
        amapLocationUtils = AmapLocationUtils.getInstacne(this);
        amapLocationUtils.setLocateCallback(this);
        //所要申请的权限
        String[] perms = {Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {//检查是否获取该权限
            //  Log.i(TAG, ”已获取权限”);
            amapLocationUtils.startLocation();
        } else {
            //第二个参数是被拒绝后再次申请该权限的解释
            //第三个参数是请求码
            //第四个参数是要申请的权限
            EasyPermissions.requestPermissions(this, "需要获取定位权限", 0, perms);
        }
    }

    @Override
    protected void setViews() {
        tvStartReceiverorder.setOnClickListener(this);
        tvArriveStartpoint.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //把申请权限的回调交由EasyPermissions处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //分别返回授权成功和失败的权限
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        //  Log.i(TAG, ”获取成功的权限” + perms);
        amapLocationUtils.startLocation();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //   Log.i(TAG, ”获取失败的权限” + perms);
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        amapLocationUtils.stopLocation();
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLocateSuccess(AMapLocation amapLocation) {
        double latitude = amapLocation.getLatitude();
        double longitude = amapLocation.getLongitude();
      //  amapLocationUtils.setLocationMark(map, this, latitude, longitude);
        amapLocationUtils.setMapCenter(map, this, latitude, longitude);
        amapLocationUtils.setDefaultLocationImg(map);
    }

    @Override
    public void onLocateFail(String error) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start_receiverorder:
                tvStartReceiverorder.setVisibility(View.GONE);
                llReceiverDispatcher.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_arrive_startpoint:
                String point = tvArriveStartpoint.getText().toString().trim();
                if (point.equals("到达起点")) {
                    tvArriveStartpoint.setText("到达乘客1起点");
                } else if (point.equals("到达乘客1起点")) {
                    tvArriveStartpoint.setText("接到乘客");
                } else if (point.equals("接到乘客")) {
                    tvArriveStartpoint.setText("到达终点");
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!isExit) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 3000);
            showToast("再按一次退出程序");
            isExit = true;
            return;
        }
        super.onBackPressed();
    }
}
