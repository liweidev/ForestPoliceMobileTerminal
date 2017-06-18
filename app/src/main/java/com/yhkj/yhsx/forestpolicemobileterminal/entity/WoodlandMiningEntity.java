package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 林地内开采沙、土、石、矿情况表
 * 
 * @author zhengtiantian
 * 
 */
public class WoodlandMiningEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int woodlandMiningID;
	private Loaction locationId;// 定位ID
	private ArrayList<Accessory> wmAttachment;
	private String specificLocation;// 具体位置
	private String miningType;// 开采类型
	private String miningArea;// 开采面积
	private String fallimentiDello;// 破坏林木情况
	private String allowUnit;// 批准部门
	private String note;// 备注
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	private int serverId;

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public int getWoodlandMiningID() {
		return woodlandMiningID;
	}

	public void setWoodlandMiningID(int woodlandMiningID) {
		this.woodlandMiningID = woodlandMiningID;
	}

	public Loaction getLocationId() {
		return locationId;
	}

	public void setLocationId(Loaction locationId) {
		this.locationId = locationId;
	}

	public ArrayList<Accessory> getwmAttachment() {
		return wmAttachment;
	}

	public void setwAttachment(ArrayList<Accessory> wmAttachment) {
		this.wmAttachment = wmAttachment;
	}

	public String getSpecificLocation() {
		return specificLocation;
	}

	public void setSpecificLocation(String specificLocation) {
		this.specificLocation = specificLocation;
	}

	public String getMiningType() {
		return miningType;
	}

	public void setMiningType(String miningType) {
		this.miningType = miningType;
	}

	public String getMiningArea() {
		return miningArea;
	}

	public void setMiningArea(String miningArea) {
		this.miningArea = miningArea;
	}

	public String getFallimentiDello() {
		return fallimentiDello;
	}

	public void setFallimentiDello(String fallimentiDello) {
		this.fallimentiDello = fallimentiDello;
	}

	public String getAllowUnit() {
		return allowUnit;
	}

	public void setAllowUnit(String allowUnit) {
		this.allowUnit = allowUnit;
	}

}
