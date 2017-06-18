package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.ForestryKeyIndustriesEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import db.Database.ForestryKeyIndustries;
import db.Database.Location;

/**
 * 涉林重点行业情况
 * 
 * @author xingyimin
 */
public class ForestryKeyIndustriesDao {

	private ForestDatabaseHelper dbHelper;
	private Context context;
	private static ForestryKeyIndustriesDao fkDao;

	public ForestryKeyIndustriesDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	public static ForestryKeyIndustriesDao init(Context ct) {
		if (fkDao == null) {
			fkDao = new ForestryKeyIndustriesDao(ct);
		}
		return fkDao;

	}

	/**
	 * 向本地数据库插入数据
	 * 
	 * @param forestryKeyIndustries
	 * @return
	 */
	public boolean insertInfo(ForestryKeyIndustriesEntity forestryKeyIndustries) {
		boolean flag = false;
		int iFborId = -1;
		LocationDao lDao = new LocationDao(context);
		int locationId = lDao
				.insertInfo(forestryKeyIndustries.getLocation_id());
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		if (locationId > 0) {
			Cursor cursor = null;
			try {
				ContentValues contentValues = new ContentValues();
				contentValues.put(ForestryKeyIndustries.LOCATION, locationId);
				contentValues.put(
						ForestryKeyIndustries.FORESTRY_KEY_INDUSTRIES_ID,
						forestryKeyIndustries.getForestryKeyIndustriesID());
				contentValues.put(ForestryKeyIndustries.ADDRESS,
						forestryKeyIndustries.getAddress());
				contentValues.put(ForestryKeyIndustries.CONTACT_INFORMATION,
						forestryKeyIndustries.getContactInformation());
				contentValues.put(ForestryKeyIndustries.LICENSE,
						forestryKeyIndustries.getLicense());
				contentValues.put(ForestryKeyIndustries.OPERATE_TYPE,
						forestryKeyIndustries.getOperateType());
				contentValues.put(ForestryKeyIndustries.PRACTITIONER_NUMBER,
						forestryKeyIndustries.getPractitionerNumber());
				contentValues.put(ForestryKeyIndustries.PRINCIPAL,
						forestryKeyIndustries.getPrincipal());
				contentValues.put(ForestryKeyIndustries.PROTECTION_LEVEL,
						forestryKeyIndustries.getProtectionLevel());
				contentValues.put(ForestryKeyIndustries.UNIT_NAME,
						forestryKeyIndustries.getUnitName());
				contentValues.put(ForestryKeyIndustries.VARIETIES_SOURCESS,
						forestryKeyIndustries.getVarietiesSourcess());
				contentValues.put(ForestryKeyIndustries.REMARK,
						forestryKeyIndustries.getRemark());

				long ok = db
						.insert(ForestryKeyIndustries.FORESTRY_KEY_INDUSTRIES_TABLE_NAME,
								null, contentValues);

				if (-1 != ok) {
					flag = true;
				}
				String sql = "SELECT MAX("
						+ ForestryKeyIndustries.FORESTRY_KEY_INDUSTRIES_TABLE_ID
						+ ") FROM "
						+ ForestryKeyIndustries.FORESTRY_KEY_INDUSTRIES_TABLE_NAME;

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
			if (forestryKeyIndustries.getAccessoryList() != null
					&& forestryKeyIndustries.getAccessoryList().size() > 0) {
				for (int i = 0; i < forestryKeyIndustries.getAccessoryList()
						.size(); i++) {
					AttachmentDao dao = new AttachmentDao(context);
					forestryKeyIndustries.getAccessoryList().get(i)
							.setTableId(ForestryKeyIndustries.CATALOG_ID);
					forestryKeyIndustries.getAccessoryList().get(i)
							.setRowId(iFborId);
					flag = dao.insertInfo(forestryKeyIndustries
							.getAccessoryList().get(i));
				}
			}
		}
		return flag;
	}

	/**
	 * 删除hang
	 */
	public boolean delTable(String forestryKeyIndustriesId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		try {
			int res = db.delete(
					ForestryKeyIndustries.FORESTRY_KEY_INDUSTRIES_TABLE_NAME,
					ForestryKeyIndustries.FORESTRY_KEY_INDUSTRIES_TABLE_ID
							+ "=?", new String[] { forestryKeyIndustriesId });

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

	public ArrayList<ForestryKeyIndustriesEntity> getAllInfos() {

		ArrayList<ForestryKeyIndustriesEntity> infos = new ArrayList<ForestryKeyIndustriesEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			String sql = "select * from "
					+ ForestryKeyIndustries.FORESTRY_KEY_INDUSTRIES_TABLE_NAME
					+ " Left join " + Location.LOCATION_TABLE_NAME + " where "
					+ ForestryKeyIndustries.FORESTRY_KEY_INDUSTRIES_TABLE_NAME
					+ "." + ForestryKeyIndustries.LOCATION + "="
					+ Location.LOCATION_TABLE_NAME + "." + Location.LOCATION_ID;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);

			while (cursor.moveToNext()) {
				ForestryKeyIndustriesEntity fkie = new ForestryKeyIndustriesEntity();
				fkie.setFkiId(cursor.getInt(cursor
						.getColumnIndex(ForestryKeyIndustries.FORESTRY_KEY_INDUSTRIES_TABLE_ID)));
				fkie.setForestryKeyIndustriesID(cursor.getInt(cursor
						.getColumnIndex(ForestryKeyIndustries.FORESTRY_KEY_INDUSTRIES_ID)));
				fkie.setAddress(cursor.getString(cursor
						.getColumnIndex(ForestryKeyIndustries.ADDRESS)));
				fkie.setContactInformation(cursor.getString(cursor
						.getColumnIndex(ForestryKeyIndustries.CONTACT_INFORMATION)));
				fkie.setLicense(cursor.getString(cursor
						.getColumnIndex(ForestryKeyIndustries.LICENSE)));
				fkie.setOperateType(cursor.getString(cursor
						.getColumnIndex(ForestryKeyIndustries.OPERATE_TYPE)));
				fkie.setPractitionerNumber(cursor.getString(cursor
						.getColumnIndex(ForestryKeyIndustries.PRACTITIONER_NUMBER)));
				fkie.setPrincipal(cursor.getString(cursor
						.getColumnIndex(ForestryKeyIndustries.PRINCIPAL)));
				fkie.setProtectionLevel(cursor.getInt(cursor
						.getColumnIndex(ForestryKeyIndustries.PROTECTION_LEVEL)));
				fkie.setUnitName(cursor.getString(cursor
						.getColumnIndex(ForestryKeyIndustries.UNIT_NAME)));
				fkie.setVarietiesSourcess(cursor.getString(cursor
						.getColumnIndex(ForestryKeyIndustries.VARIETIES_SOURCESS)));
				fkie.setRemark(cursor.getString(cursor
						.getColumnIndex(ForestryKeyIndustries.REMARK)));

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
				fkie.setLocation_id(l);

				infos.add(fkie);
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
			int borId = infos.get(i).getForestryKeyIndustriesID();
			aDao = new AttachmentDao(context);
			ArrayList<Accessory> list = aDao.getInfosById(
					ForestryKeyIndustries.CATALOG_ID, borId);
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
				+ ForestryKeyIndustries.FORESTRY_KEY_INDUSTRIES_TABLE_NAME;

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
	 * @param forestryKeyIndustries
	 * @param userId
	 * @return JSON字符串
	 * @throws JSONException
	 */

	public static String getJson(
			ForestryKeyIndustriesEntity forestryKeyIndustries, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		jo.put("userId", userId);
		jo.put("catalogID", ForestryKeyIndustries.CATALOG_ID);
		jo.put(ForestryKeyIndustries.FORESTRY_KEY_INDUSTRIES_ID,
				forestryKeyIndustries.getForestryKeyIndustriesID());
		jo.put(ForestryKeyIndustries.ADDRESS,
				forestryKeyIndustries.getAddress());
		jo.put(ForestryKeyIndustries.CONTACT_INFORMATION,
				forestryKeyIndustries.getContactInformation());
		jo.put(ForestryKeyIndustries.LICENSE,
				forestryKeyIndustries.getLicense());
		jo.put(ForestryKeyIndustries.OPERATE_TYPE,
				forestryKeyIndustries.getOperateType());
		jo.put(ForestryKeyIndustries.PRACTITIONER_NUMBER,
				forestryKeyIndustries.getPractitionerNumber());
		jo.put(ForestryKeyIndustries.PRINCIPAL,
				forestryKeyIndustries.getPrincipal());
		jo.put(ForestryKeyIndustries.PROTECTION_LEVEL,
				forestryKeyIndustries.getProtectionLevel());
		jo.put(ForestryKeyIndustries.UNIT_NAME,
				forestryKeyIndustries.getUnitName());
		jo.put(ForestryKeyIndustries.VARIETIES_SOURCESS,
				forestryKeyIndustries.getVarietiesSourcess());
		jo.put(ForestryKeyIndustries.REMARK, forestryKeyIndustries.getRemark());

		JSONObject joLocation = new JSONObject();
		joLocation.put(Location.LONGITUDE, forestryKeyIndustries
				.getLocation_id().getLONGITUDE());
		joLocation.put(Location.LATITUDE, forestryKeyIndustries
				.getLocation_id().getLATITUDE());
		joLocation.put(Location.ELEVATION, forestryKeyIndustries
				.getLocation_id().getELEVATION());
		joLocation
				.put("Time", forestryKeyIndustries.getLocation_id().getTIME());

		jo.put(ForestryKeyIndustries.LOCATION, joLocation.toString());
		
		JSONArray ja = new JSONArray();
		if (forestryKeyIndustries.getAccessoryList() != null
				&& forestryKeyIndustries.getAccessoryList().size() > 0) {
			for (int i = 0; i < forestryKeyIndustries.getAccessoryList().size(); i++) {
				JSONObject json = AttachmentDao.getJson(forestryKeyIndustries
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
	 * @param forestryKeyIndustries
	 * @param userId
	 * @return
	 */
	/*public static AjaxParams getParams(
			ForestryKeyIndustriesEntity forestryKeyIndustries, int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", String.valueOf(userId));
		params.put("catalogID",
				String.valueOf(ForestryKeyIndustries.CATALOG_ID));
		params.put(ForestryKeyIndustries.FORESTRY_KEY_INDUSTRIES_ID, String
				.valueOf(forestryKeyIndustries.getForestryKeyIndustriesID()));
		if (!forestryKeyIndustries.getAddress().equals("")) {
			params.put(ForestryKeyIndustries.ADDRESS,
					forestryKeyIndustries.getAddress());
		}
		if (!forestryKeyIndustries.getContactInformation().equals("")) {
			params.put(ForestryKeyIndustries.CONTACT_INFORMATION,
					forestryKeyIndustries.getContactInformation());
		}
		if (!forestryKeyIndustries.getLicense().equals("")) {
			params.put(ForestryKeyIndustries.LICENSE,
					forestryKeyIndustries.getLicense());
		}
		if (!forestryKeyIndustries.getLicense().equals("")) {
			params.put(ForestryKeyIndustries.LICENSE,
					forestryKeyIndustries.getLicense());
		}
		if (!forestryKeyIndustries.getOperateType().equals("")) {
			params.put(ForestryKeyIndustries.OPERATE_TYPE,
					forestryKeyIndustries.getOperateType());
		}
		if (!forestryKeyIndustries.getPractitionerNumber().equals("")) {
			params.put(ForestryKeyIndustries.PRACTITIONER_NUMBER,
					forestryKeyIndustries.getPractitionerNumber());
		}
		if (!forestryKeyIndustries.getPrincipal().equals("")) {
			params.put(ForestryKeyIndustries.PRINCIPAL,
					forestryKeyIndustries.getPrincipal());
		}

		params.put(ForestryKeyIndustries.PROTECTION_LEVEL,
				String.valueOf(forestryKeyIndustries.getProtectionLevel()));

		if (!forestryKeyIndustries.getUnitName().equals("")) {
			params.put(ForestryKeyIndustries.UNIT_NAME,
					forestryKeyIndustries.getUnitName());
		}
		if (!forestryKeyIndustries.getVarietiesSourcess().equals("")) {
			params.put(ForestryKeyIndustries.VARIETIES_SOURCESS,
					forestryKeyIndustries.getVarietiesSourcess());
		}
		if (!forestryKeyIndustries.getRemark().equals("")) {
			params.put(ForestryKeyIndustries.REMARK,
					forestryKeyIndustries.getRemark());
		}

		JSONObject joLocation = new JSONObject();
		try {
			joLocation.put(Location.LONGITUDE, forestryKeyIndustries
					.getLocation_id().getLONGITUDE());
			joLocation.put(Location.LATITUDE, forestryKeyIndustries
					.getLocation_id().getLATITUDE());
			joLocation.put(Location.ELEVATION, forestryKeyIndustries
					.getLocation_id().getELEVATION());
			joLocation.put("Time", forestryKeyIndustries.getLocation_id()
					.getTIME());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		params.put(ForestryKeyIndustries.LOCATION, joLocation.toString());
		JSONArray ja = new JSONArray();
		if (forestryKeyIndustries.getAccessoryList() != null
				&& forestryKeyIndustries.getAccessoryList().size() > 0) {
			for (int i = 0; i < forestryKeyIndustries.getAccessoryList().size(); i++) {
				JSONObject jo = AttachmentDao.getJson(forestryKeyIndustries
						.getAccessoryList().get(i));
				ja.put(jo);
			}
		}
		params.put("flist", ja.toString());
		return params;
	}*/

	/**
	 * 根据从服务器查询获取的json字符串获得ForestryKeyIndustriesEntity对象集合
	 * 
	 * @param json
	 * @return ForestryKeyIndustriesEntity对象集合
	 * @throws JSONException
	 */
	public List<ForestryKeyIndustriesEntity> getObject(String json)
			throws JSONException {
		List<ForestryKeyIndustriesEntity> forestryList = null;
		JSONArray ja = null;
		if (json != null && !json.equals("anyType{}")) {
			ja = new JSONArray(json);
			forestryList = new ArrayList<ForestryKeyIndustriesEntity>();
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				ForestryKeyIndustriesEntity fkie = new ForestryKeyIndustriesEntity();
				if (!jo.isNull(ForestryKeyIndustries.FORESTRY_KEY_INDUSTRIES_ID)) {
					fkie.setForestryKeyIndustriesID(jo
							.getInt(ForestryKeyIndustries.FORESTRY_KEY_INDUSTRIES_ID));
				}
				if (!jo.isNull(ForestryKeyIndustries.ADDRESS)) {
					fkie.setAddress(jo.getString(ForestryKeyIndustries.ADDRESS));
				}
				if (!jo.isNull(ForestryKeyIndustries.CONTACT_INFORMATION)) {
					fkie.setContactInformation(jo
							.getString(ForestryKeyIndustries.CONTACT_INFORMATION));
				}
				if (!jo.isNull(ForestryKeyIndustries.LICENSE)) {
					fkie.setLicense(jo.getString(ForestryKeyIndustries.LICENSE));
				}
				if (!jo.isNull(ForestryKeyIndustries.OPERATE_TYPE)) {
					fkie.setOperateType(jo
							.getString(ForestryKeyIndustries.OPERATE_TYPE));
				}
				if (!jo.isNull(ForestryKeyIndustries.PRACTITIONER_NUMBER)) {
					fkie.setPractitionerNumber(jo
							.getString(ForestryKeyIndustries.PRACTITIONER_NUMBER));
				}
				if (!jo.isNull(ForestryKeyIndustries.PRINCIPAL)) {
					fkie.setPrincipal(jo
							.getString(ForestryKeyIndustries.PRINCIPAL));
				}
				if (!jo.isNull(ForestryKeyIndustries.PROTECTION_LEVEL)) {
					fkie.setProtectionLevel(jo
							.getInt(ForestryKeyIndustries.PROTECTION_LEVEL));
				}
				if (!jo.isNull(ForestryKeyIndustries.UNIT_NAME)) {
					fkie.setUnitName(jo
							.getString(ForestryKeyIndustries.UNIT_NAME));
				}
				if (!jo.isNull(ForestryKeyIndustries.VARIETIES_SOURCESS)) {
					fkie.setVarietiesSourcess(jo
							.getString(ForestryKeyIndustries.VARIETIES_SOURCESS));
				}
				if (!jo.isNull(ForestryKeyIndustries.REMARK)) {
					fkie.setRemark(jo.getString(ForestryKeyIndustries.REMARK));
				}
				if (!jo.isNull(ForestryKeyIndustries.LOCATION)) {
					JSONObject joLocation = jo
							.getJSONObject(ForestryKeyIndustries.LOCATION);
					Loaction l = new Loaction();
					l.setLONGITUDE(joLocation.getDouble(Location.LONGITUDE));
					l.setLATITUDE(joLocation.getDouble(Location.LATITUDE));
					l.setELEVATION(joLocation.getDouble(Location.ELEVATION));
					l.setTIME(joLocation.getString(Location.TIME));
					fkie.setLocation_id(l);
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

					fkie.setAccessoryList(accessorys);
				}
				forestryList.add(fkie);
			}
		}
		return forestryList;
	}
}
