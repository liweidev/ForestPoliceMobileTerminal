package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.util.ArrayList;

/**
 * 林区巡逻工作情况
 * 
 * @author liupeng
 * 
 */
public class ForestPatrolWorkEntity {

	private int fpwId; // ID 自增长

	private String startTime; // 开始时间

	private String endTime; // 结束时间

	private String patrolPolice; // 巡逻民警

	private String patrolRoute; // 巡逻路线

	private String patrolChronicle; // 巡逻记事

	private String analysisOfDisposal; // 研判分析处置情况

	private String shiftLeadershipOpinion; // 带班领导意见

	private ArrayList<Accessory> fpwAccessorys; // 附件

	private String unit;// 单位

	private Loaction loaction;// 定位

	private String fpwLegend; // 备注

	public Loaction getLoaction() {
		return loaction;
	}

	public void setLoaction(Loaction loaction) {
		this.loaction = loaction;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getFpwId() {
		return fpwId;
	}

	public void setFpwId(int fpwId) {
		this.fpwId = fpwId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPatrolPolice() {
		return patrolPolice;
	}

	public void setPatrolPolice(String patrolPolice) {
		this.patrolPolice = patrolPolice;
	}

	public String getPatrolRoute() {
		return patrolRoute;
	}

	public void setPatrolRoute(String patrolRoute) {
		this.patrolRoute = patrolRoute;
	}

	public String getPatrolChronicle() {
		return patrolChronicle;
	}

	public void setPatrolChronicle(String patrolChronicle) {
		this.patrolChronicle = patrolChronicle;
	}

	public String getAnalysisOfDisposal() {
		return analysisOfDisposal;
	}

	public void setAnalysisOfDisposal(String analysisOfDisposal) {
		this.analysisOfDisposal = analysisOfDisposal;
	}

	public String getShiftLeadershipOpinion() {
		return shiftLeadershipOpinion;
	}

	public void setShiftLeadershipOpinion(String shiftLeadershipOpinion) {
		this.shiftLeadershipOpinion = shiftLeadershipOpinion;
	}

	public ArrayList<Accessory> getFpwAccessorys() {
		return fpwAccessorys;
	}

	public void setFpwAccessorys(ArrayList<Accessory> fpwAccessorys) {
		this.fpwAccessorys = fpwAccessorys;
	}

	public String getFpwLegend() {
		return fpwLegend;
	}

	public void setFpwLegend(String fpwLegend) {
		this.fpwLegend = fpwLegend;
	}

}
