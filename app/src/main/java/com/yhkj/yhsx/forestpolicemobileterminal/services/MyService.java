package com.yhkj.yhsx.forestpolicemobileterminal.services;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.Looper;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.AlarmNoticeEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Notice;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.NoticeTimeEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.WarningEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import db.NewsDao;
import db.NoticeTimeDao;
import db.WarningDao;

/**
 * 延时通知服务
 * 
 * @author xingyimin
 * 
 */
@SuppressLint("NewApi")
public class MyService extends Service {

	private MyThread myThread;
	private List<WarningEntity> warningList;
	private List<AlarmNoticeEntity> noticeList;
	private ArrayList<Notice> notice;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		/*
		 * Bundle bundle = intent.getExtras(); userId =
		 * bundle.getString("userId");
		 */// 用于接收字符串
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		MqttService.actionStart(this);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		this.flag = false;
		super.onDestroy();
		
		MqttService.actionStart(this);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		// 用于接收字符串
		sp = this.getSharedPreferences("USER", Activity.MODE_PRIVATE);
		this.myThread = new MyThread();
		this.myThread.start();
		return super.onStartCommand(intent, flags, startId);
	}

	private boolean flag = true;
	private SharedPreferences sp;

	private class MyThread extends Thread {
		@Override
		public void run() {
			Looper.prepare();
			while (flag) {
				try {
					// 每隔15分钟获取一次数据库数据
					Thread.sleep(60000 * 15);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 获取系统的通知管理器
				manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				
				getNewList(1);
				if (notice.size() > 0) {
					notification(notice);
				}
				getNewList(2);
				if (warningList.size() > 0) {
					notification(warningList);
				}
				getNewList(3);
				if (noticeList.size() > 0) {
					notificationAlarm(noticeList);
				}

			}
			Looper.loop();
		}
	}

	private NotificationManager manager;
	private Notification notification;
	private PendingIntent pi;

	private void notification(ArrayList<Notice> no) {
		// // 获取系统的通知管理器
		// manager = (NotificationManager)
		// getSystemService(Context.NOTIFICATION_SERVICE);
		for (int i = 0; i < no.size(); i++) {
			notification = new Notification(R.drawable.ic_launcher, no.get(i)
					.getTitle(), System.currentTimeMillis());

			notification.defaults = Notification.DEFAULT_ALL; // 使用默认设置，比如铃声、震动、闪灯
			notification.flags = Notification.FLAG_AUTO_CANCEL; // 但用户点击消息后，消息自动在通知栏自动消失
			notification.flags |= Notification.FLAG_NO_CLEAR;// 点击通知栏的删除，消息不会依然不会被删除

			/*Intent intent = new Intent(getApplicationContext(),
					NoticeActivity.class);
			intent.setAction(System.currentTimeMillis() + "");
			intent.putExtra("isNotice", true);
			intent.putExtra("id", no.get(i).getNoId());
			intent.putExtra("title", no.get(i).getTitle());
			intent.putExtra("text", no.get(i).getText());
			intent.putExtra("images", no.get(i).getNoAccessory());
			pi = PendingIntent.getActivity(getApplicationContext(), i, intent,
					i);
			notification.setLatestEventInfo(getApplicationContext(), no.get(i)
					.getTitle(), no.get(i).getText(), pi);
			// 将消息推送到状态栏
			manager.notify(i, notification);*/
		}
	}

	private void notification(List<WarningEntity> warningList) {
		// 获取系统的通知管理器
		// manager = (NotificationManager)
		// getSystemService(Context.NOTIFICATION_SERVICE);
		for (int i = 0; i < warningList.size(); i++) {
			notification = new Notification(R.drawable.ic_launcher, warningList
					.get(i).getNote(), System.currentTimeMillis());

			notification.defaults = Notification.DEFAULT_ALL; // 使用默认设置，比如铃声、震动、闪灯
			notification.flags = Notification.FLAG_AUTO_CANCEL; // 但用户点击消息后，消息自动在通知栏自动消失
			notification.flags |= Notification.FLAG_NO_CLEAR;// 点击通知栏的删除，消息不会依然不会被删除

			/*Intent intent = new Intent(getApplicationContext(),
					WarningDetailActivity.class);
			intent.putExtra("isNotice", true);
			intent.putExtra("Warning", warningList.get(i));

			pi = PendingIntent.getActivity(getApplicationContext(), i, intent,
					i);
			notification.setLatestEventInfo(getApplicationContext(),
					warningList.get(i).getWarningPeople(), warningList.get(i)
							.getNote(), pi);

			// 将消息推送到状态栏
			manager.notify(i + notice.size(), notification);*/
		}
	}

	/**
	 * 接报警消息
	 * 
	 * @param alarmNotice
	 */
	private void notificationAlarm(List<AlarmNoticeEntity> alarmNotice) {
		// 获取系统的通知管理器
		// manager = (NotificationManager)
		// getSystemService(Context.NOTIFICATION_SERVICE);
		for (int i = 0; i < alarmNotice.size(); i++) {
			notification = new Notification(R.drawable.ic_launcher, alarmNotice
					.get(i).getNoticeMessage(), System.currentTimeMillis());

			notification.defaults = Notification.DEFAULT_ALL; // 使用默认设置，比如铃声、震动、闪灯
			notification.flags = Notification.FLAG_AUTO_CANCEL; // 但用户点击消息后，消息自动在通知栏自动消失
			notification.flags |= Notification.FLAG_NO_CLEAR;// 点击通知栏的删除，消息不会依然不会被删除

			/*Intent intent = new Intent(getApplicationContext(),
					ReceiveAndDisposeAlarmActivity.class);
			intent.setAction(System.currentTimeMillis() + "");
			intent.putExtra("AlarmID", alarmNotice.get(i).getAlarmID());
			intent.putExtra("NoticeID", alarmNotice.get(i).getNoticeID());

			pi = PendingIntent.getActivity(getApplicationContext(), i, intent,
					i);
			notification.setLatestEventInfo(getApplicationContext(),
					getString(R.string.app_name), alarmNotice.get(i)
							.getNoticeMessage(), pi);

			// 将消息推送到状态栏
			manager.notify(i + notice.size() + warningList.size(), notification);*/
		}
	}

	/**
	 * 查询本地通知数据库，并判断消息是否已到通知时间
	 * 
	 * @param type
	 */
	private void getNewList(int type) {
		NoticeTimeDao noticeDao = new NoticeTimeDao(getApplicationContext());
		//去本地数据库查询type类型所对应的数据集合
		List<NoticeTimeEntity> noList = noticeDao.getAllInfors(type);
		switch (type) {
		case 1:
			notice = new ArrayList<Notice>();
			for (int i = 0; i < noList.size(); i++) {
				if (ActivityUtils.getTime(noList.get(i).getNewsTime())) {
					try {
						notice.add(NewsDao.init(getApplicationContext())
								.getObjectByJSONObject(
										new JSONObject(noList.get(i)
												.getNewsContent())));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			break;
		case 2:
			warningList = new ArrayList<WarningEntity>();
			for (int i = 0; i < noList.size(); i++) {
				if (ActivityUtils.getTime(noList.get(i).getNewsTime())) {
					try {
						warningList.add(WarningDao
								.init(getApplicationContext())
								.getObjectByJSONObject(
										new JSONObject(noList.get(i)
												.getNewsContent())));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			break;
		case 3:
			noticeList = new ArrayList<AlarmNoticeEntity>();
			for (int i = 0; i < noList.size(); i++) {
				if (ActivityUtils.getTime(noList.get(i).getNewsTime())) {
					try {
						noticeList.add(AlarmNoticeEntity
								.getObjectByJSONObject(new JSONObject(noList
										.get(i).getNewsContent())));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			break;
		default:
			break;
		}
	}
}
