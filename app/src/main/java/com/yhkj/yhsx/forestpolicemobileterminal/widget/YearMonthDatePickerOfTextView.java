package com.yhkj.yhsx.forestpolicemobileterminal.widget;

import java.util.Calendar;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * @author liupeng 日期输入控件
 * 
 */
public class YearMonthDatePickerOfTextView extends TextView {

	private Context context;

	private YearMonthDatePickerDialog dpDialog;

	private Calendar calendar = Calendar.getInstance(Locale.CHINA);

	/**
	 * 构造方法
	 * 
	 * @param context
	 */
	public YearMonthDatePickerOfTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	/**
	 * 构造方法
	 * 
	 * @param context
	 * @param attrs
	 */
	public YearMonthDatePickerOfTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	/**
	 * 构造方法
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public YearMonthDatePickerOfTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (!isEnabled()) {
				return super.onTouchEvent(event);
			}
			dpDialog = new YearMonthDatePickerDialog(context, DatePickerDialog.THEME_HOLO_LIGHT,
					dateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH));
			dpDialog.show();
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	};

	DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
			setText(year + " - " + (month + 1));
			dpDialog.dismiss();
		}
	};

}
