package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 情报信息 GPS
 * 
 * @author zhengtiantian
 * 
 */
public class ItelligenceEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int itelligenceID;
	private String acquisitionTime;// 收集时间
	private String acquisitionPolice;// 收集民警
	private String informationSource;// 信息来源
	private String informationType;// 信息类别
	private String informationAbstract;// 信息摘要
	private String reportedUnit;// 上报单位
	private String reportedTime;// 上报时间
	private String feedback;// 反馈意见
	private int serverId;
	private ArrayList<Accessory> itAttachment;// 附件

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public ArrayList<Accessory> getItAttachment() {
		return itAttachment;
	}

	public void setItAttachment(ArrayList<Accessory> itAttachment) {
		this.itAttachment = itAttachment;
	}

	public int getItelligenceID() {
		return itelligenceID;
	}

	public void setItelligenceID(int itelligenceID) {
		this.itelligenceID = itelligenceID;
	}

	public String getAcquisitionTime() {
		return acquisitionTime;
	}

	public void setAcquisitionTime(String acquisitionTime) {
		this.acquisitionTime = acquisitionTime;
	}

	public String getAcquisitionPolice() {
		return acquisitionPolice;
	}

	public void setAcquisitionPolice(String acquisitionPolice) {
		this.acquisitionPolice = acquisitionPolice;
	}

	public String getInformationSource() {
		return informationSource;
	}

	public void setInformationSource(String informationSource) {
		this.informationSource = informationSource;
	}

	public String getInformationType() {
		return informationType;
	}

	public void setInformationType(String informationType) {
		this.informationType = informationType;
	}

	public String getInformationAbstract() {
		return informationAbstract;
	}

	public void setInformationAbstract(String informationAbstract) {
		this.informationAbstract = informationAbstract;
	}

	public String getReportedUnit() {
		return reportedUnit;
	}

	public void setReportedUnit(String reportedUnit) {
		this.reportedUnit = reportedUnit;
	}

	public String getReportedTime() {
		return reportedTime;
	}

	public void setReportedTime(String reportedTime) {
		this.reportedTime = reportedTime;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

}
