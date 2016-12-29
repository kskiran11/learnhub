package com.hub.shop.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.hub.shop.common.constants.HubConstants;
import com.hub.shop.common.log.Log;

/**
 * Class to process date
 * 
 * @author Kiran
 *
 */
public class DateUtils {

	private DateUtils() {

	}

	/**
	 * Method to convert date to epochtime
	 * 
	 * @param ddMMMyyyDate
	 * @return
	 */
	public static String getDateFromString(String ddMMMyyyDate) {
		Log.logDebug("DateUtils", "getDateFromString() <start>");
		String gDate = "";
		try {
			Long dateMillis = new SimpleDateFormat(HubConstants.DATE_STRING_FORMAT).parse(ddMMMyyyDate).getTime();
			gDate = dateMillis.toString();
		} catch (ParseException e) {
			Log.logError("DateUtils", "Parse Exception in getDateFromString()", e);
		} catch (Exception ex) {
			Log.logFatal("DateUtils", "FATAL EXCEPTION in getDateFromString()", ex);
		}
		Log.logInfo("DateUtils", "Returning epoch date :: '" + gDate + "' from string date : " + ddMMMyyyDate);
		Log.logDebug("DateUtils", "getDateFromString() <end>");
		return gDate;
	}

	/**
	 * Method to get today's date in epoch time
	 * 
	 * @return
	 */
	public static String getTodayDate() {
		Log.logDebug("DateUtils", "getTodayDate() <start>");
		Calendar calendar = new GregorianCalendar();
		calendar.clear(Calendar.HOUR_OF_DAY);
		calendar.clear(Calendar.HOUR);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.AM_PM);
		calendar.clear(Calendar.MILLISECOND);
		Long currentDate = calendar.getTimeInMillis();
		Log.logInfo("DateUtils",
				"Returning epoch date :: '" + currentDate.toString() + "' for today's date : " + calendar);
		Log.logDebug("DateUtils", "getTodayDate() <end>");
		return currentDate.toString();
	}

	/**
	 * Method to get today's date in epoch time
	 * 
	 * @return
	 */
	public static String getTodayDateddMMMyyyy() {
		Log.logDebug("DateUtils", "getTodayDateddMMMyyyy() <start>");
		Calendar calendar = new GregorianCalendar();
		calendar.clear(Calendar.HOUR_OF_DAY);
		calendar.clear(Calendar.HOUR);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.AM_PM);
		calendar.clear(Calendar.MILLISECOND);
		String ddMMMyyyDate = "19DEC2016";
		Log.logInfo("DateUtils", "Returning today's date :: '" + calendar.toString());
		Log.logDebug("DateUtils", "getTodayDateddMMMyyyy() <end>");
		return ddMMMyyyDate;
	}
}
