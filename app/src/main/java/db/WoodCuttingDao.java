package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.WoodCuttingEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.Location;
import db.Database.WoodCutting;

/**
 * 基础台账 - 木材采伐场（点）登记
 * 
 * @author zhengtiantian
 * 
 */
public class WoodCuttingDao {

	private Context context;
	private ForestDatabaseHelper dbHelper;
	private static WoodCuttingDao wDao;

	public WoodCuttingDao(Context context) {
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	public static WoodCuttingDao init(Context ct) {
		if (wDao == null) {
			wDao = new WoodCuttingDao(ct);
		}
		return wDao;

	}

	/**
	 * 插入数据
	 * 
	 * @param wc
	 * @return flag
	 */
	public boolean insertInfo(WoodCuttingEntity wc) {
		boolean flag = false;
		int wcId = -1;
		LocationDao lDao = new LocationDao(context);
		int locationId = lDao.insertInfo(wc.getLocationId());
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		if (locationId != -1) {
			Cursor cursor = null;
			try {
				ContentValues cv = new ContentValues();
				cv.put(WoodCutting.LOCATION_ID, locationId);
				cv.put(WoodCutting.WOODCUTTING_ID, wc.getServerId());
				cv.put(WoodCutting.CUTTINGUNIT, wc.getCuttingUnit());
				cv.put(WoodCutting.CUTTINGLOCATION, wc.getCuttingLocation());
				cv.put(WoodCutting.CUTTINGCARDID, wc.getCuttingCardID());
				cv.put(WoodCutting.ALLOWCOUNT, wc.getAllowCount());
				cv.put(WoodCutting.CUTTINGSPECIES, wc.getCuttingSpecies());
				cv.put(WoodCutting.CUTTINGMODE, wc.getCuttingMode());
				cv.put(WoodCutting.CUTTINGCOUNT, wc.getCuttingCount());
				cv.put(WoodCutting.CUTTINGBEGINTIME, wc.getCuttingBeginTime());
				cv.put(WoodCutting.CUTTINGENDTIME, wc.getCuttingEndTime());
				cv.put(WoodCutting.INSPECTION, wc.getInspection());

				long ok = db.insert(WoodCutting.WOODCUTTING_TABLE_NAME, null,
						cv);
				if (-1 != ok) {
					flag = true;
				}
				String sql = "SELECT MAX(" + WoodCutting.WOODCUTTING_TABLE_ID
						+ ") FROM " + WoodCutting.WOODCUTTING_TABLE_NAME;
				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					wcId = cursor.getInt(0);
				}
				
				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				flag = false;
			} finally {
				db.endTransaction();
				if (cursor != null) {
					cursor.close();
				}
			}
			if (wc.getwcAttachment() != null && wc.getwcAttachment().size() > 0) {
				AttachmentDao aDao = new AttachmentDao(context);
				for (int i = 0; i < wc.getwcAttachment().size(); i++) {
					wc.getwcAttachment().get(i).setaId(WoodCutting.CATALOG_ID);
					wc.getwcAttachment().get(i).setRowId(wcId);
					flag = aDao.insertInfo(wc.getwcAttachment().get(i));
				}
			}
		}
		return flag;
	}

