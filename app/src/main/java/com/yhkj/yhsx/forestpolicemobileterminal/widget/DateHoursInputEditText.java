/**
 * 
 */
package com.yhkj.yhsx.forestpolicemobileterminal.widget;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.DatePicker;
import android.widget.EditText;
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
public class DateHoursInputEditText extends EditText {

	private Context context;

	private DatePickerDialog dpDialog;
	private TimePickerDialog tpDialog;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private SimpleDateFormat timeSdf = new SimpleDateFormat("HH");
	private Date date = new Date();

	private Calendar calendar = Calendar.getInstance(Locale.CHINA);

	/**
	 * @param context
	 */
	public DateHoursInputEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		// setText(sdf.format(date));
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public DateHoursInputEditText(Context context, AttributeSet attrs) {
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
	public DateHoursInputEditText(Context context, AttributeSet attrs, int defStyle) {
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

		if (focused) {
			dpDialog = new DatePickerDialog(context, dateListener,
					calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH));
			dpDialog.show();
		}

	}

	DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker datePicker, int year, int month,
				int dayOfMonth) {
			try {
				date = sdf.parse(year + "-" + (month + 1) + "-" + dayOfMonth);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setText(sdf.format(date));
			dpDialog.dismiss();
			if (!dpDialog.isShowing()) {
				tpDialog = new TimePickerDialog(context, otsl,
						calendar.get(Calendar.HOUR_OF_DAY),
						calendar.get(Calendar.MINUTE), true);
				tpDialog.show();
			}
		}
	};
	private TimePickerDialog.OnTimeSetListener otsl = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			if (getText().length() < 12) {
				try {
					date = timeSdf.parse(hourOfDay +"");

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setText(getText() + " " + timeSdf.format(date));
				tpDialog.dismiss();
			}

		}
	};
}
