package com.yhkj.yhsx.forestpolicemobileterminal.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;

import com.yhkj.yhsx.forestpolicemobileterminal.LocalLocation;
import com.yhkj.yhsx.forestpolicemobileterminal.MyApplication;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.LocationInfoEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import db.RouteInfoDao;

/**
 * 巡防路线记录服务
 * 
 * @author xingyimin
 * 
 */
public final class PatrolRouteManagementService extends Service  {
	// private MyThread myThread;
	public static int rate;
	public static String guid;
	public static int pauseServiceFlag = 0;
	// private SimpleDateFormat dateFormat;
	public static int FALG_START = 0X11;
	public static int FALG_PAUSE = 0X22;
	

	private LocalLocation mLocalLocation;

	private final int TIME = 60 * 1000;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		dao = new RouteInfoDao(this);
		// handler = new Handler();
		// rr = new RegisterRun();

		// mLocalLocation = new LocalLocation(this, 10*1000);
		// mLocalLocation.setOnLocationResult(this);
		// mLocalLocation.startLocation();
	}

	// public static int

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		MyApplication.getApplication(this).startLocation();
		if (null == dao) {
			dao = new RouteInfoDao(this);
		}
		if (null == handler) {
			handler = new Handler();
		}
		if (null == rr) {
			rr = new RegisterRun();
		}
//		if (null != intent) {
//			int pauseService = intent.getIntExtra("pauseService", -1);
//			if (1 == pauseService) {
//				handler.removeCallbacks(rr);
//			} else {
//				handler.postDelayed(rr, rate);
//			}
//		}else{
//			handler.postDelayed(rr, rate);
//		}
		if (FALG_START == pauseServiceFlag) {
			handler.postDelayed(rr, rate);
			//PatrolRouteManagementService.pauseServiceFlag = 1;
		} else {
			handler.removeCallbacks(rr);
		}
		
		// if (null == dao) {
		// dao = new RouteInfoDao(this);
		// }
		// if (null == mLocalLocation) {
		// if (0 == rate) {
		// rate = TIME;
		// }
		// mLocalLocation = new LocalLocation(this, rate);
		// mLocalLocation.setOnLocationResult(this);
		// mLocalLocation.startLocation();
		// }
		return super.onStartCommand(intent, flags, startId);
	}

	private RouteInfoDao dao = null;

	// @Override
	// public boolean stopService(Intent name) {
	// if (myThread != null && myThread.isAlive()) {
	// // TODO Auto-generated method stub
	// // this.flag=false;
	// this.myThread.stop();
	// }
	// return super.stopService(name);
	// }

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

	private SharedPreferences sp;
	private Editor editor;

	private Handler handler = null;
	private RegisterRun rr = null;

	class RegisterRun implements Runnable {
		@Override
		public void run() {
			// // 获取坐标并且把获取到的坐标添加到本地数据库
			saveLocation();
			// // 每隔一段时间获取一次坐标位置
			handler.postDelayed(rr, rate);
		}
	}

	// private class MyThread extends Thread {
	// @Override
	// public void run() {
	// while (flag) {
	// try {
	// Thread.sleep(rate);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	//
	// }
	// }
	// }

	private String pastTime = "";

	public void saveLocation() {
		// TODO Auto-generated method stub
		// sp = getSharedPreferences("RATE", Activity.MODE_PRIVATE);
		// editor = sp.edit();
		// String location = sp.getString("location", "");

		// Loaction lo = ((ForestApplication) getApplication()).loca;
		// dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		// lo.setTIME(dateFormat.format(Calendar.getInstance().getTime()));

		// ForestApplication.getApplication(this).startLocation();
		Loaction loca = MyApplication.getApplication(this).loca;
		if (loca != null && loca.getLATITUDE() != 4.9E-324 && loca.getLONGITUDE() != 4.9E-324 && !TextUtils.isEmpty(loca.getTIME()) && 0 != loca.getLocationType()) {
			LocationInfoEntity info = new LocationInfoEntity();
			info.setElevation(loca.getELEVATION());
			info.setLatitude(loca.getLATITUDE());
			info.setLongitude(loca.getLONGITUDE());
			info.setTime(loca.getTIME());
			info.setUserId(ActivityUtils.getUseId(this));
			info.setLocationType(loca.getLocationType());
			info.setGuid(guid);
			info.setStatus(0);
			pastTime = loca.getTIME();
			boolean isSuccess = dao.insertLocationInfo(info);
			// if (isSuccess) {
			// Intent intent = new Intent();
			// intent.setAction(com.yhkj.jskf.forestpoliceproject.MainActivity1.UPDATE_DATABASE);
			// sendBroadcast(intent);
			// }
		}

	}


	public void getLocation(Loaction loca) {
		// TODO Auto-generated method stub
		if (loca != null && !TextUtils.isEmpty(loca.getTIME()) && 0 != loca.getLocationType()) {
			LocationInfoEntity info = new LocationInfoEntity();
			info.setElevation(loca.getELEVATION());
			info.setLatitude(loca.getLATITUDE());
			info.setLongitude(loca.getLONGITUDE());
			info.setTime(loca.getTIME());
			info.setUserId(ActivityUtils.getUseId(this));
			info.setLocationType(loca.getLocationType());
			info.setGuid(guid);
			info.setStatus(0);
			pastTime = loca.getTIME();
			boolean isSuccess = dao.insertLocationInfo(info);
			// if (isSuccess) {
			// Intent intent = new Intent();
			// intent.setAction(com.yhkj.jskf.forestpoliceproject.MainActivity1.UPDATE_DATABASE);
			// sendBroadcast(intent);
			// }
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
