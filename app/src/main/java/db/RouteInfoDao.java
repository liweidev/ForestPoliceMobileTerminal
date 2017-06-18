package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.LocationInfoEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

import db.Database.RouteInfo;

/**
 * 路线管理-----路线点
 * 
 * @author wangxiaofei
 * 
 */
public class RouteInfoDao {
	private ForestDatabaseHelper dbHelper;

	private Context context;

	public RouteInfoDao(Context context) {
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
	public boolean insertLocationInfo(LocationInfoEntity info) {
		Cursor cursor = null;
		try {
			cursor = dbHelper.getInstence()
					.query(RouteInfo.ROUTE_INFO_TABLE_NAME,
							new String[] { RouteInfo.USER_ID, RouteInfo.LONGITUDE,
									RouteInfo.LATITUDE,
									// RouteInfo.ELEVATION,
									RouteInfo.TIME, RouteInfo.LOCATIONTYPE, },
							RouteInfo.USER_ID + "=? and " + RouteInfo.LONGITUDE + "=? and "
									+ RouteInfo.LATITUDE + "=? and " +
									// RouteInfo.ELEVATION + "=? and " +
									RouteInfo.TIME + "=? and " + RouteInfo.LOCATIONTYPE + "=?",

							new String[] { String.valueOf(info.getUserId()),
									String.valueOf(info.getLongitude()),
									String.valueOf(info.getLatitude()),
									// String.valueOf(info.getElevation()),
									String.valueOf(info.getTime()),
									String.valueOf(info.getLocationType()), }, null, null, null);
			if (null != cursor) {
				if (cursor.moveToFirst()) {
					return false;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}

		boolean flag = false;

		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();

		ContentValues contentValues = new ContentValues();
		contentValues.put(RouteInfo.USER_ID, info.getUserId());
		contentValues.put(RouteInfo.LONGITUDE, info.getLongitude());
		contentValues.put(RouteInfo.LATITUDE, info.getLatitude());
		contentValues.put(RouteInfo.ELEVATION, info.getElevation());
		contentValues.put(RouteInfo.TIME, info.getTime());
		contentValues.put(RouteInfo.LOCATIONTYPE, info.getLocationType());
		contentValues.put(RouteInfo.GUID, info.getGuid());
		contentValues.put(RouteInfo.STATUS, 0);

		long ok = db.insert(RouteInfo.ROUTE_INFO_TABLE_NAME, null, contentValues);

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
			int res = db.delete(RouteInfo.ROUTE_INFO_TABLE_NAME, "user_id=?", new String[] { ""
					+ ActivityUtils.getUseId(context) });
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
	 * 获取所有有用的路线轨迹信息
	 * 
	 * @return
	 */
	public List<LocationInfoEntity> getAllInfoUserful() {
		List<LocationInfoEntity> infoList = null;
		SQLiteDatabase db = dbHelper.getInstence();
		Cursor cursor = null;
		String sql = "SELECT * FROM " + RouteInfo.ROUTE_INFO_TABLE_NAME + " where status = 0 "
				+ " and user_id = " + ActivityUtils.getUseId(context);
		cursor = db.rawQuery(sql, null);
		infoList = new ArrayList<LocationInfoEntity>();
		while (cursor.moveToNext()) {
			LocationInfoEntity info = new LocationInfoEntity();
			info.setLongitude(cursor.getDouble(cursor.getColumnIndex(RouteInfo.LONGITUDE)));
			info.setUserId(cursor.getInt(cursor.getColumnIndex(RouteInfo.USER_ID)));
			info.setLatitude(cursor.getDouble(cursor.getColumnIndex(RouteInfo.LATITUDE)));
			info.setElevation(cursor.getDouble(cursor.getColumnIndex(RouteInfo.ELEVATION)));
			info.setTime(cursor.getString(cursor.getColumnIndex(RouteInfo.TIME)));
			info.setInfoId(cursor.getInt(cursor.getColumnIndex(RouteInfo.ID)));
			info.setLocationType(cursor.getInt(cursor.getColumnIndex(RouteInfo.LOCATIONTYPE)));
			info.setGuid(cursor.getString(cursor.getColumnIndex(RouteInfo.GUID)));
			info.setStatus((cursor.getInt(cursor.getColumnIndex(RouteInfo.STATUS))));
			infoList.add(info);
		}
		cursor.close();
		return infoList;
	}

	/**
	 * 获取所有巡逻轨迹信息，包括已经上传的数据
	 * 
	 * @return
	 */
	public List<LocationInfoEntity> getAllInfo(String guid) {
		List<LocationInfoEntity> infoList = null;
		SQLiteDatabase db = dbHelper.getInstence();
		Cursor cursor = null;
		String sql = "SELECT * FROM " + RouteInfo.ROUTE_INFO_TABLE_NAME + " where user_id = "
				+ ActivityUtils.getUseId(context) + " and guid = '" + guid + "' order by time";
		cursor = db.rawQuery(sql, null);
		infoList = new ArrayList<LocationInfoEntity>();
		while (cursor.moveToNext()) {
			LocationInfoEntity info = new LocationInfoEntity();
			info.setLongitude(cursor.getDouble(cursor.getColumnIndex(RouteInfo.LONGITUDE)));
			info.setUserId(cursor.getInt(cursor.getColumnIndex(RouteInfo.USER_ID)));
			info.setLatitude(cursor.getDouble(cursor.getColumnIndex(RouteInfo.LATITUDE)));
			info.setElevation(cursor.getDouble(cursor.getColumnIndex(RouteInfo.ELEVATION)));
			info.setTime(cursor.getString(cursor.getColumnIndex(RouteInfo.TIME)));
			info.setInfoId(cursor.getInt(cursor.getColumnIndex(RouteInfo.ID)));
			info.setLocationType(cursor.getInt(cursor.getColumnIndex(RouteInfo.LOCATIONTYPE)));
			info.setGuid(cursor.getString(cursor.getColumnIndex(RouteInfo.GUID)));
			info.setStatus((cursor.getInt(cursor.getColumnIndex(RouteInfo.STATUS))));
			infoList.add(info);
		}
		cursor.close();
		return infoList;
	}

	/**
	 * 获取可用的总量
	 * 
	 * @return
	 */
	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM " + RouteInfo.ROUTE_INFO_TABLE_NAME
				+ " where status = 0" + " and user_id = " + ActivityUtils.getUseId(context);

		try {
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				count = cursor.getInt(0);
			}
			cursor.close();
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			if(null!=cursor){
				cursor.close();
			}
		}

		return count;
	}

	/**
	 * 只获取获取前三十可用的数据
	 * 
	 * @return
	 */
	public List<LocationInfoEntity> getBefore30Datas() {
		// if (getCount() < 30) {
		// return null;
		// } else {
		//
		// }
		Cursor cursor = dbHelper.getInstence().query(RouteInfo.ROUTE_INFO_TABLE_NAME, null,
				RouteInfo.STATUS + "=? and user_id=?",
				new String[] { "0", "" + ActivityUtils.getUseId(context) }, null, null, "time",
				"0,30");
		if (null != cursor) {
			List<LocationInfoEntity> infoList = new ArrayList<LocationInfoEntity>();
			while (cursor.moveToNext()) {
				LocationInfoEntity info = new LocationInfoEntity();
				info.setLongitude(cursor.getDouble(cursor.getColumnIndex(RouteInfo.LONGITUDE)));
				info.setUserId(cursor.getInt(cursor.getColumnIndex(RouteInfo.USER_ID)));
				info.setLatitude(cursor.getDouble(cursor.getColumnIndex(RouteInfo.LATITUDE)));
				info.setElevation(cursor.getDouble(cursor.getColumnIndex(RouteInfo.ELEVATION)));
				info.setTime(cursor.getString(cursor.getColumnIndex(RouteInfo.TIME)));
				info.setInfoId(cursor.getInt(cursor.getColumnIndex(RouteInfo.ID)));
				info.setLocationType(cursor.getInt(cursor.getColumnIndex(RouteInfo.LOCATIONTYPE)));
				info.setGuid(cursor.getString(cursor.getColumnIndex(RouteInfo.GUID)));
				info.setStatus((cursor.getInt(cursor.getColumnIndex(RouteInfo.STATUS))));
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
			// db.delete(RouteInfo.ROUTE_INFO_TABLE_NAME, "limit 0,5", null);
			int res = db.delete(
					RouteInfo.ROUTE_INFO_TABLE_NAME,
					"_id in (select _id from patrolregister where user_id="
							+ ActivityUtils.getUseId(context)
							+ "  and status=0 order by status,time limit 0,30)", null);
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
	 * 更新前30条数据
	 * 
	 * @return
	 */
	public boolean updateBefore30Datas() {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			// String sql =
			// "delete from patrolregister where  _id in (select _id from patrolregister order by time asc  limit 0,5)";
			// db.delete(RouteInfo.ROUTE_INFO_TABLE_NAME, "limit 0,5", null);
			// int res =
			// db.delete(RouteInfo.ROUTE_INFO_TABLE_NAME,"_id in (select _id from patrolregister order by time asc limit 0,5)",
			// null);
			ContentValues values = new ContentValues();
			values.put(RouteInfo.STATUS, "1");
			int res = db.update(
					RouteInfo.ROUTE_INFO_TABLE_NAME,
					values,
					"_id in (select _id from route_info where user_id="
							+ ActivityUtils.getUseId(context)
							+ " and status != 1  order by time limit 0,30)", null);
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

	// public int getUnSynchronizationCount() {
	// int count = 0;
	// ReceiveAlarmDao raDao = new ReceiveAlarmDao(context);
	// count += raDao.getCount();
	// ProtectedAnimalsDao paDao = new ProtectedAnimalsDao(context);
	// count += paDao.getCount();
	// KeyProtectedPlantDao kppDao = new KeyProtectedPlantDao(context);
	// count += kppDao.getCount();
	// KeyPositionsDao kpDao = new KeyPositionsDao(context);
	// count += kpDao.getCount();
	// ForestPatrolWorkDao fpwDao = new ForestPatrolWorkDao(context);
	// count += fpwDao.getCount();
	// FirePatrolInspectionDao fpiDao = new FirePatrolInspectionDao(context);
	// count += fpiDao.getCount();
	// LawEnforcementInspectionDao leiDao = new
	// LawEnforcementInspectionDao(context);
	// count += leiDao.getCount();
	// ForestSurveyDao fsDao = new ForestSurveyDao(context);
	// count += fsDao.getCount();
	// AlarmingWorkDao awDao = new AlarmingWorkDao(context);
	// count += awDao.getCount();
	// ReceiveAndDisposeAlarmDao radaDao = new
	// ReceiveAndDisposeAlarmDao(context);
	// count += radaDao.getCount();
	// TransientRegisterDao trDao = new TransientRegisterDao(context);
	// count += trDao.getCount();
	// WatchTowerDao wtDao = new WatchTowerDao(context);
	// count += wtDao.getCount();
	// RegistrationAreaFireDao rafDao = new RegistrationAreaFireDao(context);
	// count += rafDao.getCount();
	// IllegalUseOfFireRegisterDao ifrDao = new
	// IllegalUseOfFireRegisterDao(context);
	// count += ifrDao.getCount();
	// PublicSecurityManagementDao psmDao = new
	// PublicSecurityManagementDao(context);
	// count += psmDao.getCount();
	// LocationInfoDao lld = new LocationInfoDao(context);
	// count += lld.getCount();
	// NoteDao nd = new NoteDao(context);
	// count += nd.getCount();
	// KeyPopulationDao keyDao = new KeyPopulationDao(context);
	// count += keyDao.getCount();
	// PatrolRouteManagementDao patrolDao = new
	// PatrolRouteManagementDao(context);
	// count += patrolDao.getCount();
	// TheWorkingLogDao logDao = new TheWorkingLogDao(context);
	// count += logDao.getCount();
	// BreedDao bDao = new BreedDao(context);
	// count += bDao.getCount();
	// ForestPotectionDao fpDao = new ForestPotectionDao(context);
	// count += fpDao.getCount();
	// ForestryKeyIndustriesDao fkiDao = new ForestryKeyIndustriesDao(context);
	// count += fkiDao.getCount();
	// InterviewDao iDao = new InterviewDao(context);
	// count += iDao.getCount();
	// ManagedObjectDao moDao = new ManagedObjectDao(context);
	// count += moDao.getCount();
	// QuestionedSituationDao qsDao = new QuestionedSituationDao(context);
	// count += qsDao.getCount();
	// ItelligenceDao telDao = new ItelligenceDao(context);
	// count += telDao.getCount();
	// WoodCuttingDao wcDao = new WoodCuttingDao(context);
	// count += wcDao.getCount();
	// WoodlandMiningDao wmDao = new WoodlandMiningDao(context);
	// count += wmDao.getCount();
	// SocialStatisticsDao ssDao = new SocialStatisticsDao(context);
	// count += ssDao.getCount();
	// // 三情——林情
	// LinQingDao lqd = new LinQingDao(context);
	// count += lqd.getCount();
	// // 三情——山情
	// MountConditionDao mcd = new MountConditionDao(context);
	// count += mcd.getCount();
	// // 三情——社情
	// SheConditionDao scd = new SheConditionDao(context);
	// count += scd.getCount();
	// // 巡逻登记轨迹坐标点
	// // if (flag) {
	// PatrolRegisterDao prDao = new PatrolRegisterDao(context);
	// count += prDao.getCount();
	// // count += getCount();
	// // }
	// return count;
	// }
}
