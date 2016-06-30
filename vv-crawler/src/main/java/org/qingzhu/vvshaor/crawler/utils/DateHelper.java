/**
 * 
 */
package org.qingzhu.vvshaor.crawler.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mzhgeng
 * 
 */
public final class DateHelper {

	private DateHelper() {
	}

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

	/**
	 * @return formatted string by pattern "yyyyMMdd HH:mm:ss"
	 */
	public static String format(Date date) {
		if (date == null) {
			return "";
		}
		return DATE_FORMAT.format(date);
	}

}
