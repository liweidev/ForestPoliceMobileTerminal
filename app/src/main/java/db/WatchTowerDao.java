package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.WatchtowerRegistration;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.Location;
import db.Database.WatchTower;

/**
 * 辖区瞭望塔登记
 * 
 * @author ZhengTiantian
 */
public class WatchTowerDao {

	private ForestDatabaseHelper dbHelper;
	private Context context;

	public WatchTowerDao(Context context) {
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	/**
	 * 插入数据
	 */
	public boolean insertInfo(WatchtowerRegistration watch) {
		boolean flag = false;
		int watchId = -1;
		LocationDao lDao = new LocationDao(context);
		int locationId = lDao.insertInfo(watch.getLoaction());
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		if (locationId > 0) {
			Cursor cursor = null;
			try {
				ContentValues contentValues = new ContentValues();
				contentValues.put(WatchTower.LOCATION_ID, locationId);
				contentValues.put(WatchTower.NOTE, watch.getWrLegend());
				contentValues.put(WatchTower.BASE_STATION_NAME,
						watch.getTbsName());
				contentValues.put(WatchTower.SPECIFIC_LOCATION,
						watch.getSpecificLocation());
				contentValues.put(WatchTower.COORDINATES,
						watch.getCoordinates());
				contentValues.put(WatchTower.ALTITUDE, watch.getAltitude());
				contentValues.put(WatchTower.WATCH_TOWER_NORMS,
						watch.getWtwSpecifications());
				contentValues.put(WatchTower.TRANSPORT_MODE,
						watch.getTransmission());
				contentValues.put(WatchTower.BUILD_TIME, watch.getBtTime());

				long ok = db.insert(WatchTower.WATCH_TOWER_TABLE_NAME, null,
						contentValues);

				if (-1 != ok) {
					flag = true;
				}
				String sql = "SELECT MAX(" + WatchTower.WATCH_TOWER_ID
						+ ") FROM " + WatchTower.WATCH_TOWER_TABLE_NAME;
				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					watchId = cursor.getInt(0);
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

			if (watch.getWrAccessory() != null
					&& watch.getWrAccessory().size() > 0) {
				for (int i = 0; i < watch.getWrAccessory().size(); i++) {
					AttachmentDao aDao = new AttachmentDao(context);
					watch.getWrAccessory().get(i)
							.setTableId(WatchTower.WATCH_TOWER_TABLE_ID);
					watch.getWrAccessory().get(i).setRowId(watchId);
					flag = aDao.insertInfo(watch.getWrAccessory().get(i));
				}
			}
		}
		return flag;
	}

	/**
	 * 删除表
	 */
	public boolean delTable(String wrId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		try {
			int res = db.delete(WatchTower.WATCH_TOWER_TABLE_NAME,
					WatchTower.WATCH_TOWER_ID + "=?", new String[] { wrId });

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
	public ArrayList<WatchtowerRegistration> getAllInfos() {

		ArrayList<WatchtowerRegistration> infos = new ArrayList<WatchtowerRegistration>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {

			String sql = "select * from " + WatchTower.WATCH_TOWER_TABLE_NAME
					+ " Left join " + Location.LOCATION_TABLE_NAME + " where "
					+ WatchTower.WATCH_TOWER_TABLE_NAME + "."
					+ WatchTower.LOCATION_ID + " = "
					+ Location.LOCATION_TABLE_NAME + "." + Location.LOCATION_ID;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);

			while (cursor.moveToNext()) {
				WatchtowerRegistration wring = new WatchtowerRegistration();
				wring.setWrId(cursor.getInt(cursor
						.getColumnIndex(WatchTower.WATCH_TOWER_ID)));
				wring.setWrLegend(cursor.getString(cursor
						.getColumnIndex(WatchTower.NOTE)));
				wring.setTbsName(cursor.getString(cursor
						.getColumnIndex(WatchTower.BASE_STATION_NAME)));
				wring.setSpecificLocation(cursor.getString(cursor
						.getColumnIndex(WatchTower.SPECIFIC_LOCATION)));
				wring.setCoordinates(cursor.getString(cursor
						.getColumnIndex(WatchTower.COORDINATES)));
				wring.setAltitude(cursor.getString(cursor
						.getColumnIndex(WatchTower.ALTITUDE)));
				wring.setWtwSpecifications(cursor.getString(cursor
						.getColumnIndex(WatchTower.WATCH_TOWER_NORMS)));
				wring.setTransmission(cursor.getString(cursor
						.getColumnIndex(WatchTower.TRANSPORT_MODE)));
				wring.setBtTime(cursor.getString(cursor
						.getColumnIndex(WatchTower.BUILD_TIME)));

				Loaction l = new Loaction();
				l.setLocation_id(cursor.getInt(cursor
						.getColumnIndex(Location.LOCATION_ID)));
				l.setELEVATION(cursor.getDouble(cursor
						.getColumnIndex(Location.ELEVATION)));
				l.setLATITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LATITUDE)));
				l.setLONGITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LONGITUDE)));
				wring.setLoaction(l);

