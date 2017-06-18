package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.BreedEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.Breed;
import db.Database.Location;

/**
 * 基础台账—养殖情况
 * 
 * @author zhengtiantian
 * 
 */
public class BreedDao {

	private ForestDatabaseHelper dbHelper;

	private Context context;

	private static BreedDao bDao;

	public BreedDao(Context context) {
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	public static BreedDao init(Context ct) {
		if (bDao == null) {
			bDao = new BreedDao(ct);
		}
		return bDao;
	}

	/**
	 * 插入数据
	 * 
	 * @param breed
	 * @return falg
	 */
	public boolean insertInfo(BreedEntity breed) {
		boolean flag = false;
		int breedId = -1;
		LocationDao lDao = new LocationDao(context);
		int locationId = lDao.insertInfo(breed.getLocationId());
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		if (locationId > 0) {
			Cursor cursor = null;
			try {
				ContentValues cv = new ContentValues();
				cv.put(Breed.LOCATION_ID, locationId);
				cv.put(Breed.BREED_ID, breed.getServerId());
				cv.put(Breed.REMARK, breed.getbRemarks());
				cv.put(Breed.ACQUISITIONTIME, breed.getAcquisitionTime());
				cv.put(Breed.UNITNAME, breed.getUnitName());
				cv.put(Breed.BREEDNAME, breed.getBreedName());
				cv.put(Breed.BREEDCOUNT, breed.getBreedCount());
				cv.put(Breed.BREEDMODE, breed.getBreedMode());
				cv.put(Breed.BREEDTYPE, breed.getBreedType());

				long ok = db.insert(Breed.BREED_TABLE_NAME, null, cv);
				if (-1 != ok) {
					flag = true;
				}
				String sql = "SELECT MAX(" + Breed.BREED_TABLE_ID + ") FROM "
						+ Breed.BREED_TABLE_NAME;

				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					breedId = cursor.getInt(0);
				}
				
				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				flag = false;
			} finally {
				db.endTransaction();
				if (cursor != null) {
					cursor.close();
				}
			}
			if (breed.getbAttachment() != null
					&& breed.getbAttachment().size() > 0) {
				AttachmentDao aDao = new AttachmentDao(context);
				for (int i = 0; i < breed.getbAttachment().size(); i++) {
					breed.getbAttachment().get(i).setTableId(Breed.CATALOG_ID);
					breed.getbAttachment().get(i).setRowId(breedId);
					flag = aDao.insertInfo(breed.getbAttachment().get(i));
				}
			}
		}
		return flag;
	}

