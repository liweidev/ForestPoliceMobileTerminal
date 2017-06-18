package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.ForestPatrolWorkEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.Attachment;
import db.Database.ForestPatrolWork;
import db.Database.Location;

/**
 * @author liupeng 林区巡逻
 */
public class ForestPatrolWorkDao {

	private ForestDatabaseHelper dbHelper;

	private Context context;

	/**
	 * 
	 */
	public ForestPatrolWorkDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	/*
	 * 插入
	 */
	boolean flag = false;
	int fpwisposeId = -1;
	LocationDao lDao = new LocationDao(context);
	ContentValues contentValues;
	public boolean insertInfo(ForestPatrolWorkEntity fpw) {
		int locationId = lDao.insertInfo(fpw.getLoaction());
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		if (locationId > 0) {
			Cursor cursor = null;
			try {
				contentValues= new ContentValues();
				// contentValues.put(ForestPatrolWork.UNIT, fpw.getUnit());
				contentValues.put(ForestPatrolWork.BEGIN_DATE,
						fpw.getStartTime());
				contentValues.put(ForestPatrolWork.END_DATE, fpw.getEndTime());
				contentValues.put(ForestPatrolWork.PATROL_CHRONICLE,
						fpw.getPatrolChronicle());
				contentValues.put(ForestPatrolWork.PATROL_POLICE,
						fpw.getPatrolPolice());
				contentValues.put(ForestPatrolWork.PATROL_ROUTE,
						fpw.getPatrolRoute());
				contentValues.put(ForestPatrolWork.ANALYSIS_OF_DISPOSAL,
						fpw.getAnalysisOfDisposal());
				contentValues.put(ForestPatrolWork.SHIFT_LEADERSHIP_OPINION,
						fpw.getShiftLeadershipOpinion());
				contentValues.put(ForestPatrolWork.NOTE, fpw.getFpwLegend());
				contentValues.put(ForestPatrolWork.LOCATION_ID, locationId);
				long ok = db.insert(
						ForestPatrolWork.FOREST_PATROL_WORK_TABLE_NAME, null,
						contentValues);
				// System.out.println("insert is complete..." + ok);
				if (-1 != ok) {
					flag = true;
				}
				String sql = "SELECT MAX("
						+ ForestPatrolWork.FOREST_PATROL_WORK_ID + ") FROM "
						+ ForestPatrolWork.FOREST_PATROL_WORK_TABLE_NAME;
				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					fpwisposeId = cursor.getInt(0);
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
			if (fpw.getFpwAccessorys() != null
					&& fpw.getFpwAccessorys().size() > 0) {
				AttachmentDao aDao = new AttachmentDao(context);
				for (int i = 0; i < fpw.getFpwAccessorys().size(); i++) {
					fpw.getFpwAccessorys()
							.get(i)
							.setTableId(
									ForestPatrolWork.FOREST_PATROL_WORK_TABLE_ID);
					fpw.getFpwAccessorys().get(i).setRowId(fpwisposeId);
					flag = aDao.insertInfo(fpw.getFpwAccessorys().get(i));
				}
			}
		}
		return flag;

	}

	/**
	 * 删表
	 */
	public boolean delTable(String fpwId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db.delete(ForestPatrolWork.FOREST_PATROL_WORK_TABLE_NAME,
					ForestPatrolWork.FOREST_PATROL_WORK_ID + "=?",
					new String[] { fpwId });
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
	public ArrayList<ForestPatrolWorkEntity> getAllInfos() {
		ArrayList<ForestPatrolWorkEntity> infos = new ArrayList<ForestPatrolWorkEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		try {
			String sql = "select * from "
					+ ForestPatrolWork.FOREST_PATROL_WORK_TABLE_NAME
					+ " Left join " + Location.LOCATION_TABLE_NAME + " where "
					+ ForestPatrolWork.FOREST_PATROL_WORK_TABLE_NAME + "."
					+ ForestPatrolWork.LOCATION_ID + " = "
					+ Location.LOCATION_TABLE_NAME + "." + Location.LOCATION_ID;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				ForestPatrolWorkEntity info = new ForestPatrolWorkEntity();
				info.setAnalysisOfDisposal(cursor.getString(cursor
						.getColumnIndex(ForestPatrolWork.ANALYSIS_OF_DISPOSAL)));
				info.setEndTime(cursor.getString(cursor
						.getColumnIndex(ForestPatrolWork.END_DATE)));
				// info.setUnit(cursor.getString(cursor
				// .getColumnIndex(ForestPatrolWork.UNIT)));
				info.setFpwId(cursor.getInt(cursor
						.getColumnIndex(ForestPatrolWork.FOREST_PATROL_WORK_ID)));
				info.setFpwLegend(cursor.getString(cursor
						.getColumnIndex(ForestPatrolWork.NOTE)));
				info.setPatrolChronicle(cursor.getString(cursor
						.getColumnIndex(ForestPatrolWork.PATROL_CHRONICLE)));
				info.setPatrolPolice(cursor.getString(cursor
						.getColumnIndex(ForestPatrolWork.PATROL_POLICE)));
				info.setPatrolRoute(cursor.getString(cursor
						.getColumnIndex(ForestPatrolWork.PATROL_ROUTE)));
				info.setShiftLeadershipOpinion(cursor.getString(cursor
						.getColumnIndex(ForestPatrolWork.SHIFT_LEADERSHIP_OPINION)));
				info.setStartTime(cursor.getString(cursor
						.getColumnIndex(ForestPatrolWork.BEGIN_DATE)));
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
			int rowid = infos.get(i).getFpwId();
			dao = new AttachmentDao(context);
			ArrayList<Accessory> list = dao.getInfosById(
					ForestPatrolWork.FOREST_PATROL_WORK_TABLE_ID, rowid);
			infos.get(i).setFpwAccessorys(list);
		}

		return infos;
	}

	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		int count = 0;
		String sql = "SELECT COUNT(*) FROM "
				+ ForestPatrolWork.FOREST_PATROL_WORK_TABLE_NAME;

		Cursor cursor = null;
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
	public static String getJson(ForestPatrolWorkEntity fpwe, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("userId", userId);
			// jo.put(ForestPatrolWork.UNIT, fpwe.getUnit());
			jo.put("tableId", ForestPatrolWork.FOREST_PATROL_WORK_TABLE_ID);
			jo.put(ForestPatrolWork.BEGIN_DATE, fpwe.getStartTime());
			jo.put(ForestPatrolWork.END_DATE, fpwe.getEndTime());
			jo.put(ForestPatrolWork.PATROL_POLICE, fpwe.getPatrolPolice());
			jo.put(ForestPatrolWork.PATROL_ROUTE, fpwe.getPatrolRoute());
			jo.put(ForestPatrolWork.PATROL_CHRONICLE, fpwe.getPatrolChronicle());
			jo.put(ForestPatrolWork.ANALYSIS_OF_DISPOSAL,
					fpwe.getAnalysisOfDisposal());
			jo.put(ForestPatrolWork.SHIFT_LEADERSHIP_OPINION,
					fpwe.getShiftLeadershipOpinion());
			// jo.put( ForestPatrolWork.ATTACHEMENT,
			// fka.getFwaAccessory());
			jo.put(ForestPatrolWork.NOTE, fpwe.getFpwLegend());

			JSONObject joLocation = new JSONObject();
			joLocation.put(Location.ELEVATION, fpwe.getLoaction()
					.getELEVATION());
			joLocation.put(Location.LATITUDE, fpwe.getLoaction().getLATITUDE());
			joLocation.put(Location.LONGITUDE, fpwe.getLoaction()
					.getLONGITUDE());

			jo.put(ForestPatrolWork.LOCATION_ID, joLocation.toString());
			if (fpwe.getFpwAccessorys() != null
					&& fpwe.getFpwAccessorys().size() > 0) {
				JSONArray ja = new JSONArray();
				for (int i = 0; i < fpwe.getFpwAccessorys().size(); i++) {
					JSONObject json = AttachmentDao.getJson(fpwe.getFpwAccessorys()
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
	 * @param fpwe
	 * @param userId
	 * @return
	 */
	/*public static AjaxParams getParams(ForestPatrolWorkEntity fpwe, int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId + "");
		params.put("tableId", ForestPatrolWork.FOREST_PATROL_WORK_TABLE_ID + "");
		if (fpwe.getStartTime() != null && !fpwe.getStartTime().equals("")) {
			params.put(ForestPatrolWork.BEGIN_DATE, fpwe.getStartTime());
		}
		if (fpwe.getEndTime() != null && !fpwe.getEndTime().equals("")) {
			params.put(ForestPatrolWork.END_DATE, fpwe.getEndTime());
		}
		if (fpwe.getPatrolPolice() != null
				&& !fpwe.getPatrolPolice().equals("")) {
			params.put(ForestPatrolWork.PATROL_POLICE, fpwe.getPatrolPolice());
		}
		if (fpwe.getPatrolRoute() != null && !fpwe.getPatrolRoute().equals("")) {
			params.put(ForestPatrolWork.PATROL_ROUTE, fpwe.getPatrolRoute());
		}
		if (fpwe.getPatrolChronicle() != null
				&& !fpwe.getPatrolChronicle().equals("")) {
			params.put(ForestPatrolWork.PATROL_CHRONICLE,
					fpwe.getPatrolChronicle());
		}
		if (fpwe.getAnalysisOfDisposal() != null
				&& !fpwe.getAnalysisOfDisposal().equals("")) {
			params.put(ForestPatrolWork.ANALYSIS_OF_DISPOSAL,
					fpwe.getAnalysisOfDisposal());
		}
		if (fpwe.getShiftLeadershipOpinion() != null
				&& !fpwe.getShiftLeadershipOpinion().equals("")) {
			params.put(ForestPatrolWork.SHIFT_LEADERSHIP_OPINION,
					fpwe.getShiftLeadershipOpinion());
		}
		if (fpwe.getFpwLegend() != null && !fpwe.getFpwLegend().equals("")) {
			params.put(ForestPatrolWork.NOTE, fpwe.getFpwLegend());
		}
		if (fpwe.getLoaction() != null) {
			JSONObject locationJo = new JSONObject();
			try {
				locationJo.put(Location.ELEVATION, fpwe.getLoaction()
						.getELEVATION());
				locationJo.put(Location.LATITUDE, fpwe.getLoaction()
						.getLATITUDE());
				locationJo.put(Location.LONGITUDE, fpwe.getLoaction()
						.getLONGITUDE());
				locationJo.put("Time", fpwe.getLoaction().getTIME());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			params.put(ForestPatrolWork.LOCATION_ID, locationJo.toString());
		}
		if (fpwe.getFpwAccessorys() != null
				&& fpwe.getFpwAccessorys().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < fpwe.getFpwAccessorys().size(); i++) {
				JSONObject json = AttachmentDao.getJson(fpwe.getFpwAccessorys()
						.get(i));
				ja.put(json);
			}
			params.put("flist", ja.toString());
		}
		params.put("fileType", "1");

		return params;
	}
*/
	/**
	 * 根据JSON获对象
	 * 
	 * @param json
	 * @return
	 */
	public static ArrayList<ForestPatrolWorkEntity> getObject(String json)
			throws JSONException {
		ArrayList<ForestPatrolWorkEntity> fpwes = null;
		JSONArray ja = null;
		if (json != null && !json.equals("anyType{}")) {
			ja = new JSONArray(json);
			fpwes = new ArrayList<ForestPatrolWorkEntity>();
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				ForestPatrolWorkEntity fpwe = new ForestPatrolWorkEntity();
				// if (!jo.isNull(ForestPatrolWork.UNIT)) {
				// fpwe.setUnit(jo.getString(ForestPatrolWork.UNIT));
				// }
				if (!jo.isNull(ForestPatrolWork.BEGIN_DATE)) {
					fpwe.setStartTime(jo.getString(ForestPatrolWork.BEGIN_DATE));
				}
				if (!jo.isNull(ForestPatrolWork.END_DATE)) {
					fpwe.setEndTime(jo.getString(ForestPatrolWork.END_DATE));
				}
				if (!jo.isNull(ForestPatrolWork.PATROL_POLICE)) {
					fpwe.setPatrolPolice(jo
							.getString(ForestPatrolWork.PATROL_POLICE));
				}
				if (!jo.isNull(ForestPatrolWork.PATROL_ROUTE)) {
					try {
						fpwe.setPatrolRoute(jo
								.getString(ForestPatrolWork.PATROL_ROUTE));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (!jo.isNull(ForestPatrolWork.PATROL_CHRONICLE)) {
					fpwe.setPatrolChronicle(jo
							.getString(ForestPatrolWork.PATROL_CHRONICLE));
				}
				if (!jo.isNull(ForestPatrolWork.ANALYSIS_OF_DISPOSAL)) {
					fpwe.setAnalysisOfDisposal(jo
							.getString(ForestPatrolWork.ANALYSIS_OF_DISPOSAL));
				}
				if (!jo.isNull(ForestPatrolWork.SHIFT_LEADERSHIP_OPINION)) {
					fpwe.setShiftLeadershipOpinion(jo
							.getString(ForestPatrolWork.SHIFT_LEADERSHIP_OPINION));
				}

				if (!jo.isNull(ForestPatrolWork.LOCATION_ID)) {

					JSONObject joLocation = jo
							.getJSONObject(ForestPatrolWork.LOCATION_ID);
					Loaction l = new Loaction();
					l.setELEVATION(joLocation.getDouble(Location.ELEVATION));
					l.setLATITUDE(joLocation.getDouble(Location.LATITUDE));
					l.setLONGITUDE(joLocation.getDouble(Location.LONGITUDE));
					l.setLocation_id(joLocation.getInt(Location.LOCATION_ID));
					fpwe.setLoaction(l);
				}
				if (!jo.isNull("affix")) {
					JSONArray jaAccessory = jo
							.getJSONArray(ForestPatrolWork.ATTACHMENT);
					ArrayList<Accessory> accessorys = new ArrayList<Accessory>();
					for (int j = 0; j < jaAccessory.length(); j++) {
						JSONObject joAccessory = jaAccessory.getJSONObject(j);
						Accessory ac = new Accessory();
						ac.setAccessoryPath(joAccessory
								.getString(Attachment.FILE_PATH));
						ac.setFileType(joAccessory
								.getString(Attachment.FILE_TYPE));
						ac.setaId(joAccessory.getInt(Attachment.ATTACHMENT_ID));
						ac.setTableId(joAccessory.getInt(Attachment.TABLE_ID));
						ac.setRowId(joAccessory.getInt(Attachment.ROW_ID));
						accessorys.add(ac);
					}

					fpwe.setFpwAccessorys(accessorys);

				}
				fpwes.add(fpwe);
			}
		}
		return fpwes;
	}
}
