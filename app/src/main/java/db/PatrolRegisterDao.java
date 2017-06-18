package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.LocationInfoEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import db.Database.PatrolRegisterLocation;
	/*
	 * 
	 * 巡逻本地数据库
	 * 
	 * */
public class PatrolRegisterDao {

	private ForestDatabaseHelper dbHelper;
	private Context context;
	private SQLiteDatabase db;
	public PatrolRegisterDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	/**
	 * 插入单条数据
	 * 
	 * @param info
	 * @return
	 */
	public synchronized boolean insertLocationInfo(LocationInfoEntity info) {
		// String sql = "SELECT * FROM patrolregister WHERE user_id=" +
		// info.getUserId() + " and longitude=" + info.getLongitude() +
		// " and latitude=" + info.getLatitude() + " and elevation=" +
		// info.getElevation() + " and time= " + info.getTime() +
		// " and locationType=" + info.getLocationType();
		// Cursor cursor = dbHelper.getInstence().rawQuery(sql,
		// null);
		Cursor cursor=null;
		try {
			cursor = dbHelper.getInstence().query(
					PatrolRegisterLocation.PATROLREGISTER_TABLE_NAME,
					new String[] { PatrolRegisterLocation.USER_ID, PatrolRegisterLocation.LONGITUDE,
							PatrolRegisterLocation.LATITUDE,
							// PatrolRegisterLocation.ELEVATION,
							PatrolRegisterLocation.TIME, PatrolRegisterLocation.LOCATIONTYPE, },
					PatrolRegisterLocation.USER_ID + "=? and " + PatrolRegisterLocation.LONGITUDE
							+ "=? and " + PatrolRegisterLocation.LATITUDE + "=? and "
							+
							// PatrolRegisterLocation.ELEVATION + "=? and " +
							PatrolRegisterLocation.TIME + "=? and "
							+ PatrolRegisterLocation.LOCATIONTYPE + "=?",

					new String[] { String.valueOf(info.getUserId()),
							String.valueOf(info.getLongitude()), String.valueOf(info.getLatitude()),
							// String.valueOf(info.getElevation()),
							String.valueOf(info.getTime()), String.valueOf(info.getLocationType()), },
					null, null, null);
			if (null != cursor) {
				while (cursor.moveToNext()) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (null != cursor) {
				cursor.close();
			}
		}
		
		

		boolean flag = false;

		db= dbHelper.getInstence();
		db.beginTransaction();

		ContentValues contentValues = new ContentValues();
		contentValues.put(PatrolRegisterLocation.USER_ID, info.getUserId());
		contentValues.put(PatrolRegisterLocation.LONGITUDE, info.getLongitude());
		contentValues.put(PatrolRegisterLocation.LATITUDE, info.getLatitude());
		contentValues.put(PatrolRegisterLocation.ELEVATION, info.getElevation());
		contentValues.put(PatrolRegisterLocation.TIME, info.getTime());
		contentValues.put(PatrolRegisterLocation.LOCATIONTYPE, info.getLocationType());
		contentValues.put(PatrolRegisterLocation.GUID, info.getGuid());//插入GUID

		long ok = db.insert(PatrolRegisterLocation.PATROLREGISTER_TABLE_NAME, null, contentValues);

		if (-1 != ok) {
			flag = true;
		}

		db.setTransactionSuccessful();
		db.endTransaction();

		return flag;

	}

	/**
	 * 删表
	 */
	public boolean delTable() {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db.delete(PatrolRegisterLocation.PATROLREGISTER_TABLE_NAME, PatrolRegisterLocation.USER_ID + "=?", new String[]{"" + ActivityUtils.getUseId(context)});
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
	 * 获取所有巡逻轨迹信息
	 * 
	 * @return
	 */
	public List<LocationInfoEntity> getAllInfo() {

		List<LocationInfoEntity> infoList = null;

		SQLiteDatabase db = dbHelper.getInstence();
		// db.beginTransaction();//
		Cursor cursor = null;

		String sql = "SELECT * FROM " + PatrolRegisterLocation.PATROLREGISTER_TABLE_NAME + " where " + PatrolRegisterLocation.USER_ID + "=" + ActivityUtils.getUseId(context);

		cursor = db.rawQuery(sql, null);
		infoList = new ArrayList<LocationInfoEntity>();
		while (cursor.moveToNext()) {
			LocationInfoEntity info = new LocationInfoEntity();
			info.setLongitude(cursor.getDouble(cursor
					.getColumnIndex(PatrolRegisterLocation.LONGITUDE)));
			info.setUserId(cursor.getInt(cursor.getColumnIndex(PatrolRegisterLocation.USER_ID)));
			info.setLatitude(cursor.getDouble(cursor
					.getColumnIndex(PatrolRegisterLocation.LATITUDE)));
			info.setElevation(cursor.getDouble(cursor
					.getColumnIndex(PatrolRegisterLocation.ELEVATION)));
			info.setTime(cursor.getString(cursor.getColumnIndex(PatrolRegisterLocation.TIME)));
			info.setInfoId(cursor.getInt(cursor.getColumnIndex(PatrolRegisterLocation.ID)));
			info.setLocationType(cursor.getInt(cursor
					.getColumnIndex(PatrolRegisterLocation.LOCATIONTYPE)));
			info.setGuid(cursor.getString(cursor.getColumnIndex(PatrolRegisterLocation.GUID)));
			infoList.add(info);
		}
		cursor.close();
		// db.setTransactionSuccessful();
		// db.endTransaction();

		return infoList;
	}

	/**
	 * 获取总量
	 * 
	 * @return
	 */
	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM " + PatrolRegisterLocation.PATROLREGISTER_TABLE_NAME + " where " + PatrolRegisterLocation.USER_ID + "=" + ActivityUtils.getUseId(context);

		try {
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				count = cursor.getInt(0);
			}
			cursor.close();
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.endTransaction();
			if (null != cursor) {
				cursor.close();
			}
		}

		return count;
	}

