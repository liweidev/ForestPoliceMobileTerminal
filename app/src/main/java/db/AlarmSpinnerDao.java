package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.AlarmSpinnerEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.AlarmSpinnerInfo;

public class AlarmSpinnerDao {
	private ForestDatabaseHelper dbHelper;
	private Context context;
	private static AlarmSpinnerDao asd;
	
	
	public AlarmSpinnerDao( Context context) {
		super();
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}
	public static AlarmSpinnerDao init(Context ct) {
		if (asd == null) {
			asd = new AlarmSpinnerDao(ct);
		}
		return asd;
	}
	/*
	 * 插入
	 */

	public boolean insert(AlarmSpinnerEntity ase ) {
		int plantId = -1;
		boolean flag = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();

		Cursor cursor = null;
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(AlarmSpinnerInfo.ID,ase.getId());
			contentValues.put(AlarmSpinnerInfo.ALARMWAY_ID,ase.getAlarmWayId());
			contentValues.put(AlarmSpinnerInfo.NATION_ID, ase.getNationId());
			contentValues.put(AlarmSpinnerInfo.PARTS_CULTURE_ID, ase.getParts_culture_id());
			contentValues.put(AlarmSpinnerInfo.PARTS_POST_ID, ase.getParts_post_id());
			contentValues.put(AlarmSpinnerInfo.TREATMENT_ADVICE_ID, ase.getTreatmentAdviceID());
			contentValues.put(AlarmSpinnerInfo.STATE_ID, ase.getState_id());
			
			contentValues.put(AlarmSpinnerInfo.ALARMWAY_NAME,ase.getAlarmWayName());
			contentValues.put(AlarmSpinnerInfo.NATION_NAME, ase.getNationName());
			contentValues.put(AlarmSpinnerInfo.APPROVALPEOPLEID, ase.getApprovalPeopleID());
			contentValues.put(AlarmSpinnerInfo.LEADER, ase.getLeader());
			contentValues.put(AlarmSpinnerInfo.PARTS_CULTURE, ase.getParts_culture());
			contentValues.put(AlarmSpinnerInfo.PARTS_POST, ase.getParts_post());
			contentValues.put(AlarmSpinnerInfo.TREATMENT_ADVICE, ase.getTreatmentAdvice());
			contentValues.put(AlarmSpinnerInfo.HOST_POLICE_ID, ase.getHostPolice_id());
			contentValues.put(AlarmSpinnerInfo.COPOLICE_ID, ase.getCoPolice_id());
			contentValues.put(AlarmSpinnerInfo.STATE_NAME, ase.getStateName());
			long ok = db.insert(
					AlarmSpinnerInfo.ALARM_SPINNER_INFO_TABLE_NAME, null,
					contentValues);
			if (-1 != ok) {
				flag = true;
			}

			String sql = "SELECT MAX("
					+ AlarmSpinnerInfo.ID + ") FROM "
					+ AlarmSpinnerInfo.ALARM_SPINNER_INFO_TABLE_NAME;
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				plantId = cursor.getInt(0);
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
		return flag;
	}
	
