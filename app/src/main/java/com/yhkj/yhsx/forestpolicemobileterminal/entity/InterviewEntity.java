package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 重点列管人员走访
 * 
 * @author xingyimin
 * 
 */
public class InterviewEntity implements Serializable {

	private int id;
	private int interviewID;// ID
	private String interviewTime;// 走访时间
	private String interviewSite;// 走访地点
	private String interviewPolice;// 走访民警
	private int interviewObejct;// 走访对象
	private String unitName;// 单位名称
	private String unitAddress;// 单位地址
	private String name;// 性名
	private int gender;// 性别
	private String age;// 年龄
	private String address;// 家庭住址
	private String managedCause;// 列管原因
	private String interviewContent;// 走访内容
	private String opinion;// 走访对象意见或建议及反映的其它问题
	private ArrayList<Accessory> accessoryList;// 附件
	private Loaction location_id;// 坐标
	private String remark;// 备注

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInterviewID() {
		return interviewID;
	}

	public void setInterviewID(int interviewID) {
		this.interviewID = interviewID;
	}

	public String getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(String interviewTime) {
		this.interviewTime = interviewTime;
	}

	public String getInterviewSite() {
		return interviewSite;
	}

	public void setInterviewSite(String interviewSite) {
		this.interviewSite = interviewSite;
	}

	public String getInterviewPolice() {
		return interviewPolice;
	}

	public void setInterviewPolice(String interviewPolice) {
		this.interviewPolice = interviewPolice;
	}

	public int getInterviewObejct() {
		return interviewObejct;
	}

	public void setInterviewObejct(int interviewObejct) {
		this.interviewObejct = interviewObejct;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitAddress() {
		return unitAddress;
	}

	public void setUnitAddress(String unitAddress) {
		this.unitAddress = unitAddress;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getManagedCause() {
		return managedCause;
	}

	public void setManagedCause(String managedCause) {
		this.managedCause = managedCause;
	}

	public String getInterviewContent() {
		return interviewContent;
	}

	public void setInterviewContent(String interviewContent) {
		this.interviewContent = interviewContent;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
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
