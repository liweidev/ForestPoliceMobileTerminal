package com.yhkj.yhsx.forestpolicemobileterminal.widget;


import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhkj.yhsx.forestpolicemobileterminal.R;


public class HeadTitleView extends LinearLayout implements OnClickListener {

	private TextView tvTitleText;
	private Activity activity;

	public HeadTitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.activity = (Activity) context;
		// TODO Auto-generated constructor stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.head_title, this);
		tvTitleText = (TextView) findViewById(R.id.tvTitleText);
		// if (activity.getClass() == FragmentNewsActivity.class) {
		// } else if (activity.getClass() == NewDetailActivity.class) {
		// } else if (activity.getClass() == CollectListActivity.class) {
		// tvTitleText.setText(R.string.myCollect);
		// } else if (activity.getClass() == RegistrationActivity.class) {
		// }
	}

	public HeadTitleView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.activity = (Activity) context;
	}

	public void setTltleText(String text) {
		tvTitleText.setText(text);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
