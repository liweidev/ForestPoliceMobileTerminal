/**
 * 
 */
package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.util.ArrayList;

/**
 * 林区治安防控巡逻检查记录--森林防火
 * 
 * @author liupeng
 */
public class FireproofWork {

	private int fwId; // ID 自增长

	private String fwNo; // 编号
	private String pcTime; // 巡逻检查时间
	private String pcRoutes; // 巡逻检查路线地点
	private String pInspectors; // 巡逻检察人员
	private String pcContents; // 巡逻检查内容

	private String pInspection;// 检查意见

	private String tbcSignature; // 被检查单位（个人）签字
	private String tbcPhone; // 电话
	private ArrayList<Accessory> fwAccessory; // 附件
	private String fwLegend; // 备注
	private int pUnits;// 单位
	private Loaction location;// 定位

	public Loaction getLocation() {
		return location;
	}

	public void setLocation(Loaction location) {
		this.location = location;
	}

	public int getpUnits() {
		return pUnits;
	}

	public void setpUnits(int pUnits) {
		this.pUnits = pUnits;
	}

	public int getFwId() {
		return fwId;
	}

	public void setFwId(int fwId) {
		this.fwId = fwId;
	}

	public String getFwNo() {
		return fwNo;
	}

	public void setFwNo(String fwNo) {
		this.fwNo = fwNo;
	}

	public String getPcTime() {
		return pcTime;
	}

	public void setPcTime(String pcTime) {
		this.pcTime = pcTime;
	}

	public String getPcRoutes() {
		return pcRoutes;
	}

	public void setPcRoutes(String pcRoutes) {
		this.pcRoutes = pcRoutes;
	}

	public String getpInspectors() {
		return pInspectors;
	}

	public void setpInspectors(String pInspectors) {
		this.pInspectors = pInspectors;
	}

	public String getPcContents() {
		return pcContents;
	}

	public void setPcContents(String pcContents) {
		this.pcContents = pcContents;
	}

	public String getpInspection() {
		return pInspection;
	}

	public void setpInspection(String pInspection) {
		this.pInspection = pInspection;
	}

	public String getTbcSignature() {
		return tbcSignature;
	}

	public void setTbcSignature(String tbcSignature) {
		this.tbcSignature = tbcSignature;
	}

	public String getTbcPhone() {
		return tbcPhone;
	}

	public void setTbcPhone(String tbcPhone) {
		this.tbcPhone = tbcPhone;
	}

	public ArrayList<Accessory> getFwAccessory() {
		return fwAccessory;
	}

	public void setFwAccessory(ArrayList<Accessory> fwAccessory) {
		this.fwAccessory = fwAccessory;
	}

	public String getFwLegend() {
		return fwLegend;
	}

	public void setFwLegend(String fwLegend) {
		this.fwLegend = fwLegend;
	}

}
