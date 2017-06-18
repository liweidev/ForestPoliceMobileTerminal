package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.ManagedObjectEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import db.Database.Location;
import db.Database.ManagedObject;

/**
 * 重点列管人员（单位）情况
 * 
 * @author xingyimin
 * 
 */
public class ManagedObjectDao {
	private ForestDatabaseHelper dbHelper;
	private Context context;
	private static ManagedObjectDao moDao;

	public ManagedObjectDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	public static ManagedObjectDao init(Context ct) {
		if (moDao == null) {
			moDao = new ManagedObjectDao(ct);
		}
		return moDao;

	}

	/**
	 * 向表中插入一条数据
	 * 
	 * @param managedObject
	 * @return
	 */
	public boolean insertInfo(ManagedObjectEntity managedObject) {
		boolean flag = false;
		int moeId = -1;
		LocationDao lDao = new LocationDao(context);
		int locationId = lDao.insertInfo(managedObject.getLocation_id());
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		if (locationId > 0) {
			Cursor cursor = null;
			try {
				ContentValues contentValues = new ContentValues();
				contentValues.put(ManagedObject.LOCATION, locationId);
				contentValues.put(ManagedObject.MANAGED_OBJECT_ID,
						managedObject.getManagedObjectID());
				contentValues.put(ManagedObject.ADDRESS_OR_UNIT,
						managedObject.getAddressOrUnit());
				contentValues.put(ManagedObject.CARD_ID,
						managedObject.getCardID());
				contentValues.put(ManagedObject.CAREER,
						managedObject.getCareer());
				contentValues.put(ManagedObject.DOMICILE_PLACE,
						managedObject.getDomicilePlace());
				contentValues.put(ManagedObject.GENDER,
						managedObject.getGender());
				contentValues.put(ManagedObject.MANAGED_CAUSE,
						managedObject.getManagedCause());
				contentValues.put(ManagedObject.MANAGED_TIME,
						managedObject.getManagedTIme());
				contentValues.put(ManagedObject.RESPONSIBILITY_POLICE,
						managedObject.getResponsibilityPolice());
				// contentValues.put(ManagedObject.REMARK,
				// managedObject.getRemark());
				contentValues.put(ManagedObject.UNIT_NAME,
						managedObject.getUnitName());
				contentValues.put(ManagedObject.SPECIFIC_CAUSES,
						managedObject.getSpecificCauses());

				long ok = db.insert(ManagedObject.MANAGED_OBJECT_TABLE_NAME,
						null, contentValues);
				if (-1 != ok) {
					flag = true;
				}
				String sql = "SELECT MAX("
						+ ManagedObject.MANAGED_OBJECT_TABLE_ID + ") FROM "
						+ ManagedObject.MANAGED_OBJECT_TABLE_NAME;

				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					moeId = cursor.getInt(0);
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
			if (managedObject.getAccessoryList() != null
					&& managedObject.getAccessoryList().size() > 0) {
				for (int i = 0; i < managedObject.getAccessoryList().size(); i++) {
					AttachmentDao attachment = new AttachmentDao(context);
					managedObject.getAccessoryList().get(i)
							.setTableId(ManagedObject.catalogID);
					managedObject.getAccessoryList().get(i).setRowId(moeId);
					flag = attachment.insertInfo(managedObject
							.getAccessoryList().get(i));
				}
			}
		}
		return flag;
	}

	/**
	 * 根据Id，从表中删除一条数据
	 * 
	 * @param moeId
	 * @return
	 */
	public boolean delRawById(String moeId) {
		boolean isOK = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		try {
			int res = db.delete(ManagedObject.MANAGED_OBJECT_TABLE_NAME,
					ManagedObject.MANAGED_OBJECT_TABLE_ID + "=?",
					new String[] { moeId });
			if (res > 0) {
				isOK = true;
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		return isOK;
	}

	/**
	 * 获取本地数据库所有数据
	 * 
	 * @return 数据集合
	 */
	public List<ManagedObjectEntity> getAllInfors() {
		List<ManagedObjectEntity> managedList = new ArrayList<ManagedObjectEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		String sql = "select * from " + ManagedObject.MANAGED_OBJECT_TABLE_NAME
				+ " left join " + Location.LOCATION_TABLE_NAME + " where "
				+ ManagedObject.MANAGED_OBJECT_TABLE_NAME + "."
				+ ManagedObject.LOCATION + "=" + Location.LOCATION_TABLE_NAME
				+ "." + Location.LOCATION_ID;
		cursor = db.rawQuery(sql, null);

		while (cursor.moveToNext()) {
			ManagedObjectEntity mo = new ManagedObjectEntity();
			mo.setMoId(cursor.getInt(cursor
					.getColumnIndex(ManagedObject.MANAGED_OBJECT_TABLE_ID)));
			mo.setManagedObjectID(cursor.getInt(cursor
					.getColumnIndex(ManagedObject.MANAGED_OBJECT_ID)));
			mo.setAddressOrUnit(cursor.getString(cursor
					.getColumnIndex(ManagedObject.ADDRESS_OR_UNIT)));
			mo.setCardID(cursor.getString(cursor
					.getColumnIndex(ManagedObject.CARD_ID)));
			mo.setCareer(cursor.getInt(cursor
					.getColumnIndex(ManagedObject.CAREER)));
			mo.setDomicilePlace(cursor.getString(cursor
					.getColumnIndex(ManagedObject.DOMICILE_PLACE)));
			mo.setGender(cursor.getInt(cursor
					.getColumnIndex(ManagedObject.GENDER)));
			mo.setManagedCause(cursor.getInt(cursor
					.getColumnIndex(ManagedObject.MANAGED_CAUSE)));
			mo.setManagedTIme(cursor.getString(cursor
					.getColumnIndex(ManagedObject.MANAGED_TIME)));
			mo.setUnitName(cursor.getString(cursor
					.getColumnIndex(ManagedObject.UNIT_NAME)));
			mo.setResponsibilityPolice(cursor.getInt(cursor
					.getColumnIndex(ManagedObject.RESPONSIBILITY_POLICE)));
			// mo.setRemark(cursor.getString(cursor
			// .getColumnIndex(ManagedObject.REMARK)));
			mo.setSpecificCauses(cursor.getString(cursor
					.getColumnIndex(ManagedObject.SPECIFIC_CAUSES)));
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
			mo.setLocation_id(l);
			managedList.add(mo);
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		if (cursor != null) {
			cursor.close();
		}
		AttachmentDao aDao = null;
		for (int i = 0; i < managedList.size(); i++) {
			int moId = managedList.get(i).getMoId();
			aDao = new AttachmentDao(context);
			ArrayList<Accessory> list = aDao.getInfosById(
					ManagedObject.catalogID, moId);
			managedList.get(i).setAccessoryList(list);
		}
		return managedList;
	}

	/**
	 * 获取本地数据库数据的条数
	 * 
	 * @return 数据个数
	 */
	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM "
				+ ManagedObject.MANAGED_OBJECT_TABLE_NAME;

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
	 * 根据对象获取JSON字符串
	 * 
	 * @param managedObject
	 * @param userId
	 * @return JSON字符串
	 * @throws JSONException
	 */
	public static String getJson(ManagedObjectEntity managedObject, int userId)
			throws JSONException {
		JSONObject json = new JSONObject();
		json.put("userId", userId);
		json.put("catalogID", ManagedObject.catalogID);
		json.put(ManagedObject.MANAGED_OBJECT_ID,
				managedObject.getManagedObjectID());
		json.put(ManagedObject.LOCATION, managedObject.getLocation_id());
		json.put(ManagedObject.ADDRESS_OR_UNIT,
				managedObject.getAddressOrUnit());
		json.put(ManagedObject.CARD_ID, managedObject.getCardID());
		json.put(ManagedObject.CAREER, managedObject.getCareer());
		json.put(ManagedObject.DOMICILE_PLACE, managedObject.getDomicilePlace());
		json.put(ManagedObject.GENDER, managedObject.getGender());
		json.put(ManagedObject.MANAGED_CAUSE, managedObject.getManagedCause());
		json.put(ManagedObject.MANAGED_TIME, managedObject.getManagedTIme());
		json.put(ManagedObject.RESPONSIBILITY_POLICE,
				managedObject.getResponsibilityPolice());
		// json.put(ManagedObject.REMARK, managedObject.getRemark());
		json.put(ManagedObject.UNIT_NAME, managedObject.getUnitName());
		json.put(ManagedObject.SPECIFIC_CAUSES,
				managedObject.getSpecificCauses());

		JSONObject jsonLocation = new JSONObject();
		jsonLocation.put(Location.LONGITUDE, managedObject.getLocation_id()
				.getLONGITUDE());
		jsonLocation.put(Location.LATITUDE, managedObject.getLocation_id()
				.getLATITUDE());
		jsonLocation.put(Location.ELEVATION, managedObject.getLocation_id()
				.getELEVATION());
		jsonLocation.put("Time", managedObject.getLocation_id().getTIME());

		json.put(ManagedObject.LOCATION, jsonLocation.toString());
		JSONArray ja = new JSONArray();
		if (managedObject.getAccessoryList() != null && managedObject.getAccessoryList().size() > 0) {
			
			for (int i = 0; i < managedObject.getAccessoryList().size(); i++) {
				JSONObject jo = AttachmentDao.getJson(managedObject.getAccessoryList()
						.get(i));
				ja.put(jo);
			}
			
		}
		json.put("flist", ja.toString());
		return json.toString();
	}

	/**
	 * 根据对象获取API参数
	 * 
	 * @param mo
	 * @param userId
	 * @return
	 */
	/*public static AjaxParams getParams(ManagedObjectEntity mo, int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId + "");
		params.put("catalogID", ManagedObject.catalogID + "");
		params.put(ManagedObject.MANAGED_OBJECT_ID, mo.getManagedObjectID()
				+ "");
		if (mo.getAddressOrUnit() != null && !mo.getAddressOrUnit().equals("")) {
			params.put(ManagedObject.ADDRESS_OR_UNIT, mo.getAddressOrUnit());
		}
		if (mo.getCardID() != null && !mo.getCardID().equals("")) {
			params.put(ManagedObject.CARD_ID, mo.getCardID());
		}
		params.put(ManagedObject.CAREER, mo.getCareer() + "");
		if (mo.getDomicilePlace() != null && !mo.getDomicilePlace().equals("")) {
			params.put(ManagedObject.DOMICILE_PLACE, mo.getDomicilePlace());
		}
		params.put(ManagedObject.GENDER, mo.getGender() + "");
		params.put(ManagedObject.MANAGED_CAUSE, mo.getManagedCause() + "");
		if (mo.getManagedTIme() != null && !mo.getManagedTIme().equals("")) {
			params.put(ManagedObject.MANAGED_TIME, mo.getManagedTIme());
		}
		if (mo.getResponsibilityPolice()!=0) {
			params.put(ManagedObject.RESPONSIBILITY_POLICE,
					mo.getResponsibilityPolice()+"");
		}
		if (mo.getUnitName() != null && !mo.getUnitName().equals("")) {
			// json.put(ManagedObject.REMARK, managedObject.getRemark());
			params.put(ManagedObject.UNIT_NAME, mo.getUnitName());
		}
		if (mo.getSpecificCauses() != null
				&& !mo.getSpecificCauses().equals("")) {
			params.put(ManagedObject.SPECIFIC_CAUSES, mo.getSpecificCauses());
		}
		if (mo.getLocation_id() != null) {
			JSONObject locationJo = new JSONObject();
			try {
				locationJo.put(Location.ELEVATION, mo.getLocation_id()
						.getELEVATION());
				locationJo.put(Location.LATITUDE, mo.getLocation_id()
						.getLATITUDE());
				locationJo.put(Location.LONGITUDE, mo.getLocation_id()
						.getLONGITUDE());
				locationJo.put("Time", mo.getLocation_id().getTIME());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			params.put(ForestPatrolWork.LOCATION_ID, locationJo.toString());
		}
		if (mo.getAccessoryList() != null && mo.getAccessoryList().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < mo.getAccessoryList().size(); i++) {
				JSONObject json = AttachmentDao.getJson(mo.getAccessoryList()
						.get(i));
				ja.put(json);
			}
			params.put("flist", ja.toString());
		}
		params.put("fileType", "1");
		return params;
	}*/

	/**
	 * 根据从服务器查询获取的json字符串获得ManagedObjectEntity对象集合
	 * 
	 * @param json
	 * @return ManagedObjectEntity对象集合
	 * @throws JSONException
	 */
	public List<ManagedObjectEntity> getObject(String json)
			throws JSONException {
		List<ManagedObjectEntity> moList = null;
		JSONObject jso = new JSONObject(json);
		if (jso.getInt("Error") == 0) {
			moList = new ArrayList<ManagedObjectEntity>();
			JSONArray ja = jso.getJSONArray("ManagedObjectList");
			for (int i = 0; i < ja.length(); i++) {
				ManagedObjectEntity mo = new ManagedObjectEntity();
				JSONObject jo = ja.getJSONObject(i);
				if (!jo.isNull(ManagedObject.MANAGED_OBJECT_ID)) {
					mo.setManagedObjectID(jo
							.getInt(ManagedObject.MANAGED_OBJECT_ID));
				}
				if (!jo.isNull(ManagedObject.ADDRESS_OR_UNIT)) {
					mo.setAddressOrUnit(jo
							.getString(ManagedObject.ADDRESS_OR_UNIT));
				}
				if (!jo.isNull(ManagedObject.CARD_ID)) {
					mo.setCardID(jo.getString(ManagedObject.CARD_ID));
				}
				if (!jo.isNull(ManagedObject.CAREER)
						&& !jo.getString(ManagedObject.CAREER).equals("")) {
					mo.setCareer(jo.getInt(ManagedObject.CAREER));
				}
				if (!jo.isNull(ManagedObject.DOMICILE_PLACE)) {
					mo.setDomicilePlace(jo
							.getString(ManagedObject.DOMICILE_PLACE));
				}
				if (!jo.isNull(ManagedObject.GENDER)
						&& !jo.getString(ManagedObject.GENDER).equals("")) {
					mo.setGender(jo.getInt(ManagedObject.GENDER));
				}
				if (!jo.isNull(ManagedObject.MANAGED_CAUSE)
						&& !jo.getString(ManagedObject.MANAGED_CAUSE)
								.equals("")) {
					mo.setManagedCause(jo.getInt(ManagedObject.MANAGED_CAUSE));
				}
				if (!jo.isNull(ManagedObject.MANAGED_TIME)) {
					mo.setManagedTIme(jo.getString(ManagedObject.MANAGED_TIME));
				}
				if (!jo.isNull(ManagedObject.UNIT_NAME)) {
					mo.setUnitName(jo.getString(ManagedObject.UNIT_NAME));
				}
				if (!jo.isNull(ManagedObject.RESPONSIBILITY_POLICE)) {
					mo.setResponsibilityPolice(jo
							.getInt(ManagedObject.RESPONSIBILITY_POLICE));
				}
				// if (!jo.isNull(ManagedObject.REMARK)) {
				// mo.setRemark(jo.getString(ManagedObject.REMARK));
				// }
				if (!jo.isNull(ManagedObject.SPECIFIC_CAUSES)) {
					mo.setSpecificCauses(jo
							.getString(ManagedObject.SPECIFIC_CAUSES));
				}
				if (!jo.isNull(ManagedObject.LOCATION)) {
					JSONObject joLocation = jo
							.getJSONObject(ManagedObject.LOCATION);
					Loaction l = new Loaction();
					l.setLONGITUDE(joLocation.getDouble(Location.LONGITUDE));
					l.setLATITUDE(joLocation.getDouble(Location.LATITUDE));
					l.setELEVATION(joLocation.getDouble(Location.ELEVATION));
					l.setTIME(joLocation.getString("Time"));
					mo.setLocation_id(l);
				}
				if (!jo.isNull("affix") && !jo.getString("affix").equals("")) {
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

					mo.setAccessoryList(accessorys);
				}
				moList.add(mo);
			}
		}
		return moList;
	}
}
