package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.ReceiveAlarmEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.Location;
import db.Database.ReceiveAlarm;

/**
 * @author Liupeng 受警登记
 */
public class ReceiveAlarmDao {

	private ForestDatabaseHelper dbHelper;

	private Context context;
	/**
	 * 
	 */
	public ReceiveAlarmDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	/*
	 * 插入
	 */
	public boolean insertInfo(ReceiveAlarmEntity ra) {

		boolean flag = false;
		int receiveAlarmID = -1;
		LocationDao ldao = new LocationDao(context);
		int locationId = ldao.insertInfo(ra.getLoaction());

		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor=null;
		if (locationId != -1) {
			try {
				ContentValues contentValues = new ContentValues();
				contentValues.put(ReceiveAlarm.RECEIVINT_ALARM_DATE,
						ra.getReceivingAlarmDate());
				contentValues.put(ReceiveAlarm.RECEIVINT_ALARM_STAFF,
						ra.getReceivingAlarmStaff());
				contentValues.put(ReceiveAlarm.ALARM_MODE, ra.getAlarmMode());
				contentValues.put(ReceiveAlarm.ALARM_CONTENT,
						ra.getAlarmContent());
				contentValues.put(ReceiveAlarm.ALARM_OPINION_LEADER,
						ra.getAlarmOpinionLeaders());

				contentValues.put(ReceiveAlarm.ALARM_NAME, ra.getAlarmName());
				contentValues.put(ReceiveAlarm.ALARM_SEX, ra.getAlarmSex());
				contentValues.put(ReceiveAlarm.ALARM_AGE, ra.getAlarmAge());
				contentValues.put(ReceiveAlarm.ALARM_NATIONALITY,
						ra.getAlarmNationality());
				contentValues.put(ReceiveAlarm.ALARM_PHONE, ra.getAlarmPhone());
				contentValues.put(ReceiveAlarm.ALARM_ADD, ra.getAlarmAdd());
				contentValues.put(ReceiveAlarm.ALARM_IDCARD,
						ra.getAlarmIDCard());

				contentValues.put(ReceiveAlarm.INSTRUCTION_TIME,
						ra.getInstructionTime());
				contentValues.put(ReceiveAlarm.TIME_TO_REACH,
						ra.getTimeToReach());
				contentValues.put(ReceiveAlarm.ALARMING_COMMENT,
						ra.getSituationAndOpinion());
				contentValues.put(ReceiveAlarm.ALARMING_OPINION_LEADER,
						ra.getAlarmingOpinionLeaders());

				contentValues.put(ReceiveAlarm.PROCESSING_RESULT,
						ra.getProcessingResults());
				contentValues.put(ReceiveAlarm.TRANSFER, ra.getTransfer());
				contentValues.put(ReceiveAlarm.RECEIVE_TIME,
						ra.getReceiveTime());
				contentValues.put(ReceiveAlarm.CROWN_CASE_NO,
						ra.getCrownCaseNo());
				contentValues.put(ReceiveAlarm.ADMINISTRATIVE_CASE_NO,
						ra.getAdministrativeCaseNo());
				contentValues.put(ReceiveAlarm.LOCATION_ID, locationId);
				// contentValues.put( ReceiveAlarm.ATTACHEMENT,
				// ra.getFwaAccessory());

				long ok = db.insert(ReceiveAlarm.RECEIVE_ALARM_TABLE_NAME,
						null, contentValues);
				// System.out.println("insert is complete..." + ok);
				if (-1 != ok) {
					flag = true;
				}
				String sql = "SELECT MAX(" + ReceiveAlarm.RECEIVE_ALARM_ID
						+ ") FROM " + ReceiveAlarm.RECEIVE_ALARM_TABLE_NAME;
				cursor= db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					receiveAlarmID = cursor.getInt(0);
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
			if (ra.getRaAccessory() != null && ra.getRaAccessory().size() > 0) {
				AttachmentDao dao = new AttachmentDao(context);
				for (int i = 0; i < ra.getRaAccessory().size(); i++) {
					ra.getRaAccessory().get(i)
							.setTableId(ReceiveAlarm.RECEIVE_ALARM_TABLE_ID);
					ra.getRaAccessory().get(i).setRowId(receiveAlarmID);
					flag = dao.insertInfo(ra.getRaAccessory().get(i));
				}
			}
		}
		return flag;

	}

	/**
	 * 删表
	 */
	public boolean delTable(String alarmId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db.delete(ReceiveAlarm.RECEIVE_ALARM_TABLE_NAME,
					ReceiveAlarm.RECEIVE_ALARM_ID + "=?",
					new String[] { alarmId });
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
	public ArrayList<ReceiveAlarmEntity> getAllInfos() {
		ArrayList<ReceiveAlarmEntity> infos = new ArrayList<ReceiveAlarmEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor=null;
		try {

			String sql = "select * from "
					+ ReceiveAlarm.RECEIVE_ALARM_TABLE_NAME + " Left join "
					+ Location.LOCATION_TABLE_NAME + " where "
					+ ReceiveAlarm.RECEIVE_ALARM_TABLE_NAME + "."
					+ ReceiveAlarm.LOCATION_ID + " = "
					+ Location.LOCATION_TABLE_NAME + "." + Location.LOCATION_ID;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				ReceiveAlarmEntity info = new ReceiveAlarmEntity();
				info.setRaId(cursor.getInt(cursor
						.getColumnIndex(ReceiveAlarm.RECEIVE_ALARM_ID)));
				info.setAlarmAdd(cursor.getString(cursor
						.getColumnIndex(ReceiveAlarm.ALARM_ADD)));
				info.setAlarmAge(cursor.getString(cursor
						.getColumnIndex(ReceiveAlarm.ALARM_AGE)));
				info.setAlarmContent(cursor.getString(cursor
						.getColumnIndex(ReceiveAlarm.ALARM_CONTENT)));
				info.setAlarmIDCard(cursor.getString(cursor
						.getColumnIndex(ReceiveAlarm.ALARM_IDCARD)));
				info.setAlarmingOpinionLeaders(cursor.getString(cursor
						.getColumnIndex(ReceiveAlarm.ALARMING_OPINION_LEADER)));
				info.setAlarmMode(cursor.getInt(cursor
						.getColumnIndex(ReceiveAlarm.ALARM_MODE)));
				info.setAlarmName(cursor.getString(cursor
						.getColumnIndex(ReceiveAlarm.ALARM_NAME)));

				info.setAlarmNationality(cursor.getInt(cursor
						.getColumnIndex(ReceiveAlarm.ALARM_NATIONALITY)));
				info.setAlarmPhone(cursor.getString(cursor
						.getColumnIndex(ReceiveAlarm.ALARM_PHONE)));
				info.setAlarmOpinionLeaders(cursor.getString(cursor
						.getColumnIndex(ReceiveAlarm.ALARM_OPINION_LEADER)));
				info.setAlarmSex(cursor.getInt(cursor
						.getColumnIndex(ReceiveAlarm.ALARM_SEX)));

				info.setAdministrativeCaseNo(cursor.getString(cursor
						.getColumnIndex(ReceiveAlarm.ADMINISTRATIVE_CASE_NO)));
				info.setCrownCaseNo(cursor.getString(cursor
						.getColumnIndex(ReceiveAlarm.CROWN_CASE_NO)));
				info.setInstructionTime(cursor.getString(cursor
						.getColumnIndex(ReceiveAlarm.INSTRUCTION_TIME)));
				info.setProcessingResults(cursor.getString(cursor
						.getColumnIndex(ReceiveAlarm.PROCESSING_RESULT)));

				info.setReceiveTime(cursor.getString(cursor
						.getColumnIndex(ReceiveAlarm.RECEIVE_TIME)));
				info.setReceiveUnit(cursor.getString(cursor
						.getColumnIndex(ReceiveAlarm.TRANSFER)));
				info.setReceivingAlarmDate(cursor.getString(cursor
						.getColumnIndex(ReceiveAlarm.RECEIVINT_ALARM_DATE)));

				info.setReceivingAlarmStaff(cursor.getString(cursor
						.getColumnIndex(ReceiveAlarm.RECEIVINT_ALARM_STAFF)));

				info.setSituationAndOpinion(cursor.getString(cursor
						.getColumnIndex(ReceiveAlarm.ALARMING_COMMENT)));

				info.setTimeToReach(cursor.getString(cursor
						.getColumnIndex(ReceiveAlarm.TIME_TO_REACH)));

				// Accessory ac = new Accessory();
				// ac.setAccessoryPath(cursor.getString(cursor
				// .getColumnIndex(Attachment.FILE_PATH)));
				// ac.setaId(cursor.getInt(cursor
				// .getColumnIndex(Attachment.ATTACHMENT_ID)));
				// ac.setFileType(cursor.getString(cursor
				// .getColumnIndex(Attachment.FILE_TYPE)));
				// ac.setRowId(cursor.getInt(cursor
				// .getColumnIndex(Attachment.ROW_ID)));
				// ac.setTableId(cursor.getInt(cursor
				// .getColumnIndex(Attachment.TABLE_ID)));
				//
				// info.setAlarmSignature(ac);
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
		AttachmentDao dao = null;
		for (int i = 0; i < infos.size(); i++) {
			int rowid = infos.get(i).getRaId();
			dao = new AttachmentDao(context);
			ArrayList<Accessory> list = dao.getInfosById(
					ReceiveAlarm.RECEIVE_ALARM_TABLE_ID, rowid);
			infos.get(i).setRaAccessory(list);
		}

		return infos;
	}

	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM "
				+ ReceiveAlarm.RECEIVE_ALARM_TABLE_NAME;
		
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
	public static String getJson(ReceiveAlarmEntity ra, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("userId", userId);
			jo.put("tableId", ReceiveAlarm.RECEIVE_ALARM_TABLE_ID);
			jo.put(ReceiveAlarm.ALARM_ADD, ra.getAlarmAdd());
			jo.put(ReceiveAlarm.ALARM_AGE, ra.getAlarmAge());
			jo.put(ReceiveAlarm.ALARM_CONTENT, ra.getAlarmContent());
			jo.put(ReceiveAlarm.ALARM_IDCARD, ra.getAlarmIDCard());
			jo.put(ReceiveAlarm.ALARM_MODE, ra.getAlarmMode());
			jo.put(ReceiveAlarm.ALARM_NAME, ra.getAlarmName());
			jo.put(ReceiveAlarm.ALARM_NATIONALITY, ra.getAlarmNationality());
			jo.put(ReceiveAlarm.ALARM_OPINION_LEADER,
					ra.getAlarmingOpinionLeaders());
			jo.put(ReceiveAlarm.ALARM_PHONE, ra.getAlarmPhone());
			jo.put(ReceiveAlarm.ALARM_SEX, ra.getAlarmSex());
			// jo.put(ReceiveAlarm.ALARM_SIGNATURE, attachmentId);//报警人签字
			jo.put(ReceiveAlarm.ALARMING_COMMENT, ra.getSituationAndOpinion());
			// contentValues.put( ReceiveAlarm.ATTACHEMENT,
			// ra.getFwaAccessory());
			jo.put(ReceiveAlarm.ALARMING_OPINION_LEADER,
					ra.getAlarmingOpinionLeaders());
			jo.put(ReceiveAlarm.SIGNATURE_DATE, ra.getAlarmingSituationTime());
			jo.put(ReceiveAlarm.CROWN_CASE_NO, ra.getCrownCaseNo());
			jo.put(ReceiveAlarm.ADMINISTRATIVE_CASE_NO,
					ra.getAdministrativeCaseNo());
			jo.put(ReceiveAlarm.INSTRUCTION_TIME, ra.getInstructionTime());
			jo.put(ReceiveAlarm.PROCESSING_RESULT, ra.getProcessingResults());
			jo.put(ReceiveAlarm.RECEIVE_TIME, ra.getReceiveTime());
			jo.put(ReceiveAlarm.RECEIVINT_ALARM_DATE,
					ra.getReceivingAlarmDate());
			jo.put(ReceiveAlarm.RECEIVINT_ALARM_STAFF,
					ra.getReceivingAlarmStaff());
			jo.put(ReceiveAlarm.TIME_TO_REACH, ra.getTimeToReach());
			jo.put(ReceiveAlarm.TRANSFER, ra.getTransfer());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jo.toString();
	}
}
