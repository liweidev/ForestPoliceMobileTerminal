package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.util.ArrayList;

/**
 * 发生火情登记
 * 
 * @author xingyimin
 * 
 */
public class RegistrationAreaFireEntity {
	private int rafId;// 自增长 ID
	private String rtsfTime;// 到达火情现场时间
	private String efTime;// 扑灭明火时间
	private String tfoPlace;// 发生火情地点
	private String burnedArea;// 过火面积
	private ArrayList<Accessory> rafAccessory;// 附件
	private String note;// 备注
	private Loaction location;// 定位

	public int getRafId() {
		return rafId;
	}

	public void setRafId(int rafId) {
		this.rafId = rafId;
	}

	public String getRtsfTime() {
		return rtsfTime;
	}

	public void setRtsfTime(String rtsfTime) {
		this.rtsfTime = rtsfTime;
	}

	public String getEfTime() {
		return efTime;
	}

	public void setEfTime(String efTime) {
		this.efTime = efTime;
	}

	public String getTfoPlace() {
		return tfoPlace;
	}

	public void setTfoPlace(String tfoPlace) {
		this.tfoPlace = tfoPlace;
	}

	public String getBurnedArea() {
		return burnedArea;
	}

	public void setBurnedArea(String burnedArea) {
		this.burnedArea = burnedArea;
	}

	public ArrayList<Accessory> getRafAccessory() {
		return rafAccessory;
	}

	public void setRafAccessory(ArrayList<Accessory> rafAccessory) {
		this.rafAccessory = rafAccessory;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Loaction getLocation() {
		return location;
	}

	public void setLocation(Loaction location) {
		this.location = location;
	}

}
