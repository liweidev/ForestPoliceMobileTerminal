package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.QuestionedSituationEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import db.Database.QuestionedSituation;

/**
 * 盘问情况
 * 
 * @author xingyimin
 */
public class QuestionedSituationDao {

	private ForestDatabaseHelper dbHelper;
	private Context context;
	private static QuestionedSituationDao qDao;

	public QuestionedSituationDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	public static QuestionedSituationDao init(Context ct) {
		if (qDao == null) {
			qDao = new QuestionedSituationDao(ct);
		}
		return qDao;

	}

	/**
	 * 向本地数据库插入数据
	 * 
	 * @param questionedSituation
	 * @return
	 */
	public boolean insertInfo(QuestionedSituationEntity questionedSituation) {
		boolean flag = false;
		int iFborId = -1;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(QuestionedSituation.QUESTIONED_SITUATION_ID,
					questionedSituation.getQuestionedSituationID());
			contentValues.put(QuestionedSituation.ALLOW,
					questionedSituation.getAllow());
			contentValues.put(QuestionedSituation.ASK_BEGIN_TIME,
					questionedSituation.getAskBeginTIme());
			contentValues.put(QuestionedSituation.ASK_END_TIME,
					questionedSituation.getAskEndTIme());
			contentValues.put(QuestionedSituation.ASK_WHY,
					questionedSituation.getAskWhy());
			contentValues.put(QuestionedSituation.BIRTHDAY,
					questionedSituation.getBirthday());
			contentValues.put(QuestionedSituation.DOMICILE_PLACE,
					questionedSituation.getDomicilePlace());
			contentValues.put(QuestionedSituation.GENDER,
					questionedSituation.getGender());
			contentValues.put(QuestionedSituation.HANDING_INFORMATION,
					questionedSituation.getHandingInformation());
			contentValues.put(QuestionedSituation.NAME,
					questionedSituation.getName());
			contentValues.put(QuestionedSituation.OVERTIME,
					questionedSituation.getOvertime());
			contentValues.put(QuestionedSituation.SPONSOR,
					questionedSituation.getSponsor());
			// contentValues.put(QuestionedSituation.REMARK,
			// questionedSituation.getRemark());
			contentValues.put(QuestionedSituation.UNIT_OR_ADDRESS,
					questionedSituation.getUnitOrAddress());
			contentValues.put(QuestionedSituation.YEAR,
					questionedSituation.getYear());

			long ok = db.insert(
					QuestionedSituation.QUESTIONED_SITUATION_TABLE_NAME, null,
					contentValues);

			if (-1 != ok) {
				flag = true;
			}
			String sql = "SELECT MAX("
					+ QuestionedSituation.QUESTIONED_SITUATION_TABLE_ID
					+ ") FROM "
					+ QuestionedSituation.QUESTIONED_SITUATION_TABLE_NAME;

			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				iFborId = cursor.getInt(0);
			}
			
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			db.endTransaction();
			if (cursor != null) {
				cursor.close();
			}
		}
		if (questionedSituation.getAccessoryList() != null
				&& questionedSituation.getAccessoryList().size() > 0) {
			for (int i = 0; i < questionedSituation.getAccessoryList().size(); i++) {
				AttachmentDao dao = new AttachmentDao(context);
				questionedSituation.getAccessoryList().get(i)
						.setTableId(QuestionedSituation.CATALOG_ID);
				questionedSituation.getAccessoryList().get(i).setRowId(iFborId);
				flag = dao.insertInfo(questionedSituation.getAccessoryList()
						.get(i));
			}
		}
		return flag;
	}

	/**
	 * 删除hang
	 */
	public boolean delTable(String QuestionedSituationId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		try {
			int res = db.delete(
					QuestionedSituation.QUESTIONED_SITUATION_TABLE_NAME,
					QuestionedSituation.QUESTIONED_SITUATION_TABLE_ID + "=?",
					new String[] { QuestionedSituationId });

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
	 */

	public ArrayList<QuestionedSituationEntity> getAllInfos() {

		ArrayList<QuestionedSituationEntity> infos = new ArrayList<QuestionedSituationEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			String sql = "select * from "
					+ QuestionedSituation.QUESTIONED_SITUATION_TABLE_NAME;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);

			while (cursor.moveToNext()) {
				QuestionedSituationEntity qse = new QuestionedSituationEntity();
				qse.setQsid(cursor.getInt(cursor
						.getColumnIndex(QuestionedSituation.QUESTIONED_SITUATION_TABLE_ID)));
				qse.setQuestionedSituationID(cursor.getInt(cursor
						.getColumnIndex(QuestionedSituation.QUESTIONED_SITUATION_ID)));
				qse.setAllow(cursor.getString(cursor
						.getColumnIndex(QuestionedSituation.ALLOW)));
				qse.setAskBeginTIme(cursor.getString(cursor
						.getColumnIndex(QuestionedSituation.ASK_BEGIN_TIME)));
				qse.setAskEndTIme(cursor.getString(cursor
						.getColumnIndex(QuestionedSituation.ASK_END_TIME)));
				qse.setAskWhy(cursor.getString(cursor
						.getColumnIndex(QuestionedSituation.ASK_WHY)));
				qse.setBirthday(cursor.getString(cursor
						.getColumnIndex(QuestionedSituation.BIRTHDAY)));
				qse.setDomicilePlace(cursor.getString(cursor
						.getColumnIndex(QuestionedSituation.DOMICILE_PLACE)));
				qse.setGender(cursor.getInt(cursor
						.getColumnIndex(QuestionedSituation.GENDER)));
				qse.setHandingInformation(cursor.getString(cursor
						.getColumnIndex(QuestionedSituation.HANDING_INFORMATION)));
				qse.setName(cursor.getString(cursor
						.getColumnIndex(QuestionedSituation.NAME)));
				qse.setOvertime(cursor.getString(cursor
						.getColumnIndex(QuestionedSituation.OVERTIME)));
				// qse.setRemark(cursor.getString(cursor
				// .getColumnIndex(QuestionedSituation.REMARK)));
				qse.setSponsor(cursor.getString(cursor
						.getColumnIndex(QuestionedSituation.SPONSOR)));
				qse.setUnitOrAddress(cursor.getString(cursor
						.getColumnIndex(QuestionedSituation.UNIT_OR_ADDRESS)));
				qse.setYear(cursor.getString(cursor
						.getColumnIndex(QuestionedSituation.YEAR)));
				infos.add(qse);
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

		AttachmentDao aDao = null;
		for (int i = 0; i < infos.size(); i++) {
			int borId = infos.get(i).getQuestionedSituationID();
			aDao = new AttachmentDao(context);
			ArrayList<Accessory> list = aDao.getInfosById(
					QuestionedSituation.CATALOG_ID, borId);
			infos.get(i).setAccessoryList(list);
		}
		return infos;
	}

	/**
	 * 获取数量
	 */

	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM "
				+ QuestionedSituation.QUESTIONED_SITUATION_TABLE_NAME;

		try {
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				count = cursor.getInt(0);
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
		return count;
	}

	/**
	 * 根据对象获得JSON字符串
	 * 
	 * @param questionedSituation
	 * @param userId
	 * @return JSON字符串
	 * @throws JSONException
	 */

	public static String getJson(QuestionedSituationEntity questionedSituation,
			int userId) throws JSONException {
		JSONObject jo = new JSONObject();
		jo.put("userId", userId);
		jo.put("catalogID", QuestionedSituation.CATALOG_ID);
		jo.put(QuestionedSituation.QUESTIONED_SITUATION_ID,
				questionedSituation.getQuestionedSituationID());
		jo.put(QuestionedSituation.ALLOW, questionedSituation.getAllow());
		jo.put(QuestionedSituation.ASK_BEGIN_TIME,
				questionedSituation.getAskBeginTIme());
		jo.put(QuestionedSituation.ASK_END_TIME,
				questionedSituation.getAskEndTIme());
		jo.put(QuestionedSituation.ASK_WHY, questionedSituation.getAskWhy());
		jo.put(QuestionedSituation.BIRTHDAY, questionedSituation.getBirthday());
		jo.put(QuestionedSituation.DOMICILE_PLACE,
				questionedSituation.getDomicilePlace());
		jo.put(QuestionedSituation.GENDER, questionedSituation.getGender());
		jo.put(QuestionedSituation.HANDING_INFORMATION,
				questionedSituation.getHandingInformation());
		jo.put(QuestionedSituation.NAME, questionedSituation.getName());
		jo.put(QuestionedSituation.OVERTIME, questionedSituation.getOvertime());
		jo.put(QuestionedSituation.SPONSOR, questionedSituation.getSponsor());
		// jo.put(QuestionedSituation.REMARK, questionedSituation.getRemark());
		jo.put(QuestionedSituation.UNIT_OR_ADDRESS,
				questionedSituation.getUnitOrAddress());
		jo.put(QuestionedSituation.YEAR, questionedSituation.getYear());
		JSONArray ja = new JSONArray();
		if (questionedSituation.getAccessoryList() != null
				&& questionedSituation.getAccessoryList().size() > 0) {
			for (int i = 0; i < questionedSituation.getAccessoryList().size(); i++) {
				JSONObject json = AttachmentDao.getJson(questionedSituation
						.getAccessoryList().get(i));
				ja.put(json);
			}
		}
		jo.put("flist", ja.toString());
		jo.put("fileType", 1);
		JSONObject job = new JSONObject();
		job.put("QuestionedSituationList", jo);
		return job.toString();
	}

	/**
	 * 根据对象获取API参数
	 * 
	 * @param questionedSituation
	 * @param userId
	 * @return
	 */
	/*public static AjaxParams getParams(
			QuestionedSituationEntity questionedSituation, int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId + "");
		params.put("catalogID", QuestionedSituation.CATALOG_ID + "");
		if (questionedSituation.getQuestionedSituationID() != 0) {
			params.put(QuestionedSituation.QUESTIONED_SITUATION_ID,
					questionedSituation.getQuestionedSituationID() + "");
		}
		if (questionedSituation.getAllow() != null
				&& !questionedSituation.getAllow().equals("")) {
			params.put(QuestionedSituation.ALLOW,
					questionedSituation.getAllow());
		}
		if (questionedSituation.getAskBeginTIme() != null
				&& !questionedSituation.getAskBeginTIme().equals("")) {
			params.put(QuestionedSituation.ASK_BEGIN_TIME,
					questionedSituation.getAskBeginTIme());
		}
		if (questionedSituation.getAskEndTIme() != null
				&& !questionedSituation.getAskEndTIme().equals("")) {
			params.put(QuestionedSituation.ASK_END_TIME,
					questionedSituation.getAskEndTIme());
		}
		if (questionedSituation.getAskWhy() != null
				&& !questionedSituation.getAskWhy().equals("")) {
			params.put(QuestionedSituation.ASK_WHY,
					questionedSituation.getAskWhy());
		}
		if (questionedSituation.getBirthday() != null
				&& !questionedSituation.getBirthday().equals("")) {
			params.put(QuestionedSituation.BIRTHDAY,
					questionedSituation.getBirthday());
		}
		if (questionedSituation.getDomicilePlace() != null
				&& !questionedSituation.getDomicilePlace().equals("")) {
			params.put(QuestionedSituation.DOMICILE_PLACE,
					questionedSituation.getDomicilePlace());
		}
		if (questionedSituation.getGender() != 0) {
			params.put(QuestionedSituation.GENDER,
					questionedSituation.getGender() + "");
		}
		if (questionedSituation.getHandingInformation() != null
				&& !questionedSituation.getHandingInformation().equals("")) {
			params.put(QuestionedSituation.HANDING_INFORMATION,
					questionedSituation.getHandingInformation());
		}
		if (questionedSituation.getName() != null
				&& !questionedSituation.getName().equals("")) {
			params.put(QuestionedSituation.NAME, questionedSituation.getName());
		}
		if (questionedSituation.getOvertime() != null
				&& !questionedSituation.getOvertime().equals("")) {
			params.put(QuestionedSituation.OVERTIME,
					questionedSituation.getOvertime());
		}
		if (questionedSituation.getSponsor() != null
				&& !questionedSituation.getSponsor().equals("")) {
			params.put(QuestionedSituation.SPONSOR,
					questionedSituation.getSponsor());
		}
		if (questionedSituation.getUnitOrAddress() != null
				&& !questionedSituation.getUnitOrAddress().equals("")) {
			// jo.put(QuestionedSituation.REMARK,
			// questionedSituation.getRemark());
			params.put(QuestionedSituation.UNIT_OR_ADDRESS,
					questionedSituation.getUnitOrAddress());
		}
		if (questionedSituation.getYear() != null
				&& !questionedSituation.getYear().equals("")) {
			params.put(QuestionedSituation.YEAR, questionedSituation.getYear());
		}
		if (questionedSituation.getAccessoryList() != null
				&& questionedSituation.getAccessoryList().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < questionedSituation.getAccessoryList().size(); i++) {
				JSONObject json = AttachmentDao.getJson(questionedSituation
						.getAccessoryList().get(i));
				ja.put(json);
			}
			params.put("flist", ja.toString());
		}
		return params;
	}
*/
	/**
	 * 根据从服务器查询获取的json字符串获得QuestionedSituationEntity对象集合
	 * 
	 * @param json
	 * @return QuestionedSituationEntity对象集合
	 * @throws JSONException
	 */
	public List<QuestionedSituationEntity> getObject(String json)
			throws JSONException {
		List<QuestionedSituationEntity> questionedSituationList = null;
		JSONArray ja = null;
		if (json != null && !json.equals("[]")) {
			questionedSituationList = new ArrayList<QuestionedSituationEntity>();
			ja = new JSONArray(json);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				QuestionedSituationEntity qse = new QuestionedSituationEntity();
				if (!jo.isNull(QuestionedSituation.QUESTIONED_SITUATION_ID)) {
					qse.setQuestionedSituationID(jo
							.getInt(QuestionedSituation.QUESTIONED_SITUATION_ID));
				}
				if (!jo.isNull(QuestionedSituation.ALLOW)) {
					qse.setAllow(jo.getString(QuestionedSituation.ALLOW));
				}
				if (!jo.isNull(QuestionedSituation.ASK_BEGIN_TIME)) {
					qse.setAskBeginTIme(jo
							.getString(QuestionedSituation.ASK_BEGIN_TIME));
				}
				if (!jo.isNull(QuestionedSituation.GENDER)) {
					qse.setGender(jo.getInt(QuestionedSituation.GENDER));
				}
				if (!jo.isNull(QuestionedSituation.ASK_END_TIME)) {
					qse.setAskEndTIme(jo
							.getString(QuestionedSituation.ASK_END_TIME));
				}
				if (!jo.isNull(QuestionedSituation.ASK_WHY)) {
					qse.setAskWhy(jo.getString(QuestionedSituation.ASK_WHY));
				}
				if (!jo.isNull(QuestionedSituation.BIRTHDAY)) {
					qse.setBirthday(jo.getString(QuestionedSituation.BIRTHDAY));
				}
				if (!jo.isNull(QuestionedSituation.DOMICILE_PLACE)) {
					qse.setDomicilePlace(jo
							.getString(QuestionedSituation.DOMICILE_PLACE));
				}
				if (!jo.isNull(QuestionedSituation.HANDING_INFORMATION)) {
					qse.setHandingInformation(jo
							.getString(QuestionedSituation.HANDING_INFORMATION));
				}
				if (!jo.isNull(QuestionedSituation.OVERTIME)) {
					qse.setOvertime(jo.getString(QuestionedSituation.OVERTIME));
				}
				if (!jo.isNull(QuestionedSituation.SPONSOR)) {
					qse.setSponsor(jo.getString(QuestionedSituation.SPONSOR));
				}
				if (!jo.isNull(QuestionedSituation.NAME)) {
					qse.setName(jo.getString(QuestionedSituation.NAME));
				}
				if (!jo.isNull(QuestionedSituation.UNIT_OR_ADDRESS)) {
					qse.setUnitOrAddress(jo
							.getString(QuestionedSituation.UNIT_OR_ADDRESS));
				}
				if (!jo.isNull(QuestionedSituation.YEAR)) {
					qse.setYear(jo.getString(QuestionedSituation.YEAR));
				}
				// if (!jo.isNull(QuestionedSituation.REMARK)) {
				// qse.setRemark(jo.getString(QuestionedSituation.REMARK));
				// }

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

					qse.setAccessoryList(accessorys);
				}
				questionedSituationList.add(qse);
			}
		}
		return questionedSituationList;
	}
}
