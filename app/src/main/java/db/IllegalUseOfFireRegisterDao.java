package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.IllegalFireBehavior;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.IllegalUseOfFireRegister;
import db.Database.Location;

/**
 * 违法用火行为人------防火工作
 * 
 * @author Zhengtiantian
 * 
 */
public class IllegalUseOfFireRegisterDao {

	private ForestDatabaseHelper dbHelper;
	private Context context;

	public IllegalUseOfFireRegisterDao(Context context) {
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	/**
	 * 插入数据
	 */
	public boolean insertInfo(IllegalFireBehavior lFbor) {
		boolean flag = false;
		int iFborId = -1;
		LocationDao lDao = new LocationDao(context);
		int locationId = lDao.insertInfo(lFbor.getLoaction());
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		if (locationId > 0) {
			Cursor cursor = null;
			try {
				ContentValues contentValues = new ContentValues();
				contentValues.put(IllegalUseOfFireRegister.LOCATION_ID,
						locationId);
				contentValues.put(IllegalUseOfFireRegister.NOTE,
						lFbor.getIfbLegend());
				contentValues.put(IllegalUseOfFireRegister.VIOLATOR,
						lFbor.getViolator());
				contentValues.put(IllegalUseOfFireRegister.VIOLATOR_ID_CARD,
						lFbor.getViolatorIDCard());
				contentValues.put(IllegalUseOfFireRegister.VIOLATOR_ADD,
						lFbor.getViolatorAdd());
				contentValues.put(IllegalUseOfFireRegister.LUOF_TIME,
						lFbor.getLuofTime());
				contentValues.put(IllegalUseOfFireRegister.PROCESSING_RESULTS,
						lFbor.getProcessingResults());
				contentValues.put(IllegalUseOfFireRegister.PENALTIES_TIME,
						lFbor.getPenaltiesTime());
				contentValues.put(IllegalUseOfFireRegister.ORGANIZED_POLICE,
						lFbor.getOrganizedPolice());

				long ok = db
						.insert(IllegalUseOfFireRegister.ILLEGAL_USE_FIRE_REGISTER_TABLE_NAME,
								null, contentValues);

				if (-1 != ok) {
					flag = true;
				}
				String sql = "SELECT MAX("
						+ IllegalUseOfFireRegister.ILLEGAL_USE_FIRE_REGISTER_ID
						+ ") FROM "
						+ IllegalUseOfFireRegister.ILLEGAL_USE_FIRE_REGISTER_TABLE_NAME;

				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					iFborId = cursor.getInt(0);
				}
				db.setTransactionSuccessful();
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
			} finally {
				db.endTransaction();
				if (cursor != null) {
					cursor.close();
				}
			}
			if (lFbor.getIfbAccessory() != null
					&& lFbor.getIfbAccessory().size() > 0) {
				for (int i = 0; i < lFbor.getIfbAccessory().size(); i++) {
					AttachmentDao dao = new AttachmentDao(context);
					lFbor.getIfbAccessory()
							.get(i)
							.setTableId(
									IllegalUseOfFireRegister.ILLEGAL_USE_FIRE_REGISTER_TABLE_ID);
					lFbor.getIfbAccessory().get(i).setRowId(iFborId);
					flag = dao.insertInfo(lFbor.getIfbAccessory().get(i));
				}
			}
		}
		return flag;
	}

