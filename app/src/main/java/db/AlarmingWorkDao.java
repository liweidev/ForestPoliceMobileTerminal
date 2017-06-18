package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.AlarmEducationEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.AlarmOpinionEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.AlarmPositionEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.AlarmingEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.PoliceHeadEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.AlarmingWork;
import db.Database.Location;
import db.Database.Receive_DisposeAlarm;

/**
 * 处警信息登记
 * 
 * @author xingyimin
 * 
 */
public class AlarmingWorkDao {
	private ForestDatabaseHelper dbHelper;

	private static Context context;

	public AlarmingWorkDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	/*
	 * 插入
	 */
	public boolean insertInfo(AlarmingEntity pa) {

		boolean flag = false;
		
		int alarmingWorkId = -1;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(AlarmingWork.ALARM_ID,
					pa.getAlarmId());
			contentValues.put(AlarmingWork.STATE_ID,
					pa.getStateId());
			contentValues.put(AlarmingWork.INSTRUCTION_TIME,
					pa.getReceivedTime());
			contentValues.put(AlarmingWork.TIME_TO_REACH, pa.getArrivalsTime());
			contentValues.put(AlarmingWork.CRIMES_NAME, pa.getName());
			contentValues.put(AlarmingWork.CRIMES_SEX, pa.getSex());
			contentValues.put(AlarmingWork.CRIMES_AGE, pa.getAge());
			if (null != pa.getEducation()) {
				contentValues.put(AlarmingWork.CRIMES_EDUCATION, pa
						.getEducation().getEducationID());
			}
			contentValues.put(AlarmingWork.CRIMES_UNIT, pa.getWorkplace());
			contentValues
					.put(AlarmingWork.CRIMES_ADD_OR_UNIT, pa.getAddressZ());
			contentValues.put(AlarmingWork.CRIMES_UNIT_NAME,
					pa.getCompanyName());
			contentValues.put(AlarmingWork.CRIMES_UNIT_ADD, pa.getAddressD());
			contentValues.put(AlarmingWork.LEGAL_PERSON, pa.getLegal());
			if (pa.getPosition() != null) {
				contentValues.put(AlarmingWork.DUTY, pa.getPosition()
						.getPositionID());
			}

			contentValues.put(AlarmingWork.PIB_RIEF_CASE, pa.getBriefCase());
			if (pa.getOpinion() != null) {
				contentValues.put(AlarmingWork.AP_ADVICE_ID, pa.getOpinion()
						.getOpinionID());
			}
			if(pa.getLoaction()!=null){
				contentValues.put(AlarmingWork.ELEVATION, pa.getLoaction()
						.getELEVATION());
				contentValues.put(AlarmingWork.LONGITUDE, pa.getLoaction()
						.getLONGITUDE());
				contentValues.put(AlarmingWork.LATITUDE, pa.getLoaction()
						.getLATITUDE());
				contentValues.put(AlarmingWork.TIME, pa.getLoaction()
						.getTIME());
			}
			
			if (pa.getApprovalPeople() != null) {
				contentValues.put(AlarmingWork.APPROVALPEOPLEID, pa
						.getApprovalPeople().getPeoplePoliceID());
			}
			contentValues.put(AlarmingWork.REMARK, pa.getRemark());

			long ok = db.insert(AlarmingWork.ALARMING_WORK_TABLE_NAME, null,
					contentValues);
			// System.out.println("insert is complete..." + ok);
			if (-1 != ok) {
				flag = true;
			}
			String sql = "SELECT MAX(" + AlarmingWork.ALARMING_WORK_ID
					+ ") FROM " + AlarmingWork.ALARMING_WORK_TABLE_NAME;
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				alarmingWorkId = cursor.getInt(0);
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
		if (pa.getAccessory() != null && pa.getAccessory().size() > 0) {
			AttachmentDao dao = new AttachmentDao(context);
			for (int i = 0; i < pa.getAccessory().size(); i++) {
				pa.getAccessory().get(i)
						.setTableId(AlarmingWork.ALARMING_WORK_TABLE_ID);
				pa.getAccessory().get(i).setRowId(alarmingWorkId);
				flag = dao.insertInfo(pa.getAccessory().get(i));
			}
		}
		return flag;

	}

