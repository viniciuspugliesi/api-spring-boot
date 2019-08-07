package com.sgu.services.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

	public static Date getDateWithAddMonth(Integer months) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, months);
		return calendar.getTime();
	}

	public static Date getDateWithAddDays(Integer days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}

	public static Date clearTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
        return calendar.getTime();
    }

	public static Date maxTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
        
        return calendar.getTime();
	}

	public static Date getDate() {
		return Calendar.getInstance().getTime();
	}

	public static Date createFromFormat(String format, String dateFormat) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

		Date date = simpleDateFormat.parse(dateFormat);
		calendar.setTime(date);

		return calendar.getTime();
	}

	public static String createFromFormatWithFormat(String format, String dateFormat, String display) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(display);
		Date date = createFromFormat(format, dateFormat);

		return simpleDateFormat.format(date);
	}
}
