package com.soft.tuding.pinchedriver.util;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.soft.tuding.pinchedriver.R;

/**
 * Created by server on 2017/11/7.
 */
public class AmapLocationUtils {
    private static AmapLocationUtils amapLocationUtils = null;
    private LocateCallBack mCallBack;

    private AmapLocationUtils() {
    }

    private AmapLocationUtils(Context context) {
        initLocation(context);
    }

    //同步代码快的demo加锁，安全高效
    public static AmapLocationUtils getInstacne(Context context) {
        if (amapLocationUtils == null)
            synchronized (AmapLocationUtils.class) {
                if (amapLocationUtils == null)
                    amapLocationUtils = new AmapLocationUtils(context);
            }
        return amapLocationUtils;

    }

    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    public void initLocation(Context context) {
        mlocationClient = new AMapLocationClient(context);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(5000);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
        //如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会。
        mLocationOption.setOnceLocationLatest(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        //设置定位监听
        mlocationClient.setLocationListener(new MyLocationlistener());
    }

    //设置定位小蓝点
    public void setDefaultLocationImg(AMap aMap) {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location));// 设置小蓝点的图标
        myLocationStyle.radiusFillColor(Color.argb(50, 89, 250, 252));// 设置圆形的填充颜色
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        // (MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

    }

    //启动定位
    public void startLocation() {
        if (mlocationClient != null) {
            mlocationClient.startLocation();
        }

    }

    public void stopLocation() {
        if (mlocationClient != null) {
            mlocationClient.stopLocation();

        }

    }

    //
    public void setMapCenter(AMap mAmap, Context context, double latitude, double longitude) {
        // 设置当前地图显示为当前位置
        mAmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 17));
        //实例化地理围栏客户端
    }



    public void setLocationMark(AMap mAmap, Context context, double latitude, double longitude) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(latitude, longitude));
        markerOptions.visible(true);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(context.getResources(), R.mipmap.ic_location));
        markerOptions.icon(bitmapDescriptor);
        mAmap.addMarker(markerOptions);

    }



//    public void addMark(final Context context, final AMap aMap, final List<CarPosition> list) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < list.size(); i++) {
//                    CarPosition data = list.get(i);
//                    LatLng latLng1 = new LatLng(data.currLatitude, data.currLongitude);
//                    Bitmap bitmap = BitmapFactory.decodeResource(
//                            context.getResources(), R.mipmap.ic_car);
//                    if (bitmap != null) {
//                        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap
//                                (BitmapUtil.compass(bitmap));
//                        //临时mark,缩放图片
//                        aMap.addMarker(new MarkerOptions().position(latLng1)
//                                .icon(bitmapDescriptor));
//                    }
//                }
//            }
//        }).start();
//
//    }

    public void setLocateCallback(LocateCallBack callBack) {
        this.mCallBack = callBack;
    }

    public interface LocateCallBack {
        void onLocateSuccess(AMapLocation amapLocation);
        void onLocateFail(String error);
    }

    public class MyLocationlistener implements AMapLocationListener {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null && mCallBack != null) {
                Double latitude = aMapLocation.getLatitude();
                Double longitude = aMapLocation.getLongitude();
                Log.i("ha", latitude + "," + longitude);
                mCallBack.onLocateSuccess(aMapLocation);
            } else {
                mCallBack.onLocateFail(aMapLocation.getErrorInfo());

            }
        }
    }

}