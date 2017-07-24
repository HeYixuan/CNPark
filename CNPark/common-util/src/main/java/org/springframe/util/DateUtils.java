package org.springframe.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 时间操作工具类
 * @author: HeYixuan
 * @create: 2017-06-07 10:37
 */
public class DateUtils {

    private static final SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    /**
     * 当前日期加上天数后的日期
     * @param day 天数
     * @return
     */
    public static Date add(int day){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(now());
        calendar.add(calendar.DATE,day);//把日期往后增加一天.整数往后推,负数往前移动
        return calendar.getTime();   //这个时间就是日期往后推一天的结果
    }

    public static Date add(int day, String source) throws ParseException {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(parseDate(source));
        calendar.add(calendar.DATE,day);//把日期往后增加一天.整数往后推,负数往前移动
        return calendar.getTime();   //这个时间就是日期往后推一天的结果
    }

    public static Date add(int day, Date source, boolean bool) throws ParseException {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(source);
        calendar.add(calendar.DATE,day);//把日期往后增加一天.整数往后推,负数往前移动
        if (true == bool){
            calendar.set(calendar.HOUR_OF_DAY, 23);
            calendar.set(calendar.MINUTE, 59);
            calendar.set(calendar.SECOND, 59);
            calendar.set(calendar.MILLISECOND, 999);
        }
        return calendar.getTime();   //这个时间就是日期往后推一天的结果
    }

    /**
     * 当前日期加上月数后的日期
     * @param month 月数
     * @return
     */
    public static Date addMonth(int month){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(now());
        calendar.add(calendar.MONTH,month);//把日期往后增加一天.整数往后推,负数往前移动
        return calendar.getTime();   //这个时间就是日期往后推一天的结果
    }

