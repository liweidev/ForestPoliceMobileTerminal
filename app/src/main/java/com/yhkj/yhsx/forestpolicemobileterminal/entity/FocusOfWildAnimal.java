package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * 重点野生动物情况
 * 
 * @author liupeng
 */
public class FocusOfWildAnimal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int fowaId; // 编号
	private String accountabilityUnit; // 责任单位
	private String fowaNo; // 序号
	private String animalName; // 动物名称
	private String drAndPn; // 分布具体范围及地点名称
	private String protectionLevel; // 保护级别
	private String protectFileNum; // 保护档案编号
	private String protectTheWay; // 保护方式
	private String mapoPersonnel; // 管护人员
	private String contact; // 联系方式
	private int serverId;
	private String slPath;
	private ArrayList<Accessory> fowaAccessory; // 附件

	private String fowaLegend; // 备注
	private Loaction location;// 定位

	public String getSlPath() {
		return slPath;
	}

	public void setSlPath(String slPath) {
		this.slPath = slPath;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public Loaction getLocationId() {
		return location;
	}

	public void setLocationId(Loaction location) {
		this.location = location;
	}

	public int getFowaId() {
		return fowaId;
	}

	public void setFowaId(int fowaId) {
		this.fowaId = fowaId;
	}

	public String getAccountabilityUnit() {
		return accountabilityUnit;
	}

	public void setAccountabilityUnit(String accountabilityUnit) {
		this.accountabilityUnit = accountabilityUnit;
	}

	public String getFowaNo() {
		return fowaNo;
	}

	public void setFowaNo(String fowaNo) {
		this.fowaNo = fowaNo;
	}

	public String getAnimalName() {
		return animalName;
	}

	public void setAnimalName(String animalName) {
		this.animalName = animalName;
	}

	public String getDrAndPn() {
		return drAndPn;
	}

	public void setDrAndPn(String drAndPn) {
		this.drAndPn = drAndPn;
	}

	public String getProtectionLevel() {
		return protectionLevel;
	}

	public void setProtectionLevel(String protectionLevel) {
		this.protectionLevel = protectionLevel;
	}

	public String getProtectFileNum() {
		return protectFileNum;
	}

	public void setProtectFileNum(String protectFileNum) {
		this.protectFileNum = protectFileNum;
	}

	public String getProtectTheWay() {
		return protectTheWay;
	}

	public void setProtectTheWay(String protectTheWay) {
		this.protectTheWay = protectTheWay;
	}

	public String getMapoPersonnel() {
		return mapoPersonnel;
	}

	public void setMapoPersonnel(String mapoPersonnel) {
		this.mapoPersonnel = mapoPersonnel;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public ArrayList<Accessory> getFowaAccessory() {
		return fowaAccessory;
	}

	public void setFowaAccessory(ArrayList<Accessory> fowaAccessory) {
		this.fowaAccessory = fowaAccessory;
	}

	public String getFowaLegend() {
		return fowaLegend;
	}

	public void setFowaLegend(String fowaLegend) {
		this.fowaLegend = fowaLegend;
	}

}
