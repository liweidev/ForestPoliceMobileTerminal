package com.yhkj.yhsx.forestpolicemobileterminal.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.LocationClient;
import com.yhkj.yhsx.forestpolicemobileterminal.LocalLocation;
import com.yhkj.yhsx.forestpolicemobileterminal.MyApplication;
import com.yhkj.yhsx.forestpolicemobileterminal.activity.MainActivity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.LocationInfoEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import db.PatrolRegisterDao;

/**
 * 巡逻登记服务
 * */
public final class OnPatrolRegisterService extends Service implements LocalLocation.OnLocationResult {

	// private boolean flag;//判断是否要不停的获取坐标
	// private MyBinder myBinder = new MyBinder();

	// public class MyBinder extends Binder{
	// public boolean resultBoolean(){
	// return flag;//当Activity收到此值为true说明开启成功，改变Activity按钮的名称为“停止”
	// }
	// }
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private final int TIME = 3 * 1000;

	private LocalLocation mLocalLocation;
	private String GUID;

	public void onCreate() {
		super.onCreate();
		// flag = true;//第一次启动的时候开启获取坐标
		// dao = new PatrolRegisterDao(this);
		// handler = new Handler();
		// rr = new RegisterRun();

		// mLocalLocation = new LocalLocation(this, TIME);
		// mLocalLocation.setOnLocationResult(this);
		// mLocalLocation.startLocation();
		GUID = ActivityUtils.randomUUID();
		Log.d("TAG","已经启动了OnPatrolRegisterService");
	};

	private Handler handler = null;
	private RegisterRun rr = null;

	class RegisterRun implements Runnable {
		@Override
		public void run() {
			// 获取坐标并且把获取到的坐标添加到本地数据库
			saveLocation();
			// 每隔一段时间获取一次坐标位置
			handler.postDelayed(rr, TIME);
		}
	}

	public LocationClient mLocationClient;
	public MyApplication.MyLocationListener mMyLocationListener;
	/**
	 * 获取坐标并且把获取到的坐标添加到本地数据库
	 * */
	private PatrolRegisterDao dao = null;
	private Loaction loca;
	LocationInfoEntity info;
	boolean isSuccess;
	Intent intent = new Intent();
	public void saveLocation() {
		// TODO Auto-generated method stub
		// 打开百度定位
		loca= MyApplication.getApplication(this).loca;

		if (loca != null && loca.getLATITUDE() != 4.9E-324 && loca.getLONGITUDE() != 4.9E-324 && !TextUtils.isEmpty(loca.getTIME())) {
			info= new LocationInfoEntity();
			info.setElevation(loca.getELEVATION());
			info.setLatitude(loca.getLATITUDE());
			info.setLongitude(loca.getLONGITUDE());
			info.setTime(loca.getTIME());
			info.setUserId(ActivityUtils.getUseId(this));
			info.setLocationType(loca.getLocationType());
			info.setGuid(GUID);
			isSuccess= dao.insertLocationInfo(info);
			if (isSuccess) {
				intent.setAction(MainActivity.UPDATE_DATABASE);
				sendBroadcast(intent);
			}
		}

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		MyApplication.getApplication(this).startLocation();
		if (null == dao) {
			dao = new PatrolRegisterDao(this);
		}
		if (null == handler) {
			handler = new Handler();
		}
		if (null == rr) {
			rr = new RegisterRun();
		}
		handler.postDelayed(rr, TIME);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (null != handler && null != rr) {
			handler.removeCallbacks(rr);
			handler = null;
			rr = null;
		}
		if (null != mLocalLocation) {
			mLocalLocation.stopLocation();
		}
//		ForestApplication.getApplication(this).closeLocation();
	}

	@Override
	public void getLocation(Loaction loca) {
		// TODO Auto-generated method stub
		if (loca != null && !TextUtils.isEmpty(loca.getTIME())) {
			LocationInfoEntity info = new LocationInfoEntity();
			info.setElevation(loca.getELEVATION());
			info.setLatitude(loca.getLATITUDE());
			info.setLongitude(loca.getLONGITUDE());
			info.setTime(loca.getTIME());
			info.setUserId(ActivityUtils.getUseId(this));
			info.setLocationType(loca.getLocationType());
			boolean isSuccess = dao.insertLocationInfo(info);
			if (isSuccess) {
				Intent intent = new Intent();
				intent.setAction(MainActivity.UPDATE_DATABASE);
				sendBroadcast(intent);
			}
		}
	}

}
