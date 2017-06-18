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
 * 山情
 * */
public class MountConditionsEntent implements Serializable {
	private int id;
	private int catalogid;// 功能Id
	private int userID;// 用户id
	private int depID;// 部门id
	private ArrayList<Accessory> Accessory;// 附件
	private int fileType;// 文件类型
	private String thumbnail;// 图片名称
	private String thumbnailtype;// 图片类型
	private String sqadministrativearea;// 行政区域总面积(公顷)
	private String sqtopographygeomorphology;// 地形地貌
	private String sqltitude;// 海拔
	private String sqlatitudelongitude;// 经纬度
	private String sqcompletely;// 截然地区(四至)
	private String sqriverlakeposition;// 河流湖泊位置
	private String sqtrafficroaddistribution;// 交通道路分布
	private String sqregionalspecialty;// 地区特产
	private String sqMineraldistribution;// 矿产资源分布
	private String createtime;// 创建时间
	private int mount_id;//自增长id
	private String village_name;//村名

	/*************** get/set ****************************/
	
	public int getId() {
		return id;
	}

	public String getVillage_name() {
		return village_name;
	}

	public void setVillage_name(String village_name) {
		this.village_name = village_name;
	}

	public int getMount_id() {
		return mount_id;
	}

	public void setMount_id(int mount_id) {
		this.mount_id = mount_id;
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

	public String getSqadministrativearea() {
		return sqadministrativearea;
	}

	public void setSqadministrativearea(String sqadministrativearea) {
		this.sqadministrativearea = sqadministrativearea;
	}

	public String getSqtopographygeomorphology() {
		return sqtopographygeomorphology;
	}

	public void setSqtopographygeomorphology(String sqtopographygeomorphology) {
		this.sqtopographygeomorphology = sqtopographygeomorphology;
	}

	public String getSqltitude() {
		return sqltitude;
	}

	public void setSqltitude(String sqltitude) {
		this.sqltitude = sqltitude;
	}

	public String getSqlatitudelongitude() {
		return sqlatitudelongitude;
	}

	public void setSqlatitudelongitude(String sqlatitudelongitude) {
		this.sqlatitudelongitude = sqlatitudelongitude;
	}

	public String getSqcompletely() {
		return sqcompletely;
	}

	public void setSqcompletely(String sqcompletely) {
		this.sqcompletely = sqcompletely;
	}

	public String getSqriverlakeposition() {
		return sqriverlakeposition;
	}

	public void setSqriverlakeposition(String sqriverlakeposition) {
		this.sqriverlakeposition = sqriverlakeposition;
	}

	public String getSqtrafficroaddistribution() {
		return sqtrafficroaddistribution;
	}

	public void setSqtrafficroaddistribution(String sqtrafficroaddistribution) {
		this.sqtrafficroaddistribution = sqtrafficroaddistribution;
	}

	public String getSqregionalspecialty() {
		return sqregionalspecialty;
	}

	public void setSqregionalspecialty(String sqregionalspecialty) {
		this.sqregionalspecialty = sqregionalspecialty;
	}

	public String getSqMineraldistribution() {
		return sqMineraldistribution;
	}

	public void setSqMineraldistribution(String sqMineraldistribution) {
		this.sqMineraldistribution = sqMineraldistribution;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	/**
	 * 解析json封装成集合
	 * 
	 * */

	public static List<MountConditionsEntent> getListData(String t,
			Context context) {
		List<MountConditionsEntent> mceList = new ArrayList<MountConditionsEntent>();
		try {
			JSONObject tObject = new JSONObject(t);
			MountConditionsEntent mcs;
			if (tObject.getInt("Error") == 0) {
				if (!tObject.isNull("ShanQingList")) {
					JSONArray shanQingArray = tObject
							.getJSONArray("ShanQingList");
					JSONObject chilObject = null;
					for (int i = 0; i < shanQingArray.length(); i++) {
						mcs = new MountConditionsEntent();
						chilObject = shanQingArray.getJSONObject(i);
						if (!chilObject.isNull("id")) {
							mcs.setId(chilObject.getInt("id"));
						}
						if(!chilObject.isNull("villagename")){
							mcs.setVillage_name(chilObject.getString("villagename"));
						}
						if (!chilObject.isNull("catalogid")) {
							mcs.setCatalogid(chilObject.getInt("catalogid"));
						}

						if (!chilObject.isNull("userID")) {
							mcs.setUserID(chilObject.getInt("userID"));
						}

						if (!chilObject.isNull("depID")) {
							mcs.setDepID(chilObject.getInt("depID"));
						}

						if (!chilObject.isNull("fileType")) {
							mcs.setFileType(chilObject.getInt("fileType"));
						}

						if (!chilObject.isNull("thumbnail")) {
							mcs.setThumbnail(chilObject.getString("thumbnail"));
						}

						if (!chilObject.isNull("thumbnailtype")) {
							mcs.setThumbnailtype(chilObject
									.getString("thumbnailtype"));
						}

						if (!chilObject.isNull("sqadministrativearea")) {
							mcs.setSqadministrativearea(chilObject
									.getString("sqadministrativearea"));
						}

						if (!chilObject.isNull("sqtopographygeomorphology")) {
							mcs.setSqtopographygeomorphology(chilObject
									.getString("sqtopographygeomorphology"));
						}

						if (!chilObject.isNull("sqltitude")) {
							mcs.setSqltitude(chilObject.getString("sqltitude"));
						}

						if (!chilObject.isNull("sqlatitudelongitude")) {
							mcs.setSqlatitudelongitude(chilObject
									.getString("sqlatitudelongitude"));
						}

						if (!chilObject.isNull("sqcompletely")) {
							mcs.setSqcompletely(chilObject
									.getString("sqcompletely"));
						}

						if (!chilObject.isNull("sqriverlakeposition")) {
							mcs.setSqriverlakeposition(chilObject
									.getString("sqriverlakeposition"));
						}

						if (!chilObject.isNull("sqtrafficroaddistribution")) {
							mcs.setSqtrafficroaddistribution(chilObject
									.getString("sqtrafficroaddistribution"));
						}

						if (!chilObject.isNull("sqregionalspecialty")) {
							mcs.setSqregionalspecialty(chilObject
									.getString("sqregionalspecialty"));
						}

						if (!chilObject.isNull("sqMineraldistribution")) {
							mcs.setSqMineraldistribution(chilObject
									.getString("sqMineraldistribution"));
						}

						if (!chilObject.isNull("createtime")) {
							mcs.setCreatetime(chilObject
									.getString("createtime"));
						}

						if (!chilObject.isNull("affix")
								&& !chilObject.getString("affix").equals("")) {
							ArrayList<Accessory> accessorys = new ArrayList<Accessory>();
							JSONArray jaaccessorys = new JSONArray(
									chilObject.getString("affix"));
							for (int j = 0; j < jaaccessorys.length(); j++) {
								JSONObject joAttachment = jaaccessorys
										.getJSONObject(j);
								Accessory accessory = new Accessory();

								if (!joAttachment.isNull("url")) {
									String url = joAttachment.getString("url");
									accessory.setAccessoryPath(url.replace(
											"../../", ActivityUtils
													.getServerWebApi(context)));
								}
								accessorys.add(accessory);
							}
							mcs.setAccessory(accessorys);
						}
						mceList.add(mcs);
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mceList;

	}

	/*public static AjaxParams getParams(MountConditionsEntent mce, int useId) {
		AjaxParams params = new AjaxParams();
		params.put("userID", useId + "");
		params.put("catalogid", MountConditions.KEY_MOUNT_CONDITIONS_ID + "");
		params.put("id", mce.getId() + "");
		params.put("depID", MountConditions.DEPID + "");
		params.put("fileType", mce.getFileType() + "");
		//村名
		if(mce.getVillage_name()!=null&&!mce.getVillage_name().equals("")){
			params.put("villagename", mce.getVillage_name());
		}
		
		// 图片名称
		if (mce.getThumbnail() != null && !mce.getThumbnail().equals("")) {
			params.put("thumbnail", mce.getThumbnail());
		}
		// 图片类型
		if (mce.getThumbnailtype() != null
				&& !mce.getThumbnailtype().equals("")) {
			params.put("thumbnailtype", mce.getThumbnailtype());
		}
		// 行政区域总面积(公顷)
		if (mce.getSqadministrativearea() != null
				&& !mce.getSqadministrativearea().equals("")) {
			params.put("sqadministrativearea", mce.getSqadministrativearea());
		}
		// 地形地貌
		if (mce.getSqtopographygeomorphology() != null
				&& !mce.getSqtopographygeomorphology().equals("")) {
			params.put("sqtopographygeomorphology", mce.getSqtopographygeomorphology());
		}
		// 海拔
		if (mce.getSqltitude() != null && !mce.getSqltitude().equals("")) {
			params.put("sqltitude", mce.getSqltitude());
		}
		// 经纬度
		if (mce.getSqlatitudelongitude() != null
				&& !mce.getSqlatitudelongitude().equals("")) {
			params.put("sqlatitudelongitude", mce.getSqlatitudelongitude());
		}
		// 截然地区(四至)
		if (mce.getSqcompletely() != null && !mce.getSqcompletely().equals("")) {
			params.put("sqcompletely", mce.getSqcompletely());
		}
		// 河流湖泊位置
		if (mce.getSqriverlakeposition() != null
				&& !mce.getSqriverlakeposition().equals("")) {
			params.put("sqriverlakeposition", mce.getSqriverlakeposition());
		}
		// 交通道路分布
		if (mce.getSqtrafficroaddistribution() != null
				&& !mce.getSqtrafficroaddistribution().equals("")) {
			params.put("sqtrafficroaddistribution",
					mce.getSqtrafficroaddistribution());
		}
		// 地区特产
		if (mce.getSqregionalspecialty() != null
				&& !mce.getSqregionalspecialty().equals("")) {
			params.put("sqregionalspecialty", mce.getSqregionalspecialty());
		}
		// 矿产资源分布
		if (mce.getSqMineraldistribution() != null
				&& !mce.getSqMineraldistribution().equals("")) {
			params.put("sqMineraldistribution", mce.getSqMineraldistribution());
		}

		// 附件
		if (mce.getAccessory() != null && mce.getAccessory().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < mce.getAccessory().size(); i++) {
				JSONObject json = AttachmentDao.getJson(mce.getAccessory().get(
						i));
				ja.put(json);
			}
			params.put("flist", ja.toString());
		}
		return params;
	}*/
}
