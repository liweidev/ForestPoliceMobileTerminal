/**
 * 
 */
package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.util.ArrayList;

/**
 * 受理报警登记
 * 
 * @author liupeng
 */
public class ReceiveAlarmEntity {

	private int raId; // ID 自增长

	private String receivingAlarmCode; // 接警编号
	private String receivingAlarmStaff; // 接警人员
	private String receivingAlarmDate; // 接警时间
	private int alarmMode; // 报警方式 上级批准，电话报警，来信来访，110转

	private String alarmName; // 报警人信息 报警人姓名
	private int alarmSex; // 报警人性别
	private String alarmAge; // 报警人年龄
	private int alarmNationality; // 报警人民族
	private String alarmPhone; // 报警人电话
	private String alarmAdd; // 报警人现住址
	private String alarmIDCard; // 报警人身份证号

	private String alarmContent; // 报警内容
	private String alarmOpinionLeaders; // 领导意见

	private String instructionTime; // 收到出警指定时间：
	private String timeToReach; // 到达警情现场时间
	private String situationAndOpinion; // 处境情况及意见
	private String alarmingSituation; // 填写人
	private String alarmingSituationTime; // 填写人时间

	public String getAlarmingSituationTime() {
		return alarmingSituationTime;
	}

	public void setAlarmingSituationTime(String alarmingSituationTime) {
		this.alarmingSituationTime = alarmingSituationTime;
	}

	private String transfer;// 接受单位

	public String getTransfer() {
		return transfer;
	}

	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}

	public String getAlarmingSituation() {
		return alarmingSituation;
	}

	public void setAlarmingSituation(String alarmingSituation) {
		this.alarmingSituation = alarmingSituation;
	}

	private String alarmingOpinionLeaders;// 处警领导意见

	private String processingResults; // 处理结果
	private String receiveUnit; // 移送 接收单位
	private String receiveTime; // 接收 接收时间
	private String crownCaseNo; // 备注 刑事案件立案编号
	private String administrativeCaseNo; // 备注 行政案件立案编号
	private ArrayList<Accessory> raAccessory; // 附件

	private Accessory alarmSignature; // 报警人签字
	private String alarmSignatureDate; // 报警人签字日期
	private Loaction loaction;

	public Loaction getLoaction() {
		return loaction;
	}

	public void setLoaction(Loaction loaction) {
		this.loaction = loaction;
	}

	public int getRaId() {
		return raId;
	}

	public void setRaId(int raId) {
		this.raId = raId;
	}

	public String getReceivingAlarmCode() {
		return receivingAlarmCode;
	}

	public void setReceivingAlarmCode(String receivingAlarmCode) {
		this.receivingAlarmCode = receivingAlarmCode;
	}

	public String getReceivingAlarmStaff() {
		return receivingAlarmStaff;
	}

	public void setReceivingAlarmStaff(String receivingAlarmStaff) {
		this.receivingAlarmStaff = receivingAlarmStaff;
	}

	public String getReceivingAlarmDate() {
		return receivingAlarmDate;
	}

	public void setReceivingAlarmDate(String receivingAlarmDate) {
		this.receivingAlarmDate = receivingAlarmDate;
	}

	public int getAlarmMode() {
		return alarmMode;
	}

	public void setAlarmMode(int alarmMode) {
		this.alarmMode = alarmMode;
	}

	public String getAlarmName() {
		return alarmName;
	}

	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}

	public int getAlarmSex() {
		return alarmSex;
	}

	public void setAlarmSex(int alarmSex) {
		this.alarmSex = alarmSex;
	}

	public String getAlarmAge() {
		return alarmAge;
	}

	public void setAlarmAge(String alarmAge) {
		this.alarmAge = alarmAge;
	}

	public int getAlarmNationality() {
		return alarmNationality;
	}

	public void setAlarmNationality(int alarmNationality) {
		this.alarmNationality = alarmNationality;
	}

	public String getAlarmPhone() {
		return alarmPhone;
	}

	public void setAlarmPhone(String alarmPhone) {
		this.alarmPhone = alarmPhone;
	}

	public String getAlarmAdd() {
		return alarmAdd;
	}

	public void setAlarmAdd(String alarmAdd) {
		this.alarmAdd = alarmAdd;
	}

	public String getAlarmIDCard() {
		return alarmIDCard;
	}

	public void setAlarmIDCard(String alarmIDCard) {
		this.alarmIDCard = alarmIDCard;
	}

	public String getAlarmContent() {
		return alarmContent;
	}

	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent;
	}

	public String getAlarmOpinionLeaders() {
		return alarmOpinionLeaders;
	}

	public void setAlarmOpinionLeaders(String alarmOpinionLeaders) {
		this.alarmOpinionLeaders = alarmOpinionLeaders;
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

	public String getSituationAndOpinion() {
		return situationAndOpinion;
	}

	public void setSituationAndOpinion(String situationAndOpinion) {
		this.situationAndOpinion = situationAndOpinion;
	}

	public String getAlarmingOpinionLeaders() {
		return alarmingOpinionLeaders;
	}

	public void setAlarmingOpinionLeaders(String alarmingOpinionLeaders) {
		this.alarmingOpinionLeaders = alarmingOpinionLeaders;
	}

	public String getProcessingResults() {
		return processingResults;
	}

	public void setProcessingResults(String processingResults) {
		this.processingResults = processingResults;
	}

	public String getReceiveUnit() {
		return receiveUnit;
	}

	public void setReceiveUnit(String receiveUnit) {
		this.receiveUnit = receiveUnit;
	}

	public String getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Accessory getAlarmSignature() {
		return alarmSignature;
	}

	public void setAlarmSignature(Accessory alarmSignature) {
		this.alarmSignature = alarmSignature;
	}

	public String getAlarmSignatureDate() {
		return alarmSignatureDate;
	}

	public void setAlarmSignatureDate(String alarmSignatureDate) {
		this.alarmSignatureDate = alarmSignatureDate;
	}

	public String getCrownCaseNo() {
		return crownCaseNo;
	}

	public void setCrownCaseNo(String crownCaseNo) {
		this.crownCaseNo = crownCaseNo;
	}

	public String getAdministrativeCaseNo() {
		return administrativeCaseNo;
	}

	public void setAdministrativeCaseNo(String administrativeCaseNo) {
		this.administrativeCaseNo = administrativeCaseNo;
	}

	public ArrayList<Accessory> getRaAccessory() {
		return raAccessory;
	}

	public void setRaAccessory(ArrayList<Accessory> raAccessory) {
		this.raAccessory = raAccessory;
	}

}
