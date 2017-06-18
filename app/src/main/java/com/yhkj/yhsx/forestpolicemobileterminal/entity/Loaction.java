package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.Database.Location;

/**
 * 定位信息
 * 
 * @author liupeng
 */
public class Loaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int location_id; // ID
	private double LONGITUDE; // varchar 20 not null 经度 X
	private double LATITUDE; // varchar 20 not null 纬度 Y
	private double ELEVATION; // varchar 20 not null 海拔 Z
	private String TIME;
	private int locationType;//标识当前定位坐标是否有效

	
	
	public int getLocationType() {
		return locationType;
	}

	public void setLocationType(int locationType) {
		this.locationType = locationType;
	}

	public String getTIME() {
		return TIME;
	}

	public void setTIME(String tIME) {
		TIME = tIME;
	}

	public int getLocation_id() {
		return location_id;
	}

	public void setLocation_id(int location_id) {
		this.location_id = location_id;
	}

	public double getLONGITUDE() {
		return LONGITUDE;
	}

	public void setLONGITUDE(double lONGITUDE) {
		LONGITUDE = lONGITUDE;
	}

	public double getLATITUDE() {
		return LATITUDE;
	}

	public void setLATITUDE(double d) {
		LATITUDE = d;
	}

	public double getELEVATION() {
		return ELEVATION;
	}

	public void setELEVATION(double eLEVATION) {
		ELEVATION = eLEVATION;
	}

	/**
	 * 获取定位信息的JSON字符串
	 * 
	 * @param loac
	 * @return
	 * @throws JSONException
	 */
	public static String getJsonString(Loaction loca) throws JSONException {
		if (loca != null && null != loca.getTIME() && !"".equals(loca.getTIME()) && 0 != loca.getLocationType()) {
			JSONObject locationJo = new JSONObject();
			locationJo.put(Location.ELEVATION, loca.getELEVATION());
			locationJo.put(Location.LATITUDE, loca.getLATITUDE());
			locationJo.put(Location.LONGITUDE, loca.getLONGITUDE());
			locationJo.put("time", loca.getTIME());
			locationJo.put("locationType", loca.getLocationType());
			return locationJo.toString();
		}
//		JSONObject locationJo = new JSONObject();
//		locationJo.put(Location.ELEVATION, loca.getELEVATION());
//		locationJo.put(Location.LATITUDE, loca.getLATITUDE());
//		locationJo.put(Location.LONGITUDE, loca.getLONGITUDE());
//		locationJo.put("time", loca.getTIME());
//		locationJo.put("locationType", loca.getLocationType());
		return null;
	}

	/**
	 * 通过JSONArray对象获取MyLoaction类型集合
	 * @see  ，存储坐标信息
	 * @author liupeng
	 * @param json
	 *            JSONArray
	 * @return List<MyLoaction> 坐标集合
	 * @throws JSONException
	 */
	public static List<Loaction> getEntityList(JSONArray json)
			throws JSONException {
		List<Loaction> list = null;
		if (json != null && json.length() > 0) {
			list = new ArrayList<Loaction>();
			for (int i = 0; i < json.length(); i++) {
				Loaction location = new Loaction();
				JSONObject position = (JSONObject) json.get(i);
				location.setELEVATION(position.getDouble(Location.ELEVATION));
				location.setLATITUDE(position.getDouble(Location.LATITUDE));
				location.setLONGITUDE(position.getDouble(Location.LONGITUDE));
				location.setTIME(position.getString("time"));
//				location.setLocationType(position.getInt("locationType"));
				list.add(location);
			}
		}
		return list;

	}
}
