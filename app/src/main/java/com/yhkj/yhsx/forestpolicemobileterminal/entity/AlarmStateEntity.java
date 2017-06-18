package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 状态信息--选择项
 * 
 * @author xingyimin
 * 
 */
public class AlarmStateEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int StateID;
	private String StateName;
	public int getStateID() {
		return StateID;
	}
	public void setStateID(int stateID) {
		StateID = stateID;
	}
	public String getStateName() {
		return StateName;
	}
	public void setStateName(String stateName) {
		StateName = stateName;
	}
	
	@Override
	public String toString() {
		return "AlarmStateEntity [StateID=" + StateID + ", StateName="
				+ StateName + "]";
	}
	/**
	 * 
	 * @param str
	 * @return 状态信息列表
	 * @throws JSONException
	 */
	public static List<AlarmStateEntity> getList(String str) throws JSONException{
		List<AlarmStateEntity> stateList = null;
		JSONObject jo = new JSONObject(str);
		if (jo.getInt("Error") == 0) {
			stateList = new ArrayList<AlarmStateEntity>();
			JSONArray ja = jo.getJSONArray("List");
			for (int i = 0; i < ja.length(); i++) {
				AlarmStateEntity as = new AlarmStateEntity();
				JSONObject jsonResult = ja.getJSONObject(i);
				as.setStateID(jsonResult.getInt("StateID"));
				as.setStateName(jsonResult.getString("StateName"));
				stateList.add(as);
			}
		}
		return stateList;
	}
}
