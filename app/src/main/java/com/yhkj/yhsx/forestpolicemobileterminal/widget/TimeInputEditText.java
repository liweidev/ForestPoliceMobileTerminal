package com.yhkj.yhsx.forestpolicemobileterminal.widget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TimePicker;

/**
 * @author liupeng 时间输入控件
 * 
 */
public class TimeInputEditText extends EditText {

	private Context context;

	private TimePickerDialog tpDialog;

	private SimpleDateFormat sdf = new SimpleDateFormat("hh : mm");

	private Date date = new Date();

	public TimeInputEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context = context;
		// setText(sdf.format(date));
	}

	public TimeInputEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		// setText(sdf.format(date));
	}

	public TimeInputEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		// setText(sdf.format(date));
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		// TODO Auto-generated method stub
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		if (focused) {
			Calendar calender = Calendar.getInstance(Locale.CHINA);
			tpDialog = new TimePickerDialog(context, otsl,
					calender.get(Calendar.HOUR_OF_DAY),
					calender.get(Calendar.MINUTE), true);
			tpDialog.show();
		}
	}

	private TimePickerDialog.OnTimeSetListener otsl = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			try {
				date = sdf.parse(hourOfDay + " : " + minute);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			setText(sdf.format(date));
			tpDialog.dismiss();
		}
	};
}
