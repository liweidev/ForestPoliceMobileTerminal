package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.SheQingEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.SheConditions;

/**
 * @author 三情--社情
 * 
 */
public class SheConditionDao {

	private ForestDatabaseHelper dbHelper;

	private Context context;
	private static SheConditionDao ppDao;

	/**
	 * 
	 */
	public SheConditionDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	public static SheConditionDao init(Context ct) {
		if (ppDao == null) {
			ppDao = new SheConditionDao(ct);
		}
		return ppDao;

	}

	/*
	 * 插入
	 */

	public boolean insert(SheQingEntity sqe) {
		boolean flag = false;

		int plantId = -1;

		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
			Cursor cursor = null;
			try {
				ContentValues contentValues = new ContentValues();
				
				contentValues.put(SheConditions.ID,sqe.getId());
				//表ID
				contentValues.put(SheConditions.TABLE_ID,SheConditions.KEY_SHE_CONDITIONS_ID);
				//村名
				contentValues.put(SheConditions.VILLAGE_NAME,sqe.getVillage_name());
				// 文件类型
				contentValues.put(SheConditions.FILETYPE,sqe.getFileType());
				// 图片名称
				contentValues.put(SheConditions.THUMBNAIL,sqe.getThumbnail());
				// 图片类型
				contentValues.put(SheConditions.THUMBNAILTYPE,sqe.getThumbnailtype());
				// 土地承包情况
				contentValues.put(SheConditions.SHQLANDCONTRACT,sqe.getShqlandcontract());
				// 村领导干部及联系方式
				contentValues.put(SheConditions.SHQVILLAGECONTACT,sqe.getShqvillagecontact());
				// 呆、傻精神病人情况
				contentValues.put(SheConditions.SHQSTAYSILLYPATIENT,sqe.getShqstaysillypatient());
				// 主要餐饮业情况
				contentValues.put(SheConditions.SHQMAJORINDUSTRY,sqe.getShqmajorindustry());
				// 清明节扫墓时间及风俗
				contentValues.put(SheConditions.SHQTOMBCUSTOMS,sqe.getShqtombcustoms());
				// 交通分布情况
				contentValues.put(SheConditions.SHQTRAFFICDISTRIBUTION,sqe.getShqtrafficdistribution());
				// 暂住人口数
				contentValues.put(SheConditions.SHQTRANSIENTPOPULATION,sqe.getShqtransientpopulation());
				// 居民人口数
				contentValues.put(SheConditions.SHQRESIDENTPOPULATION,sqe.getShqresidentpopulation());
				// 行政村常住人口数
				contentValues.put(SheConditions.SHQRESIDENTVILLAGE,sqe.getShqresidentvillage());
				// 文物古迹
				contentValues.put(SheConditions.SHQCULTURALSITES,sqe.getShqculturalsites());
				// 主要产业及经济来源
				contentValues.put(SheConditions.SHQMAJORSOURCES,sqe.getShqmajorsources());
				// 特色物产
				contentValues.put(SheConditions.SHQCHARACTERISTICPROPERTY,sqe.getShqcharacteristicproperty());
				
				long ok = db.insert(
						SheConditions.KEY_SHE_CONDITONS_NAME, null,
						contentValues);
				if (-1 != ok) {
					flag = true;
				}

				String sql = "SELECT MAX("
						+ SheConditions.SHE_ID + ") FROM "
						+ SheConditions.KEY_SHE_CONDITONS_NAME;
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
				if(null!=cursor){
					cursor.close();
				}
			}

			if (sqe.getAccessory() != null
					&& sqe.getAccessory().size() > 0) {
				AttachmentDao dao = new AttachmentDao(context);
				for (int i = 0; i < sqe.getAccessory().size(); i++) {
					sqe.getAccessory()
							.get(i)
							.setTableId(
									SheConditions.KEY_SHE_CONDITIONS_ID);
					sqe.getAccessory().get(i).setRowId(plantId);
					flag = dao.insertInfo(sqe.getAccessory().get(i));
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
					SheConditions.KEY_SHE_CONDITONS_NAME,
					SheConditions.SHE_ID + "=?",
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
	public ArrayList<SheQingEntity> getAllInfos() {
		ArrayList<SheQingEntity> sqes = new ArrayList<SheQingEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {

			String sql = "select * from "
					+ SheConditions.KEY_SHE_CONDITONS_NAME;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				SheQingEntity sqe = new SheQingEntity();
				
				sqe.setId(cursor.getInt(cursor.getColumnIndex(SheConditions.ID)));
				//表ID
				sqe.setCatalogid(cursor.getInt(cursor.getColumnIndex(SheConditions.TABLE_ID)));
				sqe.setVillage_name(cursor.getString(cursor.getColumnIndex(SheConditions.VILLAGE_NAME)));
				// 文件类型
				sqe.setFileType(cursor.getInt(cursor.getColumnIndex(SheConditions.FILETYPE)));
				// 图片名称
				sqe.setThumbnail(cursor.getString(cursor.getColumnIndex(SheConditions.THUMBNAIL)));
				// 图片类型
				sqe.setThumbnailtype(cursor.getString(cursor.getColumnIndex(SheConditions.THUMBNAILTYPE)));
				// 土地承包情况
				sqe.setShqlandcontract(cursor.getString(cursor.getColumnIndex(SheConditions.SHQLANDCONTRACT)));
				// 村领导干部及联系方式
				sqe.setShqvillagecontact(cursor.getString(cursor.getColumnIndex(SheConditions.SHQVILLAGECONTACT)));
				// 呆、傻精神病人情况
				sqe.setShqstaysillypatient(cursor.getString(cursor.getColumnIndex(SheConditions.SHQSTAYSILLYPATIENT)));
				// 主要餐饮业情况
				sqe.setShqmajorindustry(cursor.getString(cursor.getColumnIndex(SheConditions.SHQMAJORINDUSTRY)));
				// 清明节扫墓时间及风俗
				sqe.setShqtombcustoms(cursor.getString(cursor.getColumnIndex(SheConditions.SHQTOMBCUSTOMS)));
				// 交通分布情况
				sqe.setShqtrafficdistribution(cursor.getString(cursor.getColumnIndex(SheConditions.SHQTRAFFICDISTRIBUTION)));
				// 暂住人口数
				sqe.setShqtransientpopulation(cursor.getString(cursor.getColumnIndex(SheConditions.SHQTRANSIENTPOPULATION)));
				// 居民人口数
				sqe.setShqresidentpopulation(cursor.getString(cursor.getColumnIndex(SheConditions.SHQRESIDENTPOPULATION)));
				// 行政村常住人口数
				sqe.setShqresidentvillage(cursor.getString(cursor.getColumnIndex(SheConditions.SHQRESIDENTVILLAGE)));
				// 文物古迹
				sqe.setShqculturalsites(cursor.getString(cursor.getColumnIndex(SheConditions.SHQCULTURALSITES)));
				// 主要产业及经济来源
				sqe.setShqmajorsources(cursor.getString(cursor.getColumnIndex(SheConditions.SHQMAJORSOURCES)));
				// 特色物产
				sqe.setShqcharacteristicproperty(cursor.getString(cursor.getColumnIndex(SheConditions.SHQCHARACTERISTICPROPERTY)));
				sqe.setShe_id(cursor.getInt(cursor.getColumnIndex(SheConditions.SHE_ID)));
				sqes.add(sqe);
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

		for (int i = 0; i < sqes.size(); i++) {
			int rowid = sqes.get(i).getShe_id();
			AttachmentDao dao = new AttachmentDao(context);
			ArrayList<Accessory> list = dao.getInfosById(
					SheConditions.KEY_SHE_CONDITIONS_ID, rowid);
			sqes.get(i).setAccessory(list);
		}

		return sqes;
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
				+ SheConditions.KEY_SHE_CONDITONS_NAME;

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
			if(null!=cursor){
				cursor.close();
			}
		}

		return count;
	}

	/*
	 * 根据对象获取JSON
	 */
	public static String getJson(SheQingEntity sqe, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("userId", userId);
			jo.put("id", sqe.getId()+"");
			jo.put("catalogid", SheConditions.KEY_SHE_CONDITIONS_ID);
			jo.put("depID", SheConditions.DEPID);
			// 文件类型
			jo.put(SheConditions.FILETYPE, sqe.getFileType());
			//村名
			jo.put("villagename", sqe.getVillage_name());
			// 图片名称
			jo.put(SheConditions.THUMBNAIL, sqe.getThumbnail());
			// 图片类型
			jo.put(SheConditions.THUMBNAILTYPE, sqe.getThumbnailtype());
			// 土地承包情况
			jo.put(SheConditions.SHQLANDCONTRACT, sqe.getShqlandcontract());
			// 村领导干部及联系方式
			jo.put(SheConditions.SHQVILLAGECONTACT, sqe.getShqvillagecontact());
			// 呆、傻精神病人情况
			jo.put(SheConditions.SHQSTAYSILLYPATIENT, sqe.getShqstaysillypatient());
			// 主要餐饮业情况
			jo.put(SheConditions.SHQMAJORINDUSTRY, sqe.getShqmajorindustry());
			// 清明节扫墓时间及风俗
			jo.put(SheConditions.SHQTOMBCUSTOMS, sqe.getShqtombcustoms());
			// 交通分布情况
			jo.put(SheConditions.SHQTRAFFICDISTRIBUTION, sqe.getShqtrafficdistribution());
			// 暂住人口数
			jo.put(SheConditions.SHQTRANSIENTPOPULATION, sqe.getShqtransientpopulation());
			// 居民人口数
			jo.put(SheConditions.SHQRESIDENTPOPULATION, sqe.getShqresidentpopulation());
			// 行政村常住人口数
			jo.put(SheConditions.SHQRESIDENTVILLAGE, sqe.getShqresidentvillage());
			// 文物古迹
			jo.put(SheConditions.SHQCULTURALSITES, sqe.getShqculturalsites());
			// 主要产业及经济来源
			jo.put(SheConditions.SHQMAJORSOURCES, sqe.getShqmajorsources());
			// 特色物产
			jo.put(SheConditions.SHQCHARACTERISTICPROPERTY, sqe.getShqcharacteristicproperty());
			
			if(sqe.getAccessory() != null && sqe.getAccessory().size() > 0) {
					JSONArray ja = new JSONArray();
					for (int i = 0; i < sqe.getAccessory().size(); i++) {
						JSONObject json = AttachmentDao.getJson(sqe.getAccessory()
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
