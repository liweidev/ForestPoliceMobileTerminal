/**
 * 
 */
package com.yhkj.yhsx.forestpolicemobileterminal.widget;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author liupeng 日期输入控件
 * 
 */
public final class DateAndTimeInputEditText extends TextView {

	private Context context;

	private DatePickerDialog dpDialog;

	private TimePickerDialog tpDialog;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private SimpleDateFormat timeSdf = new SimpleDateFormat("HH : mm");

	private Date date = new Date();

	private Calendar calendar = Calendar.getInstance(Locale.CHINA);

	/**
	 * @param context
	 */
	public DateAndTimeInputEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public DateAndTimeInputEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public DateAndTimeInputEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		// TODO Auto-generated method stub
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
	}

	DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month);
			calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			
			try {
				date = sdf.parse(year + "-" + (month + 1) + "-" + dayOfMonth);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setText(sdf.format(date));
			dpDialog.dismiss();
		}
	};

	private TimePickerDialog.OnTimeSetListener otsl = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
			calendar.set(Calendar.MINUTE, minute);
			if (getText().length() < 12) {
				try {
					date = timeSdf.parse(hourOfDay + " : " + minute);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setText(getText() + " " + timeSdf.format(date));
				tpDialog.dismiss();
			}
		}
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (!isEnabled()) {
					return super.onTouchEvent(event);
				}
				
				dpDialog = new DatePickerDialog(context, dateListener, calendar.get(Calendar.YEAR),
						calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
				dpDialog.show();
				dpDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						// TODO Auto-generated method stub
						try {
							date = sdf.parse(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						setText(sdf.format(date));
						
						if (!dpDialog.isShowing()) {
							tpDialog = new TimePickerDialog(context, otsl, calendar.get(Calendar.HOUR_OF_DAY),
									calendar.get(Calendar.MINUTE), true);
							tpDialog.show();
							tpDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
								@Override
								public void onDismiss(DialogInterface dialog) {
									// TODO Auto-generated method stub
									if (getText().length() < 12) {
										try {
											date = timeSdf.parse(calendar.get(Calendar.HOUR_OF_DAY) + " : " + calendar.get(Calendar.MINUTE));
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										setText(getText() + " " + timeSdf.format(date));
									}
								}
							});
						}
					}
				});
				break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

}
