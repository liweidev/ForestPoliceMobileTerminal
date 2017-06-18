package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.List;


/**
 * 报警通知实体类
 * 
 * @author xingyimin
 * 
 */
public class WarningEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private int noteId;
	private String note;
	private String createTime;
	private String warningPeople;

	public String getWarningPeople() {
		return warningPeople;
	}

	public void setWarningPeople(String warningPeople) {
		this.warningPeople = warningPeople;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	private Loaction location;
	private List<Accessory> accessoryList;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Loaction getLocation() {
		return location;
	}

	public void setLocation(Loaction location) {
		this.location = location;
	}

	public List<Accessory> getAccessoryList() {
		return accessoryList;
	}

	public void setAccessoryList(List<Accessory> accessoryList) {
		this.accessoryList = accessoryList;
	}

	// public static List<WarningEntity> getObject(JSONArray jsonResult) throws
	// JSONException{
	// List<WarningEntity> warningList = null;
	// if (jsonResult == null) {
	// return warningList;
	// }else{
	// warningList = new ArrayList<WarningEntity>();
	// for (int i = 0; i < jsonResult.length(); i++) {
	// JSONObject object = jsonResult.getJSONObject(i);
	// WarningEntity warning = new WarningEntity();
	// warning.setNote(object.getString("note"));
	// warning.setCreateTime(object.getString("createTime"));
	// warning.setWarningPeople(object.getString("UserName"));
	// JSONObject jsonLocation = object.getJSONObject("location_id");
	// Loaction location = new Loaction();
	// location.setTIME(jsonLocation.getString("Time"));
	// location.setELEVATION(jsonLocation.getDouble("elevation"));
	// location.setLATITUDE(jsonLocation.getDouble("latitude"));
	// location.setLONGITUDE(jsonLocation.getDouble("longitude"));
	// warning.setLocation(location);
	// if (!object.isNull("affix")) {
	// JSONArray jsonAccessory = new JSONArray(object.getString("affix"));
	// List<Accessory> accessoryList = new ArrayList<Accessory>();
	// for (int j = 0; j < jsonAccessory.length(); j++) {
	// JSONObject objectAccessory = jsonAccessory.getJSONObject(i);
	// Accessory accessory = new Accessory();
	// accessory.setFileType(objectAccessory.getString("type"));
	// accessory.setAccessoryPath(objectAccessory.getString("url").replace("../../",
	// Constant.URL + Constant.WEB_PORT));
	// if (!object.isNull("fileAllPath")) {
	// accessory.setSlPath(object.getString("fileAllPath"));
	// }
	// accessoryList.add(accessory);
	// }
	// warning.setAccessoryList(accessoryList);
	// }
	// warningList.add(warning);
	// return warningList;
	// }
	// }
	// return warningList;
	// }

}
