package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.util.ArrayList;

/**
 * 处警信息
 * 
 * @author xingyimin
 * 
 */
public class AlarmingEntity {
	private int id;// id 自增长
	private int AlarmId;
	private int StateId;
	private String ReceivedTime;// 接到处警指令时间
	private String ArrivalsTime;// 到达警情现场时间
	private String Name;// 姓名
	private String Age;// 年龄
	private String Sex;// 性别
	private AlarmEducationEntity Education;// 文化程度
	private String Workplace;// 工作单位
	private String AddressZ;// 住址
	private String CompanyName;// 单位名称
	private String AddressD;// 地址
	private String Legal;// 法人
	private AlarmPositionEntity Position;// 职务
	private String BriefCase;// 简要情况
	private AlarmOpinionEntity opinion;// 处理意见
	private String Remark;// 备注
	private ArrayList<Accessory> Accessory;// 附件
	private Loaction loaction;// 定位
	private PoliceHeadEntity ApprovalPeople;// 审批人信息
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	public int getAlarmId() {
		return AlarmId;
	}

	public void setAlarmId(int alarmId) {
		AlarmId = alarmId;
	}

	public int getStateId() {
		return StateId;
	}

	public void setStateId(int stateId) {
		StateId = stateId;
	}

	public String getReceivedTime() {
		return ReceivedTime;
	}

	public void setReceivedTime(String receivedTime) {
		ReceivedTime = receivedTime;
	}

	public String getArrivalsTime() {
		return ArrivalsTime;
	}

	public void setArrivalsTime(String arrivalsTime) {
		ArrivalsTime = arrivalsTime;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getAge() {
		return Age;
	}

	public void setAge(String age) {
		Age = age;
	}

	public String getSex() {
		return Sex;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public AlarmEducationEntity getEducation() {
		return Education;
	}

	public void setEducation(AlarmEducationEntity education) {
		Education = education;
	}

	public String getWorkplace() {
		return Workplace;
	}

	public void setWorkplace(String workplace) {
		Workplace = workplace;
	}

	public String getAddressZ() {
		return AddressZ;
	}

	public void setAddressZ(String addressZ) {
		AddressZ = addressZ;
	}

	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}

	public String getAddressD() {
		return AddressD;
	}

	public void setAddressD(String addressD) {
		AddressD = addressD;
	}

	public String getLegal() {
		return Legal;
	}

	public void setLegal(String legal) {
		Legal = legal;
	}

	public AlarmPositionEntity getPosition() {
		return Position;
	}

	public void setPosition(AlarmPositionEntity position) {
		Position = position;
	}

	public String getBriefCase() {
		return BriefCase;
	}

	public void setBriefCase(String briefCase) {
		BriefCase = briefCase;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public ArrayList<Accessory> getAccessory() {
		return Accessory;
	}

	public void setAccessory(ArrayList<Accessory> accessory) {
		Accessory = accessory;
	}

	public Loaction getLoaction() {
		return loaction;
	}

	public void setLoaction(Loaction loaction) {
		this.loaction = loaction;
	}

	public PoliceHeadEntity getApprovalPeople() {
		return ApprovalPeople;
	}

	public void setApprovalPeople(PoliceHeadEntity approvalPeople) {
		ApprovalPeople = approvalPeople;
	}

	public AlarmOpinionEntity getOpinion() {
		return opinion;
	}

	public void setOpinion(AlarmOpinionEntity opinion) {
		this.opinion = opinion;
	}

	@Override
	public String toString() {
		return "AlarmingEntity [id=" + id + ", AlarmId=" + AlarmId
				+ ", StateId=" + StateId + ", ReceivedTime=" + ReceivedTime
				+ ", ArrivalsTime=" + ArrivalsTime + ", Name=" + Name
				+ ", Age=" + Age + ", Sex=" + Sex + ", Education=" + Education
				+ ", Workplace=" + Workplace + ", AddressZ=" + AddressZ
				+ ", CompanyName=" + CompanyName + ", AddressD=" + AddressD
				+ ", Legal=" + Legal + ", Position=" + Position
				+ ", BriefCase=" + BriefCase + ", opinion=" + opinion
				+ ", Remark=" + Remark + ", Accessory=" + Accessory
				+ ", loaction=" + loaction + ", ApprovalPeople="
				+ ApprovalPeople + "]";
	}
	
}
