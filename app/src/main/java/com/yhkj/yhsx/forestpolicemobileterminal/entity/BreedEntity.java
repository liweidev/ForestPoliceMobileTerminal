package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 养殖情况
 * 
 * @author zhengtiantian
 * 
 */
public class BreedEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int breedId;
	private Loaction locationId;// 定位ID
	private ArrayList<Accessory> bAttachment;
	private String bRemarks;// 备注
	private String acquisitionTime;// 信息采集时间
	private String unitName;// 单位名称
	private String breedName;// 养殖户名
	private String breedCount;// 存栏数
	private String breedType;// 主要种类
	private String breedMode;// 养殖方式
	private int serverId;

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public int getBreedId() {
		return breedId;
	}

	public void setBreedId(int breedId) {
		this.breedId = breedId;
	}

	public Loaction getLocationId() {
		return locationId;
	}

	public void setLocationId(Loaction locationId) {
		this.locationId = locationId;
	}

	public ArrayList<Accessory> getbAttachment() {
		return bAttachment;
	}

	public void setbAttachment(ArrayList<Accessory> bAttachment) {
		this.bAttachment = bAttachment;
	}

	public String getbRemarks() {
		return bRemarks;
	}

	public void setbRemarks(String bRemarks) {
		this.bRemarks = bRemarks;
	}

	public String getAcquisitionTime() {
		return acquisitionTime;
	}

	public void setAcquisitionTime(String acquisitionTime) {
		this.acquisitionTime = acquisitionTime;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getBreedName() {
		return breedName;
	}

	public void setBreedName(String breedName) {
		this.breedName = breedName;
	}

	public String getBreedCount() {
		return breedCount;
	}

	public void setBreedCount(String breedCount) {
		this.breedCount = breedCount;
	}

	public String getBreedType() {
		return breedType;
	}

	public void setBreedType(String breedType) {
		this.breedType = breedType;
	}

	public String getBreedMode() {
		return breedMode;
	}

	public void setBreedMode(String breedMode) {
		this.breedMode = breedMode;
	}

}