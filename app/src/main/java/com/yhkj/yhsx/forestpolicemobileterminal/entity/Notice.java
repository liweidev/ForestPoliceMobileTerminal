package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 新闻公告通知实体类
 * 
 * @author xingyimin
 * 
 */
public class Notice implements Serializable {

	private int noId;// id
	private String title;// 标题
	private String text;// 内容
	private ArrayList<Accessory> noAccessory;// 附件
	private int flagRead;// 状态

	public int getFlagRead() {
		return flagRead;
	}

	public void setFlagRead(int flagRead) {
		this.flagRead = flagRead;
	}

	public int getNoId() {
		return noId;
	}

	public void setNoId(int noId) {
		this.noId = noId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setNoAccessory(ArrayList<Accessory> list) {
		// TODO Auto-generated method stub
		this.noAccessory = list;
	}

	public ArrayList<Accessory> getNoAccessory() {
		return noAccessory;
	}
}
