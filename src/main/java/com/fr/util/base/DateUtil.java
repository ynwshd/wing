package com.fr.util.base;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 日期工具类
 *
 * @author admin
 *
 */
public class DateUtil {

	public static void main(String[] args) {
		// String date = "201607";

		// DateUtil.compareDate(date, null, 0);
		// DateUtil.compareDate(date, null, 1);
		// DateUtil.compareDate(date, null, 2);
		//
		// date = "2006-06-03";
		// DateUtil.compareDate(date, null, 0);
		// DateUtil.compareDate(date, null, 1);
		// DateUtil.compareDate(date, null, 2);
		// DateUtil.compareDate(date, "2009-06-01", 0);
		// DateUtil.compareDate(date, "2009-06-01", 1);
		// DateUtil.compareDate(date, "2009-06-01", 2);
		try {
			// String a = DateUtil.subMonth(date);
			String a = DateUtil.dateToWeek("2018-02-28");
			System.out.println(a);
		} catch (Exception e) {

		}
		String date1 = DateUtil.dateToStringyyyymm(new Date());
		String xcjiji = date1.substring(0, 4);
		System.out.println(xcjiji);
	}

	/**
	 * 日期转字符串
	 *
	 * @param date
	 *            日期
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static String dateToString(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		}
		return "";
	}

	/**
	 * 日期转字符串
	 *
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		return dateToString(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 日期转字符串
	 *
	 * @param date
	 * @return
	 */
	public static String dateToStringyyyymmdd(Date date) {
		return dateToString(date, "yyyy-MM-dd");
	}

	/**
	 * 日期转字符串
	 *
	 * @param date
	 * @return
	 */
	public static String dateToStringyyyymm(Date date) {
		return dateToString(date, "yyyyMM");
	}

	/**
	 * * 字符串转日期
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date stringToDate(String date, String pattern) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return new Date();
	}

	/**
	 * * 字符串转日期
	 *
	 * @param date
	 * @return
	 */
	public static Date stringToDate(String date) {
		return stringToDate(date, "yyyy-MM-dd");
	}

	/**
	 * @param date1
	 *            需要比较的时间 不能为空(null),需要正确的日期格式
	 * @param date2
	 *            被比较的时间 为空(null)则为当前时间
	 * @param stype
	 *            返回值类型 0为多少天，1为多少个月，2为多少年
	 * @return
	 */
	public static int compareDate(String date1, String date2, int stype) {
		int n = 0;

		// String[] u = { "天", "月", "年" };
		String formatStyle = stype == 1 ? "yyyy-MM" : "yyyy-MM-dd";

		date2 = date2 == null ? DateUtil.getCurrentDate() : date2;

		DateFormat df = new SimpleDateFormat(formatStyle);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(date1));
			c2.setTime(df.parse(date2));
		} catch (Exception e3) {
			System.out.println("wrong occured");
		}
		// List list = new ArrayList();
		while (!c1.after(c2)) { // 循环对比，直到相等，n 就是所要的结果
			// list.add(df.format(c1.getTime())); // 这里可以把间隔的日期存到数组中 打印出来
			n++;
			if (stype == 1) {
				c1.add(Calendar.MONTH, 1); // 比较月份，月份+1
			} else {
				c1.add(Calendar.DATE, 1); // 比较天数，日期+1
			}
		}

		n = n - 1;

		if (stype == 2) {
			n = (int) n / 365;
		}

		// System.out.println(date1 + " -- " + date2 + " 相差多少" + u[stype] + ":"
		// + n);
		return n;
	}

	/**
	 * 得到当前日期
	 *
	 * @return
	 */
	public static String getCurrentDate() {
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd");
		return simple.format(date);
	}

	/**
	 * ** 传入具体日期 ，返回具体日期减12个月。
	 *
	 * @param date
	 *            日期(2014-04-20)
	 * @return 2014-03-20
	 * @throws ParseException
	 */
	public static String subMonth(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date dt = sdf.parse(date);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);

		rightNow.add(Calendar.MONTH, -12);
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);
		return reStr;
	}

	/**
	 * 传入日期和相加减月份得到日期+月份后的日期字符串
	 * 
	 * @param date
	 *            格式(yyyyMMdd)
	 * @param addmonth
	 * @return (yyyyMMdd)
	 * @throws ParseException
	 */
	public static String addMonthDate(String date, int addmonth) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date dt = sdf.parse(date);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);

		rightNow.add(Calendar.MONTH, addmonth);
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);

		return reStr;
	}

	/**
	 * 获取当前时间N月后 YYYYMM
	 * 
	 * @param month
	 * @return
	 */
	public static String addMonth(int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, month);
		Date date = calendar.getTime();
		return dateToStringyyyymm(date);
	}

	/**
	 * 获取指定时间N月后 YYYYMM
	 * 
	 * @param date
	 * @param month
	 * @return
	 */
	public static String addMonth(String date, int month) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		try {

			Date dt = sdf.parse(date);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dt);

			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.MONTH, month);

			return dateToStringyyyymm(calendar.getTime());
		} catch (ParseException ex) {
			Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
			return date;
		}
	}

	/**
	 * 指定日期加上指定天数得到新日期
	 * 
	 * @param date
	 *            指定日期
	 * @param addday
	 *            添加的天数
	 * @return 添加天数后的日期
	 * @throws ParseException
	 */
	public static Date addDate(Date date, long addday) throws ParseException {
		long time = date.getTime(); // 得到指定日期的毫秒数
		addday = addday * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
		time += addday; // 相加得到新的毫秒数
		return new Date(time); // 将毫秒数转换成日期
	}

	/**
	 * 指定日期加上减去天数得到新日期
	 * 
	 * @param date
	 *            指定日期
	 * @param reduceday
	 *            减去的天数
	 * @return 减去天数后的日期
	 * @throws ParseException
	 */
	public static Date reduceDate(Date date, long reduceday) throws ParseException {
		long time = date.getTime(); // 得到指定日期的毫秒数
		reduceday = reduceday * 24 * 60 * 60 * 1000; // 要减去的天数转换成毫秒数
		time -= reduceday; // 相减得到新的毫秒数
		return new Date(time); // 将毫秒数转换成日期
	}

	/**
	 * 指定日期得到星期几
	 * 
	 * @param datetime
	 *            yyyy-MM-dd
	 * @return 星期几
	 */
	public static String dateToWeek(String datetime) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance(); // 获得一个日历
		Date datet = null;
		try {
			datet = f.parse(datetime);
			cal.setTime(datet);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
		if (w < 0)
			w = 0;
		return weekDays[w];
	}
}
