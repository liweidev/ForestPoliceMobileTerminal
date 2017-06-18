package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 接处警 实体类
 * 
 * @author xingyimin
 * 
 */
public class AlarmEntity implements Serializable {

	private int id;// id 自增长
	// 报警信息
	private String AlarmTime;// 接警时间
	private AlarmWayEntity alarmWay;// 接警方式
	private PoliceHeadEntity police;// 接警民警
	private String ToAlarmName;// 报警人姓名
	private String ToAlarmAge;// 报警人年龄
	private NationEntity nation;// 报警人民族
	private String ToAlarmSex;// 报警人性别
	private String Contact;// 联系方式
	private String CardNumber;// 身份证号
	private String Address;// 暂住地址
	private String ToAlarmMessage;// 报警内容

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAlarmTime() {
		return AlarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		AlarmTime = alarmTime;
	}

	public PoliceHeadEntity getPolice() {
		return police;
	}

	public void setPolice(PoliceHeadEntity police) {
		this.police = police;
	}

	public String getToAlarmName() {
		return ToAlarmName;
	}

	public void setToAlarmName(String toAlarmName) {
		ToAlarmName = toAlarmName;
	}

	public String getToAlarmAge() {
		return ToAlarmAge;
	}

	public void setToAlarmAge(String toAlarmAge) {
		ToAlarmAge = toAlarmAge;
	}

	public String getToAlarmSex() {
		return ToAlarmSex;
	}

	public void setToAlarmSex(String toAlarmSex) {
		ToAlarmSex = toAlarmSex;
	}

	public String getContact() {
		return Contact;
	}

	public void setContact(String contact) {
		Contact = contact;
	}

	public String getCardNumber() {
		return CardNumber;
	}

	public void setCardNumber(String cardNumber) {
		CardNumber = cardNumber;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getToAlarmMessage() {
		return ToAlarmMessage;
	}

	public void setToAlarmMessage(String toAlarmMessage) {
		ToAlarmMessage = toAlarmMessage;
	}

	// 审批
	private int AlarmID;// 报警信息ID
	private int ApprovalPeopleID;// 审批人ID
	private int HostPoliceID;// 主办民警ID
	private int CoPoliceID;// 协办民警ID
	private String Opinion;// 意见
	private List<AlarmingHistoryInforEntity> historyList;// 历史数据列表

	public int getAlarmID() {
		return AlarmID;
	}

	public void setAlarmID(int alarmID) {
		AlarmID = alarmID;
	}

	public int getApprovalPeopleID() {
		return ApprovalPeopleID;
	}

	public void setApprovalPeopleID(int approvalPeopleID) {
		ApprovalPeopleID = approvalPeopleID;
	}

	public int getHostPoliceID() {
		return HostPoliceID;
	}

	public void setHostPoliceID(int hostPoliceID) {
		HostPoliceID = hostPoliceID;
	}

	public int getCoPoliceID() {
		return CoPoliceID;
	}

	public void setCoPoliceID(int coPoliceID) {
		CoPoliceID = coPoliceID;
	}

	public String getOpinion() {
		return Opinion;
	}

	public void setOpinion(String opinion) {
		Opinion = opinion;
	}

	public List<AlarmingHistoryInforEntity> getHistoryList() {
		return historyList;
	}

	public void setHistoryList(List<AlarmingHistoryInforEntity> historyList) {
		this.historyList = historyList;
	}

	public AlarmWayEntity getAlarmWay() {
		return alarmWay;
	}

	public void setAlarmWay(AlarmWayEntity alarmWay) {
		this.alarmWay = alarmWay;
	}

	public NationEntity getNation() {
		return nation;
	}

	public void setNation(NationEntity nation) {
		this.nation = nation;
	}

	// 查询报警信息
	private String Number;// 接警编号
	private AlarmStateEntity state;// 状态信息

	public String getNumber() {
		return Number;
	}

	public void setNumber(String number) {
		Number = number;
	}

	public AlarmStateEntity getState() {
		return state;
	}

	public void setState(AlarmStateEntity state) {
		this.state = state;
	}

	// 处警信息
	private AlarmingEntity alarming;

	public AlarmingEntity getAlarming() {
		return alarming;
	}

	public void setAlarming(AlarmingEntity alarming) {
		this.alarming = alarming;
	}

	// 处警审批
	private AlarmingResultEntity result;

	public AlarmingResultEntity getResult() {
		return result;
	}

	public void setResult(AlarmingResultEntity result) {
		this.result = result;
	}

	// 公安处审批
	private AlarmingResultEntity policeOffice;

	public AlarmingResultEntity getPoliceOffice() {
		return policeOffice;
	}

	public void setPoliceOffice(AlarmingResultEntity policeOffice) {
		this.policeOffice = policeOffice;
	}

	@Override
	public String toString() {
		return "AlarmEntity [id=" + id + ", AlarmTime=" + AlarmTime
				+ ", alarmWay=" + alarmWay + ", police=" + police
				+ ", ToAlarmName=" + ToAlarmName + ", ToAlarmAge=" + ToAlarmAge
				+ ", nation=" + nation + ", ToAlarmSex=" + ToAlarmSex
				+ ", Contact=" + Contact + ", CardNumber=" + CardNumber
				+ ", Address=" + Address + ", ToAlarmMessage=" + ToAlarmMessage
				+ ", AlarmID=" + AlarmID + ", ApprovalPeopleID="
				+ ApprovalPeopleID + ", HostPoliceID=" + HostPoliceID
				+ ", CoPoliceID=" + CoPoliceID + ", Opinion=" + Opinion
				+ ", historyList=" + historyList + ", Number=" + Number
				+ ", state=" + state + ", alarming=" + alarming + ", result="
				+ result + ", policeOffice=" + policeOffice + "]";
	}
	
}