	/**
	 * 只获取获取前三十条数据
	 * 
	 * @return
	 */
	public List<LocationInfoEntity> getBefore30Datas() {
		// if (getCount() < 30) {
		// return null;
		// } else {
		//
		// }
		Cursor cursor = dbHelper.getInstence().query(
				PatrolRegisterLocation.PATROLREGISTER_TABLE_NAME, null, PatrolRegisterLocation.USER_ID +"=?",new String[]{"" + ActivityUtils.getUseId(context)}, null, null,
				"time asc", "0,30");
		if (null != cursor) {
			List<LocationInfoEntity> infoList = new ArrayList<LocationInfoEntity>();
			while (cursor.moveToNext()) {
				LocationInfoEntity info = new LocationInfoEntity();
				info.setLongitude(cursor.getDouble(cursor
						.getColumnIndex(PatrolRegisterLocation.LONGITUDE)));
				info.setUserId(cursor.getInt(cursor.getColumnIndex(PatrolRegisterLocation.USER_ID)));
				info.setLatitude(cursor.getDouble(cursor
						.getColumnIndex(PatrolRegisterLocation.LATITUDE)));
				info.setElevation(cursor.getDouble(cursor
						.getColumnIndex(PatrolRegisterLocation.ELEVATION)));
				info.setTime(cursor.getString(cursor.getColumnIndex(PatrolRegisterLocation.TIME)));
				info.setInfoId(cursor.getInt(cursor.getColumnIndex(PatrolRegisterLocation.ID)));
				info.setLocationType(cursor.getInt(cursor
						.getColumnIndex(PatrolRegisterLocation.LOCATIONTYPE)));
				info.setGuid(cursor.getString(cursor.getColumnIndex(PatrolRegisterLocation.GUID)));
				infoList.add(info);
			}
			cursor.close();
			return infoList;
		}
		return null;
	}

