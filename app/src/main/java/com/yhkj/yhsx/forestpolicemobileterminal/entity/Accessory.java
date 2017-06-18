package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;

/**
 * 附件实体类
 * 
 * @author liupeng
 * 
 */

public class Accessory implements Serializable {

	private int aId; // ID标识，自增长列

	private String accessoryPath; // 附件路径地址

	private String fileType; // 附件类型
	private String slPath;

	public String getSlPath() {
		return slPath;
	}

	public void setSlPath(String slPath) {
		this.slPath = slPath;
	}

	private int tableId;
	private int rowId;

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public int getaId() {
		return aId;
	}

	public void setaId(int aId) {
		this.aId = aId;
	}

	public String getAccessoryPath() {
		return accessoryPath;
	}

	public void setAccessoryPath(String accessoryPath) {
		this.accessoryPath = accessoryPath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Override
	public String toString() {
		return "Accessory [aId=" + aId + ", accessoryPath=" + accessoryPath
				+ ", fileType=" + fileType + ", slPath=" + slPath
				+ ", tableId=" + tableId + ", rowId=" + rowId + "]";
	}
	
}
