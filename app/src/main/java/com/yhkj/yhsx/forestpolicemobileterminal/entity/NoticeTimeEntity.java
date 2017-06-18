package com.yhkj.yhsx.forestpolicemobileterminal.entity;

/**
 * 延时通知实体类
 * 
 * @author xingyimin
 * 
 */
public class NoticeTimeEntity {

	private int id;// ID
	private int newsId;// 消息ID
	private int newsType;// 消息类型
	private String newsTime;// 消息通知时间
	private String newsContent;

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public int getNewsType() {
		return newsType;
	}

	public void setNewsType(int newsType) {
		this.newsType = newsType;
	}

	public String getNewsTime() {
		return newsTime;
	}

	public void setNewsTime(String newsTime) {
		this.newsTime = newsTime;
	}

}
