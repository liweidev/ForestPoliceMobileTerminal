package com.yhkj.yhsx.forestpolicemobileterminal.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.LocationInfoEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.NetUtils;

import org.json.JSONException;

import java.util.List;

import db.PatrolRegisterDao;
import db.RouteInfoDao;

/*
 * 定时上传巡逻和路线数据
 */
public final class Upload30DatasService extends Service {

	private Context ct;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		ct = this;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private Handler handler = null;
	private RegisterRun rr = null;

	private int runtimeForUpload = UPMODE_WORK;
	
	private static final int UPMODE_WORK = 60 * 1000;//上传模式
	private static final int UPMODE_REST = 10 * 60 * 1000;//休闲模式

	class RegisterRun implements Runnable {
		@Override
		public void run() {
			// 获取坐标并且把获取到的坐标添加到本地数据库
			submitLocationBefore30Datas();
			// 每隔一段时间获取一次坐标位置
			handler.postDelayed(rr, runtimeForUpload);//
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		// submitLocationBefore30Datas();
		if (null == ct) {
			ct = this;
		}
		if (null == handler) {
			handler = new Handler();
		}
		if (null == rr) {
			rr = new RegisterRun();
		}
		mRouteInfoDao = new RouteInfoDao(ct);
		mPatrolRegisterDao = new PatrolRegisterDao(ct);
		handler.postDelayed(rr, runtimeForUpload);
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 提交数据库前30条定位坐标
	 * 
	 * @param
	 */
	private synchronized void submitLocationBefore30Datas() {
		// 如果有网的情况。。。。。。
		if (NetUtils.getInstance().isNetworkAvalible(ct)) {
			scanData();
		}else{
			runtimeForUpload = UPMODE_REST;//开启休闲模式
		}
	}
	private int mRouteInfoDaoCount,mPatrolRegisterDaoCount;
	private void scanData() {
		// TODO Auto-generated method stub
		mRouteInfoDaoCount = mRouteInfoDao.getCount();
		mPatrolRegisterDaoCount = mPatrolRegisterDao.getCount();
		int totalUserfulCount = mRouteInfoDaoCount + mPatrolRegisterDaoCount;
		if (0 != totalUserfulCount) {
			runtimeForUpload = UPMODE_WORK;//开启上传模式
			uploadPatrolRegister();
			uploadRouteLine();
		}else{
			runtimeForUpload = UPMODE_REST;//开启休闲模式
		}
	}

	private RouteInfoDao mRouteInfoDao;
	private PatrolRegisterDao mPatrolRegisterDao;

	/**
	 * 上传路线管理的路线
	 */
	private void uploadRouteLine() {
		// TODO Auto-generated method stub
		if (0 == mRouteInfoDaoCount) {
			return;
		}
		List<LocationInfoEntity> leList = mRouteInfoDao.getBefore30Datas();
		String str = null;
		if (leList != null && leList.size() > 0) {
			try {
				str = LocationInfoEntity.getJsonForRouteLine(leList).toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/*AjaxParams params = new AjaxParams();
		params.put("jsonGisInfo_Add", str);
		System.out.println("路线---------》"+params.toString());*/
//		NetworkConnections.init(ct).callNetworkInterfaceByPost(ct,
//				"Catalog_Line/GisCatalog_Line_Add", params,
//				new MyAjaxCallback("Catalog_Line/GisCatalog_Line_Add", null) { // CoordinateInfo/GisInfo_Add
//					@Override
//					public void onSuccess(String t) {
//						if (ActivityUtils.ISDEBUG) {
//							System.out.println("CoordinateInfo/GisInfo_Add:-----" + t);
//						}
//						try {
//							if (new JSONObject(t).getInt("Error") == 0) {
//								// 删除表中的所有坐标点
//								RouteInfoDao dao = new RouteInfoDao(ct);
//								dao.updateBefore30Datas();
//								// dao.delBefore30Datas();
//
//								Intent intent = new Intent();
//								intent.setAction(com.yhkj.jskf.forestpoliceproject.MainActivity1.UPDATE_DATABASE);
//								sendBroadcast(intent);
//							}
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//					}
//				});
	}

	/**
	 * 上传巡逻
	 */
	private void uploadPatrolRegister() {
		if (0 == mPatrolRegisterDaoCount) {
			return;
		}
		List<LocationInfoEntity> leList = mPatrolRegisterDao.getBefore30Datas();
		String str = null;
		if (leList != null && leList.size() > 0) {
			try {
				str = LocationInfoEntity.getJsonForLocationInfoList(leList).toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/*AjaxParams params = new AjaxParams();
		params.put("jsonGisInfo_Add", str);*/
//		NetworkConnections.init(ct).callNetworkInterfaceByPost(ct, "CoordinateInfo/GisInfo_Add",
//				params, new MyAjaxCallback("CoordinateInfo/GisInfo_Add", null) {
//					@Override
//					public void onSuccess(String t) {
//						if (ActivityUtils.ISDEBUG) {
//							System.out.println("CoordinateInfo/GisInfo_Add:-----" + t);
//						}
//						try {
//							if (new JSONObject(t).getInt("Error") == 0) {
//								// 删除表中的所有坐标点
//								PatrolRegisterDao dao = new PatrolRegisterDao(ct);
//								dao.delBefore30Datas();
//
//								Intent intent = new Intent();
//								intent.setAction(com.yhkj.jskf.forestpoliceproject.MainActivity1.UPDATE_DATABASE);
//								sendBroadcast(intent);
//							}
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//					}
//				});
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (null != handler && null != rr) {
			handler.removeCallbacks(rr);
		}
	}
}
