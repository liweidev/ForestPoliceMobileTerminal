package com.yhkj.yhsx.forestpolicemobileterminal.entity;

public class AlarmSpinnerEntity {
	private int id;//自增长id
	private int alarmWayId;//报警方式
	private int nationId;//民族
	private int parts_culture_id;//文化程度
	private int parts_post_id;//职务
	private int treatmentAdviceID;//处理意见
	private int hostPolice_id;//主办民警
	private int coPolice_id;//协办民警
	private int state_id;//审批状态
	private String alarmWayName;//报警方式
	private String nationName;//民族
	private int ApprovalPeopleID;//审批领导ID
	private String leader;//审批领导
	private String parts_culture;//文化程度
	private String parts_post;//职务
	private String treatmentAdvice;//处理意见
	private String stateName;//审批状态
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAlarmWayId() {
		return alarmWayId;
	}
	public void setAlarmWayId(int alarmWayId) {
		this.alarmWayId = alarmWayId;
	}
	public int getNationId() {
		return nationId;
	}
	public void setNationId(int nationId) {
		this.nationId = nationId;
	}
	public int getParts_culture_id() {
		return parts_culture_id;
	}
	public void setParts_culture_id(int parts_culture_id) {
		this.parts_culture_id = parts_culture_id;
	}
	public int getParts_post_id() {
		return parts_post_id;
	}
	public void setParts_post_id(int parts_post_id) {
		this.parts_post_id = parts_post_id;
	}
	public int getTreatmentAdviceID() {
		return treatmentAdviceID;
	}
	public void setTreatmentAdviceID(int treatmentAdviceID) {
		this.treatmentAdviceID = treatmentAdviceID;
	}
	public int getHostPolice_id() {
		return hostPolice_id;
	}
	public void setHostPolice_id(int hostPolice_id) {
		this.hostPolice_id = hostPolice_id;
	}
	public int getCoPolice_id() {
		return coPolice_id;
	}
	public void setCoPolice_id(int coPolice_id) {
		this.coPolice_id = coPolice_id;
	}
	public int getState_id() {
		return state_id;
	}
	public void setState_id(int state_id) {
		this.state_id = state_id;
	}
	public String getAlarmWayName() {
		return alarmWayName;
	}
	public void setAlarmWayName(String alarmWayName) {
		this.alarmWayName = alarmWayName;
	}
	public String getNationName() {
		return nationName;
	}
	public void setNationName(String nationName) {
		this.nationName = nationName;
	}
	public int getApprovalPeopleID() {
		return ApprovalPeopleID;
	}
	public void setApprovalPeopleID(int approvalPeopleID) {
		ApprovalPeopleID = approvalPeopleID;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public String getParts_culture() {
		return parts_culture;
	}
	public void setParts_culture(String parts_culture) {
		this.parts_culture = parts_culture;
	}
	public String getParts_post() {
		return parts_post;
	}
	public void setParts_post(String parts_post) {
		this.parts_post = parts_post;
	}
	public String getTreatmentAdvice() {
		return treatmentAdvice;
	}
	public void setTreatmentAdvice(String treatmentAdvice) {
		this.treatmentAdvice = treatmentAdvice;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	@Override
	public String toString() {
		return "AlarmSpinnerEntity [id=" + id + ", alarmWayId=" + alarmWayId
				+ ", nationId=" + nationId + ", parts_culture_id="
				+ parts_culture_id + ", parts_post_id=" + parts_post_id
				+ ", treatmentAdviceID=" + treatmentAdviceID
				+ ", hostPolice_id=" + hostPolice_id + ", coPolice_id="
				+ coPolice_id + ", state_id=" + state_id + ", alarmWayName="
				+ alarmWayName + ", nationName=" + nationName
				+ ", ApprovalPeopleID=" + ApprovalPeopleID + ", leader="
				+ leader + ", parts_culture=" + parts_culture + ", parts_post="
				+ parts_post + ", treatmentAdvice=" + treatmentAdvice
				+ ", stateName=" + stateName + "]";
	}
	
	
	
}
