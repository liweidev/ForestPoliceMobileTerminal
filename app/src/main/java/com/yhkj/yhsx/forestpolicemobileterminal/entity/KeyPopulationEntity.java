package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * 辖区重点人口情况登记表
 * 
 * @author zhengtiantian
 * 
 */
public class KeyPopulationEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int keyId;// 自增长ID
	private String time;// 时间
	private String name;// 姓名
	private String nickName;// 绰号
	private int sex;// 性别
	private String birthday;// 生日
	private String address;// 住址或服务处住所
	private String manageTime;// 列管时间
	private String repealTime;// 撤管时间
	private String manageCause;// 列管原因
	private String fileCode;// 档案编号
	private ArrayList<Accessory> keyAccessory;// 附件
	private Loaction loaction;// 定位
	private String keyNote;// 备注

	public int getKeyId() {
		return keyId;
	}

	public void setKeyId(int keyId) {
		this.keyId = keyId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getManageTime() {
		return manageTime;
	}

	public void setManageTime(String manageTime) {
		this.manageTime = manageTime;
	}

	public String getRepealTime() {
		return repealTime;
	}

	public void setRepealTime(String repealTime) {
		this.repealTime = repealTime;
	}

	public String getManageCause() {
		return manageCause;
	}

	public void setManageCause(String manageCause) {
		this.manageCause = manageCause;
	}

	public String getFileCode() {
		return fileCode;
	}

	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}

	public ArrayList<Accessory> getKeyAccessory() {
		return keyAccessory;
	}

	public void setKeyAccessory(ArrayList<Accessory> keyAccessory) {
		this.keyAccessory = keyAccessory;
	}

	public Loaction getLoaction() {
		return loaction;
	}

	public void setLoaction(Loaction loaction) {
		this.loaction = loaction;
	}

	public String getKeyNote() {
		return keyNote;
	}

	public void setKeyNote(String keyNote) {
		this.keyNote = keyNote;
	}

}
