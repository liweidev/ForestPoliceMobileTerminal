/**
 * 
 */
package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 法律法规
 * 
 * @author liupeng
 * 
 */
public class LawsAndRegulations implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int Id;
	private String title;
	private String content;
	private String imagefile;
	private ArrayList<Accessory> lawAccessory; // 附件

	public ArrayList<Accessory> getLawAccessory() {
		return lawAccessory;
	}

	public void setLawAccessory(ArrayList<Accessory> lawAccessory) {
		this.lawAccessory = lawAccessory;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImagefile() {
		return imagefile;
	}

	public void setImagefile(String imagefile) {
		this.imagefile = imagefile;
	}

}
