package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.TemporaryResidentPopulation;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.Location;
import db.Database.TransientRegister;

/**
 * 辖区暂住人口登记
 * 
 * @author ZhengTiantian
 */
public class TransientRegisterDao {

	private ForestDatabaseHelper dbHelper;

	private Context context;

	public TransientRegisterDao(Context context) {
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	/*
	 * 插入数据
	 */
	public boolean insertInfo(TemporaryResidentPopulation trp) {
		boolean flag = false;
		int registerId = -1;
		LocationDao lDao = new LocationDao(context);
		int locationId = lDao.insertInfo(trp.getLoaction());
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			if (locationId != -1) {
				ContentValues contentValues = new ContentValues();
				contentValues.put(TransientRegister.LOCATION_ID, locationId);
				contentValues.put(TransientRegister.NOTE, trp.getTrpLegend());
				contentValues.put(TransientRegister.TRANSIENT_REGISTER_TIME,
						trp.getTime());
				contentValues.put(TransientRegister.TRANSIENT_REGISTER_NAME,
						trp.getName());
				contentValues.put(TransientRegister.TRANSIENT_REGISTER_SEX,
						trp.getSex());
				contentValues.put(
						TransientRegister.TRANSIENT_REGISTER_BIRTHDAY,
						trp.getBirthday());
				contentValues.put(TransientRegister.TRANSIENT_REGISTER_ADDRESS,
						trp.getTemporaryAddress());
				contentValues.put(TransientRegister.TRANSIENT_REGISTER_WORK,
						trp.getIndustry());
				contentValues.put(
						TransientRegister.TRANSIENT_REGISTER_RESIDENCE,
						trp.getRegisteredAddress());
				contentValues.put(
						TransientRegister.TRANSIENT_CONTACT_INFORMATION,
						trp.getContacts());
				contentValues.put(TransientRegister.TRANSIENT_ID_CARD,
						trp.getIdCardNumber());
				contentValues.put(TransientRegister.TRANSIENT_ARRIVE_TIME,
						trp.getArrivalTime());
				contentValues.put(TransientRegister.TRANSIENT_LEAVE_TIME,
						trp.getDepartureTime());

				long ok = db.insert(
						TransientRegister.TRANSIENT_REGISTER_TABLE_NAME, null,
						contentValues);

				if (-1 != ok) {
					flag = true;
				}

				String sql = "SELECT MAX("
						+ TransientRegister.TRANSIENT_REGISTER_ID + ") FROM "
						+ TransientRegister.TRANSIENT_REGISTER_TABLE_NAME;

				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					registerId = cursor.getInt(0);
				}
				db.setTransactionSuccessful();
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			db.endTransaction();
			if (cursor != null) {
				cursor.close();
			}

		}
		if (trp.getTrpAccessorys() != null && trp.getTrpAccessorys().size() > 0) {
			AttachmentDao dao = new AttachmentDao(context);
			for (int i = 0; i < trp.getTrpAccessorys().size(); i++) {
				trp.getTrpAccessorys()
						.get(i)
						.setTableId(
								TransientRegister.TRANSIENT_REGISTER_TABLE_ID);
				trp.getTrpAccessorys().get(i).setRowId(registerId);
				flag = dao.insertInfo(trp.getTrpAccessorys().get(i));
			}
		}
		return flag;
	}

