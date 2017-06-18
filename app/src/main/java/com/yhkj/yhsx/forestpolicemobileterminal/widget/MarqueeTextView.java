package com.yhkj.yhsx.forestpolicemobileterminal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 滚动文本 TextView
 */
public class MarqueeTextView extends TextView{

	public MarqueeTextView(Context context) {
		  super(context);
		 }
		 
		 public MarqueeTextView(Context context, AttributeSet attrs) {
		  super(context, attrs);
		 }

		 public MarqueeTextView(Context context, AttributeSet attrs,
		   int defStyle) {
		  super(context, attrs, defStyle);
		 }
		 
		 @Override
		 public boolean isFocused() {
		  return true;
		 }

}