	/**
	 * 删除表
	 */
	public boolean delTable(String illId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		try {
			int res = db
					.delete(IllegalUseOfFireRegister.ILLEGAL_USE_FIRE_REGISTER_TABLE_NAME,
							IllegalUseOfFireRegister.ILLEGAL_USE_FIRE_REGISTER_ID
									+ "=?", new String[] { illId });

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
	 */

	public ArrayList<IllegalFireBehavior> getAllInfos() {

		ArrayList<IllegalFireBehavior> infos = new ArrayList<IllegalFireBehavior>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			String sql = "select * from "
					+ IllegalUseOfFireRegister.ILLEGAL_USE_FIRE_REGISTER_TABLE_NAME
					+ " Left join "
					+ Location.LOCATION_TABLE_NAME
					+ " where "
					+ IllegalUseOfFireRegister.ILLEGAL_USE_FIRE_REGISTER_TABLE_NAME
					+ "." + IllegalUseOfFireRegister.LOCATION_ID + "="
					+ Location.LOCATION_TABLE_NAME + "." + Location.LOCATION_ID;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);

			while (cursor.moveToNext()) {
				IllegalFireBehavior fbor = new IllegalFireBehavior();
				fbor.setIfbId(cursor.getInt(cursor
						.getColumnIndex(IllegalUseOfFireRegister.ILLEGAL_USE_FIRE_REGISTER_ID)));
				fbor.setIfbLegend(cursor.getString(cursor
						.getColumnIndex(IllegalUseOfFireRegister.NOTE)));
				fbor.setViolator(cursor.getString(cursor
						.getColumnIndex(IllegalUseOfFireRegister.VIOLATOR)));
				fbor.setViolatorIDCard(cursor.getString(cursor
						.getColumnIndex(IllegalUseOfFireRegister.VIOLATOR_ID_CARD)));
				fbor.setViolatorAdd(cursor.getString(cursor
						.getColumnIndex(IllegalUseOfFireRegister.VIOLATOR_ADD)));
				fbor.setLuofTime(cursor.getString(cursor
						.getColumnIndex(IllegalUseOfFireRegister.LUOF_TIME)));
				fbor.setProcessingResults(cursor.getString(cursor
						.getColumnIndex(IllegalUseOfFireRegister.PROCESSING_RESULTS)));
				fbor.setPenaltiesTime(cursor.getString(cursor
						.getColumnIndex(IllegalUseOfFireRegister.PENALTIES_TIME)));
				fbor.setOrganizedPolice(cursor.getString(cursor
						.getColumnIndex(IllegalUseOfFireRegister.ORGANIZED_POLICE)));

				Loaction l = new Loaction();
				l.setLocation_id(cursor.getInt(cursor
						.getColumnIndex(Location.LOCATION_ID)));
				l.setELEVATION(cursor.getDouble(cursor
						.getColumnIndex(Location.ELEVATION)));
				l.setLATITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LATITUDE)));
				l.setLONGITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LONGITUDE)));
				fbor.setLoaction(l);

				infos.add(fbor);
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

		AttachmentDao aDao = null;
		for (int i = 0; i < infos.size(); i++) {
			int borId = infos.get(i).getIfbId();
			aDao = new AttachmentDao(context);
			ArrayList<Accessory> list = aDao
					.getInfosById(
							IllegalUseOfFireRegister.ILLEGAL_USE_FIRE_REGISTER_TABLE_ID,
							borId);
			infos.get(i).setIfbAccessory(list);
		}
		return infos;
	}

	/**
	 * 获取数量
	 */

	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM "
				+ IllegalUseOfFireRegister.ILLEGAL_USE_FIRE_REGISTER_TABLE_NAME;

		try {
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				count = cursor.getInt(0);
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
		return count;
	}

	/**
	 * 根据对象获取JSON
	 */
	public static String getJson(IllegalFireBehavior ifbar, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("userId", userId);
			jo.put("tableId",
					IllegalUseOfFireRegister.ILLEGAL_USE_FIRE_REGISTER_TABLE_ID);
			jo.put(IllegalUseOfFireRegister.VIOLATOR, ifbar.getViolator());
			jo.put(IllegalUseOfFireRegister.VIOLATOR_ID_CARD,
					ifbar.getViolatorIDCard());
			jo.put(IllegalUseOfFireRegister.VIOLATOR_ADD,
					ifbar.getViolatorAdd());
			jo.put(IllegalUseOfFireRegister.LUOF_TIME, ifbar.getLuofTime());
			jo.put(IllegalUseOfFireRegister.PROCESSING_RESULTS,
					ifbar.getProcessingResults());
			jo.put(IllegalUseOfFireRegister.PENALTIES_TIME,
					ifbar.getPenaltiesTime());
			jo.put(IllegalUseOfFireRegister.ORGANIZED_POLICE,
					ifbar.getOrganizedPolice());
			jo.put(IllegalUseOfFireRegister.NOTE, ifbar.getIfbLegend());

			JSONObject joLocation = new JSONObject();
			joLocation.put(Location.ELEVATION, ifbar.getLoaction()
					.getELEVATION());
			joLocation
					.put(Location.LATITUDE, ifbar.getLoaction().getLATITUDE());
			joLocation.put(Location.LONGITUDE, ifbar.getLoaction()
					.getLONGITUDE());
			jo.put(IllegalUseOfFireRegister.LOCATION_ID, joLocation.toString());
			
			if (ifbar.getIfbAccessory() != null && ifbar.getIfbAccessory().size() > 0) {
				JSONArray ja = new JSONArray();
				for (int i = 0; i < ifbar.getIfbAccessory().size(); i++) {
					JSONObject json = AttachmentDao.getJson(ifbar.getIfbAccessory()
							.get(i));
					ja.put(json);
				}
				jo.put("flist", ja.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo.toString();

	}

	/**
	 * 根据对象获取API参数
	 * 
	 * @param
	 * @param userId
	 * @return
	 */
	/*public static AjaxParams getParams(IllegalFireBehavior iuof, int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId + "");
		params.put("tableId",
				IllegalUseOfFireRegister.ILLEGAL_USE_FIRE_REGISTER_TABLE_ID
						+ "");
		if (iuof.getViolator() != null && !iuof.getViolator().equals("")) {
			params.put(IllegalUseOfFireRegister.VIOLATOR, iuof.getViolator());
		}
		if (iuof.getViolatorIDCard() != null
				&& !iuof.getViolatorIDCard().equals("")) {
			params.put(IllegalUseOfFireRegister.VIOLATOR_ID_CARD,
					iuof.getViolatorIDCard());
		}
		if (iuof.getViolatorAdd() != null && !iuof.getViolatorAdd().equals("")) {
			params.put(IllegalUseOfFireRegister.VIOLATOR_ADD,
					iuof.getViolatorAdd());
		}
		if (iuof.getLuofTime() != null && !iuof.getLuofTime().equals("")) {
			params.put(IllegalUseOfFireRegister.LUOF_TIME, iuof.getLuofTime());
		}
		if (iuof.getProcessingResults() != null
				&& !iuof.getProcessingResults().equals("")) {
			params.put(IllegalUseOfFireRegister.PROCESSING_RESULTS,
					iuof.getProcessingResults());
		}
		if (iuof.getPenaltiesTime() != null
				&& !iuof.getPenaltiesTime().equals("")) {
			params.put(IllegalUseOfFireRegister.PENALTIES_TIME,
					iuof.getPenaltiesTime());
		}
		if (iuof.getOrganizedPolice() != null
				&& !iuof.getOrganizedPolice().equals("")) {
			params.put(IllegalUseOfFireRegister.ORGANIZED_POLICE,
					iuof.getOrganizedPolice());
		}
		if (iuof.getIfbLegend() != null && !iuof.getIfbLegend().equals("")) {
			params.put(IllegalUseOfFireRegister.NOTE, iuof.getIfbLegend());
		}
		if (iuof.getLoaction() != null) {
			JSONObject locationJo = new JSONObject();
			try {
				locationJo.put(Location.ELEVATION, iuof.getLoaction()
						.getELEVATION());
				locationJo.put(Location.LATITUDE, iuof.getLoaction()
						.getLATITUDE());
				locationJo.put(Location.LONGITUDE, iuof.getLoaction()
						.getLONGITUDE());
				locationJo.put("Time", iuof.getLoaction().getTIME());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			params.put(IllegalUseOfFireRegister.LOCATION_ID,
					locationJo.toString());
		}
		if (iuof.getIfbAccessory() != null && iuof.getIfbAccessory().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < iuof.getIfbAccessory().size(); i++) {
				JSONObject json = AttachmentDao.getJson(iuof.getIfbAccessory()
						.get(i));
				ja.put(json);
			}
			params.put("flist", ja.toString());
		}
		params.put("fileType", "1");
		return params;
	}*/
}
