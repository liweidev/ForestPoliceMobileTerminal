package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.NoticeTimeEntity;

import java.util.ArrayList;
import java.util.List;

import db.Database.NoticeTime;

/**
 * 延时通知 
 * 
 * @author xingyimin
 * 
 */
public class NoticeTimeDao {
	private ForestDatabaseHelper dbHelper;
	private Context context;

	public NoticeTimeDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
		this.context = context;
	}

	/**
	 * 向表中插入一条数据
	 * 
	 * @param notice
	 * @return
	 */
	public boolean insertInfo(NoticeTimeEntity notice) {
		boolean flag = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(NoticeTime.NOTICE_NEWS_ID, notice.getNewsId());
			contentValues.put(NoticeTime.NOTICE_TYPE, notice.getNewsType());
			contentValues.put(NoticeTime.NOTICE_TIME, notice.getNewsTime());
			contentValues.put(NoticeTime.NOTICE_CONTENT,
					notice.getNewsContent());
			long ok = db.insert(NoticeTime.NOTICE_TABLE_NAME, null,
					contentValues);
			if (-1 != ok) {
				flag = true;
			}
			String sql = "SELECT MAX(" + NoticeTime.NOTICE_TABLE_ID + ") FROM "
					+ NoticeTime.NOTICE_TABLE_NAME;

			Cursor cursor = db.rawQuery(sql, null);
			flag = cursor.moveToNext();
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

	/**
	 * 更新数据
	 * 
	 * @param notice
	 * @return
	 */
	public boolean updateRawByNews(NoticeTimeEntity notice) {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		ContentValues contentValues = new ContentValues();
		contentValues.put(NoticeTime.NOTICE_TIME, notice.getNewsTime());
		try {
			int res = db.update(
					NoticeTime.NOTICE_TABLE_NAME,
					contentValues,
					NoticeTime.NOTICE_NEWS_ID + " =? and "
							+ NoticeTime.NOTICE_TYPE + " =?",
					new String[] { notice.getNewsId() + "",
							notice.getNewsType() + "" });
			db.setTransactionSuccessful();
			db.endTransaction();
			if (res != -1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}finally{
			db.close();
		}
	}

	/**
	 * 根据Id，从表中删除一条数据
	 * 
	 * @param moeId
	 * @return
	 */
	public boolean delRawById(String moeId) {
		boolean isOK = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		try {
			int res = db.delete(NoticeTime.NOTICE_TABLE_NAME,
					NoticeTime.NOTICE_NEWS_ID + "=?", new String[] { moeId });
			if (res > 0) {
				isOK = true;
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}
		return isOK;
	}

	/**
	 * 获取本地数据库所有数据
	 * 
	 * @return 数据集合
	 */
	public List<NoticeTimeEntity> getAllInfors(int type) {
		List<NoticeTimeEntity> noticeList = new ArrayList<NoticeTimeEntity>();
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		Cursor cursor = null;
		String sql = "select * from " + NoticeTime.NOTICE_TABLE_NAME
				+ " where " + NoticeTime.NOTICE_TYPE + "=" + type;
		cursor = db.rawQuery(sql, null);

		while (cursor.moveToNext()) {
			NoticeTimeEntity notice = new NoticeTimeEntity();
			notice.setId(cursor.getInt(cursor
					.getColumnIndex(NoticeTime.NOTICE_TABLE_ID)));
			notice.setNewsType(type);
			notice.setNewsId(cursor.getInt(cursor
					.getColumnIndex(NoticeTime.NOTICE_NEWS_ID)));
			notice.setNewsTime(cursor.getString(cursor
					.getColumnIndex(NoticeTime.NOTICE_TIME)));
			notice.setNewsContent(cursor.getString(cursor
					.getColumnIndex(NoticeTime.NOTICE_CONTENT)));
			noticeList.add(notice);
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		if (cursor != null) {
			cursor.close();
		}
		return noticeList;
	}
}
