package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.WoodlandMiningEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.Location;
import db.Database.WoodlandMining;

/**
 * 基础台账 - 林地内开采沙、土、石、矿情况表
 * 
 * @author zhengtiantian
 * 
 */
public class WoodlandMiningDao {

	private Context context;
	private ForestDatabaseHelper dbHelper;
	private static WoodlandMiningDao wmDao;

	public WoodlandMiningDao(Context context) {
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	public static WoodlandMiningDao init(Context ct) {
		if (wmDao == null) {
			wmDao = new WoodlandMiningDao(ct);
		}
		return wmDao;

	}

	/**
	 * 插入数据
	 * 
	 * @param wm
	 * @return flag
	 */
	public boolean insertInfo(WoodlandMiningEntity wm) {
		boolean flag = false;
		int wmId = -1;
		LocationDao lDao = new LocationDao(context);
		int locationId = lDao.insertInfo(wm.getLocationId());
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor=null;
		if (locationId != -1) {
			try {
				ContentValues cv = new ContentValues();
				cv.put(WoodlandMining.LOCATION_ID, locationId);
				cv.put(WoodlandMining.WOODLANDMINING_ID, wm.getServerId());
				cv.put(WoodlandMining.SPECIFICLOCATION,
						wm.getSpecificLocation());
				cv.put(WoodlandMining.MININGTYPE, wm.getMiningType());
				cv.put(WoodlandMining.MININGAREA, wm.getMiningArea());
				cv.put(WoodlandMining.FALLIMENTIDELLO, wm.getFallimentiDello());
				cv.put(WoodlandMining.ALLOWUNIT, wm.getAllowUnit());
				cv.put(WoodlandMining.REMARK, wm.getNote());
				long ok = db.insert(WoodlandMining.WOODLANDMINING_TABLE_NAME,
						null, cv);
				if (-1 != ok) {
					flag = true;
				}
				String sql = "SELECT MAX("
						+ WoodlandMining.WOODLANDMINING_TABLE_ID + ") FROM "
						+ WoodlandMining.WOODLANDMINING_TABLE_NAME;
				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					wmId = cursor.getInt(0);
				}
				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				flag = false;
			} finally {
				db.endTransaction();
				if(null!=cursor){
					cursor.close();
				}
			}
			if (wm.getwmAttachment() != null && wm.getwmAttachment().size() > 0) {
				AttachmentDao aDao = new AttachmentDao(context);
				for (int i = 0; i < wm.getwmAttachment().size(); i++) {
					wm.getwmAttachment().get(i)
							.setaId(WoodlandMining.CATALOG_ID);
					wm.getwmAttachment().get(i).setRowId(wmId);
					flag = aDao.insertInfo(wm.getwmAttachment().get(i));
				}
			}
		}
		return flag;
	}

