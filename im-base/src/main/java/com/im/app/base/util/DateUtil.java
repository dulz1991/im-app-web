package com.im.app.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;


public class DateUtil {

	/**
	 * String to Date
	 * 
	 * @param date
	 * @return
	 */
	public static Date stringToDate(String dateStr, String formate) {
		try {
			Date date = null;
			if (StringUtils.isNotBlank(dateStr)) {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(formate);
				date = dateFormatter.parse(dateStr);
			}
			return date;
		} catch (ParseException e) {
			System.out.print(e.getMessage());
			return null;
		}
	}
	
	/**
	 * Date to String
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date, String formate) {
		String dateStr;
		if (date != null) {
			try {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(formate);
				dateStr = dateFormatter.format(date);
			} catch (Exception e) {
				dateStr = null;
			}
		} else {
			dateStr = null;
		}
		return dateStr;
	}

}
