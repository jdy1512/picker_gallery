package com.allchange.dtime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Dtime {
	private static Dtime dTime;
	private SimpleDateFormat dTimeFormater = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	private SimpleDateFormat dateFormater = new SimpleDateFormat("yyyyMMdd");
	private Calendar c;

	private static String LOCAL_TIME = "+09:00";

	public static Dtime instance() {
		if (dTime == null) {
			dTime = new Dtime();
		}
		return dTime;
	}

	public String getGMTDateTime() {
		c = Calendar.getInstance();
		dTimeFormater.setTimeZone(TimeZone.getTimeZone("GMT" + LOCAL_TIME));
		return dTimeFormater.format(c.getTime());
	}

	public String getGMTDate() {
		c = Calendar.getInstance();
		dateFormater.setTimeZone(TimeZone.getTimeZone("GMT" + LOCAL_TIME));
		return dateFormater.format(c.getTime());
	}
}
