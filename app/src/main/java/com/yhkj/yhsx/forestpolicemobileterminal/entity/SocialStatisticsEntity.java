package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 社会情况统计
 * 
 * @author zhengtiantian
 */
public class SocialStatisticsEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int socialStatisticsID;
	private Loaction locationId;// 定位ID
	private ArrayList<Accessory> ssAttachment;
	private String unit;// 单位名称
	private String totalArea;// 总面积
	private String familyCount;// 户数合计
	private String agricultureFamily;// 农业户
	private String nonAgriculturalFamily;// 非农业户
	private String protectFamily;// 重点保护户
	private String temporaryFamily;// 外来临时户
	private String populationCount;// 人口总数
	private String arriculturePopulation;// 农业人口
	private String nonArriculturealPopulation;// 非农业人口
	private String temporaryPopulation;// 流动人口
	private String emphasisUnitCount;// 重点单位合计
	private String woodProcessingUnit;// 木材加工
	private String woodPurchaseUnit;// 木材收购
	private String quarryingUnit;// 采石
	private String collieryUnit;// 煤矿
	private String flaystoneUnit;// 石板
	private String wildAnimalUnit;// 野生动物经营
	private String travelUnit;// 旅游区
	private String contractor;// 承包人
	private String principal;// 学校校长
	private String grazingPersonnel;// 放牧人员
	private String employer;// 雇主
	private String graveMaster;// 坟主
	private String moronismManager;// 呆傻监护人
	private int serverId;

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public int getSocialStatisticsID() {
		return socialStatisticsID;
	}

	public void setSocialStatisticsID(int socialStatisticsID) {
		this.socialStatisticsID = socialStatisticsID;
	}

	public Loaction getLocationId() {
		return locationId;
	}

	public void setLocationId(Loaction locationId) {
		this.locationId = locationId;
	}

	public ArrayList<Accessory> getssAttachment() {
		return ssAttachment;
	}

	public void setsAttachment(ArrayList<Accessory> ssAttachment) {
		this.ssAttachment = ssAttachment;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(String totalArea) {
		this.totalArea = totalArea;
	}

	public String getFamilyCount() {
		return familyCount;
	}

	public void setFamilyCount(String familyCount) {
		this.familyCount = familyCount;
	}

	public String getAgricultureFamily() {
		return agricultureFamily;
	}

	public void setAgricultureFamily(String agricultureFamily) {
		this.agricultureFamily = agricultureFamily;
	}

	public String getNonAgriculturalFamily() {
		return nonAgriculturalFamily;
	}

	public void setNonAgriculturalFamily(String nonAgriculturalFamily) {
		this.nonAgriculturalFamily = nonAgriculturalFamily;
	}

	public String getProtectFamily() {
		return protectFamily;
	}

	public void setProtectFamily(String protectFamily) {
		this.protectFamily = protectFamily;
	}

	public String getTemporaryFamily() {
		return temporaryFamily;
	}

	public void setTemporaryFamily(String temporaryFamily) {
		this.temporaryFamily = temporaryFamily;
	}

	public String getPopulationCount() {
		return populationCount;
	}

	public void setPopulationCount(String populationCount) {
		this.populationCount = populationCount;
	}

	public String getArriculturePopulation() {
		return arriculturePopulation;
	}

	public void setArriculturePopulation(String arriculturePopulation) {
		this.arriculturePopulation = arriculturePopulation;
	}

	public String getNonArriculturealPopulation() {
		return nonArriculturealPopulation;
	}

	public void setNonArriculturealPopulation(String nonArriculturealPopulation) {
		this.nonArriculturealPopulation = nonArriculturealPopulation;
	}

	public String getTemporaryPopulation() {
		return temporaryPopulation;
	}

	public void setTemporaryPopulation(String temporaryPopulation) {
		this.temporaryPopulation = temporaryPopulation;
	}

	public String getEmphasisUnitCount() {
		return emphasisUnitCount;
	}

	public void setEmphasisUnitCount(String emphasisUnitCount) {
		this.emphasisUnitCount = emphasisUnitCount;
	}

	public String getWoodProcessingUnit() {
		return woodProcessingUnit;
	}

	public void setWoodProcessingUnit(String woodProcessingUnit) {
		this.woodProcessingUnit = woodProcessingUnit;
	}

	public String getWoodPurchaseUnit() {
		return woodPurchaseUnit;
	}

	public void setWoodPurchaseUnit(String woodPurchaseUnit) {
		this.woodPurchaseUnit = woodPurchaseUnit;
	}

	public String getQuarryingUnit() {
		return quarryingUnit;
	}

	public void setQuarryingUnit(String quarryingUnit) {
		this.quarryingUnit = quarryingUnit;
	}

	public String getCollieryUnit() {
		return collieryUnit;
	}

	public void setCollieryUnit(String collieryUnit) {
		this.collieryUnit = collieryUnit;
	}

	public String getFlaystoneUnit() {
		return flaystoneUnit;
	}

	public void setFlaystoneUnit(String flaystoneUnit) {
		this.flaystoneUnit = flaystoneUnit;
	}

	public String getWildAnimalUnit() {
		return wildAnimalUnit;
	}

	public void setWildAnimalUnit(String wildAnimalUnit) {
		this.wildAnimalUnit = wildAnimalUnit;
	}

	public String getTravelUnit() {
		return travelUnit;
	}

	public void setTravelUnit(String travelUnit) {
		this.travelUnit = travelUnit;
	}

	public String getContractor() {
		return contractor;
	}

	public void setContractor(String contractor) {
		this.contractor = contractor;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getGrazingPersonnel() {
		return grazingPersonnel;
	}

	public void setGrazingPersonnel(String grazingPersonnel) {
		this.grazingPersonnel = grazingPersonnel;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getGraveMaster() {
		return graveMaster;
	}

	public void setGraveMaster(String graveMaster) {
		this.graveMaster = graveMaster;
	}

	public String getMoronismManager() {
		return moronismManager;
	}

	public void setMoronismManager(String moronismManager) {
		this.moronismManager = moronismManager;
	}

}
