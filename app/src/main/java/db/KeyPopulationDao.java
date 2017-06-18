package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.KeyPopulationEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.KeyPopulation;
import db.Database.Location;

/**
 * 辖区重点人口情况登记表
 * 
 * @author zhengtiantian
 * 
 */
public class KeyPopulationDao {

	private ForestDatabaseHelper dbHelper;
	private Context context;

	public KeyPopulationDao(Context context) {
		this.context = context;
		this.dbHelper = new ForestDatabaseHelper(context);
	}

	/**
	 * 插入数据
	 * 
	 * @param kpe
	 * @return flag
	 */
	public boolean insertInfo(KeyPopulationEntity kpe) {
		boolean flag = false;
		int keyId = -1;
		LocationDao lDao = new LocationDao(context);
		int locationId = lDao.insertInfo(kpe.getLoaction());
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			if (locationId != -1) {
				ContentValues contentValues = new ContentValues();
				contentValues.put(KeyPopulation.LOCATION_ID, locationId);
				contentValues.put(KeyPopulation.NOTE, kpe.getKeyNote());
				contentValues.put(KeyPopulation.KEY_ADDRESS, kpe.getAddress());
				contentValues
						.put(KeyPopulation.KEY_BIRTHDAY, kpe.getBirthday());
				contentValues.put(KeyPopulation.KEY_FILE_CODE,
						kpe.getFileCode());
				contentValues.put(KeyPopulation.KEY_MANAGE_CAUSE,
						kpe.getManageCause());
				contentValues.put(KeyPopulation.KEY_MANAGE_TIME,
						kpe.getManageTime());
				contentValues.put(KeyPopulation.KEY_NAME, kpe.getName());
				contentValues
						.put(KeyPopulation.KEY_NICKNAME, kpe.getNickName());
				contentValues.put(KeyPopulation.KEY_REPEAL_TIME,
						kpe.getRepealTime());
				contentValues.put(KeyPopulation.KEY_SEX, kpe.getSex());
				contentValues.put(KeyPopulation.KEY_TIME, kpe.getTime());

				long ok = db.insert(KeyPopulation.KEY_POPULATION_TABLE_NAME,
						null, contentValues);

				if (-1 != ok) {
					flag = true;
				}
				String sql = " SELECT MAX(" + KeyPopulation.KEY_POPULATION_ID
						+ ") FROM " + KeyPopulation.KEY_POPULATION_TABLE_NAME;
				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					keyId = cursor.getInt(0);
				}
				
				db.setTransactionSuccessful();
			}
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
		if (kpe.getKeyAccessory() != null && kpe.getKeyAccessory().size() > 0) {
			AttachmentDao aDao = new AttachmentDao(context);
			for (int i = 0; i < kpe.getKeyAccessory().size(); i++) {
				kpe.getKeyAccessory().get(i)
						.setTableId(KeyPopulation.KEY_POPULATION_TABLE_ID);
				kpe.getKeyAccessory().get(i).setRowId(keyId);
				flag = aDao.insertInfo(kpe.getKeyAccessory().get(i));
			}
		}
		return flag;
	}

	/**
	 * 删除表
	 * 
	 * @param populationId
	 * @return isOk
	 */
	public boolean delTable(String populationId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		try {
			int res = db.delete(KeyPopulation.KEY_POPULATION_TABLE_NAME,
					KeyPopulation.KEY_POPULATION_ID + "=?",
					new String[] { populationId });

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
	 * @return infos
	 */
	public ArrayList<KeyPopulationEntity> getAllInfos() {
		ArrayList<KeyPopulationEntity> infos = new ArrayList<KeyPopulationEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			String sql = "select * from "
					+ KeyPopulation.KEY_POPULATION_TABLE_NAME + " Left join "
					+ Location.LOCATION_TABLE_NAME + " where "
					+ KeyPopulation.KEY_POPULATION_TABLE_NAME + "."
					+ KeyPopulation.LOCATION_ID + " = "
					+ Location.LOCATION_TABLE_NAME + "." + Location.LOCATION_ID;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);

			while (cursor.moveToNext()) {
				KeyPopulationEntity kepo = new KeyPopulationEntity();
				kepo.setKeyId(cursor.getInt(cursor
						.getColumnIndex(KeyPopulation.KEY_POPULATION_ID)));
				kepo.setAddress(cursor.getString(cursor
						.getColumnIndex(KeyPopulation.KEY_ADDRESS)));
				kepo.setBirthday(cursor.getString(cursor
						.getColumnIndex(KeyPopulation.KEY_BIRTHDAY)));
				kepo.setFileCode(cursor.getString(cursor
						.getColumnIndex(KeyPopulation.KEY_FILE_CODE)));
				kepo.setKeyNote(cursor.getString(cursor
						.getColumnIndex(KeyPopulation.NOTE)));
				kepo.setManageCause(cursor.getString(cursor
						.getColumnIndex(KeyPopulation.KEY_MANAGE_CAUSE)));
				kepo.setManageTime(cursor.getString(cursor
						.getColumnIndex(KeyPopulation.KEY_MANAGE_TIME)));
				kepo.setName(cursor.getString(cursor
						.getColumnIndex(KeyPopulation.KEY_NAME)));
				kepo.setNickName(cursor.getString(cursor
						.getColumnIndex(KeyPopulation.KEY_NICKNAME)));
				kepo.setRepealTime(cursor.getString(cursor
						.getColumnIndex(KeyPopulation.KEY_REPEAL_TIME)));
				kepo.setSex(cursor.getInt(cursor
						.getColumnIndex(KeyPopulation.KEY_SEX)));
				kepo.setTime(cursor.getString(cursor
						.getColumnIndex(KeyPopulation.KEY_TIME)));

				Loaction l = new Loaction();
				l.setLocation_id(cursor.getInt(cursor
						.getColumnIndex(Location.LOCATION_ID)));
				l.setELEVATION(cursor.getDouble(cursor
						.getColumnIndex(Location.ELEVATION)));
				l.setLATITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LATITUDE)));
				l.setLONGITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LONGITUDE)));

				kepo.setLoaction(l);
				infos.add(kepo);
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
		AttachmentDao aDao = null;
		for (int i = 0; i < infos.size(); i++) {
			int rowId = infos.get(i).getKeyId();
			aDao = new AttachmentDao(context);
			ArrayList<Accessory> list = aDao.getInfosById(
					KeyPopulation.KEY_POPULATION_TABLE_ID, rowId);
			infos.get(i).setKeyAccessory(list);
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
		String sql = "SELECT COUNT (*) FROM "
				+ KeyPopulation.KEY_POPULATION_TABLE_NAME;
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
	 * 根据对象获取JSON
	 * 
	 * @param keys
	 * @param userId
	 * @return jo.toString();
	 * @throws JSONException
	 */
	public static String getJson(KeyPopulationEntity keys, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
//		JSONObject parentJo = new JSONObject();
		try {
			// jo.put("userId", userId);
			jo.put("tableId", KeyPopulation.KEY_POPULATION_TABLE_ID);
			jo.put(KeyPopulation.KEY_ADDRESS, keys.getAddress());
			jo.put(KeyPopulation.KEY_BIRTHDAY, keys.getBirthday());
			jo.put(KeyPopulation.KEY_FILE_CODE, keys.getFileCode());
			jo.put(KeyPopulation.KEY_MANAGE_CAUSE, keys.getManageCause());
			jo.put(KeyPopulation.KEY_MANAGE_TIME, keys.getManageTime());
			jo.put(KeyPopulation.KEY_NAME, keys.getName());
			jo.put(KeyPopulation.KEY_NICKNAME, keys.getNickName());
			jo.put(KeyPopulation.KEY_REPEAL_TIME, keys.getRepealTime());
			jo.put(KeyPopulation.KEY_SEX, keys.getSex());
			jo.put(KeyPopulation.KEY_TIME, keys.getTime());
			jo.put(KeyPopulation.NOTE, keys.getKeyNote());

			JSONObject joLocation = new JSONObject();
			joLocation.put(Location.ELEVATION, keys.getLoaction()
					.getELEVATION());
			joLocation.put(Location.LATITUDE, keys.getLoaction().getLATITUDE());
			joLocation.put(Location.LONGITUDE, keys.getLoaction()
					.getLONGITUDE());
			joLocation.put(Location.TIME, keys.getLoaction().getTIME());
			jo.put(KeyPopulation.LOCATION_ID, joLocation.toString());

			JSONArray ja = new JSONArray();
			if (keys.getKeyAccessory() != null
					&& keys.getKeyAccessory().size() > 0) {
				for (int i = 0; i < keys.getKeyAccessory().size(); i++) {
					JSONObject joAccessory = AttachmentDao.getJson(keys
							.getKeyAccessory().get(i));
					ja.put(joAccessory);
				}
			}
			jo.put("flist", ja.toString());
			
			jo.put("fileType", 1);

			// parentJo.put("keyPopulation", jo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return jo.toString();

	}

	/**
	 * 根据对象获取API参数
	 * 
	 * @param keys
	 * @param userId
	 * @return
	 */
	/*public static AjaxParams getParams(KeyPopulationEntity keys, int userId) {
		AjaxParams params = new AjaxParams();
		params.put("tableId",
				String.valueOf(KeyPopulation.KEY_POPULATION_TABLE_ID));
		if (!keys.getAddress().equals("")) {
			params.put(KeyPopulation.KEY_ADDRESS, keys.getAddress());
		}
		if (!keys.getBirthday().equals("")) {
			params.put(KeyPopulation.KEY_BIRTHDAY, keys.getBirthday());
		}
		if (!keys.getFileCode().equals("")) {
			params.put(KeyPopulation.KEY_FILE_CODE, keys.getFileCode());
		}
		if (!keys.getManageCause().equals("")) {
			params.put(KeyPopulation.KEY_MANAGE_CAUSE, keys.getManageCause());
		}
		if (!keys.getManageTime().equals("")) {
			params.put(KeyPopulation.KEY_MANAGE_TIME, keys.getManageTime());
		}
		if (!keys.getName().equals("")) {
			params.put(KeyPopulation.KEY_NAME, keys.getName());
		}
		if (!keys.getNickName().equals("")) {
			params.put(KeyPopulation.KEY_NICKNAME, keys.getNickName());
		}
		if (!keys.getRepealTime().equals("")) {
			params.put(KeyPopulation.KEY_REPEAL_TIME, keys.getRepealTime());
		}
		params.put(KeyPopulation.KEY_SEX, String.valueOf(keys.getSex()));
		if (!keys.getTime().equals("")) {
			params.put(KeyPopulation.KEY_TIME, keys.getTime());
		}
		if (!keys.getKeyNote().equals("")) {
			params.put(KeyPopulation.NOTE, keys.getKeyNote());
		}

		try {
			JSONObject joLocation = new JSONObject();
			joLocation.put(Location.ELEVATION, keys.getLoaction()
					.getELEVATION());
			joLocation.put(Location.LATITUDE, keys.getLoaction().getLATITUDE());
			joLocation.put(Location.LONGITUDE, keys.getLoaction()
					.getLONGITUDE());
			joLocation.put(Location.TIME, keys.getLoaction().getTIME());
			params.put(KeyPopulation.LOCATION_ID, joLocation.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (keys.getKeyAccessory() != null && keys.getKeyAccessory().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < keys.getKeyAccessory().size(); i++) {
				JSONObject joAccessory = AttachmentDao.getJson(keys
						.getKeyAccessory().get(i));
				ja.put(joAccessory);
			}
			params.put("flist", ja.toString());
		}
		params.put("fileType", "1");
		return params;
	}*/
}
