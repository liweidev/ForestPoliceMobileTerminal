package com.yhkj.yhsx.forestpolicemobileterminal;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;


/**
 * 公用的定位坐标类型，由于定时扫面间隔不能确定，已经废弃
 * @author wangxiaofei
 *
 */
@Deprecated
public  final  class LocalLocation {
	private static final String TAG = LocalLocation.class.getSimpleName();

	public LocationClient mLocationClient;
	public MyLocationListener mMyLocationListener;
	public Loaction loca;
	private LocationManager mLocationManager;
	private Location GPSLocation;

	private Context mContext;
	private int mScanSpan;

	/**
	 * 
	 * @param context
	 * @param scanSpan
	 *            单位 : 毫秒
	 */
	public LocalLocation(Context context, int scanSpan) {
		super();
		this.mContext = context;
		this.mScanSpan = scanSpan;

		mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	}

	/**
	 * // * 初始化百度定位服务 //
	 */
	private void initLocation() {
		// if (mLocationClient == null) {
		mLocationClient = new LocationClient(mContext);
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		// mGeofenceClient = new GeofenceClient(this);
		loca = new Loaction();
		String bestProvider = mLocationManager.getBestProvider(getCriteria(), true);
		GPSLocation = mLocationManager.getLastKnownLocation(bestProvider);
		mLocationManager.requestLocationUpdates(bestProvider, 1000 * 30, 50,
				new LocationListener() {

					@Override
					public void onStatusChanged(String provider, int status, Bundle extras) {
						// TODO Auto-generated method stub
						GPSLocation = mLocationManager.getLastKnownLocation(provider);
					}

					@Override
					public void onProviderEnabled(String provider) {
						// TODO Auto-generated method stub
						GPSLocation = mLocationManager.getLastKnownLocation(provider);
					}

					@Override
					public void onProviderDisabled(String provider) {
						// TODO Auto-generated method stub
						GPSLocation = mLocationManager.getLastKnownLocation(provider);
					}

					@Override
					public void onLocationChanged(Location location) {
						// TODO Auto-generated method stub
						Log.i(TAG, "时间：" + location.getTime());
						Log.i(TAG, "经度：" + location.getLongitude());
						Log.i(TAG, "纬度：" + location.getLatitude());
						Log.i(TAG, "海拔：" + location.getAltitude());
						GPSLocation = location;
					}
				});
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
		}
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 精度
		option.setCoorType("bd09ll");// 坐标类型 默认gcj02
		option.setOpenGps(true); // 是否打开gps进行定位
		option.SetIgnoreCacheException(true);
//		int span = 1000 * mScanSpan;
		option.setScanSpan(mScanSpan);// 设置的扫描间隔，单位是毫秒
		option.setIsNeedAddress(false);
		// 设置是否需要地址信息，默认为无地址
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	/**
	 * 结束扫描定位
	 */
	public void stopLocation() {
		if (mLocationClient != null) {
			if (null != mMyLocationListener) {
				mLocationClient.unRegisterLocationListener(mMyLocationListener);
			}
			mLocationClient.stop();
		}
	}

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

			if (ActivityUtils.ISDEBUG) {
				ActivityUtils.print(
						"--纬度--" + location.getLatitude() + "\n" + "--经度--"
								+ location.getLongitude() + "\n" + "--海拔--"
								+ location.getAltitude() + "\n" + "--时间--" + location.getTime()
								+ "\n--定位类型--" + location.getLocType() + "\n/n",
						Environment.getExternalStorageDirectory() + "/LocationText" + ".txt");
			}

			if (location.getLocType() == 63 || location.getLocType() == 62
					|| location.getLocType() > 161) {
				if (GPSLocation != null) {
					location.setAltitude(GPSLocation.getAltitude());
					location.setLatitude(GPSLocation.getLatitude());
					location.setLongitude(GPSLocation.getLongitude());
					if (GPSLocation.getProvider().equals(LocationManager.GPS_PROVIDER)) {
						location.setLocType(1000);
					} else if (GPSLocation.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
						location.setLocType(2000);
					} else if (GPSLocation.getProvider().equals(LocationManager.PASSIVE_PROVIDER)) {
						location.setLocType(3000);
					}
				}
			}

			if (location.getTime() != null
					&& !location.getTime().equals("")
					&& (location.getLocType() == 61 || location.getLocType() == 62
							|| location.getLocType() == 161 || location.getLocType() == 1000
							|| location.getLocType() == 2000 || location.getLocType() == 3000)) {
				loca.setLATITUDE(location.getLatitude());
				loca.setELEVATION(location.getAltitude());
				loca.setLONGITUDE(location.getLongitude());
				loca.setTIME(location.getTime());
				loca.setLocationType(location.getLocType());
				if (null != onLocationResult) {
					onLocationResult.getLocation(loca);
				}
			}
		}
	}

	private OnLocationResult onLocationResult;

	public void setOnLocationResult(OnLocationResult onLocationResult) {
		this.onLocationResult = onLocationResult;
	}

	public interface OnLocationResult {
		void getLocation(Loaction location);
	}

}
