package com.yhkj.yhsx.forestpolicemobileterminal.entity;

/**
 * 选择项
 * 
 * @author liupeng
 */
public class OptionEntity {

	private int serverId;
	private String createTime;
	private int ctrlValue;
	private String dictName;
	private int dictOrder;
	private int dictValue;
	private int formMainId;
	private String relationDictList;
	private String remark;

	public OptionEntity() {
		// TODO Auto-generated constructor stub
	}

	public OptionEntity(int dictValue, String dictName) {
		// TODO Auto-generated constructor stub
		this.dictValue = dictValue;
		this.dictName = dictName;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getCtrlValue() {
		return ctrlValue;
	}

	public void setCtrlValue(int ctrlValue) {
		this.ctrlValue = ctrlValue;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public int getDictOrder() {
		return dictOrder;
	}

	public void setDictOrder(int dictOrder) {
		this.dictOrder = dictOrder;
	}

	public int getDictValue() {
		return dictValue;
	}

	public void setDictValue(int dictValue) {
		this.dictValue = dictValue;
	}

	public int getFormMainId() {
		return formMainId;
	}

	public void setFormMainId(int formMainId) {
		this.formMainId = formMainId;
	}

	public String getRelationDictList() {
		return relationDictList;
	}

	public void setRelationDictList(String relationDictList) {
		this.relationDictList = relationDictList;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
