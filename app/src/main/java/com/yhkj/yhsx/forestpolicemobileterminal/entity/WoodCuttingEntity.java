package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 木材采伐场（点）登记表
 * 
 * @author zhengtiantian
 * 
 */
public class WoodCuttingEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int woodCuttingID;
	private Loaction locationId;// 定位ID
	private ArrayList<Accessory> wcAttachment;
	private String cuttingUnit;// 采伐单位或个人
	private String cuttingLocation;// 采伐地点
	private String cuttingCardID;// 采伐证号
	private String allowCount;// 批准采伐数量
	private String cuttingSpecies;// 采伐树种
	private String cuttingMode;// 采伐方式
	private String cuttingCount;// 进入林区采伐人员数
	private String cuttingBeginTime;// 开始采伐时间
	private String cuttingEndTime;// 采伐结束时间
	private String inspection;// 检查情况
	private int serverId;

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public int getWoodCuttingID() {
		return woodCuttingID;
	}

	public void setWoodCuttingID(int woodCuttingID) {
		this.woodCuttingID = woodCuttingID;
	}

	public Loaction getLocationId() {
		return locationId;
	}

	public void setLocationId(Loaction locationId) {
		this.locationId = locationId;
	}

	public ArrayList<Accessory> getwcAttachment() {
		return wcAttachment;
	}

	public void setwAttachment(ArrayList<Accessory> wcAttachment) {
		this.wcAttachment = wcAttachment;
	}

	public String getCuttingUnit() {
		return cuttingUnit;
	}

	public void setCuttingUnit(String cuttingUnit) {
		this.cuttingUnit = cuttingUnit;
	}

	public String getCuttingLocation() {
		return cuttingLocation;
	}

	public void setCuttingLocation(String cuttingLocation) {
		this.cuttingLocation = cuttingLocation;
	}

	public String getCuttingCardID() {
		return cuttingCardID;
	}

	public void setCuttingCardID(String cuttingCardID) {
		this.cuttingCardID = cuttingCardID;
	}

	public String getAllowCount() {
		return allowCount;
	}

	public void setAllowCount(String allowCount) {
		this.allowCount = allowCount;
	}

	public String getCuttingSpecies() {
		return cuttingSpecies;
	}

	public void setCuttingSpecies(String cuttingSpecies) {
		this.cuttingSpecies = cuttingSpecies;
	}

	public String getCuttingMode() {
		return cuttingMode;
	}

	public void setCuttingMode(String cuttingMode) {
		this.cuttingMode = cuttingMode;
	}

	public String getCuttingCount() {
		return cuttingCount;
	}

	public void setCuttingCount(String cuttingCount) {
		this.cuttingCount = cuttingCount;
	}

	public String getCuttingBeginTime() {
		return cuttingBeginTime;
	}

	public void setCuttingBeginTime(String cuttingBeginTime) {
		this.cuttingBeginTime = cuttingBeginTime;
	}

	public String getCuttingEndTime() {
		return cuttingEndTime;
	}

	public void setCuttingEndTime(String cuttingEndTime) {
		this.cuttingEndTime = cuttingEndTime;
	}

	public String getInspection() {
		return inspection;
	}

	public void setInspection(String inspection) {
		this.inspection = inspection;
	}

}
