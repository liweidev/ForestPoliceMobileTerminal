package com.yhkj.yhsx.forestpolicemobileterminal.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.yhkj.yhsx.forestpolicemobileterminal.R;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.ImageManager2;
import com.yhkj.yhsx.forestpolicemobileterminal.utils.NetworkConnections;

import java.util.ArrayList;

/**
 * 查询结果附件--适配器
 * 
 * @author xingyimin
 * 
 */
public class ShowAccessoryAdapter extends BaseAdapter {

	private ArrayList<String> bitmapList;

	private Activity activity;

	public ShowAccessoryAdapter(Activity acitvity, ArrayList<String> bitmapList) {
		super();
		// TODO Auto-generated constructor stub
		this.bitmapList = bitmapList;
		this.activity = acitvity;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bitmapList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return bitmapList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.i("liupeng", "accessory position    :     " + position);
		if (null == convertView) {
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.gridview_item_layout, parent, false);
		}
		ImageView iv = (ImageView) convertView.findViewById(R.id.nciv_pug);
		AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
				200, 200);
		iv.setLayoutParams(layoutParams);
		iv.setBackgroundResource(R.drawable.image_add_bg);
		String type = bitmapList.get(position).substring(
				bitmapList.get(position).lastIndexOf(".") + 1);
		iv.setTag(bitmapList.get(position));
		if (type.equals("jpg") || type.equals("png")) {
			iv.setAdjustViewBounds(true);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setPadding(10, 10, 10, 10);
			iv.setTag(position);
			if (bitmapList.get(position).indexOf("http://") != -1) {
				NetworkConnections.init(activity).setImageUrl(iv,
						bitmapList.get(position), 60, 60, R.drawable.upload,
						android.R.drawable.ic_menu_report_image);
			} else {
				ImageManager2.from(activity).displayImage(iv,
						bitmapList.get(position), -1);
			}
		} else if (type.equals("mp4") || type.equals("3gp")
				|| type.equals("avi") || type.equals("rmvb")
				|| type.equals("wmv")) {
			iv.setAdjustViewBounds(true);
			iv.setTag(position);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setPadding(24, 26, 24, 26);
			ImageManager2.from(activity).displayImage(iv,
					bitmapList.get(position), R.drawable.presence_video_online);
		} else if (type.equals("3gpp") || type.equals("amr")
				|| type.equals("ogg") || type.equals("pcm")
				|| type.equals("mp3")) {
			iv.setAdjustViewBounds(true);
			iv.setTag(position);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setPadding(24, 22, 24, 22);
			ImageManager2.from(activity).displayImage(iv,
					bitmapList.get(position), R.drawable.presence_audio_online);
		}
		return iv;

	}

}
