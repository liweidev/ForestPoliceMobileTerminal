package com.yhkj.yhsx.forestpolicemobileterminal;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.services.OnPatrolRegisterService;
import com.yhkj.yhsx.forestpolicemobileterminal.services.PatrolRouteManagementService;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.CrashHandler;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ToastUtility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import bitmapcache.BitmapLruCache;

/**
 * Created by liupeng on 2017/6/7.
 */

public class MyApplication extends Application {
    private static MyApplication myApplication;


    private static final String TAG = MyApplication.class.getSimpleName();
    private BitmapLruCache mCache;
//    private HttpClient httpClient;

    private LocationClient mLocationClient;
    public GeofenceClient mGeofenceClient;
    private MyLocationListener mMyLocationListener;
    public Loaction loca;
    private LocationManager mLocationManager;
    private Location GPSLocation;

    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        //CrashHandler.getInstance().init(this);


        ActivityUtils.init(this);
        ToastUtility.init(this);
     //   ActivityUtils.
      //  ActivityUtils.get(this);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        SDKInitializer.initialize(this);

        // initLocation();

        File cacheLocation;

        // If we have external storage use it for the disk cache. Otherwise we
        // use
        // the cache dir
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            cacheLocation = new File(Environment.getExternalStorageDirectory()
                    + "/Android-BitmapCache");
        } else {
            cacheLocation = new File(getFilesDir() + "/Android-BitmapCache");
        }
        cacheLocation.mkdirs();

