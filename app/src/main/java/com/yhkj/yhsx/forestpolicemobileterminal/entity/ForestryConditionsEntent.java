package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import android.content.Context;

import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 林情
 * */
public class ForestryConditionsEntent implements Serializable {
	private int id;
	private int catalogid;// 功能id
	private int userID;// 用户ID
	private int depID;// 部门id
	private ArrayList<Accessory> Accessory;// 附件
	private int fileType;// 文件类型
	private String thumbnail;// 图片名称
	private String thumbnailtype;// 图片类型
	private String lqwoodlandArea;// 林地面积
	private String lqforeststockvolume;// 林地蓄积力量
	private String lqmajorareasofwildlife;// 野生动物主要活动地区
	private String lqforesthighareas;// 涉林案件高发区
	private String lqwildanimalmarket;// 野生动物市场
	private String lqsituationprotection;// 生态管护员情况
	private String lqgrazingfarmers;// 放牧养殖户
	private String lqseminumber;// 半专业数量及人数
	private String lqprofessionalnumber;// 专业数量及人数
	private String lqforesttypes;// 森林的种类
	private String lqagecomposition;// 树龄组成
	private String lqwildanimalspecies;// 野生动物种类
	private String lqtreespecies;// 林木种类
	private String lqoldfamoustrees;// 古树名木
	private String lqkeycolumnmanagement;// 重点列管人员
	private String lqtypesforestl;// 林业用地的种类
	private String createtime;// 创建时间
	private int _id;//自增长id
	private String village_name;//村民

	/*************** get/set ********************/
	
	public int get_id() {
		return _id;
	}

	public String getVillage_name() {
		return village_name;
	}

	public void setVillage_name(String village_name) {
		this.village_name = village_name;
	}

	public void set_id(int _id) {
		this._id = _id;
	}
	
	public int getId() {
		return id;
	}

	public int getCatalogid() {
		return catalogid;
	}

