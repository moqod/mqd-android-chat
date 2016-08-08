package com.moqod.android.chat.sample.common;

import com.moqod.android.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Sergey Chuvashev
 * Date: 30/06/16
 * Time: 18:04
 */
public class DateFormatter {


    public static final SimpleDateFormat sTodayDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    public static final SimpleDateFormat sOtherDateFormat = new SimpleDateFormat("dd.MM", Locale.getDefault());

    public static String messageDate(Date date) {
        return DateUtils.isToday(date) ? DateFormatter.sTodayDateFormat.format(date) : DateFormatter.sOtherDateFormat.format(date);
    }

}
