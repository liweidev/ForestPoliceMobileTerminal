package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 涉林重点行业情况登记表
 * 
 * @author xingyimin
 * 
 */
public class ForestryKeyIndustriesEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int fkiId;
	private int forestryKeyIndustriesID;// ID
	private String unitName;// 单位名称
	private String address;// 地址
	private String license;// 证照
	private String operateType;// 经营行业、种类、名称
	private String principal;// 负责人及联系方式
	private String contactInformation;// 管理人及联系方式
	private String varietiesSourcess;// 品种来源
	private int protectionLevel;// 保护级别
	private String practitionerNumber;// 行业人员数量
	private ArrayList<Accessory> accessoryList;// 附件
	private Loaction location_id;// 坐标
	private String remark;// 备注

	
	
	public int getFkiId() {
		return fkiId;
	}

	public void setFkiId(int fkiId) {
		this.fkiId = fkiId;
	}

	public int getForestryKeyIndustriesID() {
		return forestryKeyIndustriesID;
	}

	public void setForestryKeyIndustriesID(int forestryKeyIndustriesID) {
		this.forestryKeyIndustriesID = forestryKeyIndustriesID;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getContactInformation() {
		return contactInformation;
	}

	public void setContactInformation(String contactInformation) {
		this.contactInformation = contactInformation;
	}

	public String getVarietiesSourcess() {
		return varietiesSourcess;
	}

	public void setVarietiesSourcess(String varietiesSourcess) {
		this.varietiesSourcess = varietiesSourcess;
	}

	public int getProtectionLevel() {
		return protectionLevel;
	}

	public void setProtectionLevel(int protectionLevel) {
		this.protectionLevel = protectionLevel;
	}

	public String getPractitionerNumber() {
		return practitionerNumber;
	}

	public void setPractitionerNumber(String practitionerNumber) {
		this.practitionerNumber = practitionerNumber;
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
