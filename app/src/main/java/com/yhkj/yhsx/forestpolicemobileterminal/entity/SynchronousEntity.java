package com.yhkj.yhsx.forestpolicemobileterminal.entity;


/**
 * 同步实体类
 * 
 * @author xingyimin
 * 
 */
public class SynchronousEntity {

	private int id;// Id
	private String title;// 标题
	private int count;// 数量
	private Object object;// 内容集合
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object objectList) {
		this.object = objectList;
	}

	
}
