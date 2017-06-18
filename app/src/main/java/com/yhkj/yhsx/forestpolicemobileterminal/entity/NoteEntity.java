package com.yhkj.yhsx.forestpolicemobileterminal.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;


import db.Database.Note;

/**
 * 便签实体类
 * 
 * @author xingyimin
 * 
 */
public class NoteEntity implements Serializable {

	private String content;
	private int noteId;
	private String logDate;
	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLogDate() {
		return logDate;
	}

	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public NoteEntity(String content, int userId) {
		this.content = content;
		this.userId = userId;
	}

	public NoteEntity(String content, int noteid, String logDate) {
		this.content = content;
		this.noteId = noteid;
		this.logDate = logDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 将json转换成List<NoteEntity>
	 * 
	 * @author liupeng
	 * @param json
	 *            JSONArray
	 * @return List<NoteEntity>
	 * @throws JSONException
	 */
	public static List<NoteEntity> getAllNoteForJson(JSONArray json)
			throws JSONException {
		List<NoteEntity> noteList = null;
		if (json != null) {
			noteList = new ArrayList<NoteEntity>();
			for (int i = 0; i < json.length(); i++) {
				NoteEntity entity = new NoteEntity(json.getJSONObject(i)
						.getString(Note.CONTENT), json.getJSONObject(i).getInt(
						Note.NOTE_ID), json.getJSONObject(i).getString(
						Note.NOTE_LOG_DATE));
				noteList.add(entity);
			}
		}
		return noteList;
	}

	public static String getJson(Context ct, NoteEntity noteList)
			throws JSONException {
		// JSONArray array = null;
		JSONObject obj = null;
		if (noteList != null) {
			// for (int i = 0; i < noteList.size(); i++) {
			obj = new JSONObject();
			obj.put("UsersId", noteList.getUserId());
			obj.put("Info", noteList.getContent());
			return obj.toString();
		} else {
			return obj.toString();
		}

	}

}
