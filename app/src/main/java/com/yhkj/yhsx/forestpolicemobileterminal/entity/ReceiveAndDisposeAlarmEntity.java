package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 接警和处警工作登记
 * 
 * @author ZhengTiantian
 */
public class ReceiveAndDisposeAlarmEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int radId;// id 自增长
	private ArrayList<Accessory> radAccessory; // 附件
	private String radLegend; // 备注
	private Loaction location;
	private String receivePerson;// 接警人员
	private String receiveTiem;// 接警时间
	private String receiveModel;// 接警方式
	private String alarmNation;// 民族

	private String receiveName;// 报警人姓名
	private int receiveAge;// 报警人年龄
	private int receiveSex;// 报警人性别
	private String receiveContact;// 报警人联系方式
	private String receiveIdCard;// 报警人身份证号码
	private String receiveAddress;// 报警人暂住地址
	private String receiveContent;// 报警内容

	private String opinionLeader;// 领导意见

	private String orderTime;// 接到指令时间
	private String disposeTime;// 处警时间
	private String arriveTime;// 到达现场时间
	private String disposePerson;// 处警人员

	private String partsName;// 违法人姓名
	private int partsAge;// 违法人年龄
	private int partsSex;// 违法人性别
	private String partsCulture;// 违法人文化程度

	private String partsUnit;// 违法人工作单位
	private String homeAddress;// 家庭住址
	private String unitName;// 单位名称
	private String unitAddress;// 单位地址
	private String partsPost;// 职务
	private String lawPerson;// 法定代表人

	private String firstSituation;// 初查情况
	private String inendTime;// 初查结束时间
	private String policeIdea;// 处警民警意见
	private String writeTime;// 处警民警意见填写时间
	private String alarmIdea;// 处警情况及意见
	private String responsibleIdea;// 处警部门负责人意见
	private String responsibleTime;// 部门负责人意见填写时间

	private String alarmResult;// 处理结果
	private String acceptUnit;// 接受单位
	private String acceptTime;// 接受时间
	private String criminalCase;// 刑事案件立案编号
	private String administrativeCase;// 行政案件立案编号
	private String instructContent;// 森林公安负责人批示
	private String instructTime;// 负责人批示时间

	public String getPartsCulture() {
		return partsCulture;
	}

	public void setPartsCulture(String partsCulture) {
		this.partsCulture = partsCulture;
	}

	public String getAlarmNation() {
		return alarmNation;
	}

	public void setAlarmNation(String alarmNation) {
		this.alarmNation = alarmNation;
	}

	public int getRadId() {
		return radId;
	}

	public void setRadId(int radId) {
		this.radId = radId;
	}

	public ArrayList<Accessory> getRadAccessory() {
		return radAccessory;
	}

	public void setRadAccessory(ArrayList<Accessory> radAccessory) {
		this.radAccessory = radAccessory;
	}

	public String getRadLegend() {
		return radLegend;
	}

	public void setRadLegend(String radLegend) {
		this.radLegend = radLegend;
	}

	public Loaction getLocation() {
		return location;
	}

	public void setLocation(Loaction location) {
		this.location = location;
	}

	public String getReceivePerson() {
		return receivePerson;
	}

	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}

	public String getReceiveTiem() {
		return receiveTiem;
	}

	public void setReceiveTiem(String receiveTiem) {
		this.receiveTiem = receiveTiem;
	}

	public String getReceiveModel() {
		return receiveModel;
	}

	public void setReceiveModel(String receiveModel) {
		this.receiveModel = receiveModel;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public int getReceiveAge() {
		return receiveAge;
	}

	public void setReceiveAge(int receiveAge) {
		this.receiveAge = receiveAge;
	}

	public int getReceiveSex() {
		return receiveSex;
	}

	public void setReceiveSex(int receiveSex) {
		this.receiveSex = receiveSex;
	}

	public String getReceiveContact() {
		return receiveContact;
	}

	public void setReceiveContact(String receiveContact) {
		this.receiveContact = receiveContact;
	}

	public String getReceiveIdCard() {
		return receiveIdCard;
	}

	public void setReceiveIdCard(String receiveIdCard) {
		this.receiveIdCard = receiveIdCard;
	}

	public String getReceiveAddress() {
		return receiveAddress;
	}

	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}

	public String getReceiveContent() {
		return receiveContent;
	}

	public void setReceiveContent(String receiveContent) {
		this.receiveContent = receiveContent;
	}

	public String getOpinionLeader() {
		return opinionLeader;
	}

	public void setOpinionLeader(String opinionLeader) {
		this.opinionLeader = opinionLeader;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getDisposeTime() {
		return disposeTime;
	}

	public void setDisposeTime(String disposeTime) {
		this.disposeTime = disposeTime;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getDisposePerson() {
		return disposePerson;
	}

	public void setDisposePerson(String disposePerson) {
		this.disposePerson = disposePerson;
	}

	public String getPartsName() {
		return partsName;
	}

	public void setPartsName(String partsName) {
		this.partsName = partsName;
	}

	public int getPartsAge() {
		return partsAge;
	}

	public void setPartsAge(int partsAge) {
		this.partsAge = partsAge;
	}

	public int getPartsSex() {
		return partsSex;
	}

	public void setPartsSex(int partsSex) {
		this.partsSex = partsSex;
	}

	public String getPartsUnit() {
		return partsUnit;
	}

	public void setPartsUnit(String partsUnit) {
		this.partsUnit = partsUnit;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
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

	public String getPartsPost() {
		return partsPost;
	}

	public void setPartsPost(String partsPost) {
		this.partsPost = partsPost;
	}

	public String getLawPerson() {
		return lawPerson;
	}

	public void setLawPerson(String lawPerson) {
		this.lawPerson = lawPerson;
	}

	public String getFirstSituation() {
		return firstSituation;
	}

	public void setFirstSituation(String firstSituation) {
		this.firstSituation = firstSituation;
	}

	public String getInendTime() {
		return inendTime;
	}

	public void setInendTime(String inendTime) {
		this.inendTime = inendTime;
	}

	public String getPoliceIdea() {
		return policeIdea;
	}

	public void setPoliceIdea(String policeIdea) {
		this.policeIdea = policeIdea;
	}

	public String getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(String writeTime) {
		this.writeTime = writeTime;
	}

	public String getAlarmIdea() {
		return alarmIdea;
	}

	public void setAlarmIdea(String alarmIdea) {
		this.alarmIdea = alarmIdea;
	}

	public String getResponsibleIdea() {
		return responsibleIdea;
	}

	public void setResponsibleIdea(String responsibleIdea) {
		this.responsibleIdea = responsibleIdea;
	}

	public String getResponsibleTime() {
		return responsibleTime;
	}

	public void setResponsibleTime(String responsibleTime) {
		this.responsibleTime = responsibleTime;
	}

	public String getAlarmResult() {
		return alarmResult;
	}

	public void setAlarmResult(String alarmResult) {
		this.alarmResult = alarmResult;
	}

	public String getAcceptUnit() {
		return acceptUnit;
	}

	public void setAcceptUnit(String acceptUnit) {
		this.acceptUnit = acceptUnit;
	}

	public String getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getCriminalCase() {
		return criminalCase;
	}

	public void setCriminalCase(String criminalCase) {
		this.criminalCase = criminalCase;
	}

	public String getAdministrativeCase() {
		return administrativeCase;
	}

	public void setAdministrativeCase(String administrativeCase) {
		this.administrativeCase = administrativeCase;
	}

	public String getInstructContent() {
		return instructContent;
	}

	public void setInstructContent(String instructContent) {
		this.instructContent = instructContent;
	}

	public String getInstructTime() {
		return instructTime;
	}

	public void setInstructTime(String instructTime) {
		this.instructTime = instructTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
