package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.ForestSurvey;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.ForestSurveys;
import db.Database.Location;
import db.Database.ProtectedAnimals;

/*
 * 林情调查
 */
public class ForestSurveyDao {
	private ForestDatabaseHelper dbHelper;
	private Context context;

	/*
 * 
 */
	public ForestSurveyDao(Context context) {
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	/*
	 * 插入数据
	 */
	public boolean insertInfo(ForestSurvey fsentity) {
		boolean flag = false;
		int forestId = -1;
		LocationDao ldao = new LocationDao(context);
		int locationId = ldao.insertInfo(fsentity.getLocationId());

		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();

		if (locationId != -1) {
			Cursor cursor = null;
			try {
				ContentValues contentValues = new ContentValues();
				contentValues.put(ForestSurveys.FOREST_UNIT,
						fsentity.getUnitName());
				contentValues.put(ForestSurveys.FOREST_NUMBER,
						fsentity.getNumber());
				contentValues
						.put(ForestSurveys.FOREST_AREA, fsentity.getArea());
				contentValues.put(ForestSurveys.FOREST_TYPES,
						fsentity.getForestTypes());
				contentValues.put(ForestSurveys.FOREST_OWNERSHIP,
						fsentity.getForestOwnership());
				contentValues.put(ForestSurveys.LAND_TYPES,
						fsentity.getLandTypes());
				contentValues.put(ForestSurveys.NOTE, fsentity.getFsLegend());
				contentValues.put(ForestSurveys.LAND_OWNERSHIP,
						fsentity.getLandOwnership());
				contentValues.put(ForestSurveys.LOCATION_ID, locationId);
				contentValues.put(ForestSurveys.FOREST_SERVER_ID,
						fsentity.getServerId());

				long ok = db.insert(ForestSurveys.FOREST_NAME, null,
						contentValues);
				Log.i("zhengtian", "insert Forestpolice    :   OK   " + ok);
				if (-1 != ok) {
					flag = true;
				}
				String sql = "SELECT MAX(" + ForestSurveys.FOREST_ID
						+ ") FROM " + ForestSurveys.FOREST_NAME;
				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					forestId = cursor.getInt(0);
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
			if (fsentity.getFsAttachment() != null
					&& fsentity.getFsAttachment().size() > 0) {
				AttachmentDao dao = new AttachmentDao(context);
				for (int i = 0; i < fsentity.getFsAttachment().size(); i++) {
					fsentity.getFsAttachment().get(i)
							.setTableId(ForestSurveys.FOREST_TABLE_ID);
					fsentity.getFsAttachment().get(i).setRowId(forestId);
					flag = dao.insertInfo(fsentity.getFsAttachment().get(i));
				}

			}
		}
		return flag;

	}

	/*
	 * 删除表
	 */
	public boolean delTable(String forestId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		try {
			int res = db.delete(ForestSurveys.FOREST_NAME,
					ForestSurveys.FOREST_ID + "=?", new String[] { forestId });
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

	/*
	 * 获取本地所有
	 */

	public ArrayList<ForestSurvey> getAllInfos() {
		ArrayList<ForestSurvey> infos = new ArrayList<ForestSurvey>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			String sql = "select * from " + ForestSurveys.FOREST_NAME
					+ " Left join " + Location.LOCATION_TABLE_NAME + " where "
					+ ForestSurveys.FOREST_NAME + "."
					+ ForestSurveys.LOCATION_ID + " = "
					+ Location.LOCATION_TABLE_NAME + "." + Location.LOCATION_ID;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				ForestSurvey info = new ForestSurvey();
				info.setUnitName(cursor.getString(cursor
						.getColumnIndex(ForestSurveys.FOREST_UNIT)));
				info.setNumber(cursor.getString(cursor
						.getColumnIndex(ForestSurveys.FOREST_NUMBER)));
				info.setForestTypes(cursor.getString(cursor
						.getColumnIndex(ForestSurveys.FOREST_TYPES)));
				info.setLandTypes(cursor.getString(cursor
						.getColumnIndex(ForestSurveys.LAND_TYPES)));
				info.setLandOwnership(cursor.getString(cursor
						.getColumnIndex(ForestSurveys.LAND_OWNERSHIP)));
				info.setForestOwnership(cursor.getString(cursor
						.getColumnIndex(ForestSurveys.FOREST_OWNERSHIP)));
				info.setFsId(cursor.getInt(cursor
						.getColumnIndex(ForestSurveys.FOREST_ID)));
				info.setArea(cursor.getString(cursor
						.getColumnIndex(ForestSurveys.FOREST_AREA)));
				info.setServerId(cursor.getInt(cursor
						.getColumnIndex(ForestSurveys.FOREST_SERVER_ID)));
				info.setFsLegend(cursor.getString(cursor
						.getColumnIndex(ForestSurveys.NOTE)));

				Loaction lo = new Loaction();
				lo.setLocation_id(cursor.getInt(cursor
						.getColumnIndex(Location.LOCATION_ID)));
				lo.setELEVATION(cursor.getDouble(cursor
						.getColumnIndex(Location.ELEVATION)));
				lo.setLATITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LATITUDE)));
				lo.setLONGITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LONGITUDE)));
				info.setLocationId(lo);

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
			int rowId = infos.get(i).getFsId();
			AttachmentDao dao = new AttachmentDao(context);
			ArrayList<Accessory> list = dao.getInfosById(
					ForestSurveys.FOREST_TABLE_ID, rowId);
			infos.get(i).setFsAttachment(list);

		}
		return infos;
	}

	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM " + ForestSurveys.FOREST_NAME;
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

	/*
	 * 根据对象获取JSON
	 */
	public static String getJson(ForestSurvey fosu, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("userId", userId);
			jo.put(ForestSurveys.FOREST_UNIT, fosu.getUnitName());
			jo.put(ForestSurveys.FOREST_NUMBER, fosu.getNumber());
			jo.put("tableId", ForestSurveys.FOREST_TABLE_ID);
			jo.put(ForestSurveys.FOREST_AREA, fosu.getArea());
			jo.put(ForestSurveys.FOREST_SERVER_ID, fosu.getServerId() + "");
			jo.put(ForestSurveys.NOTE, fosu.getFsLegend());
			jo.put(ForestSurveys.FOREST_OWNERSHIP, fosu.getForestOwnership());
			jo.put(ForestSurveys.FOREST_TYPES, fosu.getForestTypes());
			jo.put(ForestSurveys.LAND_TYPES, fosu.getLandTypes());
			jo.put(ForestSurveys.LAND_OWNERSHIP, fosu.getLandOwnership());

			JSONObject locationJo = new JSONObject();
			locationJo.put(Location.ELEVATION, fosu.getLocationId()
					.getELEVATION());
			locationJo.put(Location.LATITUDE, fosu.getLocationId()
					.getLATITUDE());
			locationJo.put(Location.LONGITUDE, fosu.getLocationId()
					.getLONGITUDE());
			jo.put(ForestSurveys.LOCATION_ID, locationJo.toString());
			JSONArray ja = new JSONArray();
			if (fosu.getFsAttachment() != null
					&& fosu.getFsAttachment().size() > 0) {
				for (int i = 0; i < fosu.getFsAttachment().size(); i++) {
					JSONObject json = AttachmentDao.getJson(fosu
							.getFsAttachment().get(i));
					ja.put(json);
				}
			}
			jo.put("flist", ja.toString());
			jo.put("fileType", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*JSONObject job = new JSONObject();
		job.put("ForestName", jo);*/
		return jo.toString();

	}

	/**
	 * 根据对象获取API参数
	 * 
	 * @param
	 * @param
	 * @return
	 */
	/*public static AjaxParams getParams(ForestSurvey fs, int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId + "");
		params.put("tableId", ForestSurveys.FOREST_TABLE_ID + "");
		if (fs.getUnitName() != null && !fs.getUnitName().equals("")) {
			params.put(ForestSurveys.FOREST_UNIT, fs.getUnitName());
		}
		if (fs.getNumber() != null && !fs.getNumber().equals("")) {
			params.put(ForestSurveys.FOREST_NUMBER, fs.getNumber());
		}
		if (fs.getArea() != null && !fs.getArea().equals("")) {
			params.put(ForestSurveys.FOREST_AREA, fs.getArea());
		}
		if (fs.getServerId() != 0) {
			params.put(ForestSurveys.FOREST_SERVER_ID, fs.getServerId() + "");
		}
		if (fs.getFsLegend() != null && !fs.getFsLegend().equals("")) {
			params.put(ForestSurveys.NOTE, fs.getFsLegend());
		}
		if (fs.getForestOwnership() != null
				&& !fs.getForestOwnership().equals("")) {
			params.put(ForestSurveys.FOREST_OWNERSHIP, fs.getForestOwnership());
		}
		if (fs.getForestTypes() != null && !fs.getForestTypes().equals("")) {
			params.put(ForestSurveys.FOREST_TYPES, fs.getForestTypes());
		}
		if (fs.getLandTypes() != null && !fs.getLandTypes().equals("")) {
			params.put(ForestSurveys.LAND_TYPES, fs.getLandTypes());
		}
		if (fs.getLandOwnership() != null && !fs.getLandOwnership().equals("")) {
			params.put(ForestSurveys.LAND_OWNERSHIP, fs.getLandOwnership());
		}
		if (fs.getLocationId() != null) {
			JSONObject locationJo = new JSONObject();
			try {
				locationJo.put(Location.ELEVATION, fs.getLocationId()
						.getELEVATION());
				locationJo.put(Location.LATITUDE, fs.getLocationId()
						.getLATITUDE());
				locationJo.put(Location.LONGITUDE, fs.getLocationId()
						.getLONGITUDE());
				locationJo.put("Time", fs.getLocationId().getTIME());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			params.put(ForestSurveys.LOCATION_ID, locationJo.toString());
		}
		if (fs.getFsAttachment() != null && fs.getFsAttachment().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < fs.getFsAttachment().size(); i++) {
				JSONObject json = AttachmentDao.getJson(fs.getFsAttachment()
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
	 */
	public static ArrayList<ForestSurvey> getObject(String json)
			throws JSONException {
		JSONArray ja = null;
		ArrayList<ForestSurvey> arrfs = null;
		if (json != null && !json.equals("anyType{}")) {
			arrfs = new ArrayList<ForestSurvey>();
			ja = new JSONArray(json);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				ForestSurvey fs = new ForestSurvey();
				if (!jo.isNull(ForestSurveys.FOREST_ID)) {
					fs.setFsId(Integer.parseInt(jo
							.getString(ForestSurveys.FOREST_ID)));
				}
				if (!jo.isNull(ForestSurveys.LAND_TYPES)) {
					fs.setLandTypes(jo.getString(ForestSurveys.LAND_TYPES));
				}
				if (!jo.isNull(ForestSurveys.FOREST_TYPES)) {
					fs.setForestTypes(jo.getString(ForestSurveys.FOREST_TYPES));
				}
				if (!jo.isNull(ForestSurveys.FOREST_UNIT)) {
					fs.setUnitName(jo.getString(ForestSurveys.FOREST_UNIT));
				}
				if (!jo.isNull(ForestSurveys.FOREST_NUMBER)) {
					fs.setNumber(jo.getString(ForestSurveys.FOREST_NUMBER));
				}
				if (!jo.isNull(ForestSurveys.FOREST_AREA)) {
					fs.setArea(jo.getString(ForestSurveys.FOREST_AREA));
				}
				if (!jo.isNull(ForestSurveys.FOREST_SERVER_ID)) {
					fs.setServerId(Integer.parseInt(jo
							.getString(ForestSurveys.FOREST_SERVER_ID)));
				}

				if (!jo.isNull(ForestSurveys.NOTE)) {
					fs.setFsLegend(jo.getString(ForestSurveys.NOTE));
				}

				if (!jo.isNull(ForestSurveys.FOREST_OWNERSHIP)) {
					fs.setForestOwnership(jo
							.getString(ForestSurveys.FOREST_OWNERSHIP));
				}
				if (!jo.isNull(ForestSurveys.LAND_OWNERSHIP)) {
					fs.setLandOwnership(jo
							.getString(ForestSurveys.LAND_OWNERSHIP));
				}

				if (!jo.isNull(ProtectedAnimals.LOCATION_ID)) {
					JSONObject joLocation = jo
							.getJSONObject(ProtectedAnimals.LOCATION_ID);

					if (joLocation.getDouble(Location.LATITUDE) != 0) {
						Loaction l = new Loaction();
						l.setELEVATION(joLocation.getDouble(Location.ELEVATION));
						l.setLATITUDE(joLocation.getDouble(Location.LATITUDE));
						l.setLONGITUDE(joLocation.getDouble(Location.LONGITUDE));
						fs.setLocationId(l);
					}
				}
				if (!jo.isNull("filePath")) {
					ArrayList<Accessory> accessorys = new ArrayList<Accessory>();
					JSONArray jaaccessorys = new JSONArray(
							jo.getString("filePath"));
					for (int j = 0; j < jaaccessorys.length(); j++) {
						JSONObject joAttachment = jaaccessorys.getJSONObject(j);
						Accessory accessory = new Accessory();

						if (!joAttachment.isNull("fileAllPath")) {
							accessory.setAccessoryPath(joAttachment
									.getString("fileAllPath"));
						}
						if (!joAttachment.isNull("fileAllSPath")) {
							accessory.setSlPath(joAttachment
									.getString("fileAllSPath"));
						}
						accessorys.add(accessory);
					}

					fs.setFsAttachment(accessorys);
				}
				arrfs.add(fs);
			}
		}
		return arrfs;
	}
}