				infos.add(wring);
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
			int wrid = infos.get(i).getWrId();
			dao = new AttachmentDao(context);
			ArrayList<Accessory> list = dao.getInfosById(
					WatchTower.WATCH_TOWER_TABLE_ID, wrid);
			infos.get(i).setWrAccessory(list);
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
				+ WatchTower.WATCH_TOWER_TABLE_NAME;

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
	 * 根据对象获取JOSN
	 */
	public static String getJson(WatchtowerRegistration wred, int userId)
			throws JSONException {

		JSONObject jo = new JSONObject();
		try {
			jo.put("userId", userId);
			jo.put("tableId", WatchTower.WATCH_TOWER_TABLE_ID);
			jo.put(WatchTower.NOTE, wred.getWrLegend());
			jo.put(WatchTower.BASE_STATION_NAME, wred.getTbsName());
			jo.put(WatchTower.SPECIFIC_LOCATION, wred.getSpecificLocation());
			jo.put(WatchTower.COORDINATES, wred.getCoordinates());
			jo.put(WatchTower.ALTITUDE, wred.getAltitude());
			jo.put(WatchTower.WATCH_TOWER_NORMS, wred.getWtwSpecifications());
			jo.put(WatchTower.TRANSPORT_MODE, wred.getTransmission());
			jo.put(WatchTower.BUILD_TIME, wred.getBtTime());

			JSONObject joLocation = new JSONObject();
			joLocation.put(Location.ELEVATION, wred.getLoaction()
					.getELEVATION());
			joLocation.put(Location.LATITUDE, wred.getLoaction().getLATITUDE());
			joLocation.put(Location.LONGITUDE, wred.getLoaction()
					.getLONGITUDE());
			jo.put(WatchTower.LOCATION_ID, joLocation.toString());
			if (wred.getWrAccessory() != null && wred.getWrAccessory().size() > 0) {
				JSONArray ja = new JSONArray();
				for (int i = 0; i < wred.getWrAccessory().size(); i++) {
					JSONObject json = AttachmentDao.getJson(wred.getWrAccessory()
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
	 * @param wred
	 * @param userId
	 * @return
	 */
	/*public static AjaxParams getParams(WatchtowerRegistration wred, int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", String.valueOf(userId));
		params.put("tableId", String.valueOf(WatchTower.WATCH_TOWER_TABLE_ID));
		if (wred.getWrLegend() != null && !wred.getWrLegend().equals("")) {
			params.put(WatchTower.NOTE, wred.getWrLegend());
		}
		if (wred.getTbsName() != null && !wred.getTbsName().equals("")) {
			params.put(WatchTower.BASE_STATION_NAME, wred.getTbsName());
		}
		if (wred.getSpecificLocation() != null
				&& !wred.getSpecificLocation().equals("")) {
			params.put(WatchTower.SPECIFIC_LOCATION, wred.getSpecificLocation());
		}
		if (wred.getCoordinates() != null && !wred.getCoordinates().equals("")) {
			params.put(WatchTower.COORDINATES, wred.getCoordinates());
		}
		if (wred.getAltitude() != null && !wred.getAltitude().equals("")) {
			params.put(WatchTower.ALTITUDE, wred.getAltitude());
		}
		if (wred.getWtwSpecifications() != null
				&& !wred.getWtwSpecifications().equals("")) {
			params.put(WatchTower.WATCH_TOWER_NORMS,
					wred.getWtwSpecifications());
		}
		if (wred.getTransmission() != null
				&& !wred.getTransmission().equals("")) {
			params.put(WatchTower.TRANSPORT_MODE, wred.getTransmission());
		}
		if (wred.getBtTime() != null && !wred.getBtTime().equals("")) {
			params.put(WatchTower.BUILD_TIME, wred.getBtTime());
		}
		JSONObject joLocation = new JSONObject();
		try {
			joLocation.put(Location.ELEVATION, wred.getLoaction()
					.getELEVATION());
			joLocation.put(Location.LATITUDE, wred.getLoaction().getLATITUDE());
			joLocation.put(Location.LONGITUDE, wred.getLoaction()
					.getLONGITUDE());
			joLocation.put(Location.TIME, wred.getLoaction().getTIME());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.put(WatchTower.LOCATION_ID, joLocation.toString());
		if (wred.getWrAccessory() != null && wred.getWrAccessory().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < wred.getWrAccessory().size(); i++) {
				JSONObject jo = AttachmentDao.getJson(wred.getWrAccessory()
						.get(i));
				ja.put(jo);
			}
			params.put("flist", ja.toString());
		}
		return params;
	}*/
}
