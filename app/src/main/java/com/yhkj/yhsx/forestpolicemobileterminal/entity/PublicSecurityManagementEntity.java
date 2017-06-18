package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.util.ArrayList;

/**
 * 派出所治安管理检查
 * 
 * @author liupeng
 * 
 */
public class PublicSecurityManagementEntity {

	private int psmId; // 编号
	private String unitName; // 被检单位名称
	private String unitAdd; // 经营地点
	private String legalRepresentative; // 法人代表
	private String head; // 负责人
	private String businessProject; // 经营项目
	private String businessPractice; // 经营方式
	private String checkTheTime; // 检查时间
	private String inspectors; // 检查人员
	private String contentAndProblems; // 检查内容及存在问题
	private String rectificationOpinions; // 整改意见
	private String ownerOrHead; // 业主或负责人意见
	private ArrayList<Accessory> psmAccessory; // 附件
	private String psmLegend; // 备注
	private Loaction location;// 定位
	private String signature;// 签名
	private String date;// 签名日期
	private String le_signature_bool;
	

	public String getLe_signature_bool() {
		return le_signature_bool;
	}

	public void setLe_signature_bool(String le_signature_bool) {
		this.le_signature_bool = le_signature_bool;
	}

	public int getPsmId() {
		return psmId;
	}

	public void setPsmId(int psmId) {
		this.psmId = psmId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitAdd() {
		return unitAdd;
	}

	public void setUnitAdd(String unitAdd) {
		this.unitAdd = unitAdd;
	}

	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getBusinessProject() {
		return businessProject;
	}

	public void setBusinessProject(String businessProject) {
		this.businessProject = businessProject;
	}

	public String getBusinessPractice() {
		return businessPractice;
	}

	public void setBusinessPractice(String businessPractice) {
		this.businessPractice = businessPractice;
	}

	public String getCheckTheTime() {
		return checkTheTime;
	}

	public void setCheckTheTime(String checkTheTime) {
		this.checkTheTime = checkTheTime;
	}

	public String getInspectors() {
		return inspectors;
	}

	public void setInspectors(String inspectors) {
		this.inspectors = inspectors;
	}

	public String getContentAndProblems() {
		return contentAndProblems;
	}

	public void setContentAndProblems(String contentAndProblems) {
		this.contentAndProblems = contentAndProblems;
	}

	public String getRectificationOpinions() {
		return rectificationOpinions;
	}

	public void setRectificationOpinions(String rectificationOpinions) {
		this.rectificationOpinions = rectificationOpinions;
	}

	public String getOwnerOrHead() {
		return ownerOrHead;
	}

	public void setOwnerOrHead(String ownerOrHead) {
		this.ownerOrHead = ownerOrHead;
	}

	public ArrayList<Accessory> getPsmAccessory() {
		return psmAccessory;
	}

	public void setPsmAccessory(ArrayList<Accessory> psmAccessory) {
		this.psmAccessory = psmAccessory;
	}

	public String getPsmLegend() {
		return psmLegend;
	}

	public void setPsmLegend(String psmLegend) {
		this.psmLegend = psmLegend;
	}

	public Loaction getLocation() {
		return location;
	}

	public void setLocation(Loaction location) {
		this.location = location;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
