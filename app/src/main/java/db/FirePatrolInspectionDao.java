package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.FireproofWork;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.FirePatrolInspection;
import db.Database.ForestPatrolWork;
import db.Database.Location;

/**
 * 林区治安防控巡逻检查记录
 * 
 * @author liupeng
 */
public class FirePatrolInspectionDao {

	private ForestDatabaseHelper dbHelper;

	private Context context;

	/**
	 * 
	 */
	public FirePatrolInspectionDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	/*
	 * 插入
	 */
	public boolean insertInfo(FireproofWork fw) {

		boolean flag = false;

		int fwID = -1;
		LocationDao ldao = new LocationDao(context);
		int locationId = ldao.insertInfo(fw.getLocation());
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//

		if (locationId != -1) {
			Cursor cursor = null;
			try {
				ContentValues contentValues = new ContentValues();
				contentValues.put(FirePatrolInspection.NOTE, fw.getFwLegend());
				contentValues.put(FirePatrolInspection.UNIT, fw.getpUnits());
				contentValues.put(FirePatrolInspection.PC_TIME, fw.getPcTime());
				contentValues.put(FirePatrolInspection.PC_ROUTE,
						fw.getPcRoutes());
				contentValues.put(FirePatrolInspection.P_INSPECTORS,
						fw.getpInspectors());
				contentValues.put(FirePatrolInspection.PC_NO, fw.getFwNo());
				contentValues.put(FirePatrolInspection.PC_CONTENT,
						fw.getPcContents());
				contentValues.put(FirePatrolInspection.P_INSPECTION,
						fw.getpInspection());
				contentValues.put(FirePatrolInspection.TBC_SIGNATURE,
						fw.getTbcSignature());
				contentValues.put(FirePatrolInspection.TBC_PHONE,
						fw.getTbcPhone());
				/*
				 * contentValues.put(FirePatrolInspection.LOCATION_ID,
				 * locationId);
				 */

				long ok = db.insert(
						FirePatrolInspection.FIRE_PATROL_INSPECTION_TABLE_NAME,
						null, contentValues);
				// System.out.println("insert is complete..." + ok);
				if (-1 != ok) {
					flag = true;
				}
				String sql = "SELECT MAX("
						+ FirePatrolInspection.FIRE_PATROL_INSPECTION_ID
						+ ") FROM "
						+ FirePatrolInspection.FIRE_PATROL_INSPECTION_TABLE_NAME;
				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					fwID = cursor.getInt(0);
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
			if (fw.getFwAccessory() != null && fw.getFwAccessory().size() > 0) {
				AttachmentDao dao = new AttachmentDao(context);
				for (int i = 0; i < fw.getFwAccessory().size(); i++) {
					fw.getFwAccessory()
							.get(i)
							.setTableId(
									FirePatrolInspection.FIRE_PATROL_INSPECTION_TABLE_ID);
					fw.getFwAccessory().get(i).setRowId(fwID);
					flag = dao.insertInfo(fw.getFwAccessory().get(i));
				}
			}
		}

		return flag;

	}

	/**
	 * 删表
	 */
	public boolean delTable(String fireId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db.delete(
					FirePatrolInspection.FIRE_PATROL_INSPECTION_TABLE_NAME,
					FirePatrolInspection.FIRE_PATROL_INSPECTION_ID + "=?",
					new String[] { fireId });
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
	public ArrayList<FireproofWork> getAllInfos() {
		ArrayList<FireproofWork> infos = new ArrayList<FireproofWork>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		try {
			String sql = "select * from "
					+ FirePatrolInspection.FIRE_PATROL_INSPECTION_TABLE_NAME;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				FireproofWork info = new FireproofWork();
				info.setFwId(cursor.getInt(cursor
						.getColumnIndex(FirePatrolInspection.FIRE_PATROL_INSPECTION_ID)));
				info.setFwLegend(cursor.getString(cursor
						.getColumnIndex(FirePatrolInspection.NOTE)));
				info.setFwNo(cursor.getString(cursor
						.getColumnIndex(FirePatrolInspection.PC_NO)));
				info.setpUnits(cursor.getInt(cursor
						.getColumnIndex(FirePatrolInspection.UNIT)));
				info.setPcContents(cursor.getString(cursor
						.getColumnIndex(FirePatrolInspection.PC_CONTENT)));
				info.setPcRoutes(cursor.getString(cursor
						.getColumnIndex(FirePatrolInspection.PC_ROUTE)));
				info.setPcTime(cursor.getString(cursor
						.getColumnIndex(FirePatrolInspection.PC_TIME)));
				info.setpInspection(cursor.getString(cursor
						.getColumnIndex(FirePatrolInspection.P_INSPECTION)));
				info.setpInspectors(cursor.getString(cursor
						.getColumnIndex(FirePatrolInspection.P_INSPECTORS)));
				info.setTbcPhone(cursor.getString(cursor
						.getColumnIndex(FirePatrolInspection.TBC_PHONE)));
				info.setTbcSignature(cursor.getString(cursor
						.getColumnIndex(FirePatrolInspection.TBC_SIGNATURE)));
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
			int rowid = infos.get(i).getFwId();
			dao = new AttachmentDao(context);
			ArrayList<Accessory> list = dao
					.getInfosById(
							FirePatrolInspection.FIRE_PATROL_INSPECTION_TABLE_ID,
							rowid);
			infos.get(i).setFwAccessory(list);
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
				+ FirePatrolInspection.FIRE_PATROL_INSPECTION_TABLE_NAME;

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
	public static String getJson(FireproofWork fw, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("userId", userId);
			jo.put(FirePatrolInspection.UNIT, fw.getpUnits());
			jo.put("tableId",
					FirePatrolInspection.FIRE_PATROL_INSPECTION_TABLE_ID);
			jo.put(FirePatrolInspection.PC_TIME, fw.getPcTime());
			jo.put(FirePatrolInspection.PC_ROUTE, fw.getPcRoutes());
			jo.put(FirePatrolInspection.PC_CONTENT, fw.getPcContents());
			jo.put(FirePatrolInspection.P_INSPECTORS, fw.getpInspectors());
			jo.put(FirePatrolInspection.PC_NO, fw.getFwNo());
			jo.put(FirePatrolInspection.TBC_PHONE, fw.getTbcPhone());
			jo.put(FirePatrolInspection.TBC_SIGNATURE, fw.getTbcSignature());
			jo.put(FirePatrolInspection.P_INSPECTION, fw.getpInspection());
			jo.put(ForestPatrolWork.NOTE, fw.getFwLegend());

			JSONObject joLocation = new JSONObject();
			joLocation.put(Location.ELEVATION, fw.getLocation().getELEVATION());
			joLocation.put(Location.LATITUDE, fw.getLocation().getLATITUDE());
			joLocation.put(Location.LONGITUDE, fw.getLocation().getLONGITUDE());

			jo.put(FirePatrolInspection.LOCATION_ID, joLocation);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jo.toString();
	}

	/**
	 * 根据JSON获对象
	 * 
	 * @param json
	 * @return
	 */
	/*
	 * public static ArrayList<FirePatrol> getObject(String json) throws
	 * JSONException { ArrayList<FirePatrol> fps = null; JSONArray ja = null; if
	 * (json != null && !json.equals("anyType{}")) { ja = new JSONArray(json);
	 * fps = new ArrayList<FirePatrol>(); for (int i = 0; i < ja.length(); i++)
	 * { JSONObject jo = ja.getJSONObject(i); FirePatrol fp = new FirePatrol();
	 * if (!jo.isNull(FirePatrolInspection.UNIT)) {
	 * fp.setpUnits(jo.getInt(FirePatrolInspection.UNIT)); } if
	 * (!jo.isNull(ForestPatrolWork.BEGIN_DATE)) {
	 * fp.setStartTime(jo.getString(ForestPatrolWork.BEGIN_DATE)); } if
	 * (!jo.isNull(ForestPatrolWork.END_DATE)) { fp.setEndTime(jo
	 * .getString(ForestPatrolWork.END_DATE)); } if
	 * (!jo.isNull(ForestPatrolWork.PATROL_POLICE)) {
	 * fp.setPatrolPolice(jo.getString(ForestPatrolWork.PATROL_POLICE)); } if
	 * (!jo.isNull(ForestPatrolWork.PATROL_ROUTE)) { try {
	 * fp.setPatrolRoute(jo.getString(ForestPatrolWork.PATROL_ROUTE)); } catch
	 * (Exception e) { e.printStackTrace(); } } if
	 * (!jo.isNull(ForestPatrolWork.PATROL_CHRONICLE)) {
	 * fp.setPatrolChronicle(jo .getString(ForestPatrolWork.PATROL_CHRONICLE));
	 * } if (!jo.isNull(ForestPatrolWork.ANALYSIS_OF_DISPOSAL)) {
	 * fp.setAnalysisOfDisposal
	 * (jo.getString(ForestPatrolWork.ANALYSIS_OF_DISPOSAL)); } if
	 * (!jo.isNull(ForestPatrolWork.SHIFT_LEADERSHIP_OPINION)) {
	 * fp.setShiftLeadershipOpinion
	 * (jo.getString(ForestPatrolWork.SHIFT_LEADERSHIP_OPINION)); }
	 * 
	 * if (!jo.isNull(ForestPatrolWork.LOCATION_ID)) {
	 * 
	 * JSONObject joLocation = jo .getJSONObject(ForestPatrolWork.LOCATION_ID);
	 * Loaction l = new Loaction();
	 * l.setELEVATION(joLocation.getDouble(Location.ELEVATION));
	 * l.setLATITUDE(joLocation.getDouble(Location.LATITUDE));
	 * l.setLONGITUDE(joLocation.getDouble(Location.LONGITUDE));
	 * l.setLocation_id(joLocation.getInt(Location.LOCATION_ID));
	 * fp.setLocation(l); } if (!jo.isNull("affix")) { JSONArray jaAccessory =
	 * jo .getJSONArray(ForestPatrolWork.ATTACHMENT); ArrayList<Accessory>
	 * accessorys = new ArrayList<Accessory>(); for (int j = 0; j <
	 * jaAccessory.length(); j++) { JSONObject joAccessory =
	 * jaAccessory.getJSONObject(j); Accessory ac = new Accessory();
	 * ac.setAccessoryPath(joAccessory .getString(Attachment.FILE_PATH));
	 * ac.setFileType(joAccessory .getString(Attachment.FILE_TYPE));
	 * ac.setaId(joAccessory.getInt(Attachment.ATTACHMENT_ID));
	 * ac.setTableId(joAccessory.getInt(Attachment.TABLE_ID));
	 * ac.setRowId(joAccessory.getInt(Attachment.ROW_ID)); accessorys.add(ac); }
	 * 
	 * fp.setFpwAccessorys(accessorys);
	 * 
	 * } fps.add(fp); } } return fps; }
	 */
}
