package com.yhkj.yhsx.forestpolicemobileterminal.widget;


import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

public class CheckBoxEditText extends EditText {

	private Context activity;

	public CheckBoxEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.activity = context;
	}

	public CheckBoxEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.activity = context;
	}

	public CheckBoxEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.activity = context;
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		// TODO Auto-generated method stub
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
	}

}
