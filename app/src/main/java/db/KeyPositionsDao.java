package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.FireproofKeyAreas;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.KeyPositions;
import db.Database.KeyProtectedPlant;
import db.Database.Location;

/**
 * 重点部位
 * 
 * @author liupeng
 */
public class KeyPositionsDao {

	private ForestDatabaseHelper dbHelper;

	private Context context;
	private static KeyPositionsDao kpDao;

	/**
	 * 
	 */
	public KeyPositionsDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	public static KeyPositionsDao init(Context ct) {
		if (kpDao == null) {
			kpDao = new KeyPositionsDao(ct);
		}
		return kpDao;

	}

	/*
	 * 插入
	 */
	public boolean insertInfo(FireproofKeyAreas fka) {

		boolean flag = false;
		int keyPositionID = -1;
		LocationDao ldao = new LocationDao(context);
		int locationId = ldao.insertInfo(fka.getLocation());

		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//

		if (locationId != -1) {
			Cursor cursor = null;
			try {
				ContentValues contentValues = new ContentValues();
				contentValues.put(KeyPositions.NAME, fka.getAreasName());
				contentValues.put(KeyPositions.SITING, fka.getSiting());
				contentValues.put(KeyPositions.PERSON_IN_CHARGE,
						fka.getPersonInCharge());
				contentValues.put(KeyPositions.HEAD_OF_SECURITY,
						fka.getHeadOfSecurity());
				contentValues.put(KeyPositions.YEAR, fka.getYear());
				contentValues.put(KeyPositions.EMPLOYEES, fka.getEmployees());
				contentValues.put(KeyPositions.CONTACT, fka.getContact());
				contentValues.put(KeyPositions.AREA, fka.getArea());
				contentValues.put(KeyPositions.TRAFFIC, fka.getTraffic());
				contentValues.put(KeyPositions.LAT_LON_ALTITUDE,
						fka.getLatLonAltitude());
				contentValues.put(KeyPositions.KEY_POSITION_SERVER_ID,
						fka.getServerId());
				contentValues.put(KeyPositions.SCOPE_AND_NATURE,
						fka.getScopeAndNature());
				contentValues.put(KeyPositions.FOUR_CASES, fka.getFourCases());
				contentValues.put(KeyPositions.FS_SURROUNDING,
						fka.getFsSurrounding());
				contentValues.put(KeyPositions.REASON_TUBE,
						fka.getTheReasonTube());
				contentValues.put(KeyPositions.LOCATION_ID, locationId);
				// contentValues.put( KeyPositions.ATTACHEMENT,
				// fka.getFwaAccessory());
				contentValues.put(KeyPositions.NOTE, fka.getFkaLegend());
				long ok = db.insert(KeyPositions.KEY_POSITION_TABLE_NAME, null,
						contentValues);
				// System.out.println("insert is complete..." + ok);
				if (-1 != ok) {
					flag = true;
				}

				String sql = "SELECT MAX(" + KeyPositions.KEY_POSITION_ID
						+ ") FROM " + KeyPositions.KEY_POSITION_TABLE_NAME;
				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					keyPositionID = cursor.getInt(0);
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
			if (fka.getFwaAccessory() != null
					&& fka.getFwaAccessory().size() > 0) {
				AttachmentDao dao = new AttachmentDao(context);
				for (int i = 0; i < fka.getFwaAccessory().size(); i++) {
					fka.getFwaAccessory().get(i)
							.setTableId(KeyPositions.KEY_POSITION_TABLE_ID);
					fka.getFwaAccessory().get(i).setRowId(keyPositionID);
					flag = dao.insertInfo(fka.getFwaAccessory().get(i));
				}
			}
		}
		return flag;

	}

	/**
	 * 删表
	 */
	public boolean delTable(String positionId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db.delete(KeyPositions.KEY_POSITION_TABLE_NAME,
					KeyPositions.KEY_POSITION_ID + "=?",
					new String[] { positionId });
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
	public ArrayList<FireproofKeyAreas> getAllInfos() {
		ArrayList<FireproofKeyAreas> infos = new ArrayList<FireproofKeyAreas>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		try {

			String sql = "select * from "
					+ KeyPositions.KEY_POSITION_TABLE_NAME + " Left join "
					+ Location.LOCATION_TABLE_NAME + " where "
					+ KeyPositions.KEY_POSITION_TABLE_NAME + "."
					+ KeyPositions.LOCATION_ID + " = "
					+ Location.LOCATION_TABLE_NAME + "." + Location.LOCATION_ID;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				FireproofKeyAreas info = new FireproofKeyAreas();
				info.setAreasName(cursor.getString(cursor
						.getColumnIndex(KeyPositions.NAME)));
				info.setArea(cursor.getString(cursor
						.getColumnIndex(KeyPositions.AREA)));
				info.setServerId(cursor.getInt(cursor
						.getColumnIndex(KeyPositions.KEY_POSITION_SERVER_ID)));
				info.setYear(cursor.getInt(cursor
						.getColumnIndex(KeyPositions.YEAR)));
				info.setContact(cursor.getString(cursor
						.getColumnIndex(KeyPositions.CONTACT)));
				info.setEmployees(cursor.getString(cursor
						.getColumnIndex(KeyPositions.EMPLOYEES)));
				info.setFkaId(cursor.getInt(cursor
						.getColumnIndex(KeyPositions.KEY_POSITION_ID)));
				info.setFkaLegend(cursor.getString(cursor
						.getColumnIndex(KeyPositions.NOTE)));
				info.setFourCases(cursor.getString(cursor
						.getColumnIndex(KeyPositions.FOUR_CASES)));
				info.setFsSurrounding(cursor.getString(cursor
						.getColumnIndex(KeyPositions.FS_SURROUNDING)));
				info.setHeadOfSecurity(cursor.getString(cursor
						.getColumnIndex(KeyPositions.HEAD_OF_SECURITY)));
				info.setLatLonAltitude(cursor.getString(cursor
						.getColumnIndex(KeyPositions.LAT_LON_ALTITUDE)));

				Loaction l = new Loaction();
				l.setLocation_id(cursor.getInt(cursor
						.getColumnIndex(Location.LOCATION_ID)));
				l.setELEVATION(cursor.getDouble(cursor
						.getColumnIndex(Location.ELEVATION)));
				l.setLATITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LATITUDE)));
				l.setLONGITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LONGITUDE)));
				info.setLocation(l);

				info.setPersonInCharge(cursor.getString(cursor
						.getColumnIndex(KeyPositions.PERSON_IN_CHARGE)));
				info.setScopeAndNature(cursor.getString(cursor
						.getColumnIndex(KeyPositions.SCOPE_AND_NATURE)));
				info.setSiting(cursor.getString(cursor
						.getColumnIndex(KeyPositions.SITING)));
				info.setTheReasonTube(cursor.getString(cursor
						.getColumnIndex(KeyPositions.REASON_TUBE)));
				info.setTraffic(cursor.getString(cursor
						.getColumnIndex(KeyPositions.TRAFFIC)));

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
			int rowid = infos.get(i).getFkaId();
			dao = new AttachmentDao(context);
			ArrayList<Accessory> list = dao.getInfosById(
					KeyPositions.KEY_POSITION_TABLE_ID, rowid);
			infos.get(i).setFwaAccessory(list);
		}

		return infos;
	}

	/*
	 * 根据对象获取JSON
	 */
	public static String getJson(FireproofKeyAreas fka, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("userId", userId);
			jo.put("tableId", KeyPositions.KEY_POSITION_TABLE_ID);
			jo.put(KeyPositions.NAME, fka.getAreasName());
			jo.put(KeyPositions.YEAR, fka.getYear()+"");
			jo.put(KeyPositions.KEY_POSITION_SERVER_ID, fka.getServerId() + "");
			jo.put(KeyPositions.SITING, fka.getSiting());
			jo.put(KeyPositions.PERSON_IN_CHARGE, fka.getPersonInCharge());
			jo.put(KeyPositions.HEAD_OF_SECURITY, fka.getHeadOfSecurity());
			jo.put(KeyPositions.EMPLOYEES, fka.getEmployees());
			jo.put(KeyPositions.CONTACT, fka.getContact());
			jo.put(KeyPositions.AREA, fka.getArea());
			jo.put(KeyPositions.TRAFFIC, fka.getTraffic());
			jo.put(KeyPositions.LAT_LON_ALTITUDE, fka.getLatLonAltitude());
			jo.put(KeyPositions.SCOPE_AND_NATURE, fka.getScopeAndNature());
			jo.put(KeyPositions.FOUR_CASES, fka.getFourCases());
			jo.put(KeyPositions.FS_SURROUNDING, fka.getFsSurrounding());
			jo.put(KeyPositions.REASON_TUBE, fka.getTheReasonTube());
			// jo.put( KeyPositions.ATTACHEMENT,
			// fka.getFwaAccessory());
			jo.put(KeyPositions.NOTE, fka.getFkaLegend());

			JSONObject joLocation = new JSONObject();
			joLocation
					.put(Location.ELEVATION, fka.getLocation().getELEVATION());
			joLocation.put(Location.LATITUDE, fka.getLocation().getLATITUDE());
			joLocation
					.put(Location.LONGITUDE, fka.getLocation().getLONGITUDE());

			jo.put(KeyProtectedPlant.LOCATION_ID, joLocation.toString());
			if (fka.getFwaAccessory() != null && fka.getFwaAccessory().size() > 0) {
				JSONArray ja = new JSONArray();
				for (int i = 0; i < fka.getFwaAccessory().size(); i++) {
					JSONObject json = AttachmentDao.getJson(fka.getFwaAccessory()
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
	 * @param fka
	 * @param userId
	 * @return
	 */
	/*public static AjaxParams getParams(FireproofKeyAreas fka, int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId + "");
		params.put("tableId", KeyPositions.KEY_POSITION_TABLE_ID + "");
		params.put(KeyPositions.YEAR, fka.getYear() + "");
		params.put(KeyPositions.KEY_POSITION_SERVER_ID, fka.getServerId() + "");
		if (fka.getSiting() != null && !fka.getSiting().equals("")) {
			params.put(KeyPositions.SITING, fka.getSiting());
		}
		if (fka.getAreasName() != null && !fka.getAreasName().equals("")) {
			params.put(KeyPositions.NAME, fka.getAreasName());
		}
		if (fka.getPersonInCharge() != null
				&& !fka.getPersonInCharge().equals("")) {
			params.put(KeyPositions.PERSON_IN_CHARGE, fka.getPersonInCharge());
		}
		if (fka.getHeadOfSecurity() != null
				&& !fka.getHeadOfSecurity().equals("")) {
			params.put(KeyPositions.HEAD_OF_SECURITY, fka.getHeadOfSecurity());
		}
		if (fka.getEmployees() != null && !fka.getEmployees().equals("")) {
			params.put(KeyPositions.EMPLOYEES, fka.getEmployees());
		}
		if (fka.getContact() != null && !fka.getContact().equals("")) {
			params.put(KeyPositions.CONTACT, fka.getContact());
		}
		if (fka.getArea() != null && !fka.getArea().equals("")) {
			params.put(KeyPositions.AREA, fka.getArea());
		}
		if (fka.getTraffic() != null && !fka.getTraffic().equals("")) {
			params.put(KeyPositions.TRAFFIC, fka.getTraffic());
		}
		if (fka.getLatLonAltitude() != null
				&& !fka.getLatLonAltitude().equals("")) {
			params.put(KeyPositions.LAT_LON_ALTITUDE, fka.getLatLonAltitude());
		}
		if (fka.getScopeAndNature() != null
				&& !fka.getScopeAndNature().equals("")) {
			params.put(KeyPositions.SCOPE_AND_NATURE, fka.getScopeAndNature());
		}
		if (fka.getFourCases() != null && !fka.getFourCases().equals("")) {
			params.put(KeyPositions.FOUR_CASES, fka.getFourCases());
		}
		if (fka.getFsSurrounding() != null
				&& !fka.getFsSurrounding().equals("")) {
			params.put(KeyPositions.FS_SURROUNDING, fka.getFsSurrounding());
		}
		if (fka.getTheReasonTube() != null
				&& !fka.getTheReasonTube().equals("")) {
			params.put(KeyPositions.REASON_TUBE, fka.getTheReasonTube());
		}
		if (fka.getFkaLegend() != null && !fka.getFkaLegend().equals("")) {
			params.put(KeyPositions.NOTE, fka.getFkaLegend());
		}
		if (fka.getLocation() != null) {
			JSONObject locationJo = new JSONObject();
			try {
				locationJo.put(Location.ELEVATION, fka.getLocation()
						.getELEVATION());
				locationJo.put(Location.LATITUDE, fka.getLocation()
						.getLATITUDE());
				locationJo.put(Location.LONGITUDE, fka.getLocation()
						.getLONGITUDE());
				locationJo.put("Time", fka.getLocation().getTIME());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			params.put(ForestPatrolWork.LOCATION_ID, locationJo.toString());
		}
		if (fka.getFwaAccessory() != null && fka.getFwaAccessory().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < fka.getFwaAccessory().size(); i++) {
				JSONObject json = AttachmentDao.getJson(fka.getFwaAccessory()
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
	public ArrayList<FireproofKeyAreas> getObject(String json)
			throws JSONException {
		ArrayList<FireproofKeyAreas> fkas = null;
		JSONObject jso = new JSONObject(json);
		if (jso.getInt("Error") == 0) {
			JSONArray ja = jso.getJSONArray("PartList");
			fkas = new ArrayList<FireproofKeyAreas>();
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				FireproofKeyAreas fka = new FireproofKeyAreas();
				if (!jo.isNull(KeyPositions.NAME)) {
					fka.setAreasName(jo.getString(KeyPositions.NAME));
				}
				if (!jo.isNull(KeyPositions.AREA)) {
					fka.setArea(jo.getString(KeyPositions.AREA));
				}
				if (!jo.isNull(KeyPositions.KEY_POSITION_SERVER_ID)) {
					fka.setServerId(jo
							.getInt(KeyPositions.KEY_POSITION_SERVER_ID));
				}
				if (!jo.isNull(KeyPositions.YEAR)
						&& !jo.getString(KeyPositions.YEAR).equals("")) {
					fka.setYear(jo.getInt(KeyPositions.YEAR));
				}
				if (!jo.isNull(KeyPositions.SITING)) {
					try {
						fka.setSiting(jo.getString(KeyPositions.SITING));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (!jo.isNull(KeyPositions.PERSON_IN_CHARGE)) {
					fka.setPersonInCharge(jo
							.getString(KeyPositions.PERSON_IN_CHARGE));
				}
				if (!jo.isNull(KeyPositions.CONTACT)) {
					fka.setContact(jo.getString(KeyPositions.CONTACT));
				}
				if (!jo.isNull(KeyPositions.EMPLOYEES)) {
					fka.setEmployees(jo.getString(KeyPositions.EMPLOYEES));
				}
				if (!jo.isNull(KeyPositions.TRAFFIC)) {
					fka.setTraffic(jo.getString(KeyPositions.TRAFFIC));
				}
				if (!jo.isNull(KeyPositions.SCOPE_AND_NATURE)) {
					fka.setScopeAndNature(jo
							.getString(KeyPositions.SCOPE_AND_NATURE));
				}
				if (!jo.isNull(KeyPositions.REASON_TUBE)) {
					fka.setTheReasonTube(jo.getString(KeyPositions.REASON_TUBE));
				}
				if (!jo.isNull(KeyPositions.KEY_POSITION_ID)) {
					try {
						fka.setFkaId(Integer.parseInt(jo
								.getString(KeyPositions.KEY_POSITION_ID)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (!jo.isNull(KeyPositions.NOTE)) {
					fka.setFkaLegend(jo.getString(KeyPositions.NOTE));
				}
				if (!jo.isNull(KeyPositions.FOUR_CASES)) {
					fka.setFourCases(jo.getString(KeyPositions.FOUR_CASES));
				}
				if (!jo.isNull(KeyPositions.FS_SURROUNDING)) {
					fka.setFsSurrounding(jo
							.getString(KeyPositions.FS_SURROUNDING));
				}
				if (!jo.isNull(KeyPositions.HEAD_OF_SECURITY)) {
					fka.setHeadOfSecurity(jo
							.getString(KeyPositions.HEAD_OF_SECURITY));
				}
				if (!jo.isNull(KeyPositions.LAT_LON_ALTITUDE)) {
					fka.setLatLonAltitude(jo
							.getString(KeyPositions.LAT_LON_ALTITUDE));
				}
				if (!jo.isNull(KeyPositions.LOCATION_ID)) {

					JSONObject joLocation = jo
							.getJSONObject(KeyPositions.LOCATION_ID);
					Loaction l = new Loaction();
					if (joLocation.getDouble(Location.LATITUDE) != 0) {
						l.setELEVATION(joLocation.getDouble(Location.ELEVATION));
						l.setLATITUDE(joLocation.getDouble(Location.LATITUDE));
						l.setLONGITUDE(joLocation.getDouble(Location.LONGITUDE));
						fka.setLocation(l);
					}
				}

				if (!jo.isNull("affix") && !jo.getString("affix").equals("")) {
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

					fka.setFwaAccessory(accessorys);

				}
				fkas.add(fka);
			}
		}
		return fkas;
	}

	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM "
				+ KeyPositions.KEY_POSITION_TABLE_NAME;

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

}
