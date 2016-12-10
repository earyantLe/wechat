package com.earyant.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    private static final Log log = LogFactory.getLog(DateUtils.class);
    public static SimpleDateFormat fmt = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat dateFmt = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat timeFmt = new SimpleDateFormat("HHmmss");
    // 年月日格式串
    public static final String FORMAT_YMD = "yyyyMMdd";
    // 年月日格式串
    public static final String FORMAT_YMD2 = "yyyy-MM-dd";
    // 年月日时分秒格式串
    public static final String FORMAT_YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMATYMDHMS = "yyyyMMddHHmmss";
    // 时分格式串
    public static final String FORMAT_HM = "HH:mm";

    public static void main(String[] args) {

        // System.out.println(fmt.format(date));
        // System.out.println(addTime(Calendar.HOUR, -1));
        System.out.println(DateUtils.getCurrentDateStr(FORMATYMDHMS));
    }

    /**
     * 将时间转换成串格式
     *
     * @param date
     * @param format
     * @return
     * @serialData 2016-04-06
     * @author 韩志伟
     */
    public static String getFormatDateStr(Date date, String format) {
        if (format == null) {
            format = DateUtils.FORMAT_YMD;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * @param format
     * @return
     * @throws ParseException
     * @description 【取得当前日期】
     * @createTime 2016-04-06
     * @author hanzhiwei
     */
    public static Date getCurrentDate(String format) {
        // 默认格式 yyyy-MM-dd
        Date date = null;
        try {
            date = String2Date(getCurrentDateStr(format), format);

        } catch (Exception e) {
            log.error("", e);
        }
        return date;
    }

    /**
     * @param dateStr
     * @param format
     * @return
     * @throws ParseException
     * @description 【字符转化为时间】
     * @createTime 2016-04-06
     * @author hanzhiwei
     */
    public static Date String2Date(String dateStr) {
        // 默认格式
        String format = "yyyy-MM-dd HH:mm:ss";
        return String2Date(dateStr, format);
    }

    /**
     * @param dateStr
     * @param format
     * @return
     * @throws ParseException
     * @description 【字符转化为时间】
     * @createTime 2016-04-06
     * @author 韩志伟
     */
    public static Date String2Date(String dateStr, String format) {
        if (StringUtils.isEmpty(dateStr))
            return null;

        // 格式化时间
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            log.error("", e);
        }
        return date;
    }

    /**
     * @return String
     * @description 【取得当前日期】
     * @createTime 2016-04-06
     * @author 韩志伟
     */
    public static String getCurrentDateStr(String format) {
        // 按格式取得时间
        return DateFormatUtils.format(new Date(), format);
    }

    public static Date addTime(int field, int addNum) {
        Calendar cal = Calendar.getInstance();
        cal.add(field, addNum);
        return cal.getTime();
    }

    /**
     * 用于返回指定日期格式的日期增加指定天数的日期
     *
     * @param date   指定日期
     * @param format 指定日期格式
     * @param days   指定天数
     * @return 指定日期格式的日期增加指定天数的日期
     */
    public static String getFutureDay(Date date, String format, int days) {
        String future = "";
        try {
            Calendar calendar = GregorianCalendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            calendar.setTime(date);
            calendar.add(Calendar.DATE, days);
            date = calendar.getTime();
            future = simpleDateFormat.format(date);
        } catch (Exception e) {
        }
        return future;
    }

    /**
     * 把毫秒转化成日期
     *
     * @param dateFormat (日期格式，例如：MM/ dd/yyyy HH:mm:ss)
     * @param millSec    (毫秒数)
     * @return
     */
    public static String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    /**
     * 将字符串转换为自定义型日期
     *
     * @param sDate
     * @param sFmt
     * @return
     */
    public static Date toDate(String sDate, String sFmt) {
        Date dt = null;
        try {
            dt = new SimpleDateFormat(sFmt).parse(sDate);
        } catch (ParseException e) {
            return dt;
        }
        return dt;
    }

    public static String getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        String msg = "";
        switch (dayOfWeek) {
            case 1:
                msg = "星期日";
                break;
            case 2:
                msg = "星期一";
                break;
            case 3:
                msg = "星期二";
                break;
            case 4:
                msg = "星期三";
                break;
            case 5:
                msg = "星期四";
                break;
            case 6:
                msg = "星期五";
                break;
            case 7:
                msg = "星期六";
                break;
            default:
                break;
        }
        return msg;
    }

    /**
     * 比较两个String类型时间大小
     *
     * @param d1
     * @param d2
     * @return
     */
    public static String compare(Date d1, Date d2) {
        String str1 = fmt.format(d1);
        String str2 = fmt.format(d2);
        int result = str1.compareTo(str2);
        if (result > 0) {
            return str2;
        } else if (result == 0) {
            return str1;
        } else {
            return str2;
        }
    }

    public static Date getNextDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, day);
        return cal.getTime();
    }

    public static Date getNextWeek(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, day * 7);
        return cal.getTime();
    }

    public static Date getNextMonth(Date date, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    public static Date getNextQuarter(Date date, int Quarter) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, Quarter * 3);
        return cal.getTime();
    }

    public static Date getNextYear(Date date, int year) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, year);
        return cal.getTime();
    }

    /***
     * 获取【查询时间】到现在多少天
     *
     * @param fromDate
     *            查询时间
     * @return
     * @throws Exception
     */
    public static int getDayFromCookiePlantTime(Date fromDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        long interval = now.getTimeInMillis() - cal.getTimeInMillis();
        long syts = interval / 60 / 60 / 1000 / 24;// 获取今天到指定日期剩余天数
        return (int) syts;
    }

    /**
     * 给日期增加多少天
     *
     * @param calDate
     * @param addDate 类型必须是long
     * @return
     */
    public static String addCalendarDay(Date calDate, long addDate) {
        long time = calDate.getTime();
        addDate = addDate * 24 * 60 * 60 * 1000;
        time += addDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date(time));
    }

    /**
     * 获取当前年月（返回string）
     *
     * @return String
     * @author wgw
     * @date Nov 26, 2012
     */
    public static String getCurrentYearMonthToString() {
        Calendar cal = Calendar.getInstance();
        String currentYear = (new Integer(cal.get(1))).toString();
        String currentMonth = null;
        if (cal.get(2) < 9) {
            currentMonth = (new StringBuilder()).append("0").append((new Integer(cal.get(2) + 1)).toString())
                    .toString();
        } else {
            currentMonth = (new Integer(cal.get(2) + 1)).toString();
        }
        return (new StringBuilder()).append(currentYear).append(currentMonth).toString();
    }
}
