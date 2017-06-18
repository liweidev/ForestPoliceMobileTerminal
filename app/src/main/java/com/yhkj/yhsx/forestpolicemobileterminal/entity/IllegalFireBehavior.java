package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.util.ArrayList;

/**
 * 违法用火行为人登记
 * 
 * @author liupeng
 * 
 */
public class IllegalFireBehavior {

	private int ifbId; // ID 自增长
	private String violator; // 违法行为人
	private String violatorIDCard; // 身份证号
	private String violatorAdd; // 现住址
	private String luofTime; // 违法用火时间
	private String processingResults; // 处理结果
	private String penaltiesTime; // 处罚时间
	private String organizedPolice; // 主办民警
	private Loaction loaction;
	public Loaction getLoaction() {
		return loaction;
	}

	public void setLoaction(Loaction loaction) {
		this.loaction = loaction;
	}

	private ArrayList<Accessory> ifbAccessory; // 附件
	private String ifbLegend; // 备注

	public int getIfbId() {
		return ifbId;
	}

	public void setIfbId(int ifbId) {
		this.ifbId = ifbId;
	}

	public String getViolator() {
		return violator;
	}

	public void setViolator(String violator) {
		this.violator = violator;
	}

	public String getViolatorIDCard() {
		return violatorIDCard;
	}

	public void setViolatorIDCard(String violatorIDCard) {
		this.violatorIDCard = violatorIDCard;
	}

	public String getViolatorAdd() {
		return violatorAdd;
	}

	public void setViolatorAdd(String violatorAdd) {
		this.violatorAdd = violatorAdd;
	}

	public String getLuofTime() {
		return luofTime;
	}

	public void setLuofTime(String luofTime) {
		this.luofTime = luofTime;
	}

	public String getProcessingResults() {
		return processingResults;
	}

	public void setProcessingResults(String processingResults) {
		this.processingResults = processingResults;
	}

	public String getPenaltiesTime() {
		return penaltiesTime;
	}

	public void setPenaltiesTime(String penaltiesTime) {
		this.penaltiesTime = penaltiesTime;
	}

	public String getOrganizedPolice() {
		return organizedPolice;
	}

	public void setOrganizedPolice(String organizedPolice) {
		this.organizedPolice = organizedPolice;
	}

	public ArrayList<Accessory> getIfbAccessory() {
		return ifbAccessory;
	}

	public void setIfbAccessory(ArrayList<Accessory> ifbAccessory) {
		this.ifbAccessory = ifbAccessory;
	}

	public String getIfbLegend() {
		return ifbLegend;
	}

	public void setIfbLegend(String ifbLegend) {
		this.ifbLegend = ifbLegend;
	}

}
