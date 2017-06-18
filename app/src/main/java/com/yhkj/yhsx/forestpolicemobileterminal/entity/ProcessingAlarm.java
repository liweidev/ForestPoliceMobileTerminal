package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.util.ArrayList;

/**
 * 处理报警登记
 * 
 * @author liupeng
 */
public class ProcessingAlarm {

	private int paId; // ID 自增长

	private String alarmingNumbers; // 处警编号
	private String instructionTime; // 接到出警指令时间
	private String timeToReach; // 到达现场时间
	private String organizedPolice; // 主办民警
	private String coPolice; // 协办民警
	private String crimesName; // 违法犯罪人基本情况 姓名
	private int crimesSex; // 性别
	private String crimesAge; // 年龄
	private String crimesEducation; // 文化程度
	private String crimesUnit;// 工作单位

	private String crimesAddress;// 住址
	private String crimesUnitName;// 单位名称
	private String crimesAdd; // 地址
	private String crimesRrLegRepre; // 法定代表人
	private String crimesPost;// 职务

	private String briefCase; // / 初查简要情况（时间、地点、人员、事实经过等）
	private String preliminaryEndTime; // 初查结束时间
	private String apAdvice; // 处警民警意见
	private String aprfComments; // 处警单位负责人意见
	private String fpsoInstructions; // 森林公安机关负责人批示
	private ArrayList<Accessory> paAccessory; // 附件
	private String paLegend; // 备注
	private Loaction location;

	public Loaction getLocation() {
		return location;
	}

	public void setLocation(Loaction location) {
		this.location = location;
	}

	public String getCrimesPost() {
		return crimesPost;
	}

	public void setCrimesPost(String crimesPost) {
		this.crimesPost = crimesPost;
	}

	public String getCrimesUnit() {
		return crimesUnit;
	}

	public void setCrimesUnit(String crimesUnit) {
		this.crimesUnit = crimesUnit;
	}

	public String getCrimesAddress() {
		return crimesAddress;
	}

	public void setCrimesAddress(String crimesAddress) {
		this.crimesAddress = crimesAddress;
	}

	public String getCrimesUnitName() {
		return crimesUnitName;
	}

	public void setCrimesUnitName(String crimesUnitName) {
		this.crimesUnitName = crimesUnitName;
	}

	public String getCrimesAdd() {
		return crimesAdd;
	}

	public void setCrimesAdd(String crimesAdd) {
		this.crimesAdd = crimesAdd;
	}

	public int getPaId() {
		return paId;
	}

	public void setPaId(int paId) {
		this.paId = paId;
	}

	public String getAlarmingNumbers() {
		return alarmingNumbers;
	}

	public void setAlarmingNumbers(String alarmingNumbers) {
		this.alarmingNumbers = alarmingNumbers;
	}

	public String getInstructionTime() {
		return instructionTime;
	}

	public void setInstructionTime(String instructionTime) {
		this.instructionTime = instructionTime;
	}

	public String getTimeToReach() {
		return timeToReach;
	}

	public void setTimeToReach(String timeToReach) {
		this.timeToReach = timeToReach;
	}

	public String getOrganizedPolice() {
		return organizedPolice;
	}

	public void setOrganizedPolice(String organizedPolice) {
		this.organizedPolice = organizedPolice;
	}

	public String getCoPolice() {
		return coPolice;
	}

	public void setCoPolice(String coPolice) {
		this.coPolice = coPolice;
	}

	public String getCrimesName() {
		return crimesName;
	}

	public void setCrimesName(String crimesName) {
		this.crimesName = crimesName;
	}

	public int getCrimesSex() {
		return crimesSex;
	}

	public void setCrimesSex(int crimesSex) {
		this.crimesSex = crimesSex;
	}

	public String getCrimesAge() {
		return crimesAge;
	}

	public void setCrimesAge(String crimesAge) {
		this.crimesAge = crimesAge;
	}

	public String getCrimesEducation() {
		return crimesEducation;
	}

	public void setCrimesEducation(String crimesEducation) {
		this.crimesEducation = crimesEducation;
	}

	public String getCrimesRrLegRepre() {
		return crimesRrLegRepre;
	}

	public void setCrimesRrLegRepre(String crimesRrLegRepre) {
		this.crimesRrLegRepre = crimesRrLegRepre;
	}

	public String getBriefCase() {
		return briefCase;
	}

	public void setBriefCase(String briefCase) {
		this.briefCase = briefCase;
	}

	public String getPreliminaryEndTime() {
		return preliminaryEndTime;
	}

	public void setPreliminaryEndTime(String preliminaryEndTime) {
		this.preliminaryEndTime = preliminaryEndTime;
	}

	public String getApAdvice() {
		return apAdvice;
	}

	public void setApAdvice(String apAdvice) {
		this.apAdvice = apAdvice;
	}

	public String getAprfComments() {
		return aprfComments;
	}

	public void setAprfComments(String aprfComments) {
		this.aprfComments = aprfComments;
	}

	public String getFpsoInstructions() {
		return fpsoInstructions;
	}

	public void setFpsoInstructions(String fpsoInstructions) {
		this.fpsoInstructions = fpsoInstructions;
	}

	public ArrayList<Accessory> getPaAccessory() {
		return paAccessory;
	}

	public void setPaAccessory(ArrayList<Accessory> paAccessory) {
		this.paAccessory = paAccessory;
	}

	public String getPaLegend() {
		return paLegend;
	}

	public void setPaLegend(String paLegend) {
		this.paLegend = paLegend;
	}

}
