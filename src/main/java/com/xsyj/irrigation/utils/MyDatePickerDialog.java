package com.xsyj.irrigation.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class MyDatePickerDialog extends DatePickerDialog {

//	private static Calendar c = Calendar.getInstance();

	public MyDatePickerDialog(Context context, int theme,
			OnDateSetListener callBack, int year, int monthOfYear,
			int dayOfMonth) {
		super(context, theme, callBack, year, monthOfYear, dayOfMonth);
	}

	public MyDatePickerDialog(Context context, final Calendar c, int theme, final TextView view) {
		super(context, theme, new OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker arg0, int year, int monthOfYear,
					int dayOfYear) {
				c.set(year, monthOfYear, dayOfYear);
				if(view!=null)
					view.setText(DateFormat.format("yyyy-MM-dd", c));
			}
		}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	protected void onStop() {
		// super.onStop();
	}
}