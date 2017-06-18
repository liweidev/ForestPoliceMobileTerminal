package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import db.Database.TheWorkingLog;

/**
 * 工作日志
 * 
 * @author xingyimin
 * 
 */
public class TheWorkingLogEntity implements Serializable{

	private int logId;
	private String date;
	private String policeName;
	private int classLearder;
	private String week;
	private String event;
	private int weatherId;
	
	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public int getLogId() {
		return logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPoliceName() {
		return policeName;
	}

	public void setPoliceName(String policeName) {
		this.policeName = policeName;
	}

	public int getClassLearder() {
		return classLearder;
	}

	public void setClassLearder(int classLearder) {
		this.classLearder = classLearder;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public int getWeatherId() {
		return weatherId;
	}

	public void setWeatherId(int weatherId) {
		this.weatherId = weatherId;
	}

	/**
	 * 工作日志对象的JSON字符串
	 * 
	 * @param log
	 * @return
	 * @throws JSONException
	 */
	public static String getJsonString(TheWorkingLogEntity log, int userId,
			String userName) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("userId", userId);
		json.put("userName", userName);
		json.put("tableId", TheWorkingLog.THE_WORKING_LOG_TABLE_ID);
		json.put(TheWorkingLog.THE_WORKING_LOG_DATE, log.getDate());
//		json.put(TheWorkingLog.POLICE_ON_DUTY, log.getPoliceName());
		json.put(TheWorkingLog.CLASS_LEADERS, log.getClassLearder());
		json.put(TheWorkingLog.THE_MAIN_WORK_AND_IMPORTANT_EVENTS,
				log.getEvent());
		json.put(TheWorkingLog.WEATHER, log.getWeatherId());
		return json.toString();
	}

	/**
	 * 通过查询返回的JSON字符串转换成TheWorkingLogEntity对象集合
	 * 
	 * @param json
	 * @return TheWorkingLogEntity对象集合
	 * @throws JSONException
	 */
	public static List<TheWorkingLogEntity> getObject(String json)
			throws JSONException {
		List<TheWorkingLogEntity> logList = null;
		JSONArray ja = null;
		if (json != null) {
			ja = new JSONArray(json);
			logList = new ArrayList<TheWorkingLogEntity>();
			for (int i = 0; i < ja.length(); i++) {
				TheWorkingLogEntity log = new TheWorkingLogEntity();
				JSONObject jo = ja.getJSONObject(i);
				if (!jo.isNull(TheWorkingLog.THE_WORKING_LOG_DATE)) {
					log.setDate(jo
							.getString(TheWorkingLog.THE_WORKING_LOG_DATE));
				}
				if (!jo.isNull(TheWorkingLog.THE_MAIN_WORK_AND_IMPORTANT_EVENTS)) {
					log.setEvent(jo
							.getString(TheWorkingLog.THE_MAIN_WORK_AND_IMPORTANT_EVENTS));
				}
				logList.add(log);
			}
		}
		return logList;
	}
}
