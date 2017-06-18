package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 林情调查
 * 
 * @author xingyimin
 */
public class ForestSurvey implements Serializable {

	private static final long serialVersionUID = 1L;
	private int fsId; // 编号
	private String unitName; // 单位
	private String number; // 小班号
	private String area; // 面 积
	private String forestTypes;// 林种
	private String landTypes;// 地类
	private String forestOwnership; // 林木权属
	private String landOwnership;// 土地所有权
	private ArrayList<Accessory> fsAttachment; // 附件
	private String fsLegend; // 备注
	private int serverId;
	private Loaction locationId;// 定位

	public Loaction getLocationId() {
		return locationId;
	}

	public void setLocationId(Loaction locationId) {
		this.locationId = locationId;
	}

	private String slPath;

	public int getFsId() {
		return fsId;
	}

	public void setFsId(int fsId) {
		this.fsId = fsId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getForestTypes() {
		return forestTypes;
	}

	public void setForestTypes(String forestTypes) {
		this.forestTypes = forestTypes;
	}

	public String getLandTypes() {
		return landTypes;
	}

	public void setLandTypes(String landTypes) {
		this.landTypes = landTypes;
	}

	public String getForestOwnership() {
		return forestOwnership;
	}

	public void setForestOwnership(String forestOwnership) {
		this.forestOwnership = forestOwnership;
	}

	public String getLandOwnership() {
		return landOwnership;
	}

	public void setLandOwnership(String landOwnership) {
		this.landOwnership = landOwnership;
	}

	public ArrayList<Accessory> getFsAttachment() {
		return fsAttachment;
	}

	public void setFsAttachment(ArrayList<Accessory> fsAttachment) {
		this.fsAttachment = fsAttachment;
	}

	public String getFsLegend() {
		return fsLegend;
	}

	public void setFsLegend(String fsLegend) {
		this.fsLegend = fsLegend;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getSlPath() {
		return slPath;
	}

	public void setSlPath(String slPath) {
		this.slPath = slPath;
	}

}