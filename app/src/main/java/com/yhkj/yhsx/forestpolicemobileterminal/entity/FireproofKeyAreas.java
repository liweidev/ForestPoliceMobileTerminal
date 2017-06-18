/**
 * 
 */
package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 防火重点地区
 * 
 * @author liupeng
 */
public class FireproofKeyAreas implements Serializable {

	private int fkaId; // ID 自增长
	private int serverId;
	private int year;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	private String areasName; // 名称  
	private String siting; // 坐落地点

	private String personInCharge; // 负责人
	private String headOfSecurity; // 安全负责人
	private String employees; // 职工人数
	private String contact; // 联系方式
	private String area; // 面积
	private String traffic; // 交通情况
	private String latLonAltitude; // 经纬度及海拔
	private String scopeAndNature; // 经营范围及性质
	private String fourCases; // 四至情况
	private String fsSurrounding; // 周边林情
	private String theReasonTube; // 列管原因
	private ArrayList<Accessory> fwaAccessory; // 附件
	private Loaction location;

	public Loaction getLocation() {
		return location;
	}

	public void setLocation(Loaction location) {
		this.location = location;
	}

	private String fkaLegend; // 备注

	public int getFkaId() {
		return fkaId;
	}

	public void setFkaId(int fkaId) {
		this.fkaId = fkaId;
	}

	public String getAreasName() {
		return areasName;
	}

	public void setAreasName(String areasName) {
		this.areasName = areasName;
	}

	public String getSiting() {
		return siting;
	}

	public void setSiting(String siting) {
		this.siting = siting;
	}

	public String getPersonInCharge() {
		return personInCharge;
	}

	public void setPersonInCharge(String personInCharge) {
		this.personInCharge = personInCharge;
	}

	public String getHeadOfSecurity() {
		return headOfSecurity;
	}

	public void setHeadOfSecurity(String headOfSecurity) {
		this.headOfSecurity = headOfSecurity;
	}

	public String getEmployees() {
		return employees;
	}

	public void setEmployees(String employees) {
		this.employees = employees;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getLatLonAltitude() {
		return latLonAltitude;
	}

	public void setLatLonAltitude(String latLonAltitude) {
		this.latLonAltitude = latLonAltitude;
	}

	public String getScopeAndNature() {
		return scopeAndNature;
	}

	public void setScopeAndNature(String scopeAndNature) {
		this.scopeAndNature = scopeAndNature;
	}

	public String getFourCases() {
		return fourCases;
	}

	public void setFourCases(String fourCases) {
		this.fourCases = fourCases;
	}

	public String getFsSurrounding() {
		return fsSurrounding;
	}

	public void setFsSurrounding(String fsSurrounding) {
		this.fsSurrounding = fsSurrounding;
	}

	public String getTheReasonTube() {
		return theReasonTube;
	}

	public void setTheReasonTube(String theReasonTube) {
		this.theReasonTube = theReasonTube;
	}

	public ArrayList<Accessory> getFwaAccessory() {
		return fwaAccessory;
	}

	public void setFwaAccessory(ArrayList<Accessory> fwaAccessory) {
		this.fwaAccessory = fwaAccessory;
	}

	public String getFkaLegend() {
		return fkaLegend;
	}

	public void setFkaLegend(String fkaLegend) {
		this.fkaLegend = fkaLegend;
	}

}
