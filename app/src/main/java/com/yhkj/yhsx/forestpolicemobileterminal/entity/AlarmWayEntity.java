package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 接警方式--选择项
 * 
 * @author xingyimin
 * 
 */
public class AlarmWayEntity implements Serializable{
	private int alarmWayID;// 接警方式ID
	private String alarmWayName;// 接警方式名称

	public int getAlarmWayID() {
		return alarmWayID;
	}

	public void setAlarmWayID(int alarmWayID) {
		this.alarmWayID = alarmWayID;
	}

	public String getAlarmWayName() {
		return alarmWayName;
	}

	public void setAlarmWayName(String alarmWayName) {
		this.alarmWayName = alarmWayName;
	}
	
	@Override
	public String toString() {
		return "AlarmWayEntity [alarmWayID=" + alarmWayID + ", alarmWayName="
				+ alarmWayName + "]";
	}

	/**
	 * 根据json获取接警方式信息集合
	 * 
	 * @param str
	 * @return 接警方式信息集合
	 * @throws JSONException
	 */
	public static List<AlarmWayEntity> getModeList(String str)
			throws JSONException {
		List<AlarmWayEntity> alarmWayList = null;
		JSONObject jo = new JSONObject(str);
		if (jo.getInt("Error") == 0) {
			alarmWayList = new ArrayList<AlarmWayEntity>();
			JSONArray ja = jo.getJSONArray("List");
			for (int i = 0; i < ja.length(); i++) {
				JSONObject json = ja.getJSONObject(i);
				AlarmWayEntity nation = new AlarmWayEntity();
				nation.setAlarmWayID(json
						.getInt("AlarmWayID"));
				nation.setAlarmWayName(json.getString("AlarmWayName"));
				alarmWayList.add(nation);
			}
		}
		return alarmWayList;
	}
}
