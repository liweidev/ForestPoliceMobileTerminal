package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import android.content.Context;

import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SheQingEntity implements Serializable {
	private int id;
	private int catalogid;// 功能id
	private int userID;// 用户id
	private int depID;// 部门id
	private ArrayList<Accessory> Accessory;// 附件
	private int fileType;// 文件类型
	private String thumbnail;// 图片名称
	private String thumbnailtype;// 图片类型
	private String shqlandcontract;// 土地承包情况
	private String shqvillagecontact;// 村领导干部及联系方式
	private String shqstaysillypatient;// 呆、傻精神病人情况
	private String shqmajorindustry;// 主要餐饮业情况
	private String shqtombcustoms;// 清明节扫墓时间及风俗
	private String shqtrafficdistribution;// 交通分布情况
	private String shqtransientpopulation;// 暂住人口数
	private String shqresidentpopulation;// 居民人口数
	private String shqresidentvillage;// 行政村常住人口数
	private String shqculturalsites;// 文物古迹
	private String shqmajorsources;// 主要产业及经济来源
	private String shqcharacteristicproperty;// 特色物产
	private String createtime;// 创建时间
	private int she_id;//自增长id
	private String village_name;//村名

	/************ get/set **************/
	
	public int getId() {
		return id;
	}

	public String getVillage_name() {
		return village_name;
	}

	public void setVillage_name(String village_name) {
		this.village_name = village_name;
	}

	public int getShe_id() {
		return she_id;
	}

	public void setShe_id(int she_id) {
		this.she_id = she_id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCatalogid() {
		return catalogid;
	}

	public void setCatalogid(int catalogid) {
		this.catalogid = catalogid;
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

	public String getShqlandcontract() {
		return shqlandcontract;
	}

	public void setShqlandcontract(String shqlandcontract) {
		this.shqlandcontract = shqlandcontract;
	}

	public String getShqvillagecontact() {
		return shqvillagecontact;
	}

	public void setShqvillagecontact(String shqvillagecontact) {
		this.shqvillagecontact = shqvillagecontact;
	}

	public String getShqstaysillypatient() {
		return shqstaysillypatient;
	}

	public void setShqstaysillypatient(String shqstaysillypatient) {
		this.shqstaysillypatient = shqstaysillypatient;
	}

	public String getShqmajorindustry() {
		return shqmajorindustry;
	}

	public void setShqmajorindustry(String shqmajorindustry) {
		this.shqmajorindustry = shqmajorindustry;
	}

	public String getShqtombcustoms() {
		return shqtombcustoms;
	}

	public void setShqtombcustoms(String shqtombcustoms) {
		this.shqtombcustoms = shqtombcustoms;
	}

	public String getShqtrafficdistribution() {
		return shqtrafficdistribution;
	}

	public void setShqtrafficdistribution(String shqtrafficdistribution) {
		this.shqtrafficdistribution = shqtrafficdistribution;
	}

	public String getShqtransientpopulation() {
		return shqtransientpopulation;
	}

	public void setShqtransientpopulation(String shqtransientpopulation) {
		this.shqtransientpopulation = shqtransientpopulation;
	}

	public String getShqresidentpopulation() {
		return shqresidentpopulation;
	}

	public void setShqresidentpopulation(String shqresidentpopulation) {
		this.shqresidentpopulation = shqresidentpopulation;
	}

	public String getShqresidentvillage() {
		return shqresidentvillage;
	}

	public void setShqresidentvillage(String shqresidentvillage) {
		this.shqresidentvillage = shqresidentvillage;
	}

	public String getShqculturalsites() {
		return shqculturalsites;
	}

	public void setShqculturalsites(String shqculturalsites) {
		this.shqculturalsites = shqculturalsites;
	}

	public String getShqmajorsources() {
		return shqmajorsources;
	}

	public void setShqmajorsources(String shqmajorsources) {
		this.shqmajorsources = shqmajorsources;
	}

	public String getShqcharacteristicproperty() {
		return shqcharacteristicproperty;
	}

	public void setShqcharacteristicproperty(String shqcharacteristicproperty) {
		this.shqcharacteristicproperty = shqcharacteristicproperty;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	/**
	 * 解析json封装成集合
	 * */
	public static List<SheQingEntity> getListData(String t, Context context) {
		List<SheQingEntity> sqeList = new ArrayList<SheQingEntity>();
		try {
			JSONObject tObject = new JSONObject(t);
			SheQingEntity sqe;
			if (!tObject.isNull("SheQingList")) {
				JSONArray SheQingArray = tObject.getJSONArray("SheQingList");
				JSONObject childObject;
				for (int i = 0; i < SheQingArray.length(); i++) {
					childObject = SheQingArray.getJSONObject(i);
					sqe = new SheQingEntity();
					if (!childObject.isNull("id")) {
						sqe.setId(childObject.getInt("id"));
					}

					if(!childObject.isNull("villagename")){
						sqe.setVillage_name(childObject.getString("villagename"));
					}
					if (!childObject.isNull("catalogid")) {
						sqe.setCatalogid(childObject.getInt("catalogid"));
					}

					if (!childObject.isNull("userID")) {
						sqe.setUserID(childObject.getInt("userID"));
					}

					if (!childObject.isNull("depID")) {
						sqe.setDepID(childObject.getInt("depID"));
					}

					if (!childObject.isNull("fileType")) {
						sqe.setFileType(childObject.getInt("fileType"));
					}
				
					// 图片名称
					if (!childObject.isNull("thumbnail")) {
						sqe.setThumbnail(childObject.getString("thumbnail"));
					}
					// 图片类型
					if (!childObject.isNull("thumbnailtype")) {
						sqe.setThumbnailtype(childObject
								.getString("thumbnailtype"));
					}
					// 土地承包情况
					if (!childObject.isNull("shqlandcontract")) {
						sqe.setShqlandcontract(childObject
								.getString("shqlandcontract"));
					}
					// 村领导干部及联系方式
					if (!childObject.isNull("shqvillagecontact")) {
						sqe.setShqvillagecontact(childObject
								.getString("shqvillagecontact"));
					}
					// 呆、傻精神病人情况
					if (!childObject.isNull("shqstaysillypatient")) {
						sqe.setShqstaysillypatient(childObject
								.getString("shqstaysillypatient"));
					}
					// 主要餐饮业情况
					if (!childObject.isNull("shqmajorindustry")) {
						sqe.setShqmajorindustry(childObject
								.getString("shqmajorindustry"));
					}
					// 清明节扫墓时间及风俗
					if (!childObject.isNull("shqtombcustoms")) {
						sqe.setShqtombcustoms(childObject
								.getString("shqtombcustoms"));
					}
					// 交通分布情况
					if (!childObject.isNull("shqtrafficdistribution")) {
						sqe.setShqtrafficdistribution(childObject
								.getString("shqtrafficdistribution"));
					}
					// 暂住人口数
					if (!childObject.isNull("shqtransientpopulation")) {
						sqe.setShqtransientpopulation(childObject
								.getString("shqtransientpopulation"));
					}
					// 居民人口数
					if (!childObject.isNull("shqresidentpopulation")) {
						sqe.setShqresidentpopulation(childObject
								.getString("shqresidentpopulation"));
					}
					// 行政村常住人口数
					if (!childObject.isNull("shqresidentvillage")) {
						sqe.setShqresidentvillage(childObject
								.getString("shqresidentvillage"));
					}
					// 文物古迹
					if (!childObject.isNull("shqculturalsites")) {
						sqe.setShqculturalsites(childObject
								.getString("shqculturalsites"));
					}
					// 主要产业及经济来源
					if (!childObject.isNull("shqmajorsources")) {
						sqe.setShqmajorsources(childObject
								.getString("shqmajorsources"));
					}
					// 特色物产
					if (!childObject.isNull("shqcharacteristicproperty")) {
						sqe.setShqcharacteristicproperty(childObject
								.getString("shqcharacteristicproperty"));
					}

					if (!childObject.isNull("createtime")) {
						sqe.setCreatetime(childObject.getString("createtime"));
					}

					if (!childObject.isNull("affix")
							&& !childObject.getString("affix").equals("")) {
						ArrayList<Accessory> accessorys = new ArrayList<Accessory>();
						JSONArray jaaccessorys = new JSONArray(
								childObject.getString("affix"));
						for (int j = 0; j < jaaccessorys.length(); j++) {
							JSONObject joAttachment = jaaccessorys
									.getJSONObject(j);
							Accessory accessory = new Accessory();

							if (!joAttachment.isNull("url")) {
								String url = joAttachment.getString("url");
								accessory
										.setAccessoryPath(url
												.replace(
														"../../",
														ActivityUtils
																.getServerWebApi(context)));
							}
							accessorys.add(accessory);
						}
						sqe.setAccessory(accessorys);
					}
					sqeList.add(sqe);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sqeList;
	}

	/*public static AjaxParams getParams(SheQingEntity sqe, int useId) {
		AjaxParams params = new AjaxParams();
		params.put("id", sqe.getId() + "");
		params.put("catalogid", SheConditions.KEY_SHE_CONDITIONS_ID + "");
		params.put("userID", useId + "");
		params.put("depID", SheConditions.DEPID + "");

		//村名
		if(sqe.getVillage_name()!=null&&!sqe.getVillage_name().equals("")){
			params.put("villagename", sqe.getVillage_name());
		}
		
		// 图片名称
		if (sqe.getThumbnail() != null && !sqe.getThumbnail().equals("")) {
			params.put("thumbnail", sqe.getThumbnail());
		}
		// 图片类型
		if (sqe.getThumbnailtype() != null
				&& !sqe.getThumbnailtype().equals("")) {
			params.put("thumbnailtype", sqe.getThumbnailtype());
		}
		// 土地承包情况
		if (sqe.getShqlandcontract() != null
				&& !sqe.getShqlandcontract().equals("")) {
			params.put("shqlandcontract", sqe.getShqlandcontract());
		}
		// 村领导干部及联系方式
		if (sqe.getShqvillagecontact() != null
				&& !sqe.getShqvillagecontact().equals("")) {
			params.put("shqvillagecontact", sqe.getShqvillagecontact());
		}
		// 呆、傻精神病人情况
		if (sqe.getShqstaysillypatient() != null
				&& !sqe.getShqstaysillypatient().equals("")) {
			params.put("shqstaysillypatient", sqe.getShqstaysillypatient());
		}
		// 主要餐饮业情况
		if (sqe.getShqmajorindustry() != null
				&& !sqe.getShqmajorindustry().equals("")) {
			params.put("shqmajorindustry", sqe.getShqmajorindustry());
		}
		// 清明节扫墓时间及风俗
		if (sqe.getShqtombcustoms() != null
				&& !sqe.getShqtombcustoms().equals("")) {
			params.put("shqtombcustoms", sqe.getShqtombcustoms());
		}
		// 交通分布情况
		if (sqe.getShqtrafficdistribution() != null
				&& !sqe.getShqtrafficdistribution().equals("")) {
			params.put("shqtrafficdistribution",
					sqe.getShqtrafficdistribution());
		}
		// 暂住人口数
		if (sqe.getShqtransientpopulation() != null
				&& !sqe.getShqtransientpopulation().equals("")) {
			params.put("shqtransientpopulation",
					sqe.getShqtransientpopulation());
		}
		// 居民人口数
		if (sqe.getShqresidentpopulation() != null
				&& !sqe.getShqresidentpopulation().equals("")) {
			params.put("shqresidentpopulation", sqe.getShqresidentpopulation());
		}
		// 行政村常住人口数
		if (sqe.getShqresidentvillage() != null
				&& !sqe.getShqresidentvillage().equals("")) {
			params.put("shqresidentvillage", sqe.getShqresidentvillage());
		}
		// 文物古迹
		if (sqe.getShqculturalsites() != null
				&& !sqe.getShqculturalsites().equals("")) {
			params.put("shqculturalsites", sqe.getShqculturalsites());
		}
		// 主要产业及经济来源
		if (sqe.getShqmajorsources() != null
				&& !sqe.getShqmajorsources().equals("")) {
			params.put("shqmajorsources", sqe.getShqmajorsources());
		}
		// 特色物产
		if(sqe.getShqcharacteristicproperty()!=null&&!sqe.getShqcharacteristicproperty().equals("")){
			params.put("shqcharacteristicproperty", sqe.getShqcharacteristicproperty());
		}
		
		// 附件
		if (sqe.getAccessory() != null && sqe.getAccessory().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < sqe.getAccessory().size(); i++) {
				JSONObject json = AttachmentDao.getJson(sqe.getAccessory().get(
						i));
				ja.put(json);
			}
			params.put("flist", ja.toString());
		}
		return params;
	}*/
}
