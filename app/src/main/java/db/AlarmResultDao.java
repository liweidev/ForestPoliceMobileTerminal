package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.AlarmingResultEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.AlarmResult;

/**
 * 处理结果数据操作
 * 
 * @author qianhaohong
 * 
 */
public class AlarmResultDao {
	private ForestDatabaseHelper dbHelper;

	private static Context context;

	/**
	 * 
	 */
	public AlarmResultDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	/*
	 * 插入
	 */
	public boolean insertInfo(AlarmingResultEntity art) {

		boolean flag = false;

		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//

		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(AlarmResult.ALARM_RESULT,
					art.getResult());
			contentValues.put(AlarmResult.STATEID,
					art.getStateID());
			contentValues.put(AlarmResult.ACCEPT_UNIT, art.getAcceptanceUnit());
			contentValues.put(AlarmResult.ACCEPT_TIME, art.getAcceptanceTime());
			contentValues.put(AlarmResult.CRIMINAL_CASE_CODE, art.getCriminalCaseNum());
			contentValues.put(AlarmResult.ADMINISTRATIVE_CASE_CODE, art.getAdministrativeCaseNum());
			contentValues.put(AlarmResult.ALARMID, art.getAlarmID());
			contentValues.put(AlarmResult.POLICE_PEOPLE_ID, art.getPeoplePoliceId());
			

			long ok = db.insert(AlarmResult.RECEIVE_DISPOSE_ALARM_TABLE_NAME, null,
					contentValues);
			// System.out.println("insert is complete..." + ok);
			if (-1 != ok) {
				flag = true;
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		} finally {
			db.endTransaction();
		}
		return flag;

	}

	/**
	 * 删表
	 */
	public boolean delTable(int attachmentId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db.delete(AlarmResult.RECEIVE_DISPOSE_ALARM_TABLE_NAME, AlarmResult.RESULT_ID+"=?"	, new String[]{attachmentId+""});
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
	 * 判断数据库中是否已存在此数据
	 * 存在 返回false
	 * 不存在返回 true
	 */
	public boolean query(String alarm_id){
		boolean flag=false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		String sql="SELECT * FROM "
				+ AlarmResult.RECEIVE_DISPOSE_ALARM_TABLE_NAME+" where alarm_id='"
				+alarm_id+"';";
		try{
			cursor=db.rawQuery(sql, null);
			if(cursor.moveToNext()){
				
			}else{
				flag=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			db.endTransaction();
			if (cursor != null) {
				cursor.close();
			}
		}
		return flag;
	}
	
	
	/**
	 * 获取本地所有
	 * 
	 */
	public ArrayList<AlarmingResultEntity> getInfosById(int alarmId) {
		ArrayList<AlarmingResultEntity> infos = new ArrayList<AlarmingResultEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		try {

			String sql = "select * from " + AlarmResult.RECEIVE_DISPOSE_ALARM_TABLE_NAME
					+ " where " + AlarmResult.ALARMID + "= '" + alarmId + 
					 "'";
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				AlarmingResultEntity info = new AlarmingResultEntity();
				info.setResult(cursor.getString(cursor
						.getColumnIndex(AlarmResult.ALARM_RESULT)));
				info.setStateID(cursor.getInt(cursor
						.getColumnIndex(AlarmResult.STATEID)));
				info.setAcceptanceUnit(cursor.getString(cursor
						.getColumnIndex(AlarmResult.ACCEPT_UNIT)));
				info.setAcceptanceTime(cursor.getString(cursor
						.getColumnIndex(AlarmResult.ACCEPT_TIME)));
				info.setAdministrativeCaseNum(cursor.getString(cursor
						.getColumnIndex(AlarmResult.ADMINISTRATIVE_CASE_CODE)));
				info.setCriminalCaseNum(cursor.getString(cursor
						.getColumnIndex(AlarmResult.CRIMINAL_CASE_CODE)));
				info.setAlarmID(cursor.getInt(cursor
						.getColumnIndex(AlarmResult.ALARMID)));
				info.setPeoplePoliceId(cursor.getInt(cursor
						.getColumnIndex(AlarmResult.POLICE_PEOPLE_ID)));
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
	public ArrayList<AlarmingResultEntity> getInfosById() {
		ArrayList<AlarmingResultEntity> infos = new ArrayList<AlarmingResultEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		try {

			String sql = "select * from " + AlarmResult.RECEIVE_DISPOSE_ALARM_TABLE_NAME;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				AlarmingResultEntity info = new AlarmingResultEntity();
				info.setResultId(cursor.getInt(cursor.getColumnIndex(AlarmResult.RESULT_ID)));
				info.setAlarmID(cursor.getInt(cursor.getColumnIndex(AlarmResult.ALARMID)));
				info.setStateID(cursor.getInt(cursor.getColumnIndex(AlarmResult.STATEID)));
				info.setResult(cursor.getString(cursor
						.getColumnIndex(AlarmResult.ALARM_RESULT)));
				info.setAcceptanceUnit(cursor.getString(cursor
						.getColumnIndex(AlarmResult.ACCEPT_UNIT)));
				info.setAcceptanceTime(cursor.getString(cursor
						.getColumnIndex(AlarmResult.ACCEPT_TIME)));
				info.setAdministrativeCaseNum(cursor.getString(cursor
						.getColumnIndex(AlarmResult.ADMINISTRATIVE_CASE_CODE)));
				info.setCriminalCaseNum(cursor.getString(cursor
						.getColumnIndex(AlarmResult.CRIMINAL_CASE_CODE)));
				info.setPeoplePoliceId(cursor.getInt(cursor
						.getColumnIndex(AlarmResult.POLICE_PEOPLE_ID)));
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
	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM "
				+ AlarmResult.RECEIVE_DISPOSE_ALARM_TABLE_NAME;

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
	public static String getAlarmJson(AlarmingResultEntity alarm) throws JSONException {
		JSONObject jo = new JSONObject();
		jo.put("DeviceSN", ActivityUtils.getDeviceID(context));
		jo.put("AlarmID", alarm.getAlarmID());
		jo.put("StateID", alarm.getStateID());
		jo.put("Result", alarm.getResult());
		jo.put("AcceptanceUnit", alarm.getAcceptanceUnit());
		jo.put("AcceptanceTime", alarm.getAcceptanceTime());
		jo.put("CriminalCaseNum",alarm.getCriminalCaseNum());
		jo.put("AdministrativeCaseNum", alarm.getAdministrativeCaseNum());
		jo.put("PolicePeopleID", alarm.getPeoplePoliceId());
		return jo.toString();
	}
}
