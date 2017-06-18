package db;

import android.content.Context;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.Accessory;
import com.yhkj.yhsx.forestpolicemobileterminal.entity.LawsAndRegulations;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ActivityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import db.Database.LawsRegulations;

/**
 * 
 * @author ZhengTiantian 法律法规
 *
 */
public class LawsRegulationsDao {
	private Context context;
	private static LawsRegulationsDao lrDao;

	public LawsRegulationsDao(Context context) {
		this.context = context;
	}

	public static LawsRegulationsDao init(Context ct){
		if (lrDao == null) {
			lrDao = new LawsRegulationsDao(ct);
		}
		return lrDao;
		
	}
	/*
	 * 根据JOSN获取对象
	 */
	public ArrayList<LawsAndRegulations> getObject(String json)
			throws JSONException {
		JSONArray ja = null;
		ArrayList<LawsAndRegulations> lar = null;
		if (json != null && !json.equals("anyType{}")) {
			lar = new ArrayList<LawsAndRegulations>();
			ja = new JSONArray(json);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				LawsAndRegulations landr = new LawsAndRegulations();
				if (!jo.isNull(LawsRegulations.LAWSREGULATIONS_TITLE)) {
					landr.setTitle(jo
							.getString(LawsRegulations.LAWSREGULATIONS_TITLE));
				}
				if (!jo.isNull(LawsRegulations.LAWSREGULATIONS_CONTENT)) {
					landr.setContent(jo
							.getString(LawsRegulations.LAWSREGULATIONS_CONTENT));
				}
				if (!jo.isNull(LawsRegulations.LAWSREGULATIONS_ID)) {
					landr.setId(Integer.parseInt(jo
							.getString(LawsRegulations.LAWSREGULATIONS_ID)));
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

					landr.setLawAccessory(accessorys);

				}
				lar.add(landr);
			}
		}

		return lar;

	}

}
