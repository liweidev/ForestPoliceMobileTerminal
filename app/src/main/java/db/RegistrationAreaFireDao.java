package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.RegistrationAreaFireEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.Location;
import db.Database.RegistrationAreaFire;

/**
 * 发生火情登记
 * 
 * @author xingyimin
 * 
 */
public class RegistrationAreaFireDao {
	private ForestDatabaseHelper dbHelper;

	private Context context;

	public RegistrationAreaFireDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	/*
	 * 插入
	 */
	public boolean insertInfo(RegistrationAreaFireEntity raf) {

		boolean flag = false;

		int RegistrationAreaFireId = -1;
		LocationDao ldao = new LocationDao(context);
		int locationId = ldao.insertInfo(raf.getLocation());
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//

		if (locationId > 0) {
			Cursor cursor = null;
			try {
				ContentValues contentValues = new ContentValues();
				contentValues.put(RegistrationAreaFire.NOTE, raf.getNote()
						.toString());
				contentValues.put(RegistrationAreaFire.BURNED_AREA,
						raf.getBurnedArea());
				contentValues
						.put(RegistrationAreaFire.EF_TIME, raf.getEfTime());
				contentValues.put(RegistrationAreaFire.LOCATION_ID, locationId);
				contentValues.put(RegistrationAreaFire.RTSF_TIME,
						raf.getRtsfTime());
				contentValues.put(RegistrationAreaFire.TFO_PLACE,
						raf.getTfoPlace());

				long ok = db.insert(
						RegistrationAreaFire.REGISTRATION_AREA_FIRE_TABLE_NAME,
						null, contentValues);
				// System.out.println("insert is complete..." + ok);
				if (-1 != ok) {
					flag = true;
				}
				String sql = "SELECT MAX("
						+ RegistrationAreaFire.REGISTRATION_AREA_FIRE_ID
						+ ") FROM "
						+ RegistrationAreaFire.REGISTRATION_AREA_FIRE_TABLE_NAME;
				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					RegistrationAreaFireId = cursor.getInt(0);
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
			if (raf.getRafAccessory() != null
					&& raf.getRafAccessory().size() > 0) {
				AttachmentDao dao = new AttachmentDao(context);
				for (int i = 0; i < raf.getRafAccessory().size(); i++) {
					raf.getRafAccessory()
							.get(i)
							.setTableId(
									RegistrationAreaFire.REGISTRATION_AREA_FIRE_TABLE_ID);
					raf.getRafAccessory().get(i)
							.setRowId(RegistrationAreaFireId);
					flag = dao.insertInfo(raf.getRafAccessory().get(i));
				}
			}
		}
		return flag;

	}

	/**
	 * 删表
	 */
	public boolean delTable(String rafId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db.delete(
					RegistrationAreaFire.REGISTRATION_AREA_FIRE_TABLE_NAME,
					RegistrationAreaFire.REGISTRATION_AREA_FIRE_ID + "=?",
					new String[] { rafId });
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
	public ArrayList<RegistrationAreaFireEntity> getAllInfos() {
		ArrayList<RegistrationAreaFireEntity> infos = new ArrayList<RegistrationAreaFireEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		try {

			String sql = "select * from "
					+ RegistrationAreaFire.REGISTRATION_AREA_FIRE_TABLE_NAME
					+ " Left join " + Location.LOCATION_TABLE_NAME + " where "
					+ RegistrationAreaFire.REGISTRATION_AREA_FIRE_TABLE_NAME
					+ "." + RegistrationAreaFire.LOCATION_ID + " = "
					+ Location.LOCATION_TABLE_NAME + "." + Location.LOCATION_ID;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				RegistrationAreaFireEntity info = new RegistrationAreaFireEntity();
				info.setBurnedArea(cursor.getString(cursor
						.getColumnIndex(RegistrationAreaFire.BURNED_AREA)));
				info.setEfTime(cursor.getString(cursor
						.getColumnIndex(RegistrationAreaFire.EF_TIME)));
				info.setNote(cursor.getString(cursor
						.getColumnIndex(RegistrationAreaFire.NOTE)));
				info.setRafId(cursor.getInt(cursor
						.getColumnIndex(RegistrationAreaFire.REGISTRATION_AREA_FIRE_ID)));
				info.setRtsfTime(cursor.getString(cursor
						.getColumnIndex(RegistrationAreaFire.RTSF_TIME)));
				info.setTfoPlace(cursor.getString(cursor
						.getColumnIndex(RegistrationAreaFire.TFO_PLACE)));
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
		AttachmentDao dao = null;
		for (int i = 0; i < infos.size(); i++) {
			int rowid = infos.get(i).getRafId();
			dao = new AttachmentDao(context);
			ArrayList<Accessory> list = dao
					.getInfosById(
							RegistrationAreaFire.REGISTRATION_AREA_FIRE_TABLE_ID,
							rowid);
			infos.get(i).setRafAccessory(list);
		}

		return infos;
	}

	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM "
				+ RegistrationAreaFire.REGISTRATION_AREA_FIRE_TABLE_NAME;

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
	public static String getJson(RegistrationAreaFireEntity rafe, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("userId", userId);
			jo.put("tableId",
					RegistrationAreaFire.REGISTRATION_AREA_FIRE_TABLE_ID);
			jo.put(RegistrationAreaFire.BURNED_AREA, rafe.getBurnedArea());
			jo.put(RegistrationAreaFire.EF_TIME, rafe.getEfTime());
			jo.put(RegistrationAreaFire.NOTE, rafe.getNote());
			jo.put(RegistrationAreaFire.RTSF_TIME, rafe.getRtsfTime());
			jo.put(RegistrationAreaFire.TFO_PLACE, rafe.getTfoPlace());

			JSONObject joLocation = new JSONObject();
			joLocation.put(Location.ELEVATION, rafe.getLocation()
					.getELEVATION());
			joLocation.put(Location.LATITUDE, rafe.getLocation().getLATITUDE());
			joLocation.put(Location.LONGITUDE, rafe.getLocation()
					.getLONGITUDE());

			jo.put(RegistrationAreaFire.LOCATION_ID, joLocation.toString());
			if (rafe.getRafAccessory() != null && rafe.getRafAccessory().size() > 0) {
				JSONArray ja = new JSONArray();
				for (int i = 0; i < rafe.getRafAccessory().size(); i++) {
					JSONObject json = AttachmentDao.getJson(rafe.getRafAccessory()
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
	 * @param raf
	 * @param userId
	 * @return
	 */
	/*public static AjaxParams getParams(RegistrationAreaFireEntity raf,
			int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId + "");
		params.put("tableId",
				RegistrationAreaFire.REGISTRATION_AREA_FIRE_TABLE_ID + "");
		if (raf.getBurnedArea() != null && !raf.getBurnedArea().equals("")) {
			params.put(RegistrationAreaFire.BURNED_AREA, raf.getBurnedArea());
		}
		if (raf.getEfTime() != null && !raf.getEfTime().equals("")) {
			params.put(RegistrationAreaFire.EF_TIME, raf.getEfTime());
		}
		if (raf.getNote() != null && !raf.getNote().equals("")) {
			params.put(RegistrationAreaFire.NOTE, raf.getNote());
		}
		if (raf.getRtsfTime() != null && !raf.getRtsfTime().equals("")) {
			params.put(RegistrationAreaFire.RTSF_TIME, raf.getRtsfTime());
		}
		if (raf.getTfoPlace() != null && !raf.getTfoPlace().equals("")) {
			params.put(RegistrationAreaFire.TFO_PLACE, raf.getTfoPlace());
		}
		if (raf.getLocation() != null) {
			JSONObject locationJo = new JSONObject();
			try {
				locationJo.put(Location.ELEVATION, raf.getLocation()
						.getELEVATION());
				locationJo.put(Location.LATITUDE, raf.getLocation()
						.getLATITUDE());
				locationJo.put(Location.LONGITUDE, raf.getLocation()
						.getLONGITUDE());
				locationJo.put("Time", raf.getLocation().getTIME());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			params.put(RegistrationAreaFire.LOCATION_ID, locationJo.toString());
		}
		if (raf.getRafAccessory() != null && raf.getRafAccessory().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < raf.getRafAccessory().size(); i++) {
				JSONObject json = AttachmentDao.getJson(raf.getRafAccessory()
						.get(i));
				ja.put(json);
			}
			params.put("flist", ja.toString());
		}
		params.put("fileType", "1");
		return params;
	}*/
}
