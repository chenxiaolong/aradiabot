package me.iarekylew00t.utils;

import java.util.concurrent.TimeUnit;

public final class TimeUtils {
	private static final long MILLIS_IN_SECOND = 1000;
	private static final long MILLIS_IN_MINUTE = MILLIS_IN_SECOND * 60;
	private static final long MILLIS_IN_HOUR = MILLIS_IN_MINUTE * 60;
	private static final long MILLIS_IN_DAY = MILLIS_IN_HOUR * 24;
	private static final long MILLIS_IN_MONTH = MILLIS_IN_DAY * 31;
	private static final long MILLIS_IN_YEAR = MILLIS_IN_DAY * 365;

	public static final String millisToString(long ms) {
		if (ms <= 0) { throw new IllegalArgumentException("Input must be greater than zero"); }
		long hours = TimeUnit.MILLISECONDS.toHours(ms);
		ms -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(ms);
		ms -= TimeUnit.MINUTES.toMillis(minutes);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(ms);
		
		StringBuilder sb = new StringBuilder();
		if (hours > 0) {
			sb.append(hours + "h");
		}
		if (minutes > 0) {
			sb.append(minutes + "m");
		}
		if (seconds > 0) {
			sb.append(seconds + "s");
		}
		return sb.toString();
	}
	
	public static final long createFutureTime(int interval, String type) {
		return TimeUtils.addTime(System.currentTimeMillis(), interval, type);
	}
	
	public static final long addTime(long time, int addition, String type) {
		if (type.equalsIgnoreCase("years") || type.equalsIgnoreCase("year") || type.equalsIgnoreCase("y")) {
			return time + (MILLIS_IN_YEAR * addition);
		} else if (type.equalsIgnoreCase("months") || type.equalsIgnoreCase("month") || type.equalsIgnoreCase("mon")) {
			return time + (MILLIS_IN_MONTH * addition);
		} else if (type.equalsIgnoreCase("days") || type.equalsIgnoreCase("day") || type.equalsIgnoreCase("d")) {
			return time + (MILLIS_IN_DAY * addition);
		} else if (type.equalsIgnoreCase("hours") || type.equalsIgnoreCase("hour") || type.equalsIgnoreCase("h")) {
			return time + (MILLIS_IN_HOUR * addition);
		} else if (type.equalsIgnoreCase("minutes") || type.equalsIgnoreCase("minute") || type.equalsIgnoreCase("min") || type.equalsIgnoreCase("m")) {
			return time + (MILLIS_IN_MINUTE * addition);
		} else if (type.equalsIgnoreCase("seconds") || type.equalsIgnoreCase("second") || type.equalsIgnoreCase("sec") || type.equalsIgnoreCase("s")) {
			return time + (MILLIS_IN_SECOND * addition);
		} else {
			throw new IllegalArgumentException("Specify y,m,d,h,min,s");
		}
	}
}