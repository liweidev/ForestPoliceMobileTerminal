package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.MountConditionsEntent;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.MountConditions;

/**
 * @author 三情--山情
 * 
 */
public class MountConditionDao {

	private ForestDatabaseHelper dbHelper;

	private Context context;
	private static MountConditionDao ppDao;

	/**
	 * 
	 */
	public MountConditionDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	public static MountConditionDao init(Context ct) {
		if (ppDao == null) {
			ppDao = new MountConditionDao(ct);
		}
		return ppDao;

	}

	/*
	 * 插入
	 */

	public boolean insert(MountConditionsEntent mce) {
		boolean flag = false;

		int plantId = -1;

		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
			Cursor cursor=null;
			try {
				ContentValues contentValues = new ContentValues();
				//id
				contentValues.put(MountConditions.ID,mce.getId());
				//表ID
				contentValues.put(MountConditions.TABLE_ID,MountConditions.KEY_MOUNT_CONDITIONS_ID);
				//村名
				contentValues.put(MountConditions.VILLAGE_NAME, mce.getVillage_name());
				//文件类型
				contentValues.put(MountConditions.FILETYPE,mce.getFileType());
				//图片名称
				contentValues.put(MountConditions.THUMBNAIL,mce.getThumbnail());
				//图片类型
				contentValues.put(MountConditions.THUMBNAILTYPE,mce.getThumbnailtype());
				//行政区域总面积(公顷)
				contentValues.put(MountConditions.SQADMINISTRATIVEAREA,mce.getSqadministrativearea());
				//地形地貌
				contentValues.put(MountConditions.SQTOPOGRAPHYGEOMORPHOLOGY,mce.getSqtopographygeomorphology());
				// 海拔
				contentValues.put(MountConditions.SQLTITUDE,mce.getSqltitude());
				// 经纬度
				contentValues.put(MountConditions.SQLATITUDELONGITUDE,mce.getSqlatitudelongitude());
				// 截然地区(四至)
				contentValues.put(MountConditions.SQCOMPLETELY,mce.getSqcompletely());
				// 河流湖泊位置
				contentValues.put(MountConditions.SQRIVERLAKEPOSITION,mce.getSqriverlakeposition());
				// 交通道路分布
				contentValues.put(MountConditions.SQTRAFFICROADDISTRIBUTION,mce.getSqtrafficroaddistribution());
				//地区特产
				contentValues.put(MountConditions.SQREGIONALSPECIALTY,mce.getSqregionalspecialty());
				// 矿产资源分布
				contentValues.put(MountConditions.SQMINERALDISTRIBUTION,mce.getSqMineraldistribution());

				long ok = db.insert(
						MountConditions.KEY_MOUNT_CONDITIONS_NAME, null,
						contentValues);
				if (-1 != ok) {
					flag = true;
				}

				String sql = "SELECT MAX("
						+ MountConditions.MOUNT_ID + ") FROM "
						+ MountConditions.KEY_MOUNT_CONDITIONS_NAME;
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

			if (mce.getAccessory() != null
					&& mce.getAccessory().size() > 0) {
				AttachmentDao dao = new AttachmentDao(context);
				for (int i = 0; i < mce.getAccessory().size(); i++) {
					mce.getAccessory()
							.get(i)
							.setTableId(
									MountConditions.KEY_MOUNT_CONDITIONS_ID);
					mce.getAccessory().get(i).setRowId(plantId);
					flag = dao.insertInfo(mce.getAccessory().get(i));
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
					MountConditions.KEY_MOUNT_CONDITIONS_NAME,
					MountConditions.MOUNT_ID + "=?",
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
	public ArrayList<MountConditionsEntent> getAllInfos() {
		ArrayList<MountConditionsEntent> mces = new ArrayList<MountConditionsEntent>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {

			String sql = "select * from "
					+ MountConditions.KEY_MOUNT_CONDITIONS_NAME;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				MountConditionsEntent mce = new MountConditionsEntent();
				mce.setId(cursor.getInt(cursor.getColumnIndex(MountConditions.ID)));
				//表ID
				mce.setCatalogid(cursor.getInt(cursor.getColumnIndex(MountConditions.TABLE_ID)));
				//村名
				mce.setVillage_name(cursor.getString(cursor.getColumnIndex(MountConditions.VILLAGE_NAME)));
				//文件类型
				mce.setFileType(cursor.getInt(cursor.getColumnIndex(MountConditions.FILETYPE)));
				//图片名称
				mce.setThumbnail(cursor.getString(cursor.getColumnIndex(MountConditions.THUMBNAIL)));
				//图片类型
				mce.setThumbnailtype(cursor.getString(cursor.getColumnIndex(MountConditions.THUMBNAILTYPE)));
				//行政区域总面积(公顷)
				mce.setSqadministrativearea(cursor.getString(cursor.getColumnIndex(MountConditions.SQADMINISTRATIVEAREA)));
				//地形地貌
				mce.setSqtopographygeomorphology(cursor.getString(cursor.getColumnIndex(MountConditions.SQTOPOGRAPHYGEOMORPHOLOGY)));
				// 海拔
				mce.setSqltitude(cursor.getString(cursor.getColumnIndex(MountConditions.SQLTITUDE)));
				// 经纬度
				mce.setSqlatitudelongitude(cursor.getString(cursor.getColumnIndex(MountConditions.SQLATITUDELONGITUDE)));
				// 截然地区(四至)
				mce.setSqcompletely(cursor.getString(cursor.getColumnIndex(MountConditions.SQCOMPLETELY)));
				// 河流湖泊位置
				mce.setSqriverlakeposition(cursor.getString(cursor.getColumnIndex(MountConditions.SQRIVERLAKEPOSITION)));
				// 交通道路分布
				mce.setSqtrafficroaddistribution(cursor.getString(cursor.getColumnIndex(MountConditions.SQTRAFFICROADDISTRIBUTION)));
				//地区特产
				mce.setSqregionalspecialty(cursor.getString(cursor.getColumnIndex(MountConditions.SQREGIONALSPECIALTY)));
				// 矿产资源分布
				mce.setSqMineraldistribution(cursor.getString(cursor.getColumnIndex(MountConditions.SQMINERALDISTRIBUTION)));
				//自增长ID
				mce.setMount_id(cursor.getInt(cursor.getColumnIndex(MountConditions.MOUNT_ID)));
				mces.add(mce);
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

		for (int i = 0; i < mces.size(); i++) {
			int rowid = mces.get(i).getMount_id();
			AttachmentDao dao = new AttachmentDao(context);
			ArrayList<Accessory> list = dao.getInfosById(
					MountConditions.KEY_MOUNT_CONDITIONS_ID, rowid);
			mces.get(i).setAccessory(list);
		}

		return mces;
	}

	/*
	 * 获取数据库数量
	 */
	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
//		db.beginTransaction();
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM "
				+ MountConditions.KEY_MOUNT_CONDITIONS_NAME;

		try {
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				count = cursor.getInt(0);
			}
//			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
//			db.endTransaction();
			if(null!=cursor){
				cursor.close();
			}
		}

		return count;
	}

	/*
	 * 根据对象获取JSON
	 */
	public static String getJson(MountConditionsEntent mce, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("userId", userId);
			jo.put("catalogid", MountConditions.KEY_MOUNT_CONDITIONS_ID);
			jo.put("id", mce.getId()+"");
			jo.put("depID", MountConditions.DEPID);
			jo.put("", mce.getVillage_name());
			// 文件类型
			jo.put("fileType", mce.getFileType());
			jo.put("villagename", mce.getVillage_name());
			// 图片名称
			jo.put("thumbnail", mce.getThumbnail());
			// 图片类型
			jo.put("thumbnailtype", mce.getThumbnailtype());
			// 行政区域总面积(公顷)
			jo.put("sqadministrativearea", mce.getSqadministrativearea());
			// 地形地貌
			jo.put("sqtopographygeomorphology", mce.getSqtopographygeomorphology());
			// 海拔
			jo.put("sqltitude", mce.getSqltitude());
			// 经纬度
			jo.put("sqlatitudelongitude", mce.getSqlatitudelongitude());
			// 截然地区(四至)
			jo.put("sqcompletely", mce.getSqcompletely());
			// 河流湖泊位置
			jo.put("sqriverlakeposition", mce.getSqriverlakeposition());
			// 交通道路分布
			jo.put("sqtrafficroaddistribution", mce.getSqtrafficroaddistribution());
			// 地区特产
			jo.put("sqregionalspecialty", mce.getSqregionalspecialty());
			// 矿产资源分布
			jo.put("sqMineraldistribution", mce.getSqMineraldistribution());
			
			if(mce.getAccessory() != null && mce.getAccessory().size() > 0) {
					JSONArray ja = new JSONArray();
					for (int i = 0; i < mce.getAccessory().size(); i++) {
						JSONObject json = AttachmentDao.getJson(mce.getAccessory()
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
