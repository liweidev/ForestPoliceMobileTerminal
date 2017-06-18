package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 重点列管人员（单位）情况
 * 
 * @author xingyimin
 * 
 */
public class ManagedObjectEntity implements Serializable {

	private int moId;
	private int managedObjectID;// ID
	private int gender;// 性别
	private String unitName;// 姓名或单位名称
	private String domicilePlace;// 户籍地
	private String cardID;// 身份证或者执照号码
	private String addressOrUnit;// 现住址或单位
	private int career;// 职业
	private int managedCause;// 列管原因
	private String specificCauses;// 何时何因被何部门给何处罚
	private String managedTIme;// 列管时间
	private int responsibilityPolice;// 负责民警
	private ArrayList<Accessory> accessoryList;// 附件
	private Loaction location_id;// 坐标
	private String remark;// 备注

	public int getMoId() {
		return moId;
	}

	public void setMoId(int moId) {
		this.moId = moId;
	}

	public int getManagedObjectID() {
		return managedObjectID;
	}

	public void setManagedObjectID(int managedObjectID) {
		this.managedObjectID = managedObjectID;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getDomicilePlace() {
		return domicilePlace;
	}

	public void setDomicilePlace(String domicilePlace) {
		this.domicilePlace = domicilePlace;
	}

	public String getCardID() {
		return cardID;
	}

	public void setCardID(String cardID) {
		this.cardID = cardID;
	}

	public String getAddressOrUnit() {
		return addressOrUnit;
	}

	public void setAddressOrUnit(String addressOrUnit) {
		this.addressOrUnit = addressOrUnit;
	}

	public int getCareer() {
		return career;
	}

	public void setCareer(int career) {
		this.career = career;
	}

	public int getManagedCause() {
		return managedCause;
	}

	public void setManagedCause(int managedCause) {
		this.managedCause = managedCause;
	}

	public String getSpecificCauses() {
		return specificCauses;
	}

	public void setSpecificCauses(String specificCauses) {
		this.specificCauses = specificCauses;
	}

	public String getManagedTIme() {
		return managedTIme;
	}

	public void setManagedTIme(String managedTIme) {
		this.managedTIme = managedTIme;
	}

	public int getResponsibilityPolice() {
		return responsibilityPolice;
	}

	public void setResponsibilityPolice(int responsibilityPolice) {
		this.responsibilityPolice = responsibilityPolice;
	}

	public ArrayList<Accessory> getAccessoryList() {
		return accessoryList;
	}

	public void setAccessoryList(ArrayList<Accessory> accessoryList) {
		this.accessoryList = accessoryList;
	}

	public Loaction getLocation_id() {
		return location_id;
	}

	public void setLocation_id(Loaction location_id) {
		this.location_id = location_id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
