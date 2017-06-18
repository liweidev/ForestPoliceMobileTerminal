package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 报警信息-历史信息 实体类
 * 
 * @author zhengtiantian
 * 
 */
public class AlarmingHistoryInforEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String time;// 时间
	private String hisOpinion;// 意见信息
	private AlarmStateEntity alarmState;// 状态信息
	private PoliceHeadEntity police;// 审批人
	private int alarmId;
	
	public int getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(int alarmId) {
		this.alarmId = alarmId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getHisOpinion() {
		return hisOpinion;
	}

	public void setHisOpinion(String hisOpinion) {
		this.hisOpinion = hisOpinion;
	}

	public AlarmStateEntity getAlarmState() {
		return alarmState;
	}

	public void setAlarmState(AlarmStateEntity alarmState) {
		this.alarmState = alarmState;
	}

	public PoliceHeadEntity getPolice() {
		return police;
	}

	public void setPolice(PoliceHeadEntity police) {
		this.police = police;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		return "AlarmingHistoryInforEntity [time=" + time + ", hisOpinion="
				+ hisOpinion + ", alarmState=" + alarmState + ", police="
				+ police + ", alarmId=" + alarmId + "]";
	}

	/**
	 * 根据报警信息Id获取历史信息
	 * @param json
	 * @return historicalList
	 * @throws JSONException
	 */
	public static List<AlarmingHistoryInforEntity> getListObject(String json)
			throws JSONException {
		List<AlarmingHistoryInforEntity> historicalList=null;
		JSONObject jo = new JSONObject(json);
		int error = jo.getInt("Error");
		if (error == 0) {
			historicalList = new ArrayList<AlarmingHistoryInforEntity>();
			JSONArray ja = (JSONArray) jo.get("List");
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jsonResult = (JSONObject) ja.get(i);
				AlarmingHistoryInforEntity alarm = new AlarmingHistoryInforEntity();
				if (!jsonResult.isNull("Time")) {
					alarm.setTime(jsonResult.getString("Time"));
				}
				if (!jsonResult.isNull("HisOpinion")) {
					alarm.setHisOpinion(jsonResult.getString("HisOpinion"));
				}
				JSONObject jsonObject = jsonResult.getJSONObject("AlarmState");
				AlarmStateEntity state = new AlarmStateEntity();
				state.setStateID(jsonObject.getInt("StateID"));
				if (!jsonObject.isNull("StateName")) {
					state.setStateName(jsonObject.getString("StateName"));
				}
				alarm.setAlarmState(state);
				historicalList.add(alarm);
			}
		}
		return historicalList;

	}

}
