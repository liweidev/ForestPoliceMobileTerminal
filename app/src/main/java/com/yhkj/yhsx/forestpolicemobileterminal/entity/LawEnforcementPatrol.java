package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.util.ArrayList;

/**
 * 林区治安防控巡逻检查记录
 * 
 * @author xingyimin
 * 
 */
public class LawEnforcementPatrol {
	private int leId; // ID 自增长
	// private int leUnits;// 单位
	private String leNo; // 编号
	private String le_signature_bool;
	private String leTime; // 巡逻检查时间
	private int leVehicle; // 巡逻检查车辆
	private int leInspectors; // 巡逻检察人员
	private String leContents; // 巡逻检查内容
	private String leSituation;// 检查情况
	private String leSignature; // 被检查单位（个人）签字
	private String lePhone; // 电话
	private ArrayList<Accessory> leAccessory; // 附件
	private String leLegend; // 备注
	private Loaction loaction;// 定位
	private String leUnit;// 被检查单位
	
	public String getLe_signature_bool() {
		return le_signature_bool;
	}

	public void setLe_signature_bool(String le_signature_bool) {
		this.le_signature_bool = le_signature_bool;
	}

	public String getLeUnit() {
		return leUnit;
	}

	public void setLeUnit(String leUnit) {
		this.leUnit = leUnit;
	}

	public Loaction getLoaction() {
		return loaction;
	}

	public void setLoaction(Loaction loaction) {
		this.loaction = loaction;
	}

	public int getLeId() {
		return leId;
	}

	public void setLeId(int leId) {
		this.leId = leId;
	}

	// public int getLeUnits() {
	// return leUnits;
	// }
	// public void setLeUnits(int leUnits) {
	// this.leUnits = leUnits;
	// }
	public String getLeNo() {
		return leNo;
	}

	public void setLeNo(String leNo) {
		this.leNo = leNo;
	}

	public String getLeTime() {
		return leTime;
	}

	public void setLeTime(String leTime) {
		this.leTime = leTime;
	}

	public int getLeVehicle() {
		return leVehicle;
	}

	public void setLeVehicle(int leVehicle) {
		this.leVehicle = leVehicle;
	}

	public int getLeInspectors() {
		return leInspectors;
	}

	public void setLeInspectors(int leInspectors) {
		this.leInspectors = leInspectors;
	}

	public String getLeContents() {
		return leContents;
	}

	public void setLeContents(String leContents) {
		this.leContents = leContents;
	}

	public String getLeSituation() {
		return leSituation;
	}

	public void setLeSituation(String leSituation) {
		this.leSituation = leSituation;
	}

	public String getLeSignature() {
		return leSignature;
	}

	public void setLeSignature(String leSignature) {
		this.leSignature = leSignature;
	}

	public String getLePhone() {
		return lePhone;
	}

	public void setLePhone(String lePhone) {
		this.lePhone = lePhone;
	}

	public ArrayList<Accessory> getLeAccessory() {
		return leAccessory;
	}

	public void setLeAccessory(ArrayList<Accessory> leAccessory) {
		this.leAccessory = leAccessory;
	}

	public String getLeLegend() {
		return leLegend;
	}

	public void setLeLegend(String leLegend) {
		this.leLegend = leLegend;
	}
}
