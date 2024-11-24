/*
 * Created on 28.06.2003
 *
 * To change this generated comment go to
 * Window>Preferences>Java>Code Generation>Code Template
 */
package de.ruu.lib.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/** make constants "real" constants (without calculations during value assignment) so that they can be used in annotations */
public final class Time
{
	public final static long MSECS_SEC   = 1000L;
	public final static long MSECS_MIN   = MSECS_SEC  *  60L;
	public final static long MSECS_HOUR  = MSECS_MIN  *  60L;
	public final static long MSECS_DAY   = MSECS_HOUR *  24L;
	public final static long MSECS_WEEK  = MSECS_DAY  *   7L;
	public final static long MSECS_MONTH = MSECS_DAY  *  30L;
	public final static long MSECS_YEAR  = MSECS_DAY  * 365L;

	public final static SimpleDateFormat getDateFormatSortableTimestamp()
	{
		return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss:SSS");
	}

	public final static SimpleDateFormat getDateFormatSortableTimestampPrecisionSeconds()
	{
		return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	}

	public final static SimpleDateFormat getDateFormatSortableTimestampPrecisionMinutes()
	{
		return new SimpleDateFormat("yyyy.MM.dd HH:mm");
	}

	public final static SimpleDateFormat getDateFormatSortableTimestampPrecisionHours()
	{
		return new SimpleDateFormat("yyyy.MM.dd HH");
	}

	public final static SimpleDateFormat getDateFormatSortableTimestampPrecisionDays()
	{
		return new SimpleDateFormat("yyyy.MM.dd");
	}

	public final static String getSortableTimestamp()
	{
		return Time.getDateFormatSortableTimestamp().format(new Date());
	}

	public final static String getSortableTimestampPrecisionSeconds()
	{
		return Time.getDateFormatSortableTimestampPrecisionSeconds().format(new Date());
	}

	public final static String getSortableTimestampPrecisionMinutes()
	{
		return Time.getDateFormatSortableTimestampPrecisionMinutes().format(new Date());
	}

	public final static String getSortableTimestampPrecisionMinutes(Date date)
	{
		return Time.getDateFormatSortableTimestampPrecisionMinutes().format(date);
	}
}