package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Loaction;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.PublicSecurityManagementEntity;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.Location;
import db.Database.PublicSecurityManagement;

/**
 * 派出所治安管理检查
 * 
 * @author xingyimin
 * 
 */
public class PublicSecurityManagementDao {
	private ForestDatabaseHelper dbHelper;

	private Context context;

	public PublicSecurityManagementDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	/*
	 * 插入
	 */
	public boolean insertInfo(PublicSecurityManagementEntity psme) {

		boolean flag = false;

		int publicSecurityManagementId = -1;
		LocationDao ldao = new LocationDao(context);
		int locationId = ldao.insertInfo(psme.getLocation());
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//

		if (locationId > 0) {
			Cursor cursor = null;
			try {
				ContentValues contentValues = new ContentValues();
				contentValues.put(PublicSecurityManagement.NOTE, psme
						.getPsmLegend().toString());
				contentValues.put(
						PublicSecurityManagement.PSM_BUSINESS_PRACTICE,
						psme.getBusinessPractice());
				contentValues.put(
						PublicSecurityManagement.PSM_BUSINESS_PROJECT,
						psme.getBusinessProject());
				contentValues.put(PublicSecurityManagement.PSM_CHECK_THE_TIME,
						psme.getCheckTheTime().toString());
				contentValues.put(
						PublicSecurityManagement.PSM_CONTENT_AND_PROBLEMS,
						psme.getContentAndProblems());
				contentValues.put(PublicSecurityManagement.PSM_HEAD,
						psme.getHead());
				contentValues.put(PublicSecurityManagement.PSM_IMSPECTORS,
						psme.getInspectors());
				contentValues.put(
						PublicSecurityManagement.PSM_LEGAL_REPRESENTATIVE,
						psme.getLegalRepresentative());
				contentValues.put(PublicSecurityManagement.PSM_OWNER_OR_HEAD,
						psme.getOwnerOrHead());
				contentValues.put(
						PublicSecurityManagement.PSM_RECTIFICATION_OPINIONS,
						psme.getRectificationOpinions());
				contentValues.put(PublicSecurityManagement.PSM_SIGNATRUE_DATE,
						psme.getDate());
				contentValues.put(PublicSecurityManagement.PSM_UNITADD,
						psme.getUnitAdd());
				contentValues.put(PublicSecurityManagement.PSM_UNITNAME,
						psme.getUnitName());
				contentValues.put(PublicSecurityManagement.LOCATION_ID,
						locationId);
				contentValues.put(PublicSecurityManagement.le_signature_bool, psme.getLe_signature_bool());
				long ok = db
						.insert(PublicSecurityManagement.PUBLIC_SECURITY_MANAGEMENT_TABLE_NAME,
								null, contentValues);
				// System.out.println("insert is complete..." + ok);
				if (-1 != ok) {
					flag = true;
				}
				String sql = "SELECT MAX("
						+ PublicSecurityManagement.PUBLIC_SECURITY_MANAGEMENT_ID
						+ ") FROM "
						+ PublicSecurityManagement.PUBLIC_SECURITY_MANAGEMENT_TABLE_NAME;
				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					publicSecurityManagementId = cursor.getInt(0);
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
			if (psme.getPsmAccessory() != null
					&& psme.getPsmAccessory().size() > 0) {
				AttachmentDao dao = new AttachmentDao(context);
				for (int i = 0; i < psme.getPsmAccessory().size(); i++) {
					psme.getPsmAccessory()
							.get(i)
							.setTableId(
									PublicSecurityManagement.PUBLIC_SECURITY_MANAGEMENT_TABLE_ID);
					psme.getPsmAccessory().get(i)
							.setRowId(publicSecurityManagementId);
					flag = dao.insertInfo(psme.getPsmAccessory().get(i));
				}
			}
		}
		return flag;

	}

	/**
	 * 删表
	 */
	public boolean delTable(String psmId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db
					.delete(PublicSecurityManagement.PUBLIC_SECURITY_MANAGEMENT_TABLE_NAME,
							PublicSecurityManagement.PUBLIC_SECURITY_MANAGEMENT_ID
									+ "=?", new String[] { psmId });
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
	public ArrayList<PublicSecurityManagementEntity> getAllInfos() {
		ArrayList<PublicSecurityManagementEntity> infos = new ArrayList<PublicSecurityManagementEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		try {

			String sql = "select * from "
					+ PublicSecurityManagement.PUBLIC_SECURITY_MANAGEMENT_TABLE_NAME
					+ " Left join "
					+ Location.LOCATION_TABLE_NAME
					+ " where "
					+ PublicSecurityManagement.PUBLIC_SECURITY_MANAGEMENT_TABLE_NAME
					+ "." + PublicSecurityManagement.LOCATION_ID + " = "
					+ Location.LOCATION_TABLE_NAME + "." + Location.LOCATION_ID;
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				PublicSecurityManagementEntity info = new PublicSecurityManagementEntity();
				info.setPsmLegend(cursor.getString(cursor
						.getColumnIndex(PublicSecurityManagement.NOTE)));
				info.setBusinessPractice(cursor.getString(cursor
						.getColumnIndex(PublicSecurityManagement.PSM_BUSINESS_PRACTICE)));
				info.setBusinessProject(cursor.getString(cursor
						.getColumnIndex(PublicSecurityManagement.PSM_BUSINESS_PROJECT)));
				info.setCheckTheTime(cursor.getString(cursor
						.getColumnIndex(PublicSecurityManagement.PSM_CHECK_THE_TIME)));
				info.setContentAndProblems(cursor.getString(cursor
						.getColumnIndex(PublicSecurityManagement.PSM_CONTENT_AND_PROBLEMS)));
				info.setDate(cursor.getString(cursor
						.getColumnIndex(PublicSecurityManagement.PSM_SIGNATRUE_DATE)));
				info.setHead(cursor.getString(cursor
						.getColumnIndex(PublicSecurityManagement.PSM_HEAD)));
				info.setInspectors(cursor.getString(cursor
						.getColumnIndex(PublicSecurityManagement.PSM_IMSPECTORS)));
				info.setLegalRepresentative(cursor.getString(cursor
						.getColumnIndex(PublicSecurityManagement.PSM_LEGAL_REPRESENTATIVE)));
				info.setOwnerOrHead(cursor.getString(cursor
						.getColumnIndex(PublicSecurityManagement.PSM_OWNER_OR_HEAD)));
				info.setLe_signature_bool(cursor.getString(cursor
						.getColumnIndex(PublicSecurityManagement.le_signature_bool)));
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
				info.setPsmId(cursor.getInt(cursor
						.getColumnIndex(PublicSecurityManagement.PUBLIC_SECURITY_MANAGEMENT_ID)));
				info.setRectificationOpinions(cursor.getString(cursor
						.getColumnIndex(PublicSecurityManagement.PSM_RECTIFICATION_OPINIONS)));
				info.setUnitAdd(cursor.getString(cursor
						.getColumnIndex(PublicSecurityManagement.PSM_UNITADD)));

				info.setUnitName(cursor.getString(cursor
						.getColumnIndex(PublicSecurityManagement.PSM_UNITNAME)));

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
			int rowid = infos.get(i).getPsmId();
			dao = new AttachmentDao(context);
			ArrayList<Accessory> list = dao
					.getInfosById(
							PublicSecurityManagement.PUBLIC_SECURITY_MANAGEMENT_TABLE_ID,
							rowid);
			infos.get(i).setPsmAccessory(list);
		}

		return infos;
	}

	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM "
				+ PublicSecurityManagement.PUBLIC_SECURITY_MANAGEMENT_TABLE_NAME;

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
	public static String getJson(PublicSecurityManagementEntity psm, int userId)
			throws JSONException {
		JSONObject jo = new JSONObject();
		try {
			jo.put("userId", userId);
			jo.put("tableId",
					PublicSecurityManagement.PUBLIC_SECURITY_MANAGEMENT_TABLE_ID);
			jo.put(PublicSecurityManagement.PSM_UNITNAME, psm.getUnitName());
			jo.put(PublicSecurityManagement.PSM_UNITADD, psm.getUnitAdd());
			jo.put(PublicSecurityManagement.PSM_SIGNATRUE_DATE, psm.getDate());
			jo.put(PublicSecurityManagement.PSM_RECTIFICATION_OPINIONS,
					psm.getRectificationOpinions());
			jo.put(PublicSecurityManagement.PSM_OWNER_OR_HEAD,
					psm.getOwnerOrHead());
			jo.put(PublicSecurityManagement.PSM_LEGAL_REPRESENTATIVE,
					psm.getLegalRepresentative());
			jo.put(PublicSecurityManagement.PSM_IMSPECTORS, psm.getInspectors());
			jo.put(PublicSecurityManagement.PSM_HEAD, psm.getHead());
			jo.put(PublicSecurityManagement.PSM_CONTENT_AND_PROBLEMS,
					psm.getContentAndProblems());
			jo.put(PublicSecurityManagement.PSM_CHECK_THE_TIME,
					psm.getCheckTheTime());
			jo.put("le_signature_bool", psm.getLe_signature_bool());
			jo.put(PublicSecurityManagement.PSM_BUSINESS_PROJECT,
					psm.getBusinessProject());
			jo.put(PublicSecurityManagement.PSM_BUSINESS_PRACTICE,
					psm.getBusinessPractice());
			jo.put(PublicSecurityManagement.NOTE, psm.getPsmLegend());

			JSONObject joLocation = new JSONObject();
			joLocation
					.put(Location.ELEVATION, psm.getLocation().getELEVATION());
			joLocation.put(Location.LATITUDE, psm.getLocation().getLATITUDE());
			joLocation
					.put(Location.LONGITUDE, psm.getLocation().getLONGITUDE());

			jo.put(PublicSecurityManagement.LOCATION_ID, joLocation.toString());
			if (psm.getPsmAccessory() != null && psm.getPsmAccessory().size() > 0) {
				JSONArray ja = new JSONArray();
				JSONArray psm_signature = new JSONArray();
				for (int i = 0; i < psm.getPsmAccessory().size(); i++) {
					JSONObject json = AttachmentDao.getJson(psm.getPsmAccessory()
							.get(i));
					if (psm.getLe_signature_bool().equals("1")&&i==psm.getPsmAccessory().size()-1) {
						psm_signature.put(json);
					}else{
						ja.put(json);
					}
					
				}
				jo.put("psm_signature", psm_signature.toString());
				jo.put("flist", ja.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jo.toString();
	}

	/**
	 * 根据对象获取API参数
	 * 
	 * @param psm
	 * @param userId
	 * @return
	 */
	/*public static AjaxParams getParams(PublicSecurityManagementEntity psm,
			int userId) {
		AjaxParams params = new AjaxParams();
		params.put("userId", userId + "");
		params.put("tableId",
				PublicSecurityManagement.PUBLIC_SECURITY_MANAGEMENT_TABLE_ID
						+ "");
		if (psm.getUnitName() != null && !psm.getUnitName().equals("")) {
			params.put(PublicSecurityManagement.PSM_UNITNAME, psm.getUnitName());
		}
		if (psm.getUnitAdd() != null && !psm.getUnitAdd().equals("")) {
			params.put(PublicSecurityManagement.PSM_UNITADD, psm.getUnitAdd());
		}
		if (psm.getDate() != null && !psm.getDate().equals("")) {
			params.put(PublicSecurityManagement.PSM_SIGNATRUE_DATE,
					psm.getDate());
		}
		if (psm.getRectificationOpinions() != null
				&& !psm.getRectificationOpinions().equals("")) {
			params.put(PublicSecurityManagement.PSM_RECTIFICATION_OPINIONS,
					psm.getRectificationOpinions());
		}
		if (psm.getOwnerOrHead() != null && !psm.getOwnerOrHead().equals("")) {
			params.put(PublicSecurityManagement.PSM_OWNER_OR_HEAD,
					psm.getOwnerOrHead());
		}
		if (psm.getLegalRepresentative() != null
				&& !psm.getLegalRepresentative().equals("")) {
			params.put(PublicSecurityManagement.PSM_LEGAL_REPRESENTATIVE,
					psm.getLegalRepresentative());
		}
		if (psm.getInspectors() != null && !psm.getInspectors().equals("")) {
			params.put(PublicSecurityManagement.PSM_IMSPECTORS,
					psm.getInspectors());
		}
		if (psm.getHead() != null && !psm.getHead().equals("")) {
			params.put(PublicSecurityManagement.PSM_HEAD, psm.getHead());
		}
		if (psm.getContentAndProblems() != null
				&& !psm.getContentAndProblems().equals("")) {
			params.put(PublicSecurityManagement.PSM_CONTENT_AND_PROBLEMS,
					psm.getContentAndProblems());
		}
		if (psm.getCheckTheTime() != null && !psm.getCheckTheTime().equals("")) {
			params.put(PublicSecurityManagement.PSM_CHECK_THE_TIME,
					psm.getCheckTheTime());
		}
		if (psm.getBusinessProject() != null
				&& !psm.getBusinessProject().equals("")) {
			params.put(PublicSecurityManagement.PSM_BUSINESS_PROJECT,
					psm.getBusinessProject());
		}
		if (psm.getBusinessPractice() != null
				&& !psm.getBusinessPractice().equals("")) {
			params.put(PublicSecurityManagement.PSM_BUSINESS_PRACTICE,
					psm.getBusinessPractice());
		}
		if (psm.getPsmLegend() != null && !psm.getPsmLegend().equals("")) {
			params.put(PublicSecurityManagement.NOTE, psm.getPsmLegend());
		}
		if (psm.getLocation() != null) {
			try {
				JSONObject lo = new JSONObject();
				lo.put(Location.ELEVATION, psm.getLocation().getELEVATION());
				lo.put(Location.LATITUDE, psm.getLocation().getLATITUDE());
				lo.put(Location.LONGITUDE, psm.getLocation().getLONGITUDE());
				lo.put(Location.TIME, psm.getLocation().getTIME());
				params.put(Breed.LOCATION_ID, lo.toString());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		params.put("le_signature_bool", psm.getLe_signature_bool());
		if (psm.getPsmAccessory() != null && psm.getPsmAccessory().size() > 0) {
			JSONArray ja = new JSONArray();
			JSONArray psm_signature = new JSONArray();
			for (int i = 0; i < psm.getPsmAccessory().size(); i++) {
				JSONObject json = AttachmentDao.getJson(psm
						.getPsmAccessory().get(i));
				if (psm.getLe_signature_bool().equals("1")&&i==psm.getPsmAccessory().size()-1) {
					psm_signature.put(json);
				}else{
					ja.put(json);
					
				}
			}
			params.put("flist", ja.toString());
			params.put("psm_signature", psm_signature.toString());
		}
		
		
		params.put("fileType", "1");
//		NetUtil.print(params.toString());
		return params;
	}*/
}
