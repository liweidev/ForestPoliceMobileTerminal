package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.FocusOfWildAnimal;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.Location;
import db.Database.ProtectedAnimals;

/**
 * @author liupeng 重点动物
 */
public class ProtectedAnimalsDao {

	private ForestDatabaseHelper dbHelper;

	private Context context;
	private static ProtectedAnimalsDao paDao;

	/**
	 * 
	 */
	public ProtectedAnimalsDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	public static ProtectedAnimalsDao init(Context ct) {
		if (paDao == null) {
			paDao = new ProtectedAnimalsDao(ct);
		}
		return paDao;

	}

	/*
	 * 插入
	 */

	public boolean insert(FocusOfWildAnimal fwa) {
		boolean flag = false;

		int proteceedAnimalId = -1;
		LocationDao ldao = new LocationDao(context);
		int locationId = ldao.insertInfo(fwa.getLocationId());

		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();

		if (locationId > 0) {
			Cursor cursor = null;
			try {
				ContentValues contentValues = new ContentValues();
				contentValues.put(ProtectedAnimals.COMPETENT_DEPARMENT,
						fwa.getAnimalName());
				contentValues.put(ProtectedAnimals.ACCOUNTABILITY_UNIT,
						fwa.getAccountabilityUnit());

				contentValues.put(ProtectedAnimals.CONTACT, fwa.getContact());
				contentValues.put(ProtectedAnimals.DR_AND_PN, fwa.getDrAndPn());
				contentValues.put(ProtectedAnimals.LOCATION_ID, locationId);
				contentValues.put(ProtectedAnimals.MAPO_PERSONNEL,
						fwa.getMapoPersonnel());
				contentValues.put(ProtectedAnimals.NOTE, fwa.getFowaLegend());
				contentValues.put(ProtectedAnimals.PROTECT_FILE_NUM,
						fwa.getProtectFileNum());
				contentValues.put(ProtectedAnimals.PROTECTED_ANIMALS_SERVER_ID,
						fwa.getServerId());
				contentValues.put(ProtectedAnimals.PROTECT_THE_WAY,
						fwa.getProtectTheWay());
				contentValues.put(ProtectedAnimals.PROTECTION_LEVEL,
						fwa.getProtectionLevel());
				long ok = db.insert(
						ProtectedAnimals.PROTECTED_ANIMALS_TABLE_NAME, null,
						contentValues);
				Log.i("liupeng", "insert ProtectedAnimals    :   OK   " + ok);

				if (-1 != ok) {
					flag = true;
				}

				String sql = "SELECT MAX("
						+ ProtectedAnimals.PROTECTED_ANIMALS_ID + ") FROM "
						+ ProtectedAnimals.PROTECTED_ANIMALS_TABLE_NAME;
				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					proteceedAnimalId = cursor.getInt(0);
				}
				
				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
				return flag;
			} finally {
				db.endTransaction();
				if (cursor != null) {
					cursor.close();
				}
			}

			if (fwa.getFowaAccessory() != null
					&& fwa.getFowaAccessory().size() > 0) {
				AttachmentDao dao = new AttachmentDao(context);
				for (int i = 0; i < fwa.getFowaAccessory().size(); i++) {
					fwa.getFowaAccessory()
							.get(i)
							.setTableId(
									ProtectedAnimals.PROTECTED_ANIMALS_TABLE_ID);
					fwa.getFowaAccessory().get(i).setRowId(proteceedAnimalId);
					flag = dao.insertInfo(fwa.getFowaAccessory().get(i));
				}
			}
		}
		return flag;
	}

	/**
	 * 删表
	 */
	public boolean delTable(int animalId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		try {
			int res = db.delete(ProtectedAnimals.PROTECTED_ANIMALS_TABLE_NAME,
					ProtectedAnimals.PROTECTED_ANIMALS_ID + "=?",
					new String[] { animalId + "" });
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
	public ArrayList<FocusOfWildAnimal> getAllInfos() {
		ArrayList<FocusOfWildAnimal> infos = new ArrayList<FocusOfWildAnimal>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {

			String sql = "select * from "
					+ ProtectedAnimals.PROTECTED_ANIMALS_TABLE_NAME
					+ " Left join " + Location.LOCATION_TABLE_NAME + " where "
					+ ProtectedAnimals.PROTECTED_ANIMALS_TABLE_NAME + "."
					+ ProtectedAnimals.LOCATION_ID + " = "
					+ Location.LOCATION_TABLE_NAME + "." + Location.LOCATION_ID;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				FocusOfWildAnimal info = new FocusOfWildAnimal();
				info.setAccountabilityUnit(cursor.getString(cursor
						.getColumnIndex(ProtectedAnimals.ACCOUNTABILITY_UNIT)));
				info.setAnimalName(cursor.getString(cursor
						.getColumnIndex(ProtectedAnimals.COMPETENT_DEPARMENT)));
				info.setContact(cursor.getString(cursor
						.getColumnIndex(ProtectedAnimals.CONTACT)));
				info.setDrAndPn(cursor.getString(cursor
						.getColumnIndex(ProtectedAnimals.DR_AND_PN)));
				info.setFowaLegend(cursor.getString(cursor
						.getColumnIndex(ProtectedAnimals.NOTE)));
				info.setMapoPersonnel(cursor.getString(cursor
						.getColumnIndex(ProtectedAnimals.MAPO_PERSONNEL)));
				info.setProtectFileNum(cursor.getString(cursor
						.getColumnIndex(ProtectedAnimals.PROTECT_FILE_NUM)));
				info.setProtectionLevel(cursor.getString(cursor
						.getColumnIndex(ProtectedAnimals.PROTECTION_LEVEL)));
				info.setProtectTheWay(cursor.getString(cursor
						.getColumnIndex(ProtectedAnimals.PROTECT_THE_WAY)));
				info.setServerId(cursor.getInt(cursor
						.getColumnIndex(ProtectedAnimals.PROTECTED_ANIMALS_SERVER_ID)));
				info.setFowaId(cursor.getInt(cursor
						.getColumnIndex(ProtectedAnimals.PROTECTED_ANIMALS_ID)));
				Loaction l = new Loaction();
				l.setLocation_id(cursor.getInt(cursor
						.getColumnIndex(Location.LOCATION_ID)));
				l.setELEVATION(cursor.getDouble(cursor
						.getColumnIndex(Location.ELEVATION)));
				l.setLATITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LATITUDE)));
				l.setLONGITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LONGITUDE)));
				info.setLocationId(l);

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
			int rowid = infos.get(i).getFowaId();
			AttachmentDao dao = new AttachmentDao(context);
			ArrayList<Accessory> list = dao.getInfosById(
					ProtectedAnimals.PROTECTED_ANIMALS_TABLE_ID, rowid);
			infos.get(i).setFowaAccessory(list);
		}

		return infos;
	}

	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM "
				+ ProtectedAnimals.PROTECTED_ANIMALS_TABLE_NAME;

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
	public static String getJson(FocusOfWildAnimal fwa, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("userId", userId);
			jo.put("tableId", ProtectedAnimals.PROTECTED_ANIMALS_TABLE_ID);
			jo.put(ProtectedAnimals.ACCOUNTABILITY_UNIT,
					fwa.getAccountabilityUnit());
			jo.put(ProtectedAnimals.COMPETENT_DEPARMENT, fwa.getAnimalName());
			jo.put(ProtectedAnimals.PROTECTED_ANIMALS_SERVER_ID,
					fwa.getServerId() + "");
			jo.put(ProtectedAnimals.CONTACT, fwa.getContact());
			jo.put(ProtectedAnimals.DR_AND_PN, fwa.getDrAndPn());
			jo.put(ProtectedAnimals.MAPO_PERSONNEL, fwa.getMapoPersonnel());
			jo.put(ProtectedAnimals.NOTE, fwa.getFowaLegend());
			jo.put(ProtectedAnimals.PROTECT_FILE_NUM, fwa.getProtectFileNum());
			jo.put(ProtectedAnimals.PROTECT_THE_WAY, fwa.getProtectTheWay());
			jo.put(ProtectedAnimals.PROTECTION_LEVEL, fwa.getProtectionLevel());

			JSONObject locationJo = new JSONObject();
			locationJo.put(Location.ELEVATION, fwa.getLocationId()
					.getELEVATION());

			locationJo
					.put(Location.LATITUDE, fwa.getLocationId().getLATITUDE());

			locationJo.put(Location.LONGITUDE, fwa.getLocationId()
					.getLONGITUDE());

			jo.put(ProtectedAnimals.LOCATION_ID, locationJo.toString());
			if (fwa.getFowaAccessory() != null && fwa.getFowaAccessory().size() > 0) {
				JSONArray ja = new JSONArray();
				for (int i = 0; i < fwa.getFowaAccessory().size(); i++) {
					JSONObject json = AttachmentDao.getJson(fwa.getFowaAccessory()
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
	 * 通过对象获取参数对象
	 * 
	 * @param
	 * @param
	 * @return
	 */
	/*public static AjaxParams getParams(FocusOfWildAnimal fwa, int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId + "");
		params.put("tableId", ProtectedAnimals.PROTECTED_ANIMALS_TABLE_ID + "");
		if (fwa.getAccountabilityUnit() != null
				&& !fwa.getAccountabilityUnit().equals("")) {
			params.put(ProtectedAnimals.ACCOUNTABILITY_UNIT,
					fwa.getAccountabilityUnit());
		}
		if (fwa.getAnimalName() != null && !fwa.getAnimalName().equals("")) {
			params.put(ProtectedAnimals.COMPETENT_DEPARMENT,
					fwa.getAnimalName());
		}
		params.put(ProtectedAnimals.PROTECTED_ANIMALS_SERVER_ID,
				fwa.getServerId() + "");
		if (fwa.getContact() != null && !fwa.getContact().equals("")) {
			params.put(ProtectedAnimals.CONTACT, fwa.getContact());
		}
		if (fwa.getDrAndPn() != null && !fwa.getDrAndPn().equals("")) {
			params.put(ProtectedAnimals.DR_AND_PN, fwa.getDrAndPn());
		}
		if (fwa.getMapoPersonnel() != null
				&& !fwa.getMapoPersonnel().equals("")) {
			params.put(ProtectedAnimals.MAPO_PERSONNEL, fwa.getMapoPersonnel());
		}
		if (fwa.getFowaLegend() != null && !fwa.getFowaLegend().equals("")) {
			params.put(ProtectedAnimals.NOTE, fwa.getFowaLegend());
		}
		if (fwa.getProtectFileNum() != null
				&& !fwa.getProtectFileNum().equals("")) {
			params.put(ProtectedAnimals.PROTECT_FILE_NUM,
					fwa.getProtectFileNum());
		}
		if (fwa.getProtectTheWay() != null
				&& !fwa.getProtectTheWay().equals("")) {
			params.put(ProtectedAnimals.PROTECT_THE_WAY, fwa.getProtectTheWay());
		}
		if (fwa.getProtectionLevel() != null
				&& !fwa.getProtectionLevel().equals("")) {
			params.put(ProtectedAnimals.PROTECTION_LEVEL,
					fwa.getProtectionLevel());
		}
		if (fwa.getLocationId() != null) {
			JSONObject locationJo = new JSONObject();
			try {
				locationJo.put(Location.ELEVATION, fwa.getLocationId()
						.getELEVATION());
				locationJo.put(Location.LATITUDE, fwa.getLocationId()
						.getLATITUDE());
				locationJo.put(Location.LONGITUDE, fwa.getLocationId()
						.getLONGITUDE());
				locationJo.put("Time", fwa.getLocationId().getTIME());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			params.put(ForestPatrolWork.LOCATION_ID, locationJo.toString());
		}
		if (fwa.getFowaAccessory() != null && fwa.getFowaAccessory().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < fwa.getFowaAccessory().size(); i++) {
				JSONObject json = AttachmentDao.getJson(fwa.getFowaAccessory()
						.get(i));
				ja.put(json);
			}
			params.put("flist", ja.toString());
		}
		params.put("fileType", "1");
		return params;
	}*/

	/*
	 * 根据JSON获取对象
	 * 
	 * public static ArrayList<FocusOfWildAnimal> getObject(String json) throws
	 * JSONException { JSONArray ja = null; ArrayList<FocusOfWildAnimal> fowas =
	 * null; if (json != null && !json.equals("anyType{}")) { fowas = new
	 * ArrayList<FocusOfWildAnimal>(); ja = new JSONArray(json); for (int i = 0;
	 * i < ja.length(); i++) { JSONObject jo = ja.getJSONObject(i);
	 * FocusOfWildAnimal fowa = new FocusOfWildAnimal(); if
	 * (!jo.isNull(ProtectedAnimals.PROTECTED_ANIMALS_ID)) {
	 * fowa.setFowaId(Integer.parseInt(jo
	 * .getString(ProtectedAnimals.PROTECTED_ANIMALS_ID))); } if
	 * (!jo.isNull(ProtectedAnimals.ACCOUNTABILITY_UNIT)) {
	 * fowa.setAccountabilityUnit(jo
	 * .getString(ProtectedAnimals.ACCOUNTABILITY_UNIT)); } if
	 * (!jo.isNull(ProtectedAnimals.PROTECTED_ANIMALS_SERVER_ID)) {
	 * fowa.setServerId(Integer.parseInt(jo
	 * .getString(ProtectedAnimals.PROTECTED_ANIMALS_SERVER_ID))); } if
	 * (!jo.isNull(ProtectedAnimals.COMPETENT_DEPARMENT)) {//动物名称
	 * fowa.setAnimalName(jo .getString(ProtectedAnimals.COMPETENT_DEPARMENT));
	 * }else{ fowa.setAnimalName("0"); } if
	 * (!jo.isNull(ProtectedAnimals.CONTACT)) {
	 * fowa.setContact(jo.getString(ProtectedAnimals.CONTACT)); } if
	 * (!jo.isNull(ProtectedAnimals.DR_AND_PN)) {
	 * fowa.setDrAndPn(jo.getString(ProtectedAnimals.DR_AND_PN)); } if
	 * (!jo.isNull(ProtectedAnimals.NOTE)) {
	 * fowa.setFowaLegend(jo.getString(ProtectedAnimals.NOTE)); } if
	 * (!jo.isNull(ProtectedAnimals.MAPO_PERSONNEL)) { fowa.setMapoPersonnel(jo
	 * .getString(ProtectedAnimals.MAPO_PERSONNEL)); } if
	 * (!jo.isNull(ProtectedAnimals.PROTECT_FILE_NUM)) {
	 * fowa.setProtectFileNum(jo .getString(ProtectedAnimals.PROTECT_FILE_NUM));
	 * } if (!jo.isNull(ProtectedAnimals.PROTECTION_LEVEL)) {
	 * fowa.setProtectionLevel(jo
	 * .getString(ProtectedAnimals.PROTECTION_LEVEL)); } if
	 * (!jo.isNull(ProtectedAnimals.PROTECT_THE_WAY)) { fowa.setProtectTheWay(jo
	 * .getString(ProtectedAnimals.PROTECT_THE_WAY)); } if
	 * (!jo.isNull(ProtectedAnimals.LOCATION_ID)) { JSONObject joLocation = jo
	 * .getJSONObject(ProtectedAnimals.LOCATION_ID); if
	 * (joLocation.getDouble(Location.LATITUDE) != 0) { Loaction l = new
	 * Loaction(); l.setELEVATION(joLocation.getDouble(Location.ELEVATION));
	 * l.setLATITUDE(joLocation.getDouble(Location.LATITUDE));
	 * l.setLONGITUDE(joLocation.getDouble(Location.LONGITUDE));
	 * fowa.setLocationId(l); } }
	 * 
	 * if (!jo.isNull("affix")) { ArrayList<Accessory> accessorys = new
	 * ArrayList<Accessory>(); JSONArray jaaccessorys = new JSONArray(
	 * jo.getString("affix")); for (int j = 0; j < jaaccessorys.length(); j++) {
	 * JSONObject joAttachment = jaaccessorys.getJSONObject(j); Accessory
	 * accessory = new Accessory();
	 * 
	 * if (!joAttachment.isNull("url")) { String url =
	 * joAttachment.getString("url");
	 * accessory.setAccessoryPath(url.replace("../..",
	 * Constant.URL+Constant.WEB_PORT)); } accessorys.add(accessory); }
	 * fowa.setFowaAccessory(accessorys); } fowas.add(fowa); } } return fowas; }
	 */

	/*
	 * 根据JSON获取对象
	 */
	public ArrayList<FocusOfWildAnimal> getObject(String json)
			throws JSONException {
		JSONObject jso = new JSONObject(json);
		ArrayList<FocusOfWildAnimal> fowas = null;
		int error = jso.getInt("Error");
		if (error != 0) {
			return null;
		} else if (error == 0) {
			fowas = new ArrayList<FocusOfWildAnimal>();
			JSONArray ja = jso.getJSONArray("AnimalList");
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				FocusOfWildAnimal fowa = new FocusOfWildAnimal();
				if (!jo.isNull(ProtectedAnimals.PROTECTED_ANIMALS_ID)) {
					fowa.setFowaId(Integer.parseInt(jo
							.getString(ProtectedAnimals.PROTECTED_ANIMALS_ID)));
				}
				if (!jo.isNull(ProtectedAnimals.ACCOUNTABILITY_UNIT)) {
					fowa.setAccountabilityUnit(jo
							.getString(ProtectedAnimals.ACCOUNTABILITY_UNIT));
				}
				if (!jo.isNull(ProtectedAnimals.PROTECTED_ANIMALS_SERVER_ID)) {
					fowa.setServerId(Integer.parseInt(jo
							.getString(ProtectedAnimals.PROTECTED_ANIMALS_SERVER_ID)));
				}
				if (!jo.isNull(ProtectedAnimals.COMPETENT_DEPARMENT)) {// 动物名称
					fowa.setAnimalName(jo
							.getString(ProtectedAnimals.COMPETENT_DEPARMENT));
				} else {
					fowa.setAnimalName("0");
				}
				if (!jo.isNull(ProtectedAnimals.CONTACT)) {
					fowa.setContact(jo.getString(ProtectedAnimals.CONTACT));
				}
				if (!jo.isNull(ProtectedAnimals.DR_AND_PN)) {
					fowa.setDrAndPn(jo.getString(ProtectedAnimals.DR_AND_PN));
				}
				if (!jo.isNull(ProtectedAnimals.NOTE)) {
					fowa.setFowaLegend(jo.getString(ProtectedAnimals.NOTE));
				}
				if (!jo.isNull(ProtectedAnimals.MAPO_PERSONNEL)) {
					fowa.setMapoPersonnel(jo
							.getString(ProtectedAnimals.MAPO_PERSONNEL));
				}
				if (!jo.isNull(ProtectedAnimals.PROTECT_FILE_NUM)) {
					fowa.setProtectFileNum(jo
							.getString(ProtectedAnimals.PROTECT_FILE_NUM));
				}
				if (!jo.isNull(ProtectedAnimals.PROTECTION_LEVEL)) {
					fowa.setProtectionLevel(jo
							.getString(ProtectedAnimals.PROTECTION_LEVEL));
				}
				if (!jo.isNull(ProtectedAnimals.PROTECT_THE_WAY)) {
					fowa.setProtectTheWay(jo
							.getString(ProtectedAnimals.PROTECT_THE_WAY));
				}
				if (!jo.isNull(ProtectedAnimals.LOCATION_ID)) {
					JSONObject joLocation = jo
							.getJSONObject(ProtectedAnimals.LOCATION_ID);
					if (joLocation.getDouble(Location.LATITUDE) != 0) {
						Loaction l = new Loaction();
						l.setELEVATION(joLocation.getDouble(Location.ELEVATION));
						l.setLATITUDE(joLocation.getDouble(Location.LATITUDE));
						l.setLONGITUDE(joLocation.getDouble(Location.LONGITUDE));
						fowa.setLocationId(l);
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
					fowa.setFowaAccessory(accessorys);
				}
				fowas.add(fowa);
			}
		}
		return fowas;
	}

}