	/**
	 * 删除表
	 * 
	 * @param instantId
	 * @return
	 */
	public boolean delTable(String instantId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		try {
			int res = db.delete(WoodCutting.WOODCUTTING_TABLE_NAME,
					WoodCutting.WOODCUTTING_TABLE_ID + "=?",
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
	public ArrayList<WoodCuttingEntity> getAllInfos() {
		ArrayList<WoodCuttingEntity> infos = new ArrayList<WoodCuttingEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			String sql = "select * from " + WoodCutting.WOODCUTTING_TABLE_NAME
					+ " Left join " + Location.LOCATION_TABLE_NAME + " where "
					+ WoodCutting.WOODCUTTING_TABLE_NAME + "."
					+ WoodCutting.LOCATION_ID + " = "
					+ Location.LOCATION_TABLE_NAME + "." + Location.LOCATION_ID;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				WoodCuttingEntity we = new WoodCuttingEntity();
				we.setWoodCuttingID(cursor.getInt(cursor
						.getColumnIndex(WoodCutting.WOODCUTTING_TABLE_ID)));
				we.setServerId(cursor.getInt(cursor
						.getColumnIndex(WoodCutting.WOODCUTTING_ID)));
				we.setCuttingBeginTime(cursor.getString(cursor
						.getColumnIndex(WoodCutting.CUTTINGBEGINTIME)));
				we.setAllowCount(cursor.getString(cursor
						.getColumnIndex(WoodCutting.ALLOWCOUNT)));
				we.setCuttingCardID(cursor.getString(cursor
						.getColumnIndex(WoodCutting.CUTTINGCARDID)));
				we.setCuttingCount(cursor.getString(cursor
						.getColumnIndex(WoodCutting.CUTTINGCOUNT)));
				we.setCuttingEndTime(cursor.getString(cursor
						.getColumnIndex(WoodCutting.CUTTINGENDTIME)));
				we.setCuttingLocation(cursor.getString(cursor
						.getColumnIndex(WoodCutting.CUTTINGLOCATION)));
				we.setCuttingMode(cursor.getString(cursor
						.getColumnIndex(WoodCutting.CUTTINGMODE)));
				we.setCuttingSpecies(cursor.getString(cursor
						.getColumnIndex(WoodCutting.CUTTINGSPECIES)));
				we.setCuttingUnit(cursor.getString(cursor
						.getColumnIndex(WoodCutting.CUTTINGUNIT)));
				we.setInspection(cursor.getString(cursor
						.getColumnIndex(WoodCutting.INSPECTION)));

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
				we.setLocationId(l);
				infos.add(we);
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
			int rowId = infos.get(i).getWoodCuttingID();
			dao = new AttachmentDao(context);
			ArrayList<Accessory> list = dao.getInfosById(
					WoodCutting.CATALOG_ID, rowId);
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
				+ WoodCutting.WOODCUTTING_TABLE_NAME;
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
			if (cursor != null) {
				cursor.close();
			}
		}
		return count;
	}

	/**
	 * 根据对象获取json
	 * 
	 * @param wood
	 * @param userId
	 * @return
	 * @throws JSONException
	 */
	public static String getJson(WoodCuttingEntity wood, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("userId", userId);
			jo.put("catalogID", WoodCutting.CATALOG_ID);
			jo.put(WoodCutting.WOODCUTTING_ID, wood.getServerId());
			jo.put(WoodCutting.CUTTINGUNIT, wood.getCuttingUnit());
			jo.put(WoodCutting.CUTTINGLOCATION, wood.getCuttingLocation());
			jo.put(WoodCutting.CUTTINGCARDID, wood.getCuttingCardID());
			jo.put(WoodCutting.ALLOWCOUNT, wood.getAllowCount());
			jo.put(WoodCutting.CUTTINGSPECIES, wood.getCuttingSpecies());
			jo.put(WoodCutting.CUTTINGMODE, wood.getCuttingMode());
			jo.put(WoodCutting.CUTTINGCOUNT, wood.getCuttingCount());
			jo.put(WoodCutting.CUTTINGBEGINTIME, wood.getCuttingBeginTime());
			jo.put(WoodCutting.CUTTINGENDTIME, wood.getCuttingEndTime());
			jo.put(WoodCutting.INSPECTION, wood.getInspection());

			JSONObject lo = new JSONObject();
			lo.put(Location.ELEVATION, wood.getLocationId().getELEVATION());
			lo.put(Location.LATITUDE, wood.getLocationId().getLATITUDE());
			lo.put(Location.LONGITUDE, wood.getLocationId().getLONGITUDE());
			lo.put(Location.TIME, wood.getLocationId().getTIME());

			jo.put(WoodCutting.LOCATION_ID, lo.toString());
			if (wood.getwcAttachment() != null && wood.getwcAttachment().size() > 0) {
				JSONArray accessoryJson = new JSONArray();
				for (int i = 0; i < wood.getwcAttachment().size(); i++) {
					JSONObject json = AttachmentDao.getJson(wood.getwcAttachment()
							.get(i));
					accessoryJson.put(json);
				}
				jo.put("flist", accessoryJson.toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return jo.toString();
	}

	/**
	 * 根据对象获取API参数
	 * 
	 * @param wood
	 * @param userId
	 * @return
	 */
	/*public static AjaxParams getParams(WoodCuttingEntity wood, int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", String.valueOf(userId));
		params.put("catalogID", String.valueOf(WoodCutting.CATALOG_ID));
		params.put(WoodCutting.WOODCUTTING_ID,
				String.valueOf(wood.getServerId()));
		if (!wood.getCuttingUnit().equals("")) {
			params.put(WoodCutting.CUTTINGUNIT, wood.getCuttingUnit());
		}
		if (!wood.getCuttingLocation().equals("")) {
			params.put(WoodCutting.CUTTINGLOCATION, wood.getCuttingLocation());
		}
		if (!wood.getCuttingCardID().equals("")) {
			params.put(WoodCutting.CUTTINGCARDID, wood.getCuttingCardID());
		}
		if (!wood.getAllowCount().equals("")) {
			params.put(WoodCutting.ALLOWCOUNT, wood.getAllowCount());
		}
		if (!wood.getCuttingSpecies().equals("")) {
			params.put(WoodCutting.CUTTINGSPECIES, wood.getCuttingSpecies());
		}
		if (!wood.getCuttingMode().equals("")) {
			params.put(WoodCutting.CUTTINGMODE, wood.getCuttingMode());
		}
		if (!wood.getCuttingCount().equals("")) {
			params.put(WoodCutting.CUTTINGCOUNT, wood.getCuttingCount());
		}
		if (!wood.getCuttingBeginTime().equals("")) {
			params.put(WoodCutting.CUTTINGBEGINTIME, wood.getCuttingBeginTime());
		}
		if (!wood.getCuttingEndTime().equals("")) {
			params.put(WoodCutting.CUTTINGENDTIME, wood.getCuttingEndTime());
		}
		if (!wood.getInspection().equals("")) {
			params.put(WoodCutting.INSPECTION, wood.getInspection());
		}

		JSONObject lo = new JSONObject();
		try {
			lo.put(Location.ELEVATION, wood.getLocationId().getELEVATION());
			lo.put(Location.LATITUDE, wood.getLocationId().getLATITUDE());
			lo.put(Location.LONGITUDE, wood.getLocationId().getLONGITUDE());
			lo.put(Location.TIME, wood.getLocationId().getTIME());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		params.put(WoodCutting.LOCATION_ID, lo.toString());
		if (wood.getwcAttachment() != null && wood.getwcAttachment().size() > 0) {
			JSONArray accessoryJson = new JSONArray();
			for (int i = 0; i < wood.getwcAttachment().size(); i++) {
				JSONObject jo = AttachmentDao.getJson(wood.getwcAttachment()
						.get(i));
				accessoryJson.put(jo);
			}
			params.put("flist", accessoryJson.toString());
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
	public ArrayList<WoodCuttingEntity> getObject(String json)
			throws JSONException {
		ArrayList<WoodCuttingEntity> woods = null;
		JSONArray ja = null;
		if (json != null && !json.equals("anyType{}")) {
			woods = new ArrayList<WoodCuttingEntity>();
			ja = new JSONArray(json);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				WoodCuttingEntity wc = new WoodCuttingEntity();
				if (!jo.isNull(WoodCutting.WOODCUTTING_ID)) {
					wc.setServerId(jo.getInt(WoodCutting.WOODCUTTING_ID));
				}
				if (!jo.isNull(WoodCutting.CUTTINGUNIT)) {
					wc.setCuttingUnit(jo.getString(WoodCutting.CUTTINGUNIT));
				}
				if (!jo.isNull(WoodCutting.CUTTINGLOCATION)) {
					wc.setCuttingLocation(jo
							.getString(WoodCutting.CUTTINGLOCATION));
				}
				if (!jo.isNull(WoodCutting.CUTTINGCARDID)) {
					wc.setCuttingCardID(jo.getString(WoodCutting.CUTTINGCARDID));
				}
				if (!jo.isNull(WoodCutting.ALLOWCOUNT)) {
					wc.setAllowCount(jo.getString(WoodCutting.ALLOWCOUNT));
				}
				if (!jo.isNull(WoodCutting.CUTTINGSPECIES)) {
					wc.setCuttingSpecies(jo
							.getString(WoodCutting.CUTTINGSPECIES));
				}
				if (!jo.isNull(WoodCutting.CUTTINGMODE)) {
					wc.setCuttingMode(jo.getString(WoodCutting.CUTTINGMODE));
				}
				if (!jo.isNull(WoodCutting.CUTTINGCOUNT)) {
					wc.setCuttingCount(jo.getString(WoodCutting.CUTTINGCOUNT));
				}
				if (!jo.isNull(WoodCutting.CUTTINGBEGINTIME)) {
					wc.setCuttingBeginTime(jo
							.getString(WoodCutting.CUTTINGBEGINTIME));
				}
				if (!jo.isNull(WoodCutting.CUTTINGENDTIME)) {
					wc.setCuttingEndTime(jo
							.getString(WoodCutting.CUTTINGENDTIME));
				}
				if (!jo.isNull(WoodCutting.INSPECTION)) {
					wc.setInspection(jo.getString(WoodCutting.INSPECTION));
				}
				if (!jo.isNull(WoodCutting.LOCATION_ID)) {
					JSONObject joLocation = jo
							.getJSONObject(WoodCutting.LOCATION_ID);
					if (joLocation.getDouble(Location.LATITUDE) != 0) {
						Loaction l = new Loaction();
						l.setELEVATION(joLocation.getDouble(Location.ELEVATION));
						l.setLATITUDE(joLocation.getDouble(Location.LATITUDE));
						l.setLONGITUDE(joLocation.getDouble(Location.LONGITUDE));
						l.setTIME(joLocation.getString(Location.TIME));
						wc.setLocationId(l);
					}
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
					wc.setwAttachment(accessorys);
				}
				woods.add(wc);
			}
		} else if (json == null) {
			return null;
		}
		return woods;
	}
}