	/**
	 * 删表
	 */
	public boolean delTable(String transientId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db.delete(
					TransientRegister.TRANSIENT_REGISTER_TABLE_NAME,
					TransientRegister.TRANSIENT_REGISTER_ID + "=?",
					new String[] { transientId });
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
	public ArrayList<TemporaryResidentPopulation> getAllInfos() {
		ArrayList<TemporaryResidentPopulation> infos = new ArrayList<TemporaryResidentPopulation>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;

		try {

			String sql = "select * from "
					+ TransientRegister.TRANSIENT_REGISTER_TABLE_NAME
					+ " Left join " + Location.LOCATION_TABLE_NAME + " where "
					+ TransientRegister.TRANSIENT_REGISTER_TABLE_NAME + "."
					+ TransientRegister.LOCATION_ID + " = "
					+ Location.LOCATION_TABLE_NAME + "." + Location.LOCATION_ID;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);

			while (cursor.moveToNext()) {

				TemporaryResidentPopulation info = new TemporaryResidentPopulation();
				info.setTrpId(cursor.getInt(cursor
						.getColumnIndex(TransientRegister.TRANSIENT_REGISTER_ID)));
				info.setTime(cursor.getString(cursor
						.getColumnIndex(TransientRegister.TRANSIENT_REGISTER_TIME)));
				info.setTrpLegend(cursor.getString(cursor
						.getColumnIndex(TransientRegister.NOTE)));
				info.setName(cursor.getString(cursor
						.getColumnIndex(TransientRegister.TRANSIENT_REGISTER_NAME)));
				info.setSex(cursor.getInt(cursor
						.getColumnIndex(TransientRegister.TRANSIENT_REGISTER_SEX)));
				info.setBirthday(cursor.getString(cursor
						.getColumnIndex(TransientRegister.TRANSIENT_REGISTER_BIRTHDAY)));
				info.setTemporaryAddress(cursor.getString(cursor
						.getColumnIndex(TransientRegister.TRANSIENT_REGISTER_ADDRESS)));
				info.setRegisteredAddress(cursor.getString(cursor
						.getColumnIndex(TransientRegister.TRANSIENT_REGISTER_RESIDENCE)));
				info.setIndustry(cursor.getString(cursor
						.getColumnIndex(TransientRegister.TRANSIENT_REGISTER_WORK)));
				info.setIdCardNumber(cursor.getString(cursor
						.getColumnIndex(TransientRegister.TRANSIENT_ID_CARD)));
				info.setContacts(cursor.getString(cursor
						.getColumnIndex(TransientRegister.TRANSIENT_CONTACT_INFORMATION)));
				info.setArrivalTime(cursor.getString(cursor
						.getColumnIndex(TransientRegister.TRANSIENT_ARRIVE_TIME)));
				info.setDepartureTime(cursor.getString(cursor
						.getColumnIndex(TransientRegister.TRANSIENT_LEAVE_TIME)));

				Loaction l = new Loaction();
				l.setLocation_id(cursor.getInt(cursor
						.getColumnIndex(Location.LOCATION_ID)));
				l.setELEVATION(cursor.getDouble(cursor
						.getColumnIndex(Location.ELEVATION)));
				l.setLATITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LATITUDE)));
				l.setLONGITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LONGITUDE)));

				info.setLoaction(l);
				infos.add(info);
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
			int rowId = infos.get(i).getTrpId();
			aDao = new AttachmentDao(context);
			ArrayList<Accessory> list = aDao.getInfosById(
					TransientRegister.TRANSIENT_REGISTER_TABLE_ID, rowId);
			infos.get(i).setTrpAccessorys(list);
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
		Cursor cursor=null;
		int count = 0;
		String sql = "SELECT COUNT (*) FROM "
				+ TransientRegister.TRANSIENT_REGISTER_TABLE_NAME;

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

	/*
	 * 根据对象获取JSON
	 */
	public static String getJson(TemporaryResidentPopulation tr, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("userId", userId);
			jo.put("tableId", TransientRegister.TRANSIENT_REGISTER_TABLE_ID);
			jo.put(TransientRegister.TRANSIENT_REGISTER_TIME, tr.getTime());
			jo.put(TransientRegister.NOTE, tr.getTrpLegend());
			jo.put(TransientRegister.TRANSIENT_REGISTER_NAME, tr.getName());
			jo.put(TransientRegister.TRANSIENT_REGISTER_SEX, tr.getSex());
			jo.put(TransientRegister.TRANSIENT_REGISTER_BIRTHDAY,
					tr.getBirthday());
			jo.put(TransientRegister.TRANSIENT_REGISTER_ADDRESS,
					tr.getTemporaryAddress());
			jo.put(TransientRegister.TRANSIENT_REGISTER_WORK, tr.getIndustry());
			jo.put(TransientRegister.TRANSIENT_REGISTER_RESIDENCE,
					tr.getRegisteredAddress());
			jo.put(TransientRegister.TRANSIENT_CONTACT_INFORMATION,
					tr.getContacts());
			jo.put(TransientRegister.TRANSIENT_ID_CARD, tr.getIdCardNumber());
			jo.put(TransientRegister.TRANSIENT_ARRIVE_TIME, tr.getArrivalTime());
			jo.put(TransientRegister.TRANSIENT_LEAVE_TIME,
					tr.getDepartureTime());

			JSONObject joLocation = new JSONObject();
			joLocation.put(Location.ELEVATION, tr.getLoaction().getELEVATION());
			joLocation.put(Location.LATITUDE, tr.getLoaction().getLATITUDE());
			joLocation.put(Location.LONGITUDE, tr.getLoaction().getLONGITUDE());

			jo.put(TransientRegister.LOCATION_ID, joLocation.toString());

			JSONArray ja = new JSONArray();
			if (tr.getTrpAccessorys() != null
					&& tr.getTrpAccessorys().size() > 0) {
				for (int i = 0; i < tr.getTrpAccessorys().size(); i++) {
					JSONObject json = AttachmentDao.getJson(tr
							.getTrpAccessorys().get(i));
					ja.put(json);
				}
			}
			jo.put("flist", ja.toString());
			jo.put("fileType", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		JSONObject job = new JSONObject();
//		job.put("Transient_Register_Table_Name", jo);
		return jo.toString();

	}

	/**
	 * 根据对象获取API参数
	 * 
	 * @param trp
	 * @param userId
	 * @return
	 */
	/*public static AjaxParams getParams(TemporaryResidentPopulation trp,
			int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId + "");
		params.put("tableId", TransientRegister.TRANSIENT_REGISTER_TABLE_ID
				+ "");
		if (trp.getTime() != null && !trp.getTime().equals("")) {
			params.put(TransientRegister.TRANSIENT_REGISTER_TIME, trp.getTime());
		}
		if (trp.getTrpLegend() != null && !trp.getTrpLegend().equals("")) {
			params.put(TransientRegister.NOTE, trp.getTrpLegend());
		}
		if (trp.getName() != null && !trp.getName().equals("")) {
			params.put(TransientRegister.TRANSIENT_REGISTER_NAME, trp.getName());
		}
		if (trp.getSex() != 0) {
			params.put(TransientRegister.TRANSIENT_REGISTER_SEX, trp.getSex()
					+ "");
		}
		if (trp.getBirthday() != null && !trp.getBirthday().equals("")) {
			params.put(TransientRegister.TRANSIENT_REGISTER_BIRTHDAY,
					trp.getBirthday());
		}
		if (trp.getTemporaryAddress() != null
				&& !trp.getTemporaryAddress().equals("")) {
			params.put(TransientRegister.TRANSIENT_REGISTER_ADDRESS,
					trp.getTemporaryAddress());
		}
		if (trp.getIndustry() != null && !trp.getIndustry().equals("")) {
			params.put(TransientRegister.TRANSIENT_REGISTER_WORK,
					trp.getIndustry());
		}
		if (trp.getRegisteredAddress() != null
				&& !trp.getRegisteredAddress().equals("")) {
			params.put(TransientRegister.TRANSIENT_REGISTER_RESIDENCE,
					trp.getRegisteredAddress());
		}
		if (trp.getContacts() != null && !trp.getContacts().equals("")) {
			params.put(TransientRegister.TRANSIENT_CONTACT_INFORMATION,
					trp.getContacts());
		}
		if (trp.getIdCardNumber() != null && !trp.getIdCardNumber().equals("")) {
			params.put(TransientRegister.TRANSIENT_ID_CARD,
					trp.getIdCardNumber());
		}
		if (trp.getArrivalTime() != null && !trp.getArrivalTime().equals("")) {
			params.put(TransientRegister.TRANSIENT_ARRIVE_TIME,
					trp.getArrivalTime());
		}
		if (trp.getDepartureTime() != null
				&& !trp.getDepartureTime().equals("")) {
			params.put(TransientRegister.TRANSIENT_LEAVE_TIME,
					trp.getDepartureTime());
		}
		if (trp.getLoaction() != null) {
			JSONObject joLocation = new JSONObject();
			try {
				joLocation.put(Location.ELEVATION, trp.getLoaction()
						.getELEVATION());
				joLocation.put(Location.LATITUDE, trp.getLoaction()
						.getLATITUDE());
				joLocation.put(Location.LONGITUDE, trp.getLoaction()
						.getLONGITUDE());
				joLocation.put(Location.TIME, trp.getLoaction().getTIME());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			params.put(TransientRegister.LOCATION_ID, joLocation.toString());
		}
		if (trp.getTrpAccessorys() != null && trp.getTrpAccessorys().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < trp.getTrpAccessorys().size(); i++) {
				JSONObject json = AttachmentDao.getJson(trp.getTrpAccessorys()
						.get(i));
				ja.put(json);
			}
			params.put("flist", ja.toString());
		}
		params.put("fileType", "1");
		return params;
	}
*/
}
