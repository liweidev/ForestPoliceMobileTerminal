package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.KeyProtectedPlants;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.KeyProtectedPlant;
import db.Database.Location;

/**
 * @author liupeng 重点植物
 * 
 */
public class KeyProtectedPlantDao {

	private ForestDatabaseHelper dbHelper;

	private Context context;
	private static KeyProtectedPlantDao ppDao;

	/**
	 * 
	 */
	public KeyProtectedPlantDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	public static KeyProtectedPlantDao init(Context ct) {
		if (ppDao == null) {
			ppDao = new KeyProtectedPlantDao(ct);
		}
		return ppDao;

	}

	/*
	 * 插入
	 */

	public boolean insert(KeyProtectedPlants kpp) {
		boolean flag = false;

		int plantId = -1;
		LocationDao ldao = new LocationDao(context);
		int locationId = ldao.insertInfo(kpp.getLocation());

		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();

		if (locationId != -1) {
			Cursor cursor = null;
			try {
				ContentValues contentValues = new ContentValues();

				contentValues.put(KeyProtectedPlant.COMPETENT_DEPARMENT,
						kpp.getCompetent_deparment());

				contentValues.put(KeyProtectedPlant.CONTACT, kpp.getContact());
				contentValues.put(KeyProtectedPlant.DR_AND_PN,
						kpp.getComOrSLNumber());
				contentValues.put(KeyProtectedPlant.FILE_NO, kpp.getFileNo());
				contentValues.put(KeyProtectedPlant.LOCATION_ID, locationId);
				contentValues.put(KeyProtectedPlant.MAPO_PERSONNEL,
						kpp.getMapoPersonnel());
				contentValues.put(KeyProtectedPlant.NOTE, kpp.getKppLegeng());

				contentValues.put(KeyProtectedPlant.PLANT_NAME,
						kpp.getPlantNames());
				contentValues.put(
						KeyProtectedPlant.KEY_PROTECTED_PLANT_SERVER_ID,
						kpp.getServerId());
				contentValues.put(KeyProtectedPlant.PROTECTION_LEVEL,
						kpp.getProtectionLevel());

				contentValues.put(KeyProtectedPlant.STATUS, kpp.getStatus());
				contentValues.put(KeyProtectedPlant.TREE_AGE, kpp.getTreeAge());

				long ok = db.insert(
						KeyProtectedPlant.KEY_PROTECTED_PLANT_TABLE_NAME, null,
						contentValues);
				if (-1 != ok) {
					flag = true;
				}

				String sql = "SELECT MAX("
						+ KeyProtectedPlant.KEY_PROTECTED_PLANT_ID + ") FROM "
						+ KeyProtectedPlant.KEY_PROTECTED_PLANT_TABLE_NAME;
				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					plantId = cursor.getInt(0);
				}
				
				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
			} finally {
				db.endTransaction();
				if (cursor != null) {
					cursor.close();
				}
			}

			if (kpp.getKppAccessory() != null
					&& kpp.getKppAccessory().size() > 0) {
				AttachmentDao dao = new AttachmentDao(context);
				for (int i = 0; i < kpp.getKppAccessory().size(); i++) {
					kpp.getKppAccessory()
							.get(i)
							.setTableId(
									KeyProtectedPlant.KEY_PROTECTED_PLANT_TABLE_ID);
					kpp.getKppAccessory().get(i).setRowId(plantId);
					flag = dao.insertInfo(kpp.getKppAccessory().get(i));
				}
			}
		}
		return flag;
	}

	/**
	 * 删表
	 */
	public boolean delTable(String plantId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		try {
			int res = db.delete(
					KeyProtectedPlant.KEY_PROTECTED_PLANT_TABLE_NAME,
					KeyProtectedPlant.KEY_PROTECTED_PLANT_ID + "=?",
					new String[] { plantId });
			if (res > 0) {
				isOk = true;
			}
			db.setTransactionSuccessful();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		return isOk;
	}

	/**
	 * 获取本地所有
	 * 
	 */
	public ArrayList<KeyProtectedPlants> getAllInfos() {
		ArrayList<KeyProtectedPlants> infos = new ArrayList<KeyProtectedPlants>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {

			String sql = "select * from "
					+ KeyProtectedPlant.KEY_PROTECTED_PLANT_TABLE_NAME
					+ " Left join " + Location.LOCATION_TABLE_NAME + " where "
					+ KeyProtectedPlant.KEY_PROTECTED_PLANT_TABLE_NAME + "."
					+ KeyProtectedPlant.LOCATION_ID + " = "
					+ Location.LOCATION_TABLE_NAME + "." + Location.LOCATION_ID;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				KeyProtectedPlants info = new KeyProtectedPlants();
				info.setComOrSLNumber(cursor.getString(cursor
						.getColumnIndex(KeyProtectedPlant.DR_AND_PN)));
				info.setCompetent_deparment(cursor.getString(cursor
						.getColumnIndex(KeyProtectedPlant.COMPETENT_DEPARMENT)));
				info.setContact(cursor.getString(cursor
						.getColumnIndex(KeyProtectedPlant.CONTACT)));
				info.setFileNo(cursor.getString(cursor
						.getColumnIndex(KeyProtectedPlant.FILE_NO)));
				info.setServerId(cursor.getShort(cursor
						.getColumnIndex(KeyProtectedPlant.KEY_PROTECTED_PLANT_SERVER_ID)));
				info.setKppLegeng(cursor.getString(cursor
						.getColumnIndex(KeyProtectedPlant.NOTE)));
				info.setMapoPersonnel(cursor.getString(cursor
						.getColumnIndex(KeyProtectedPlant.MAPO_PERSONNEL)));
				info.setPkkId(cursor.getInt(cursor
						.getColumnIndex(KeyProtectedPlant.KEY_PROTECTED_PLANT_ID)));
				info.setPlantNames(cursor.getString(cursor
						.getColumnIndex(KeyProtectedPlant.PLANT_NAME)));
				info.setProtectionLevel(cursor.getString(cursor
						.getColumnIndex(KeyProtectedPlant.PROTECTION_LEVEL)));
				info.setStatus(cursor.getString(cursor
						.getColumnIndex(KeyProtectedPlant.STATUS)));
				info.setTreeAge(cursor.getString(cursor
						.getColumnIndex(KeyProtectedPlant.TREE_AGE)));

				Loaction l = new Loaction();
				l.setLocation_id(cursor.getInt(cursor
						.getColumnIndex(Location.LOCATION_ID)));
				l.setELEVATION(cursor.getDouble(cursor
						.getColumnIndex(Location.ELEVATION)));
				l.setLATITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LATITUDE)));
				l.setLONGITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LONGITUDE)));
				info.setLocation(l);

				infos.add(info);
			}
			db.setTransactionSuccessful();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			if (cursor != null) {
				cursor.close();
			}
		}

		for (int i = 0; i < infos.size(); i++) {
			int rowid = infos.get(i).getPkkId();
			AttachmentDao dao = new AttachmentDao(context);
			ArrayList<Accessory> list = dao.getInfosById(
					KeyProtectedPlant.KEY_PROTECTED_PLANT_TABLE_ID, rowid);
			infos.get(i).setKppAccessory(list);
		}

		return infos;
	}

	/*
	 * 获取数据库数量
	 */
	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM "
				+ KeyProtectedPlant.KEY_PROTECTED_PLANT_TABLE_NAME;

		try {
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				count = cursor.getInt(0);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.endTransaction();
			if (cursor != null) {
				cursor.close();
			}
		}

		return count;
	}

	/*
	 * 根据对象获取JSON
	 */
	public static String getJson(KeyProtectedPlants kpp, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("userId", userId);
			jo.put("tableId", KeyProtectedPlant.KEY_PROTECTED_PLANT_TABLE_ID);
			jo.put(KeyProtectedPlant.COMPETENT_DEPARMENT,
					kpp.getCompetent_deparment());
			jo.put(KeyProtectedPlant.CONTACT, kpp.getContact());
			jo.put(KeyProtectedPlant.DR_AND_PN, kpp.getComOrSLNumber());
			jo.put(KeyProtectedPlant.FILE_NO, kpp.getFileNo());
			jo.put(KeyProtectedPlant.KEY_PROTECTED_PLANT_SERVER_ID,
					kpp.getServerId() + "");
			jo.put(KeyProtectedPlant.MAPO_PERSONNEL, kpp.getMapoPersonnel());
			jo.put(KeyProtectedPlant.NOTE, kpp.getKppLegeng());
			jo.put(KeyProtectedPlant.PLANT_NAME, kpp.getPlantNames());
			jo.put(KeyProtectedPlant.PROTECTION_LEVEL, kpp.getProtectionLevel());
			jo.put(KeyProtectedPlant.STATUS, kpp.getStatus());
			jo.put(KeyProtectedPlant.TREE_AGE, kpp.getTreeAge());

			JSONObject joLocation = new JSONObject();
			joLocation
					.put(Location.ELEVATION, kpp.getLocation().getELEVATION());
			joLocation.put(Location.LATITUDE, kpp.getLocation().getLATITUDE());
			joLocation
					.put(Location.LONGITUDE, kpp.getLocation().getLONGITUDE());

			jo.put(KeyProtectedPlant.LOCATION_ID, joLocation.toString());
			if(kpp.getKppAccessory() != null && kpp.getKppAccessory().size() > 0) {
					JSONArray ja = new JSONArray();
					for (int i = 0; i < kpp.getKppAccessory().size(); i++) {
						JSONObject json = AttachmentDao.getJson(kpp.getKppAccessory()
								.get(i));
						ja.put(json);
					}
					jo.put("flist", ja.toString());
				}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jo.toString();
	}

	/**
	 * 根据对象获取API参数
	 * 
	 * @param
	 * @param
	 * @return
	 */
	/*public static AjaxParams getParams(KeyProtectedPlants kpp, int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId + "");
		params.put("tableId", KeyProtectedPlant.KEY_PROTECTED_PLANT_TABLE_ID
				+ "");
		if (kpp.getCompetent_deparment() != null
				&& !kpp.getCompetent_deparment().equals("")) {
			params.put(KeyProtectedPlant.COMPETENT_DEPARMENT,
					kpp.getCompetent_deparment());
		}
		if (kpp.getContact() != null && !kpp.getContact().equals("")) {
			params.put(KeyProtectedPlant.CONTACT, kpp.getContact());
		}
		if (kpp.getComOrSLNumber() != null
				&& !kpp.getComOrSLNumber().equals("")) {
			params.put(KeyProtectedPlant.DR_AND_PN, kpp.getComOrSLNumber());
		}
		if (kpp.getFileNo() != null && !kpp.getFileNo().equals("")) {
			params.put(KeyProtectedPlant.FILE_NO, kpp.getFileNo());
		}
		params.put(KeyProtectedPlant.KEY_PROTECTED_PLANT_SERVER_ID,
				kpp.getServerId() + "");
		if (kpp.getMapoPersonnel() != null
				&& !kpp.getMapoPersonnel().equals("")) {
			params.put(KeyProtectedPlant.MAPO_PERSONNEL, kpp.getMapoPersonnel());
		}
		if (kpp.getKppLegeng() != null && !kpp.getKppLegeng().equals("")) {
			params.put(KeyProtectedPlant.NOTE, kpp.getKppLegeng());
		}
		if (kpp.getPlantNames() != null && !kpp.getPlantNames().equals("")) {
			params.put(KeyProtectedPlant.PLANT_NAME, kpp.getPlantNames());
		}
		if (kpp.getProtectionLevel() != null
				&& !kpp.getProtectionLevel().equals("")) {
			params.put(KeyProtectedPlant.PROTECTION_LEVEL,
					kpp.getProtectionLevel());
		}
		if (kpp.getStatus() != null && !kpp.getStatus().equals("")) {
			params.put(KeyProtectedPlant.STATUS, kpp.getStatus());
		}
		if (kpp.getTreeAge() != null && !kpp.getTreeAge().equals("")) {
			params.put(KeyProtectedPlant.TREE_AGE, kpp.getTreeAge());
		}
		if (kpp.getLocation() != null) {
			JSONObject locationJo = new JSONObject();
			try {
				locationJo.put(Location.ELEVATION, kpp.getLocation()
						.getELEVATION());
				locationJo.put(Location.LATITUDE, kpp.getLocation()
						.getLATITUDE());
				locationJo.put(Location.LONGITUDE, kpp.getLocation()
						.getLONGITUDE());
				locationJo.put("Time", kpp.getLocation().getTIME());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			params.put(ForestPatrolWork.LOCATION_ID, locationJo.toString());
		}
		if (kpp.getKppAccessory() != null && kpp.getKppAccessory().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < kpp.getKppAccessory().size(); i++) {
				JSONObject json = AttachmentDao.getJson(kpp.getKppAccessory()
						.get(i));
				ja.put(json);
			}
			params.put("flist", ja.toString());
		}
		params.put("fileType", "1");

		return params;
	}
*/
	/*
	 * public static ArrayList<KeyProtectedPlants> getObject(String json) throws
	 * JSONException { ArrayList<KeyProtectedPlants> kpps = null; JSONArray ja =
	 * null; if (json != null && !json.equals("anyType{}")) { kpps = new
	 * ArrayList<KeyProtectedPlants>(); ja = new JSONArray(json); for (int i =
	 * 0; i < ja.length(); i++) { JSONObject jo = ja.getJSONObject(i);
	 * KeyProtectedPlants kpp = new KeyProtectedPlants(); if
	 * (!jo.isNull(KeyProtectedPlant.DR_AND_PN)) { kpp.setComOrSLNumber(jo
	 * .getString(KeyProtectedPlant.DR_AND_PN)); } if
	 * (!jo.isNull(KeyProtectedPlant.COMPETENT_DEPARMENT)) {
	 * kpp.setCompetent_deparment(jo
	 * .getString(KeyProtectedPlant.COMPETENT_DEPARMENT)); } if
	 * (!jo.isNull(KeyProtectedPlant.CONTACT)) {
	 * kpp.setContact(jo.getString(KeyProtectedPlant.CONTACT)); } if
	 * (!jo.isNull(KeyProtectedPlant.FILE_NO)) {
	 * kpp.setFileNo(jo.getString(KeyProtectedPlant.FILE_NO)); } if
	 * (!jo.isNull(KeyProtectedPlant.NOTE)) {
	 * kpp.setKppLegeng(jo.getString(KeyProtectedPlant.NOTE)); } if
	 * (!jo.isNull(KeyProtectedPlant.MAPO_PERSONNEL)) { kpp.setMapoPersonnel(jo
	 * .getString(KeyProtectedPlant.MAPO_PERSONNEL)); } if
	 * (!jo.isNull(KeyProtectedPlant.KEY_PROTECTED_PLANT_SERVER_ID)) {
	 * kpp.setServerId(jo
	 * .getInt(KeyProtectedPlant.KEY_PROTECTED_PLANT_SERVER_ID)); } if
	 * (!jo.isNull(KeyProtectedPlant.PLANT_NAME)) {
	 * Log.i("liupeng","jo.getString(KeyProtectedPlant.PLANT_NAME)"+ jo
	 * .getString(KeyProtectedPlant.PLANT_NAME)); kpp.setPlantNames(jo
	 * .getString(KeyProtectedPlant.PLANT_NAME)); }else{ kpp.setPlantNames("0");
	 * } if (!jo.isNull(KeyProtectedPlant.PROTECTION_LEVEL)) {
	 * kpp.setProtectionLevel(jo
	 * .getString(KeyProtectedPlant.PROTECTION_LEVEL)); } if
	 * (!jo.isNull(KeyProtectedPlant.STATUS)) {
	 * kpp.setStatus(jo.getString(KeyProtectedPlant.STATUS)); } if
	 * (!jo.isNull(KeyProtectedPlant.TREE_AGE)) { kpp.setTreeAge(jo
	 * .getString(KeyProtectedPlant.TREE_AGE)); } if
	 * (!jo.isNull(KeyProtectedPlant.LOCATION_ID)) { JSONObject joLocation = jo
	 * .getJSONObject(KeyProtectedPlant.LOCATION_ID); if
	 * (joLocation.getDouble(Location.LATITUDE) != 0) { Loaction l = new
	 * Loaction(); l.setELEVATION(joLocation.getDouble(Location.ELEVATION));
	 * l.setLATITUDE(joLocation.getDouble(Location.LATITUDE));
	 * l.setLONGITUDE(joLocation.getDouble(Location.LONGITUDE));
	 * kpp.setLocation(l); } } if (!jo.isNull("affix")) { ArrayList<Accessory>
	 * accessorys = new ArrayList<Accessory>(); JSONArray jaaccessorys = new
	 * JSONArray( jo.getString("affix")); for (int j = 0; j <
	 * jaaccessorys.length(); j++) { JSONObject joAttachment =
	 * jaaccessorys.getJSONObject(j); Accessory accessory = new Accessory(); if
	 * (!joAttachment.isNull("url")) { String url =
	 * joAttachment.getString("url");
	 * accessory.setAccessoryPath(url.replace("../..",
	 * Constant.URL+Constant.WEB_PORT)); } accessorys.add(accessory); }
	 * 
	 * kpp.setKppAccessory(accessorys); } kpps.add(kpp); } } return kpps; }
	 */

	public ArrayList<KeyProtectedPlants> getObject(String json)
			throws JSONException {
		ArrayList<KeyProtectedPlants> kpps = null;
		JSONObject jso = new JSONObject(json);
		if (jso.getInt("Error") == 0) {
			kpps = new ArrayList<KeyProtectedPlants>();
			JSONArray ja = jso.getJSONArray("PlantList");
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				KeyProtectedPlants kpp = new KeyProtectedPlants();
				if (!jo.isNull(KeyProtectedPlant.DR_AND_PN)) {
					kpp.setComOrSLNumber(jo
							.getString(KeyProtectedPlant.DR_AND_PN));
				}
				if (!jo.isNull(KeyProtectedPlant.COMPETENT_DEPARMENT)) {
					kpp.setCompetent_deparment(jo
							.getString(KeyProtectedPlant.COMPETENT_DEPARMENT));
				}
				if (!jo.isNull(KeyProtectedPlant.CONTACT)) {
					kpp.setContact(jo.getString(KeyProtectedPlant.CONTACT));
				}
				if (!jo.isNull(KeyProtectedPlant.FILE_NO)) {
					kpp.setFileNo(jo.getString(KeyProtectedPlant.FILE_NO));
				}
				if (!jo.isNull(KeyProtectedPlant.NOTE)) {
					kpp.setKppLegeng(jo.getString(KeyProtectedPlant.NOTE));
				}
				if (!jo.isNull(KeyProtectedPlant.MAPO_PERSONNEL)) {
					kpp.setMapoPersonnel(jo
							.getString(KeyProtectedPlant.MAPO_PERSONNEL));
				}
				if (!jo.isNull(KeyProtectedPlant.KEY_PROTECTED_PLANT_SERVER_ID)) {
					kpp.setServerId(jo
							.getInt(KeyProtectedPlant.KEY_PROTECTED_PLANT_SERVER_ID));
				}
				if (!jo.isNull(KeyProtectedPlant.PLANT_NAME)) {
					Log.i("liupeng",
							"jo.getString(KeyProtectedPlant.PLANT_NAME)"
									+ jo.getString(KeyProtectedPlant.PLANT_NAME));
					kpp.setPlantNames(jo
							.getString(KeyProtectedPlant.PLANT_NAME));
				} else {
					kpp.setPlantNames("0");
				}
				if (!jo.isNull(KeyProtectedPlant.PROTECTION_LEVEL)) {
					kpp.setProtectionLevel(jo
							.getString(KeyProtectedPlant.PROTECTION_LEVEL));
				}
				if (!jo.isNull(KeyProtectedPlant.STATUS)) {
					kpp.setStatus(jo.getString(KeyProtectedPlant.STATUS));
				}
				if (!jo.isNull(KeyProtectedPlant.TREE_AGE)) {
					kpp.setTreeAge(jo.getString(KeyProtectedPlant.TREE_AGE));
				}
				if (!jo.isNull(KeyProtectedPlant.LOCATION_ID)) {
					JSONObject joLocation = jo
							.getJSONObject(KeyProtectedPlant.LOCATION_ID);
					if (!joLocation.isNull(Location.LATITUDE)&&!joLocation.getString(Location.LATITUDE).equals("")) {
						Loaction l = new Loaction();
						if (!joLocation.isNull(Location.LONGITUDE)) {
							l.setLONGITUDE(joLocation
									.getDouble(Location.LONGITUDE));
						}
						if (!joLocation.isNull(Location.LATITUDE)) {
							l.setLATITUDE(joLocation
									.getDouble(Location.LATITUDE));
						}
						if (!joLocation.isNull(Location.ELEVATION)) {
							l.setELEVATION(joLocation
									.getDouble(Location.ELEVATION));
						}
						if (!joLocation.isNull(Location.TIME)) {
							l.setTIME(joLocation.getString(Location.TIME));
						}
						kpp.setLocation(l);
					}
				}
				if (!jo.isNull("affix") && !jo.getString("affix").equals("")) {
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

					kpp.setKppAccessory(accessorys);
				}
				kpps.add(kpp);
			}
		}
		return kpps;
	}
}
