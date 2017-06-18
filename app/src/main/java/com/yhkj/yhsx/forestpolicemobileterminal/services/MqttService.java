package com.yhkj.yhsx.forestpolicemobileterminal.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.provider.Settings.Secure;
import android.util.Log;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.AlarmNoticeEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Notice;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.WarningEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.receiver.ConnectionBroadcastReceiver;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDefaultFilePersistence;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.MemoryPersistence;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import db.NewsDao;
import db.WarningDao;

/**
 * 获取通知信息并发送通知
 * 
 * @author liupeng
 * 
 */
public class MqttService extends Service implements MqttCallback {
	public static final String DEBUG_TAG = "ForestService"; // Debug TAG

	private static final String MQTT_THREAD_NAME = "MqttService[" + DEBUG_TAG
			+ "]"; // Handler Thread ID

	// private static final String MQTT_BROKER = "192.168.1.41"; // Broker URL
	// or
	// IP Address
	private static final int MQTT_PORT = 10986; // Broker Port 11049 1883 11357

	public static final int MQTT_QOS_0 = 0; // QOS Level 0 ( Delivery Once no
											// confirmation )
	public static final int MQTT_QOS_1 = 1; // QOS Level 1 ( Delevery at least
											// Once with confirmation )
	public static final int MQTT_QOS_2 = 2; // QOS Level 2 ( Delivery only once
											// with confirmation with handshake
											// )

	private static final int MQTT_KEEP_ALIVE = 240000; // KeepAlive Interval in
														// 240000 60000
														// MS
	private static final String MQTT_KEEP_ALIVE_TOPIC_FORAMT = "/users/%s/keepalive"; // Topic
																						// format
																						// for
																						// KeepAlives
	private static final byte[] MQTT_KEEP_ALIVE_MESSAGE = { 0 }; // Keep Alive
																	// message
																	// to send
	private static final int MQTT_KEEP_ALIVE_QOS = MQTT_QOS_0; // Default
																// Keepalive QOS

	private static final boolean MQTT_CLEAN_SESSION = true; // Start a clean
															// session?

	private static final String MQTT_URL_FORMAT = "tcp://%s:%s"; // URL Format
																	// normally
																	// don't
																	// change

	private static final String ACTION_START = DEBUG_TAG + ".START"; // Action
																		// to
																		// start
	private static final String ACTION_STOP = DEBUG_TAG + ".STOP"; // Action to
																	// stop
	private static final String ACTION_KEEPALIVE = DEBUG_TAG + ".KEEPALIVE"; // Action
																				// to
																				// keep
																				// alive
																				// used
																				// by
																				// alarm
																				// manager
	private static final String ACTION_CHANGEUSER = DEBUG_TAG + ".CHANGEUSER";

	private static final String ACTION_RECONNECT = DEBUG_TAG + ".RECONNECT"; // Action
																				// to
																				// reconnect

	private static final String DEVICE_ID_FORMAT = "lotus_%s"; // Device ID
																// Format, add
																// any prefix
																// you'd like
																// Note: There
																// is a 23
																// character
																// limit you
																// will get
																// An NPE if you
																// go over that
																// limit
	private boolean mStarted = false; // Is the Client started?
	private String mDeviceId; // Device ID, Secure.ANDROID_ID
	private Handler mConnHandler; // Seperate Handler thread for networking

	private MqttDefaultFilePersistence mDataStore; // Defaults to FileStore
	private MemoryPersistence mMemStore; // On Fail reverts to MemoryStore
	private MqttConnectOptions mOpts; // Connection Options

	private MqttTopic mKeepAliveTopic; // Instance Variable for Keepalive topic

	private MqttClient mClient; // Mqtt Client

	private AlarmManager mAlarmManager; // Alarm manager to perform repeating
										// tasks
	private ConnectivityManager mConnectivityManager; // To check for
														// connectivity changes
	private NotificationManager mNotifMan;
	private NetworkInfo info;
	private SharedPreferences preferences, userPreferences;
	private Notification notification;
	private PendingIntent pi;

