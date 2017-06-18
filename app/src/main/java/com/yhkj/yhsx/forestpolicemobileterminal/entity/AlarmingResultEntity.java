package com.yhkj.yhsx.forestpolicemobileterminal.entity;

/**
 * 处警审批
 * 
 * @author xingyimin
 * 
 */
public class AlarmingResultEntity {
	private int resultId;
	private int AlarmID;//报警信息ID
	private int StateID;//审批状态ID
	private String Remark;// 理由Remark--处警审批
	private String excuse;// 理由---公安处审批
	private String result;// 处理结果
	private String acceptanceUnit;// 接受单位
	private String acceptanceTime;// 接受时间
	private String criminalCaseNum;// 刑事案件立案编号
	private String administrativeCaseNum;// 行政案件立案编号
	private int ApprovalPeopleID;// 审批人ID
	private int peoplePoliceId;
	
	
	
	
	public int getPeoplePoliceId() {
		return peoplePoliceId;
	}

	public void setPeoplePoliceId(int peoplePoliceId) {
		this.peoplePoliceId = peoplePoliceId;
	}

	public int getResultId() {
		return resultId;
	}

	public void setResultId(int resultId) {
		this.resultId = resultId;
	}

	public int getAlarmID() {
		return AlarmID;
	}

	public void setAlarmID(int alarmID) {
		AlarmID = alarmID;
	}

	public int getStateID() {
		return StateID;
	}

	public void setStateID(int stateID) {
		StateID = stateID;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getAcceptanceUnit() {
		return acceptanceUnit;
	}

	public void setAcceptanceUnit(String acceptanceUnit) {
		this.acceptanceUnit = acceptanceUnit;
	}

	public String getAcceptanceTime() {
		return acceptanceTime;
	}

	public void setAcceptanceTime(String acceptanceTime) {
		this.acceptanceTime = acceptanceTime;
	}

	public String getCriminalCaseNum() {
		return criminalCaseNum;
	}

	public void setCriminalCaseNum(String criminalCaseNum) {
		this.criminalCaseNum = criminalCaseNum;
	}

	public String getAdministrativeCaseNum() {
		return administrativeCaseNum;
	}

	public void setAdministrativeCaseNum(String administrativeCaseNum) {
		this.administrativeCaseNum = administrativeCaseNum;
	}

	public String getExcuse() {
		return excuse;
	}

	public void setExcuse(String excuse) {
		this.excuse = excuse;
	}

	public int getApprovalPeopleID() {
		return ApprovalPeopleID;
	}

	public void setApprovalPeopleID(int approvalPeopleID) {
		ApprovalPeopleID = approvalPeopleID;
	}

	@Override
	public String toString() {
		return "AlarmingResultEntity [resultId=" + resultId + ", AlarmID="
				+ AlarmID + ", StateID=" + StateID + ", Remark=" + Remark
				+ ", excuse=" + excuse + ", result=" + result
				+ ", acceptanceUnit=" + acceptanceUnit + ", acceptanceTime="
				+ acceptanceTime + ", criminalCaseNum=" + criminalCaseNum
				+ ", administrativeCaseNum=" + administrativeCaseNum
				+ ", ApprovalPeopleID=" + ApprovalPeopleID + "]";
	}
	
}
