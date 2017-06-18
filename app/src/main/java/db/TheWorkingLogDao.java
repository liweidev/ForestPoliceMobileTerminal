package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.TheWorkingLogEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.TheWorkingLog;

/**
 * 工作日志
 * 
 * @author xingyimin
 * 
 */
public class TheWorkingLogDao {
	private ForestDatabaseHelper dbHelper;

	/**
	 * 
	 */
	public TheWorkingLogDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
	}

	/*
	 * 插入
	 */
	public boolean insert(TheWorkingLogEntity log) {
		boolean flag = false;
		long ok = -1;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {

			ContentValues cv = new ContentValues();
			cv.put(TheWorkingLog.CLASS_LEADERS, log.getClassLearder());
			cv.put(TheWorkingLog.POLICE_ON_DUTY, log.getPoliceName());
			cv.put(TheWorkingLog.THE_MAIN_WORK_AND_IMPORTANT_EVENTS,
					log.getEvent());
			cv.put(TheWorkingLog.POLICE_ON_DUTY, log.getPoliceName());
			cv.put(TheWorkingLog.THE_WORKING_LOG_DATE, log.getDate());
			cv.put(TheWorkingLog.WEATHER, log.getWeatherId());
			
			ok = db.insert(TheWorkingLog.THE_WORKING_LOG_TABLE_NAME, null, cv);
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
	public boolean delTable(String logId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db.delete(TheWorkingLog.THE_WORKING_LOG_TABLE_NAME,
					TheWorkingLog.THE_WORKING_LOG_ID + "=?",
					new String[] { logId });
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
				+ TheWorkingLog.THE_WORKING_LOG_TABLE_NAME;

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
	public ArrayList<TheWorkingLogEntity> getAllInfos() {
		ArrayList<TheWorkingLogEntity> infos = new ArrayList<TheWorkingLogEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		try {
			String sql = "select * from "
					+ TheWorkingLog.THE_WORKING_LOG_TABLE_NAME;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				TheWorkingLogEntity info = new TheWorkingLogEntity();
				info.setLogId(cursor.getInt(cursor
						.getColumnIndex(TheWorkingLog.THE_WORKING_LOG_ID)));
				info.setPoliceName(cursor.getString(cursor
						.getColumnIndex(TheWorkingLog.POLICE_ON_DUTY)));
				info.setClassLearder(cursor.getInt(cursor
						.getColumnIndex(TheWorkingLog.CLASS_LEADERS)));
				info.setDate(cursor.getString(cursor
						.getColumnIndex(TheWorkingLog.THE_WORKING_LOG_DATE)));
				info.setEvent(cursor.getString(cursor
						.getColumnIndex(TheWorkingLog.THE_MAIN_WORK_AND_IMPORTANT_EVENTS)));
				info.setWeatherId(cursor.getInt(cursor
						.getColumnIndex(TheWorkingLog.WEATHER)));
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
	/*public static AjaxParams getParams(TheWorkingLogEntity log,int userId,String userName){
		AjaxParams params = new AjaxParams();
		
		
		params.put("userId", String.valueOf(userId));
		params.put("userName", userName);
		params.put("tableId", String.valueOf(TheWorkingLog.THE_WORKING_LOG_TABLE_ID));
		params.put(TheWorkingLog.THE_WORKING_LOG_DATE, log.getDate());
		params.put(TheWorkingLog.WEATHER, log.getWeatherId()+"");
		params.put(TheWorkingLog.CLASS_LEADERS, log.getClassLearder()+"");
		params.put(TheWorkingLog.THE_MAIN_WORK_AND_IMPORTANT_EVENTS,
				log.getEvent());
		params.put("the_working_log_Week", log.getWeek());
		params.put("police_on_duty", log.getPoliceName());
		return params;
	}*/
	/**
	 * 根据对象获取json格式字符串
	 * @throws JSONException 
	 * */
	public static String getJson(TheWorkingLogEntity log,int userId,String userName) throws JSONException{
		//AjaxParams params = new AjaxParams();
		JSONObject json = new JSONObject();
		
		json.put("userId", String.valueOf(userId));
		json.put("userName", userName);
		json.put("tableId", String.valueOf(TheWorkingLog.THE_WORKING_LOG_TABLE_ID));
		json.put(TheWorkingLog.THE_WORKING_LOG_DATE, log.getDate());
		json.put(TheWorkingLog.WEATHER, log.getWeatherId());
		json.put(TheWorkingLog.CLASS_LEADERS, log.getClassLearder());
		json.put(TheWorkingLog.THE_MAIN_WORK_AND_IMPORTANT_EVENTS,
				log.getEvent());
		json.put("the_working_log_Week", log.getWeek());
		json.put("police_on_duty", log.getPoliceName());
		return json.toString();
	}
}
