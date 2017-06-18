/**
 * 
 */
package com.yhkj.yhsx.forestpolicemobileterminal.widget;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author liupeng 日期输入控件
 * 
 */
public class DateInputEditText extends TextView {

	private Context context;

	private DatePickerDialog dpDialog;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private Date date = new Date();

	private Calendar calendar = Calendar.getInstance(Locale.CHINA);

	/**
	 * @param context
	 */
	public DateInputEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		// setText(sdf.format(date));
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public DateInputEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		// setText(sdf.format(date));
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public DateInputEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
		// setText(sdf.format(date));.
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		// TODO Auto-generated method stub
		super.onFocusChanged(focused, direction, previouslyFocusedRect);

//		if (focused) {
//			
//		}

	}

	DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker datePicker, int year, int month,
				int dayOfMonth) {
			try {
				date = sdf.parse(year + "-" + (month + 1) + "-" + dayOfMonth);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			setText(sdf.format(date));
			dpDialog.dismiss();
		}
	};
	
//	/**
//	 * 重载日期的选择
//	 */
//	public void setOnClickListener(OnClickListener l) {
//		super.setOnClickListener(l);
//		
//	};
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (!isEnabled()) {
					return super.onTouchEvent(event);
				}
				dpDialog = new DatePickerDialog(context, dateListener,
						calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
						calendar.get(Calendar.DAY_OF_MONTH));
				dpDialog.show();
				break;
			default:
				break;
		}
		return super.onTouchEvent(event);
	}
	
}
