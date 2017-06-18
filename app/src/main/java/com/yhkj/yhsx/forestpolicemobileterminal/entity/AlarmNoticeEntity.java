package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 接报警--通知信息
 * 
 * @author xingyimin
 * 
 */
public class AlarmNoticeEntity {

	private int noticeID;// 通知信息ID
	private int alarmID;// 报警信息ID
	private String noticeMessage;// 通知信息

	public int getNoticeID() {
		return noticeID;
	}

	public void setNoticeID(int noticeID) {
		this.noticeID = noticeID;
	}

	public int getAlarmID() {
		return alarmID;
	}

	public void setAlarmID(int alarmID) {
		this.alarmID = alarmID;
	}

	public String getNoticeMessage() {
		return noticeMessage;
	}

	public void setNoticeMessage(String noticeMessage) {
		this.noticeMessage = noticeMessage;
	}

	/**
	 * 根据查询结果获得接警信息通知集合
	 * 
	 * @param
	 * @return
	 * @throws JSONException
	 */
	public List<AlarmNoticeEntity> getNoticeList(JSONArray ja)
			throws JSONException {
		List<AlarmNoticeEntity> noticeList = null;
		// JSONObject jo = new JSONObject(str);
		if (ja != null) {
			noticeList = new ArrayList<AlarmNoticeEntity>();
			// JSONArray ja = jo.getJSONArray("List");
			for (int i = 0; i < ja.length(); i++) {
				AlarmNoticeEntity notice = new AlarmNoticeEntity();
				JSONObject json = ja.getJSONObject(i);
				notice.setNoticeID(json.getInt("NoticeID"));
				notice.setAlarmID(json.getInt("AlarmID"));
				notice.setNoticeMessage(json.getString("NoticeMessage"));
				noticeList.add(notice);
			}
		}
		return noticeList;
	}

	/**
	 * 根据查询结果获得接警信息通知
	 * 
	 * @param object
	 * @return
	 * @throws JSONException
	 */
	public static AlarmNoticeEntity getObjectByJSONObject(JSONObject object)
			throws JSONException {
		AlarmNoticeEntity notice = null;
		if (object != null) {
			notice = new AlarmNoticeEntity();
			if (!object.isNull("NoticeID")) {
				notice.setNoticeID(object.getInt("NoticeID"));
			}
			if (!object.isNull("AlarmID")) {
				notice.setAlarmID(object.getInt("AlarmID"));
			}
			if (!object.isNull("NoticeMessage")) {
				notice.setNoticeMessage(object.getString("NoticeMessage"));
			}
		}
		return notice;
	}
}