	/**
	 * 删除前30条数据
	 * 
	 * @return
	 */
	public boolean delBefore30Datas() {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			// String sql =
			// "delete from patrolregister where  _id in (select _id from patrolregister order by time asc  limit 0,5)";
			// db.delete(PatrolRegisterLocation.PATROLREGISTER_TABLE_NAME,
			// "limit 0,5", null);
			int res = db.delete(PatrolRegisterLocation.PATROLREGISTER_TABLE_NAME,
					"_id in (select _id from patrolregister where user_id=" + ActivityUtils.getUseId(context) + " order by time asc limit 0,30)", null);
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

	public int getUnSynchronizationCount() {
		int count = 0;
		ReceiveAlarmDao raDao = new ReceiveAlarmDao(context);
		count += raDao.getCount();
		ProtectedAnimalsDao paDao = new ProtectedAnimalsDao(context);
		count += paDao.getCount();
		KeyProtectedPlantDao kppDao = new KeyProtectedPlantDao(context);
		count += kppDao.getCount();
		KeyPositionsDao kpDao = new KeyPositionsDao(context);
		count += kpDao.getCount();
		ForestPatrolWorkDao fpwDao = new ForestPatrolWorkDao(context);
		count += fpwDao.getCount();
		FirePatrolInspectionDao fpiDao = new FirePatrolInspectionDao(context);
		count += fpiDao.getCount();
		LawEnforcementInspectionDao leiDao = new LawEnforcementInspectionDao(context);
		count += leiDao.getCount();
		ForestSurveyDao fsDao = new ForestSurveyDao(context);
		count += fsDao.getCount();
		AlarmingWorkDao awDao = new AlarmingWorkDao(context);
		count += awDao.getCount();
		ReceiveAndDisposeAlarmDao radaDao = new ReceiveAndDisposeAlarmDao(context);
		count += radaDao.getCount();
		TransientRegisterDao trDao = new TransientRegisterDao(context);
		count += trDao.getCount();
		WatchTowerDao wtDao = new WatchTowerDao(context);
		count += wtDao.getCount();
		RegistrationAreaFireDao rafDao = new RegistrationAreaFireDao(context);
		count += rafDao.getCount();
		IllegalUseOfFireRegisterDao ifrDao = new IllegalUseOfFireRegisterDao(context);
		count += ifrDao.getCount();
		PublicSecurityManagementDao psmDao = new PublicSecurityManagementDao(context);
		count += psmDao.getCount();
		LocationInfoDao lld = new LocationInfoDao(context);
		count += lld.getCount();
		NoteDao nd = new NoteDao(context);
		count += nd.getCount();
		KeyPopulationDao keyDao = new KeyPopulationDao(context);
		count += keyDao.getCount();
		PatrolRouteManagementDao patrolDao = new PatrolRouteManagementDao(context);
		count += patrolDao.getCount();
		TheWorkingLogDao logDao = new TheWorkingLogDao(context);
		count += logDao.getCount();
		BreedDao bDao = new BreedDao(context);
		count += bDao.getCount();
		ForestPotectionDao fpDao = new ForestPotectionDao(context);
		count += fpDao.getCount();
		ForestryKeyIndustriesDao fkiDao = new ForestryKeyIndustriesDao(context);
		count += fkiDao.getCount();
		InterviewDao iDao = new InterviewDao(context);
		count += iDao.getCount();
		ManagedObjectDao moDao = new ManagedObjectDao(context);
		count += moDao.getCount();
		QuestionedSituationDao qsDao = new QuestionedSituationDao(context);
		count += qsDao.getCount();
		ItelligenceDao telDao = new ItelligenceDao(context);
		count += telDao.getCount();
		WoodCuttingDao wcDao = new WoodCuttingDao(context);
		count += wcDao.getCount();
		WoodlandMiningDao wmDao = new WoodlandMiningDao(context);
		count += wmDao.getCount();
		SocialStatisticsDao ssDao = new SocialStatisticsDao(context);
		count += ssDao.getCount();
		// 三情——林情
		LinQingDao lqd = new LinQingDao(context);
		count += lqd.getCount();
		// 三情——山情
		MountConditionDao mcd = new MountConditionDao(context);
		count += mcd.getCount();
		// 三情——社情
		SheConditionDao scd = new SheConditionDao(context);
		count += scd.getCount();
		// 巡逻登记轨迹坐标点
		// if (flag) {
		
//		RouteInfoDao prDao = new RouteInfoDao(context);
//		count += prDao.getCount();
		
		count += getCount();
		// }
		return count;
	}
}
