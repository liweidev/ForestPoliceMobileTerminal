/**
 * 
 */
package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 重点保护植物
 * 
 * @author liupeng
 * 
 */
public class KeyProtectedPlants implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pkkId; // ID 自增长
	private String competent_deparment;

	private int pkkNo; // 序号
	private String comOrSLNumber; // 具体分布地点或林班号小班号
	private int serverId;
	private String plantNames; // 植物名称
	private String treeAge; // 树龄
	private String protectionLevel; // 保护级别
	private String status; // 现况
	private String fileNo; // 档案编号

	private String contact; // 联系方式
	private ArrayList<Accessory> kppAccessory; // 附件
	private String kppLegeng;// 备注
	private Loaction location;// 定位

	public String getCompetent_deparment() {
		return competent_deparment;
	}

	public void setCompetent_deparment(String competent_deparment) {
		this.competent_deparment = competent_deparment;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	private String mapoPersonnel; // 管护人员

	public Loaction getLocation() {
		return location;
	}

	public void setLocation(Loaction location) {
		this.location = location;
	}

	public int getPkkId() {
		return pkkId;
	}

	public void setPkkId(int pkkId) {
		this.pkkId = pkkId;
	}

	public int getPkkNo() {
		return pkkNo;
	}

	public void setPkkNo(int pkkNo) {
		this.pkkNo = pkkNo;
	}

	public String getComOrSLNumber() {
		return comOrSLNumber;
	}

	public void setComOrSLNumber(String comOrSLNumber) {
		this.comOrSLNumber = comOrSLNumber;
	}

	public String getPlantNames() {
		return plantNames;
	}

	public void setPlantNames(String plantNames) {
		this.plantNames = plantNames;
	}

	public String getTreeAge() {
		return treeAge;
	}

	public void setTreeAge(String treeAge) {
		this.treeAge = treeAge;
	}

	public String getProtectionLevel() {
		return protectionLevel;
	}

	public void setProtectionLevel(String protectionLevel) {
		this.protectionLevel = protectionLevel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
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

	public ArrayList<Accessory> getKppAccessory() {
		return kppAccessory;
	}

	public void setKppAccessory(ArrayList<Accessory> kppAccessory) {
		this.kppAccessory = kppAccessory;
	}

	public String getKppLegeng() {
		return kppLegeng;
	}

	public void setKppLegeng(String kppLegeng) {
		this.kppLegeng = kppLegeng;
	}; // 备注

}
