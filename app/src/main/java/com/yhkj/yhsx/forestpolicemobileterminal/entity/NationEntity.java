package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.Database.Receive_DisposeAlarm;

/**
 * 民族信息
 * 
 * @author xingyimin
 * 
 */
public class NationEntity implements Serializable{
	private int NationID;// 报警人民族ID
	private String NationName;// 报警人民族名称

	public int getNationID() {
		return NationID;
	}

	public void setNationID(int nationID) {
		NationID = nationID;
	}

	public String getNationName() {
		return NationName;
	}

	public void setNationName(String nationName) {
		NationName = nationName;
	}
	
	@Override
	public String toString() {
		return "NationEntity [NationID=" + NationID + ", NationName="
				+ NationName + "]";
	}

	/**
	 * 根据json获取民族信息集合
	 * 
	 * @param str
	 * @return 民族信息集合
	 * @throws JSONException
	 */
	public static List<NationEntity> getNationList(String str)
			throws JSONException {
		List<NationEntity> nationList = null;
		JSONObject jo = new JSONObject(str);
		if (jo.getInt("Error") == 0) {
			nationList = new ArrayList<NationEntity>();
			JSONArray ja = jo.getJSONArray("List");
			for (int i = 0; i < ja.length(); i++) {
				JSONObject json = ja.getJSONObject(i);
				NationEntity nation = new NationEntity();
				nation.setNationID(json.getInt(Receive_DisposeAlarm.NATION_ID));
				nation.setNationName(json.getString("NationName"));
				nationList.add(nation);
			}
		}
		return nationList;
	}
}
