package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理意见--选择项
 * 
 * @author xingyimin
 * 
 */
public class AlarmOpinionEntity {

	private int opinionID;// 处理意见ID
	private String opinionName;// 处理意见

	public int getOpinionID() {
		return opinionID;
	}

	public void setOpinionID(int opinionID) {
		this.opinionID = opinionID;
	}

	public String getOpinionName() {
		return opinionName;
	}

	public void setOpinionName(String opinionName) {
		this.opinionName = opinionName;
	}
	/**
	 * 
	 * @param str
	 * @return 职务信息列表
	 * @throws JSONException
	 */
	public static List<AlarmOpinionEntity> getList(String str) throws JSONException{
		List<AlarmOpinionEntity> opinionList = null;
		JSONObject jo = new JSONObject(str);
		if (jo.getInt("Error") == 0) {
			opinionList = new ArrayList<AlarmOpinionEntity>();
			JSONArray ja = jo.getJSONArray("List");
			for (int i = 0; i < ja.length(); i++) {
				AlarmOpinionEntity as = new AlarmOpinionEntity();
				JSONObject jsonResult = ja.getJSONObject(i);
				as.setOpinionID(jsonResult.getInt("TreatmentAdviceID"));
				as.setOpinionName(jsonResult.getString("TreatmentAdviceName"));
				opinionList.add(as);
			}
		}
		return opinionList;
	}
}
