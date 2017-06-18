package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 文化程度-选择项
 * 
 * @author xingyimin
 * 
 */
public class AlarmEducationEntity {
	private int educationId;// 文化程度ID
	private String educationName;// 文化程度name

	public int getEducationID() {
		return educationId;
	}

	public void setEducationID(int educationId) {
		this.educationId = educationId;
	}

	public String getEducationName() {
		return educationName;
	}

	public void setEducationName(String educationName) {
		this.educationName = educationName;
	}

	/**
	 * 
	 * @param str
	 * @return 文化程度信息列表
	 * @throws JSONException
	 */
	public static List<AlarmEducationEntity> getList(String str)
			throws JSONException {
		List<AlarmEducationEntity> educationList = null;
		JSONObject jo = new JSONObject(str);
		if (jo.getInt("Error") == 0) {
			educationList = new ArrayList<AlarmEducationEntity>();
			JSONArray ja = jo.getJSONArray("List");
			for (int i = 0; i < ja.length(); i++) {
				AlarmEducationEntity as = new AlarmEducationEntity();
				JSONObject jsonResult = ja.getJSONObject(i);
				as.setEducationID(jsonResult.getInt("EducationID"));
				as.setEducationName(jsonResult.getString("EducationName"));
				educationList.add(as);
			}
		}
		return educationList;
	}

}
