package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.InterviewEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import db.Database.Interview;
import db.Database.Location;

/**
 * 重点列管人员走访
 * 
 * @author xingyimin
 * 
 */
public class InterviewDao {

	private ForestDatabaseHelper dbHelper;
	private Context context;
	private static InterviewDao ivDao;

	public InterviewDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	public static InterviewDao init(Context ct) {
		if (ivDao == null) {
			ivDao = new InterviewDao(ct);
		}
		return ivDao;

	}

	/**
	 * 向本地数据库插入数据
	 * 
	 * @param interview
	 * @return
	 */
	public boolean insertInfo(InterviewEntity interview) {
		boolean flag = false;
		int iFborId = -1;
		LocationDao lDao = new LocationDao(context);
		int locationId = lDao.insertInfo(interview.getLocation_id());
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		if (locationId > 0) {
			Cursor cursor = null;
			try {
				ContentValues contentValues = new ContentValues();
				contentValues.put(Interview.LOCATION, locationId);
				contentValues.put(Interview.INTERVIEW_ID,
						interview.getInterviewID());
				contentValues.put(Interview.ADDRESS, interview.getAddress());
				contentValues.put(Interview.AGE, interview.getAge());
				contentValues.put(Interview.GENDER, interview.getGender());
				contentValues.put(Interview.INTERVIEW_CONTENT,
						interview.getInterviewContent());
				contentValues.put(Interview.INTERVIEW_OBJECT,
						interview.getInterviewObejct());
				contentValues.put(Interview.INTERVIEW_POLICE,
						interview.getInterviewPolice());
				contentValues.put(Interview.INTERVIEW_SITE,
						interview.getInterviewSite());
				contentValues.put(Interview.INTERVIEW_TIME,
						interview.getInterviewTime());
				contentValues.put(Interview.MANAGED_CAUSE,
						interview.getManagedCause());
				contentValues.put(Interview.NAME, interview.getName());
				contentValues.put(Interview.OPINION, interview.getOpinion());
				// contentValues.put(Interview.REMARK, interview.getRemark());
				contentValues.put(Interview.UNIT_NAME, interview.getUnitName());
				contentValues.put(Interview.UNIT_ADDRESS,
						interview.getUnitAddress());

				long ok = db.insert(Interview.INTERVIEW_TABLE_NAME, null,
						contentValues);

				if (-1 != ok) {
					flag = true;
				}
				String sql = "SELECT MAX(" + Interview.INTERVIEW_TABLE_ID
						+ ") FROM " + Interview.INTERVIEW_TABLE_NAME;

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
			if (interview.getAccessoryList() != null
					&& interview.getAccessoryList().size() > 0) {
				for (int i = 0; i < interview.getAccessoryList().size(); i++) {
					AttachmentDao dao = new AttachmentDao(context);
					interview.getAccessoryList().get(i)
							.setTableId(Interview.catalogID);
					interview.getAccessoryList().get(i).setRowId(iFborId);
					flag = dao.insertInfo(interview.getAccessoryList().get(i));
				}
			}
		}
		return flag;
	}

	/**
	 * 删除hang
	 */
	public boolean delTable(String interviewId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		try {
			int res = db.delete(Interview.INTERVIEW_TABLE_NAME,
					Interview.INTERVIEW_TABLE_ID + "=?",
					new String[] { interviewId });

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

	public ArrayList<InterviewEntity> getAllInfos() {

		ArrayList<InterviewEntity> infos = new ArrayList<InterviewEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			String sql = "select * from " + Interview.INTERVIEW_TABLE_NAME
					+ " Left join " + Location.LOCATION_TABLE_NAME + " where "
					+ Interview.INTERVIEW_TABLE_NAME + "." + Interview.LOCATION
					+ "=" + Location.LOCATION_TABLE_NAME + "."
					+ Location.LOCATION_ID;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);

			while (cursor.moveToNext()) {
				InterviewEntity ie = new InterviewEntity();
				ie.setInterviewID(cursor.getInt(cursor
						.getColumnIndex(Interview.INTERVIEW_ID)));
				ie.setId(cursor.getInt(cursor
						.getColumnIndex(Interview.INTERVIEW_TABLE_ID)));
				ie.setAddress(cursor.getString(cursor
						.getColumnIndex(Interview.ADDRESS)));
				ie.setAge(cursor.getString(cursor.getColumnIndex(Interview.AGE)));
				ie.setGender(cursor.getInt(cursor
						.getColumnIndex(Interview.GENDER)));
				ie.setInterviewContent(cursor.getString(cursor
						.getColumnIndex(Interview.INTERVIEW_CONTENT)));
				ie.setInterviewObejct(cursor.getInt(cursor
						.getColumnIndex(Interview.INTERVIEW_OBJECT)));
				ie.setInterviewPolice(cursor.getString(cursor
						.getColumnIndex(Interview.INTERVIEW_POLICE)));
				ie.setInterviewSite(cursor.getString(cursor
						.getColumnIndex(Interview.INTERVIEW_SITE)));
				ie.setInterviewTime(cursor.getString(cursor
						.getColumnIndex(Interview.INTERVIEW_TIME)));
				ie.setManagedCause(cursor.getString(cursor
						.getColumnIndex(Interview.MANAGED_CAUSE)));
				ie.setOpinion(cursor.getString(cursor
						.getColumnIndex(Interview.OPINION)));
				ie.setName(cursor.getString(cursor
						.getColumnIndex(Interview.NAME)));
				ie.setUnitAddress(cursor.getString(cursor
						.getColumnIndex(Interview.UNIT_ADDRESS)));
				ie.setUnitName(cursor.getString(cursor
						.getColumnIndex(Interview.UNIT_NAME)));
				// ie.setRemark(cursor.getString(cursor
				// .getColumnIndex(Interview.REMARK)));

				Loaction l = new Loaction();
				l.setLocation_id(cursor.getInt(cursor
						.getColumnIndex(Location.LOCATION_ID)));
				l.setELEVATION(cursor.getDouble(cursor
						.getColumnIndex(Location.ELEVATION)));
				l.setLATITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LATITUDE)));
				l.setLONGITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LONGITUDE)));
				l.setTIME(cursor.getString(cursor.getColumnIndex(Location.TIME)));
				ie.setLocation_id(l);

				infos.add(ie);
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
			int borId = infos.get(i).getInterviewID();
			aDao = new AttachmentDao(context);
			ArrayList<Accessory> list = aDao.getInfosById(Interview.catalogID,
					borId);
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
		String sql = "SELECT COUNT(*) FROM " + Interview.INTERVIEW_TABLE_NAME;

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
	 * @param ie
	 * @param userId
	 * @return JSON字符串
	 * @throws JSONException
	 */

	public static String getJson(InterviewEntity ie, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		jo.put("userId", userId);
		jo.put("catalogID", Interview.catalogID);
		jo.put(Interview.INTERVIEW_ID, ie.getInterviewID());
		jo.put(Interview.ADDRESS, ie.getAddress());
		jo.put(Interview.AGE, ie.getAge());
		jo.put(Interview.GENDER, ie.getGender());
		jo.put(Interview.INTERVIEW_CONTENT, ie.getInterviewContent());
		jo.put(Interview.INTERVIEW_OBJECT, ie.getInterviewObejct());
		jo.put(Interview.INTERVIEW_POLICE, ie.getInterviewPolice());
		jo.put(Interview.INTERVIEW_SITE, ie.getInterviewSite());
		jo.put(Interview.INTERVIEW_TIME, ie.getInterviewTime());
		jo.put(Interview.NAME, ie.getName());
		jo.put(Interview.MANAGED_CAUSE, ie.getManagedCause());
		jo.put(Interview.OPINION, ie.getOpinion());
		jo.put(Interview.UNIT_ADDRESS, ie.getUnitAddress());
		jo.put(Interview.UNIT_NAME, ie.getUnitName());
		// jo.put(Interview.REMARK, ie.getRemark());

		JSONObject joLocation = new JSONObject();
		joLocation.put(Location.LONGITUDE, ie.getLocation_id().getLONGITUDE());
		joLocation.put(Location.LATITUDE, ie.getLocation_id().getLATITUDE());
		joLocation.put(Location.ELEVATION, ie.getLocation_id().getELEVATION());
		joLocation.put("Time", ie.getLocation_id().getTIME());

		jo.put(Interview.LOCATION, joLocation.toString());
		JSONArray ja = new JSONArray();
		if (ie.getAccessoryList() != null && ie.getAccessoryList().size() > 0) {
			for (int i = 0; i < ie.getAccessoryList().size(); i++) {
				JSONObject json = AttachmentDao.getJson(ie.getAccessoryList()
						.get(i));
				ja.put(json);
			}
		}
		jo.put("flist", ja.toString());
		jo.put("fileType", 1);
		JSONObject job = new JSONObject();
		job.put("InterviewList", jo);
		return job.toString();
	}

	/**
	 * 根据对象获取API参数
	 * 
	 * @param ie
	 * @param userId
	 * @return
	 */
	/*public static AjaxParams getParams(InterviewEntity ie, int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId + "");
		params.put("catalogID", Interview.catalogID + "");
		params.put(Interview.INTERVIEW_ID, ie.getInterviewID() + "");
		params.put(Interview.ADDRESS, ie.getAddress());
		params.put(Interview.AGE, ie.getAge());
		params.put(Interview.GENDER, ie.getGender() + "");
		params.put(Interview.INTERVIEW_CONTENT, ie.getInterviewContent());
		params.put(Interview.INTERVIEW_OBJECT, ie.getInterviewObejct() + "");
		params.put(Interview.INTERVIEW_POLICE, ie.getInterviewPolice());
		params.put(Interview.INTERVIEW_SITE, ie.getInterviewSite());
		params.put(Interview.INTERVIEW_TIME, ie.getInterviewTime());
		params.put(Interview.NAME, ie.getName());
		params.put(Interview.MANAGED_CAUSE, ie.getManagedCause());
		params.put(Interview.OPINION, ie.getOpinion());
		params.put(Interview.UNIT_ADDRESS, ie.getUnitAddress());
		params.put(Interview.UNIT_NAME, ie.getUnitName());
		// jo.put(Interview.REMARK, ie.getRemark());
		JSONObject joLocation = new JSONObject();
		try {
			joLocation.put(Location.LONGITUDE, ie.getLocation_id()
					.getLONGITUDE());
			joLocation
					.put(Location.LATITUDE, ie.getLocation_id().getLATITUDE());
			joLocation.put(Location.ELEVATION, ie.getLocation_id()
					.getELEVATION());
			joLocation.put("Time", ie.getLocation_id().getTIME());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		params.put(Interview.LOCATION, joLocation.toString());
		if (ie.getAccessoryList() != null && ie.getAccessoryList().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < ie.getAccessoryList().size(); i++) {
				JSONObject json = AttachmentDao.getJson(ie.getAccessoryList()
						.get(i));
				ja.put(json);
			}
			params.put("flist", ja.toString());
		}
		return params;
	}*/

	/**
	 * 根据查询结果，获取对象集合
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public List<InterviewEntity> getObject(String json) throws JSONException {
		List<InterviewEntity> interviewList = null;
		JSONArray ja = null;
		if (json != null && !json.equals("[]")) {
			ja = new JSONArray(json);
			interviewList = new ArrayList<InterviewEntity>();
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				InterviewEntity ie = new InterviewEntity();
				if (!jo.isNull(Interview.INTERVIEW_ID)) {
					ie.setInterviewID(jo.getInt(Interview.INTERVIEW_ID));
				}
				if (!jo.isNull(Interview.ADDRESS)) {
					ie.setAddress(jo.getString(Interview.ADDRESS));
				}
				if (!jo.isNull(Interview.AGE)) {
					ie.setAge(jo.getString(Interview.AGE));
				}
				if (!jo.isNull(Interview.GENDER)) {
					ie.setGender(jo.getInt(Interview.GENDER));
				}
				if (!jo.isNull(Interview.INTERVIEW_CONTENT)) {
					ie.setInterviewContent(jo
							.getString(Interview.INTERVIEW_CONTENT));
				}
				if (!jo.isNull(Interview.INTERVIEW_OBJECT)) {
					ie.setInterviewObejct(jo.getInt(Interview.INTERVIEW_OBJECT));
				}
				if (!jo.isNull(Interview.INTERVIEW_POLICE)) {
					ie.setInterviewPolice(jo
							.getString(Interview.INTERVIEW_POLICE));
				}
				if (!jo.isNull(Interview.INTERVIEW_SITE)) {
					ie.setInterviewSite(jo.getString(Interview.INTERVIEW_SITE));
				}
				if (!jo.isNull(Interview.INTERVIEW_TIME)) {
					ie.setInterviewTime(jo.getString(Interview.INTERVIEW_TIME));
				}
				if (!jo.isNull(Interview.MANAGED_CAUSE)) {
					ie.setManagedCause(jo.getString(Interview.MANAGED_CAUSE));
				}
				if (!jo.isNull(Interview.OPINION)) {
					ie.setOpinion(jo.getString(Interview.OPINION));
				}
				if (!jo.isNull(Interview.NAME)) {
					ie.setName(jo.getString(Interview.NAME));
				}
				if (!jo.isNull(Interview.UNIT_ADDRESS)) {
					ie.setUnitAddress(jo.getString(Interview.UNIT_ADDRESS));
				}
				if (!jo.isNull(Interview.UNIT_NAME)) {
					ie.setUnitName(jo.getString(Interview.UNIT_NAME));
				}
				// if (!jo.isNull(Interview.REMARK)) {
				// ie.setRemark(jo.getString(Interview.REMARK));
				// }
				if (!jo.isNull(Interview.LOCATION)) {
					JSONObject joLocation = jo
							.getJSONObject(Interview.LOCATION);
					Loaction l = new Loaction();
					if (!joLocation.isNull(Location.LONGITUDE)) {
						l.setLONGITUDE(joLocation.getDouble(Location.LONGITUDE));
					}
					if (!joLocation.isNull(Location.LATITUDE)) {
						l.setLATITUDE(joLocation.getDouble(Location.LATITUDE));
					}
					if (!joLocation.isNull(Location.ELEVATION)) {
						l.setELEVATION(joLocation.getDouble(Location.ELEVATION));
					}
					if (!joLocation.isNull(Location.TIME)) {
						l.setTIME(joLocation.getString(Location.TIME));
					}
					ie.setLocation_id(l);
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
					ie.setAccessoryList(accessorys);
				}
				interviewList.add(ie);
			}
		}
		return interviewList;
	}
}
