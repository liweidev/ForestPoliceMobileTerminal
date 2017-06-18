package db;

import android.content.Context;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.Notice;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.News;

/**
 * 新闻公告
 * 
 * @author xingyimin
 * 
 */
public class NewsDao {

	private Context context;
	private static NewsDao nDao;

	/**
	 * 
	 */
	public NewsDao(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public static NewsDao init(Context ct) {
		if (nDao == null) {
			nDao = new NewsDao(ct);
		}
		return nDao;

	}

	/**
	 * 根据对象获JSON
	 * 
	 * @param json
	 * @return
	 */
	public ArrayList<Notice> getObject(String json) throws JSONException {
		ArrayList<Notice> notices = null;
		JSONArray ja = null;
		if (json != null && !json.equals("[]")) {
			ja = new JSONArray(json);
			notices = new ArrayList<Notice>();
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				Notice notice = new Notice();
				if (!jo.isNull(News.NEWS_TITLE)) {
					notice.setTitle(jo.getString(News.NEWS_TITLE));
				}
				if (!jo.isNull(News.NEWS_CONTENT)) {
					notice.setText(jo.getString(News.NEWS_CONTENT));
				}

				if (!jo.isNull(News.NEWS_ID)) {
					try {
						notice.setNoId(Integer.parseInt(jo
								.getString(News.NEWS_ID)));
					} catch (Exception e) {
						e.printStackTrace();
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
							/*
							 * accessory.setAccessoryPath(url.replace("../..",
							 * "http://beijingwealth.vicp.cc:20940/"));
							 */
							accessory.setAccessoryPath(url.replace("../../",
									ActivityUtils.getServerWebApi(context)));
						}
						accessorys.add(accessory);
					}

					notice.setNoAccessory(accessorys);

				}
				notices.add(notice);
			}
		}
		return notices;
	}

	/**
	 * 根据JSON获对象集合
	 * 
	 * @param
	 * @return
	 */
	public ArrayList<Notice> getObject(JSONArray ja) throws JSONException {
		ArrayList<Notice> notices = null;
		// JSONArray ja = null;
		if (ja != null) {
			// ja = new JSONArray(json);
			notices = new ArrayList<Notice>();
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				Notice notice = new Notice();
				if (!jo.isNull("NoticeTitle")) {
					notice.setTitle(jo.getString("NoticeTitle"));
				}
				if (!jo.isNull("NoticeContent")) {
					notice.setText(jo.getString("NoticeContent"));
				}

				if (!jo.isNull("NoticeID")) {
					try {
						notice.setNoId(Integer.parseInt(jo
								.getString("NoticeID")));
					} catch (Exception e) {
						e.printStackTrace();
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
							/*
							 * accessory.setAccessoryPath(url.replace("../..",
							 * "http://beijingwealth.vicp.cc:20940/"));
							 */
							accessory.setAccessoryPath(url.replace("../../",
									ActivityUtils.getServerWebApi(context)));
						}
						accessorys.add(accessory);
					}

					notice.setNoAccessory(accessorys);

				}
				notices.add(notice);
			}
		}
		return notices;
	}

	public Notice getObjectByJSONObject(JSONObject object) throws JSONException {
		Notice notice = null;
		if (object != null) {
			notice = new Notice();
			if (!object.isNull("NoticeTitle")) {
				notice.setTitle(object.getString("NoticeTitle"));
			}
			if (!object.isNull("NoticeContent")) {
				notice.setText(object.getString("NoticeContent"));
			}

			if (!object.isNull("NoticeID")) {
				notice.setNoId(object.getInt("NoticeID"));
			}

			if (!object.isNull("affix")
					&& !object.getString("affix").equals("")) {
				ArrayList<Accessory> accessorys = new ArrayList<Accessory>();
				JSONArray jaaccessorys = new JSONArray(
						object.getString("affix"));
				for (int j = 0; j < jaaccessorys.length(); j++) {
					JSONObject joAttachment = jaaccessorys.getJSONObject(j);
					Accessory accessory = new Accessory();

					if (!joAttachment.isNull("url")) {
						String url = joAttachment.getString("url");
						/*
						 * accessory.setAccessoryPath(url.replace("../..",
						 * "http://beijingwealth.vicp.cc:20940/"));
						 */
						accessory.setAccessoryPath(url.replace("../../",
								ActivityUtils.getServerWebApi(context)));
					}
					accessorys.add(accessory);
				}

				notice.setNoAccessory(accessorys);
			}
		}
		return notice;
	}
}