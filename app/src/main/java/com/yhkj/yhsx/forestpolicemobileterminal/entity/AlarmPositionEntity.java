package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 职务--选择项
 * @author xingyimin
 *
 */
public class AlarmPositionEntity {
	private int positionId;// 职务ID
	private String positionName;// 职务name
	public int getPositionID() {
		return positionId;
	}
	public void setPositionID(int positionId) {
		this.positionId = positionId;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	/**
	 * 
	 * @param str
	 * @return 职务信息列表
	 * @throws JSONException
	 */
	public static List<AlarmPositionEntity> getList(String str) throws JSONException{
		List<AlarmPositionEntity> positionList = null;
		JSONObject jo = new JSONObject(str);
		if (jo.getInt("Error") == 0) {
			positionList = new ArrayList<AlarmPositionEntity>();
			JSONArray ja = jo.getJSONArray("List");
			for (int i = 0; i < ja.length(); i++) {
				AlarmPositionEntity as = new AlarmPositionEntity();
				JSONObject jsonResult = ja.getJSONObject(i);
				as.setPositionID(jsonResult.getInt("PositionID"));
				as.setPositionName(jsonResult.getString("PositionName"));
				positionList.add(as);
			}
		}
		return positionList;
	}
}
