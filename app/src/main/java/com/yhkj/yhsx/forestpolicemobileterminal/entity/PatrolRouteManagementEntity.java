package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.Database.PatrolRouteManagement;

/**
 * 巡防路线管理
 * 
 * @author xingyimin
 * 
 */
public class PatrolRouteManagementEntity implements Serializable {

	private int patrolId;// 自增长Id
	private String name;// 目的地名称
	private int typeId;// 线路类型Id
	private int travelAdviceId;// 行驶建议Id
	private String route;// 线路记录
	private double distance;// 距离
	private int weather;// 天气

	public int getPatrolId() {
		return patrolId;
	}

	public void setPatrolId(int patrolId) {
		this.patrolId = patrolId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getTravelAdviceId() {
		return travelAdviceId;
	}

	public void setTravelAdviceId(int rateId) {
		this.travelAdviceId = rateId;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getWeather() {
		return weather;
	}

	public void setWeather(int weather) {
		this.weather = weather;
	}

	/**
	 * 获取巡防路线对象的JSON字符串
	 * 
	 * @param patrol
	 * @return
	 * @throws JSONException
	 */
	public static String getJsonString(PatrolRouteManagementEntity patrol,
			int userId) throws JSONException {
		JSONObject json = new JSONObject();
		json.put("userId", userId);
		json.put("tableId",
				PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TABLE_ID);
		json.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_NAME,
				patrol.getName());
		json.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TYPE,
				patrol.getTypeId());
		json.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TRAVEL_ADVICE,
				patrol.getTravelAdviceId());
		json.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_DISTANCE,
				patrol.getDistance());
		json.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_ROUTE,
				new JSONArray(patrol.getRoute()));
		json.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_WEATHER,
				patrol.getWeather());
		return json.toString();
	}

	/**
	 * 根据JSON对象获取线路管理对象集合
	 * 
	 * @param str
	 * @return
	 * @throws JSONException
	 */
	public static List<PatrolRouteManagementEntity> getObject(String str)
			throws JSONException {
		List<PatrolRouteManagementEntity> patrolList = null;
		JSONArray ja = null;
		if (str != null && !str.equals("anyType{}")) {
			ja = new JSONArray(str);
			patrolList = new ArrayList<PatrolRouteManagementEntity>();
			for (int i = 0; i < ja.length(); i++) {
				JSONObject json = ja.getJSONObject(i);
				PatrolRouteManagementEntity patrol = new PatrolRouteManagementEntity();
				if (!json.isNull(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_ID)) {
					patrol.setPatrolId(json
							.getInt(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_ID));
				}
				if (!json.isNull(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_NAME)) {
				patrol.setName(json
						.getString(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_NAME));
				}
				if (!json.isNull(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TYPE)) {
				patrol.setTypeId(json
						.getInt(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TYPE));
				}
				if (!json.isNull(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TRAVEL_ADVICE)) {
				patrol.setTravelAdviceId(json
						.getInt(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TRAVEL_ADVICE));
				}
				if (!json.isNull(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_DISTANCE)) {
				patrol.setDistance(json
						.getDouble(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_DISTANCE));
				}
				if (!json.isNull(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_ROUTE)) {
				patrol.setRoute(json
						.getString(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_ROUTE));
				}
				if (!json.isNull(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_WEATHER)) {
				patrol.setWeather(json
						.getInt(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_WEATHER));
				}
				patrolList.add(patrol);
			}
		}
		return patrolList;

	}
}
