package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import android.app.Activity;

import com.yhkj.yhsx.forestpolicemobileterminal.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 巡逻轨迹
 * 
 * @author liupeng
 */
public class LocationInfoEntity {

	public LocationInfoEntity() {
		// TODO Auto-generated constructor stub
	}

	public LocationInfoEntity(int userid, Activity ct) {
		// TODO Auto-generated constructor stub
		this.userId = userid;
		this.latitude = ((MyApplication) ct.getApplication()).loca.getLATITUDE();
		this.longitude = ((MyApplication) ct.getApplication()).loca.getLONGITUDE();
		this.elevation = ((MyApplication) ct.getApplication()).loca.getELEVATION();
		this.time = ((MyApplication) ct.getApplication()).loca.getTIME();
	}

	private int infoId;
	private int userId;
	private double longitude;
	private double latitude;
	private double elevation;
	private String time;
	private int locationType;
	private String guid;
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public int getInfoId() {
		return infoId;
	}

	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * 
	 * @param
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * 
	 * @param longitude
	 *            经度
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * 
	 * @param
	 *
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * 
	 * @param
	 *
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * 
	 * @param
	 */
	public double getElevation() {
		return elevation;
	}

	/**
	 * 
	 * @param
	 */
	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public static JSONArray getJsonForLocationInfoList(List<LocationInfoEntity> locationInfoList)
			throws JSONException {
		JSONArray ja = null;
		if (locationInfoList != null && locationInfoList.size() > 0) {
			ja = new JSONArray();
			for (int i = 0; i < locationInfoList.size(); i++) {
				JSONObject jo = new JSONObject();
				jo.put("UsersId", locationInfoList.get(i).getUserId() + "");
				jo.put("X", locationInfoList.get(i).getLatitude() + "");
				jo.put("Y", locationInfoList.get(i).getLongitude() + "");
				jo.put("Z", locationInfoList.get(i).getElevation() + "");
				jo.put("CourseTime", locationInfoList.get(i).getTime() + "");
				jo.put("locationType", locationInfoList.get(i).getLocationType() + "");
				jo.put("GUID", locationInfoList.get(i).getGuid());
				ja.put(jo);
			}
		} else {
			return ja;
		}
		return ja;

	}

	/**
	 * 路线管理
	 * 
	 * @param locationInfoList
	 * @return
	 * @throws JSONException
	 */
	public static JSONArray getJsonForRouteLine(List<LocationInfoEntity> locationInfoList)
			throws JSONException {
		JSONArray ja = null;
		if (locationInfoList != null && locationInfoList.size() > 0) {
			ja = new JSONArray();
			for (int i = 0; i < locationInfoList.size(); i++) {
				JSONObject jo = new JSONObject();
				// jo.put("UsersId", locationInfoList.get(i).getUserId() + "");
				jo.put("X", locationInfoList.get(i).getLatitude() + "");
				jo.put("Y", locationInfoList.get(i).getLongitude() + "");
				jo.put("Z", locationInfoList.get(i).getElevation() + "");
				jo.put("time", locationInfoList.get(i).getTime() + "");// CourseTime
				jo.put("locationType", locationInfoList.get(i).getLocationType() + "");
				jo.put("GuId", locationInfoList.get(i).getGuid() + "");
				ja.put(jo);
			}
		} else {
			return ja;
		}
		return ja;

	}

	public int getLocationType() {
		return locationType;
	}

	public void setLocationType(int locationType) {
		this.locationType = locationType;
	}

	/**
	 * 路线管理
	 * 
	 * @param
	 * @return
	 * @throws JSONException
	 */
	public static List<LocationInfoEntity> getLocationInfoEntityLists(JSONArray jsonArray)
			throws JSONException {
		if (null != jsonArray && 0 != jsonArray.length()) {
			List<LocationInfoEntity> lists = new ArrayList<LocationInfoEntity>();
			for (int i = 0, length = jsonArray.length(); i < length; i++) {
				/**
				 * [{"ID":4222,"usersID":1,"X":"39.843088","Y":"116.302278","Z":
				 * "4.9E-324","CourseTime":"2016/4/22 13:39:40"}
				 */
				JSONObject obj = jsonArray.getJSONObject(i);
				LocationInfoEntity entity = new LocationInfoEntity();
				if (!obj.isNull("ID")) {
					entity.infoId = obj.getInt("ID");
				}
				if (!obj.isNull("usersID")) {
					entity.userId = obj.getInt("usersID");
				} else if(!obj.isNull("UsersId")) {
					entity.userId = obj.getInt("UsersId");
				}
				entity.latitude = obj.getDouble("X");
				entity.longitude = obj.getDouble("Y");
				entity.elevation = obj.getDouble("Z");
				entity.time = obj.getString("CourseTime");
					entity.guid=obj.getString("GUID");
				
				lists.add(entity);
			}
			return lists;
		}
		return null;
	}
	
}
