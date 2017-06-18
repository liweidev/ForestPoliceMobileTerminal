package com.yhkj.yhsx.forestpolicemobileterminal.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.yhkj.yhsx.forestpolicemobileterminal.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;

/**
 * 判断网络状态和类型
 * 
 * @description
 * @author Tom
 * @update 2013-12-13 下午4:22:15
 */
public class NetUtil {

	private static NetUtil connection;
	private Context context;
	public static String nameSpace ="";
	public static String endPoint = "";

	private NetUtil(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		nameSpace = ActivityUtils.getServerApi(context);
		endPoint = nameSpace + "WebServiceWisdom.asmx";
	}
	
	public static NetUtil init(Context context){
		if (connection == null) {
			connection = new NetUtil(context);
		}else{
			nameSpace = ActivityUtils.getServerApi(context);
			endPoint = nameSpace + "WebServiceWisdom.asmx";
		}
		return connection;
	}
	
	/**
	 * 判断网络情况
	 * 
	 * @param context
	 *            上下文
	 * @return false 表示没有网络 true 表示有网络
	 */
	public boolean isNetworkAvalible(Context context) {
		boolean isOk = false;

		// 获得网络状态管理器
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager == null) {
			isOk = false;
		} else {
			// 建立网络数组
			NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();
			if (net_info != null) {
				for (int i = 0; i < net_info.length; i++) {
					// 判断获得的网络状态是否是处于连接状态
					if (net_info[i].getState() == State.CONNECTED) {
						isOk = true;
					}
				}
			}
		}
		if (!isOk) {
			Toast.makeText(context, R.string.net_dialog_title, Toast.LENGTH_SHORT)
					.show();
		}
		return isOk;
	}

	public static boolean isNetworkAvalibleService(Context context) {
		boolean isOk = false;

		// 获得网络状态管理器
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager == null) {
			isOk = false;
		} else {
			// 建立网络数组
			NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();
			if (net_info != null) {
				for (int i = 0; i < net_info.length; i++) {
					// 判断获得的网络状态是否是处于连接状态
					if (net_info[i].getState() == State.CONNECTED) {
						isOk = true;
					}
				}
			}
		}
		if (!isOk) {
		}
		return isOk;
	}

	/**
	 * 如果没有网络，则弹出网络设置对话框
	 * 
	 * @param activity
	 * @description
	 * @author Tom
	 */
	public void checkNetwork(final Activity activity) {
		if (!isNetworkAvalible(activity)) {
			TextView msg = new TextView(activity);
			msg.setText(R.string.net_dialog_title);
			new AlertDialog.Builder(activity)
					.setIcon(R.drawable.ic_launcher)
					.setTitle(R.string.net_dialog_content)
					.setView(msg)
					.setPositiveButton(R.string.net_dialog_yes,
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int whichButton) {
									// 跳转到设置界面
									activity.startActivityForResult(new Intent(
											Settings.ACTION_WIRELESS_SETTINGS),
											0);
								}
							}).create().show();
		}
		return;
	}

	/**
	 * 判断网络是否连接
	 **/
	public boolean netState(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// 获取代表联网状态的NetWorkInfo对象
		NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
		// 获取当前的网络连接是否可用
		boolean available = false;
		try {
			if (null == networkInfo) {
				available = false;
				return false;
			}
			available = networkInfo.isAvailable();
		} catch (Exception e) {
			e.printStackTrace();
			available = false;
		}
		if (available) {
			Log.i("通知", "当前的网络连接可用");
			return true;
		} else {
			Log.i("通知", "当前的网络连接不可用");
//			if (!NetUtil.isNetworkAvalibleService(context)) {
////				Toast.makeText(context,
////						"您的网络已经断开，请检查网络!", Toast.LENGTH_LONG)
////						.show();
//				
//				Intent intent = new Intent(Settings.ACTION_SETTINGS);// ACTION_SETTINGS 
//				context.startActivity(intent);
//			}
			return false;
		}
	}

	/**
	 * 在连接到网络基础之上,判断设备是否是SIM网络连接
	 * 
	 * @param context
	 * @return
	 */
	public boolean IsMobileNetConnect(Context context) {
		try {
			ConnectivityManager connManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			State state = connManager.getNetworkInfo(
					ConnectivityManager.TYPE_MOBILE).getState();
			if (State.CONNECTED == state)
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 判断现在所处网络的状态
	 * 
	 * @param context
	 * @return int 0为无连接，1为3g/4g连接，2为wifi连接
	 */
	public int getCurrentNetworkInfo(Context context) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		NetworkInfo wimax = null;
		try {
			wimax = connectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		if (networkInfo != null
				&& networkInfo.getState() == State.CONNECTED) {
			if (networkInfo.getTypeName().contains("WIFI")) {// wifi
				return 2;
			} else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE
					|| (wimax != null && wimax.isConnected())) {
				return 1;
			}
		}
		return 0;
	}

	/*
	 * 调用webService 查询 返回Json
	 */
	public String getJSONString(String methodName, String key,
			String strWhere) {
		String result = null;
		if (key == null || key.equals("")) {
			key = "strwhere";
		}
		// String nameSpace = "http://192.168.1.125:85/";
		// 调用的方法名称
		// String endPoint = "http://192.168.1.125:85/WebServiceWisdom.asmx";
		// SOAP Action
		try {
			String soapAction = nameSpace + methodName;
			// 指定WebService的命名空间和调用的方法名
			SoapObject rpc = new SoapObject(nameSpace, methodName);
	
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER10);
			if (strWhere != null && !strWhere.equals("")) {
				rpc.addProperty(key, strWhere);
				envelope.dotNet = false;
			} else {
				envelope.dotNet = true;
			}
			envelope.bodyOut = rpc;
			// 设置是否调用的是dotNet开发的WebService
	
			// 等价于envelope.bodyOut = rpc;
			envelope.setOutputSoapObject(rpc);
	
			HttpTransportSE transport = new HttpTransportSE(endPoint);
			// 调用WebService
			transport.call(soapAction, envelope);
			// 获取返回的数据
			SoapObject object = (SoapObject) envelope.bodyIn;
			// 获取返回的结果
			result = object.getProperty(0).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getUpdateAppUrl(String methodName, List<String> keys,
			List<String> values) {
		String result = null;
		// String nameSpace = "http://192.168.1.125:85/";
		// 调用的方法名称
		// String endPoint = "http://192.168.1.125:85/WebServiceWisdom.asmx";
		// SOAP Action
		try {
			String soapAction = nameSpace + methodName;
			// 指定WebService的命名空间和调用的方法名
			SoapObject rpc = new SoapObject(nameSpace, methodName);
	
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER10);
			for (int i = 0; i < keys.size(); i++) {
				rpc.addProperty(keys.get(i), values.get(i));
				envelope.dotNet = false;
			}
			envelope.bodyOut = rpc;
			// 设置是否调用的是dotNet开发的WebService
	
			// 等价于envelope.bodyOut = rpc;
			envelope.setOutputSoapObject(rpc);
	
			HttpTransportSE transport = new HttpTransportSE(endPoint);
			// 调用WebService
			transport.call(soapAction, envelope);
			// 获取返回的数据
			SoapObject object = (SoapObject) envelope.bodyIn;
			// 获取返回的结果
			result = object.getProperty(0).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * 插入数据 返回boolean
	 */
	public boolean submitData(String methodName, String key,
			String Json, String accessory, List<String> types) {
		boolean result = false;

		// String nameSpace = "http://192.168.1.125:85/";
		// 调用的方法名称
		// String endPoint = "http://192.168.1.125:85/WebServiceWisdom.asmx";
		// SOAP Action
		try {
		String soapAction = nameSpace + methodName;
		// 指定WebService的命名空间和调用的方法名
		SoapObject rpc = new SoapObject(nameSpace, methodName);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER10);
		if (Json != null && !Json.equals("")) {
			rpc.addProperty(key, Json);
			envelope.dotNet = false;
		}
		// if (accessory != null && accessory.size() > 0) {
		rpc.addProperty("flist", accessory);
		envelope.dotNet = false;
		rpc.addProperty("fileType", 1);
		// }
		envelope.bodyOut = rpc;
		// 设置是否调用的是dotNet开发的WebService

		// 等价于envelope.bodyOut = rpc;
		envelope.setOutputSoapObject(rpc);

		HttpTransportSE transport = new HttpTransportSE(endPoint, 30000);
			// 调用WebService
			transport.call(soapAction, envelope);
			// 获取返回的数据
			SoapObject object = (SoapObject) envelope.bodyIn;
			// 获取返回的结果
			String index = object.getProperty(0).toString();

			result = index.toUpperCase().equals("TRUE") ? true : false;

		} catch (Exception e) {
			e.printStackTrace();

		}
		return result;
	}

	/*
	 * 获取通知内容
	 */
	public String getNoticeJsonString(String methodName, int strWhere) {
		String result = null;

		// String nameSpace = "http://192.168.1.125:85/";
		// 调用的方法名称
		// String endPoint = "http://192.168.1.125:85/WebServiceWisdom.asmx";
		// SOAP Action
		try {
		String soapAction = nameSpace + methodName;
		// 指定WebService的命名空间和调用的方法名
		SoapObject rpc = new SoapObject(nameSpace, methodName);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER10);
		if (strWhere > 0) {
			rpc.addProperty("strUserID", strWhere);
			envelope.dotNet = false;
		} else {
			envelope.dotNet = true;
		}
		envelope.bodyOut = rpc;
		// 设置是否调用的是dotNet开发的WebService

		// 等价于envelope.bodyOut = rpc;
		envelope.setOutputSoapObject(rpc);

		HttpTransportSE transport = new HttpTransportSE(endPoint);
			// 调用WebService
			transport.call(soapAction, envelope);
			// 获取返回的数据
			SoapObject object = (SoapObject) envelope.bodyIn;
			// 获取返回的结果
			result = object.getProperty(0).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 更改通知状态
	 * 
	 * @param methodName
	 * @param nidkey
	 * @param nid
	 * @param uidkey
	 * @param uid
	 * @return
	 */
	public boolean editNotice(String methodName, String nidkey, int nid,
			String uidkey, int uid) {
		boolean result = false;

		// String nameSpace = "http://192.168.1.125:85/";
		// 调用的方法名称
		// String endPoint = "http://192.168.1.125:85/WebServiceWisdom.asmx";
		// SOAP Action
		try {
		String soapAction = nameSpace + methodName;
		// 指定WebService的命名空间和调用的方法名
		SoapObject rpc = new SoapObject(nameSpace, methodName);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER10);
		if (nid > 0 && uid > 0) {
			rpc.addProperty(nidkey, nid);
			rpc.addProperty(uidkey, uid);
			envelope.dotNet = false;
		}
		envelope.dotNet = false;
		envelope.bodyOut = rpc;
		// 设置是否调用的是dotNet开发的WebService

		// 等价于envelope.bodyOut = rpc;
		envelope.setOutputSoapObject(rpc);

		HttpTransportSE transport = new HttpTransportSE(endPoint, 15000);
			// 调用WebService
			transport.call(soapAction, envelope);
			// 获取返回的数据
			SoapObject object = (SoapObject) envelope.bodyIn;
			// 获取返回的结果
			String index = object.getProperty(0).toString();

			result = index.toUpperCase().equals("TRUE") ? true : false;

		} catch (Exception e) {
			e.printStackTrace();

		}
		return result;
	}

	/*
	 * 登陆 返回用户JSON字符串
	 */
	public String Login(String userName, String password) {
		String result = null;

		// String nameSpace = "http://192.168.1.125:85/";
		// 调用的方法名称
		String methodName = "Get_users";
		// String endPoint = "http://192.168.1.125:85/WebServiceWisdom.asmx";
		// SOAP Action
		try {
		String soapAction = nameSpace + methodName;
		// 指定WebService的命名空间和调用的方法名
		SoapObject rpc = new SoapObject(nameSpace, methodName);

		rpc.addProperty("account", userName);
		rpc.addProperty("password ", password);

		// 设置需调用WebService接口需要传入的两个参数mobileCode、userId

		// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER10);

		envelope.bodyOut = rpc;
		// 设置是否调用的是dotNet开发的WebService
		envelope.dotNet = false;
		// 等价于envelope.bodyOut = rpc;
		envelope.setOutputSoapObject(rpc);

		HttpTransportSE transport = new HttpTransportSE(endPoint);
			// 调用WebService
			transport.call(soapAction, envelope);
			// 获取返回的数据
			SoapObject object = (SoapObject) envelope.bodyIn;
			// 获取返回的结果
			result = object.getProperty(0).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int getVersionCodeByFtpFile(){
		int versionCode = 0;
		
		return versionCode;
	}

}
