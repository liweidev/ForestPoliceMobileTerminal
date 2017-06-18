package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.SocialStatisticsEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.Location;
import db.Database.SocialStatistics;

/**
 * 基础台账 - 社会情况统计
 * 
 * @author zhengtiantian
 * 
 */
public class SocialStatisticsDao {

	private Context context;
	private ForestDatabaseHelper dbHelper;
	private static SocialStatisticsDao sDao;

	public SocialStatisticsDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	public static SocialStatisticsDao init(Context ct) {
		if (sDao == null) {
			sDao = new SocialStatisticsDao(ct);
		}
		return sDao;

	}

	/**
	 * 插入数据
	 * 
	 * @param se
	 * @return
	 */
	public boolean insertInfo(SocialStatisticsEntity se) {
		boolean flag = false;
		int seId = -1;
		LocationDao lDao = new LocationDao(context);
		int locationId = lDao.insertInfo(se.getLocationId());
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		if (locationId > 0) {
			Cursor cursor=null;
			try {
				ContentValues cv = new ContentValues();
				cv.put(SocialStatistics.LOCATION_ID, locationId);
				cv.put(SocialStatistics.SOCIASTATISTICS_ID, se.getServerId());
				cv.put(SocialStatistics.UNIT, se.getUnit());
				cv.put(SocialStatistics.TOTALAREA, se.getTotalArea());
				cv.put(SocialStatistics.FAMILYCOUNT, se.getFamilyCount());
				cv.put(SocialStatistics.AGRICULTUREFAMILY,
						se.getAgricultureFamily());
				cv.put(SocialStatistics.NONAGRICULTURALFAMILY,
						se.getNonAgriculturalFamily());
				cv.put(SocialStatistics.PROTECTFAMILY, se.getProtectFamily());
				cv.put(SocialStatistics.TEMPORARYFAMILY,
						se.getTemporaryFamily());
				cv.put(SocialStatistics.POPULATIONCOUNT,
						se.getPopulationCount());
				cv.put(SocialStatistics.ARRICULTUREPOPULATION,
						se.getArriculturePopulation());
				cv.put(SocialStatistics.NONARRICULTUREALPOPULATION,
						se.getNonArriculturealPopulation());
				cv.put(SocialStatistics.TEMPORARYPOPULATION,
						se.getTemporaryPopulation());
				cv.put(SocialStatistics.EMPHASISUNITCOUNT,
						se.getEmphasisUnitCount());
				cv.put(SocialStatistics.WOODPROCESSINGUNIT,
						se.getWoodProcessingUnit());
				cv.put(SocialStatistics.WOODPURCHASEUNIT,
						se.getWoodPurchaseUnit());
				cv.put(SocialStatistics.QUARRYINGUNIT, se.getQuarryingUnit());
				cv.put(SocialStatistics.COLLIERYUNIT, se.getCollieryUnit());
				cv.put(SocialStatistics.FLAYSTONEUNIT, se.getFlaystoneUnit());
				cv.put(SocialStatistics.WILDANIMALUNIT, se.getWildAnimalUnit());
				cv.put(SocialStatistics.TRAVELUNIT, se.getTravelUnit());
				cv.put(SocialStatistics.CONTRACTOR, se.getContractor());
				cv.put(SocialStatistics.PRINCIPAL, se.getPrincipal());
				cv.put(SocialStatistics.GRAZINGPERSONNEL,
						se.getGrazingPersonnel());
				cv.put(SocialStatistics.EMPLOYER, se.getEmployer());
				cv.put(SocialStatistics.GRAVEMASTER, se.getGraveMaster());
				cv.put(SocialStatistics.MORONISMMANAGER,
						se.getMoronismManager());

				long ok = db
						.insert(SocialStatistics.SOCIA_TABLE_NAME, null, cv);
				if (-1 != ok) {
					flag = true;
				}
				String sql = "SELECT MAX("
						+ SocialStatistics.SOCIASTATISTICS_TABLE_ID + ") FROM "
						+ SocialStatistics.SOCIA_TABLE_NAME;
				 cursor= db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					seId = cursor.getInt(0);
				}
				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				flag = false;
			} finally {
				db.endTransaction();
				if (cursor != null) {
					cursor.close();
				}
			}
			if (se.getssAttachment() != null && se.getssAttachment().size() > 0) {
				AttachmentDao aDao = new AttachmentDao(context);
				for (int i = 0; i < se.getssAttachment().size(); i++) {
					se.getssAttachment().get(i)
							.setaId(SocialStatistics.CATALOG_ID);
					se.getssAttachment().get(i).setRowId(seId);
					flag = aDao.insertInfo(se.getssAttachment().get(i));
				}
			}
		}
		return flag;
	}

	/**
	 * 删除表
	 * 
	 * @param instantId
	 * @return
	 */
	public boolean delTable(String instantId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		try {
			int res = db.delete(SocialStatistics.SOCIA_TABLE_NAME,
					SocialStatistics.SOCIASTATISTICS_TABLE_ID + "=?",
					new String[] { instantId });
			if (res > 0) {
				isOk = true;
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		return isOk;
	}

	/**
	 * 获取本地所有
	 * 
	 * @return
	 */
	public ArrayList<SocialStatisticsEntity> getAllInfos() {
		ArrayList<SocialStatisticsEntity> infos = new ArrayList<SocialStatisticsEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		try {
			String sql = "select * from " + SocialStatistics.SOCIA_TABLE_NAME
					+ " Left join " + Location.LOCATION_TABLE_NAME + " where "
					+ SocialStatistics.SOCIA_TABLE_NAME + "."
					+ SocialStatistics.LOCATION_ID + "="
					+ Location.LOCATION_TABLE_NAME + "." + Location.LOCATION_ID;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				SocialStatisticsEntity so = new SocialStatisticsEntity();
				so.setSocialStatisticsID(cursor.getInt(cursor
						.getColumnIndex(SocialStatistics.SOCIASTATISTICS_TABLE_ID)));
				so.setServerId(cursor.getInt(cursor
						.getColumnIndex(SocialStatistics.SOCIASTATISTICS_ID)));
				so.setUnit(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.UNIT)));
				so.setTotalArea(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.TOTALAREA)));
				so.setFamilyCount(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.FAMILYCOUNT)));
				so.setAgricultureFamily(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.AGRICULTUREFAMILY)));
				so.setNonAgriculturalFamily(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.NONAGRICULTURALFAMILY)));
				so.setProtectFamily(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.PROTECTFAMILY)));
				so.setTemporaryFamily(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.TEMPORARYFAMILY)));
				so.setPopulationCount(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.POPULATIONCOUNT)));
				so.setArriculturePopulation(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.ARRICULTUREPOPULATION)));
				so.setNonArriculturealPopulation(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.NONARRICULTUREALPOPULATION)));
				so.setTemporaryPopulation(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.TEMPORARYPOPULATION)));
				so.setEmphasisUnitCount(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.EMPHASISUNITCOUNT)));
				so.setWoodProcessingUnit(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.WOODPROCESSINGUNIT)));
				so.setWoodPurchaseUnit(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.WOODPURCHASEUNIT)));
				so.setQuarryingUnit(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.QUARRYINGUNIT)));
				so.setCollieryUnit(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.COLLIERYUNIT)));
				so.setFlaystoneUnit(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.FLAYSTONEUNIT)));
				so.setWildAnimalUnit(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.WILDANIMALUNIT)));
				so.setTravelUnit(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.TRAVELUNIT)));
				so.setContractor(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.CONTRACTOR)));
				so.setPrincipal(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.PRINCIPAL)));
				so.setGrazingPersonnel(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.GRAZINGPERSONNEL)));
				so.setEmployer(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.EMPLOYER)));
				so.setGraveMaster(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.GRAVEMASTER)));
				so.setMoronismManager(cursor.getString(cursor
						.getColumnIndex(SocialStatistics.MORONISMMANAGER)));

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
				so.setLocationId(l);
				infos.add(so);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.endTransaction();
			if (cursor != null) {
				cursor.close();
			}
		}
		AttachmentDao dao = null;
		for (int i = 0; i < infos.size(); i++) {
			int rowId = infos.get(i).getSocialStatisticsID();
			dao = new AttachmentDao(context);
			ArrayList<Accessory> list = dao.getInfosById(
					SocialStatistics.CATALOG_ID, rowId);
			infos.get(i).setsAttachment(list);
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
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT (*) FROM "
				+ SocialStatistics.SOCIA_TABLE_NAME;
		try {
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				count = cursor.getInt(0);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
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
	 * 根据对象获取json
	 * 
	 * @param ss
	 * @param userId
	 * @return jo
	 * @throws JSONException
	 */
	public static String getJson(SocialStatisticsEntity ss, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("userId", userId);
			jo.put("catalogID", SocialStatistics.CATALOG_ID);
			jo.put(SocialStatistics.SOCIASTATISTICS_ID, ss.getServerId());
			jo.put(SocialStatistics.UNIT, ss.getUnit());
			jo.put(SocialStatistics.TOTALAREA, ss.getTotalArea());
			jo.put(SocialStatistics.FAMILYCOUNT, ss.getFamilyCount());
			jo.put(SocialStatistics.AGRICULTUREFAMILY,
					ss.getAgricultureFamily());
			jo.put(SocialStatistics.NONAGRICULTURALFAMILY,
					ss.getNonAgriculturalFamily());
			jo.put(SocialStatistics.PROTECTFAMILY, ss.getProtectFamily());
			jo.put(SocialStatistics.TEMPORARYFAMILY, ss.getTemporaryFamily());
			jo.put(SocialStatistics.POPULATIONCOUNT, ss.getPopulationCount());
			jo.put(SocialStatistics.ARRICULTUREPOPULATION,
					ss.getArriculturePopulation());
			jo.put(SocialStatistics.NONARRICULTUREALPOPULATION,
					ss.getNonArriculturealPopulation());
			jo.put(SocialStatistics.TEMPORARYPOPULATION,
					ss.getTemporaryPopulation());
			jo.put(SocialStatistics.EMPHASISUNITCOUNT,
					ss.getEmphasisUnitCount());
			jo.put(SocialStatistics.WOODPROCESSINGUNIT,
					ss.getWoodProcessingUnit());
			jo.put(SocialStatistics.WOODPURCHASEUNIT, ss.getWoodPurchaseUnit());
			jo.put(SocialStatistics.QUARRYINGUNIT, ss.getQuarryingUnit());
			jo.put(SocialStatistics.COLLIERYUNIT, ss.getCollieryUnit());
			jo.put(SocialStatistics.FLAYSTONEUNIT, ss.getFlaystoneUnit());
			jo.put(SocialStatistics.WILDANIMALUNIT, ss.getWildAnimalUnit());
			jo.put(SocialStatistics.TRAVELUNIT, ss.getTravelUnit());
			jo.put(SocialStatistics.CONTRACTOR, ss.getContractor());
			jo.put(SocialStatistics.PRINCIPAL, ss.getPrincipal());
			jo.put(SocialStatistics.GRAZINGPERSONNEL, ss.getGrazingPersonnel());
			jo.put(SocialStatistics.EMPLOYER, ss.getEmployer());
			jo.put(SocialStatistics.GRAVEMASTER, ss.getGraveMaster());
			jo.put(SocialStatistics.MORONISMMANAGER, ss.getMoronismManager());

			JSONObject lo = new JSONObject();
			lo.put(Location.ELEVATION, ss.getLocationId().getELEVATION());
			lo.put(Location.LATITUDE, ss.getLocationId().getLATITUDE());
			lo.put(Location.LONGITUDE, ss.getLocationId().getLONGITUDE());
			lo.put(Location.TIME, ss.getLocationId().getTIME());

			jo.put(SocialStatistics.LOCATION_ID, lo.toString());
			if (ss.getssAttachment() != null && ss.getssAttachment().size() > 0) {
				JSONArray ja = new JSONArray();
				for (int i = 0; i < ss.getssAttachment().size(); i++) {
					JSONObject json = AttachmentDao.getJson(ss.getssAttachment().get(
							i));
					ja.put(json);
				}
				jo.put("flist", ja.toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return jo.toString();
	}

	/**
	 * 根据对象获取API参数---社会情况
	 * 
	 * @param ss
	 * @param userId
	 * @return
	 */
	/*public static AjaxParams getParams(SocialStatisticsEntity ss, int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", String.valueOf(userId));
		params.put("catalogID", String.valueOf(SocialStatistics.CATALOG_ID));
		params.put(SocialStatistics.SOCIASTATISTICS_ID,
				String.valueOf(ss.getServerId()));
		if (!ss.getUnit().equals("")) {
			params.put(SocialStatistics.UNIT, ss.getUnit());
		}
		if (!ss.getTotalArea().equals("")) {
			params.put(SocialStatistics.TOTALAREA, ss.getTotalArea());
		}
		if (!ss.getFamilyCount().equals("")) {
			params.put(SocialStatistics.FAMILYCOUNT, ss.getFamilyCount());
		}
		if (!ss.getAgricultureFamily().equals("")) {
			params.put(SocialStatistics.AGRICULTUREFAMILY,
					ss.getAgricultureFamily());
		}
		if (!ss.getNonAgriculturalFamily().equals("")) {
			params.put(SocialStatistics.NONAGRICULTURALFAMILY,
					ss.getNonAgriculturalFamily());
		}
		if (!ss.getProtectFamily().equals("")) {
			params.put(SocialStatistics.PROTECTFAMILY, ss.getProtectFamily());
		}
		if (!ss.getTemporaryFamily().equals("")) {
			params.put(SocialStatistics.TEMPORARYFAMILY,
					ss.getTemporaryFamily());
		}
		if (!ss.getPopulationCount().equals("")) {
			params.put(SocialStatistics.POPULATIONCOUNT,
					ss.getPopulationCount());
		}
		if (!ss.getArriculturePopulation().equals("")) {
			params.put(SocialStatistics.ARRICULTUREPOPULATION,
					ss.getArriculturePopulation());
		}
		if (!ss.getNonArriculturealPopulation().equals("")) {
			params.put(SocialStatistics.NONARRICULTUREALPOPULATION,
					ss.getNonArriculturealPopulation());
		}
		if (!ss.getTemporaryPopulation().equals("")) {
			params.put(SocialStatistics.TEMPORARYPOPULATION,
					ss.getTemporaryPopulation());
		}
		if (!ss.getEmphasisUnitCount().equals("")) {
			params.put(SocialStatistics.EMPHASISUNITCOUNT,
					ss.getEmphasisUnitCount());
		}
		if (!ss.getWoodProcessingUnit().equals("")) {
			params.put(SocialStatistics.WOODPROCESSINGUNIT,
					ss.getWoodProcessingUnit());
		}
		if (!ss.getWoodPurchaseUnit().equals("")) {
			params.put(SocialStatistics.WOODPURCHASEUNIT,
					ss.getWoodPurchaseUnit());
		}
		if (!ss.getQuarryingUnit().equals("")) {
			params.put(SocialStatistics.QUARRYINGUNIT, ss.getQuarryingUnit());
		}
		if (!ss.getCollieryUnit().equals("")) {
			params.put(SocialStatistics.COLLIERYUNIT, ss.getCollieryUnit());
		}
		if (!ss.getFlaystoneUnit().equals("")) {
			params.put(SocialStatistics.FLAYSTONEUNIT, ss.getFlaystoneUnit());
		}
		if (!ss.getWildAnimalUnit().equals("")) {
			params.put(SocialStatistics.WILDANIMALUNIT, ss.getWildAnimalUnit());
		}
		if (!ss.getTravelUnit().equals("")) {
			params.put(SocialStatistics.TRAVELUNIT, ss.getTravelUnit());
		}
		if (!ss.getContractor().equals("")) {
			params.put(SocialStatistics.CONTRACTOR, ss.getContractor());
		}
		if (!ss.getPrincipal().equals("")) {
			params.put(SocialStatistics.PRINCIPAL, ss.getPrincipal());
		}
		if (!ss.getGrazingPersonnel().equals("")) {
			params.put(SocialStatistics.GRAZINGPERSONNEL,
					ss.getGrazingPersonnel());
		}
		if (!ss.getEmployer().equals("")) {
			params.put(SocialStatistics.EMPLOYER, ss.getEmployer());
		}
		if (!ss.getGraveMaster().equals("")) {
			params.put(SocialStatistics.GRAVEMASTER, ss.getGraveMaster());
		}
		if (!ss.getMoronismManager().equals("")) {
			params.put(SocialStatistics.MORONISMMANAGER,
					ss.getMoronismManager());
		}

		JSONObject lo = new JSONObject();

		if (ss.getssAttachment() != null && ss.getssAttachment().size() > 0) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < ss.getssAttachment().size(); i++) {
				JSONObject jo = AttachmentDao.getJson(ss.getssAttachment().get(
						i));
				ja.put(jo);
			}
			params.put("flist", ja.toString());
		}
		try {
			lo.put(Location.ELEVATION, ss.getLocationId().getELEVATION());
			lo.put(Location.LATITUDE, ss.getLocationId().getLATITUDE());
			lo.put(Location.LONGITUDE, ss.getLocationId().getLONGITUDE());
			lo.put(Location.TIME, ss.getLocationId().getTIME());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.put(SocialStatistics.LOCATION_ID, lo.toString());
		return params;
	}*/

	/**
	 * 根据json获取对象
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public ArrayList<SocialStatisticsEntity> getObject(String json)
			throws JSONException {
		ArrayList<SocialStatisticsEntity> socias = null;
		JSONArray ja = null;
		if (json != null && !json.equals("anyType{}")) {
			socias = new ArrayList<SocialStatisticsEntity>();
			ja = new JSONArray(json);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				SocialStatisticsEntity so = new SocialStatisticsEntity();
				if (!jo.isNull(SocialStatistics.SOCIASTATISTICS_ID)) {
					so.setServerId(jo
							.getInt(SocialStatistics.SOCIASTATISTICS_ID));
				}
				if (!jo.isNull(SocialStatistics.UNIT)) {
					so.setUnit(jo.getString(SocialStatistics.UNIT));
				}
				if (!jo.isNull(SocialStatistics.TOTALAREA)) {
					so.setTotalArea(jo.getString(SocialStatistics.TOTALAREA));
				}
				if (!jo.isNull(SocialStatistics.FAMILYCOUNT)) {
					so.setFamilyCount(jo
							.getString(SocialStatistics.FAMILYCOUNT));
				}
				if (!jo.isNull(SocialStatistics.AGRICULTUREFAMILY)) {
					so.setAgricultureFamily(jo
							.getString(SocialStatistics.AGRICULTUREFAMILY));
				}
				if (!jo.isNull(SocialStatistics.NONAGRICULTURALFAMILY)) {
					so.setNonAgriculturalFamily(jo
							.getString(SocialStatistics.NONAGRICULTURALFAMILY));
				}
				if (!jo.isNull(SocialStatistics.PROTECTFAMILY)) {
					so.setProtectFamily(jo
							.getString(SocialStatistics.PROTECTFAMILY));
				}
				if (!jo.isNull(SocialStatistics.TEMPORARYFAMILY)) {
					so.setTemporaryFamily(jo
							.getString(SocialStatistics.TEMPORARYFAMILY));
				}
				if (!jo.isNull(SocialStatistics.POPULATIONCOUNT)) {
					so.setPopulationCount(jo
							.getString(SocialStatistics.POPULATIONCOUNT));
				}
				if (!jo.isNull(SocialStatistics.ARRICULTUREPOPULATION)) {
					so.setArriculturePopulation(jo
							.getString(SocialStatistics.ARRICULTUREPOPULATION));
				}
				if (!jo.isNull(SocialStatistics.NONARRICULTUREALPOPULATION)) {
					so.setNonArriculturealPopulation(jo
							.getString(SocialStatistics.NONARRICULTUREALPOPULATION));
				}
				if (!jo.isNull(SocialStatistics.TEMPORARYPOPULATION)) {
					so.setTemporaryPopulation(jo
							.getString(SocialStatistics.TEMPORARYPOPULATION));
				}
				if (!jo.isNull(SocialStatistics.EMPHASISUNITCOUNT)) {
					so.setEmphasisUnitCount(jo
							.getString(SocialStatistics.EMPHASISUNITCOUNT));
				}
				if (!jo.isNull(SocialStatistics.WOODPROCESSINGUNIT)) {
					so.setWoodProcessingUnit(jo
							.getString(SocialStatistics.WOODPROCESSINGUNIT));
				}
				if (!jo.isNull(SocialStatistics.WOODPURCHASEUNIT)) {
					so.setWoodPurchaseUnit(jo
							.getString(SocialStatistics.WOODPURCHASEUNIT));
				}
				if (!jo.isNull(SocialStatistics.QUARRYINGUNIT)) {
					so.setQuarryingUnit(jo
							.getString(SocialStatistics.QUARRYINGUNIT));
				}
				if (!jo.isNull(SocialStatistics.COLLIERYUNIT)) {
					so.setCollieryUnit(jo
							.getString(SocialStatistics.COLLIERYUNIT));
				}
				if (!jo.isNull(SocialStatistics.FLAYSTONEUNIT)) {
					so.setFlaystoneUnit(jo
							.getString(SocialStatistics.FLAYSTONEUNIT));
				}
				if (!jo.isNull(SocialStatistics.WILDANIMALUNIT)) {
					so.setWildAnimalUnit(jo
							.getString(SocialStatistics.WILDANIMALUNIT));
				}
				if (!jo.isNull(SocialStatistics.TRAVELUNIT)) {
					so.setTravelUnit(jo.getString(SocialStatistics.TRAVELUNIT));
				}
				if (!jo.isNull(SocialStatistics.CONTRACTOR)) {
					so.setContractor(jo.getString(SocialStatistics.CONTRACTOR));
				}
				if (!jo.isNull(SocialStatistics.PRINCIPAL)) {
					so.setPrincipal(jo.getString(SocialStatistics.PRINCIPAL));
				}
				if (!jo.isNull(SocialStatistics.GRAZINGPERSONNEL)) {
					so.setGrazingPersonnel(jo
							.getString(SocialStatistics.GRAZINGPERSONNEL));
				}
				if (!jo.isNull(SocialStatistics.EMPLOYER)) {
					so.setEmployer(jo.getString(SocialStatistics.EMPLOYER));
				}
				if (!jo.isNull(SocialStatistics.GRAVEMASTER)) {
					so.setGraveMaster(jo
							.getString(SocialStatistics.GRAVEMASTER));
				}
				if (!jo.isNull(SocialStatistics.MORONISMMANAGER)) {
					so.setMoronismManager(jo
							.getString(SocialStatistics.MORONISMMANAGER));
				}
				if (!jo.isNull(SocialStatistics.LOCATION_ID)) {
					JSONObject joLocation = jo
							.getJSONObject(SocialStatistics.LOCATION_ID);
					if (joLocation.getDouble(Location.LATITUDE) != 0) {
						Loaction l = new Loaction();
						l.setELEVATION(joLocation.getDouble(Location.ELEVATION));
						l.setLATITUDE(joLocation.getDouble(Location.LATITUDE));
						l.setLONGITUDE(joLocation.getDouble(Location.LONGITUDE));
						l.setTIME(joLocation.getString(Location.TIME));
						so.setLocationId(l);
					}
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
					so.setsAttachment(accessorys);
				}
				socias.add(so);
			}
		}
		return socias;
	}
}
