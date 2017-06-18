package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 民警负责人--选择项
 * 
 * @author xingyimin
 * 
 */
public class PoliceHeadEntity implements Serializable{
	private int PeoplePoliceID;// 民警Id
	private String PeoplePoliceName;// 民警负责人名称

	public int getPeoplePoliceID() {
		return PeoplePoliceID;
	}

	public void setPeoplePoliceID(int peoplePoliceID) {
		PeoplePoliceID = peoplePoliceID;
	}

	public String getPeoplePoliceName() {
		return PeoplePoliceName;
	}

	public void setPeoplePoliceName(String peoplePoliceName) {
		PeoplePoliceName = peoplePoliceName;
	}
	
	@Override
	public String toString() {
		return "PoliceHeadEntity [PeoplePoliceID=" + PeoplePoliceID
				+ ", PeoplePoliceName=" + PeoplePoliceName + "]";
	}

	/**
	 * 根据查询到的json得到民警负责人列表
	 * 
	 * @param str
	 * @return 民警负责人列表
	 * @throws JSONException
	 */
	public static List<PoliceHeadEntity> getList(String str)
			throws JSONException {
		List<PoliceHeadEntity> policeList = null;
		JSONObject jo = new JSONObject(str);
		if (jo.getInt("Error") == 0) {
			policeList = new ArrayList<PoliceHeadEntity>();
			JSONArray ja = jo.getJSONArray("List");
			for (int i = 0; i < ja.length(); i++) {
				PoliceHeadEntity police = new PoliceHeadEntity();
				JSONObject json = ja.getJSONObject(i);
				police.setPeoplePoliceID(json.getInt("PeoplePoliceID"));
				police.setPeoplePoliceName(json.getString("PeoplePoliceName"));
				policeList.add(police);
			}
		}
		return policeList;
	}
}
