package com.xsyj.irrigation.utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;


import com.xsyj.irrigation.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateTimePickerDialog1 implements OnDateChangedListener,
        OnTimeChangedListener {

    private TextView alert_title;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button alert_sure;
    private Button alert_clear;
    private Button alert_cancel;
    private AlertDialog ad;
    private String dateTime;
    private Date initDateTime;
    private Activity activity;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * 日期时间弹出选择框构造函数
     *
     * @param activity     ：调用的父activity
     * @param initDateTime 初始日期时间值，作为弹出窗口的标题和日期时间初始值
     */
    public DateTimePickerDialog1(Activity activity, Date initDateTime) {
        this.activity = activity;
        this.initDateTime = initDateTime;
    }

    public void init(DatePicker datePicker, TimePicker timePicker) {
        Calendar calendar = Calendar.getInstance();
        if (!(null == initDateTime || "".equals(initDateTime))) {
            calendar = this.getCalendarByInintData(initDateTime);
        } else {
//			initDateTime = calendar.get(Calendar.YEAR) + "年"
//					+ calendar.get(Calendar.MONTH) + "月"
//					+ calendar.get(Calendar.DAY_OF_MONTH) + "日 "
//					+ calendar.get(Calendar.HOUR_OF_DAY) + ":"
//					+ calendar.get(Calendar.MINUTE);
            initDateTime = calendar.getTime();
        }

        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), this);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
    }

    /**
     * 弹出日期时间选择框方法
     *
     * @param inputDate :为需要设置的日期时间文本编辑框
     * @return
     */
    public AlertDialog dateTimePicKDialog(final TextView inputDate) {
        LinearLayout dateTimeLayout = (LinearLayout) activity
                .getLayoutInflater().inflate(R.layout.common_datetime1, null);
        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
        timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.timepicker);
        alert_title = (TextView) dateTimeLayout.findViewById(R.id.alert_title);
        alert_sure = (Button) dateTimeLayout.findViewById(R.id.alert_sure);
//        alert_sure.setVisibility(View.GONE);
        alert_clear = (Button) dateTimeLayout.findViewById(R.id.alert_clear);
//        alert_clear.setVisibility(View.GONE);
        alert_cancel = (Button) dateTimeLayout.findViewById(R.id.alert_cancel) ;
//        alert_cancel.setVisibility(View.GONE);
        init(datePicker, timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(this);
        alert_title.setText(sdf.format(initDateTime));

        alert_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDate.setText(dateTime);
                if (onTimeClickListener!=null){
                    onTimeClickListener.OnSubmit(dateTime);
                }
                ad.dismiss();
            }
        });

        alert_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTime = "点击选择时间";
                inputDate.setText(dateTime);
                if (onTimeClickListener!=null){
                    onTimeClickListener.OnSubmit(dateTime);
                }
                ad.dismiss();
            }
        });

        alert_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDate.setText("点击选择时间");
                ad.dismiss();
            }
        });

        ad = new AlertDialog.Builder(activity).create(); //.setTitle(sdf.format(initDateTime))
        ad.setView(dateTimeLayout,0,0,0,0);
        ad.show();

//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        inputDate.setText(dateTime);
//                        if (onTimeClickListener!=null){
//                            onTimeClickListener.OnSubmit(dateTime);
//                        }
//                    }
//                })
//                .setNeutralButton("清空", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dateTime = "";
//                        inputDate.setText(dateTime);
//                        if (onTimeClickListener!=null){
//                            onTimeClickListener.OnSubmit(dateTime);
//                        }
//                    }
//                })
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        inputDate.setText("");
//                    }
//                }).show();
        onDateChanged(null, 0, 0, 0);
        return ad;
    }

    /**
     * 实现将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒,并赋值给calendar
     *
     * @param initDateTime 初始日期时间值 字符串型
     * @return Calendar
     */
    private Calendar getCalendarByInintData(Date initDateTime) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(initDateTime);
        // 将初始日期时间2012年07月02日 16:45 拆分成年 月 日 时 分 秒
//
//		String date = spliteString(initDateTime, "日", "index", "front"); // 日期
//		String time = spliteString(initDateTime, "日", "index", "back"); // 时间
//
//		String yearStr = spliteString(date, "年", "index", "front"); // 年份
//		String monthAndDay = spliteString(date, "年", "index", "back"); // 月日
//
//		String monthStr = spliteString(monthAndDay, "月", "index", "front"); // 月
//		String dayStr = spliteString(monthAndDay, "月", "index", "back"); // 日
//
//		String hourStr = spliteString(time, ":", "index", "front"); // 时
//		String minuteStr = spliteString(time, ":", "index", "back"); // 分
//
//		int currentYear = Integer.valueOf(yearStr.trim()).intValue();
//		int currentMonth = Integer.valueOf(monthStr.trim()).intValue() - 1;
//		int currentDay = Integer.valueOf(dayStr.trim()).intValue();
//		int currentHour = Integer.valueOf(hourStr.trim()).intValue();
//		int currentMinute = Integer.valueOf(minuteStr.trim()).intValue();
//
//		calendar.set(currentYear, currentMonth, currentDay, currentHour,
//				currentMinute);
        return calendar;
    }

    /**
     * 截取子串
     *
     * @param srcStr      源串
     * @param pattern     匹配模式
     * @param indexOrLast
     * @param frontOrBack
     * @return
     */
    public static String spliteString(String srcStr, String pattern,
                                      String indexOrLast, String frontOrBack) {
        String result = "";
        int loc = -1;
        if (indexOrLast.equalsIgnoreCase("index")) {
            loc = srcStr.indexOf(pattern); // 取得字符串第一次出现的位置
        } else {
            loc = srcStr.lastIndexOf(pattern); // 最后一个匹配串的位置
        }
        if (frontOrBack.equalsIgnoreCase("front")) {
            if (loc != -1)
                result = srcStr.substring(0, loc); // 截取子串
        } else {
            if (loc != -1)
                result = srcStr.substring(loc + 1, srcStr.length()); // 截取子串
        }
        return result;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        // TODO Auto-generated method stub
        onDateChanged(null, 0, 0, 0);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
        // TODO Auto-generated method stub
        // 获得日历实例
        Calendar calendar = Calendar.getInstance();

        calendar.set(datePicker.getYear(), datePicker.getMonth(),
                datePicker.getDayOfMonth(), timePicker.getCurrentHour(),
                timePicker.getCurrentMinute());
        dateTime = sdf.format(calendar.getTime()).concat(":00");
//        ad.setTitle(dateTime);
        alert_title.setText(dateTime);
    }

    private OnTimeClickListener onTimeClickListener;

    public interface OnTimeClickListener {

        //传递时间数据的方法
        public void OnSubmit(String dateTime);
    }

    public void setOnTimeClickListener(OnTimeClickListener onTimeClickListener) {
        this.onTimeClickListener = onTimeClickListener;
    }
}

