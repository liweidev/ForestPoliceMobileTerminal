package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.AlarmStateEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.AlarmingHistoryInforEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.PoliceHeadEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import java.util.ArrayList;

import db.Database.AlarmingHistoryInfo;

public class AlarmingHistoryDao {
	private ForestDatabaseHelper dbHelper;

	private Context context;

	/**
	 * 
	 */
	public AlarmingHistoryDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	/*
	 * 插入
	 */
	public boolean insertInfo(AlarmingHistoryInforEntity ahie) {

		boolean flag = false;

		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//

		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(AlarmingHistoryInfo.TIME,
					ahie.getTime());
			contentValues.put(AlarmingHistoryInfo.HISOPTION, ahie.getHisOpinion());
			if(ahie.getAlarmState()!=null){
				contentValues.put(AlarmingHistoryInfo.ALARM_STATE, ahie.getAlarmState().getStateName());
			}
			if(ahie.getPolice()!=null){
				contentValues.put(AlarmingHistoryInfo.POLICE, ahie.getPolice().getPeoplePoliceName());
			}
			
			contentValues.put(AlarmingHistoryInfo.ALARMID, ahie.getAlarmId());

			long ok = db.insert(AlarmingHistoryInfo.ALARMING_HISTORY_INFO_TABLE_NAME, null,
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

	/*
	 * 插入并返回ID
	 
	public int insertInfoAndReturnId(Accessory accessory) {

		int flag = -1;
		String sql = "select  max(" + Location.LOCATION_ID + ") from "
				+ Location.LOCATION_TABLE_NAME;
		Cursor cursor = null;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//

		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(Attachment.FILE_PATH,
					accessory.getAccessoryPath());
			contentValues.put(Attachment.FILE_TYPE, accessory.getFileType());
			contentValues.put(Attachment.TABLE_ID, accessory.getTableId());
			contentValues.put(Attachment.ROW_ID, accessory.getRowId());

			long ok = db.insert(Attachment.ATTACHMENT_TABLE_NAME, null,
					contentValues);
			// System.out.println("insert is complete..." + ok);
			if (-1 != ok) {
				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					flag = cursor.getInt(cursor.getInt(0));
				}
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = -1;
		} finally {
			db.endTransaction();
		}
		return flag;

	}*/

	/**
	 * 删表
	 */
	public boolean delTable(int attachmentId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db.delete(AlarmingHistoryInfo.ALARMING_HISTORY_INFO_TABLE_NAME, AlarmingHistoryInfo.ID+"=?"	, new String[]{attachmentId+""});
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
	public boolean delFromAlarmID(int alarmID) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db.delete(AlarmingHistoryInfo.ALARMING_HISTORY_INFO_TABLE_NAME, AlarmingHistoryInfo.ALARMID+"=?"	, new String[]{alarmID+""});
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
	public ArrayList<AlarmingHistoryInforEntity> getInfosById(int alarmId) {
		ArrayList<AlarmingHistoryInforEntity> infos = new ArrayList<AlarmingHistoryInforEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		try {

			String sql = "select * from " + AlarmingHistoryInfo.ALARMING_HISTORY_INFO_TABLE_NAME
					+ " where " + AlarmingHistoryInfo.ALARMID + "= '" + alarmId + 
					 "'";
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				AlarmingHistoryInforEntity info = new AlarmingHistoryInforEntity();
				info.setTime(cursor.getString(cursor
						.getColumnIndex(AlarmingHistoryInfo.TIME)));
				
				info.setHisOpinion(cursor.getString(cursor
						.getColumnIndex(AlarmingHistoryInfo.HISOPTION)));
				AlarmStateEntity ase=new AlarmStateEntity();
				ase.setStateName(cursor.getString(cursor
						.getColumnIndex(AlarmingHistoryInfo.ALARM_STATE)));
				info.setAlarmState(ase);
				PoliceHeadEntity phe=new PoliceHeadEntity();
				phe.setPeoplePoliceName(cursor.getString(cursor
						.getColumnIndex(AlarmingHistoryInfo.POLICE)));
				info.setPolice(phe);
				info.setAlarmId(cursor.getInt(cursor
						.getColumnIndex(AlarmingHistoryInfo.ALARMID)));
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
	
	/*public static JSONObject getJson(Accessory acc){
		JSONObject jo = new JSONObject();
		String type = acc.getAccessoryPath().substring(acc.getAccessoryPath().lastIndexOf(".")+1);
		String baos = null;
		try {
			if (acc.getAccessoryPath().indexOf("http://") != -1) {
				baos = acc.getAccessoryPath();
				type = "net";
			}else{
				baos = ActivityUtils.encodeBase64File(acc.getAccessoryPath());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if (type != null && type.equals("jpg")) {
				jo.put("fileType", 1);
			}else if (type != null && (type.equals("3gpp") || type.equals("amr") || type.equals("ogg"))) {
				jo.put("fileType", 2);
			}else if (type != null && (type.equals("mp4") || type.equals("3gp") || type.equals("avi"))) {
				jo.put("fileType", 2);
			}else if(type != null && type.equals("net")){
				jo.put("fileType", 0);
			}
			jo.put("fileName", acc.getAccessoryPath().substring(acc.getAccessoryPath().lastIndexOf("/")+1));
			jo.put("fileBase64", baos);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return jo;
		}*/
}