	/**
	 * 删除表
	 * 
	 * @param instantId
	 * @return isOk
	 */
	public boolean delTable(String instantId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		try {
			int res = db.delete(WoodlandMining.WOODLANDMINING_TABLE_NAME,
					WoodlandMining.WOODLANDMINING_TABLE_ID + "=?",
					new String[] { instantId });
			if (res > 0) {
				isOk = true;
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		return isOk;
	}

	/**
	 * 获取本地所有
	 * 
	 * @return
	 */
	public ArrayList<WoodlandMiningEntity> getAllInfos() {
		ArrayList<WoodlandMiningEntity> infos = new ArrayList<WoodlandMiningEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			String sql = "select * from "
					+ WoodlandMining.WOODLANDMINING_TABLE_NAME + " Left join "
					+ Location.LOCATION_TABLE_NAME + " where "
					+ WoodlandMining.WOODLANDMINING_TABLE_NAME + "."
					+ WoodlandMining.LOCATION_ID + " = "
					+ Location.LOCATION_TABLE_NAME + "." + Location.LOCATION_ID;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);

			while (cursor.moveToNext()) {
				WoodlandMiningEntity wl = new WoodlandMiningEntity();
				wl.setWoodlandMiningID(cursor.getInt(cursor
						.getColumnIndex(WoodlandMining.WOODLANDMINING_TABLE_ID)));
				wl.setServerId(cursor.getInt(cursor
						.getColumnIndex(WoodlandMining.WOODLANDMINING_ID)));
				wl.setSpecificLocation(cursor.getString(cursor
						.getColumnIndex(WoodlandMining.SPECIFICLOCATION)));
				wl.setMiningType(cursor.getString(cursor
						.getColumnIndex(WoodlandMining.MININGTYPE)));
				wl.setMiningArea(cursor.getString(cursor
						.getColumnIndex(WoodlandMining.MININGAREA)));
				wl.setFallimentiDello(cursor.getString(cursor
						.getColumnIndex(WoodlandMining.FALLIMENTIDELLO)));
				wl.setAllowUnit(cursor.getString(cursor
						.getColumnIndex(WoodlandMining.ALLOWUNIT)));
				wl.setNote(cursor.getString(cursor
						.getColumnIndex(WoodlandMining.REMARK)));
				Loaction l = new Loaction();
				l.setLocation_id(cursor.getInt(cursor
						.getColumnIndex(Location.LOCATION_ID)));
				l.setELEVATION(cursor.getDouble(cursor
						.getColumnIndex(Location.ELEVATION)));
				l.setLATITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LATITUDE)));
				l.setLONGITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LONGITUDE)));
				l.setTIME(cursor.getString(cursor.getColumnIndex(Location.TIME)));

				wl.setLocationId(l);
				infos.add(wl);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.endTransaction();
			if (cursor != null) {
				cursor.close();
			}
		}
		AttachmentDao dao = null;
		for (int i = 0; i < infos.size(); i++) {
			int rowId = infos.get(i).getWoodlandMiningID();
			dao = new AttachmentDao(context);
			ArrayList<Accessory> list = dao.getInfosById(
					WoodlandMining.CATALOG_ID, rowId);
			infos.get(i).setwAttachment(list);
		}
		return infos;
	}

	/**
	 * 获取数量
	 * 
	 * @return
	 */
	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT (*) FROM "
				+ WoodlandMining.WOODLANDMINING_TABLE_NAME;
		try {
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				count = cursor.getInt(0);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.endTransaction();
			if(null!=cursor){
				cursor.close();
			}
		}
		return count;
	}

	/**
	 * 根据对象获取json
	 * 
	 * @param wo
	 * @param userId
	 * @return
	 * @throws JSONException
	 */
	public static String getJson(WoodlandMiningEntity wo, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("userId", userId);
			jo.put("catalogID", WoodlandMining.CATALOG_ID);
			jo.put(WoodlandMining.WOODLANDMINING_ID, wo.getServerId());
			jo.put(WoodlandMining.SPECIFICLOCATION, wo.getSpecificLocation());
			jo.put(WoodlandMining.MININGTYPE, wo.getMiningType());
			jo.put(WoodlandMining.MININGAREA, wo.getMiningArea());
			jo.put(WoodlandMining.FALLIMENTIDELLO, wo.getFallimentiDello());
			jo.put(WoodlandMining.ALLOWUNIT, wo.getAllowUnit());
			jo.put("remark", wo.getNote());
			JSONObject lo = new JSONObject();
			lo.put(Location.ELEVATION, wo.getLocationId().getELEVATION());
			lo.put(Location.LATITUDE, wo.getLocationId().getLATITUDE());
			lo.put(Location.LONGITUDE, wo.getLocationId().getLONGITUDE());
			lo.put(Location.TIME, wo.getLocationId().getTIME());

			jo.put(WoodlandMining.LOCATION_ID, lo.toString());
			JSONArray ja = new JSONArray();
			if (wo.getwmAttachment() != null && wo.getwmAttachment().size() > 0) {
				for (int i = 0; i < wo.getwmAttachment().size(); i++) {
					JSONObject json = AttachmentDao.getJson(wo
							.getwmAttachment().get(i));
					ja.put(json);
				}
			}
			jo.put("flist", ja.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		//JSONObject json = new JSONObject();
		//json.put("WoodMining", jo);
		return jo.toString();
	}

	/**
	 * 根据对象获取API参数
	 * 
	 * @param wood
	 * @param userId
	 * @return
	 */
	/*public static AjaxParams getParams(WoodlandMiningEntity wood, int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId + "");
		params.put("catalogID", WoodlandMining.CATALOG_ID + "");
		params.put(WoodlandMining.WOODLANDMINING_ID, wood.getServerId() + "");
		if (wood.getSpecificLocation() != null
				&& !wood.getSpecificLocation().equals("")) {
			params.put(WoodlandMining.SPECIFICLOCATION,
					wood.getSpecificLocation());
		}
		if (wood.getMiningType() != null && !wood.getMiningType().equals("")) {
			params.put(WoodlandMining.MININGTYPE, wood.getMiningType());
		}
		if (wood.getMiningArea() != null && !wood.getMiningArea().equals("")) {
			params.put(WoodlandMining.MININGAREA, wood.getMiningArea());
		}
		if (wood.getFallimentiDello() != null
				&& !wood.getFallimentiDello().equals("")) {
			params.put(WoodlandMining.FALLIMENTIDELLO,
					wood.getFallimentiDello());
		}
		if (wood.getAllowUnit() != null && !wood.getAllowUnit().equals("")) {
			params.put(WoodlandMining.ALLOWUNIT, wood.getAllowUnit());
		}
		if (wood.getNote() != null && !wood.getNote().equals("")) {
			params.put("remark", wood.getNote());
		}
		JSONObject lo = new JSONObject();
		try {
			lo.put(Location.ELEVATION, wood.getLocationId().getELEVATION());
			lo.put(Location.LATITUDE, wood.getLocationId().getLATITUDE());
			lo.put(Location.LONGITUDE, wood.getLocationId().getLONGITUDE());
			lo.put(Location.TIME, wood.getLocationId().getTIME());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		params.put(WoodlandMining.LOCATION_ID, lo.toString());
		if (wood.getwmAttachment() != null && wood.getwmAttachment().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < wood.getwmAttachment().size(); i++) {
				JSONObject json = AttachmentDao.getJson(wood.getwmAttachment()
						.get(i));
				ja.put(json);
			}
			params.put("flist", ja.toString());
		}
		return params;
	}*/

	/**
	 * 根据json获取对象
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public ArrayList<WoodlandMiningEntity> getObject(JSONArray json)
			throws JSONException {
		ArrayList<WoodlandMiningEntity> wlms = null;
		if (json != null && !json.equals("[]")) {
			wlms = new ArrayList<WoodlandMiningEntity>();
			for (int i = 0; i < json.length(); i++) {
				JSONObject jo = json.getJSONObject(i);
				WoodlandMiningEntity wood = new WoodlandMiningEntity();
				if (!jo.isNull(WoodlandMining.WOODLANDMINING_ID)) {
					wood.setServerId(jo
							.getInt(WoodlandMining.WOODLANDMINING_ID));
				}
				if (!jo.isNull(WoodlandMining.SPECIFICLOCATION)) {
					wood.setSpecificLocation(jo
							.getString(WoodlandMining.SPECIFICLOCATION));
				}
				if (!jo.isNull(WoodlandMining.MININGTYPE)) {
					wood.setMiningType(jo.getString(WoodlandMining.MININGTYPE));
				}
				if (!jo.isNull(WoodlandMining.MININGAREA)) {
					wood.setMiningArea(jo.getString(WoodlandMining.MININGAREA));
				}
				if (!jo.isNull(WoodlandMining.FALLIMENTIDELLO)) {
					wood.setFallimentiDello(jo
							.getString(WoodlandMining.FALLIMENTIDELLO));
				}
				if (!jo.isNull(WoodlandMining.ALLOWUNIT)) {
					wood.setAllowUnit(jo.getString(WoodlandMining.ALLOWUNIT));
				}
				if(!jo.isNull(WoodlandMining.REMARK)){
					wood.setNote(jo.getString(WoodlandMining.REMARK));
				}
				if (!jo.isNull(WoodlandMining.LOCATION_ID)) {
					JSONObject joLocation = jo
							.getJSONObject(WoodlandMining.LOCATION_ID);
					Loaction l = new Loaction();
					if (!joLocation.isNull(Location.LONGITUDE)) {
						l.setLONGITUDE(joLocation.getDouble(Location.LONGITUDE));
					}
					if (!joLocation.isNull(Location.LATITUDE)) {
						l.setLATITUDE(joLocation.getDouble(Location.LATITUDE));
					}
					if (!joLocation.isNull(Location.ELEVATION)) {
						l.setELEVATION(joLocation.getDouble(Location.ELEVATION));
					}
					if (!joLocation.isNull(Location.TIME)) {
						l.setTIME(joLocation.getString(Location.TIME));
					}
					wood.setLocationId(l);
				}
				if (!jo.isNull("affix")) {
					ArrayList<Accessory> accessorys = new ArrayList<Accessory>();
					JSONArray jaaccessorys = new JSONArray(
							jo.getString("affix"));
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
					wood.setwAttachment(accessorys);
				}
				wlms.add(wood);
			}
		}
		return wlms;

	}
}
