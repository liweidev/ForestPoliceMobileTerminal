/**
 * 
 */
package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 值班表
 * 
 * @author liupeng
 */
public class OndutyEntity implements Comparable<Long> {
	private int ondutyID; // ID
	private int catalogID; // 表ID
	private String ondutyDate; // 日期
	private String ondutyWeek; // 星期
	private String ondutyPeople; // 值班民警
	private String ondutyLeader;// 值班领导
	private String remark; // 备注

	/*
	 * private List<Accessory> ondutyAttachment; public List<Accessory>
	 * getOndutyAttachment() { return ondutyAttachment; } public void
	 * setOndutyAttachment(List<Accessory> ondutyAttachment) {
	 * this.ondutyAttachment = ondutyAttachment; }
	 */
	public int getOndutyID() {
		return ondutyID;
	}

	public void setOndutyID(int ondutyID) {
		this.ondutyID = ondutyID;
	}

	public int getCatalogID() {
		return catalogID;
	}

	public void setCatalogID(int catalogID) {
		this.catalogID = catalogID;
	}

	public String getOndutyDate() {
		return ondutyDate;
	}

	public void setOndutyDate(String ondutyDate) {
		this.ondutyDate = ondutyDate;
	}

	public String getOndutyWeek() {
		return ondutyWeek;
	}

	public void setOndutyWeek(String ondutyWeek) {
		this.ondutyWeek = ondutyWeek;
	}

	public String getOndutyPeople() {
		return ondutyPeople;
	}

	public void setOndutyPeople(String ondutyPeople) {
		this.ondutyPeople = ondutyPeople;
	}

	public String getOndutyLeader() {
		return ondutyLeader;
	}

	public void setOndutyLeader(String ondutyLeader) {
		this.ondutyLeader = ondutyLeader;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public int compareTo(Long arg0) {
		// TODO Auto-generated method stub
		if (arg0.compareTo(ActivityUtils
				.getDateMillisFromISO8601(this.ondutyDate)) > 0) {
			return 1;
		} else if (arg0.compareTo(ActivityUtils
				.getDateMillisFromISO8601(this.ondutyDate)) == 0) {
			return 0;
		} else {
			return -1;
		}
	}

	public static List<OndutyEntity> getListByJson(String json) {
		List<OndutyEntity> list = new ArrayList<OndutyEntity>();
		try {
			JSONObject jo = new JSONObject(json);
			int error = jo.getInt("Error");
			if (error == 0) {
				JSONArray array = jo.getJSONArray("OndutyList");
				for (int i = 0; i < array.length(); i++) {
					JSONObject object = array.getJSONObject(i);
					OndutyEntity onduty = new OndutyEntity();
					if (!object.getString("ondutyDate").equals("")) {
						onduty.setOndutyDate(object.getString("ondutyDate"));
					}
					if (!object.getString("ondutyPeople").equals("")) {
						onduty.setOndutyPeople(object.getString("ondutyPeople"));
					}
					if (!object.getString("ondutyManager").equals("")) {
						onduty.setOndutyLeader(object
								.getString("ondutyManager"));
					}
					onduty.setOndutyID(object.getInt("ondutyID"));
					onduty.setCatalogID(object.getInt("catalogID"));
					if (!object.getString("remark").equals("")) {
						onduty.setRemark(object.getString("remark"));
					}
					if (!object.getString("ondutyWeek").equals("")) {
						onduty.setOndutyWeek(object.getString("ondutyWeek"));
					}

					/*
					 * if (!object.isNull("affix")) { ArrayList<Accessory>
					 * accessorys = new ArrayList<Accessory>(); JSONArray
					 * jaaccessorys = new JSONArray( object.getString("affix"));
					 * for (int j = 0; j < jaaccessorys.length(); j++) {
					 * JSONObject joAttachment = jaaccessorys.getJSONObject(j);
					 * Accessory accessory = new Accessory();
					 * 
					 * if (!joAttachment.isNull("url")) { String url =
					 * joAttachment.getString("url");
					 * accessory.setAccessoryPath(url.replace("../../",
					 * Constant.INTERNAL_URL+Constant.INTERNAL_WEB_PORT)); }
					 * accessorys.add(accessory); }
					 * onduty.setOndutyAttachment(accessorys); }
					 */
					list.add(onduty);
				}
			} else {
				list = null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
