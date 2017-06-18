package db;

import android.content.Context;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.WarningEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 报警信息
 * 
 * @author zhengtiantian
 * 
 */
public class WarningDao {

	private Context context;
	private static WarningDao wDao;

	public WarningDao(Context ct) {
		this.context = ct;
	}

	public static WarningDao init(Context context) {
		if (wDao == null) {
			wDao = new WarningDao(context);
		}
		return wDao;
	}

	/**
	 * 根据json获取对象集合
	 * 
	 * @param jsonResult
	 * @return
	 * @throws JSONException
	 */
	public List<WarningEntity> getObject(JSONArray jsonResult)
			throws JSONException {
		List<WarningEntity> warningList = null;
		if (jsonResult == null) {
			return warningList;
		} else {
			warningList = new ArrayList<WarningEntity>();
			for (int i = 0; i < jsonResult.length(); i++) {
				JSONObject object = jsonResult.getJSONObject(i);
				WarningEntity warning = new WarningEntity();
				if (!object.isNull("note")) {
					warning.setNote(object.getString("note"));
				}
				if (!object.isNull("createTime")) {
					warning.setCreateTime(object.getString("createTime"));
				}
				if (!object.isNull("UserName")) {
					warning.setWarningPeople(object.getString("UserName"));
				}
				JSONObject jsonLocation = object.getJSONObject("location_id");
				if (jsonLocation.isNull("latitude")
						|| jsonLocation.isNull("longitude")
						|| jsonLocation.getString("latitude").equals("")
						|| jsonLocation.getString("longitude").equals("")) {
				} else {
					Loaction location = new Loaction();
					location.setTIME(jsonLocation.getString("Time"));
					location.setLATITUDE(Double.parseDouble(jsonLocation
							.getString("latitude")));
					location.setLONGITUDE(Double.parseDouble(jsonLocation
							.getString("longitude")));
					location.setELEVATION(Double.parseDouble(jsonLocation
							.getString("elevation")));
					warning.setLocation(location);
				}
				if (!object.isNull("affix") && !object.getString("affix").equals("")) {
					JSONArray jsonAccessory = new JSONArray(
							object.getString("affix"));
					List<Accessory> accessoryList = new ArrayList<Accessory>();
					for (int j = 0; j < jsonAccessory.length(); j++) {
						JSONObject objectAccessory = jsonAccessory
								.getJSONObject(j);
						Accessory accessory = new Accessory();
						accessory
								.setFileType(objectAccessory.getString("type"));
						accessory.setAccessoryPath(objectAccessory.getString(
								"url").replace("../../",
								ActivityUtils.getServerWebApi(context)));
						if (!object.isNull("fileAllPath")) {
							accessory
									.setSlPath(object.getString("fileAllPath"));
						}
						accessoryList.add(accessory);
					}
					warning.setAccessoryList(accessoryList);
				}
				warningList.add(warning);
			}
			return warningList;
		}
	}


	/**
	 * 根据JSONObject获取报警通知对象
	 * 
	 * @param object
	 * @return
	 * @throws JSONException
	 */
	public WarningEntity getObjectByJSONObject(JSONObject object)
			throws JSONException {
		WarningEntity warning = null;
		if (object != null) {
			warning = new WarningEntity();
			if (!object.isNull("note")) {
				warning.setNote(object.getString("note"));
			}
			if (!object.isNull("createTime")) {
				warning.setCreateTime(object.getString("createTime"));
			}
			if (!object.isNull("UserName")) {
				warning.setWarningPeople(object.getString("UserName"));
			}
			JSONObject jsonLocation = object.getJSONObject("location_id");
			if (jsonLocation.isNull("latitude")
					|| jsonLocation.isNull("longitude")
					|| jsonLocation.getString("latitude").equals("")
					|| jsonLocation.getString("longitude").equals("")) {
			} else {
				Loaction location = new Loaction();
				location.setTIME(jsonLocation.getString("Time"));
				location.setLATITUDE(Double.parseDouble(jsonLocation
						.getString("latitude")));
				location.setLONGITUDE(Double.parseDouble(jsonLocation
						.getString("longitude")));
				location.setELEVATION(Double.parseDouble(jsonLocation
						.getString("elevation")));
				warning.setLocation(location);
			}
			if (!object.isNull("affix") && !object.getString("affix").equals("")) {
				JSONArray jsonAccessory = new JSONArray(
						object.getString("affix"));
				List<Accessory> accessoryList = new ArrayList<Accessory>();
				for (int j = 0; j < jsonAccessory.length(); j++) {
					JSONObject objectAccessory = jsonAccessory.getJSONObject(j);
					Accessory accessory = new Accessory();
					accessory.setFileType(objectAccessory.getString("type"));
					accessory.setAccessoryPath(objectAccessory.getString("url")
							.replace("../../",
									ActivityUtils.getServerWebApi(context)));
					if (!object.isNull("fileAllPath")) {
						accessory.setSlPath(object.getString("fileAllPath"));
					}
					accessoryList.add(accessory);
				}
				warning.setAccessoryList(accessoryList);
			}
		}
		return warning;
	}
}