	public void setCatalogid(int catalogid) {
		this.catalogid = catalogid;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getDepID() {
		return depID;
	}

	public void setDepID(int depID) {
		this.depID = depID;
	}

	public ArrayList<Accessory> getAccessory() {
		return Accessory;
	}

	public void setAccessory(ArrayList<Accessory> accessory) {
		Accessory = accessory;
	}

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getThumbnailtype() {
		return thumbnailtype;
	}

	public void setThumbnailtype(String thumbnailtype) {
		this.thumbnailtype = thumbnailtype;
	}

	public String getLqwoodlandArea() {
		return lqwoodlandArea;
	}

	public void setLqwoodlandArea(String lqwoodlandArea) {
		this.lqwoodlandArea = lqwoodlandArea;
	}

	public String getLqforeststockvolume() {
		return lqforeststockvolume;
	}

	public void setLqforeststockvolume(String lqforeststockvolume) {
		this.lqforeststockvolume = lqforeststockvolume;
	}

	public String getLqmajorareasofwildlife() {
		return lqmajorareasofwildlife;
	}

	public void setLqmajorareasofwildlife(String lqmajorareasofwildlife) {
		this.lqmajorareasofwildlife = lqmajorareasofwildlife;
	}

	public String getLqforesthighareas() {
		return lqforesthighareas;
	}

	public void setLqforesthighareas(String lqforesthighareas) {
		this.lqforesthighareas = lqforesthighareas;
	}

	public String getLqwildanimalmarket() {
		return lqwildanimalmarket;
	}

	public void setLqwildanimalmarket(String lqwildanimalmarket) {
		this.lqwildanimalmarket = lqwildanimalmarket;
	}

	public String getLqsituationprotection() {
		return lqsituationprotection;
	}

	public void setLqsituationprotection(String lqsituationprotection) {
		this.lqsituationprotection = lqsituationprotection;
	}

	public String getLqgrazingfarmers() {
		return lqgrazingfarmers;
	}

	public void setLqgrazingfarmers(String lqgrazingfarmers) {
		this.lqgrazingfarmers = lqgrazingfarmers;
	}

	public String getLqseminumber() {
		return lqseminumber;
	}

	public void setLqseminumber(String lqseminumber) {
		this.lqseminumber = lqseminumber;
	}

	public String getLqprofessionalnumber() {
		return lqprofessionalnumber;
	}

	public void setLqprofessionalnumber(String lqprofessionalnumber) {
		this.lqprofessionalnumber = lqprofessionalnumber;
	}

	public String getLqforesttypes() {
		return lqforesttypes;
	}

	public void setLqforesttypes(String lqforesttypes) {
		this.lqforesttypes = lqforesttypes;
	}

	public String getLqagecomposition() {
		return lqagecomposition;
	}

	public void setLqagecomposition(String lqagecomposition) {
		this.lqagecomposition = lqagecomposition;
	}

	public String getLqwildanimalspecies() {
		return lqwildanimalspecies;
	}

	public void setLqwildanimalspecies(String lqwildanimalspecies) {
		this.lqwildanimalspecies = lqwildanimalspecies;
	}

	public String getLqtreespecies() {
		return lqtreespecies;
	}

	public void setLqtreespecies(String lqtreespecies) {
		this.lqtreespecies = lqtreespecies;
	}

	public String getLqoldfamoustrees() {
		return lqoldfamoustrees;
	}

	public void setLqoldfamoustrees(String lqoldfamoustrees) {
		this.lqoldfamoustrees = lqoldfamoustrees;
	}

	public String getLqkeycolumnmanagement() {
		return lqkeycolumnmanagement;
	}

	public void setLqkeycolumnmanagement(String lqkeycolumnmanagement) {
		this.lqkeycolumnmanagement = lqkeycolumnmanagement;
	}

	public String getLqtypesforestl() {
		return lqtypesforestl;
	}

	public void setLqtypesforestl(String lqtypesforestl) {
		this.lqtypesforestl = lqtypesforestl;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	/**************** 解析json封装成集合 
	 * @return ************************/
	public static List<ForestryConditionsEntent> getListData(String t,Context context) {
		List<ForestryConditionsEntent> fceList = new ArrayList<ForestryConditionsEntent>();
		try {
			JSONObject jsonT = new JSONObject(t);
			if (!jsonT.isNull("LinQingList")) {
				JSONArray jsonArrayLinQingList = jsonT
						.getJSONArray("LinQingList");
				JSONObject childObject = null;
				ForestryConditionsEntent fce;
				for (int i = 0; i < jsonArrayLinQingList.length(); i++) {
					childObject = jsonArrayLinQingList.getJSONObject(i);
					fce = new ForestryConditionsEntent();
					if (!childObject.isNull("id")) {
						fce.setId(childObject.getInt("id"));
					}

					if(!childObject.isNull("villagename")){
						fce.setVillage_name(childObject.getString("villagename"));
					}
					
					if (!childObject.isNull("catalogid")) {
						fce.setCatalogid(childObject.getInt("catalogid"));
					}

					if (!childObject.isNull("userID")) {
						fce.setUserID(childObject.getInt("userID"));
					}

					if (!childObject.isNull("depID")) {
						fce.setDepID(childObject.getInt("depID"));
					}

					if (!childObject.isNull("fileType")) {
						fce.setFileType(childObject.getInt("fileType"));
					}

					if (!childObject.isNull("thumbnail")) {
						fce.setThumbnail(childObject.getString("thumbnail"));
					}
					
					if (!childObject.isNull("thumbnailtype")) {
						fce.setThumbnailtype(childObject.getString("thumbnailtype"));
					}
					
					if (!childObject.isNull("lqwoodlandArea")) {
						fce.setLqwoodlandArea(childObject.getString("lqwoodlandArea"));
					}
					
					if (!childObject.isNull("lqforeststockvolume")) {
						fce.setLqforeststockvolume(childObject.getString("lqforeststockvolume"));
					}
					if (!childObject.isNull("lqmajorareasofwildlife")) {
						fce.setLqmajorareasofwildlife(childObject.getString("lqmajorareasofwildlife"));
					}
					if (!childObject.isNull("lqforesthighareas")) {
						fce.setLqforesthighareas(childObject.getString("lqforesthighareas"));
					}
					if (!childObject.isNull("lqwildanimalmarket")) {
						fce.setLqwildanimalmarket(childObject.getString("lqwildanimalmarket"));
					}
					if (!childObject.isNull("lqsituationprotection")) {
						fce.setLqsituationprotection(childObject.getString("lqsituationprotection"));
					}
					if (!childObject.isNull("lqgrazingfarmers")) {
						fce.setLqgrazingfarmers(childObject.getString("lqgrazingfarmers"));
					}
					if (!childObject.isNull("lqseminumber")) {
						fce.setLqseminumber(childObject.getString("lqseminumber"));
					}
					if (!childObject.isNull("lqprofessionalnumber")) {
						fce.setLqprofessionalnumber(childObject.getString("lqprofessionalnumber"));
					}
					if (!childObject.isNull("lqforesttypes")) {
						fce.setLqforesttypes(childObject.getString("lqforesttypes"));
					}
					if (!childObject.isNull("lqagecomposition")) {
						fce.setLqagecomposition(childObject.getString("lqagecomposition"));
					}
					if (!childObject.isNull("lqwildanimalspecies")) {
						fce.setLqwildanimalspecies(childObject.getString("lqwildanimalspecies"));
					}
					if (!childObject.isNull("lqtreespecies")) {
						fce.setLqtreespecies(childObject.getString("lqtreespecies"));
					}
					
					if (!childObject.isNull("lqoldfamoustrees")) {
						fce.setLqoldfamoustrees(childObject.getString("lqoldfamoustrees"));
					}
					if (!childObject.isNull("lqkeycolumnmanagement")) {
						fce.setLqkeycolumnmanagement(childObject.getString("lqkeycolumnmanagement"));
					}
					if (!childObject.isNull("lqtypesforestl")) {
						fce.setLqtypesforestl(childObject.getString("lqtypesforestl"));
					}
					if (!childObject.isNull("createtime")) {
						fce.setCreatetime(childObject.getString("createtime"));
					}
					
					if (!childObject.isNull("affix") && !childObject.getString("affix").equals("")) {
						ArrayList<Accessory> accessorys = new ArrayList<Accessory>();
						JSONArray jaaccessorys = new JSONArray(
								childObject.getString("affix"));
						for (int j = 0; j < jaaccessorys.length(); j++) {
							JSONObject joAttachment = jaaccessorys.getJSONObject(j);
							Accessory accessory = new Accessory();

							if (!joAttachment.isNull("url")) {
								String url = joAttachment.getString("url");
								accessory.setAccessoryPath(url.replace("../../",
										ActivityUtils.getServerWebApi(context)));
							}
							accessorys.add(accessory);
						}
						fce.setAccessory(accessorys);
					}
					fceList.add(fce);
				}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fceList;
	}

	/*public static AjaxParams getParams(ForestryConditionsEntent fce, int useId) {
		AjaxParams params = new AjaxParams();
		params.put("userID", useId+"");
		params.put("catalogid", ForestryConditions.KEY_FORESTRY_CONDITIONS_ID+"");
		params.put("depID", ForestryConditions.DEPID+"");
		//ID
		params.put("id", fce.getId()+"");
		//村名
		if(fce.getVillage_name()!=null){
			params.put("villagename", fce.getVillage_name());
		}
		//林地面积
		if(fce.getLqwoodlandArea()!=null){
			params.put("lqwoodlandArea", fce.getLqwoodlandArea());
		}
		//lqforeststockvolume;// 林地蓄积力量
		if(fce.getLqforeststockvolume()!=null&&!fce.getLqforeststockvolume().equals("")){
			params.put("lqforeststockvolume", fce.getLqforeststockvolume());
		}
		//lqmajorareasofwildlife;// 野生动物主要活动地区
		if(fce.getLqmajorareasofwildlife()!=null&&!fce.getLqmajorareasofwildlife().equals("")){
			params.put("lqmajorareasofwildlife", fce.getLqmajorareasofwildlife());
		}
		//lqforesthighareas;// 涉林案件高发区
		if(fce.getLqforesthighareas()!=null&&!fce.getLqforesthighareas().equals("")){
			params.put("lqforesthighareas", fce.getLqforesthighareas());
		}
		//lqwildanimalmarket;// 野生动物市场
		if(fce.getLqwildanimalmarket()!=null&&!fce.getLqwildanimalmarket().equals("")){
			params.put("lqwildanimalmarket", fce.getLqwildanimalmarket());
		}
		// lqsituationprotection;// 生态管护员情况
		if(fce.getLqsituationprotection()!=null&&!fce.getLqsituationprotection().equals("")){
			params.put("lqsituationprotection", fce.getLqsituationprotection());
		}
		//lqgrazingfarmers;// 放牧养殖户
		if(fce.getLqgrazingfarmers()!=null&&!fce.getLqgrazingfarmers().equals("")){
			params.put("lqgrazingfarmers", fce.getLqgrazingfarmers());
		}
		//lqseminumber;// 半专业数量及人数
		if(fce.getLqseminumber()!=null&&!fce.getLqseminumber().equals("")){
			params.put("lqseminumber", fce.getLqseminumber());
		}
		//lqprofessionalnumber;// 专业数量及人数
		if(fce.getLqprofessionalnumber()!=null&&!fce.getLqprofessionalnumber().equals("")){
			params.put("lqprofessionalnumber", fce.getLqprofessionalnumber());
		}
		//lqforesttypes;// 森林的种类
		if(fce.getLqforesttypes()!=null&&!fce.getLqforesttypes().equals("")){
			params.put("lqforesttypes", fce.getLqforesttypes());
		}
		//lqagecomposition;// 树龄组成
		if(fce.getLqagecomposition()!=null&&!fce.getLqagecomposition().equals("")){
			params.put("lqagecomposition", fce.getLqagecomposition());
		}
		//lqwildanimalspecies;// 野生动物种类
		if(fce.getLqwildanimalspecies()!=null&&!fce.getLqwildanimalspecies().equals("")){
			params.put("lqwildanimalspecies", fce.getLqwildanimalspecies());
		}
		//lqtreespecies;// 林木种类
		if(fce.getLqtreespecies()!=null&&!fce.getLqtreespecies().equals("")){
			params.put("lqtreespecies", fce.getLqtreespecies());
		}
		//lqoldfamoustrees;// 古树名木
		if(fce.getLqoldfamoustrees()!=null&&!fce.getLqoldfamoustrees().equals("")){
			params.put("lqoldfamoustrees", fce.getLqoldfamoustrees());
		}
		//lqkeycolumnmanagement;// 重点列管人员
		if(fce.getLqkeycolumnmanagement()!=null&&!fce.getLqkeycolumnmanagement().equals("")){
			params.put("lqkeycolumnmanagement", fce.getLqkeycolumnmanagement());
		}
		//lqtypesforestl;// 林业用地的种类
		if(fce.getLqtypesforestl()!=null&&!fce.getLqtypesforestl().equals("")){
			params.put("lqtypesforestl", fce.getLqtypesforestl());
		}
		
			params.put("fileType", fce.getFileType()+"");
		
		//附件
		if (fce.getAccessory() != null && fce.getAccessory().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < fce.getAccessory().size(); i++) {
				JSONObject json = AttachmentDao.getJson(fce.getAccessory()
						.get(i));
				ja.put(json);
			}
			params.put("flist", ja.toString());
		}
			*//*ActivityUtils.print(params.toString(), Environment.getExternalStorageDirectory()
				.getPath()+"/xiugai");*//*
		Log.e("paramsLin", params.toString());
		return params;
	}*/


}