	/**
	 * 根据ID修改数据库
	 * clause字段名
	 * args修改后的值
	 * 
	 */
	public void updataTable(int id,String clause,String args){
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		try{
			String upSql="update "+AlarmSpinnerInfo.ALARM_SPINNER_INFO_TABLE_NAME+" set "
					+clause+"='"+args+"'  where id= '"+id+"'";
			db.execSQL(upSql);
			db.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}
	}
	
	
	/**
	 * 删表
	 */
	public boolean delTable(String plantId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		try {
			int res = db.delete(
					AlarmSpinnerInfo.ALARM_SPINNER_INFO_TABLE_NAME,
					AlarmSpinnerInfo.ID + "=?",
					new String[] { plantId });
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
	public ArrayList<AlarmSpinnerEntity> getAllInfos() {
		ArrayList<AlarmSpinnerEntity> ases = new ArrayList<AlarmSpinnerEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {

			String sql = "select * from "
					+ AlarmSpinnerInfo.ALARM_SPINNER_INFO_TABLE_NAME;
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				AlarmSpinnerEntity ase = new AlarmSpinnerEntity();

				// 自增长id
				ase.setId(cursor.getInt(cursor
						.getColumnIndex(AlarmSpinnerInfo.ID)));
				// 报警方式
				ase.setAlarmWayId(cursor.getInt(cursor
						.getColumnIndex(AlarmSpinnerInfo.ALARMWAY_ID)));
				ase.setAlarmWayName(cursor.getString(cursor
						.getColumnIndex(AlarmSpinnerInfo.ALARMWAY_NAME)));
				// 民族
				ase.setNationId(cursor.getInt(cursor
						.getColumnIndex(AlarmSpinnerInfo.NATION_ID)));
				ase.setNationName(cursor.getString(cursor
						.getColumnIndex(AlarmSpinnerInfo.NATION_NAME)));
				//审批领导
				ase.setApprovalPeopleID(cursor.getInt(cursor.
						getColumnIndex(AlarmSpinnerInfo.APPROVALPEOPLEID)));
				//审批领导
				ase.setLeader(cursor.getString(cursor.
						getColumnIndex(AlarmSpinnerInfo.LEADER)));
				//文化程度
				ase.setParts_culture_id(cursor.getInt(cursor.
						getColumnIndex(AlarmSpinnerInfo.PARTS_CULTURE_ID)));
				ase.setParts_culture(cursor.getString(cursor.
						getColumnIndex(AlarmSpinnerInfo.PARTS_CULTURE)));
				//职务
				ase.setParts_post_id(cursor.getInt(cursor.
						getColumnIndex(AlarmSpinnerInfo.PARTS_POST_ID)));
				ase.setParts_post(cursor.getString(cursor.
						getColumnIndex(AlarmSpinnerInfo.PARTS_POST)));
				//处理意见
				ase.setTreatmentAdviceID(cursor.getInt(cursor.
						getColumnIndex(AlarmSpinnerInfo.TREATMENT_ADVICE_ID)));
				ase.setTreatmentAdvice(cursor.getString(cursor.
						getColumnIndex(AlarmSpinnerInfo.TREATMENT_ADVICE)));
				//主办民警
				ase.setHostPolice_id(cursor.getInt(cursor.
						getColumnIndex(AlarmSpinnerInfo.HOST_POLICE_ID)));
				//协办民警
				ase.setCoPolice_id(cursor.getInt(cursor.
						getColumnIndex(AlarmSpinnerInfo.COPOLICE_ID)));
				//审批状态
				ase.setState_id(cursor.getInt(cursor.
						getColumnIndex(AlarmSpinnerInfo.STATE_ID)));
				ase.setStateName(cursor.getString(cursor.
						getColumnIndex(AlarmSpinnerInfo.STATE_NAME)));
				ases.add(ase);
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
		return ases;
	}
	/*
	 * 获取数据库某字段的数据
	 */
	public ArrayList<String> getClumn(String column){
		ArrayList<String> list=new ArrayList<String>();
		String sql="select "+column+" from "
				+ AlarmSpinnerInfo.ALARM_SPINNER_INFO_TABLE_NAME;
		System.out.println(sql);
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			cursor=db.rawQuery(sql, null);
			if(cursor.moveToNext()){
				while(cursor.moveToNext()){
					list.add(cursor.getString(cursor.getColumnIndex(column)));
				}
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
		return list;
	}
	/*
	 * 获取数据库数量
	 */
	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM "
				+ AlarmSpinnerInfo.ALARM_SPINNER_INFO_TABLE_NAME;

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

	/*
	 * 根据对象获取JSON
	 */
	public static String getJson(AlarmSpinnerEntity ase)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put(AlarmSpinnerInfo.ID, ase.getId());
			//报警方式
			jo.put(AlarmSpinnerInfo.ALARMWAY_NAME, ase.getAlarmWayName());
			// 民族
			jo.put(AlarmSpinnerInfo.NATION_NAME, ase.getNationName());
			//审批领导id
			jo.put(AlarmSpinnerInfo.APPROVALPEOPLEID, ase.getApprovalPeopleID());
			//审批领导
			jo.put(AlarmSpinnerInfo.LEADER, ase.getLeader());
			// 文化程度
			jo.put(AlarmSpinnerInfo.PARTS_CULTURE, ase.getParts_culture());
			//职务
			jo.put(AlarmSpinnerInfo.PARTS_POST, ase.getParts_post());
			// 处理意见
			jo.put(AlarmSpinnerInfo.TREATMENT_ADVICE, ase.getTreatmentAdviceID());
			//主办民警
			jo.put(AlarmSpinnerInfo.HOST_POLICE_ID, ase.getHostPolice_id());
			// 协办民警
			jo.put(AlarmSpinnerInfo.COPOLICE_ID, ase.getCoPolice_id());
			//审批状态
			jo.put(AlarmSpinnerInfo.STATE_NAME, ase.getStateName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jo.toString();
	}
}
