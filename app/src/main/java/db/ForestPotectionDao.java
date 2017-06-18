package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.ForestPotectionEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import db.Database.ForestPotection;
import db.Database.Location;

/**
 * 护林基本力量
 * 
 * @author xingyimin
 */
public class ForestPotectionDao {

	private ForestDatabaseHelper dbHelper;
	private Context context;
	private static ForestPotectionDao fpDao;

	public ForestPotectionDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	public static ForestPotectionDao init(Context ct) {
		if (fpDao == null) {
			fpDao = new ForestPotectionDao(ct);
		}
		return fpDao;

	}

	/**
	 * 向本地数据库插入数据
	 * 
	 * @param forestPotection
	 * @return
	 */
	public boolean insertInfo(ForestPotectionEntity forestPotection) {
		boolean flag = false;
		int iFborId = -1;
		LocationDao lDao = new LocationDao(context);
		int locationId = lDao.insertInfo(forestPotection.getLoaction());
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		if (locationId > 0) {
			Cursor cursor = null;
			try {
				ContentValues contentValues = new ContentValues();
				contentValues.put(ForestPotection.LOCATION_ID, locationId);
				contentValues.put(ForestPotection.FOREST_POTECTION_ID,
						forestPotection.getForestPotectionID());
				contentValues.put(ForestPotection.ADMINISTRATION,
						forestPotection.getAdministration());
				contentValues
						.put(ForestPotection.AGE, forestPotection.getAge());
				contentValues.put(ForestPotection.GENDER,
						forestPotection.getGender());
				contentValues.put(ForestPotection.MANAGEMENT_AREA,
						forestPotection.getManagementArea());
				contentValues.put(ForestPotection.NAME,
						forestPotection.getName());
				contentValues.put(ForestPotection.OFFICIAL_RANK,
						forestPotection.getOfficialRank());
				contentValues.put(ForestPotection.RESPONSIBILITY,
						forestPotection.getResponsibility());
				contentValues
						.put(ForestPotection.TEL, forestPotection.getTel());
				contentValues.put(ForestPotection.UNIT,
						forestPotection.getUnit());
				contentValues.put(ForestPotection.YEAR,
						forestPotection.getYear());
				// contentValues.put(ForestPotection.REMARK,
				// forestPotection.getRemark());

				long ok = db.insert(
						ForestPotection.FOREST_POTECTION_TABLE_NAME, null,
						contentValues);

				if (-1 != ok) {
					flag = true;
				}
				String sql = "SELECT MAX("
						+ ForestPotection.FOREST_POTECTION_TABLE_ID + ") FROM "
						+ ForestPotection.FOREST_POTECTION_TABLE_NAME;

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
			if (forestPotection.getAccessoryList() != null
					&& forestPotection.getAccessoryList().size() > 0) {
				for (int i = 0; i < forestPotection.getAccessoryList().size(); i++) {
					AttachmentDao dao = new AttachmentDao(context);
					forestPotection.getAccessoryList().get(i)
							.setTableId(ForestPotection.CATALOG_ID);
					forestPotection.getAccessoryList().get(i).setRowId(iFborId);
					flag = dao.insertInfo(forestPotection.getAccessoryList()
							.get(i));
				}
			}
		}
		return flag;
	}

	/**
	 * 删除hang
	 */
	public boolean delTable(String ForestPotectionId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		try {
			int res = db.delete(ForestPotection.FOREST_POTECTION_TABLE_NAME,
					ForestPotection.FOREST_POTECTION_TABLE_ID + "=?",
					new String[] { ForestPotectionId });

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

	public List<ForestPotectionEntity> getAllInfos() {

		List<ForestPotectionEntity> infos = new ArrayList<ForestPotectionEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			String sql = "select * from "
					+ ForestPotection.FOREST_POTECTION_TABLE_NAME
					+ " Left join " + Location.LOCATION_TABLE_NAME + " where "
					+ ForestPotection.FOREST_POTECTION_TABLE_NAME + "."
					+ ForestPotection.LOCATION_ID + "="
					+ Location.LOCATION_TABLE_NAME + "." + Location.LOCATION_ID;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);

			while (cursor.moveToNext()) {
				ForestPotectionEntity fpe = new ForestPotectionEntity();
				fpe.setFpId(cursor.getInt(cursor
						.getColumnIndex(ForestPotection.FOREST_POTECTION_TABLE_ID)));
				fpe.setForestPotectionID(cursor.getInt(cursor
						.getColumnIndex(ForestPotection.FOREST_POTECTION_ID)));
				fpe.setAdministration(cursor.getInt(cursor
						.getColumnIndex(ForestPotection.ADMINISTRATION)));
				fpe.setAge(cursor.getString(cursor
						.getColumnIndex(ForestPotection.AGE)));
				fpe.setGender(cursor.getInt(cursor
						.getColumnIndex(ForestPotection.GENDER)));
				fpe.setManagementArea(cursor.getString(cursor
						.getColumnIndex(ForestPotection.MANAGEMENT_AREA)));
				fpe.setName(cursor.getString(cursor
						.getColumnIndex(ForestPotection.NAME)));
				fpe.setOfficialRank(cursor.getInt(cursor
						.getColumnIndex(ForestPotection.OFFICIAL_RANK)));
				fpe.setResponsibility(cursor.getInt(cursor
						.getColumnIndex(ForestPotection.RESPONSIBILITY)));
				fpe.setTel(cursor.getString(cursor
						.getColumnIndex(ForestPotection.TEL)));
				fpe.setUnit(cursor.getInt(cursor
						.getColumnIndex(ForestPotection.UNIT)));
				fpe.setYear(cursor.getInt(cursor
						.getColumnIndex(ForestPotection.YEAR)));
				// fpe.setRemark(cursor.getString(cursor
				// .getColumnIndex(ForestPotection.REMARK)));

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
				fpe.setLoaction(l);

				infos.add(fpe);
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
			int borId = infos.get(i).getForestPotectionID();
			aDao = new AttachmentDao(context);
			ArrayList<Accessory> list = aDao.getInfosById(
					ForestPotection.CATALOG_ID, borId);
			infos.get(i).setAccessoryList(list);
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
				+ ForestPotection.FOREST_POTECTION_TABLE_NAME;

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
	 * 根据对象获得JSON字符串
	 * 
	 * @param forestPotection
	 * @param userId
	 * @return JSON字符串
	 * @throws JSONException
	 */

	public static String getJson(ForestPotectionEntity forestPotection,
			int userId) throws JSONException {
		JSONObject jo = new JSONObject();
		jo.put("userId", userId);
		jo.put("catalogID", ForestPotection.CATALOG_ID);
		jo.put(ForestPotection.FOREST_POTECTION_ID,
				forestPotection.getForestPotectionID());
		jo.put(ForestPotection.ADMINISTRATION,
				forestPotection.getAdministration());
		jo.put(ForestPotection.AGE, forestPotection.getAge());
		jo.put(ForestPotection.GENDER, forestPotection.getGender());
		jo.put(ForestPotection.MANAGEMENT_AREA,
				forestPotection.getManagementArea());
		jo.put(ForestPotection.NAME, forestPotection.getName());
		jo.put(ForestPotection.OFFICIAL_RANK, forestPotection.getOfficialRank());
		jo.put(ForestPotection.RESPONSIBILITY,
				forestPotection.getResponsibility());
		jo.put(ForestPotection.TEL, forestPotection.getTel());
		jo.put(ForestPotection.UNIT, forestPotection.getUnit());
		jo.put(ForestPotection.YEAR, forestPotection.getYear());
		// jo.put(ForestPotection.REMARK, forestPotection.getRemark());

		JSONObject joLocation = new JSONObject();
		joLocation.put(Location.LONGITUDE, forestPotection.getLoaction()
				.getLONGITUDE());
		joLocation.put(Location.LATITUDE, forestPotection.getLoaction()
				.getLATITUDE());
		joLocation.put(Location.ELEVATION, forestPotection.getLoaction()
				.getELEVATION());
		joLocation.put("Time", forestPotection.getLoaction().getTIME());

		jo.put(ForestPotection.LOCATION_ID, joLocation.toString());
		if (forestPotection.getAccessoryList() != null
				&& forestPotection.getAccessoryList().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < forestPotection.getAccessoryList().size(); i++) {
				JSONObject json = AttachmentDao.getJson(forestPotection
						.getAccessoryList().get(i));
				ja.put(json);
			}
			jo.put("flist", ja.toString());
		}
		return jo.toString();
	}

	/**
	 * 根据对象获取API参数
	 * 
	 * @param forestPotection
	 * @param userId
	 * @return
	 *//*
	public static AjaxParams getParams(ForestPotectionEntity forestPotection,
			int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", String.valueOf(userId));
		params.put("catalogID", String.valueOf(ForestPotection.CATALOG_ID));
		params.put(ForestPotection.FOREST_POTECTION_ID,
				String.valueOf(forestPotection.getForestPotectionID()));
		params.put(ForestPotection.ADMINISTRATION,
				String.valueOf(forestPotection.getAdministration()));
		if (!forestPotection.getAge().equals("")) {
			params.put(ForestPotection.AGE, forestPotection.getAge());
		}
		params.put(ForestPotection.GENDER,
				String.valueOf(forestPotection.getGender()));
		if (!forestPotection.getManagementArea().equals("")) {
			params.put(ForestPotection.MANAGEMENT_AREA,
					forestPotection.getManagementArea());
		}
		if (!forestPotection.getName().equals("")) {
			params.put(ForestPotection.NAME, forestPotection.getName());
		}
		params.put(ForestPotection.OFFICIAL_RANK,
				String.valueOf(forestPotection.getOfficialRank()));
		params.put(ForestPotection.RESPONSIBILITY,
				String.valueOf(forestPotection.getResponsibility()));
		if (!forestPotection.getTel().equals("")) {
			params.put(ForestPotection.TEL, forestPotection.getTel());
		}
		params.put(ForestPotection.UNIT,
				String.valueOf(forestPotection.getUnit()));
		params.put(ForestPotection.YEAR,
				String.valueOf(forestPotection.getYear()));
		// jo.put(ForestPotection.REMARK, forestPotection.getRemark());

		JSONObject joLocation = new JSONObject();
		try {
			joLocation.put(Location.LONGITUDE, forestPotection.getLoaction()
					.getLONGITUDE());
			joLocation.put(Location.LATITUDE, forestPotection.getLoaction()
					.getLATITUDE());
			joLocation.put(Location.ELEVATION, forestPotection.getLoaction()
					.getELEVATION());
			joLocation.put("Time", forestPotection.getLoaction().getTIME());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.put(ForestPotection.LOCATION_ID, joLocation.toString());
		if (forestPotection.getAccessoryList() != null
				&& forestPotection.getAccessoryList().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < forestPotection.getAccessoryList().size(); i++) {
				JSONObject json = AttachmentDao.getJson(forestPotection
						.getAccessoryList().get(i));
				ja.put(json);
			}
			params.put("flist", ja.toString());
		}
		return params;
	}*/

	/**
	 * 根据从服务器查询获取的json字符串获得ForestPotectionEntity对象集合
	 * 
	 * @param json
	 * @return ForestPotectionEntity对象集合
	 * @throws JSONException
	 */
	public List<ForestPotectionEntity> getObject(String json)
			throws JSONException {
		List<ForestPotectionEntity> forestList = null;
		JSONArray ja = null;
		if (json != null && !json.equals("anyType{}")) {
			ja = new JSONArray(json);
			forestList = new ArrayList<ForestPotectionEntity>();
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				ForestPotectionEntity forest = new ForestPotectionEntity();
				if (!jo.isNull(ForestPotection.FOREST_POTECTION_ID)) {
					forest.setForestPotectionID(jo
							.getInt(ForestPotection.FOREST_POTECTION_ID));
				}
				if (!jo.isNull(ForestPotection.ADMINISTRATION)) {
					forest.setAdministration(jo
							.getInt(ForestPotection.ADMINISTRATION));
				}
				if (!jo.isNull(ForestPotection.AGE)) {
					forest.setAge(jo.getString(ForestPotection.AGE));
				}
				if (!jo.isNull(ForestPotection.GENDER)) {
					forest.setGender(jo.getInt(ForestPotection.GENDER));
				}
				if (!jo.isNull(ForestPotection.MANAGEMENT_AREA)) {
					forest.setManagementArea(jo
							.getString(ForestPotection.MANAGEMENT_AREA));
				}
				if (!jo.isNull(ForestPotection.NAME)) {
					forest.setName(jo.getString(ForestPotection.NAME));
				}
				if (!jo.isNull(ForestPotection.OFFICIAL_RANK)) {
					forest.setOfficialRank(jo
							.getInt(ForestPotection.OFFICIAL_RANK));
				}
				if (!jo.isNull(ForestPotection.RESPONSIBILITY)) {
					forest.setResponsibility(jo
							.getInt(ForestPotection.RESPONSIBILITY));
				}
				if (!jo.isNull(ForestPotection.TEL)) {
					forest.setTel(jo.getString(ForestPotection.TEL));
				}
				if (!jo.isNull(ForestPotection.UNIT)) {
					forest.setUnit(jo.getInt(ForestPotection.UNIT));
				}
				if (!jo.isNull(ForestPotection.YEAR)) {
					forest.setYear(jo.getInt(ForestPotection.YEAR));
				}
				// if (!jo.isNull(ForestPotection.REMARK)) {
				// forest.setRemark(jo.getString(ForestPotection.REMARK));
				// }
				if (!jo.isNull(ForestPotection.LOCATION_ID)) {
					JSONObject joLocation = jo
							.getJSONObject(ForestPotection.LOCATION_ID);
					Loaction l = new Loaction();
					l.setLONGITUDE(joLocation.getDouble(Location.LONGITUDE));
					l.setLATITUDE(joLocation.getDouble(Location.LATITUDE));
					l.setELEVATION(joLocation.getDouble(Location.ELEVATION));
					l.setTIME(joLocation.getString(Location.TIME));
					forest.setLoaction(l);
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

					forest.setAccessoryList(accessorys);
				}
				forestList.add(forest);
			}
		}
		return forestList;
	}
}