	/**
	 * 删表
	 */
	public boolean delTable(String alarmingId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db.delete(AlarmingWork.ALARMING_WORK_TABLE_NAME,
					AlarmingWork.ALARMING_WORK_ID + "=?",
					new String[] { alarmingId });
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
	public ArrayList<AlarmingEntity> getAllInfos() {
		ArrayList<AlarmingEntity> infos = new ArrayList<AlarmingEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		try {

			String sql = "select * from "
					+ AlarmingWork.ALARMING_WORK_TABLE_NAME;
			if (ActivityUtils.ISDEBUG) {
				System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				AlarmingEntity info = new AlarmingEntity();
				info.setAlarmId(cursor.getInt(cursor
						.getColumnIndex(AlarmingWork.ALARM_ID)));
				info.setStateId(cursor.getInt(cursor
						.getColumnIndex(AlarmingWork.STATE_ID)));
				info.setId(cursor.getInt(cursor
						.getColumnIndex(AlarmingWork.ALARMING_WORK_ID)));
				info.setReceivedTime(cursor.getString(cursor
						.getColumnIndex(AlarmingWork.INSTRUCTION_TIME)));
				info.setArrivalsTime(cursor.getString(cursor
						.getColumnIndex(AlarmingWork.TIME_TO_REACH)));
				info.setName(cursor.getString(cursor
						.getColumnIndex(AlarmingWork.CRIMES_NAME)));
				info.setSex(cursor.getString(cursor
						.getColumnIndex(AlarmingWork.CRIMES_SEX)));
				info.setAge(cursor.getString(cursor
						.getColumnIndex(AlarmingWork.CRIMES_AGE)));
				AlarmEducationEntity Education=new AlarmEducationEntity();
				Education.setEducationID(cursor.getInt(cursor
						.getColumnIndex(AlarmingWork.CRIMES_EDUCATION)));
				info.setEducation(Education);
				info.setWorkplace(cursor.getString(cursor
						.getColumnIndex(AlarmingWork.CRIMES_UNIT)));
				info.setAddressZ(cursor.getString(cursor
						.getColumnIndex(AlarmingWork.CRIMES_ADD_OR_UNIT)));
				info.setCompanyName(cursor.getString(cursor
						.getColumnIndex(AlarmingWork.CRIMES_UNIT_NAME)));

				info.setAddressD(cursor.getString(cursor
						.getColumnIndex(AlarmingWork.CRIMES_UNIT_ADD)));
				AlarmPositionEntity Position=new AlarmPositionEntity();
				Position.setPositionID(cursor.getInt(cursor
						.getColumnIndex(AlarmingWork.DUTY)));
				info.setPosition(Position);
				info.setLegal(cursor.getString(cursor
						.getColumnIndex(AlarmingWork.LEGAL_PERSON)));
				info.setBriefCase(cursor.getString(cursor
						.getColumnIndex(AlarmingWork.PIB_RIEF_CASE)));
				AlarmOpinionEntity opinion=new AlarmOpinionEntity();
				opinion.setOpinionID(cursor.getInt(cursor
						.getColumnIndex(AlarmingWork.AP_ADVICE_ID)));
				info.setOpinion(opinion);
				PoliceHeadEntity phe=new PoliceHeadEntity();
				phe.setPeoplePoliceID(cursor.getInt(cursor
						.getColumnIndex(AlarmingWork.APPROVALPEOPLEID)));
				info.setApprovalPeople(phe);
				info.setRemark(cursor.getString(cursor
						.getColumnIndex(AlarmingWork.REMARK)));
				Loaction lt=new Loaction();
				lt.setLONGITUDE(cursor.getDouble(cursor
						.getColumnIndex(AlarmingWork.LONGITUDE)));
				lt.setLATITUDE(cursor.getDouble(cursor
						.getColumnIndex(AlarmingWork.LATITUDE)));
				lt.setTIME(cursor.getString(cursor
						.getColumnIndex(AlarmingWork.TIME)));
				lt.setELEVATION(cursor.getDouble(cursor
						.getColumnIndex(AlarmingWork.ELEVATION)));
				info.setLoaction(lt);
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
			int rowid = infos.get(i).getId();
			dao = new AttachmentDao(context);
			ArrayList<Accessory> list = dao
					.getInfosById(
							AlarmingWork.ALARMING_WORK_TABLE_ID,
							rowid);
			infos.get(i).setAccessory(list);
			
		}
		return infos;
	}

	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM "
				+ AlarmingWork.ALARMING_WORK_TABLE_NAME;

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
	public static String getJson(AlarmingEntity pa, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("DeviceSN", ActivityUtils.getDeviceID(context));
			jo.put(Receive_DisposeAlarm.ALARM_ID, pa.getAlarmId());
			jo.put(Receive_DisposeAlarm.RECEIVED_TIME, pa.getReceivedTime()
					.toString());
			jo.put(Receive_DisposeAlarm.ARRIVALS_TIME, pa.getArrivalsTime());
			jo.put(Receive_DisposeAlarm.NAME, pa.getName());
			jo.put(Receive_DisposeAlarm.AGE, pa.getAge());
			jo.put(Receive_DisposeAlarm.SEX, pa.getSex());
			jo.put(Receive_DisposeAlarm.EDUCATION, pa.getEducation().getEducationID()+"");
			jo.put(Receive_DisposeAlarm.WORKPLACE, pa.getWorkplace());
			jo.put(Receive_DisposeAlarm.ADDRESS_Z, pa.getAddressZ());
			jo.put(Receive_DisposeAlarm.COMPANY_NAME, pa.getCompanyName());
			jo.put(Receive_DisposeAlarm.ADDRESS_D, pa.getAddressD());
			jo.put(Receive_DisposeAlarm.LEGAL, pa.getLegal());
			jo.put(Receive_DisposeAlarm.POSITION, pa.getPosition().getPositionID()+"");
			jo.put(Receive_DisposeAlarm.BRIEF_CASE, pa.getBriefCase());
			jo.put(Receive_DisposeAlarm.TREATMENT_ADVICE, pa.getOpinion().getOpinionID());
			jo.put(Receive_DisposeAlarm.REMARK, pa.getRemark());
			if(pa.getApprovalPeople()!=null){
				jo.put(Receive_DisposeAlarm.APPROVAL_PEOPLE_ID, pa.getApprovalPeople().getPeoplePoliceID());
			}
			if(pa.getLoaction()!=null){
				jo.put(Location.ELEVATION, pa.getLoaction().getELEVATION());
				jo.put(Location.LATITUDE, pa.getLoaction().getLATITUDE());
				jo.put(Location.LONGITUDE, pa.getLoaction().getLONGITUDE());
				jo.put(Location.TIME, pa.getLoaction().getTIME());
			}
			JSONArray ja = new JSONArray();
			if (pa.getAccessory() != null
					&& pa.getAccessory().size() > 0) {
				for (int i = 0; i < pa.getAccessory().size(); i++) {
					JSONObject job = AttachmentDao.getJson(pa.getAccessory()
							.get(i));
					ja.put(job);
				}
				jo.put(Receive_DisposeAlarm.ACCESSORY, ja.toString());
			}
			jo.put(Receive_DisposeAlarm.STATE_ID, pa.getStateId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(jo.toString());
		return jo.toString();
	}
}