//        BitmapLruCache.Builder builder = new BitmapLruCache.Builder(this);
//        builder.setMemoryCacheEnabled(true)
//                .setMemoryCacheMaxSizeUsingHeapSize();
//        builder.setDiskCacheEnabled(true).setDiskCacheLocation(cacheLocation);
//
//        mCache = builder.build();

        // startLocation();

        CrashHandler.getInstance().init(this);

    }

    /**
     * // * 初始化百度定位服务 //
     */
    private void initLocation() {
        // if (mLocationClient == null) {
        mLocationClient = new LocationClient(this);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        mGeofenceClient = new GeofenceClient(this);
        loca = new Loaction();
        String bestProvider = mLocationManager.getBestProvider(getCriteria(),
                true);
        GPSLocation = mLocationManager.getLastKnownLocation(bestProvider);
        mLocationManager.requestLocationUpdates(bestProvider, 1000 * 10, 50,
                new LocationListener() {

                    @Override
                    public void onStatusChanged(String provider, int status,
                                                Bundle extras) {
                        // TODO Auto-generated method stub
                        GPSLocation = mLocationManager
                                .getLastKnownLocation(provider);
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        // TODO Auto-generated method stub
                        GPSLocation = mLocationManager
                                .getLastKnownLocation(provider);
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        // TODO Auto-generated method stub
                        GPSLocation = mLocationManager
                                .getLastKnownLocation(provider);
                    }

                    @Override
                    public void onLocationChanged(Location location) {
                        // TODO Auto-generated method stub
                        // com.baidu.mapapi.utils.CoordinateConverter converter
                        // = new CoordinateConverter();
                        // CoordinateConverter cc = converter.coord(new
                        // LatLng(location.getLatitude(),
                        // location.getAltitude()));
                        // converter.from(CoordType.GPS);
                        Log.i(TAG, "时间：" + location.getTime());
                        Log.i(TAG, "经度：" + location.getLongitude());
                        Log.i(TAG, "纬度：" + location.getLatitude());
                        Log.i(TAG, "海拔：" + location.getAltitude());
                        GPSLocation = location;
                    }
                });
    }

    /**
     * 手机gps -> baidu gps
     *
     * @param lat
     * @param lng
     * @return
     */
    private LatLng gpsToBaidu(double lat, double lng) {
        LatLng sourceLatLng = new LatLng(lat, lng);
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        // sourceLatLng待转换坐标
        converter = converter.coord(sourceLatLng);
        LatLng desLatLng = converter.convert();
        return desLatLng;
    }

    /**
     * 结束扫描定位
     */
    private void stopLocation() {
        if (mLocationClient != null) {
            if (null != mMyLocationListener) {
                mLocationClient.unRegisterLocationListener(mMyLocationListener);
            }
            mLocationClient.stop();
            mMyLocationListener = null;
            mLocationClient = null;
        }
    }

    /**
     * 关闭系统的持续定位
     */
    public void closeLocation() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(Integer.MAX_VALUE);
        boolean isRunning = false;// 路线管理
        boolean patroIsRunning = false;// 巡逻登记
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(
                    PatrolRouteManagementService.class.getName())) {
                isRunning = true;// 已经启动
                break;
            } else {
                isRunning = false;
            }
            if (serviceList.get(i).service.getClassName().toString()
                    .equals(OnPatrolRegisterService.class.getName())) {
                patroIsRunning = true;// 已经启动
                break;
            } else {
                patroIsRunning = false;
            }
        }
        if (!isRunning && !patroIsRunning) {
            stopLocation();
        }
    }

    private Criteria getCriteria() {
        Criteria criteria = new Criteria();
        // 设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        // 设置是否需要方位信息
        criteria.setBearingRequired(false);
        // 设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }

    /**
     * 打开百度定位服务，并开始定位
     */
    public void startLocation() {
        // mLocationClient = ((MyApplication) getApplication()).mLocationClient;
        if (mLocationClient == null) {
            initLocation();

            LocationClientOption option = new LocationClientOption();
            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 精度
            option.setCoorType("bd09ll");// 坐标类型 默认gcj02
            option.setOpenGps(true); // 是否打开gps进行定位
            option.SetIgnoreCacheException(true);
            int span = 1000 * 3;
            option.setScanSpan(span);// 设置的扫描间隔，单位是毫秒
            option.setIsNeedAddress(false);// 设置是否需要地址信息，默认为无地址
            option.setLocationNotify(true);
            option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            mLocationClient.setLocOption(option);
            mLocationClient.start();
        }
    }

    public static MyApplication getInstance(){
        return myApplication;
    }

    public static MyApplication getApplication(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    // // 创建HttpClient实例
    // private HttpClient createHttpClient() {
    // HttpParams params = new BasicHttpParams();
    // HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
    // HttpProtocolParams.setContentCharset(params,
    // HTTP.DEFAULT_CONTENT_CHARSET);
    // HttpProtocolParams.setUseExpectContinue(params, true);
    // HttpConnectionParams.setConnectionTimeout(params, 20 * 1000);
    // HttpConnectionParams.setSoTimeout(params, 20 * 1000);
    // HttpConnectionParams.setSocketBufferSize(params, 8192);
    // SchemeRegistry schReg = new SchemeRegistry();
    // schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(),
    // 80));
    // schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(),
    // 443));
    // ClientConnectionManager connMgr = new ThreadSafeClientConnManager(params,
    // schReg);
    // return new DefaultHttpClient(connMgr, params);
    // }

    // // 关闭连接管理器并释放资源
    // private void shutdownHttpClient() {
    // if (httpClient != null && httpClient.getConnectionManager() != null) {
    // httpClient.getConnectionManager().shutdown();
    // }
    // }

//    // 对外提供HttpClient实例
//    public HttpClient getHttpClient() {
//        return httpClient;
//    }

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // Receive Location
            // Log.i("BaiduLocationApiDem 纬度", location.getLatitude() + "");
            // Log.i("BaiduLocationApiDem 经度", location.getLongitude() + "");
            // Log.i("BaiduLocationApiDem 海拔", location.getAltitude() + "");
            // Log.i("BaiduLocationApiDem 时间", location.getTime() + "");
            if (!ActivityUtils.isGPSOpen(getApplicationContext())) {
                ActivityUtils.openGPS(getApplicationContext());
            } else {
                if (location.getLocType() == 63 || location.getLocType() == 62
                        || location.getLocType() == 68
                        || location.getLocType() > 161) {
                    if (GPSLocation != null) {
                        LatLng latlng = gpsToBaidu(GPSLocation.getLatitude(),
                                GPSLocation.getLongitude());
                        location.setAltitude(GPSLocation.getAltitude());
                        location.setLatitude(latlng.latitude);
                        location.setLongitude(latlng.longitude);
                        // location.setLatitude(GPSLocation.getLatitude());
                        // location.setLongitude(GPSLocation.getLongitude());

                        if (GPSLocation.getProvider().equals(
                                LocationManager.GPS_PROVIDER)) {
                            location.setLocType(1000);
                        } else if (GPSLocation.getProvider().equals(
                                LocationManager.NETWORK_PROVIDER)) {
                            location.setLocType(2000);
                        } else if (GPSLocation.getProvider().equals(
                                LocationManager.PASSIVE_PROVIDER)) {
                            location.setLocType(3000);
                        }
                    }
                }
                location.setTime(sdf.format(new Date()));
				/*
				 * if (TextUtils.isEmpty(location.getTime())) {
				 *
				 * }
				 */

                if (location.getLatitude() != 4.9E-324
                        && location.getLongitude() != 4.9E-324
                        && !TextUtils.isEmpty(location.getTime())
                        && (location.getLocType() == 61
                        || location.getLocType() == 62
                        || location.getLocType() == 66
                        || location.getLocType() == 161
                        || location.getLocType() == 1000
                        || location.getLocType() == 2000 || location
                        .getLocType() == 3000)) {
                    loca.setLATITUDE(location.getLatitude());
                    loca.setLONGITUDE(location.getLongitude());
                    loca.setTIME(location.getTime());
                    loca.setELEVATION(location.getAltitude());
                    loca.setLocationType(location.getLocType());
                    int flag = 0;
                    if (ActivityUtils.ISDEBUG) {
//                        flag = NetUtil.init(getApplicationContext())
//                                .getCurrentNetworkInfo(getApplicationContext());
                        ActivityUtils.print("--纬度--" + loca.getLATITUDE()
                                        + "\n" + "--经度--" + loca.getLONGITUDE() + "\n"
                                        + "--海拔--" + loca.getELEVATION() + "\n"
                                        + "--时间--" + loca.getTIME() + "\n--网络状态--"
                                        + flag + "\n--定位类型--" + loca.getLocationType()
                                        + "\n/n",
                                Environment.getExternalStorageDirectory()
                                        + "/LocationText" + ".txt");
                    }
                }
            }
        }
    }

    public BitmapLruCache getBitmapCache() {
        return mCache;
    }
}
