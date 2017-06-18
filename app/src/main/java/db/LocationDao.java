package db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;

import db.Database.Location;

/**
 * @author liupeng 定位
 */
public class LocationDao {

	private ForestDatabaseHelper dbHelper;

	/**
	 * 
	 */
	public LocationDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
	}

	/*
	 * 插入
	 */
	public int insertInfo(Loaction l) {

		int success = 0;
		boolean flag = false;
		String sql = "SELECT  MAX(" + Location.LOCATION_ID + ") from "
				+ Location.LOCATION_TABLE_NAME;
		Cursor cursor = null;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//

		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(Location.ELEVATION, l.getELEVATION());
			contentValues.put(Location.LATITUDE, l.getLATITUDE());
			contentValues.put(Location.LONGITUDE, l.getLONGITUDE());
			contentValues.put(Location.TIME, l.getTIME());
			long ok = db.insert(Location.LOCATION_TABLE_NAME, null,
					contentValues);
			Log.i("liupeng", "insert Location    :   OK   " + ok);
			if (0 < ok) {
				flag = true;
			} else {
				flag = false;
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			db.endTransaction();
			// db.close();
		}

		if (flag) {
			SQLiteDatabase db2 = dbHelper.getInstence();
			db2.beginTransaction();
			try {
				cursor = db2.rawQuery(sql, null);
				cursor.moveToFirst();
				success = cursor.getInt(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				db2.endTransaction();
				db2.close();
				cursor.close();
			}
		}
		return success;
	}

	/**
	 * 删表
	 */
	public boolean delTable(String locationId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db.delete(Location.LOCATION_TABLE_NAME,
					Location.LOCATION_ID + "=?", new String[] { locationId });
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

	public Loaction getLocation(int id) {
		Loaction l = null;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;

		String sql = "select * from " + Location.LOCATION_TABLE_NAME
				+ " where " + Location.LOCATION_ID + " = " + id;
		try {
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				l = new Loaction();
				l.setLocation_id(cursor.getInt(cursor
						.getColumnIndex(Location.LOCATION_ID)));
				l.setELEVATION(cursor.getDouble(cursor
						.getColumnIndex(Location.ELEVATION)));
				l.setLATITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LATITUDE)));
				l.setLONGITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LONGITUDE)));
				l.setTIME(cursor.getString(cursor.getColumnIndex(Location.TIME)));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			l = null;
		} finally {
			db.endTransaction();
			if (cursor != null) {
				cursor.close();
			}
		}
		return l;
	}

}