	private WarningEntity warningList;
	private AlarmNoticeEntity noticeList;
	private Notice notice;
	private String[] topics;
	private int[] qos;

	/**
	 * Start MQTT Client
	 * 
	 *
	 * @return void
	 */
	public static void actionStart(Context ctx) {
		Intent i = new Intent(ctx, MqttService.class);
		i.setAction(ACTION_START);
		ctx.startService(i);
	}

	/**
	 * Stop MQTT Client
	 * 
	 * @return void
	 */
	public static void actionStop(Context ctx) {
		Intent i = new Intent(ctx, MqttService.class);
		i.setAction(ACTION_STOP);
		ctx.startService(i);
	}

	/**
	 * Send a KeepAlive Message
	 * @return void
	 */
	public static void actionKeepalive(Context ctx) {
		Intent i = new Intent(ctx, MqttService.class);
		i.setAction(ACTION_KEEPALIVE);
		ctx.startService(i);
	}

	public static void actionChangeUser(Context ctx) {
		Intent i = new Intent(ctx, MqttService.class);
		i.setAction(ACTION_CHANGEUSER);
		ctx.startService(i);
	}

	private ConnectionBroadcastReceiver receiver;
	
	/**
	 * Initalizes the DeviceId and most instance variables Including the
	 * Connection Handler, Datastore, Alarm Manager and ConnectivityManager.
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		
		if(null == receiver){
			IntentFilter filter = new IntentFilter();
			//解锁监听
			filter.addAction(Intent.ACTION_SCREEN_OFF);
			filter.addAction(Intent.ACTION_SCREEN_ON);
			receiver = new ConnectionBroadcastReceiver();
			registerReceiver(receiver, filter);
		}
		
		Intent intent = new Intent(this,MyService.class);
		startService(intent);

		//DEVICE_ID_FORMAT = "lotus_%s" 格式化字符串类型
		mDeviceId = String.format(DEVICE_ID_FORMAT,
				Secure.getString(getContentResolver(), Secure.ANDROID_ID));
		//LogUtils.d("TAG", "设备ID："+mDeviceId);
		//MQTT_THREAD_NAME="MqttService[ + ForestService+ ]" 用意？？？
		HandlerThread thread = new HandlerThread(MQTT_THREAD_NAME);
		thread.start();

		mConnHandler = new Handler(thread.getLooper());

		try {
			mDataStore = new MqttDefaultFilePersistence(getCacheDir()
					.getAbsolutePath());
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
			mDataStore = null;
			mMemStore = new MemoryPersistence();
		}

		mOpts = new MqttConnectOptions();
		mOpts.setCleanSession(MQTT_CLEAN_SESSION);
		// Do not set keep alive interval on mOpts we keep track of it with
		// alarm's

		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		mConnectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		mNotifMan = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		preferences = getSharedPreferences("SERVER", MODE_PRIVATE);
		userPreferences = getSharedPreferences("USER", MODE_PRIVATE);
	}

	/**
	 * Service onStartCommand Handles the action passed via the Intent
	 * 
	 * @return START_REDELIVER_INTENT
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// super.onStartCommand(intent, flags, startId);
		//进程被系统重启导致intent==null
		if (null == intent) {
			start();
			return super.onStartCommand(intent, flags, startId);
		}
		//ACTION_START = DEBUG_TAG + ".START"
		String action = intent.getAction();
		
		//配置主题  ["FOREST_428","FOREST"]
		topics = new String[] {
				userPreferences.getInt("userID", 0) == 0 ? "FOREST" : "FOREST_"
						+ userPreferences.getInt("userID", 0), "FOREST" };
		//配置接收级别 1：最少传输一次
		qos = new int[] { 1, 1 };
		Log.i(DEBUG_TAG, "Received action of " + action);

		if (action == null) {
			start();
			Log.i(DEBUG_TAG,
					"Starting service with no action\n Probably from a crash");
		} else {
			if (action.equals(ACTION_START)) {
				Log.i(DEBUG_TAG, "Received ACTION_START");
				start();
			} else if (action.equals(ACTION_STOP)) {
				stop();
			} else if (action.equals(ACTION_KEEPALIVE)) {
				keepAlive();
			} else if (action.equals(ACTION_RECONNECT)) {
				if (isNetworkAvailable()) {
					reconnectIfNecessary();
				}
			} else if (action.equals(ACTION_CHANGEUSER)) {
				if (isConnected()) {
					stop();
				}
				start();
			}
		}

		return Service.START_REDELIVER_INTENT;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		// super.onDestroy();
		if (mStarted == true) {
			stop();
		}
		//actionStart(getApplicationContext());
		Intent intent = new Intent(this,MyService.class);
		startService(intent);
		
		
		
		if(null != receiver){
			unregisterReceiver(receiver);
			receiver = null;
		}
		
	}

	/**
	 * Attempts connect to the Mqtt Broker and listen for Connectivity changes
	 * via ConnectivityManager.CONNECTVITIY_ACTION BroadcastReceiver
	 * 尝试连接MQTT代理，并且监听网络变化
	 */
	private synchronized void start() {
		if (mStarted) {
			Log.i(DEBUG_TAG, "Attempt to start while already started");
			return;
		}

		//当前是否已经存活
		if (hasScheduledKeepAlives()) {
			stopKeepAlives();
		}

		connect();

		//监听网络变化
		registerReceiver(mConnectivityReceiver, new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION));
		startForeground(1, new Notification());
	}

	/**
	 * Attempts to stop the Mqtt client as well as halting all keep alive
	 * messages queued in the alarm manager
	 */
	private synchronized void stop() {
		if (!mStarted) {
			Log.i(DEBUG_TAG, "Attemtpign to stop connection that isn't running");
			return;
		}

		if (mClient != null) {
			mConnHandler.post(new Runnable() {
				@Override
				public void run() {
					try {
						mClient.disconnect();
					} catch (MqttException ex) {
						ex.printStackTrace();
					}
					mClient = null;
					mStarted = false;

					stopKeepAlives();
					Log.i(DEBUG_TAG, "mStarted------" + mStarted);
				}
			});
		}

		unregisterReceiver(mConnectivityReceiver);
	}

	/**
	 * Connects to the broker with the appropriate datastore
	 * 连接代理，并做适当的存储
	 * MQTT服务是基于TCP协议
	 */
	private synchronized void connect() {
		String url = String.format(Locale.US, MQTT_URL_FORMAT,
				preferences.getString("SERVERIP", null),
				preferences.getString("MSGPORT", "10986") /*
														 * preferences.getString(
														 * "APIPORT", "10986")
														 * preferences.
														 * getString (
														 * "MQTT_PORT" , null)
														 */);
		Log.d("TAG", "MQTT服务器地址: " + url);
		try {
			if (mDataStore != null) {
				Log.i(DEBUG_TAG, "Connecting with DataStore");
				mClient = new MqttClient(url, mDeviceId, mDataStore);
			} else {
				Log.i(DEBUG_TAG, "Connecting with MemStore");
				mClient = new MqttClient(url, mDeviceId, mMemStore);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		mConnHandler.post(new Runnable() {
			@Override
			public void run() {
				try {
					long id=Thread.currentThread().getId();
					Log.d("TAG", "MQTT连接服务器所在的线程："+id);
					
					mClient.connect(mOpts);

					mClient.subscribe(topics, qos);
					Log.d("TAG", "topics："+topics);
					Log.d("TAG", "qos："+qos);
					

					mClient.setCallback(MqttService.this);

					mStarted = true; // Service is now connected

					Log.i(DEBUG_TAG,
							"Successfully connected and subscribed starting keep alives");

					startKeepAlives();
				} catch (MqttException e) {
					e.printStackTrace();
					reconnectIfNecessary();
				}
			}
		});

	}

	/**
	 * Schedules keep alives via a PendingIntent in the Alarm Manager
	 */
	private void startKeepAlives() {
		Intent i = new Intent();
		i.setClass(this, MqttService.class);
		i.setAction(ACTION_KEEPALIVE);
		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis() + MQTT_KEEP_ALIVE, MQTT_KEEP_ALIVE,
				pi);
	}

	/**
	 * Cancels the Pending Intent in the alarm manager
	 */
	private void stopKeepAlives() {
		Intent i = new Intent();
		i.setClass(this, MqttService.class);
		i.setAction(ACTION_KEEPALIVE);
		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		mAlarmManager.cancel(pi);
	}

	/**
	 * Publishes a KeepALive to the topic in the broker
	 */
	private synchronized void keepAlive() {
		if (isConnected()) {
			try {
				sendKeepAlive();
				return;
			} catch (MqttConnectivityException ex) {
				ex.printStackTrace();
				reconnectIfNecessary();
			} catch (MqttPersistenceException ex) {
				ex.printStackTrace();
				stop();
			} catch (MqttException ex) {
				ex.printStackTrace();
				stop();
			}
		}
	}

	/**
	 * Checkes the current connectivity and reconnects if it is required.
	 */
	private synchronized void reconnectIfNecessary() {
		if (mStarted && mClient == null) {
			connect();
		}
	}

	/**
	 * Query's the NetworkInfo via ConnectivityManager to return the current
	 * connected state
	 * 
	 * @return boolean true if we are connected false otherwise
	 */
	private boolean isNetworkAvailable() {
		NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();

		return (info == null) ? false : info.isConnected();
	}

	/**
	 * Verifies the client State with our local connected state
	 * 
	 * @return true if its a match we are connected false if we aren't connected
	 */
	private boolean isConnected() {
		if (mStarted && mClient != null && !mClient.isConnected()) {
			Log.i(DEBUG_TAG,
					"Mismatch between what we think is connected and what is connected");
		}
		if (mClient != null) {
			return (mStarted && mClient.isConnected()) ? true : false;
		}
		return false;
	}

	/**
	 * Receiver that listens for connectivity chanes via ConnectivityManager
	 */
	private final BroadcastReceiver mConnectivityReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(DEBUG_TAG, "Connectivity Changed...");
			if (intent.getAction().equals(
					ConnectivityManager.CONNECTIVITY_ACTION)) {
				mConnectivityManager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				info = mConnectivityManager.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					if (isNetworkAvailable()) {
						reconnectIfNecessary();
					}
				} else {
					connectionLost(null);
				}
			}
		}
	};

	/**
	 * Sends a Keep Alive message to the specified topic
	 * 
	 * @return MqttDeliveryToken specified token you can choose to wait for
	 *         completion
	 */
	private synchronized MqttDeliveryToken sendKeepAlive()
			throws MqttConnectivityException, MqttPersistenceException,
			MqttException {
		if (!isConnected())
			throw new MqttConnectivityException();

		if (mKeepAliveTopic == null) {
			mKeepAliveTopic = mClient.getTopic(String.format(Locale.US,
					MQTT_KEEP_ALIVE_TOPIC_FORAMT, mDeviceId));
		}

		Log.i(DEBUG_TAG,
				"Sending Keepalive to "
						+ preferences.getString("SERVERIP", null));

		MqttMessage message = new MqttMessage(MQTT_KEEP_ALIVE_MESSAGE);
		message.setQos(MQTT_KEEP_ALIVE_QOS);

		return mKeepAliveTopic.publish(message);
	}

	/**
	 * Query's the AlarmManager to check if there is a keep alive currently
	 * scheduled
	 * 
	 * @return true if there is currently one scheduled false otherwise
	 */
	private synchronized boolean hasScheduledKeepAlives() {
		Intent i = new Intent();
		i.setClass(this, MqttService.class);
		i.setAction(ACTION_KEEPALIVE);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i,
				PendingIntent.FLAG_NO_CREATE);

		return (pi != null) ? true : false;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	/**
	 * Connectivity Lost from broker
	 */
	@Override
	public void connectionLost(Throwable arg0) {
		stopKeepAlives();

		mClient = null;

		if (isNetworkAvailable()) {
			reconnectIfNecessary();
		}
	}

	/**
	 * Publish Message Completion
	 */
	@Override
	public void deliveryComplete(MqttDeliveryToken arg0) {
	}

	/**
	 * Received Message from broker
	 * 从服务器接收消息
	 */
	@Override
	public void messageArrived(MqttTopic topic, MqttMessage message)
			throws Exception {
		Log.i(DEBUG_TAG,
				"  Topic:\t" + topic.getName() + "  Message:\t"
						+ new String(message.getPayload()) + "  QoS:\t"
						+ message.getQos());
		// Toast.makeText(this, topic.getName(), Toast.LENGTH_LONG).show();
		String test = new String(message.getPayload());
		if (ActivityUtils.ISDEBUG) {
			System.out.println("test-----------" + test);
		}
		try {
			// 获取系统的通知管理器
			JSONObject result = new JSONObject(test);

			if (result.getInt("Error") == 0) {
				if (!result.isNull("NoticeList")
						&& !result.getString("NoticeList").equals("")) {
					notice = NewsDao.init(getApplicationContext())
							.getObjectByJSONObject(
									result.getJSONObject("NoticeList"));
					if (notice != null) {
						// getNewList(1);
						// if (notice.size() > 0) {
						notification(notice, result.getString("NoticeList")
								.toString());
						// }

						sendSureBackPackage(notice.getNoId());

					}
				}
				if (!result.isNull("AlarmList")
						&& !result.getString("AlarmList").equals("")) {
					warningList = WarningDao.init(getApplicationContext())
							.getObjectByJSONObject(
									result.getJSONObject("AlarmList"));
					if (warningList != null) {

						notification(warningList, result.getString("AlarmList")
								.toString());
					}
				}
				if (!result.isNull("AlermInfoList")
						&& !result.getString("AlermInfoList").equals("")) {
					noticeList = AlarmNoticeEntity.getObjectByJSONObject(result
							.getJSONObject("AlermInfoList"));
					if (noticeList != null) {

						// getNewList(3);
						// if (noticeList.size() > 0) {
						notificationAlarm(noticeList,
								result.getString("AlermInfoList").toString());
						// }
					}
				}

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
	}

	private void sendSureBackPackage(int noticeID) {
		// TODO Auto-generated method stub
		String urlString = ActivityUtils.getServerApi(this) + "WebApi/api/"	+ "Notice/InsertNoticeRecord";
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(10 * 1000);
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			String params = "NoticeID="+noticeID+"&UserID="+userPreferences.getInt("userID", 0);
			byte[] data = params.getBytes();
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 请求头, 必须设置
			connection.setRequestProperty("Content-Length", data.length + "");// 注意是字节长度, 不是字符长度
			connection.getOutputStream().write(data);
			int responseCode = connection.getResponseCode();
			System.out.println("responseCode = " + responseCode);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		AjaxParams params = new AjaxParams();
//		params.put("NoticeID", noticeID + "");
//		params.put("UserID", userPreferences.getInt("userID", 0) + "");
//		NetworkConnections.init(this).callNetworkInterfaceByPost(this,
//				"Notice/InsertNoticeRecord", params,
//				new MyAjaxCallback("Notice/InsertNoticeRecord", null) {
//					@Override
//					public void onSuccess(String t) {
//						if (ActivityUtils.ISDEBUG) {
//							System.out.println("--------------返回值：" + t);
//						}
//					}
//
//					@Override
//					public void onFailure(Throwable t, int errorNo,
//							String strMsg) {
//						// TODO Auto-generated method stub
//						if (ActivityUtils.ISDEBUG) {
//							System.out.println("error--------------返回值："
//									+ strMsg);
//						}
//					}
//
//				});
	}

	/**
	 * MqttConnectivityException Exception class
	 */
	private class MqttConnectivityException extends Exception {
		private static final long serialVersionUID = -7385866796799469420L;
	}

	private void notification(Notice no, String json) {
		// // 获取系统的通知管理器
		// manager = (NotificationManager)
		// getSystemService(Context.NOTIFICATION_SERVICE);
		// for (int i = 0; i < no.size(); i++) {
		notification = new Notification(R.drawable.ic_launcher, no.getTitle(),
				System.currentTimeMillis());

		notification.defaults = Notification.DEFAULT_ALL; // 使用默认设置，比如铃声、震动、闪灯
		notification.flags = Notification.FLAG_AUTO_CANCEL; // 但用户点击消息后，消息自动在通知栏自动消失
		notification.flags |= Notification.FLAG_NO_CLEAR;// 点击通知栏的删除，消息不会依然不会被删除

		/*Intent intent = new Intent(getApplicationContext(),
				NoticeActivity.class);
		intent.setAction(System.currentTimeMillis() + "");
		intent.putExtra("isNotice", true);
		intent.putExtra("id", no.getNoId());
		intent.putExtra("title", no.getTitle());
		intent.putExtra("text", no.getText());
		intent.putExtra("images", no.getNoAccessory());
		intent.putExtra("json", json);
*/
		/*pi = PendingIntent.getActivity(getApplicationContext(), no.getNoId(),
				intent, no.getNoId());
		notification.setLatestEventInfo(getApplicationContext(), no.getTitle(),
				no.getText(), pi);
		// 将消息推送到状态栏
		mNotifMan.notify(no.getNoId(), notification);*/
		// }
	}

	private void notification(WarningEntity warningList, String json) {
		// 获取系统的通知管理器
		// manager = (NotificationManager)
		// getSystemService(Context.NOTIFICATION_SERVICE);
		// for (int i = 0; i < warningList.size(); i++) {
		notification = new Notification(R.drawable.ic_launcher,
				warningList.getNote(), System.currentTimeMillis());

		notification.defaults = Notification.DEFAULT_ALL; // 使用默认设置，比如铃声、震动、闪灯
		notification.flags = Notification.FLAG_AUTO_CANCEL; // 但用户点击消息后，消息自动在通知栏自动消失
		notification.flags |= Notification.FLAG_NO_CLEAR;// 点击通知栏的删除，消息不会依然不会被删除
/*
		Intent intent = new Intent(getApplicationContext(),
				WarningDetailActivity.class);
		intent.putExtra("isNotice", true);
		intent.putExtra("Warning", warningList);
		intent.putExtra("json", json);
		pi = PendingIntent.getActivity(getApplicationContext(),
				warningList.getNoteId(), intent, warningList.getId());
		notification.setLatestEventInfo(getApplicationContext(),
				warningList.getWarningPeople(), warningList.getNote(), pi);

		// 将消息推送到状态栏
		mNotifMan.notify(warningList.getId(), notification);*/
		// }
	}

	/**
	 * 接报警消息
	 * 
	 * @param alarmNotice
	 */
	private void notificationAlarm(AlarmNoticeEntity alarmNotice, String json) {
		// 获取系统的通知管理器
		// manager = (NotificationManager)
		// getSystemService(Context.NOTIFICATION_SERVICE);
		// for (int i = 0; i < alarmNotice.size(); i++) {
		notification = new Notification(R.drawable.ic_launcher,
				alarmNotice.getNoticeMessage(), System.currentTimeMillis());

		notification.defaults = Notification.DEFAULT_ALL; // 使用默认设置，比如铃声、震动、闪灯
		notification.flags = Notification.FLAG_AUTO_CANCEL; // 但用户点击消息后，消息自动在通知栏自动消失
		notification.flags |= Notification.FLAG_NO_CLEAR;// 点击通知栏的删除，消息不会依然不会被删除

	/*	Intent intent = new Intent(getApplicationContext(),
				ReceiveAndDisposeAlarmActivity.class);
		intent.setAction(System.currentTimeMillis() + "");
		intent.putExtra("AlarmID", alarmNotice.getAlarmID());
		intent.putExtra("NoticeID", alarmNotice.getNoticeID());
		intent.putExtra("json", json);
		pi = PendingIntent.getActivity(getApplicationContext(),
				alarmNotice.getAlarmID(), intent, alarmNotice.getAlarmID());
		notification.setLatestEventInfo(getApplicationContext(),
				getString(R.string.app_name), alarmNotice.getNoticeMessage(),
				pi);

		// 将消息推送到状态栏
		mNotifMan.notify(alarmNotice.getAlarmID(), notification);*/
		// }
	}

	// private void getNewList(int type) {
	// NoticeTimeDao noticeDao = new NoticeTimeDao(getApplicationContext());
	// List<NoticeTimeEntity> noList = noticeDao.getAllInfors(type);
	// switch (type) {
	// case 1:
	// List<Notice> list = new ArrayList<Notice>();
	// for (int i = 0; i < noList.size(); i++) {
	// int len = 0;
	// for (int j = 0; j < notice.size(); j++) {
	//
	// if (noList.get(i).getNewsId() == notice.get(j).getNoId()
	// && ActivityUtils.getTime(noList.get(i)
	// .getNewsTime())) {
	// list.add(notice.get(j));
	// } else {
	// len++;
	// }
	// }
	// if (len == notice.size()) {
	// noticeDao.delRawById(noList.get(i).getId() + "");
	// }
	// }
	// for (int i = 0; i < list.size(); i++) {
	// for (int j = notice.size() - 1; j >= 0; j--) {
	// if (notice.get(j).getNoId() == list.get(i).getNoId()) {
	// notice.remove(j);
	// }
	// }
	// }
	// break;
	// case 2:
	// List<WarningEntity> warnlist = new ArrayList<WarningEntity>();
	// for (int i = 0; i < noList.size(); i++) {
	// int len = 0;
	// for (int j = 0; j < warningList.size(); j++) {
	// if (noList.get(i).getNewsId() == warningList.get(j)
	// .getNoteId()
	// && ActivityUtils.getTime(noList.get(i)
	// .getNewsTime())) {
	// warnlist.add(warningList.get(j));
	// } else {
	// len++;
	// }
	// }
	// if (len == warningList.size()) {
	// noticeDao.delRawById(noList.get(i).getId() + "");
	// }
	// }
	// for (int i = 0; i < warnlist.size(); i++) {
	// for (int j = warningList.size() - 1; j >= 0; j--) {
	// if (warningList.get(j).getNoteId() == warnlist.get(i)
	// .getNoteId()) {
	// warningList.remove(j);
	// }
	// }
	// }
	// break;
	// case 3:
	// List<AlarmNoticeEntity> alarmList = new ArrayList<AlarmNoticeEntity>();
	// for (int i = 0; i < noList.size(); i++) {
	// int len = 0;
	// for (int j = 0; j < noticeList.size(); j++) {
	// if (noList.get(i).getNewsId() == noticeList.get(j)
	// .getNoticeID()
	// && ActivityUtils.getTime(noList.get(i)
	// .getNewsTime())) {
	// alarmList.add(noticeList.get(j));
	// } else {
	// len++;
	// }
	// }
	// if (len == noticeList.size()) {
	// noticeDao.delRawById(noList.get(i).getId() + "");
	// }
	// }
	// for (int i = 0; i < alarmList.size(); i++) {
	// for (int j = noticeList.size() - 1; j >= 0; j--) {
	// if (noticeList.get(j).getNoticeID() == alarmList.get(i)
	// .getNoticeID()) {
	// noticeList.remove(j);
	// }
	// }
	// }
	// break;
	// default:
	// break;
	// }
	// }
}
