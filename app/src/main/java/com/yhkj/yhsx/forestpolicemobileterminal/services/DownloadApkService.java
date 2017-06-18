package com.yhkj.yhsx.forestpolicemobileterminal.services;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.yhkj.yhsx.forestpolicemobileterminal.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;


/**
 * 应用更新 An {@link IntentService} subclass for handling asynchronous task
 * requests in a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DownloadApkService extends Service { // IntentService
	// TODO: Rename actions, choose action names that describe tasks that this
	// IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
	private static final String ACTION_FOO = "com.yhkj.jskf.supplychainmanager.services.action.FOO";
	private static final String ACTION_BAZ = "com.yhkj.jskf.supplychainmanager.services.action.BAZ";

	// TODO: Rename parameters
	private static final String EXTRA_PARAM1 = "com.yhkj.jskf.supplychainmanager.services.extra.PARAM1";
	private static final String EXTRA_PARAM2 = "com.yhkj.jskf.supplychainmanager.services.extra.PARAM2";

	// public DownloadApkService() {
	// super("DownloadApkService");
	// }

	private static Context mContext;

	/**
	 * Starts this service to perform action Foo with the given parameters. If
	 * the service is already performing a task this action will be queued.
	 * 
	 * @see IntentService
	 */
	// TODO: Customize helper method
	public static void startActionFoo(Context context, String param1,
			String param2) {
		mContext = context;
		Intent intent = new Intent(context, DownloadApkService.class);
		intent.setAction(ACTION_FOO);
		intent.putExtra(EXTRA_PARAM1, param1);
		intent.putExtra(EXTRA_PARAM2, param2);
		context.startService(intent);
	}

	/**
	 * Starts this service to perform action Baz with the given parameters. If
	 * the service is already performing a task this action will be queued.
	 * 
	 * @see IntentService
	 */
	// TODO: Customize helper method
	public static void startActionBaz(Context context, String param1,
			String param2) {
		Intent intent = new Intent(context, DownloadApkService.class);
		intent.setAction(ACTION_BAZ);
		intent.putExtra(EXTRA_PARAM1, param1);
		intent.putExtra(EXTRA_PARAM2, param2);
		context.startService(intent);
	}

	protected void onHandleIntent(Intent intent) {
		if (intent != null) {
			final String action = intent.getAction();
			if (ACTION_FOO.equals(action)) {
				final String param1 = intent.getStringExtra(EXTRA_PARAM1);
				final String param2 = intent.getStringExtra(EXTRA_PARAM2);
				handleActionFoo(param1, param2);
			} else if (ACTION_BAZ.equals(action)) {
				final String param1 = intent.getStringExtra(EXTRA_PARAM1);
				final String param2 = intent.getStringExtra(EXTRA_PARAM2);
				handleActionBaz(param1, param2);
			}
		}
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// buildNotification();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		onHandleIntent(intent);
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 比对app版本判断是否需要更新
	 */
	private void updateApp() {
		String urlString = "http://www.yinghekeji.com/upload/APP/version.html";

		/*FinalHttp finalHttp = new FinalHttp();
		finalHttp.get(urlString, new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String resultString) {
				// TODO Auto-generated method stub
				super.onSuccess(resultString);
				if (!TextUtils.isEmpty(resultString)) {
					String[] spliteString = resultString.split("@");
					String[] versionSpliteString = spliteString[0].split("=");
					String[] apkUrlSpliteString = spliteString[1].split("=");
					try {
						int latestVersionCode = Integer
								.parseInt(versionSpliteString[1]);
						PackageManager pm = getPackageManager();
						PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
						int versionCode = pi.versionCode;
						if (latestVersionCode > versionCode) {
							downloadApk(apkUrlSpliteString[1],
									versionSpliteString[1]);
						}
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(mContext, "获取远程版本号错误!!!",
								Toast.LENGTH_LONG).show();
					} catch (PackageManager.NameNotFoundException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});*/

		// 以下注释的方法是原始的方法来http
		// try {
		// URL url = new URL(urlString);
		// HttpURLConnection connection = (HttpURLConnection) url
		// .openConnection();
		// int responseCode = connection.getResponseCode();
		// if (responseCode == HttpURLConnection.HTTP_OK) {
		// InputStream is = connection.getInputStream();
		// BufferedInputStream bis = new BufferedInputStream(is);
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// byte[] bytes = new byte[1024];
		// int len = 0;
		// while (-1 != (len = bis.read(bytes))) {
		// baos.write(bytes, 0, len);
		// }
		// String resultString = new String(baos.toByteArray());
		// baos.close();
		// bis.close();
		// is.close();
		// if (!TextUtils.isEmpty(resultString)) {
		// String[] spliteString = resultString.split("@");
		// String[] versionSpliteString = spliteString[0].split("=");
		// String[] apkUrlSpliteString = spliteString[1].split("=");
		// try {
		// int latestVersionCode = Integer
		// .parseInt(versionSpliteString[1]);
		// PackageManager pm = getPackageManager();
		// PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
		// int versionCode = pi.versionCode;
		// if (latestVersionCode > 15) {
		// downloadApk(apkUrlSpliteString[1],
		// versionSpliteString[1]);
		// }
		// } catch (NumberFormatException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// Toast.makeText(this, "获取远程版本号错误!!!", Toast.LENGTH_LONG)
		// .show();
		// } catch (PackageManager.NameNotFoundException e) {
		// e.printStackTrace();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// System.out.println(resultString);
		// }
		// } catch (MalformedURLException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	/**
	 * http://res3.d.cn/android/new/game/47/74547/fkdwy_1475057882438.apk Handle
	 * action Foo in the provided background thread with the provided
	 * parameters.
	 */
	private void handleActionFoo(String param1, String param2) {
		// TODO: Handle action Foo
		// downloadApk(param1);
		updateApp();
	}

	private ProgressDialog pd = null;

	private void downloadApk(String param1, final String latestVersionCode)
			throws Exception {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			throw new Exception("sdcard not exits");
		}

		final String parentPath = Environment.getExternalStorageDirectory()
				.getPath()
				+ File.separator
				+ "forestploice"
				+ File.separator
				+ "apk" + File.separator + latestVersionCode;

		final String destApkPath = parentPath + "-forestploice.apk";

		final String downloadLog = parentPath + "-progress.log";

		final File apkFile = new File(destApkPath);
		final File logFile = new File(downloadLog);

		File parentFile = apkFile.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}

		if (!logFile.exists()) {
			logFile.createNewFile();
		}

		File[] childFiles = parentFile.listFiles();

		/**
		 * 避免重复下载
		 */
		boolean isExistLastesApk = false;// 是否已经有了最新的文件

		for (File childFile : childFiles) {
			if (childFile.getName().equals(
					latestVersionCode + "-forestploice.apk")) {
				isExistLastesApk = true;
				break;
			}
		}

		/**
		 * 断点下载
		 */
		boolean isDownloadCompleted = false;// 是否是整个文件下载完成

		if (isExistLastesApk) {
			if (logFile.exists()) {
				Reader reader = new FileReader(logFile);
				BufferedReader br = new BufferedReader(reader);
				String string = br.readLine();
				String downloadedLength = String.valueOf(apkFile.length());
				if (downloadedLength.equals(string)) {// 代表整个文件都已经下载完成了
					isDownloadCompleted = true;
				}
				br.close();
				reader.close();
			}
		}

		if (isDownloadCompleted) {
			initApp(destApkPath);
			return;
		}

		// if (isExistLastesApk) {
		// initApp(destApkPath);
		// return;
		// }

		/*FinalHttp finalHttp = new FinalHttp();
		finalHttp.download(param1, destApkPath, !isDownloadCompleted,
				new AjaxCallBack<File>() {
					@Override
					public void onLoading(long count, long current) {
						// TODO Auto-generated method stub
						super.onLoading(count, current);
						mContentView.setProgressBar(R.id.pb, 100,
								(int) (current * 1.0 / count * 100), false);
						mNotificationManager.notify(notificationid,
								mNotification);

						try {
							FileWriter writer = new FileWriter(logFile);
							writer.write(String.valueOf(count));// 记录文件的总大小
							writer.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
						buildNotification();
					}

					@Override
					public void onSuccess(File t) {
						// TODO Auto-generated method stub
						super.onSuccess(t);
						stopSelf();
						mNotificationManager.cancel(notificationid);
						initApp(t.getPath());
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						// TODO Auto-generated method stub
						super.onFailure(t, errorNo, strMsg);
						// btnFileDownload.setText("下载出错");
						if (null != pd && pd.isShowing()) {
							pd.dismiss();
						}
						mNotificationManager.cancel(notificationid);
						stopSelf();

						*//**
						 * 如果发生了下载中失败的情况就删除重新下载
						 *//*
						File file = new File(destApkPath);
						if (file.exists()) {
							boolean isRet = file.delete();
							System.out.println("delete file:"
									+ (isRet ? "success" : "failed"));
						}
					}

				});*/
	}

	private int notificationid = 0;

	private RemoteViews mContentView;

	private NotificationManager mNotificationManager;

	private Notification mNotification;

	private void buildNotification() {
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		mNotification = new Notification();
		mNotification.tickerText = "下载更新包";
		mNotification.icon = R.drawable.ic_launcher;

		mContentView = new RemoteViews(getPackageName(),
				R.layout.service_downloadapk);

		mContentView.setTextViewText(R.id.tv_title, "正在下载更新包");
		mContentView.setImageViewResource(R.id.iv_logo, R.drawable.ic_launcher);
		mContentView.setProgressBar(R.id.pb, 100, 0, false);

		mNotification.contentView = mContentView;

		mNotificationManager.notify(notificationid, mNotification);
	}

	/**
	 * 安装APP
	 * 
	 * @param apkFileUrl
	 */
	private void initApp(final String apkFileUrl) {
		if (mContext instanceof Activity
				&& !((Activity) mContext).isFinishing()) {
			new AlertDialog.Builder(mContext).setTitle("系统")
					.setMessage("您的软件需要更新，现在需要更新吗?")
					.setPositiveButton("确定", new AlertDialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(Intent.ACTION_VIEW);
							intent.addCategory(Intent.CATEGORY_DEFAULT);
							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							intent.setDataAndType(
									Uri.fromFile(new File(apkFileUrl)),
									"application/vnd.android.package-archive");
							startActivity(intent);
						}
					}).setNegativeButton("取消", null).create().show();
		}

	}

	/**
	 * Handle action Baz in the provided background thread with the provided
	 * parameters.
	 */
	private void handleActionBaz(String param1, String param2) {
		// TODO: Handle action Baz
		// throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