    public static Date addMonth(int month, boolean bool) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.MONTH,month);
        if (true == bool){
            calendar.set(calendar.HOUR_OF_DAY, 23);
            calendar.set(calendar.MINUTE, 59);
            calendar.set(calendar.SECOND, 59);
            calendar.set(calendar.MILLISECOND, 999);
        }

        return calendar.getTime();
    }

    /**
     * 获得当前日期时间
     * <p>
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String currentDatetime() {
        return dateTime.format(now());
    }

    /**
     * 格式化日期时间
     * <p>
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String formatDatetime(Date date) {
        return dateTime.format(date);
    }

    /**
     * 获得当前日期
     * <p>
     * 日期格式yyyy-MM-dd
     *
     * @return
     */
    public static String currentDate() {
        return dateFormat.format(now());
    }

    /**
     * 格式化日期
     * <p>
     * 日期格式yyyy-MM-dd
     *
     * @return
     */
    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    /**
     * 获得当前时间
     * <p>
     * 时间格式HH:mm:ss
     *
     * @return
     */
    public static String currentTime() {
        return timeFormat.format(now());
    }

    /**
     * 格式化时间
     * <p>
     * 时间格式HH:mm:ss
     *
     * @return
     */
    public static String formatTime(Date date) {
        return timeFormat.format(date);
    }

    /**
     * 获得当前时间的<code>java.util.Date</code>对象
     *
     * @return
     */
    public static Date now() {
        return new Date();
    }

    public static Calendar calendar() {
        Calendar cal = GregorianCalendar.getInstance(Locale.CHINESE);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        return cal;
    }

    /**
     * 获得当前时间的毫秒数
     * <p>
     * 详见{@link System#currentTimeMillis()}
     *
     * @return
     */
    public static long millis() {
        return System.currentTimeMillis();
    }

    /**
     *
     * 获得当前Chinese月份
     *
     * @return
     */
    public static int month() {
        return calendar().get(Calendar.MONTH) + 1;
    }

    /**
     * 获得月份中的第几天
     *
     * @return
     */
    public static int dayOfMonth() {
        return calendar().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 今天是星期的第几天
     *
     * @return
     */
    public static int dayOfWeek() {
        return calendar().get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 今天是年中的第几天
     *
     * @return
     */
    public static int dayOfYear() {
        return calendar().get(Calendar.DAY_OF_YEAR);
    }

    /**
     *判断原日期是否在目标日期之前
     *
     * @param src
     * @param dst
     * @return
     */
    public static boolean isBefore(Date src, Date dst) {
        return src.before(dst);
    }

    /**
     *判断原日期是否在目标日期之后
     *
     * @param src
     * @param dst
     * @return
     */
    public static boolean isAfter(Date src, Date dst) {
        return src.after(dst);
    }

    /**
     *判断两日期是否相同
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isEqual(Date date1, Date date2) {
        return date1.compareTo(date2) == 0;
    }

    /**
     * 判断某个日期是否在某个日期范围
     *
     * @param beginDate
     *            日期范围开始
     * @param endDate
     *            日期范围结束
     * @param src
     *            需要判断的日期
     * @return
     */
    public static boolean between(Date beginDate, Date endDate, Date src) {
        return beginDate.before(src) && endDate.after(src);
    }

    /**
     * 获得当前月的最后一天
     * <p>
     * HH:mm:ss为0，毫秒为999
     *
     * @return
     */
    public static Date lastDayOfMonth() {
        Calendar cal = calendar();
        cal.set(Calendar.DAY_OF_MONTH, 0); // M月置零
        cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
        cal.set(Calendar.MINUTE, 0);// m置零
        cal.set(Calendar.SECOND, 0);// s置零
        cal.set(Calendar.MILLISECOND, 0);// S置零
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);// 月份+1
        cal.set(Calendar.MILLISECOND, -1);// 毫秒-1
        return cal.getTime();
    }

    /**
     * 获得当前月的第一天
     * <p>
     * HH:mm:ss SS为零
     *
     * @return
     */
    public static Date firstDayOfMonth() {
        Calendar cal = calendar();
        cal.set(Calendar.DAY_OF_MONTH, 1); // M月置1
        cal.set(Calendar.HOUR_OF_DAY, 0);// H置零
        cal.set(Calendar.MINUTE, 0);// m置零
        cal.set(Calendar.SECOND, 0);// s置零
        cal.set(Calendar.MILLISECOND, 0);// S置零
        return cal.getTime();
    }

    private static Date weekDay(int week) {
        Calendar cal = calendar();
        cal.set(Calendar.DAY_OF_WEEK, week);
        return cal.getTime();
    }

    /**
     * 获得周五日期
     * <p>
     * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
     *
     * @return
     */
    public static Date friday() {
        return weekDay(Calendar.FRIDAY);
    }

    /**
     * 获得周六日期
     * <p>
     * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
     *
     * @return
     */
    public static Date saturday() {
        return weekDay(Calendar.SATURDAY);
    }

    /**
     * 获得周日日期
     * <p>
     * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
     *
     * @return
     */
    public static Date sunday() {
        return weekDay(Calendar.SUNDAY);
    }

    /**
     * 将字符串日期时间转换成java.util.Date类型
     * <p>
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @param datetime
     * @return
     */
    public static Date parseDatetime(String datetime) throws ParseException {
        return dateTime.parse(datetime);
    }

    /**
     * 将字符串日期转换成java.util.Date类型
     *<p>
     * 日期时间格式yyyy-MM-dd
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String date) throws ParseException {
        return dateFormat.parse(date);
    }

    /**
     * 将字符串日期转换成java.util.Date类型
     *<p>
     * 时间格式 HH:mm:ss
     *
     * @param time
     * @return
     * @throws ParseException
     */
    public static Date parseTime(String time) throws ParseException {
        return timeFormat.parse(time);
    }

    /**
     * 根据自定义pattern将字符串日期转换成java.util.Date类型
     *
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String date, String pattern) throws ParseException {
        SimpleDateFormat format = (SimpleDateFormat) dateTime.clone();
        format.applyPattern(pattern);
        return format.parse(date);
    }

    /**
     * 格式化日期时间
     *
     * @param date
     * @param pattern
     *            格式化模式，详见{@link SimpleDateFormat}构造器
     *            <code>SimpleDateFormat(String pattern)</code>
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat format = (SimpleDateFormat) dateTime.clone();
        format.applyPattern(pattern);
        return format.format(date);
    }

    /**
     * 计算两个日期之间相差的天数
     * @param src 较小的时间
     * @param dst  较大的时间
     * @return 相差天数
     * TODO: 2017-06-10 10:00:00与2017-06-20 00:00:00会少一天
     * TODO: 所以最好时分秒都是00:00:00
     * @throws ParseException
     */
    public static int daysBetween(Date src,Date dst) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(src);
        long time1 = cal.getTimeInMillis();
        cal.setTime(dst);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期之间相差的天数
     * @param src 较小的时间
     * @param dst 较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(String src,String dst) throws ParseException{
        Calendar cal = Calendar.getInstance();
        cal.setTime(parseDate(src));
        long time1 = cal.getTimeInMillis();
        cal.setTime(parseDate(dst));
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static String ToString(){
        SimpleDateFormat sdf = new SimpleDateFormat("现在是yyyy年MM月dd日 HH(hh)时   mm分 ss秒 S毫秒  E 今年的第D天  这个月的第F星期   今年的第w个星期   这个月的第W个星期  [今天ak时 1~24制时间] [K时 0-11小时制时间] Z时区");
        Date date = new Date();
        return sdf.format(date);
    }



    public static void main(String [] args) throws ParseException {
        Date date = add(5);
        Date date1 = add(5, "2017-06-10");
        Date date2 = add(1, new Date(), true);
        System.err.println(formatDatetime(date1));
        System.err.println(formatDatetime(date2));

        Date d1=parseDatetime("2012-09-08 10:00:00");
        Date d2=parseDatetime("2012-09-15 00:00:00");
        System.err.println("日期相差:"+daysBetween(d1,d2)+"天");

        System.err.println(formatDatetime(lastDayOfMonth()));

        System.err.println(formatDatetime(addMonth(1)));

        System.err.println(ToString());
        System.err.println(formatDatetime(addMonth(1,true)));
    }
}
