package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.LeaderEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.OptionEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.PoliceHeadEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.WeatherEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.Option;

/**
 * 选择项
 * 
 * @author Administrator
 * 
 */
public class OptionDao {

	private ForestDatabaseHelper dbHelper;

	/**
	 * 
	 */
	public OptionDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
	}

	/*
	 * 插入
	 */
	public long insert(JSONObject object) throws JSONException {
		long ok = -1;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {

			ContentValues cv = new ContentValues();
			cv.put(Option.OPTION_SERVER_ID, object.getInt("ID"));
			cv.put(Option.CREATE_TIME, object.getString("CreateTime"));
			cv.put(Option.CTRL_VALUE, object.getInt("CtrlValue"));
			cv.put(Option.DICT_NAME, object.getString("DictName"));
			cv.put(Option.DICT_ORDER, object.getInt("DictOrder"));
			cv.put(Option.DICT_VALUE, object.getInt("DictValue"));
			cv.put(Option.FORM_MAIN_ID, object.getInt("FormMainID"));
			cv.put(Option.RELATION_DICT_LIST,
					object.getString("RelationDictList"));
			cv.put(Option.REMARK, object.getString("Remark"));

			ok = db.insert(Option.OPTION_TABLE_NAME, null, cv);
			// System.out.println("insert is complete..." + ok);

			db.setTransactionSuccessful();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.endTransaction();
//			this.dbHelper.close(db);
		}
		return ok;
	}

	/**
	 * 删表
	 */
	public boolean delTable() {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db.delete(Option.OPTION_TABLE_NAME, null, null);
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
	public ArrayList<PoliceHeadEntity> query(int fromMainID, int ctrlValue){
		String sql = "SELECT * FROM " + Option.OPTION_TABLE_NAME + " where "
				+ Option.FORM_MAIN_ID + "=" + fromMainID+" and "+ Option.CTRL_VALUE+"="+ctrlValue;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		ArrayList<PoliceHeadEntity> options = null;
		try{
			cursor=db.rawQuery(sql, null);
			if (cursor == null && cursor.getCount() == 0) {
				return options;
			}
			options = new ArrayList<PoliceHeadEntity>();
			while (cursor.moveToNext()) {
				PoliceHeadEntity option = new PoliceHeadEntity();
				option.setPeoplePoliceID(cursor.getInt(cursor
						.getColumnIndex(Option.DICT_VALUE)));
				option.setPeoplePoliceName(cursor.getString(cursor
						.getColumnIndex(Option.DICT_NAME)));
				options.add(option);
			}
			db.setTransactionSuccessful();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.endTransaction();
			if (cursor != null) {
				cursor.close();
			}
		}
		return options;
	}
	public ArrayList<WeatherEntity> queryWeather(int fromMainID, int ctrlValue){
		String sql = "SELECT * FROM " + Option.OPTION_TABLE_NAME + " where "
				+ Option.FORM_MAIN_ID + "=" + fromMainID+" and "+ Option.CTRL_VALUE+"="+ctrlValue;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		ArrayList<WeatherEntity> options = null;
		try{
			cursor=db.rawQuery(sql, null);
			if (cursor == null && cursor.getCount() == 0) {
				return options;
			}
			options = new ArrayList<WeatherEntity>();
			while (cursor.moveToNext()) {
				WeatherEntity tkl=new WeatherEntity();
				tkl.setWeather(cursor.getString(cursor.getColumnIndex("dict_name")));
				tkl.setWeatherId(cursor.getInt(cursor.getColumnIndex("dict_value")));
				options.add(tkl);
			}
			db.setTransactionSuccessful();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.endTransaction();
			if (cursor != null) {
				cursor.close();
			}
		}
		return options;
	}
	public ArrayList<LeaderEntity> queryLeader(int fromMainID, int ctrlValue){
		String sql = "SELECT * FROM " + Option.OPTION_TABLE_NAME + " where "
				+ Option.FORM_MAIN_ID + "=" + fromMainID+" and "+ Option.CTRL_VALUE+"="+ctrlValue;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		ArrayList<LeaderEntity> options = null;
		try{
			cursor=db.rawQuery(sql, null);
			if (cursor == null && cursor.getCount() == 0) {
				return options;
			}
			options = new ArrayList<LeaderEntity>();
			while (cursor.moveToNext()) {
				LeaderEntity tkl=new LeaderEntity();
				tkl.setLeader(cursor.getString(cursor.getColumnIndex("dict_name")));
				tkl.setLeaderId(cursor.getInt(cursor.getColumnIndex("dict_value")));
				options.add(tkl);
			}
			db.setTransactionSuccessful();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.endTransaction();
			if (cursor != null) {
				cursor.close();
			}
		}
		return options;
	}
	public ArrayList<OptionEntity> getOptionByFormMainId(int tableId) {
		String sql = "SELECT * FROM " + Option.OPTION_TABLE_NAME + " where "
				+ Option.FORM_MAIN_ID + "=" + tableId;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		ArrayList<OptionEntity> options = null;
		try {
			cursor = db.rawQuery(sql, null);
			if (cursor == null && cursor.getCount() == 0) {
				return options;
			}
			options = new ArrayList<OptionEntity>();
			while (cursor.moveToNext()) {
				OptionEntity option = new OptionEntity();
				option.setCreateTime(cursor.getString(cursor
						.getColumnIndex(Option.CREATE_TIME)));
				option.setCtrlValue(cursor.getInt(cursor
						.getColumnIndex(Option.CTRL_VALUE)));
				option.setDictName(cursor.getString(cursor
						.getColumnIndex(Option.DICT_NAME)));
				option.setDictOrder(cursor.getInt(cursor
						.getColumnIndex(Option.DICT_ORDER)));
				option.setDictValue(cursor.getInt(cursor
						.getColumnIndex(Option.DICT_VALUE)));
				option.setFormMainId(cursor.getInt(cursor
						.getColumnIndex(Option.FORM_MAIN_ID)));
				option.setRelationDictList(cursor.getString(cursor
						.getColumnIndex(Option.RELATION_DICT_LIST)));
				option.setRemark(cursor.getString(cursor
						.getColumnIndex(Option.REMARK)));

				options.add(option);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}

		return options;
	}

	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM " + Option.OPTION_TABLE_NAME;

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
