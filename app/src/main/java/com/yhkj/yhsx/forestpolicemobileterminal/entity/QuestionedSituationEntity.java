package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 盘问情况
 * 
 * @author xingyimin
 * 
 */
public class QuestionedSituationEntity implements Serializable {

	private int qsid;
	private int questionedSituationID;// ID
	private String year;// 年度
	private String name;// 被盘问人姓名
	private int gender;// 性别
	private String birthday;// 出生年月
	private String domicilePlace;// 户口所在地
	private String unitOrAddress;// 单位或地址
	private String askWhy;// 盘问原因
	private String askBeginTIme;// 继续盘问开始时间
	private String askEndTIme;// 继续盘问结束时间
	private String sponsor;// 主办人
	private String overtime;// 延长时间
	private String allow;// 上级机关批准人
	private String handingInformation;// 处理情况
	private ArrayList<Accessory> accessoryList;// 附件
	private String remark;// 备注

	public int getQsid() {
		return qsid;
	}

	public void setQsid(int qsid) {
		this.qsid = qsid;
	}

	public int getQuestionedSituationID() {
		return questionedSituationID;
	}

	public void setQuestionedSituationID(int questionedSituationID) {
		this.questionedSituationID = questionedSituationID;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getDomicilePlace() {
		return domicilePlace;
	}

	public void setDomicilePlace(String domicilePlace) {
		this.domicilePlace = domicilePlace;
	}

	public String getUnitOrAddress() {
		return unitOrAddress;
	}

	public void setUnitOrAddress(String unitOrAddress) {
		this.unitOrAddress = unitOrAddress;
	}

	public String getAskWhy() {
		return askWhy;
	}

	public void setAskWhy(String askWhy) {
		this.askWhy = askWhy;
	}

	public String getAskBeginTIme() {
		return askBeginTIme;
	}

	public void setAskBeginTIme(String askBeginTIme) {
		this.askBeginTIme = askBeginTIme;
	}

	public String getAskEndTIme() {
		return askEndTIme;
	}

	public void setAskEndTIme(String askEndTIme) {
		this.askEndTIme = askEndTIme;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getOvertime() {
		return overtime;
	}

	public void setOvertime(String overtime) {
		this.overtime = overtime;
	}

	public String getAllow() {
		return allow;
	}

	public void setAllow(String allow) {
		this.allow = allow;
	}

	public String getHandingInformation() {
		return handingInformation;
	}

	public void setHandingInformation(String handingInformation) {
		this.handingInformation = handingInformation;
	}

	public ArrayList<Accessory> getAccessoryList() {
		return accessoryList;
	}

	public void setAccessoryList(ArrayList<Accessory> accessoryList) {
		this.accessoryList = accessoryList;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
