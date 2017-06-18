package com.yhkj.yhsx.forestpolicemobileterminal.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yhkj.yhsx.forestpolicemobileterminal.entity.OptionEntity;

import java.util.List;

/**
 * 下拉列表适配器
 * 
 * @author xingyimin
 * 
 */
public class SpinnerAdapter extends BaseAdapter {

	private Activity context;

	private List<OptionEntity> itemList;

	public SpinnerAdapter(Activity ct, List<OptionEntity> items) {
		super();
		// TODO Auto-generated constructor stub
		this.context = ct;
		this.itemList = items;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		TextView tv = new TextView(context);
		tv.setText(itemList.get(arg0).getDictName());
		tv.setTag(itemList.get(arg0).getDictValue());
		tv.setSingleLine(true);
		tv.setPadding(20, 20, 20, 20);
		return tv;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return itemList.get(arg0).getDictValue();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return itemList.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemList.size();
	}
	
	

}
