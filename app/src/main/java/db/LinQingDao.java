package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.ForestryConditionsEntent;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.ForestryConditions;

public class LinQingDao {

	private ForestDatabaseHelper dbHelper;

	private Context context;
	private static LinQingDao lqd;

	/**
	 * 
	 */
	public LinQingDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	public static LinQingDao init(Context ct) {
		if (lqd == null) {
			lqd = new LinQingDao(ct);
		}
		return lqd;

	}

	/*
	 * 插入
	 */

	public boolean insert(ForestryConditionsEntent fce) {
		boolean flag = false;

		int plantId = -1;

		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor=null;
		try {
			ContentValues contentValues = new ContentValues();

			contentValues.put(ForestryConditions.FORESTRY_ID, fce.getId());
			contentValues.put(ForestryConditions.CATALOGID,
					ForestryConditions.KEY_FORESTRY_CONDITIONS_ID);
			contentValues.put(ForestryConditions.USERID, fce.getUserID());
			contentValues.put(ForestryConditions.LQWOODLANDAREA,
					fce.getLqwoodlandArea());
			// 村名
			contentValues.put(ForestryConditions.VILLAGE_NAME,
					fce.getVillage_name());

			// 林地蓄积力量
			contentValues.put(ForestryConditions.LQFORESTSTOCKVOLUME,
					fce.getLqforeststockvolume());
			// 野生动物主要活动地区
			contentValues.put(ForestryConditions.LQMAJORAREASOFWILDLIFE,
					fce.getLqmajorareasofwildlife());
			// 涉林案件高发区
			contentValues.put(ForestryConditions.LQFORESTHIGHAREAS,
					fce.getLqforesthighareas());
			// 野生动物市场
			contentValues.put(ForestryConditions.LQWILDANIMALMARKET,
					fce.getLqwildanimalmarket());
			// 生态管护员情况
			contentValues.put(ForestryConditions.LQSITUATIONPROTECTION,
					fce.getLqsituationprotection());
			// 放牧养殖户
			contentValues.put(ForestryConditions.LQGRAZINGFARMERS,
					fce.getLqgrazingfarmers());
			// 半专业数量及人数
			contentValues.put(ForestryConditions.LQSEMINUMBER,
					fce.getLqseminumber());
			// 专业数量及人数
			contentValues.put(ForestryConditions.LQPROFESSIONALNUMBER,
					fce.getLqprofessionalnumber());
			// 森林的种类
			contentValues.put(ForestryConditions.LQFORESTTYPES,
					fce.getLqforesttypes());
			// 树龄组成
			contentValues.put(ForestryConditions.LQAGECOMPOSITION,
					fce.getLqagecomposition());
			// 野生动物种类
			contentValues.put(ForestryConditions.LQWILDANIMALSPECIES,
					fce.getLqwildanimalspecies());
			// 林木种类
			contentValues.put(ForestryConditions.LQTREESPECIES,
					fce.getLqtreespecies());
			// 古树名木
			contentValues.put(ForestryConditions.LQOLDFAMOUSTREES,
					fce.getLqoldfamoustrees());
			// 重点列管人员
			contentValues.put(ForestryConditions.LQKEYCOLUMNMANAGEMENT,
					fce.getLqkeycolumnmanagement());
			// 林业用地的种类
			contentValues.put(ForestryConditions.LQTYPESFORESTL,
					fce.getLqtypesforestl());
			long ok = db.insert(
					ForestryConditions.FORESTRY_CONDITIONS_TABLE_NAME, null,
					contentValues);
			if (-1 != ok) {
				flag = true;
			}

			String sql = "SELECT MAX("
					+ ForestryConditions.FORESTRY_CONDITIONS_ID + ") FROM "
					+ ForestryConditions.FORESTRY_CONDITIONS_TABLE_NAME;
			cursor= db.rawQuery(sql, null);
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
			if(null!=cursor){
				cursor.close();
			}
		}

		if (fce.getAccessory() != null && fce.getAccessory().size() > 0) {
			AttachmentDao dao = new AttachmentDao(context);
			for (int i = 0; i < fce.getAccessory().size(); i++) {
				fce.getAccessory()
						.get(i)
						.setTableId(
								ForestryConditions.KEY_FORESTRY_CONDITIONS_ID);
				fce.getAccessory().get(i).setRowId(plantId);
				flag = dao.insertInfo(fce.getAccessory().get(i));
			}
		}

		return flag;
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
					ForestryConditions.FORESTRY_CONDITIONS_TABLE_NAME,
					ForestryConditions.FORESTRY_CONDITIONS_ID + "=?",
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
	public ArrayList<ForestryConditionsEntent> getAllInfos() {
		ArrayList<ForestryConditionsEntent> fces = new ArrayList<ForestryConditionsEntent>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {

			String sql = "select * from "
					+ ForestryConditions.FORESTRY_CONDITIONS_TABLE_NAME;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				ForestryConditionsEntent fce = new ForestryConditionsEntent();
				/**
				 * public static final String FORESTRY_CONDITIONS_TABLE_NAME=
				 * "forestry_condition";//表名 public static final String
				 * FORESTRY_CONDITIONS_ID = "_id";//自增长ID
				 * */

				// 自增长id
				fce.set_id(cursor.getInt(cursor
						.getColumnIndex(ForestryConditions.FORESTRY_CONDITIONS_ID)));
				// id
				fce.setId(cursor.getInt(cursor
						.getColumnIndex(ForestryConditions.FORESTRY_ID)));
				// 用户ID
				fce.setUserID(cursor.getInt(cursor
						.getColumnIndex(ForestryConditions.USERID)));
				// 功能id
				fce.setCatalogid(cursor.getInt(cursor
						.getColumnIndex(ForestryConditions.CATALOGID)));
				//村名
				fce.setVillage_name(cursor.getString(cursor.getColumnIndex(ForestryConditions.VILLAGE_NAME)));
				// 林业用地的种类
				fce.setLqtypesforestl(cursor.getString(cursor
						.getColumnIndex(ForestryConditions.LQTYPESFORESTL)));
				// 重点列管人员
				fce.setLqkeycolumnmanagement(cursor.getString(cursor
						.getColumnIndex(ForestryConditions.LQKEYCOLUMNMANAGEMENT)));
				// 古树名木
				fce.setLqoldfamoustrees(cursor.getString(cursor
						.getColumnIndex(ForestryConditions.LQOLDFAMOUSTREES)));
				// 林木种类
				fce.setLqtreespecies(cursor.getString(cursor
						.getColumnIndex(ForestryConditions.LQTREESPECIES)));
				// 野生动物种类
				fce.setLqwildanimalspecies(cursor.getString(cursor
						.getColumnIndex(ForestryConditions.LQWILDANIMALSPECIES)));
				// 树龄组成
				fce.setLqagecomposition(cursor.getString(cursor
						.getColumnIndex(ForestryConditions.LQAGECOMPOSITION)));
				// 森林的种类
				fce.setLqforesttypes(cursor.getString(cursor
						.getColumnIndex(ForestryConditions.LQFORESTTYPES)));
				// 专业数量及人数
				fce.setLqprofessionalnumber(cursor.getString(cursor
						.getColumnIndex(ForestryConditions.LQPROFESSIONALNUMBER)));
				// 半专业数量及人数
				fce.setLqseminumber(cursor.getString(cursor
						.getColumnIndex(ForestryConditions.LQSEMINUMBER)));
				// 放牧养殖户
				fce.setLqgrazingfarmers(cursor.getString(cursor
						.getColumnIndex(ForestryConditions.LQGRAZINGFARMERS)));
				// 生态管护员情况
				fce.setLqsituationprotection(cursor.getString(cursor
						.getColumnIndex(ForestryConditions.LQSITUATIONPROTECTION)));
				// 野生动物市场
				fce.setLqwildanimalmarket(cursor.getString(cursor
						.getColumnIndex(ForestryConditions.LQWILDANIMALMARKET)));
				// 涉林案件高发区
				fce.setLqforesthighareas(cursor.getString(cursor
						.getColumnIndex(ForestryConditions.LQFORESTHIGHAREAS)));
				// 野生动物主要活动地区
				fce.setLqmajorareasofwildlife(cursor.getString(cursor
						.getColumnIndex(ForestryConditions.LQMAJORAREASOFWILDLIFE)));
				// 林地蓄积力量
				fce.setLqforeststockvolume(cursor.getString(cursor
						.getColumnIndex(ForestryConditions.LQFORESTSTOCKVOLUME)));
				// 林地面积
				fce.setLqwoodlandArea(cursor.getString(cursor
						.getColumnIndex(ForestryConditions.LQWOODLANDAREA)));

				fces.add(fce);
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

		for (int i = 0; i < fces.size(); i++) {
			int rowid = fces.get(i).get_id();
			AttachmentDao dao = new AttachmentDao(context);
			ArrayList<Accessory> list = dao.getInfosById(
					ForestryConditions.KEY_FORESTRY_CONDITIONS_ID, rowid);
			fces.get(i).setAccessory(list);
		}

		return fces;
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
				+ ForestryConditions.FORESTRY_CONDITIONS_TABLE_NAME;

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
	public static String getJson(ForestryConditionsEntent fce, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("userId", userId + "");
			jo.put("id", fce.getId());
			jo.put(ForestryConditions.CATALOGID,
					ForestryConditions.KEY_FORESTRY_CONDITIONS_ID);
			//村名
			jo.put("villagename", fce.getVillage_name());
			// 林业用地的种类
			jo.put(ForestryConditions.LQTYPESFORESTL, fce.getLqtypesforestl());
			// 重点列管人员
			jo.put(ForestryConditions.LQKEYCOLUMNMANAGEMENT,
					fce.getLqkeycolumnmanagement());
			// 古树名木
			jo.put(ForestryConditions.LQOLDFAMOUSTREES,
					fce.getLqoldfamoustrees());
			// 林木种类
			jo.put(ForestryConditions.LQTREESPECIES, fce.getLqtreespecies());
			// 野生动物种类
			jo.put(ForestryConditions.LQWILDANIMALSPECIES,
					fce.getLqwildanimalspecies());
			// 树龄组成
			jo.put(ForestryConditions.LQAGECOMPOSITION,
					fce.getLqagecomposition());
			// 森林的种类
			jo.put(ForestryConditions.LQFORESTTYPES, fce.getLqforesttypes());
			// 专业数量及人数
			jo.put(ForestryConditions.LQPROFESSIONALNUMBER,
					fce.getLqprofessionalnumber());
			// 半专业数量及人数
			jo.put(ForestryConditions.LQSEMINUMBER, fce.getLqseminumber());
			// 放牧养殖户
			jo.put(ForestryConditions.LQGRAZINGFARMERS,
					fce.getLqgrazingfarmers());
			// 生态管护员情况
			jo.put(ForestryConditions.LQSITUATIONPROTECTION,
					fce.getLqsituationprotection());
			// 野生动物市场
			jo.put(ForestryConditions.LQWILDANIMALMARKET,
					fce.getLqwildanimalmarket());
			// 涉林案件高发区
			jo.put(ForestryConditions.LQFORESTHIGHAREAS,
					fce.getLqforesthighareas());
			// 野生动物主要活动地区
			jo.put(ForestryConditions.LQMAJORAREASOFWILDLIFE,
					fce.getLqmajorareasofwildlife());
			// 林地蓄积力量
			jo.put(ForestryConditions.LQFORESTSTOCKVOLUME,
					fce.getLqforeststockvolume());
			// 林地面积
			jo.put(ForestryConditions.LQWOODLANDAREA, fce.getLqwoodlandArea());

			if (fce.getAccessory() != null && fce.getAccessory().size() > 0) {
				JSONArray ja = new JSONArray();
				for (int i = 0; i < fce.getAccessory().size(); i++) {
					JSONObject json = AttachmentDao.getJson(fce.getAccessory()
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
}
