package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.AlarmEducationEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.AlarmEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.AlarmOpinionEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.AlarmPositionEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.AlarmStateEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.AlarmWayEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.AlarmingEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.AlarmingHistoryInforEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.AlarmingResultEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.NationEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.PoliceHeadEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import db.Database.Receive_DisposeAlarm;

/**
 * 新增接处警
 * 
 * @author xingyimin
 * 
 */
public class AlarmingDao {

	private ForestDatabaseHelper dbHelper;
	private Context context;
	private static AlarmingDao alarDao;

	public AlarmingDao(Context context) {
		this.context = context;
		this.dbHelper = new ForestDatabaseHelper(context);
	}

	public static AlarmingDao init(Context ct) {
		if (alarDao == null) {
			alarDao = new AlarmingDao(ct);
		}
		return alarDao;

	}

	/**
	 * 插入数据
	 * 
	 * @param alarm
	 * @return
	 */
	public boolean insertInfo(AlarmEntity alarm) {
		System.out.println("接处警信息---------->"+alarm);
		boolean flag = false;
		int alarmid = -1;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();

		Cursor cursor = null;
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(Receive_DisposeAlarm.ALARM_ID, alarm.getAlarmID());
			contentValues.put(Receive_DisposeAlarm.CASE_NUMBER, alarm.getNumber());
			contentValues.put(Receive_DisposeAlarm.ADDRESS, alarm.getAddress());
			contentValues.put(Receive_DisposeAlarm.TO_ALARM_MESSAGE,
					alarm.getToAlarmMessage());
			contentValues.put(Receive_DisposeAlarm.ALARM_TIME,
					alarm.getAlarmTime());
			if(alarm.getAlarmWay()!=null){
				contentValues.put(Receive_DisposeAlarm.ALARM_WAY_ID, alarm
						.getAlarmWay().getAlarmWayID());
			}
			if(alarm.getPolice()!=null){//存储接警民警的名字
				contentValues.put(Receive_DisposeAlarm.PEOPLE_POLICE_ID, alarm
						.getPolice().getPeoplePoliceID());
				contentValues.put(Receive_DisposeAlarm.PEOPLE_POLICE_NAME, alarm
						.getPolice().getPeoplePoliceName());
			}
			contentValues.put(Receive_DisposeAlarm.COPOLICE_ID, alarm
					.getCoPoliceID());
			contentValues.put(Receive_DisposeAlarm.TO_ALARM_NAME,
					alarm.getToAlarmName());
			contentValues.put(Receive_DisposeAlarm.TO_ALARM_AGE,
					alarm.getToAlarmAge());
			if(alarm.getNation()!=null){
				contentValues.put(Receive_DisposeAlarm.NATION_ID, alarm.getNation()
						.getNationID());
			}
			contentValues.put(Receive_DisposeAlarm.TO_ALARM_SEX,
					alarm.getToAlarmSex());
			contentValues.put(Receive_DisposeAlarm.CONTACT, alarm.getContact());
			contentValues.put(Receive_DisposeAlarm.CARD_NUMBER,
					alarm.getCardNumber());
			contentValues.put(Receive_DisposeAlarm.APPROVAL_PEOPLE_ID,
					alarm.getApprovalPeopleID());
			contentValues.put(Receive_DisposeAlarm.HOST_POLICE_ID,
					alarm.getHostPoliceID());
			contentValues.put(Receive_DisposeAlarm.COPOLICE_ID,
					alarm.getCoPoliceID());
			contentValues.put(Receive_DisposeAlarm.OPINION,
					alarm.getOpinion());
			if(alarm.getState()!=null){
				contentValues.put(Receive_DisposeAlarm.STATE_ID,
						alarm.getState().getStateID());
				contentValues.put(Receive_DisposeAlarm.ALARM_STATE,
						alarm.getState().getStateName());
			}
			if(alarm.getHistoryList()!=null&&alarm.getHistoryList().size()>0){
				AlarmingHistoryDao ahDao=new AlarmingHistoryDao(context);
				List<AlarmingHistoryInforEntity> list=alarm.getHistoryList();
				for (int i = 0; i < list.size(); i++) {
					list.get(i).setAlarmId(alarm.getAlarmID());
					ahDao.insertInfo(list.get(i));
				}
			}
				
			long ok = db.insert(
					Receive_DisposeAlarm.RECEIVE_DISPOSE_ALARM_TABLE_NAME,
					null, contentValues);

			if (-1 != ok) {
				flag = true;
			}
			String sql = "SELECT MAX("
					+ Receive_DisposeAlarm.RECEIVE_DISPOSE_ALARM_ID + ") FROM "
					+ Receive_DisposeAlarm.RECEIVE_DISPOSE_ALARM_TABLE_NAME;
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				alarmid = cursor.getInt(0);
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

		return flag;
	}
	/*
	 * 删除表
	 */
	public boolean delTable(String alarmId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db.delete(
					Receive_DisposeAlarm.RECEIVE_DISPOSE_ALARM_TABLE_NAME,
					Receive_DisposeAlarm.RECEIVE_DISPOSE_ALARM_ID + "=?",
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
	public boolean deAlarmID(String alarmId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db.delete(
					Receive_DisposeAlarm.RECEIVE_DISPOSE_ALARM_TABLE_NAME,
					Receive_DisposeAlarm.ALARM_ID + "=?",
					new String[] { alarmId });
			if (res > 0) {
				isOk = true;
				AlarmingHistoryDao ahdao=new AlarmingHistoryDao(context);
				ahdao.delFromAlarmID(Integer.parseInt(alarmId));
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
	public ArrayList<AlarmEntity> getAllInfos() {
		ArrayList<AlarmEntity> infos = new ArrayList<AlarmEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			String sql = "select * from "
					+ Receive_DisposeAlarm.RECEIVE_DISPOSE_ALARM_TABLE_NAME;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				AlarmEntity info = new AlarmEntity();
				info.setId(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.RECEIVE_DISPOSE_ALARM_ID)));
				info.setAlarmID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.ALARM_ID)));
				AlarmingHistoryDao ahDao=new AlarmingHistoryDao(context);
				List<AlarmingHistoryInforEntity> list=ahDao.getInfosById(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.ALARM_ID)));
				if(list!=null&&list.size()>0){
					info.setHistoryList(list);
				}
				info.setNumber(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.CASE_NUMBER)));
				info.setAddress(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.ADDRESS)));
				info.setToAlarmAge(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.TO_ALARM_AGE)));
				info.setToAlarmName(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.TO_ALARM_NAME)));
				info.setToAlarmSex(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.TO_ALARM_SEX)));
				info.setToAlarmMessage(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.TO_ALARM_MESSAGE)));
				AlarmWayEntity way = new AlarmWayEntity();
				way.setAlarmWayID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.ALARM_WAY_ID)));
				info.setAlarmWay(way);
				info.setContact(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.CONTACT)));
				PoliceHeadEntity police = new PoliceHeadEntity();
				police.setPeoplePoliceID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.PEOPLE_POLICE_ID)));
				police.setPeoplePoliceName(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.PEOPLE_POLICE_NAME)));
				info.setPolice(police);
				info.setApprovalPeopleID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.APPROVAL_PEOPLE_ID)));
				info.setHostPoliceID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.HOST_POLICE_ID)));
				info.setCoPoliceID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.COPOLICE_ID)));
				NationEntity nation = new NationEntity();
				nation.setNationID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.NATION_ID)));
				info.setNation(nation);
				info.setCardNumber(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.CARD_NUMBER)));
				info.setAlarmTime(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.ALARM_TIME)));
				AlarmStateEntity ase=new AlarmStateEntity();
				ase.setStateID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.STATE_ID)));
				ase.setStateName(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.ALARM_STATE)));
				info.setState(ase);
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
		return infos;
	}
	
	/*
	 * 获取限定审批状态的信息
	 */
	public ArrayList<AlarmEntity> getAllFromState(String alarmState) {
		ArrayList<AlarmEntity> infos = new ArrayList<AlarmEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			String sql = "select * from "
					+ Receive_DisposeAlarm.RECEIVE_DISPOSE_ALARM_TABLE_NAME+" where AlarmState='"+alarmState+"';";
			if (ActivityUtils.ISDEBUG) {
			System.out.println("------->"+sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				AlarmEntity info = new AlarmEntity();
				info.setId(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.RECEIVE_DISPOSE_ALARM_ID)));
				info.setAlarmID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.ALARM_ID)));
				AlarmingHistoryDao ahDao=new AlarmingHistoryDao(context);
				List<AlarmingHistoryInforEntity> list=new ArrayList<AlarmingHistoryInforEntity>();
				if(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.ALARM_ID))>0){
					list=ahDao.getInfosById(cursor.getInt(cursor
							.getColumnIndex(Receive_DisposeAlarm.ALARM_ID)));
				}
				if(list!=null&&list.size()>0){
					info.setHistoryList(list);
				}
				info.setNumber(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.CASE_NUMBER)));
				info.setAddress(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.ADDRESS)));
				info.setToAlarmAge(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.TO_ALARM_AGE)));
				info.setToAlarmName(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.TO_ALARM_NAME)));
				info.setToAlarmSex(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.TO_ALARM_SEX)));
				info.setToAlarmMessage(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.TO_ALARM_MESSAGE)));
				AlarmWayEntity way = new AlarmWayEntity();
				way.setAlarmWayID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.ALARM_WAY_ID)));
				info.setAlarmWay(way);
				info.setContact(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.CONTACT)));
				PoliceHeadEntity police = new PoliceHeadEntity();
				police.setPeoplePoliceID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.PEOPLE_POLICE_ID)));
				police.setPeoplePoliceName(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.PEOPLE_POLICE_NAME)));
				info.setPolice(police);
				info.setApprovalPeopleID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.APPROVAL_PEOPLE_ID)));
				info.setHostPoliceID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.HOST_POLICE_ID)));
				info.setCoPoliceID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.COPOLICE_ID)));
				NationEntity nation = new NationEntity();
				nation.setNationID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.NATION_ID)));
				info.setNation(nation);
				info.setCardNumber(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.CARD_NUMBER)));
				info.setAlarmTime(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.ALARM_TIME)));
				AlarmStateEntity ase=new AlarmStateEntity();
				ase.setStateID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.STATE_ID)));
				ase.setStateName(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.ALARM_STATE)));
				info.setState(ase);
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
				+ Receive_DisposeAlarm.RECEIVE_DISPOSE_ALARM_TABLE_NAME;

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
	 * 获取要同步的数量
	 */
	public int getSyncCount(String alarmState) {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM "
				+ Receive_DisposeAlarm.RECEIVE_DISPOSE_ALARM_TABLE_NAME+" where AlarmState='"+alarmState+"';";

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
	 * 根据接警对象获取JSON
	 */
	public static String getAlarmJson(AlarmEntity alarm) throws JSONException {
		JSONObject jo = new JSONObject();
		jo.put(Receive_DisposeAlarm.ADDRESS, alarm.getAddress());
		jo.put(Receive_DisposeAlarm.TO_ALARM_MESSAGE, alarm.getToAlarmMessage());
		jo.put(Receive_DisposeAlarm.ALARM_TIME, alarm.getAlarmTime());
		jo.put(Receive_DisposeAlarm.ALARM_WAY_ID, alarm.getAlarmWay()
				.getAlarmWayID());
		jo.put(Receive_DisposeAlarm.PEOPLE_POLICE_ID, alarm.getPolice()
				.getPeoplePoliceID());
		jo.put(Receive_DisposeAlarm.APPROVAL_PEOPLE_ID,
				alarm.getApprovalPeopleID());
		jo.put(Receive_DisposeAlarm.TO_ALARM_NAME, alarm.getToAlarmName());
		jo.put(Receive_DisposeAlarm.TO_ALARM_AGE, alarm.getToAlarmAge());
		jo.put(Receive_DisposeAlarm.NATION_ID, alarm.getNation().getNationID());
		jo.put(Receive_DisposeAlarm.TO_ALARM_SEX, alarm.getToAlarmSex());
		jo.put(Receive_DisposeAlarm.CONTACT, alarm.getContact());
		jo.put(Receive_DisposeAlarm.CARD_NUMBER, alarm.getCardNumber());
		jo.put(Receive_DisposeAlarm.STATE_ID, alarm.getState().getStateID());
		jo.put(Receive_DisposeAlarm.ALARM_STATE, alarm.getState().getStateName());
		return jo.toString();
	}
	/**
	 * 判断数据库中是否已存在此数据
	 * 存在 返回false
	 * 不存在返回 true
	 */
	public boolean query(String AlarmID){
		boolean flag=false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		String sql="SELECT * FROM "
				+ Receive_DisposeAlarm.RECEIVE_DISPOSE_ALARM_TABLE_NAME+" where AlarmID='"
				+AlarmID+"';";
		try{
			cursor=db.rawQuery(sql, null);
			if(cursor.moveToNext()){
				
			}else{
				flag=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			db.endTransaction();
			if (cursor != null) {
				cursor.close();
			}
		}
		return flag;
	}
	/**
	 * 根据AlarmID查询AlarmEntity对象
	 * 
	 */
	public AlarmEntity queryAlarmID(int alarmID){
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		String sql="SELECT * FROM "
				+ Receive_DisposeAlarm.RECEIVE_DISPOSE_ALARM_TABLE_NAME+" where AlarmID='"
				+alarmID+"';";
		AlarmEntity info=null;
		try{
			cursor=db.rawQuery(sql, null);
			if(cursor.moveToNext()){
				info= new AlarmEntity();
				info.setId(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.RECEIVE_DISPOSE_ALARM_ID)));
				info.setAlarmID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.ALARM_ID)));
				AlarmingHistoryDao ahDao=new AlarmingHistoryDao(context);
				List<AlarmingHistoryInforEntity> list=ahDao.getInfosById(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.ALARM_ID)));
				if(list!=null&&list.size()>0){
					info.setHistoryList(list);
				}
				info.setNumber(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.CASE_NUMBER)));
				info.setAddress(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.ADDRESS)));
				info.setToAlarmAge(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.TO_ALARM_AGE)));
				info.setToAlarmName(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.TO_ALARM_NAME)));
				info.setToAlarmSex(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.TO_ALARM_SEX)));
				info.setToAlarmMessage(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.TO_ALARM_MESSAGE)));
				AlarmWayEntity way = new AlarmWayEntity();
				way.setAlarmWayName(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.ALARM_WAY_ID)));
				info.setAlarmWay(way);
				info.setContact(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.CONTACT)));
				PoliceHeadEntity police = new PoliceHeadEntity();
				police.setPeoplePoliceID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.PEOPLE_POLICE_ID)));
				police.setPeoplePoliceName(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.PEOPLE_POLICE_NAME)));
				info.setPolice(police);
				info.setApprovalPeopleID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.APPROVAL_PEOPLE_ID)));
				info.setHostPoliceID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.HOST_POLICE_ID)));
				info.setCoPoliceID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.COPOLICE_ID)));
				NationEntity nation = new NationEntity();
				nation.setNationID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.NATION_ID)));
				info.setNation(nation);
				info.setCardNumber(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.CARD_NUMBER)));
				info.setAlarmTime(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.ALARM_TIME)));
				AlarmStateEntity ase=new AlarmStateEntity();
				ase.setStateID(cursor.getInt(cursor
						.getColumnIndex(Receive_DisposeAlarm.STATE_ID)));
				ase.setStateName(cursor.getString(cursor
						.getColumnIndex(Receive_DisposeAlarm.ALARM_STATE)));
				info.setState(ase);
				System.out.println("查询-----》"+info);
				return info;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			db.endTransaction();
			if (cursor != null) {
				cursor.close();
			}
			
		}
		return info;
	}
	/**
	 * 按照查询【按照民警ID查询、按照接警人时间状态查询】结果转成报警信息集合
	 * 
	 * @param json
	 * @return 报警信息集合
	 * @throws JSONException
	 */
	public List<AlarmEntity> getListObject(String json) throws JSONException {
		List<AlarmEntity> alarmingList = null;
		JSONObject jo = new JSONObject(json);
		int error = jo.getInt("Error");
		if (error == 0) {
			alarmingList = new ArrayList<AlarmEntity>();
			JSONArray ja = (JSONArray) jo.get("List");
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jsonResult = (JSONObject) ja.get(i);
				AlarmEntity alarm = new AlarmEntity();
				if (!jsonResult.isNull("AlarmID")) {
					alarm.setAlarmID(jsonResult.getInt("AlarmID"));
				}
				if (!jsonResult.isNull("CaseNumber")) {
					alarm.setNumber(jsonResult.getString("CaseNumber"));
				}
				if (!jsonResult.isNull("AlarmTime")) {
					alarm.setAlarmTime(jsonResult.getString("AlarmTime"));
				}
				if (!jsonResult.isNull("ApprovalPeopleID")) {
					alarm.setApprovalPeopleID(jsonResult
							.getInt("ApprovalPeopleID"));
				}
				if (!jsonResult.isNull("PeoplePolice")) {
					JSONObject jsonPolice = jsonResult
							.getJSONObject("PeoplePolice");
					PoliceHeadEntity alarmPolice = new PoliceHeadEntity();
					if (!jsonPolice
							.isNull(Receive_DisposeAlarm.PEOPLE_POLICE_ID)) {
						alarmPolice.setPeoplePoliceID(jsonPolice
								.getInt(Receive_DisposeAlarm.PEOPLE_POLICE_ID));
					}
					if (!jsonPolice
							.isNull(Receive_DisposeAlarm.PEOPLE_POLICE_NAME)) {
						alarmPolice
								.setPeoplePoliceName(jsonPolice
										.getString(Receive_DisposeAlarm.PEOPLE_POLICE_NAME));
					}
					alarm.setPolice(alarmPolice);
				}
				if (!jsonResult.isNull("ToAlarmName")) {
					alarm.setToAlarmName(jsonResult.getString("ToAlarmName"));
				}

				if (!jsonResult.isNull("AlarmState")) {
					JSONObject jAlarmState = jsonResult
							.getJSONObject("AlarmState");
					AlarmStateEntity alarmState = new AlarmStateEntity();
					if (!jAlarmState.isNull(Receive_DisposeAlarm.STATE_ID)) {
						alarmState.setStateID(jAlarmState
								.getInt(Receive_DisposeAlarm.STATE_ID));
					}
					if (!jAlarmState.isNull(Receive_DisposeAlarm.STATE_NAME)) {
						alarmState.setStateName(jAlarmState
								.getString(Receive_DisposeAlarm.STATE_NAME));
					}
					alarm.setState(alarmState);
				}
				// 处警信息
				if (!jsonResult.isNull("AlarmingInfo")) {
					JSONObject jsonAlarming = jsonResult
							.getJSONObject("AlarmingInfo");
					AlarmingEntity alarming = new AlarmingEntity();
					if (!jsonAlarming.isNull("ReceivedTime")) {
						alarming.setReceivedTime(jsonAlarming
								.getString("ReceivedTime"));
					}
					if (!jsonAlarming.isNull("ArrivalsTime")) {
						alarming.setArrivalsTime(jsonAlarming
								.getString("ArrivalsTime"));
					}
					if (!jsonAlarming.isNull("Name")) {
						alarming.setName(jsonAlarming.getString("Name"));
					}
					if (!jsonAlarming.isNull("Sex")) {
						alarming.setSex(jsonAlarming.getString("Sex"));
					}
					if (!jsonAlarming.isNull("Education")) {
						AlarmEducationEntity edu = new AlarmEducationEntity();
						edu.setEducationName(jsonAlarming
								.getString("Education"));
						alarming.setEducation(edu);
					}
					if (!jsonAlarming.isNull("Workplace")) {
						alarming.setWorkplace(jsonAlarming
								.getString("Workplace"));
					}
					if (!jsonAlarming.isNull("AddressZ")) {
						alarming.setAddressZ(jsonAlarming.getString("AddressZ"));
					}
					if (!jsonAlarming.isNull("CompanyName")) {
						alarming.setCompanyName(jsonAlarming
								.getString("CompanyName"));
					}
					if (!jsonAlarming.isNull("AddressD")) {
						alarming.setAddressD(jsonAlarming.getString("AddressD"));
					}
					if (!jsonAlarming.isNull("Legal")) {
						alarming.setLegal(jsonAlarming.getString("Legal"));
					}
					if (!jsonAlarming.isNull("Position")) {
						AlarmPositionEntity position = new AlarmPositionEntity();
						position.setPositionName(jsonAlarming
								.getString("Position"));
						alarming.setPosition(position);
					}
					if (!jsonAlarming.isNull("BriefCase")) {
						alarming.setBriefCase(jsonAlarming
								.getString("BriefCase"));
					}
					if (!jsonAlarming.isNull("TreatmentAdviceID")) {
						AlarmOpinionEntity opinion = new AlarmOpinionEntity();
						opinion.setOpinionID(Integer.parseInt(jsonAlarming
								.getString("TreatmentAdviceID")));
						alarming.setOpinion(opinion);
					}
					if (!jsonAlarming.isNull("Remark")) {
						alarming.setRemark(jsonAlarming.getString("Remark"));
					}
					// 审批人
					if (!jsonAlarming.isNull("ApprovalPeople")) {
						JSONObject jsonPolice = jsonAlarming
								.getJSONObject("ApprovalPeople");
						PoliceHeadEntity police = new PoliceHeadEntity();
						if (!jsonPolice
								.isNull(Receive_DisposeAlarm.PEOPLE_POLICE_ID)) {
							police.setPeoplePoliceID(jsonPolice
									.getInt(Receive_DisposeAlarm.PEOPLE_POLICE_ID));
						}
						if (!jsonPolice
								.isNull(Receive_DisposeAlarm.PEOPLE_POLICE_NAME)) {
							police.setPeoplePoliceName(jsonPolice
									.getString(Receive_DisposeAlarm.PEOPLE_POLICE_NAME));
						}
						alarming.setApprovalPeople(police);
					}
					// 定位
					Loaction location = new Loaction();
					if (!jsonAlarming.isNull("Longitude")) {
						location.setLONGITUDE(Double.parseDouble(jsonAlarming
								.getString("Longitude")));
					}
					if (!jsonAlarming.isNull("Latitude")) {
						location.setLATITUDE(Double.parseDouble(jsonAlarming
								.getString("Latitude")));
					}
					if (!jsonAlarming.isNull("Elevation")) {
						location.setELEVATION(Double.parseDouble(jsonAlarming
								.getString("Elevation")));
					}
					alarming.setLoaction(location);
					// 附件
					if (!jsonAlarming.isNull("Accessory")
							&& !jsonAlarming.getString("Accessory").equals("")
							&& !jsonAlarming.getString("Accessory").equals(
									"[null]")) {
						ArrayList<Accessory> accessorys = new ArrayList<Accessory>();
						JSONArray jaaccessorys = new JSONArray(
								jsonAlarming.getString("Accessory"));
						for (int j = 0; j < jaaccessorys.length(); j++) {
							JSONObject joAttachment = jaaccessorys
									.getJSONObject(j);
							Accessory accessory = new Accessory();

							if (!joAttachment.isNull("url")) {
								String url = joAttachment.getString("url");
								accessory
										.setAccessoryPath(url
												.replace(
														"../../",
														ActivityUtils
																.getServerWebApi(context)));
							}
							accessorys.add(accessory);
						}
						alarming.setAccessory(accessorys);
					}
					alarm.setAlarming(alarming);
				}
				// 处警处理结果
				if (!jsonResult.isNull("AlarmingResultInfo")) {
					JSONObject jsonInfo = jsonResult
							.getJSONObject("AlarmingResultInfo");
					AlarmingResultEntity result = alarm.getResult();
					if (result == null) {
						result = new AlarmingResultEntity();
					}
					if (!jsonInfo.isNull("Result")) {
						result.setResult(jsonInfo.getString("Result"));
					}
					if (!jsonInfo.isNull("AcceptanceUnit")) {
						result.setAcceptanceUnit(jsonInfo
								.getString("AcceptanceUnit"));
					}
					if (!jsonInfo.isNull("AcceptanceTime")) {
						result.setAcceptanceTime(jsonInfo
								.getString("AcceptanceTime"));
					}
					if (!jsonInfo.isNull("CriminalCaseNum")) {
						result.setCriminalCaseNum(jsonInfo
								.getString("CriminalCaseNum"));
					}
					if (!jsonInfo.isNull("AdministrativeCaseNum")) {
						result.setAdministrativeCaseNum(jsonInfo
								.getString("AdministrativeCaseNum"));
					}
					alarm.setResult(result);
				}

				// 历史记录
				if (!jsonResult.isNull("HistoryList")) {
					JSONArray array = (JSONArray) jsonResult.get("HistoryList");
					List<AlarmingHistoryInforEntity> historicalList = new ArrayList<AlarmingHistoryInforEntity>();
					for (int j = 0; j < array.length(); j++) {
						JSONObject jsonHirstory = (JSONObject) array.get(j);
						AlarmingHistoryInforEntity history = new AlarmingHistoryInforEntity();
						if (!jsonHirstory.isNull("Time")) {
							history.setTime(jsonHirstory.getString("Time"));
						}
						if (!jsonHirstory.isNull("HisOpinion")) {
							history.setHisOpinion(jsonHirstory
									.getString("HisOpinion"));
						}
						if (!jsonHirstory.isNull("AlarmState")) {
							JSONObject jAlarmState = jsonHirstory
									.getJSONObject("AlarmState");
							AlarmStateEntity alarmState = new AlarmStateEntity();
							if (!jAlarmState
									.isNull(Receive_DisposeAlarm.STATE_ID)) {
								alarmState.setStateID(jAlarmState
										.getInt(Receive_DisposeAlarm.STATE_ID));
							}
							if (!jAlarmState
									.isNull(Receive_DisposeAlarm.STATE_NAME)) {
								alarmState
										.setStateName(jAlarmState
												.getString(Receive_DisposeAlarm.STATE_NAME));
							}
							history.setAlarmState(alarmState);
						}
						if (!jsonHirstory.isNull("PeoplePolice")) {
							JSONObject jsonPolice = jsonHirstory
									.getJSONObject("PeoplePolice");
							PoliceHeadEntity alarmPolice = new PoliceHeadEntity();
							if (!jsonPolice
									.isNull(Receive_DisposeAlarm.PEOPLE_POLICE_ID)) {
								alarmPolice
										.setPeoplePoliceID(jsonPolice
												.getInt(Receive_DisposeAlarm.PEOPLE_POLICE_ID));
							}
							if (!jsonPolice
									.isNull(Receive_DisposeAlarm.PEOPLE_POLICE_NAME)) {
								alarmPolice
										.setPeoplePoliceName(jsonPolice
												.getString(Receive_DisposeAlarm.PEOPLE_POLICE_NAME));
							}
							history.setPolice(alarmPolice);
						}
						historicalList.add(history);
					}
					alarm.setHistoryList(historicalList);
				}

				// 接警审批
				if (!jsonResult.isNull("AlarmApproval")) {
					JSONObject jAlarmApproval = jsonResult
							.getJSONObject("AlarmApproval");
					if (!jAlarmApproval
							.isNull(Receive_DisposeAlarm.COPOLICE_ID)) {
						alarm.setCoPoliceID(jAlarmApproval
								.getInt(Receive_DisposeAlarm.COPOLICE_ID));
					}
					if (!jAlarmApproval
							.isNull(Receive_DisposeAlarm.HOST_POLICE_ID)) {
						alarm.setHostPoliceID(jAlarmApproval
								.getInt(Receive_DisposeAlarm.HOST_POLICE_ID));
					}
					if (!jAlarmApproval.isNull(Receive_DisposeAlarm.OPINION)) {
						alarm.setOpinion(jAlarmApproval
								.getString(Receive_DisposeAlarm.OPINION));
					}
				}
				// 处警审批
				if (!jsonResult.isNull("AlarmingApprovalInfo")) {
					JSONObject jsonApp = jsonResult
							.getJSONObject("AlarmingApprovalInfo");
					AlarmingResultEntity all = alarm.getResult();
					if (all == null) {
						all = new AlarmingResultEntity();
					}
					if (!jsonApp.isNull("AlarmID")) {
						all.setAlarmID(jsonApp.getInt("AlarmID"));
					}
					if (!jsonApp.isNull("StateID")) {
						all.setStateID(jsonApp.getInt("StateID"));
					}
					if (!jsonApp.isNull("Remark")) {
						all.setRemark(jsonApp.getString("Remark"));
					}
					if (!jsonApp.isNull("ApprovalPeopleID")) {
						all.setApprovalPeopleID(jsonApp
								.getInt("ApprovalPeopleID"));
					}
					alarm.setResult(all);
				}
				// 公安处审批
				if (!jsonResult.isNull("PoliceOfficeApprovalInfo")) {
					JSONObject jsonPa = jsonResult
							.getJSONObject("PoliceOfficeApprovalInfo");
					AlarmingResultEntity app = alarm.getPoliceOffice();
					if (app == null) {
						app = new AlarmingResultEntity();
					}
					if (!jsonPa.isNull("AlarmID")) {
						app.setAlarmID(jsonPa.getInt("AlarmID"));
					}
					if (!jsonPa.isNull("StateID")) {
						app.setStateID(jsonPa.getInt("StateID"));
					}
					if (!jsonPa.isNull("Remark")) {
						app.setExcuse(jsonPa.getString("Remark"));
					}
					if (!jsonPa.isNull("ApprovalPeopleID")) {
						app.setApprovalPeopleID(jsonPa
								.getInt("ApprovalPeopleID"));
					}
					alarm.setPoliceOffice(app);
				}

				alarmingList.add(alarm);
			}
		}
		return alarmingList;
	}

	/**
	 * 按照报警信息ID查询报警信息
	 * 
	 * @throws JSONException
	 */
	public AlarmEntity getAlarmObject(String str) throws JSONException {
		AlarmEntity alarm = null;
		JSONObject jo = new JSONObject(str);
		if (jo.getInt("Error") == 0) {
			alarm = new AlarmEntity();
			// 接警信息
			if (!jo.isNull("AlarmInfo")) {
				JSONObject json = jo.getJSONObject("AlarmInfo");
				if (!json.isNull(Receive_DisposeAlarm.ALARM_ID)) {
					alarm.setAlarmID(json.getInt(Receive_DisposeAlarm.ALARM_ID));
				}
				if (!json.isNull(Receive_DisposeAlarm.CASE_NUMBER)) {
					alarm.setNumber(json
							.getString(Receive_DisposeAlarm.CASE_NUMBER));
				}
				if (!json.isNull(Receive_DisposeAlarm.ADDRESS)) {
					alarm.setAddress(json
							.getString(Receive_DisposeAlarm.ADDRESS));
				}
				if (!json.isNull(Receive_DisposeAlarm.TO_ALARM_AGE)) {
					alarm.setToAlarmAge(json.getString(
							Receive_DisposeAlarm.TO_ALARM_AGE).equals("0") ? ""
							: json.getString(Receive_DisposeAlarm.TO_ALARM_AGE));
				}
				if (!json.isNull(Receive_DisposeAlarm.TO_ALARM_NAME)) {
					alarm.setToAlarmName(json
							.getString(Receive_DisposeAlarm.TO_ALARM_NAME));
				}
				if (!json.isNull(Receive_DisposeAlarm.TO_ALARM_SEX)) {
					alarm.setToAlarmSex(json
							.getString(Receive_DisposeAlarm.TO_ALARM_SEX));
				}
				if (!json.isNull(Receive_DisposeAlarm.TO_ALARM_MESSAGE)) {
					alarm.setToAlarmMessage(json
							.getString(Receive_DisposeAlarm.TO_ALARM_MESSAGE));
				}
				if (!json.isNull("AlarmWay")) {
					JSONObject jAlarmWay = json.getJSONObject("AlarmWay");
					AlarmWayEntity alarmWay = new AlarmWayEntity();
					if (!jAlarmWay.isNull(Receive_DisposeAlarm.ALARM_WAY_ID)) {
						alarmWay.setAlarmWayID(jAlarmWay
								.getInt(Receive_DisposeAlarm.ALARM_WAY_ID));
					}
					if (!jAlarmWay.isNull("AlarmWayName")) {
						alarmWay.setAlarmWayName(jAlarmWay
								.getString("AlarmWayName"));
					}
					alarm.setAlarmWay(alarmWay);
				}
				if (!json.isNull(Receive_DisposeAlarm.CONTACT)) {
					alarm.setContact(json
							.getString(Receive_DisposeAlarm.CONTACT));
				}
				if (!json.isNull("PeoplePolice")) {
					JSONObject jsonPolice = json.getJSONObject("PeoplePolice");
					PoliceHeadEntity alarmPolice = new PoliceHeadEntity();
					if (!jsonPolice
							.isNull(Receive_DisposeAlarm.PEOPLE_POLICE_ID)) {
						alarmPolice.setPeoplePoliceID(jsonPolice
								.getInt(Receive_DisposeAlarm.PEOPLE_POLICE_ID));
					}
					if (!jsonPolice
							.isNull(Receive_DisposeAlarm.PEOPLE_POLICE_NAME)) {
						alarmPolice
								.setPeoplePoliceName(jsonPolice
										.getString(Receive_DisposeAlarm.PEOPLE_POLICE_NAME));
					}
					alarm.setPolice(alarmPolice);
				}
				if (!json.isNull("Nation")) {
					JSONObject jNation = json.getJSONObject("Nation");
					NationEntity nation = new NationEntity();
					if (!jNation.isNull(Receive_DisposeAlarm.NATION_ID)) {
						nation.setNationID(jNation
								.getInt(Receive_DisposeAlarm.NATION_ID));
					}
					if (!jNation.isNull("NationName")) {
						nation.setNationName(jNation.getString("NationName"));
					}
					alarm.setNation(nation);
				}
				if (!json.isNull("AlarmState")) {
					JSONObject jAlarmState = json.getJSONObject("AlarmState");
					AlarmStateEntity alarmState = new AlarmStateEntity();
					if (!jAlarmState.isNull(Receive_DisposeAlarm.STATE_ID)) {
						alarmState.setStateID(jAlarmState
								.getInt(Receive_DisposeAlarm.STATE_ID));
					}
					if (!jAlarmState.isNull(Receive_DisposeAlarm.STATE_NAME)) {
						alarmState.setStateName(jAlarmState
								.getString(Receive_DisposeAlarm.STATE_NAME));
					}
					alarm.setState(alarmState);
				}
				if (!json.isNull(Receive_DisposeAlarm.CARD_NUMBER)) {
					alarm.setCardNumber(json
							.getString(Receive_DisposeAlarm.CARD_NUMBER));
				}
				if (!json.isNull(Receive_DisposeAlarm.ALARM_TIME)) {
					alarm.setAlarmTime(json
							.getString(Receive_DisposeAlarm.ALARM_TIME));
				}
				if (!json.isNull("ApprovalPeopleID")) {
					alarm.setApprovalPeopleID(json.getInt("ApprovalPeopleID"));
				}
			}
			// 历史记录
			if (!jo.isNull("HistoryList")) {
				JSONArray array = (JSONArray) jo.get("HistoryList");
				List<AlarmingHistoryInforEntity> historicalList = new ArrayList<AlarmingHistoryInforEntity>();
				for (int j = 0; j < array.length(); j++) {
					JSONObject jsonHirstory = (JSONObject) array.get(j);
					AlarmingHistoryInforEntity history = new AlarmingHistoryInforEntity();
					if (!jsonHirstory.isNull("Time")) {
						history.setTime(jsonHirstory.getString("Time"));
					}
					if (!jsonHirstory.isNull("HisOpinion")) {
						history.setHisOpinion(jsonHirstory
								.getString("HisOpinion"));
					}
					if (!jsonHirstory.isNull("AlarmState")) {
						JSONObject jsonObject = jsonHirstory
								.getJSONObject("AlarmState");
						AlarmStateEntity state = new AlarmStateEntity();
						if (!jsonObject.isNull("StateID")) {
							state.setStateID(jsonObject.getInt("StateID"));
						}
						if (!jsonObject.getString("StateName").equals("")) {
							state.setStateName(jsonObject
									.getString("StateName"));
						}
						history.setAlarmState(state);
					}
					if (!jsonHirstory.isNull("PeoplePolice")) {
						JSONObject jsonPolice = jsonHirstory
								.getJSONObject("PeoplePolice");
						PoliceHeadEntity police = new PoliceHeadEntity();
						if (!jsonPolice
								.isNull(Receive_DisposeAlarm.PEOPLE_POLICE_ID)) {
							police.setPeoplePoliceID(jsonPolice
									.getInt(Receive_DisposeAlarm.PEOPLE_POLICE_ID));
						}
						if (!jsonPolice
								.isNull(Receive_DisposeAlarm.PEOPLE_POLICE_NAME)) {
							police.setPeoplePoliceName(jsonPolice
									.getString(Receive_DisposeAlarm.PEOPLE_POLICE_NAME));
						}
						history.setPolice(police);
					}
					historicalList.add(history);
				}
				alarm.setHistoryList(historicalList);
			}

			// 处警信息
			if (!jo.isNull("AlarmingInfo")) {
				JSONObject jsonAlarming = jo.getJSONObject("AlarmingInfo");
				AlarmingEntity alarming = new AlarmingEntity();
				if (!jsonAlarming.isNull("ReceivedTime")) {
					alarming.setReceivedTime(jsonAlarming
							.getString("ReceivedTime"));
				}
				if (!jsonAlarming.isNull("ArrivalsTime")) {
					alarming.setArrivalsTime(jsonAlarming
							.getString("ArrivalsTime"));
				}
				if (!jsonAlarming.isNull("Name")) {
					alarming.setName(jsonAlarming.getString("Name"));
				}
				if (!jsonAlarming.isNull("Age")) {
					alarming.setAge(jsonAlarming.getString("Age").equals("0") ? ""
							: jsonAlarming.getString("Age"));
				}
				if (!jsonAlarming.isNull("Sex")) {
					alarming.setSex(jsonAlarming.getString("Sex"));
				}
				if (!jsonAlarming.isNull("Education")) {
					AlarmEducationEntity edu = new AlarmEducationEntity();
					edu.setEducationID(Integer.parseInt(jsonAlarming
							.getString("Education")));
					alarming.setEducation(edu);
				}
				if (!jsonAlarming.isNull("Workplace")) {
					alarming.setWorkplace(jsonAlarming.getString("Workplace"));
				}
				if (!jsonAlarming.isNull("AddressZ")) {
					alarming.setAddressZ(jsonAlarming.getString("AddressZ"));
				}
				if (!jsonAlarming.isNull("CompanyName")) {
					alarming.setCompanyName(jsonAlarming
							.getString("CompanyName"));
				}
				if (!jsonAlarming.isNull("AddressD")) {
					alarming.setAddressD(jsonAlarming.getString("AddressD"));
				}
				if (!jsonAlarming.isNull("Legal")) {
					alarming.setLegal(jsonAlarming.getString("Legal"));
				}
				if (!jsonAlarming.isNull("Position")) {
					AlarmPositionEntity position = new AlarmPositionEntity();
					position.setPositionID(Integer.parseInt(jsonAlarming
							.getString("Position")));
					alarming.setPosition(position);
				}
				if (!jsonAlarming.isNull("BriefCase")) {
					alarming.setBriefCase(jsonAlarming.getString("BriefCase"));
				}
				if (!jsonAlarming.isNull("TreatmentAdviceID")) {
					AlarmOpinionEntity opinion = new AlarmOpinionEntity();
					opinion.setOpinionID(Integer.parseInt(jsonAlarming
							.getString("TreatmentAdviceID")));
					alarming.setOpinion(opinion);
				}
				if (!jsonAlarming.isNull("Remark")) {
					alarming.setRemark(jsonAlarming.getString("Remark"));
				}
				// 审批人
				if (!jsonAlarming.isNull("ApprovalPeople")) {
					JSONObject jsonPolice = jsonAlarming
							.getJSONObject("ApprovalPeople");
					PoliceHeadEntity police = new PoliceHeadEntity();
					if (!jsonPolice
							.isNull(Receive_DisposeAlarm.PEOPLE_POLICE_ID)) {
						police.setPeoplePoliceID(jsonPolice
								.getInt(Receive_DisposeAlarm.PEOPLE_POLICE_ID));
					}
					if (!jsonPolice
							.isNull(Receive_DisposeAlarm.PEOPLE_POLICE_NAME)) {
						police.setPeoplePoliceName(jsonPolice
								.getString(Receive_DisposeAlarm.PEOPLE_POLICE_NAME));
					}
					alarming.setApprovalPeople(police);
				}
				// 定位
				Loaction location = new Loaction();
				if (!jsonAlarming.isNull("Longitude")) {
					location.setLONGITUDE(Double.parseDouble(jsonAlarming
							.getString("Longitude")));
				}
				if (!jsonAlarming.isNull("Latitude")) {
					location.setLATITUDE(Double.parseDouble(jsonAlarming
							.getString("Latitude")));
				}
				if (!jsonAlarming.isNull("Elevation")) {
					location.setELEVATION(Double.parseDouble(jsonAlarming
							.getString("Elevation")));
				}
				if (!jsonAlarming.isNull("Time")) {
					location.setTIME(jsonAlarming.getString("Time"));
				}
				alarming.setLoaction(location);
				// 附件
				if (!jsonAlarming.isNull("Accessory")
						&& !jsonAlarming.getString("Accessory").equals("")) {
					ArrayList<Accessory> accessorys = new ArrayList<Accessory>();
					JSONArray jaaccessorys = new JSONArray(
							jsonAlarming.getString("Accessory"));
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

					alarming.setAccessory(accessorys);
				}
				alarm.setAlarming(alarming);
			}
			// 处警处理结果
			if (!jo.isNull("AlarmingResultInfo")) {
				JSONObject jsonInfo = jo.getJSONObject("AlarmingResultInfo");
				AlarmingResultEntity result = alarm.getResult();
				if (result == null) {
					result = new AlarmingResultEntity();
				}
				if (!jsonInfo.isNull("Result")) {
					result.setResult(jsonInfo.getString("Result"));
				}
				if (!jsonInfo.isNull("AcceptanceUnit")) {
					result.setAcceptanceUnit(jsonInfo
							.getString("AcceptanceUnit"));
				}
				if (!jsonInfo.isNull("AcceptanceTime")) {
					result.setAcceptanceTime(jsonInfo
							.getString("AcceptanceTime"));
				}
				if (!jsonInfo.isNull("CriminalCaseNum")) {
					result.setCriminalCaseNum(jsonInfo
							.getString("CriminalCaseNum"));
				}
				if (!jsonInfo.isNull("AdministrativeCaseNum")) {
					result.setAdministrativeCaseNum(jsonInfo
							.getString("AdministrativeCaseNum"));
				}
				alarm.setResult(result);
			}
			// 审批页面
			if (!jo.isNull("AlarmApproval")) {
				JSONObject jAlarmApproval = jo.getJSONObject("AlarmApproval");
				if (!jAlarmApproval.isNull(Receive_DisposeAlarm.COPOLICE_ID)) {
					alarm.setCoPoliceID(jAlarmApproval
							.getInt(Receive_DisposeAlarm.COPOLICE_ID));
				}
				if (!jAlarmApproval.isNull(Receive_DisposeAlarm.HOST_POLICE_ID)) {
					alarm.setHostPoliceID(jAlarmApproval
							.getInt(Receive_DisposeAlarm.HOST_POLICE_ID));
				}
				if (!jAlarmApproval.isNull(Receive_DisposeAlarm.OPINION)) {
					alarm.setOpinion(jAlarmApproval
							.getString(Receive_DisposeAlarm.OPINION));
				}
			}
			// 处警审批
			if (!jo.isNull("AlarmingApprovalInfo")) {
				JSONObject jsonAa = jo.getJSONObject("AlarmingApprovalInfo");
				AlarmingResultEntity results = alarm.getResult();
				if (results == null) {
					results = new AlarmingResultEntity();
				}
				if (!jsonAa.isNull("AlarmID")) {
					results.setAlarmID(jsonAa.getInt("AlarmID"));
				}
				if (!jsonAa.isNull("StateID")) {
					results.setStateID(jsonAa.getInt("StateID"));
				}
				if (!jsonAa.isNull("Remark")) {
					results.setRemark(jsonAa.getString("Remark"));
				}
				if (!jsonAa.isNull("ApprovalPeopleID")) {
					results.setApprovalPeopleID(jsonAa
							.getInt("ApprovalPeopleID"));
				}
				alarm.setResult(results);
			}
			// 公安处审批
			if (!jo.isNull("PoliceOfficeApprovalInfo")) {
				JSONObject jsonPp = jo
						.getJSONObject("PoliceOfficeApprovalInfo");
				AlarmingResultEntity res = alarm.getPoliceOffice();
				if (res == null) {
					res = new AlarmingResultEntity();
				}
				if (!jsonPp.isNull("AlarmID")) {
					res.setAlarmID(jsonPp.getInt("AlarmID"));
				}
				if (!jsonPp.isNull("StateID")) {
					res.setStateID(jsonPp.getInt("StateID"));
				}
				if (!jsonPp.isNull("Remark")) {// 理由
					res.setExcuse(jsonPp.getString("Remark"));
				}
				if (!jsonPp.isNull("ApprovalPeopleID")) {
					res.setApprovalPeopleID(jsonPp.getInt("ApprovalPeopleID"));
				}
				alarm.setPoliceOffice(res);
			}
		}
		return alarm;
	}

	/**
	 * 根据审批对象获取json
	 * 
	 * @param approval
	 * @return json
	 * @throws JSONException
	 */
	public static String getApprovalJson(AlarmEntity approval)
			throws JSONException {
		JSONObject jo = new JSONObject();
		jo.put(Receive_DisposeAlarm.ALARM_ID, approval.getAlarmID());
		jo.put(Receive_DisposeAlarm.APPROVAL_PEOPLE_ID,
				approval.getApprovalPeopleID());
		jo.put(Receive_DisposeAlarm.HOST_POLICE_ID, approval.getHostPoliceID());
		jo.put(Receive_DisposeAlarm.COPOLICE_ID, approval.getCoPoliceID());
		jo.put(Receive_DisposeAlarm.OPINION, approval.getOpinion());

		return jo.toString();
	}
}
