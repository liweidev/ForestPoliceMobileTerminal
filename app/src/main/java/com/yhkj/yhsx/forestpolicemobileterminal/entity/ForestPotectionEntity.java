package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 护林基本力量
 * 
 * @author xingyimin
 * 
 */
public class ForestPotectionEntity implements Serializable {

	private int fpId;
	private int forestPotectionID;// ID
	private int year;// 年度
	private int administration;// 主管部门
	private int unit;// 单位
	private int officialRank;// 职别
	private String name;// 姓名
	private int gender;// 性别
	private String age;// 年龄
	private String tel;// 联系电话
	private String managementArea;// 管护责任区
	private int responsibility;// 具体责任
	private Loaction loaction;// 定位
	private String remark;// 备注
	private ArrayList<Accessory> accessoryList;// 附件

	
	public int getFpId() {
		return fpId;
	}

	public void setFpId(int fpId) {
		this.fpId = fpId;
	}

	public int getForestPotectionID() {
		return forestPotectionID;
	}

	public void setForestPotectionID(int forestPotectionID) {
		this.forestPotectionID = forestPotectionID;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getAdministration() {
		return administration;
	}

	public void setAdministration(int administration) {
		this.administration = administration;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public int getOfficialRank() {
		return officialRank;
	}

	public void setOfficialRank(int officialRank) {
		this.officialRank = officialRank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getManagementArea() {
		return managementArea;
	}

	public void setManagementArea(String managementArea) {
		this.managementArea = managementArea;
	}

	public int getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(int responsibility) {
		this.responsibility = responsibility;
	}

	public Loaction getLoaction() {
		return loaction;
	}

	public void setLoaction(Loaction loaction) {
		this.loaction = loaction;
	}

	public ArrayList<Accessory> getAccessoryList() {
		return accessoryList;
	}

	public void setAccessoryList(ArrayList<Accessory> accessoryList) {
		this.accessoryList = accessoryList;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
