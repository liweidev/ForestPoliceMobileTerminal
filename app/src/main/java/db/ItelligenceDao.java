package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.ItelligenceEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.Itelligence;

/**
 * 情报工作
 * 
 * @author zhengtiantian
 * 
 */
public class ItelligenceDao {

	private ForestDatabaseHelper dbHelper;
	private Context context;
	private static ItelligenceDao llDao;

	public ItelligenceDao(Context context) {
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	public static ItelligenceDao init(Context ct) {
		if (llDao == null) {
			llDao = new ItelligenceDao(ct);
		}
		return llDao;

	}

	/**
	 * 插入数据
	 * 
	 * @param ite
	 * @return
	 */
	public boolean insertInfo(ItelligenceEntity ite) {
		boolean flag = false;
		int iteId = -1;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			ContentValues cv = new ContentValues();
			cv.put(Itelligence.ITELLIGENCE_ID, ite.getServerId());
			cv.put(Itelligence.ACQUISITIONTIME, ite.getAcquisitionTime());
			cv.put(Itelligence.ACQUISITIONPOLICE, ite.getAcquisitionPolice());
			cv.put(Itelligence.INFORMATIONSOURCE, ite.getInformationSource());
			cv.put(Itelligence.INFORMATIONTYPE, ite.getInformationType());
			cv.put(Itelligence.INFORMATIONABSTRACT,
					ite.getInformationAbstract());
			cv.put(Itelligence.REPORTEDUNIT, ite.getReportedUnit());
			cv.put(Itelligence.REPORTEDTIME, ite.getReportedTime());
			cv.put(Itelligence.FEEDBACK, ite.getFeedback());

			long ok = db.insert(Itelligence.ITELLIGENCE_TABLE_NAME, null, cv);
			if (-1 != ok) {
				flag = true;
			}
			String sql = "SELECT MAX(" + Itelligence.ITELLIGENCE_TABLE_ID
					+ ") FROM " + Itelligence.ITELLIGENCE_TABLE_NAME;
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				iteId = cursor.getInt(0);
			}
			
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			flag = false;
		} finally {
			db.endTransaction();
			if (cursor != null) {
				cursor.close();
			}
		}
		if (ite.getItAttachment() != null && ite.getItAttachment().size() > 0) {
			AttachmentDao aDao = new AttachmentDao(context);
			for (int i = 0; i < ite.getItAttachment().size(); i++) {
				ite.getItAttachment().get(i).setTableId(Itelligence.CATALOG_ID);
				ite.getItAttachment().get(i).setRowId(iteId);
				flag = aDao.insertInfo(ite.getItAttachment().get(i));
			}
		}
		return flag;
	}

	/**
	 * 删除表
	 * 
	 * @param instantId
	 * @return
	 */
	public boolean delTable(String instantId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		try {
			int res = db.delete(Itelligence.ITELLIGENCE_TABLE_NAME,
					Itelligence.ITELLIGENCE_TABLE_ID + "=?",
					new String[] { instantId });
			if (res > 0) {
				isOk = true;
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		return isOk;
	}

	/**
	 * 获取本地所有
	 * 
	 * @return
	 */
	public ArrayList<ItelligenceEntity> getAllInfos() {
		ArrayList<ItelligenceEntity> infos = new ArrayList<ItelligenceEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			String sql = "select * from " + Itelligence.ITELLIGENCE_TABLE_NAME;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				ItelligenceEntity tel = new ItelligenceEntity();
				tel.setItelligenceID(cursor.getInt(cursor
						.getColumnIndex(Itelligence.ITELLIGENCE_TABLE_ID)));
				tel.setServerId(cursor.getInt(cursor
						.getColumnIndex(Itelligence.ITELLIGENCE_ID)));
				tel.setAcquisitionTime(cursor.getString(cursor
						.getColumnIndex(Itelligence.ACQUISITIONTIME)));
				tel.setAcquisitionPolice(cursor.getString(cursor
						.getColumnIndex(Itelligence.ACQUISITIONPOLICE)));
				tel.setInformationSource(cursor.getString(cursor
						.getColumnIndex(Itelligence.INFORMATIONSOURCE)));
				tel.setInformationType(cursor.getString(cursor
						.getColumnIndex(Itelligence.INFORMATIONTYPE)));
				tel.setInformationAbstract(cursor.getString(cursor
						.getColumnIndex(Itelligence.INFORMATIONABSTRACT)));
				tel.setReportedTime(cursor.getString(cursor
						.getColumnIndex(Itelligence.REPORTEDTIME)));
				tel.setReportedUnit(cursor.getString(cursor
						.getColumnIndex(Itelligence.REPORTEDUNIT)));
				tel.setFeedback(cursor.getString(cursor
						.getColumnIndex(Itelligence.FEEDBACK)));

				infos.add(tel);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.endTransaction();
			if (cursor != null) {
				cursor.close();
			}
		}
		AttachmentDao dao = null;
		for (int i = 0; i < infos.size(); i++) {
			int rowId = infos.get(i).getItelligenceID();
			dao = new AttachmentDao(context);
			ArrayList<Accessory> list = dao.getInfosById(
					Itelligence.CATALOG_ID, rowId);
			infos.get(i).setItAttachment(list);
		}
		return infos;
	}

	/**
	 * 获取数量
	 * 
	 * @return
	 */
	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT (*) FROM "
				+ Itelligence.ITELLIGENCE_TABLE_NAME;
		try {
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				count = cursor.getInt(0);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
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
	 * 根据对象获取json
	 * 
	 * @param it
	 * @param userId
	 * @return
	 * @throws JSONException
	 */
	public static String getJson(ItelligenceEntity it, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		//JSONObject parentJo = new JSONObject();
		try {
			jo.put("userId", userId);
			jo.put("catalogID", Itelligence.CATALOG_ID);
			jo.put(Itelligence.ITELLIGENCE_ID, it.getServerId());
			jo.put(Itelligence.ACQUISITIONTIME, it.getAcquisitionTime());
			jo.put(Itelligence.ACQUISITIONPOLICE, it.getAcquisitionPolice());
			jo.put(Itelligence.INFORMATIONSOURCE, it.getInformationSource());
			jo.put(Itelligence.INFORMATIONABSTRACT, it.getInformationAbstract());
			jo.put(Itelligence.INFORMATIONTYPE, it.getInformationType());
			jo.put(Itelligence.REPORTEDTIME, it.getReportedTime());
			jo.put(Itelligence.REPORTEDUNIT, it.getReportedUnit());
			jo.put(Itelligence.FEEDBACK, it.getFeedback());

			JSONArray ja = new JSONArray();
			if (it.getItAttachment() != null && it.getItAttachment().size() > 0) {
				for (int i = 0; i < it.getItAttachment().size(); i++) {
					JSONObject joAccessory = AttachmentDao.getJson(it
							.getItAttachment().get(i));
					ja.put(joAccessory);
				}
			}
			jo.put("flist", ja.toString());
			//parentJo.put("patrol", jo);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return jo.toString();
	}

	/**
	 * 根据对象获取API参数--情报信息
	 * 
	 * @param
	 * @param userId
	 * @return
	 */
	/*public static AjaxParams getParams(ItelligenceEntity ie, int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", String.valueOf(userId));
		params.put("catalogID", String.valueOf(Itelligence.CATALOG_ID));
		params.put(Itelligence.ITELLIGENCE_ID, String.valueOf(ie.getServerId()));
		if (!ie.getAcquisitionTime().equals("")) {
			params.put(Itelligence.ACQUISITIONTIME, ie.getAcquisitionTime());
		}
		if (!ie.getAcquisitionPolice().equals("")) {
			params.put(Itelligence.ACQUISITIONPOLICE, ie.getAcquisitionPolice());
		}
		if (!ie.getInformationSource().equals("")) {
			params.put(Itelligence.INFORMATIONSOURCE, ie.getInformationSource());
		}
		if (!ie.getInformationAbstract().equals("")) {
			params.put(Itelligence.INFORMATIONABSTRACT,
					ie.getInformationAbstract());
		}
		if (!ie.getInformationAbstract().equals("")) {
			params.put(Itelligence.INFORMATIONABSTRACT,
					ie.getInformationAbstract());
		}
		if (!ie.getInformationType().equals("")) {
			params.put(Itelligence.INFORMATIONTYPE, ie.getInformationType());
		}
		if (!ie.getReportedTime().equals("")) {
			params.put(Itelligence.REPORTEDTIME, ie.getReportedTime());
		}
		if (!ie.getReportedUnit().equals("")) {
			params.put(Itelligence.REPORTEDUNIT, ie.getReportedUnit());
		}
		if (!ie.getFeedback().equals("")) {
			params.put(Itelligence.FEEDBACK, ie.getFeedback());
		}

		if (ie.getItAttachment() != null && ie.getItAttachment().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < ie.getItAttachment().size(); i++) {
				JSONObject joAccessory = AttachmentDao.getJson(ie
						.getItAttachment().get(i));
				ja.put(joAccessory);
			}
			params.put("flist", ja.toString());
		}
		return params;
	}
*/
	/**
	 * 根据json获取对象
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public ArrayList<ItelligenceEntity> getObject(String json)
			throws JSONException {
		ArrayList<ItelligenceEntity> tels = null;
		JSONArray ja = null;
		if (json != null && !json.equals("anyType{}")) {
			tels = new ArrayList<ItelligenceEntity>();
			ja = new JSONArray(json);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				ItelligenceEntity ies = new ItelligenceEntity();
				if (!jo.isNull(Itelligence.ITELLIGENCE_ID)) {
					ies.setServerId(jo.getInt(Itelligence.ITELLIGENCE_ID));
				}
				if (!jo.isNull(Itelligence.ACQUISITIONTIME)) {
					ies.setAcquisitionTime(jo
							.getString(Itelligence.ACQUISITIONTIME));
				}
				if (!jo.isNull(Itelligence.ACQUISITIONPOLICE)) {
					ies.setAcquisitionPolice(jo
							.getString(Itelligence.ACQUISITIONPOLICE));
				}
				if (!jo.isNull(Itelligence.INFORMATIONSOURCE)) {
					ies.setInformationSource(jo
							.getString(Itelligence.INFORMATIONSOURCE));
				}
				if (!jo.isNull(Itelligence.INFORMATIONTYPE)) {
					ies.setInformationType(jo
							.getString(Itelligence.INFORMATIONTYPE));
				}
				if (!jo.isNull(Itelligence.INFORMATIONABSTRACT)) {
					ies.setInformationAbstract(jo
							.getString(Itelligence.INFORMATIONABSTRACT));
				}
				if (!jo.isNull(Itelligence.REPORTEDUNIT)) {
					ies.setReportedUnit(jo.getString(Itelligence.REPORTEDUNIT));
				}
				if (!jo.isNull(Itelligence.REPORTEDTIME)) {
					ies.setReportedTime(jo.getString(Itelligence.REPORTEDTIME));
				}
				if (!jo.isNull(Itelligence.FEEDBACK)) {
					ies.setFeedback(jo.getString(Itelligence.FEEDBACK));
				}
				if (!jo.isNull("affix")) {
					ArrayList<Accessory> accessorys = new ArrayList<Accessory>();
					JSONArray jaaccessorys = new JSONArray(
							jo.getString("affix"));
					for (int j = 0; j < jaaccessorys.length(); j++) {
						JSONObject joAttachment = jaaccessorys.getJSONObject(j);
						Accessory accessory = new Accessory();
						if (!joAttachment.isNull("url")) {
							String url = joAttachment.getString("url");
							accessory.setAccessoryPath(url.replace("../../",
									ActivityUtils.getServerWebApi(context)));
						}
						accessorys.add(accessory);
					}
					ies.setItAttachment(accessorys);
				}
				tels.add(ies);
			}
		}
		return tels;

	}
}
