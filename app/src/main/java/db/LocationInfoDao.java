package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.LocationInfoEntity;

import java.util.ArrayList;
import java.util.List;

import db.Database.LocationInfo;

/**
 * 巡逻轨迹
 * 
 * @author liupeng
 */
public class LocationInfoDao {

	private ForestDatabaseHelper dbHelper;

	private Context context;

	public LocationInfoDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}
	
	/**
	 * 插入单条数据
	 * @param info
	 * @return
	 */
	public boolean insertLocationInfo(LocationInfoEntity info){
		
		boolean flag = false;
		
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		
		ContentValues contentValues = new ContentValues();
		contentValues.put(LocationInfo.USER_ID, info.getUserId());
		contentValues.put(LocationInfo.LONGITUDE, info.getLongitude());
		contentValues.put(LocationInfo.LATITUDE, info.getLatitude());
		contentValues.put(LocationInfo.ELEVATION, info.getElevation());
		contentValues.put(LocationInfo.TIME, info.getTime());
		contentValues.put(LocationInfo.LOCATIONTYPE, info.getLocationType());
		
		long ok = db.insert(LocationInfo.LOCATION_INFO_TABLE_NAME,	null, contentValues);
		
		if (-1 != ok) {
			flag = true;
		}
		
		db.setTransactionSuccessful();
		db.endTransaction();
		
		return flag;
		
	}
	
	/**
	 * 删表
	 */
	public boolean delTable() {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db.delete(LocationInfo.LOCATION_INFO_TABLE_NAME, null, null);
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
	 * 获取所有巡逻轨迹信息
	 * @return
	 */
	public List<LocationInfoEntity> getAllInfo(){
		
		List<LocationInfoEntity> infoList = null;
		
		SQLiteDatabase db = dbHelper.getInstence();
		Cursor cursor = null;
		
		String sql = "SELECT * FROM " + LocationInfo.LOCATION_INFO_TABLE_NAME;
		
		cursor = db.rawQuery(sql, null);
		infoList = new ArrayList<LocationInfoEntity>();
		while (cursor.moveToNext()) {
			LocationInfoEntity info = new LocationInfoEntity();
			info.setLongitude(cursor.getDouble(cursor.getColumnIndex(LocationInfo.LONGITUDE)));
			info.setUserId(cursor.getInt(cursor.getColumnIndex(LocationInfo.USER_ID)));
			info.setLatitude(cursor.getDouble(cursor.getColumnIndex(LocationInfo.LATITUDE)));
			info.setElevation(cursor.getDouble(cursor.getColumnIndex(LocationInfo.ELEVATION)));
			info.setTime(cursor.getString(cursor.getColumnIndex(LocationInfo.TIME)));
			info.setInfoId(cursor.getInt(cursor.getColumnIndex(LocationInfo.INFO_ID)));
			info.setLocationType(cursor.getInt(cursor.getColumnIndex(LocationInfo.LOCATIONTYPE)));
			infoList.add(info);
		}
		cursor.close();
		return infoList;
	}
	
	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM "
				+ LocationInfo.LOCATION_INFO_TABLE_NAME;
		try {
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				count = cursor.getInt(0);
			}
			cursor.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (cursor != null) {
				cursor.close();
			}
		} 
		return count;
	}
	
}
