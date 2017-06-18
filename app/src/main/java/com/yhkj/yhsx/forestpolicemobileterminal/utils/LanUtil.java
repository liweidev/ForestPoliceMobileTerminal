package com.yhkj.yhsx.forestpolicemobileterminal.utils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.List;

/**
 * 局域网状态下
 * @author 
 *
 */
public class LanUtil {

//	private static String lanSpace = Constant.LAN + Constant.ANDROID_PORT;
	private static String lanSpace = "";
	private static String endPoint = lanSpace + "WebServiceWisdom.asmx";
	
/**
 * 插入数据  返回Boolean型
 * @param methodName
 * @param key
 * @param Json
 * @param accessory
 * @param types
 * @return
 */
	public static boolean submitData(String methodName, String key,
			String Json, String accessory, List<String> types) {
		boolean result = false;
		String soapAction = lanSpace + methodName;
		SoapObject rpc = new SoapObject(lanSpace, methodName);

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

		HttpTransportSE transport = new HttpTransportSE(endPoint, 15000);
		try {
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
	/**
	 * 获取通知内容
	 * @param methodName
	 * @param strWhere
	 * @return
	 */
	public static String getNoticeJsonString(String methodName, int strWhere) {
		String result = null;

		// String nameSpace = "http://192.168.1.125:85/";
		// 调用的方法名称
		// String endPoint = "http://192.168.1.125:85/WebServiceWisdom.asmx";
		// SOAP Action
		String soapAction = lanSpace + methodName;
		// 指定WebService的命名空间和调用的方法名
		SoapObject rpc = new SoapObject(lanSpace, methodName);

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
		try {
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
	 * 调用webService 查询 返回Json
	 * @param methodName
	 * @param key
	 * @param strWhere
	 * @return
	 */
	public static String getJSONString(String methodName,String key, String strWhere) {
		String result = null;
		if (key == null || key.equals("")) {
			key = "strwhere";
		}
		// String nameSpace = "http://192.168.1.125:85/";
		// 调用的方法名称
		// String endPoint = "http://192.168.1.125:85/WebServiceWisdom.asmx";
		// SOAP Action
		String soapAction = lanSpace + methodName;
		// 指定WebService的命名空间和调用的方法名
		SoapObject rpc = new SoapObject(lanSpace, methodName);

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
		try {
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
}
