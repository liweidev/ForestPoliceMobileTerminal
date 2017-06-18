/**
 * 
 */
package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.Attachment;
import db.Database.Location;

/**
 * @author liupeng 附件
 */
public class AttachmentDao {

	private ForestDatabaseHelper dbHelper;

	private Context context;

	/**
	 * 
	 */
	public AttachmentDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	/*
	 * 插入
	 */
	public boolean insertInfo(Accessory accessory) {

		boolean flag = false;

		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//

		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(Attachment.FILE_PATH,
					accessory.getAccessoryPath());
			contentValues.put(Attachment.FILE_TYPE, accessory.getFileType());
			contentValues.put(Attachment.TABLE_ID, accessory.getTableId());
			contentValues.put(Attachment.ROW_ID, accessory.getRowId());

			long ok = db.insert(Attachment.ATTACHMENT_TABLE_NAME, null,
					contentValues);
			// System.out.println("insert is complete..." + ok);
			if (-1 != ok) {
				flag = true;
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		} finally {
			db.endTransaction();
		}
		return flag;

	}

	/*
	 * 插入并返回ID
	 */
	public int insertInfoAndReturnId(Accessory accessory) {

		int flag = -1;
		String sql = "select  max(" + Location.LOCATION_ID + ") from "
				+ Location.LOCATION_TABLE_NAME;
		Cursor cursor = null;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//

		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(Attachment.FILE_PATH,
					accessory.getAccessoryPath());
			contentValues.put(Attachment.FILE_TYPE, accessory.getFileType());
			contentValues.put(Attachment.TABLE_ID, accessory.getTableId());
			contentValues.put(Attachment.ROW_ID, accessory.getRowId());

			long ok = db.insert(Attachment.ATTACHMENT_TABLE_NAME, null,
					contentValues);
			// System.out.println("insert is complete..." + ok);
			if (-1 != ok) {
				cursor = db.rawQuery(sql, null);
				while (cursor.moveToNext()) {
					flag = cursor.getInt(cursor.getInt(0));
				}
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = -1;
		} finally {
			db.endTransaction();
			if (cursor != null) {
				cursor.close();
			}
		}
		return flag;

	}

	/**
	 * 删表
	 */
	public boolean delTable(int attachmentId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		try {
			int res = db.delete(Attachment.ATTACHMENT_TABLE_NAME, Attachment.ATTACHMENT_ID+"=?"	, new String[]{attachmentId+""});
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
	public ArrayList<Accessory> getInfosById(int tableId, int rowId) {
		ArrayList<Accessory> infos = new ArrayList<Accessory>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		try {

			String sql = "select * from " + Attachment.ATTACHMENT_TABLE_NAME
					+ " where " + Attachment.TABLE_ID + "=" + tableId + " AND "
					+ Attachment.ROW_ID + "=" + rowId + "";
			if (ActivityUtils.ISDEBUG) {
			System.out.println(sql);
			}
			cursor = db.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				Accessory info = new Accessory();
				info.setaId(cursor.getInt(cursor
						.getColumnIndex(Attachment.ATTACHMENT_ID)));
				info.setAccessoryPath(cursor.getString(cursor
						.getColumnIndex(Attachment.FILE_PATH)));
				info.setFileType(cursor.getString(cursor
						.getColumnIndex(Attachment.FILE_TYPE)));
				info.setRowId(cursor.getInt(cursor
						.getColumnIndex(Attachment.ROW_ID)));
				info.setTableId(cursor.getInt(cursor
						.getColumnIndex(Attachment.TABLE_ID)));

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
	
	public static JSONObject getJson(Accessory acc){
		JSONObject jo = new JSONObject();
		String type = acc.getAccessoryPath().substring(acc.getAccessoryPath().lastIndexOf(".")+1);
		String baos = null;
		try {
			if (acc.getAccessoryPath().indexOf("http://") != -1) {
				baos = acc.getAccessoryPath();
				type = "net";
			}else{
				baos = ActivityUtils.encodeBase64File(acc.getAccessoryPath());
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if (type != null && type.equals("jpg")) {
				jo.put("fileType", 1);
			}else if (type != null && (type.equals("3gpp") || type.equals("amr") || type.equals("ogg"))) {
				jo.put("fileType", 2);
			}else if (type != null && (type.equals("mp4") || type.equals("3gp") || type.equals("avi"))) {
				jo.put("fileType", 2);
			}else if(type != null && type.equals("net")){
				jo.put("fileType", 0);
			}
			jo.put("fileName", acc.getAccessoryPath().substring(acc.getAccessoryPath().lastIndexOf("/")+1));
			jo.put("fileBase64", baos);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return jo;
		}
}
