package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.NoteEntity;

import java.util.ArrayList;
import java.util.List;

import db.Database.Note;

/**
 * 便签
 * 
 * @author xingyimin
 * 
 */
public class NoteDao {

	private ForestDatabaseHelper dbHelper;

	public NoteDao(Context context) {
		// TODO Auto-generated constructor stub
		this.dbHelper = new ForestDatabaseHelper(context);
	}

	public boolean insertNote(NoteEntity entity) {

		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//

		long result = -1;
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put(Note.CONTENT, entity.getContent());
			contentValues.put(Note.USER_ID, entity.getUserId());
			result = db.insert(Note.NOTE_TABLE_NAME, null, contentValues);
			db.setTransactionSuccessful();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			db.endTransaction();
		}

		return result > 0 ? true : false;

	}

	public List<NoteEntity> getAllNote() {
		List<NoteEntity> noteList = null;
		String sql = "select * from " + Note.NOTE_TABLE_NAME;
		Cursor cursor = null;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//

		try {
			cursor = db.rawQuery(sql, null);
			noteList = new ArrayList<NoteEntity>();
			while (cursor.moveToNext()) {
				NoteEntity entity = new NoteEntity(cursor.getString(cursor
						.getColumnIndex(Note.CONTENT)), cursor.getInt(cursor
						.getColumnIndex(Note.USER_ID)));
				entity.setNoteId(cursor.getInt(cursor
						.getColumnIndex(Note.NOTE_ID)));
				noteList.add(entity);
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
		return noteList;
	}

	/**
	 * 删表
	 */
	public boolean delTable(int noteId) {
		boolean isOk = false;
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();
		try {
			int res = db.delete(Note.NOTE_TABLE_NAME, Note.NOTE_ID + "=?",
					new String[] { noteId + "" });
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

	public int getCount() {
		SQLiteDatabase db = dbHelper.getInstence();
		db.beginTransaction();//
		Cursor cursor = null;
		int count = 0;
		String sql = "SELECT COUNT(*) FROM " + Note.NOTE_TABLE_NAME;

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

	/**
	 * 根据内容获取API参数
	 * 
	 * @param
	 * @param
	 * @return
	 */
	/*public static AjaxParams getParams(NoteEntity note) {
		AjaxParams params = new AjaxParams();
		params.put("UsersId", note.getUserId() + "");
		if (note.getContent() != null && !note.getContent().equals("")) {
			params.put("Info", note.getContent());
		}
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日   HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		params.put("LogDate", formatter.format(curDate));
		return params;

	}*/
}