	/**
	 * 删除表
	 * 
	 * @param instantId
	 * @return isOk
	 */
	public boolean delTable(String instantId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		try {
			int res = db.delete(Breed.BREED_TABLE_NAME, Breed.BREED_TABLE_ID
					+ "=?", new String[] { instantId });
			if (res > 0) {
				isOk = true;
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		return isOk;
	}

	/**
	 * 获取本地所有
	 * 
	 * @return
	 */
	public ArrayList<BreedEntity> getAllInfos() {
		ArrayList<BreedEntity> infos = new ArrayList<BreedEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;

		try {
			String sql = "select * from " + Breed.BREED_TABLE_NAME
					+ " Left join " + Location.LOCATION_TABLE_NAME + " where "
					+ Breed.BREED_TABLE_NAME + "." + Breed.LOCATION_ID + "="
					+ Location.LOCATION_TABLE_NAME + "." + Location.LOCATION_ID;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				BreedEntity be = new BreedEntity();
				be.setBreedId(cursor.getInt(cursor
						.getColumnIndex(Breed.BREED_TABLE_ID)));
				be.setServerId(cursor.getInt(cursor
						.getColumnIndex(Breed.BREED_ID)));
				be.setbRemarks(cursor.getString(cursor
						.getColumnIndex(Breed.REMARK)));
				be.setBreedCount(cursor.getString(cursor
						.getColumnIndex(Breed.BREEDCOUNT)));
				be.setBreedMode(cursor.getString(cursor
						.getColumnIndex(Breed.BREEDMODE)));
				be.setBreedName(cursor.getString(cursor
						.getColumnIndex(Breed.BREEDNAME)));
				be.setBreedType(cursor.getString(cursor
						.getColumnIndex(Breed.BREEDTYPE)));
				be.setUnitName(cursor.getString(cursor
						.getColumnIndex(Breed.UNITNAME)));
				be.setAcquisitionTime(cursor.getString(cursor
						.getColumnIndex(Breed.ACQUISITIONTIME)));

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
				be.setLocationId(l);
				infos.add(be);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.endTransaction();
			if (cursor != null) {
				cursor.close();
			}
		}
		AttachmentDao dao = null;
		for (int i = 0; i < infos.size(); i++) {
			int rowId = infos.get(i).getBreedId();
			dao = new AttachmentDao(context);
			ArrayList<Accessory> list = dao.getInfosById(Breed.CATALOG_ID,
					rowId);
			infos.get(i).setbAttachment(list);
		}
		return infos;
	}

	/**
	 * 获取数量
	 * 
	 * @return
	 */
	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT (*) FROM " + Breed.BREED_TABLE_NAME;
		try {
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				count = cursor.getInt(0);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
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
	 * 根据对象获取Json
	 * 
	 * @param bee
	 * @param userId
	 * @return
	 * @throws JSONException
	 */
	public static String getJson(BreedEntity bee, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("userId", userId);
			jo.put("catalogID", Breed.CATALOG_ID);
			jo.put(Breed.BREED_ID, bee.getServerId());
			jo.put(Breed.ACQUISITIONTIME, bee.getAcquisitionTime());
			jo.put(Breed.UNITNAME, bee.getUnitName());
			jo.put(Breed.BREEDNAME, bee.getBreedName());
			jo.put(Breed.BREEDCOUNT, bee.getBreedCount());
			jo.put(Breed.BREEDMODE, bee.getBreedMode());
			jo.put(Breed.BREEDTYPE, bee.getBreedType());
			jo.put(Breed.REMARK, bee.getbRemarks());

			JSONObject lo = new JSONObject();
			lo.put(Location.ELEVATION, bee.getLocationId().getELEVATION());
			lo.put(Location.LATITUDE, bee.getLocationId().getLATITUDE());
			lo.put(Location.LONGITUDE, bee.getLocationId().getLONGITUDE());
			lo.put(Location.TIME, bee.getLocationId().getTIME());

			jo.put(Breed.LOCATION_ID, lo.toString());
			JSONArray ja = new JSONArray();
			if (bee.getbAttachment() != null && bee.getbAttachment().size() > 0) {
				for (int i = 0; i < bee.getbAttachment().size(); i++) {
					JSONObject json = AttachmentDao.getJson(bee
							.getbAttachment().get(i));
					ja.put(json);
				}
			}
			jo.put("flist", ja.toString());
			jo.put("fileType", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject job = new JSONObject();
		job.put("BreedList", jo);
		return job.toString();
	}

	/**
	 * 根据对象获取API参数 ---养殖情况
	 * 
	 * @param breed
	 * @param userId
	 * @return
	 */
	/*public static AjaxParams getParams(BreedEntity breed, int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId + "");
		params.put("catalogID", Breed.CATALOG_ID + "");
		params.put(Breed.BREED_ID, breed.getServerId() + "");
		if (breed.getAcquisitionTime() != null
				&& !breed.getAcquisitionTime().equals("")) {
			params.put(Breed.ACQUISITIONTIME, breed.getAcquisitionTime());
		}
		if (breed.getUnitName() != null && !breed.getUnitName().equals("")) {
			params.put(Breed.UNITNAME, breed.getUnitName());
		}
		if (breed.getBreedName() != null && !breed.getBreedName().equals("")) {
			params.put(Breed.BREEDNAME, breed.getBreedName());
		}
		if (breed.getBreedCount() != null && !breed.getBreedCount().equals("")) {
			params.put(Breed.BREEDCOUNT, breed.getBreedCount());
		}
		if (breed.getBreedMode() != null && !breed.getBreedMode().equals("")) {
			params.put(Breed.BREEDMODE, breed.getBreedMode());
		}
		if (breed.getBreedType() != null && !breed.getBreedType().equals("")) {
			params.put(Breed.BREEDTYPE, breed.getBreedType());
		}
		if (breed.getbRemarks() != null && !breed.getbRemarks().equals("")) {
			params.put(Breed.REMARK, breed.getbRemarks());
		}
		if (breed.getLocationId() != null) {
			JSONObject lo = new JSONObject();
			try {
				lo.put(Location.ELEVATION, breed.getLocationId().getELEVATION());
				lo.put(Location.LATITUDE, breed.getLocationId().getLATITUDE());
				lo.put(Location.LONGITUDE, breed.getLocationId().getLONGITUDE());
				lo.put(Location.TIME, breed.getLocationId().getTIME());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			params.put(Breed.LOCATION_ID, lo.toString());
		}
		if (breed.getbAttachment() != null && breed.getbAttachment().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < breed.getbAttachment().size(); i++) {
				JSONObject json = AttachmentDao.getJson(breed.getbAttachment()
						.get(i));
				ja.put(json);
			}
			params.put("flist", ja.toString());
		}
		return params;
	}*/

	/**
	 * 根据json获取对象
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public ArrayList<BreedEntity> getObject(String json) throws JSONException {
		ArrayList<BreedEntity> breeds = null;
		JSONArray ja = null;
		if (json != null && !json.equals("[]")) {
			breeds = new ArrayList<BreedEntity>();
			ja = new JSONArray(json);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				BreedEntity be = new BreedEntity();
				if (!jo.isNull(Breed.BREED_ID)) {
					be.setServerId(jo.getInt(Breed.BREED_ID));
				}
				if (!jo.isNull(Breed.ACQUISITIONTIME)) {
					be.setAcquisitionTime(jo.getString(Breed.ACQUISITIONTIME));
				}
				if (!jo.isNull(Breed.UNITNAME)) {
					be.setUnitName(jo.getString(Breed.UNITNAME));
				}
				if (!jo.isNull(Breed.BREEDNAME)) {
					be.setBreedName(jo.getString(Breed.BREEDNAME));
				}
				if (!jo.isNull(Breed.BREEDCOUNT)) {
					be.setBreedCount(jo.getString(Breed.BREEDCOUNT));
				}
				if (!jo.isNull(Breed.BREEDTYPE)) {
					be.setBreedType(jo.getString(Breed.BREEDTYPE));
				}
				if (!jo.isNull(Breed.BREEDMODE)) {
					be.setBreedMode(jo.getString(Breed.BREEDMODE));
				}
				if (!jo.isNull(Breed.REMARK)) {
					be.setbRemarks(jo.getString(Breed.REMARK));
				}
				if (!jo.isNull(Breed.LOCATION_ID)) {
					JSONObject joLocation = jo.getJSONObject(Breed.LOCATION_ID);
					Loaction l = new Loaction();
					if (!joLocation.isNull(Location.LONGITUDE)) {
						l.setLONGITUDE(joLocation.getDouble(Location.LONGITUDE));
					}
					if (!joLocation.isNull(Location.LATITUDE)) {
						l.setLATITUDE(joLocation.getDouble(Location.LATITUDE));
					}
					if (!joLocation.isNull(Location.ELEVATION)) {
						l.setELEVATION(joLocation.getDouble(Location.ELEVATION));
					}
					if (!joLocation.isNull(Location.TIME)) {
						l.setTIME(joLocation.getString(Location.TIME));
					}
					be.setLocationId(l);
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
					be.setbAttachment(accessorys);
				}
				breeds.add(be);
			}
		}
		return breeds;

	}
}
