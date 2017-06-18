package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.LawEnforcementPatrol;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.LawEnforcementInspection;
import db.Database.Location;
import db.Database.ReceiveAndDisposeAlarm;

/**
 * 林区治安防控巡逻检查记录
 * 
 * @author xingyimin
 * 
 */
public class LawEnforcementInspectionDao {
	private ForestDatabaseHelper dbHelper;

	private Context context;

	/**
	 * 
	 */
	public LawEnforcementInspectionDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	/*
	 * 插入
	 */
	public boolean insertInfo(LawEnforcementPatrol lep) {

		boolean flag = false;

		int lepId = -1;
		LocationDao lDao = new LocationDao(context);
		int locationId = lDao.insertInfo(lep.getLoaction());
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//

		if (locationId > 0) {
			Cursor cursor = null;
			try {
				ContentValues contentValues = new ContentValues();
				contentValues.put(LawEnforcementInspection.LOCATION_ID,
						locationId);
				contentValues.put(LawEnforcementInspection.NOTE,
						lep.getLeLegend());
				contentValues.put(LawEnforcementInspection.LE_UNIT,
						lep.getLeUnit());
				contentValues.put(LawEnforcementInspection.LE_TIME,
						lep.getLeTime());
				contentValues.put(LawEnforcementInspection.LE_VEHICLE,
						lep.getLeVehicle());
				contentValues.put(LawEnforcementInspection.LE_INSPECTORS,
						lep.getLeInspectors());
				contentValues
						.put(LawEnforcementInspection.LE_NO, lep.getLeNo());
				contentValues.put(LawEnforcementInspection.LE_CONTENT,
						lep.getLeContents());
				contentValues.put(LawEnforcementInspection.LE_SITUATION,
						lep.getLeSituation());
				contentValues.put(LawEnforcementInspection.LE_SIGNATURE,
						lep.getLeSignature());
				contentValues.put(LawEnforcementInspection.LE_PHONE,
						lep.getLePhone());
				contentValues.put(LawEnforcementInspection.le_signature_bool, lep.getLe_signature_bool());
				long ok = db
						.insert(LawEnforcementInspection.LAW_ENFORCEMENT_INSPECTION_TABLE_NAME,
								null, contentValues);
				// System.out.println("insert is complete..." + ok);
				if (-1 != ok) {
					flag = true;
				}
				String sql = "SELECT MAX("
						+ LawEnforcementInspection.LAW_ENFORCEMENT_INSPECTION_ID
						+ ") FROM "
						+ LawEnforcementInspection.LAW_ENFORCEMENT_INSPECTION_TABLE_NAME;
				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					lepId = cursor.getInt(0);
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
			if (lep.getLeAccessory() != null && lep.getLeAccessory().size() > 0) {
				AttachmentDao dao = new AttachmentDao(context);
				for (int i = 0; i < lep.getLeAccessory().size(); i++) {
					lep.getLeAccessory()
							.get(i)
							.setTableId(
									LawEnforcementInspection.LAW_ENFORCEMENT_INSPECTION_TABLE_ID);
					lep.getLeAccessory().get(i).setRowId(lepId);
					flag = dao.insertInfo(lep.getLeAccessory().get(i));
				}
			}
		}
		return flag;

	}

	/**
	 * 删表
	 */
	public boolean delTable(String lepId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db
					.delete(LawEnforcementInspection.LAW_ENFORCEMENT_INSPECTION_TABLE_NAME,
							LawEnforcementInspection.LAW_ENFORCEMENT_INSPECTION_ID
									+ "=?", new String[] { lepId });
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
	public ArrayList<LawEnforcementPatrol> getAllInfos() {
		ArrayList<LawEnforcementPatrol> infos = new ArrayList<LawEnforcementPatrol>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		try {
			String sql = "select * from "
					+ LawEnforcementInspection.LAW_ENFORCEMENT_INSPECTION_TABLE_NAME
					+ " Left join "
					+ Location.LOCATION_TABLE_NAME
					+ " where "
					+ LawEnforcementInspection.LAW_ENFORCEMENT_INSPECTION_TABLE_NAME
					+ "." + LawEnforcementInspection.LOCATION_ID + " = "
					+ Location.LOCATION_TABLE_NAME + "." + Location.LOCATION_ID;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				LawEnforcementPatrol info = new LawEnforcementPatrol();
				info.setLeId(cursor.getInt(cursor
						.getColumnIndex(LawEnforcementInspection.LAW_ENFORCEMENT_INSPECTION_ID)));
				info.setLeLegend(cursor.getString(cursor
						.getColumnIndex(LawEnforcementInspection.NOTE)));
				info.setLeNo(cursor.getString(cursor
						.getColumnIndex(LawEnforcementInspection.LE_NO)));
				info.setLeUnit(cursor.getString(cursor
						.getColumnIndex(LawEnforcementInspection.LE_UNIT)));
				info.setLeContents(cursor.getString(cursor
						.getColumnIndex(LawEnforcementInspection.LE_CONTENT)));
				info.setLeVehicle(cursor.getInt(cursor
						.getColumnIndex(LawEnforcementInspection.LE_VEHICLE)));
				info.setLeTime(cursor.getString(cursor
						.getColumnIndex(LawEnforcementInspection.LE_TIME)));
				info.setLeSituation(cursor.getString(cursor
						.getColumnIndex(LawEnforcementInspection.LE_SITUATION)));
				info.setLeInspectors(cursor.getInt(cursor
						.getColumnIndex(LawEnforcementInspection.LE_INSPECTORS)));
				info.setLePhone(cursor.getString(cursor
						.getColumnIndex(LawEnforcementInspection.LE_PHONE)));
				info.setLeSignature(cursor.getString(cursor
						.getColumnIndex(LawEnforcementInspection.LE_SIGNATURE)));
				info.setLe_signature_bool(cursor.getString(cursor
						.getColumnIndex(LawEnforcementInspection.le_signature_bool)));
				Loaction l = new Loaction();
				l.setLocation_id(cursor.getInt(cursor
						.getColumnIndex(Location.LOCATION_ID)));
				l.setELEVATION(cursor.getDouble(cursor
						.getColumnIndex(Location.ELEVATION)));
				l.setLATITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LATITUDE)));
				l.setLONGITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LONGITUDE)));
				info.setLoaction(l);
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
			int rowid = infos.get(i).getLeId();
			dao = new AttachmentDao(context);
			ArrayList<Accessory> list = dao
					.getInfosById(
							LawEnforcementInspection.LAW_ENFORCEMENT_INSPECTION_TABLE_ID,
							rowid);
			infos.get(i).setLeAccessory(list);
		}

		return infos;
	}

	/**
	 * 
	 * @return
	 */
	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM "
				+ LawEnforcementInspection.LAW_ENFORCEMENT_INSPECTION_TABLE_NAME;

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
	public static String getJson(LawEnforcementPatrol lep, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("userId", userId);
			jo.put("tableId",
					LawEnforcementInspection.LAW_ENFORCEMENT_INSPECTION_TABLE_ID);
			jo.put(LawEnforcementInspection.LE_TIME, lep.getLeTime());
			jo.put(LawEnforcementInspection.LE_UNIT, lep.getLeUnit());
			jo.put(LawEnforcementInspection.LE_VEHICLE, lep.getLeVehicle());
			jo.put(LawEnforcementInspection.LE_CONTENT, lep.getLeContents());
			jo.put(LawEnforcementInspection.LE_INSPECTORS,
					lep.getLeInspectors());
			jo.put(LawEnforcementInspection.LE_NO, lep.getLeNo());
			jo.put(LawEnforcementInspection.LE_PHONE, lep.getLePhone());
			jo.put("le_signature_bool", lep.getLe_signature_bool());
			jo.put(LawEnforcementInspection.LE_SIGNATURE, lep.getLeSignature());
			jo.put(LawEnforcementInspection.LE_SITUATION, lep.getLeSituation());
			jo.put(LawEnforcementInspection.NOTE, lep.getLeLegend());

			JSONObject joLocation = new JSONObject();
			joLocation
					.put(Location.ELEVATION, lep.getLoaction().getELEVATION());
			joLocation.put(Location.LATITUDE, lep.getLoaction().getLATITUDE());
			joLocation
					.put(Location.LONGITUDE, lep.getLoaction().getLONGITUDE());
			jo.put(ReceiveAndDisposeAlarm.LOCATION_ID, joLocation.toString());

			if (lep.getLeAccessory() != null && lep.getLeAccessory().size() > 0) {
				JSONArray ja = new JSONArray();
				JSONArray psm_signature = new JSONArray();
				for (int i = 0; i < lep.getLeAccessory().size(); i++) {
					JSONObject json = AttachmentDao.getJson(lep.getLeAccessory()
							.get(i));
					if (lep.getLe_signature_bool().equals("1")&&i==lep.getLeAccessory().size()-1) {
						psm_signature.put(json);
					}else{
						ja.put(json);
					}
					//ja.put(json);
				}
				jo.put("le_signature", psm_signature.toString());
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
	 * @param lep
	 * @param userId
	 * @return
	 */
	/*public static AjaxParams getParams(LawEnforcementPatrol lep, int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId + "");
		params.put("tableId",
				LawEnforcementInspection.LAW_ENFORCEMENT_INSPECTION_TABLE_ID
						+ "");
		if (lep.getLeTime() != null && !lep.getLeTime().equals("")) {
			params.put(LawEnforcementInspection.LE_TIME, lep.getLeTime());
		}
		if (lep.getLeUnit() != null && !lep.getLeUnit().equals("")) {
			params.put(LawEnforcementInspection.LE_UNIT, lep.getLeUnit());
		}
		if (lep.getLeVehicle() > 0) {
			params.put(LawEnforcementInspection.LE_VEHICLE, lep.getLeVehicle()
					+ "");
		}
		if (lep.getLeContents() != null && !lep.getLeContents().equals("")) {
			params.put(LawEnforcementInspection.LE_CONTENT, lep.getLeContents());
		}
		if (lep.getLeInspectors() != 0) {
			params.put(LawEnforcementInspection.LE_INSPECTORS,
					lep.getLeInspectors()+"");
		}
		if (lep.getLeNo() != null && !lep.getLeNo().equals("")) {
			params.put(LawEnforcementInspection.LE_NO, lep.getLeNo());
		}
		if (lep.getLePhone() != null && !lep.getLePhone().equals("")) {
			params.put(LawEnforcementInspection.LE_PHONE, lep.getLePhone());
		}
		// if (lep.getLeSignature() != null & !lep.getLeSignature().equals(""))
		// {
		// params.put(LawEnforcementInspection.LE_SIGNATURE,
		// lep.getLeSignature());
		// }
		if (lep.getLeSituation() != null && !lep.getLeSituation().equals("")) {
			params.put(LawEnforcementInspection.LE_SITUATION,
					lep.getLeSituation());
		}
		if (lep.getLeLegend() != null && !lep.getLeLegend().equals("")) {
			params.put(LawEnforcementInspection.NOTE, lep.getLeLegend());
		}
		if (lep.getLoaction() != null) {
			JSONObject locationJo = new JSONObject();
			try {
				locationJo.put(Location.ELEVATION, lep.getLoaction()
						.getELEVATION());
				locationJo.put(Location.LATITUDE, lep.getLoaction()
						.getLATITUDE());
				locationJo.put(Location.LONGITUDE, lep.getLoaction()
						.getLONGITUDE());
				locationJo.put("Time", lep.getLoaction().getTIME());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			params.put(LawEnforcementInspection.LOCATION_ID,
					locationJo.toString());
		}
		if (lep.getLeAccessory() != null && lep.getLeAccessory().size() > 0) {
			JSONArray ja = new JSONArray();
			JSONArray psm_signature = new JSONArray();
			for (int i = 0; i < lep.getLeAccessory().size(); i++) {
				JSONObject json = AttachmentDao.getJson(lep.getLeAccessory()
						.get(i));
				if (lep.getLe_signature_bool().equals("1")&&i==lep.getLeAccessory().size()-1) {
					psm_signature.put(json);
				}else{
					ja.put(json);
					
				}
			//	ja.put(json);
			}
			params.put("flist", ja.toString());
			params.put("le_signature", psm_signature.toString());
		}
		params.put("le_signature_bool", lep.getLe_signature_bool());
		params.put("fileType", "1");
		return params;
	}
*/
}
