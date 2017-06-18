package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.util.ArrayList;

/**
 * 瞭望塔登记
 * 
 * @author liupeng
 */
public class WatchtowerRegistration {

	private int wrId; // ID 自增长
	private String tbsName; // 基站名称
	private String specificLocation; // 具体位置
	private String coordinates; // 经纬度
	private String altitude; // 海拔高度
	private String wtwSpecifications; // 瞭望塔规格 砖混结构
	private String transmission; // 传输方式
	private String btTime; // 建塔时间
	private ArrayList<Accessory> wrAccessory; // 附件
	private String wrLegend; // 备注
	private Loaction loaction;

	public Loaction getLoaction() {
		return loaction;
	}

	public void setLoaction(Loaction loaction) {
		this.loaction = loaction;
	}

	public int getWrId() {
		return wrId;
	}

	public void setWrId(int wrId) {
		this.wrId = wrId;
	}

	public String getTbsName() {
		return tbsName;
	}

	public void setTbsName(String tbsName) {
		this.tbsName = tbsName;
	}

	public String getSpecificLocation() {
		return specificLocation;
	}

	public void setSpecificLocation(String specificLocation) {
		this.specificLocation = specificLocation;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getWtwSpecifications() {
		return wtwSpecifications;
	}

	public void setWtwSpecifications(String wtwSpecifications) {
		this.wtwSpecifications = wtwSpecifications;
	}

	public String getTransmission() {
		return transmission;
	}

	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}

	public String getBtTime() {
		return btTime;
	}

	public void setBtTime(String btTime) {
		this.btTime = btTime;
	}

	public ArrayList<Accessory> getWrAccessory() {
		return wrAccessory;
	}

	public void setWrAccessory(ArrayList<Accessory> wrAccessory) {
		this.wrAccessory = wrAccessory;
	}

	public String getWrLegend() {
		return wrLegend;
	}

	public void setWrLegend(String wrLegend) {
		this.wrLegend = wrLegend;
	}

}
