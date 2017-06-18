package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.util.ArrayList;

/**
 * 辖区暂住人口
 * 
 * @author liupeng
 */

public class TemporaryResidentPopulation {

	private int trpId; // ID 自增长列

	private String time;// 时间

	private String name; // 姓名

	private int sex; // 性别

	private String birthday; // 出生日期

	private String temporaryAddress; // 暂住地址

	private String registeredAddress; // 户口所在地

	private String industry; // 从事行业

	private String idCardNumber; // 身份证号码

	private String contacts; // 联系方式

	private String arrivalTime; // 到达时间

	private String departureTime; // 离开时间

	private ArrayList<Accessory> trpAccessorys; // 附件

	private String trpLegend; // 备注

	private Loaction loaction;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Loaction getLoaction() {
		return loaction;
	}

	public void setLoaction(Loaction loaction) {
		this.loaction = loaction;
	}

	public int getTrpId() {
		return trpId;
	}

	public void setTrpId(int trpId) {
		this.trpId = trpId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getTemporaryAddress() {
		return temporaryAddress;
	}

	public void setTemporaryAddress(String temporaryAddress) {
		this.temporaryAddress = temporaryAddress;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public ArrayList<Accessory> getTrpAccessorys() {
		return trpAccessorys;
	}

	public void setTrpAccessorys(ArrayList<Accessory> trpAccessorys) {
		this.trpAccessorys = trpAccessorys;
	}

	public String getTrpLegend() {
		return trpLegend;
	}

	public void setTrpLegend(String trpLegend) {
		this.trpLegend = trpLegend;
	}

}
