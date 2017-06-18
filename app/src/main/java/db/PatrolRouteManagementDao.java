package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.PatrolRouteManagementEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.PatrolRouteManagement;

/**
 * 巡防路线管理
 * 
 * @author xingyimin
 * 
 */
public class PatrolRouteManagementDao {
	private ForestDatabaseHelper dbHelper;

	public PatrolRouteManagementDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
	}

	/*
	 * 插入
	 */
	public boolean insert(PatrolRouteManagementEntity patrol) {
		boolean flag = false;
		long ok = -1;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {

			ContentValues cv = new ContentValues();
			cv.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_NAME,
					patrol.getName());
			cv.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TYPE,
					patrol.getTypeId());
			cv.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TRAVEL_ADVICE,
					patrol.getTravelAdviceId());
			cv.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_ROUTE,
					patrol.getRoute());
			cv.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_DISTANCE,
					patrol.getDistance());
			cv.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_WEATHER,
					patrol.getWeather());

			ok = db.insert(
					PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TABLE_NAME,
					null, cv);
			// System.out.println("insert is complete..." + ok);
			if (-1 != ok) {
				flag = true;
			}
			db.setTransactionSuccessful();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			flag = false;
			e.printStackTrace();
		} finally {
			db.endTransaction();
//			this.dbHelper.close(db);
		}
		return flag;
	}

	/**
	 * 删表
	 */
	public boolean delTable(String patrolId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db.delete(
					PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TABLE_NAME,
					PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_ID + "=?",
					new String[] { patrolId });
			if (res > 0) {
				isOk = true;
			}
			db.setTransactionSuccessful();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
//			this.dbHelper.close(db);
		}
		return isOk;
	}

	/**
	 * 获取本地数据个数
	 * 
	 * @return
	 */
	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM "
				+ PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TABLE_NAME;

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

	/**
	 * 获取本地所有
	 * 
	 */
	public ArrayList<PatrolRouteManagementEntity> getAllInfos() {
		ArrayList<PatrolRouteManagementEntity> infos = new ArrayList<PatrolRouteManagementEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		try {
			String sql = "select * from "
					+ PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TABLE_NAME;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				PatrolRouteManagementEntity info = new PatrolRouteManagementEntity();
				info.setPatrolId(cursor.getInt(cursor
						.getColumnIndex(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_ID)));
				info.setName(cursor.getString(cursor
						.getColumnIndex(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_NAME)));
				info.setTypeId(cursor.getInt(cursor
						.getColumnIndex(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TYPE)));
				info.setTravelAdviceId(cursor.getInt(cursor
						.getColumnIndex(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TRAVEL_ADVICE)));
				info.setDistance(cursor.getDouble(cursor
						.getColumnIndex(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_DISTANCE)));
				info.setRoute(cursor.getString(cursor
						.getColumnIndex(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_ROUTE)));
				info.setWeather(cursor.getInt(cursor
						.getColumnIndex(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_WEATHER)));
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
		return infos;
	}

	/**
	 * 根据对象获取API参数
	 * 
	 * @param patrol
	 * @param userId
	 * @return
	 */
	/*public static AjaxParams getParams(PatrolRouteManagementEntity patrol,
			int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", String.valueOf(userId));
		params.put(
				"tableId",
				String.valueOf(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TABLE_ID));
		params.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_NAME,
				patrol.getName());
		params.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TYPE,
				String.valueOf(patrol.getTypeId()));
		params.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TRAVEL_ADVICE,
				String.valueOf(patrol.getTravelAdviceId()));
		params.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_DISTANCE,
				String.valueOf(patrol.getDistance()));
		params.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_ROUTE,
				patrol.getRoute());
		// Log.e("route", patrol.getRoute());
		params.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_WEATHER,
				String.valueOf(patrol.getWeather()));
		return params;
	}*/
	/**
	 * 根据对象获取json格式的字符串
	 * */
	public static String getJson(PatrolRouteManagementEntity patrol,
			int userId) throws JSONException {
		//AjaxParams params = new AjaxParams();
		JSONObject json = new JSONObject();
		json.put("userId", String.valueOf(userId));
		json.put(
				"tableId",
				String.valueOf(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TABLE_ID));
		json.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_NAME,
				patrol.getName());
		json.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TYPE,
				String.valueOf(patrol.getTypeId()));
		json.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_TRAVEL_ADVICE,
				String.valueOf(patrol.getTravelAdviceId()));
		json.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_DISTANCE,
				String.valueOf(patrol.getDistance()));
		json.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_ROUTE,
				patrol.getRoute());
		// Log.e("route", patrol.getRoute());
		json.put(PatrolRouteManagement.PATROL_ROUTE_MANAGEMENT_WEATHER,
				String.valueOf(patrol.getWeather()));
		return json.toString();
	}
	
	
}
