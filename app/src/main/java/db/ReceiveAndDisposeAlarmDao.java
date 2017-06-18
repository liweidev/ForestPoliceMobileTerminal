package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.ReceiveAndDisposeAlarmEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.Location;
import db.Database.ReceiveAndDisposeAlarm;

/**
 * 接警及处警工作
 * 
 * @author Zhengtiantian
 * 
 */
public class ReceiveAndDisposeAlarmDao {

	private ForestDatabaseHelper dbHelper;
	private Context context;

	public ReceiveAndDisposeAlarmDao(Context context) {
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	/*
	 * 插入数据
	 */
	public boolean insertInfo(ReceiveAndDisposeAlarmEntity rada) {
		boolean flag = false;
		int radisposeId = -1;

		LocationDao lDao = new LocationDao(context);
		int locationId = lDao.insertInfo(rada.getLocation());
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();

		if (locationId > 0) {
			Cursor cursor = null;
			try {
				ContentValues contentValues = new ContentValues();
				contentValues.put(ReceiveAndDisposeAlarm.LOCATION_ID,
						locationId);
				contentValues.put(ReceiveAndDisposeAlarm.NOTE,
						rada.getRadLegend());
				contentValues.put(ReceiveAndDisposeAlarm.RECEIVE_ALARM_PERSON,
						rada.getReceivePerson());
				contentValues.put(ReceiveAndDisposeAlarm.RECEIVE_ALARM_TIME,
						rada.getReceiveTiem());
				contentValues.put(ReceiveAndDisposeAlarm.RECEIVE_ALARM_MODEL,
						rada.getReceiveModel());
				contentValues.put(ReceiveAndDisposeAlarm.GIVE_ALARM_NAME,
						rada.getReceiveName());
				contentValues.put(ReceiveAndDisposeAlarm.GIVE_ALARM_AGE,
						rada.getReceiveAge());
				contentValues.put(ReceiveAndDisposeAlarm.GIVE_ALARM_SEX,
						rada.getReceiveSex());
				contentValues.put(ReceiveAndDisposeAlarm.GIVE_ALARM_CONTACT,
						rada.getReceiveContact());
				contentValues.put(ReceiveAndDisposeAlarm.GIVE_ALARM_ID_CARD,
						rada.getReceiveIdCard());
				contentValues.put(ReceiveAndDisposeAlarm.GIVE_ALARM_ADDRESS,
						rada.getReceiveAddress());
				contentValues.put(ReceiveAndDisposeAlarm.GIVE_ALARM_CONTENT,
						rada.getReceiveContent());
				contentValues.put(ReceiveAndDisposeAlarm.OPINION_LEADER,
						rada.getOpinionLeader());
				contentValues.put(ReceiveAndDisposeAlarm.RECEIVE_ORDER_TIME,
						rada.getOrderTime());
				contentValues.put(ReceiveAndDisposeAlarm.ALARM_TIME,
						rada.getDisposeTime());
				contentValues.put(ReceiveAndDisposeAlarm.ARRIVE_SITE_TIME,
						rada.getArriveTime());
				contentValues.put(ReceiveAndDisposeAlarm.ALARM_PERSON,
						rada.getDisposePerson());
				contentValues.put(ReceiveAndDisposeAlarm.PARTS_NAME,
						rada.getPartsName());
				contentValues.put(ReceiveAndDisposeAlarm.PARTS_AGE,
						rada.getPartsAge());
				contentValues.put(ReceiveAndDisposeAlarm.PARTS_SEX,
						rada.getPartsSex());
				contentValues.put(ReceiveAndDisposeAlarm.PARTS_CULTURE,
						rada.getPartsCulture());
				contentValues.put(ReceiveAndDisposeAlarm.PARTS_WORK_UNIT,
						rada.getPartsUnit());
				contentValues.put(ReceiveAndDisposeAlarm.PARTS_HOME_ADDRESS,
						rada.getHomeAddress());
				contentValues.put(ReceiveAndDisposeAlarm.PARTS_UNIT_NAME,
						rada.getUnitName());
				contentValues.put(ReceiveAndDisposeAlarm.PARTS_UNIT_ADDRESS,
						rada.getUnitAddress());
				contentValues.put(ReceiveAndDisposeAlarm.PARTS_POST,
						rada.getPartsPost());
				contentValues.put(ReceiveAndDisposeAlarm.PARTS_LAW_PERSON,
						rada.getLawPerson());
				contentValues.put(ReceiveAndDisposeAlarm.FIRST_INSPECT,
						rada.getFirstSituation());
				contentValues.put(ReceiveAndDisposeAlarm.INSPECT_END_TIME,
						rada.getInendTime());
				contentValues.put(ReceiveAndDisposeAlarm.ALARM_POLICE_IDEA,
						rada.getPoliceIdea());
				contentValues.put(ReceiveAndDisposeAlarm.ALARM_IDEA_WRITE_TIME,
						rada.getWriteTime());
				contentValues.put(ReceiveAndDisposeAlarm.ALARM_AND_IDEA,
						rada.getAlarmIdea());
				contentValues.put(
						ReceiveAndDisposeAlarm.ALARM_RESPONSIBLE_IDEA,
						rada.getResponsibleIdea());
				contentValues.put(ReceiveAndDisposeAlarm.RESPONSIBLE_IDEA_TIME,
						rada.getResponsibleTime());
				contentValues.put(ReceiveAndDisposeAlarm.ALARM_RESULT,
						rada.getAlarmResult());
				contentValues.put(ReceiveAndDisposeAlarm.ACCEPT_UNIT,
						rada.getAcceptUnit());
				contentValues.put(ReceiveAndDisposeAlarm.ACCEPT_TIME,
						rada.getAcceptTime());
				contentValues.put(ReceiveAndDisposeAlarm.CRIMINAL_CASE_CODE,
						rada.getCriminalCase());
				contentValues.put(
						ReceiveAndDisposeAlarm.ADMINISTRATIVE_CASE_CODE,
						rada.getAdministrativeCase());
				contentValues.put(ReceiveAndDisposeAlarm.PRINCIPAL_INSTRUCT,
						rada.getInstructContent());
				contentValues.put(
						ReceiveAndDisposeAlarm.PRINCIPAL_INSTRUCT_TIME,
						rada.getInstructTime());
				contentValues.put(ReceiveAndDisposeAlarm.ALARM_NATION,
						rada.getAlarmNation());

				long ok = db
						.insert(ReceiveAndDisposeAlarm.RECEIVE_DISPOSE_ALARM_TABLE_NAME,
								null, contentValues);

				if (-1 != ok) {
					flag = true;
				}
				String sql = "SELECT MAX("
						+ ReceiveAndDisposeAlarm.RECEIVE_DISPOSE_ALARM_ID
						+ ") FROM "
						+ ReceiveAndDisposeAlarm.RECEIVE_DISPOSE_ALARM_TABLE_NAME;
				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					radisposeId = cursor.getInt(0);
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
			if (rada.getRadAccessory() != null
					&& rada.getRadAccessory().size() > 0) {
				AttachmentDao aDao = new AttachmentDao(context);
				for (int i = 0; i < rada.getRadAccessory().size(); i++) {
					rada.getRadAccessory()
							.get(i)
							.setTableId(
									ReceiveAndDisposeAlarm.RECEIVE_DISPOSE_ALARM_TABLE_ID);
					rada.getRadAccessory().get(i).setRowId(radisposeId);
					flag = aDao.insertInfo(rada.getRadAccessory().get(i));
				}
			}
		}

		return flag;
	}

	/*
	 * 删除表
	 */
	public boolean delTable(String radaId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db.delete(
					ReceiveAndDisposeAlarm.RECEIVE_DISPOSE_ALARM_TABLE_NAME,
					ReceiveAndDisposeAlarm.RECEIVE_DISPOSE_ALARM_ID + "=?",
					new String[] { radaId });
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

	/*
	 * 获取本地所有
	 */
	public ArrayList<ReceiveAndDisposeAlarmEntity> getAllInfos() {
		ArrayList<ReceiveAndDisposeAlarmEntity> infos = new ArrayList<ReceiveAndDisposeAlarmEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			String sql = "select * from "
					+ ReceiveAndDisposeAlarm.RECEIVE_DISPOSE_ALARM_TABLE_NAME
					+ " Left join " + Location.LOCATION_TABLE_NAME + " where "
					+ ReceiveAndDisposeAlarm.RECEIVE_DISPOSE_ALARM_TABLE_NAME
					+ "." + ReceiveAndDisposeAlarm.LOCATION_ID + " = "
					+ Location.LOCATION_TABLE_NAME + "." + Location.LOCATION_ID;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				ReceiveAndDisposeAlarmEntity info = new ReceiveAndDisposeAlarmEntity();
				info.setRadId(cursor.getInt(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.RECEIVE_DISPOSE_ALARM_ID)));
				info.setRadLegend(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.NOTE)));
				info.setReceivePerson(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.RECEIVE_ALARM_PERSON)));
				info.setReceiveTiem(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.RECEIVE_ALARM_TIME)));
				info.setReceiveModel(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.RECEIVE_ALARM_MODEL)));
				info.setReceiveName(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.GIVE_ALARM_NAME)));
				info.setReceiveAge(cursor.getInt(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.GIVE_ALARM_AGE)));
				info.setReceiveSex(cursor.getInt(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.GIVE_ALARM_SEX)));
				info.setReceiveContact(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.GIVE_ALARM_CONTACT)));
				info.setReceiveIdCard(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.GIVE_ALARM_ID_CARD)));
				info.setReceiveAddress(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.GIVE_ALARM_ADDRESS)));
				info.setReceiveContent(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.GIVE_ALARM_CONTENT)));
				info.setOpinionLeader(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.OPINION_LEADER)));
				info.setOrderTime(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.RECEIVE_ORDER_TIME)));
				info.setDisposeTime(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.ALARM_TIME)));
				info.setArriveTime(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.ARRIVE_SITE_TIME)));
				info.setDisposePerson(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.ALARM_PERSON)));
				info.setPartsName(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.PARTS_NAME)));
				info.setPartsAge(cursor.getInt(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.PARTS_AGE)));
				info.setPartsSex(cursor.getInt(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.PARTS_SEX)));
				info.setPartsCulture(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.PARTS_CULTURE)));
				info.setPartsUnit(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.PARTS_WORK_UNIT)));
				info.setHomeAddress(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.PARTS_HOME_ADDRESS)));
				info.setUnitName(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.PARTS_UNIT_NAME)));
				info.setUnitAddress(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.PARTS_UNIT_ADDRESS)));
				info.setPartsPost(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.PARTS_POST)));
				info.setLawPerson(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.PARTS_LAW_PERSON)));
				info.setFirstSituation(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.FIRST_INSPECT)));
				info.setInendTime(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.INSPECT_END_TIME)));
				info.setPoliceIdea(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.ALARM_POLICE_IDEA)));
				info.setWriteTime(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.ALARM_IDEA_WRITE_TIME)));
				info.setAlarmIdea(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.ALARM_AND_IDEA)));
				info.setResponsibleIdea(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.ALARM_RESPONSIBLE_IDEA)));
				info.setResponsibleTime(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.RESPONSIBLE_IDEA_TIME)));
				info.setAlarmResult(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.ALARM_RESULT)));
				info.setAcceptUnit(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.ACCEPT_UNIT)));
				info.setAcceptTime(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.ACCEPT_TIME)));
				info.setCriminalCase(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.CRIMINAL_CASE_CODE)));
				info.setAdministrativeCase(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.ADMINISTRATIVE_CASE_CODE)));
				info.setInstructContent(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.PRINCIPAL_INSTRUCT)));
				info.setInstructTime(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.PRINCIPAL_INSTRUCT_TIME)));
				info.setAlarmNation(cursor.getString(cursor
						.getColumnIndex(ReceiveAndDisposeAlarm.ALARM_NATION)));

				Loaction l = new Loaction();
				l.setLocation_id(cursor.getInt(cursor
						.getColumnIndex(Location.LOCATION_ID)));
				l.setELEVATION(cursor.getDouble(cursor
						.getColumnIndex(Location.ELEVATION)));
				l.setLATITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LATITUDE)));
				l.setLONGITUDE(cursor.getDouble(cursor
						.getColumnIndex(Location.LONGITUDE)));
				info.setLocation(l);

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
		AttachmentDao dao = null;
		for (int i = 0; i < infos.size(); i++) {
			int rowId = infos.get(i).getRadId();
			dao = new AttachmentDao(context);
			ArrayList<Accessory> list = dao.getInfosById(
					ReceiveAndDisposeAlarm.RECEIVE_DISPOSE_ALARM_TABLE_ID,
					rowId);
			infos.get(i).setRadAccessory(list);
		}
		return infos;
	}

	/*
	 * 获取数量
	 */
	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM "
				+ ReceiveAndDisposeAlarm.RECEIVE_DISPOSE_ALARM_TABLE_NAME;

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
	public static String getJson(ReceiveAndDisposeAlarmEntity rad, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("userId", userId);
			jo.put("tableId",
					ReceiveAndDisposeAlarm.RECEIVE_DISPOSE_ALARM_TABLE_ID);
			jo.put(ReceiveAndDisposeAlarm.NOTE, rad.getRadLegend());
			jo.put(ReceiveAndDisposeAlarm.RECEIVE_ALARM_PERSON,
					rad.getReceivePerson());
			jo.put(ReceiveAndDisposeAlarm.RECEIVE_ALARM_TIME,
					rad.getReceiveTiem());
			jo.put(ReceiveAndDisposeAlarm.RECEIVE_ALARM_MODEL,
					rad.getReceiveModel());
			jo.put(ReceiveAndDisposeAlarm.GIVE_ALARM_NAME, rad.getReceiveName());
			jo.put(ReceiveAndDisposeAlarm.GIVE_ALARM_AGE, rad.getReceiveAge());
			jo.put(ReceiveAndDisposeAlarm.GIVE_ALARM_SEX, rad.getReceiveSex());
			jo.put(ReceiveAndDisposeAlarm.GIVE_ALARM_CONTACT,
					rad.getReceiveContact());
			jo.put(ReceiveAndDisposeAlarm.GIVE_ALARM_ID_CARD,
					rad.getReceiveIdCard());
			jo.put(ReceiveAndDisposeAlarm.GIVE_ALARM_ADDRESS,
					rad.getReceiveAddress());
			jo.put(ReceiveAndDisposeAlarm.GIVE_ALARM_CONTENT,
					rad.getReceiveContent());
			jo.put(ReceiveAndDisposeAlarm.OPINION_LEADER,
					rad.getOpinionLeader());
			jo.put(ReceiveAndDisposeAlarm.RECEIVE_ORDER_TIME, rad
					.getOrderTime().toString());
			jo.put(ReceiveAndDisposeAlarm.ALARM_TIME, rad.getDisposeTime()
					.toString());
			jo.put(ReceiveAndDisposeAlarm.ARRIVE_SITE_TIME, rad.getArriveTime()
					.toString());
			jo.put(ReceiveAndDisposeAlarm.ALARM_PERSON, rad.getDisposePerson());
			jo.put(ReceiveAndDisposeAlarm.PARTS_NAME, rad.getPartsName());
			jo.put(ReceiveAndDisposeAlarm.PARTS_AGE, rad.getPartsAge());
			jo.put(ReceiveAndDisposeAlarm.PARTS_SEX, rad.getPartsSex());
			jo.put(ReceiveAndDisposeAlarm.PARTS_CULTURE, rad.getPartsCulture());
			jo.put(ReceiveAndDisposeAlarm.PARTS_WORK_UNIT, rad.getPartsUnit());
			jo.put(ReceiveAndDisposeAlarm.PARTS_HOME_ADDRESS,
					rad.getHomeAddress());
			jo.put(ReceiveAndDisposeAlarm.PARTS_UNIT_NAME, rad.getUnitName());
			jo.put(ReceiveAndDisposeAlarm.PARTS_UNIT_ADDRESS,
					rad.getUnitAddress());
			jo.put(ReceiveAndDisposeAlarm.PARTS_POST, rad.getPartsPost());
			jo.put(ReceiveAndDisposeAlarm.PARTS_LAW_PERSON, rad.getLawPerson());
			jo.put(ReceiveAndDisposeAlarm.FIRST_INSPECT,
					rad.getFirstSituation());
			jo.put(ReceiveAndDisposeAlarm.INSPECT_END_TIME, rad.getInendTime()
					.toString());
			jo.put(ReceiveAndDisposeAlarm.ALARM_POLICE_IDEA,
					rad.getPoliceIdea());
			jo.put(ReceiveAndDisposeAlarm.ALARM_IDEA_WRITE_TIME, rad
					.getWriteTime().toString());
			jo.put(ReceiveAndDisposeAlarm.ALARM_AND_IDEA, rad.getAlarmIdea());
			jo.put(ReceiveAndDisposeAlarm.ALARM_RESPONSIBLE_IDEA,
					rad.getResponsibleIdea());
			jo.put(ReceiveAndDisposeAlarm.RESPONSIBLE_IDEA_TIME, rad
					.getResponsibleTime().toString());
			jo.put(ReceiveAndDisposeAlarm.ALARM_RESULT, rad.getAlarmResult());
			jo.put(ReceiveAndDisposeAlarm.ACCEPT_UNIT, rad.getAcceptUnit());
			jo.put(ReceiveAndDisposeAlarm.ACCEPT_TIME, rad.getAcceptTime()
					.toString());
			jo.put(ReceiveAndDisposeAlarm.CRIMINAL_CASE_CODE,
					rad.getCriminalCase());
			jo.put(ReceiveAndDisposeAlarm.ADMINISTRATIVE_CASE_CODE,
					rad.getAdministrativeCase());
			jo.put(ReceiveAndDisposeAlarm.PRINCIPAL_INSTRUCT,
					rad.getInstructContent());
			jo.put(ReceiveAndDisposeAlarm.PRINCIPAL_INSTRUCT_TIME, rad
					.getInstructTime().toString());
			jo.put(ReceiveAndDisposeAlarm.ALARM_NATION, rad.getAlarmNation());

			JSONObject joLocation = new JSONObject();
			joLocation
					.put(Location.ELEVATION, rad.getLocation().getELEVATION());
			joLocation.put(Location.LATITUDE, rad.getLocation().getLATITUDE());
			joLocation
					.put(Location.LONGITUDE, rad.getLocation().getLONGITUDE());
			jo.put(ReceiveAndDisposeAlarm.LOCATION_ID, joLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jo.toString();

	}
}
