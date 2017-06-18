/**
 * 
 */
package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.util.ArrayList;

/**
 * 重点单位、重点部位基本情况
 * 
 * @author liupeng
 * 
 */
public class KeyUnits {
	private int kuId; // 序号
	private String unitName; // 重点单位（部位）名称
	private String address; // 地　　　址
	private String responsibleName; // 责任人姓名
	private String responsiblePosition; // 责任人职位
	private String responsiblePhone; // 责任人联系电话
	private String mtcotSituation; // “三防”措施落实情况
	private ArrayList<Accessory> kuAccessory; // 附件
	private String kuLegend; // 备注

	public int getKuId() {
		return kuId;
	}

	public void setKuId(int kuId) {
		this.kuId = kuId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getResponsibleName() {
		return responsibleName;
	}

	public void setResponsibleName(String responsibleName) {
		this.responsibleName = responsibleName;
	}

	public String getResponsiblePosition() {
		return responsiblePosition;
	}

	public void setResponsiblePosition(String responsiblePosition) {
		this.responsiblePosition = responsiblePosition;
	}

	public String getResponsiblePhone() {
		return responsiblePhone;
	}

	public void setResponsiblePhone(String responsiblePhone) {
		this.responsiblePhone = responsiblePhone;
	}

	public String getMtcotSituation() {
		return mtcotSituation;
	}

	public void setMtcotSituation(String mtcotSituation) {
		this.mtcotSituation = mtcotSituation;
	}

	public ArrayList<Accessory> getKuAccessory() {
		return kuAccessory;
	}

	public void setKuAccessory(ArrayList<Accessory> kuAccessory) {
		this.kuAccessory = kuAccessory;
	}

	public String getKuLegend() {
		return kuLegend;
	}

	public void setKuLegend(String kuLegend) {
		this.kuLegend = kuLegend;
	}

}
