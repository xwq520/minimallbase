package com.minimall.boilerplate.common;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import static org.springframework.util.Assert.notNull;

/**
 * Title: 日期时间帮助类.
 * <p>Description: 处理日期时间相关初始化, 转换, 格式化等.</p>

 */
public final class DateHelper {

  public static final String dateFormt = "yyyyMMdd";
  public static final String defualtFormt = "yyyy-MM-dd";
  public static final String normalFormt = "yyyy-MM-dd HH:mm:ss";

  private static final ZoneId SYSTEM_DEFAULT_ZONE_ID = ZoneId.systemDefault();

  private static transient int gregorianCutoverYear = 1582;


  private DateHelper() {
  }


  public static long toEpochMilli(LocalDate date) {
    notNull(date);
    return date.atStartOfDay(SYSTEM_DEFAULT_ZONE_ID).toInstant().toEpochMilli();
  }

  public static LocalDate toLocalDate(long epochMilli) {
    if(epochMilli <= 0L)
      throw new IllegalArgumentException("[epochMilli] must be greater than 0.");
    return Instant.ofEpochMilli(epochMilli)
        .atZone(SYSTEM_DEFAULT_ZONE_ID).toLocalDate();
  }

  public static boolean beforeAndEqualToday(long epochMilli) {
    LocalDate beforeDate = toLocalDate(epochMilli);
    return beforeDate.isBefore(LocalDate.now());
  }

  public static int formatPeriod(int year, int month) {
    return year * 100 + month;
  }

  public static String formatEpochMilli(long epochMilli, String pattern) {
    return toLocalDate(epochMilli).format(DateTimeFormatter.ofPattern(pattern));
  }

  public static String formatNow(String pattern) {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
  }


  public static Long toStartEpochMilli(int period) {
    int year = period / 100;
    int month = period % 100;
    LocalDateTime localDateTime = LocalDateTime.of(year, month, 1, 0, 0, 0);
    return toEpochMilli(localDateTime);
  }

  public static Long toEndEpochMilli(int period) {
    int year = period / 100;
    int month = period % 100;
    LocalDateTime localDateTime = LocalDateTime.of(year, month, 1, 23, 59,59).plusMonths(1).minusDays(1);
    return toEpochMilli(localDateTime);
  }


  public static Long toStartOfDay(long epochMilli) {
    return toEpochMilli(toLocalDate(epochMilli).atStartOfDay());
  }

  public static Long toEndOfDay(long epochMilli) {
    return toEpochMilli(toLocalDateTime(epochMilli).with(LocalTime.MAX));
  }


  public static Date toDate(int period) {
    int year = period / 100;
    int month = period % 100;
    LocalDateTime localDateTime = LocalDateTime.of(year, month, 1, 0, 0,0);
    return toDate(localDateTime);
  }

  public static int toPeriod(Date date) {
    LocalDateTime localDateTime = toLocalDateTime(date);
    return formatPeriod(localDateTime.getYear(), localDateTime.getMonthValue());
  }

  public static int toPeriod(long epochMilli) {
    LocalDate localDate = toLocalDate(epochMilli);
    return formatPeriod(localDate.getYear(), localDate.getMonthValue());
  }

  public static int toPrePeriod(long epochMilli) {
    LocalDate localDate = toLocalDate(epochMilli).minusMonths(1L);
    return formatPeriod(localDate.getYear(), localDate.getMonthValue());
  }

  public static int toPeriod(LocalDate localDate) {
    return formatPeriod(localDate.getYear(), localDate.getMonthValue());
  }

  public static int getNowPeriod() {
    LocalDate localDate = LocalDate.now();
    return formatPeriod(localDate.getYear(), localDate.getMonthValue());
  }

  public static LocalDate toLocalDate(int period) {
    int year = period / 100;
    int month = period % 100;
    return LocalDate.of(year, month, 1);
  }

  public static long toStartOfMonth(int yearMonth) {
    LocalDate start = toLocalDate(yearMonth);
    return toEpochMilli(start);
  }

  public static long toEndOfMonth(int yearMonth) {
    LocalDate end = toLocalDate(yearMonth);
    end = end.withDayOfMonth(end.lengthOfMonth());
    return toEpochMilli(end);
  }

  public static long diffYears(int startPeriod, int endPeriod) {
    LocalDate startDate = toLocalDate(startPeriod);
    LocalDate endDate = toLocalDate(endPeriod);
    return ChronoUnit.YEARS.between(startDate, endDate);
  }

  public static int prevMonth() {
    LocalDate localDate = LocalDate.now().minusMonths(1L);
    return formatPeriod(localDate.getYear(), localDate.getMonthValue());
  }

  public static int nextMonth(int period){
    int year = period / 100;
    int month = period % 100;
    LocalDate localDate = LocalDate.of(year, month, 1).plusMonths(1);
    return formatPeriod(localDate.getYear(), localDate.getMonthValue());
  }
  public static int prevMonth(int period){
    int year = period / 100;
    int month = period % 100;
    LocalDate localDate = LocalDate.of(year, month, 1).minusMonths(1);
    return formatPeriod(localDate.getYear(), localDate.getMonthValue());
  }

  public static int nextMonth(long epochMilli){
    LocalDate tempLocalDate = toLocalDate(epochMilli);
    int year = tempLocalDate.getYear();
    int month = tempLocalDate.getMonthValue();
    LocalDate localDate = LocalDate.of(year, month, 1).plusMonths(1);
    return formatPeriod(localDate.getYear(), localDate.getMonthValue());
  }

  /**
   * 获取当月所有日期
   * @param month YYYYMM格式
   * @return 当月所有日期列表
   */
  public static List<LocalDate> datesThisMonth(Integer month) {
    LocalDate firstDate = toLocalDate(month);
    return IntStream.rangeClosed(1, firstDate.lengthOfMonth()).boxed()
        .map(firstDate::withDayOfMonth).collect(Collectors.toList());
  }

  public static String toWeekday(LocalDate date, TextStyle style, Locale locale) {
    return date.getDayOfWeek().getDisplayName(style, locale);
  }

  public static List<Integer> convertIntYearMonths(Integer from, Integer to) {
    LocalDate startDate = toLocalDate(from);
    LocalDate endDate = toLocalDate(to);
    long untilMonths = ChronoUnit.MONTHS.between(startDate, endDate);
    return LongStream.rangeClosed(0, untilMonths).mapToObj(idx -> {
      LocalDate thisMonth  = startDate.plusMonths(idx);
      int year = thisMonth.getYear();
      int month = thisMonth.getMonthValue();
      return year * 100 + month;
    }).collect(Collectors.toList());
  }

  public static List<YearMonth> convertYearMonths(Integer from, Integer to) {
    LocalDate startDate = toLocalDate(from);
    LocalDate endDate = toLocalDate(to);
    long untilMonths = ChronoUnit.MONTHS.between(startDate, endDate);
    return LongStream.rangeClosed(0, untilMonths).mapToObj(idx -> {
      LocalDate thisMonth  = startDate.plusMonths(idx);
      return YearMonth.from(thisMonth);
    }).collect(Collectors.toList());
  }

  /**
   * 闰年中每月天数
   */
  private static final int[] DAYS_P_MONTH_LY = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

  /**
   * 非闰年中每月天数
   */
  private static final int[] DAYS_P_MONTH_CY = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

  /**
   * 代表数组里的年、月、日
   */
  private static final int Y = 0, M = 1, D = 2;



  public static LocalDateTime toLocalDateTime(Date date) {
    notNull(date);
    return date.toInstant().atZone(SYSTEM_DEFAULT_ZONE_ID).toLocalDateTime();
  }

  public static Date toDate(LocalDateTime time) {
    notNull(time);
    return Date.from(time.atZone(SYSTEM_DEFAULT_ZONE_ID).toInstant());
  }

  public static long toEpochMilli(LocalDateTime time) {
    notNull(time);
    return time.atZone(SYSTEM_DEFAULT_ZONE_ID).toInstant().toEpochMilli();
  }

  public static LocalDateTime toLocalDateTime(long epochMilli) {
    if (epochMilli <= 0L)
      throw new IllegalArgumentException("[epochMilli] must be greater than 0.");
    return Instant.ofEpochMilli(epochMilli)
            .atZone(SYSTEM_DEFAULT_ZONE_ID).toLocalDateTime();
  }

  public static String formatLocalDateTime(LocalDateTime dateTime, String formatter) {
    if (CheckUtils.isEmpty(dateTime)) {
      return "";
    }
    String strDt = "";
    try {
      DateTimeFormatter format = DateTimeFormatter.ofPattern(formatter);
      strDt = dateTime.format(format);

    } catch (DateTimeException ex) {
    }
    return strDt;
  }

  public static String formatLongDateTime(Long dateTime, String formatter) {
    if (CheckUtils.isEmpty(dateTime)) {
      return "";
    }
    String strDt = "";
    try {
      LocalDateTime ldt = toLocalDateTime(dateTime);
      DateTimeFormatter format = DateTimeFormatter.ofPattern(formatter);
      strDt = ldt.format(format);

    } catch (DateTimeException ex) {
    }
    return strDt;
  }

  public static Long toLongDateTime(String dateTime) throws ParseException {
    if (CheckUtils.isEmpty(dateTime)) {
      return null;
    }
    //Date dt = new Date(dateTime);
    //return dt.getTime();
    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
    Date date = dt.parse(dateTime);

    return date.getTime();
  }

  /**
   * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
   *
   * @param strDate
   * @return
   */
  public static Long strToDateLong(String strDate)  {
    Date newDate=new Date();
    try {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      newDate = formatter.parse(strDate);
    }catch (Exception e){
      e.printStackTrace();
    }
    return newDate.getTime();
  }

  /**
   * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
   *
   * @param strDate
   * @return
   */
  public static Long strToDateLong(String strDate,String format)  {
    Date newDate=new Date();
    try {
      SimpleDateFormat formatter = new SimpleDateFormat(format);
      newDate = formatter.parse(strDate);
    }catch (Exception e){
      e.printStackTrace();
    }
    return newDate.getTime();
  }

  /**
   * 将长时间格式字符串转换为时间 yyyy-MM-dd
   *
   * @param strDate
   * @return
   */
  public static Long stringToDateLong(String strDate) {
    Long strtodateTime = 0L;
    try {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      Date strtodate = formatter.parse(strDate);
      strtodateTime = strtodate.getTime();
      strtodate.getTime();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return strtodateTime;
  }

  /**
   * 将长时间格式字符串转换为时间 yyyy-MM-dd
   *
   * @param strDate
   * @return
   */
  public static Long stringToDateLong(String strDate,String dateFormt) {
    Long strtodateTime = 0L;
    try {
      SimpleDateFormat formatter = new SimpleDateFormat(dateFormt);
      Date strtodate = formatter.parse(strDate);
      strtodateTime = strtodate.getTime();
      strtodate.getTime();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return strtodateTime;
  }

  public static Date stringToDate(String strDate) {
    Date strtodate=new Date();
    try {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      strtodate = formatter.parse(strDate);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return strtodate;
  }

  public static String dateToString(Date date) {
    String strtodate=null;
    try {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      strtodate = formatter.format(date);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return strtodate;
  }

  public static boolean isDimission(Long dimissionDate) {
    if (CheckUtils.isEmpty(dimissionDate) || dimissionDate <= 0) {
      return false;
    }

    LocalDate today = LocalDate.now();
    int salaryYear = today.getYear();
    int salaryMonth = today.getMonthValue();

    if (salaryMonth == 1) {
      salaryYear = salaryYear - 1;
      salaryMonth = 12;
    }

    Calendar dimCal = Calendar.getInstance();
    dimCal.setTimeInMillis(dimissionDate);

    int dimYear = dimCal.get(Calendar.YEAR);
    int dimMonth = dimCal.get(Calendar.MONTH) + 1;

    if (salaryYear <= dimYear && salaryMonth <= dimMonth) {
      return false;
    }

    return true;
  }

  public static int getCurrentYear() {
    LocalDate today = LocalDate.now();
    return today.getYear();
  }

  public static int getCurrentMonth() {
    LocalDate today = LocalDate.now();
    return today.getMonthValue();
  }

  public static int getCurrentDay() {
    LocalDate today = LocalDate.now();
    return today.getDayOfMonth();
  }

  public static int getLastMonth() {
    Date today = new Date();

    Calendar cal = Calendar.getInstance();
    cal.setTime(today);
    cal.add(Calendar.MONTH, -1);

    return cal.getTime().getMonth();
  }

  public static Date getNextMonthDate(Date nowDate) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(nowDate);
    cal.add(Calendar.MONTH, 1);

    return cal.getTime();
  }

  /**
   * 收集起始时间到结束时间之间所有的时间并以字符串集合方式返回
   */
  public static List<String> collectLocalDates(String timeStart, String timeEnd) {
    LocalDate startTime = LocalDate.parse(timeStart);
    LocalDate endTime = LocalDate.parse(timeEnd);
    return Stream.iterate(startTime, localDate -> localDate.plusDays(1))
            // 截断无限流，长度为起始时间和结束时间的差+1个
            .limit(ChronoUnit.DAYS.between(startTime, endTime) + 1)
            // 由于最后要的是字符串，所以map转换一下
            .map(LocalDate::toString)
            // 把流收集为List
            .collect(Collectors.toList());
  }

  /**
   * Long型时间转换成字符串
   *
   * @param time
   * @return
   */
  public static String LongToString(Long time) {
    String times = "";
    try {
      Date date = new Date(time);
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      times = df.format(date);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return times;
  }

  /**
   * Long型时间转换成字符串
   *
   * @param time
   * @return
   */
  public static String LongToStringFormat(Long time,String format) {
    String times = "";
    try {
      Date date = new Date(time);
      SimpleDateFormat df = new SimpleDateFormat(format);
      times = df.format(date);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return times;
  }

  /**
   * Long型时间转换成字符串
   *
   * @param time
   * @return
   */
  public static String LongToStringChinese(Long time) {
    String times = "";
    try {
      Date date = new Date(time);
      SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
      times = df.format(date);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return times;
  }

  /**
   * Long型时间转换成字符串(yyyy-MM)
   *
   * @param time
   * @return
   */
  public static String LongToStringMonth(Long time) {
    String times = "";
    try {
      Date date = new Date(time);
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
      times = df.format(date);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return times;
  }

  /**
   * 获取查询时间区间和日历区间集合的字符串数组
   * 获取两个集合的交集
   */
  public static List<String> retainAllDates(Long startTime, Long endTime, Long searchStartTime, Long searchEndTime) {
    List<String> strTime = collectLocalDates(LongToString(startTime), LongToString(endTime));
    List<String> strSearchTime = collectLocalDates(LongToString(searchStartTime), LongToString(searchEndTime));
    strTime.retainAll(strSearchTime);
    return strTime;
  }


  /**
   * 将代表日期的字符串分割为代表年月日的整形数组
   *
   * @param date
   * @return
   */
  public static int[] splitYMD(String date) {
    date = date.replace("-", "");
    int[] ymd = {0, 0, 0};
    ymd[Y] = Integer.parseInt(date.substring(0, 4));
    ymd[M] = Integer.parseInt(date.substring(4, 6));
    ymd[D] = Integer.parseInt(date.substring(6, 8));
    return ymd;
  }

  /**
   * 检查传入的参数代表的年份是否为闰年
   *
   * @param year
   * @return
   */
  public static boolean isLeapYear(int year) {
    return year >= gregorianCutoverYear ?
            ((year % 4 == 0) && ((year % 100 != 0) || (year % 400 == 0))) : (year % 4 == 0);
  }

  /**
   * 日期加1天
   *
   * @param year
   * @param month
   * @param day
   * @return
   */
  private static int[] addOneDay(int year, int month, int day) {
    if (isLeapYear(year)) {
      day++;
      if (day > DAYS_P_MONTH_LY[month - 1]) {
        month++;
        if (month > 12) {
          year++;
          month = 1;
        }
        day = 1;
      }
    } else {
      day++;
      if (day > DAYS_P_MONTH_CY[month - 1]) {
        month++;
        if (month > 12) {
          year++;
          month = 1;
        }
        day = 1;
      }
    }
    int[] ymd = {year, month, day};
    return ymd;
  }

  /**
   * 将不足两位的月份或日期补足为两位
   *
   * @param decimal
   * @return
   */
  public static String formatMonthDay(int decimal) {
    DecimalFormat df = new DecimalFormat("00");
    return df.format(decimal);
  }

  /**
   * 将不足四位的年份补足为四位
   *
   * @param decimal
   * @return
   */
  public static String formatYear(int decimal) {
    DecimalFormat df = new DecimalFormat("0000");
    return df.format(decimal);
  }

  /**
   * 计算两个日期之间相隔的天数
   *
   * @param begin
   * @param end
   * @return
   * @throws ParseException
   */
  public static long countDay(String begin, String end) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date beginDate, endDate;
    long day = 0;
    try {
      beginDate = format.parse(begin);
      endDate = format.parse(end);
      day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return day;
  }

  /**
   * 以循环的方式计算日期
   *
   * @param beginDate beginDate
   * @param endDate   endDate
   * @return
   */
  public static List<String> getEveryday(String beginDate, String endDate) {
    long days = countDay(beginDate, endDate);
    int[] ymd = splitYMD(beginDate);
    List<String> everyDays = new ArrayList<String>();
    everyDays.add(beginDate);
    for (int i = 0; i < days; i++) {
      ymd = addOneDay(ymd[Y], ymd[M], ymd[D]);
      everyDays.add(formatYear(ymd[Y]) + "-" + formatMonthDay(ymd[M]) + "-" + formatMonthDay(ymd[D]));
    }
    return everyDays;
  }

  /**
   * 以循环的方式计算日期精确到秒
   * attendance
   * "yyyy-MM-dd HH:mm:ss"
   * "yyyy-MM-dd"
   */
  public static String timeStampFormater(Timestamp ts,String format) {
    DateFormat sdf = new SimpleDateFormat(format);

    return sdf.format(ts);
  }


/*  *//**
   * 开始时间转换YYYYMMDD
   *//*
  public static Long toStartEpochMilli(int period) {
    int year = period / 10000;
    int month = (period % 10000) / 100;
    int day = period % 100;
    LocalDateTime localDateTime = LocalDateTime.of(year, month, day, 0, 0, 0);
    return toEpochMilli(localDateTime);
  }

  *//**
   * 结束时间转换YYYYMMDD
   *//*
  public static Long toEndEpochMilli(int period) {
    int year = period / 10000;
    int month = (period % 10000) / 100;
    int day = period % 100;
    LocalDateTime localDateTime = LocalDateTime.of(year, month, day, 23, 59, 59);
    return toEpochMilli(localDateTime);
  }

  *//**
   * 结束时间转换YYYYMM
   *//*
  public static Long toStartYMEpochMilli(int period) {
    int year = period / 100;
    int month = period % 100;
    LocalDateTime localDateTime = LocalDateTime.of(year, month, 1, 0, 0, 0);
    return toEpochMilli(localDateTime);
  }

  *//**
   * 结束时间转换YYYYMM
   *//*
  public static Long toEndYMEpochMilli(int period) {
    int year = period / 100;
    int month = period % 100;
    LocalDateTime localDateTime = LocalDateTime.of(year, month, 1, 23, 59, 59).plusMonths(1).minusDays(1);
    return toEpochMilli(localDateTime);
  }*/

  /**
   * 获得当天的起始时间
   *
   * @return
   */
  public static Calendar getStartDate(Calendar today) {
    today.set(Calendar.HOUR_OF_DAY, 0);
    today.set(Calendar.MINUTE, 0);
    today.set(Calendar.SECOND, 0);
    today.set(Calendar.MILLISECOND, 0);
    return today;
  }

  /**
   * 获取当天截止时间
   *
   * @return
   */
  public static Calendar getEndDate(Calendar endToday) {
    endToday.set(Calendar.HOUR_OF_DAY, 23);
    endToday.set(Calendar.MINUTE, 59);
    endToday.set(Calendar.SECOND, 59);
    endToday.set(Calendar.MILLISECOND, 59);
    return endToday;
  }

  /**
   * 获得当月起始时间
   *
   * @return
   */
  public static Calendar getStartMounth(Calendar today) {
    Calendar calendar = getStartDate(today);
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
    return calendar;
  }

  /**
   * 获得当月结束时间
   *
   * @return
   */
  public static Calendar getEndMounth(Calendar today) {
    Calendar endMounth = getEndDate(today);
    endMounth.set(Calendar.DAY_OF_MONTH, endMounth.getActualMaximum(endMounth.DAY_OF_MONTH));
    return endMounth;
  }

  /**
   * 获取当年起始时间
   */
  public static Calendar getStartYear(Calendar today) {
    try {
      today.set(Calendar.MONTH, 0);
      today.set(Calendar.DAY_OF_MONTH, today.getActualMinimum(Calendar.DAY_OF_MONTH));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return today;
  }

  /**
   * 获取当年结束时间
   */
  public static Calendar getEndYear(Calendar today) {
    try {
      today.set(Calendar.MONTH, 11);
      today.set(Calendar.DAY_OF_MONTH, today.getMaximum(Calendar.DAY_OF_MONTH));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return today;
  }

  public static String getLastMonday() {
    Calendar cal = Calendar.getInstance();
    cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
    cal.add(Calendar.DATE, -1*7);
    cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
    String monday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    return monday;
  }

  public static String getLastSunday() {
    Calendar cal = Calendar.getInstance();
    cal.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
    cal.add(Calendar.DATE, -1*7);
    cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    String sunday = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    return sunday;
  }

  /**
   * 获取更新截止时间
   */
  public static Long getExpiryTime(Long today) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(today);
    cal.add(Calendar.YEAR, 2);
    return cal.getTimeInMillis();
  }

  /**
   * 按周复制日期
   *
   * @param startLong
   * @param endLong
   * @param currentStart
   * @param currentEnd
   * @return List<String>
   */
  public static List<String> toDomainByWeek(Long startLong, Long endLong, Long currentStart, Long currentEnd) {
    List weekList = new ArrayList<>();
    Long minus = endLong - startLong;
    weekList.add(toDomainByWeekOut(startLong, currentStart, currentEnd, minus));
    ArrayList weekStartList = (ArrayList) weekList.get(0);
    if (weekStartList.size() != 0)
      weekList.add(toDomainByWeekCur(weekStartList, minus));
    return weekList;
  }

  /**
   * 按周复制日期计算结束时间
   *
   * @param weekStartList
   * @param minus
   * @return List<String>
   */
  public static List<String> toDomainByWeekCur(ArrayList weekStartList, Long minus) {
    List weekList = new ArrayList<>();
    for (int k = 0; k < weekStartList.size(); k++) {
      Long weekEnd = Long.valueOf(weekStartList.get(k).toString());
      weekList.add(weekEnd + minus);
    }
    return weekList;
  }

  /**
   * 按周复制日期从方法
   *
   * @param startLong
   * @param currentStart
   * @param currentEnd
   * @return List<String>
   */
  public static List<String> toDomainByWeekOut(Long startLong, Long currentStart, Long currentEnd, Long minus) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(startLong);
    List weekList = new ArrayList<>();

    for (int j = 0; j < 5200; j++) {
      cal.add(Calendar.DATE, 7);
      //if ( currentEnd<cal.getTimeInMillis()+minus) break;
      if ((cal.getTimeInMillis() >= currentStart && cal.getTimeInMillis() <= currentEnd) ||
              (cal.getTimeInMillis() <= currentStart && cal.getTimeInMillis() + minus >= currentEnd) ||
              (cal.getTimeInMillis() + minus >= currentStart && cal.getTimeInMillis() + minus <= currentEnd)) {
        weekList.add(cal.getTimeInMillis());
      }
    }
    return weekList;
  }

  /**
   * 按月复制日期
   *
   * @param startLong
   * @param endLong
   * @param currentStart(区间)
   * @param currentEnd(区间)
   * @return List<String>
   */
  public static List<String> toDomainByMonth(Long startLong, Long endLong, Long currentStart, Long currentEnd) {
    List monthList = new ArrayList<>();
    Long minus = endLong - startLong;
    monthList.add(toDomainByMonthOut(startLong, currentStart, currentEnd, minus));
    ArrayList monthStartList = (ArrayList) monthList.get(0);
    if (monthStartList.size() != 0)
      monthList.add(toDomainByMonthCur(monthStartList, minus));
    return monthList;
  }

  /**
   * 按月复制日期抽出结束时间
   *
   * @param monthStartList
   * @param minus
   * @return int
   */
  public static List<String> toDomainByMonthCur(ArrayList monthStartList, Long minus) {
    List monthList = new ArrayList<>();
    for (int k = 0; k < monthStartList.size(); k++) {
      Long monthEnd = Long.valueOf(monthStartList.get(k).toString());
      monthList.add(monthEnd + minus);
    }
    return monthList;
  }

  /**
   * 按月复制日期从方法
   *
   * @param dateLong
   * @param currentStart
   * @param currentEnd
   * @return List<String>
   */
  public static List<String> toDomainByMonthOut(Long dateLong, Long currentStart, Long currentEnd, Long minus) {
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormt);
    SimpleDateFormat sdfnaor = new SimpleDateFormat(normalFormt);
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(dateLong);

    int dayStart = Integer.parseInt(sdf.format(cal.getTime())) % 100;
    int dateMaxday = getDaysByYearMonth(Integer.parseInt(sdf.format(cal.getTime())) / 100
            , Integer.parseInt(sdf.format(cal.getTime())) % 10000 / 100);
    int dateMin = dateMaxday - dayStart;

    List monthList = new ArrayList<>();
    for (int k = 0; k < 1200; k++) {
      cal.add(Calendar.MONTH, 1);
      int maxday = getDaysByYearMonth(Integer.parseInt(sdf.format(cal.getTime())) / 100
              , Integer.parseInt(sdf.format(cal.getTime())) % 10000 / 100);
      if (dayStart > maxday) {
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        //int min = dateMaxday - maxday;
        cal.add(Calendar.DAY_OF_MONTH, -dateMin);
      } else {
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        int min = maxday - dayStart;
        //if(maxday==dateMaxday)
        cal.add(Calendar.DAY_OF_MONTH, -min);
      }
      //TestCoding
      //String st = sdfnaor.format(cal.getTime());
      //String ed = LongToYMD(cal.getTimeInMillis()+minus);
      //String da1 = LongToYMD(currentStart);
      //String da2 = LongToYMD(currentEnd);
      //if ( currentEnd<cal.getTimeInMillis()+minus) break;
      if ((cal.getTimeInMillis() >= currentStart && cal.getTimeInMillis() <= currentEnd) ||
              (cal.getTimeInMillis() <= currentStart && cal.getTimeInMillis() + minus >= currentEnd) ||
              (cal.getTimeInMillis() + minus >= currentStart && cal.getTimeInMillis() + minus <= currentEnd)) {
        monthList.add(cal.getTimeInMillis());

      }
    }
    return monthList;
  }


  /**
   * 根据年 月 获取对应的月份 天数
   */
  public static int getDaysByYearMonth(int year, int month) {

    Calendar a = Calendar.getInstance();
    a.set(Calendar.YEAR, year);
    a.set(Calendar.MONTH, month - 1);
    a.set(Calendar.DATE, 1);
    a.roll(Calendar.DATE, -1);
    int maxDate = a.get(Calendar.DATE);
    return maxDate;
  }

  /**
   * 按年复制日期主方法
   *
   * @param startLong
   * @param endLong
   * @param currentStart
   * @param currentEnd
   * @return List<String>
   */
  public static List<String> toDomainByYear(Long startLong, Long endLong, Long currentStart, Long currentEnd) {
    List yearList = new ArrayList<>();
    Long minus = endLong - startLong;

    yearList.add(toDomainByYearOut(startLong, currentStart, currentEnd, minus));
    ArrayList yearStartList = (ArrayList) yearList.get(0);
    if (yearStartList.size() != 0)
      yearList.add(toDomainByYearCur(yearStartList, minus));
    return yearList;
  }

  /**
   * 按年复制日期推算结束时间
   *
   * @param yearStartList
   * @param minus
   * @return List<String>
   */
  public static List<String> toDomainByYearCur(ArrayList yearStartList, Long minus) {
    List yearList = new ArrayList<>();
    for (int k = 0; k < yearStartList.size(); k++) {
      Long yearEnd = Long.valueOf(yearStartList.get(k).toString());
      yearList.add(yearEnd + minus);
    }
    return yearList;
  }

  /**
   * 按年复制日期从方法
   *
   * @param dateLong
   * @param currentStart
   * @param currentEnd
   * @return List<String>
   */
  public static List<String> toDomainByYearOut(Long dateLong, Long currentStart, Long currentEnd, Long minus) {
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormt);
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(dateLong);

    int day = Integer.parseInt(sdf.format(cal.getTime())) % 100;
    int month = Integer.parseInt(sdf.format(cal.getTime())) % 10000 / 100;
    int dateMaxday = getDaysByYearMonth(Integer.parseInt(sdf.format(cal.getTime())) / 100
            , Integer.parseInt(sdf.format(cal.getTime())) % 10000 / 100);
    int dateMin = dateMaxday - day;


    List yearList = new ArrayList<>();
    for (int j = 0; j < 100; j++) {
      cal.add(Calendar.YEAR, 1);
      //if ( currentEnd<cal.getTimeInMillis()+minus) break;
      if (month == 2 && day == 29) {
        int maxday = getDaysByYearMonth(Integer.parseInt(sdf.format(cal.getTime())) / 100
                , Integer.parseInt(sdf.format(cal.getTime())) % 10000 / 100);
        if (day > maxday) {
          cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
          int min = dateMaxday - maxday;
          cal.add(Calendar.DAY_OF_MONTH, -dateMin);
        } else {
          cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
          int min = maxday - day;
          cal.add(Calendar.DAY_OF_MONTH, -min);
        }
      }
      if ((cal.getTimeInMillis() >= currentStart && cal.getTimeInMillis() <= currentEnd) ||
              (cal.getTimeInMillis() <= currentStart && cal.getTimeInMillis() + minus >= currentEnd) ||
              (cal.getTimeInMillis() + minus >= currentStart && cal.getTimeInMillis() + minus <= currentEnd)) {
        yearList.add(cal.getTimeInMillis());
      }
    }
    return yearList;
  }

  /**
   * 按日复制日期主方法
   *
   * @param startLong
   * @param endLong
   * @param currentEnd
   * @return List<String> normalFormt
   */
  public static List<String> toDomainByDay(Long startLong, Long endLong, Long currentStart, Long currentEnd) {
    List dayList = new ArrayList<>();
    Long minus = endLong - startLong;

    dayList.add(toDomainByDayOut(startLong, currentStart, currentEnd, minus));
    ArrayList dayStartList = (ArrayList) dayList.get(0);
    if (dayStartList.size() != 0)
      dayList.add(toDomainByDayCur(dayStartList, minus));
    return dayList;
  }

  /**
   * 按日复制日期从方法
   *
   * @param dateLong
   * @param currentStart
   * @param currentEnd
   * @param minus
   * @return List<String>
   */
  public static List<String> toDomainByDayOut(Long dateLong, Long currentStart, Long currentEnd, Long minus) {
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(dateLong);

    List dayList = new ArrayList<>();
    for (int j = 0; j < 36500; j++) {
      cal.add(Calendar.DATE, 1);
      if (currentEnd < cal.getTimeInMillis() + minus) break;
      if ((cal.getTimeInMillis() >= currentStart && cal.getTimeInMillis() <= currentEnd) ||
              (cal.getTimeInMillis() <= currentStart && cal.getTimeInMillis() + minus >= currentEnd) ||
              (cal.getTimeInMillis() + minus >= currentStart && cal.getTimeInMillis() + minus <= currentEnd)) {
        dayList.add(cal.getTimeInMillis());
      }
    }
    return dayList;
  }

  /**
   * 按日复制日期抽出结束时间
   *
   * @param dayStartList
   * @param minus
   * @return int
   */
  public static List<String> toDomainByDayCur(ArrayList dayStartList, Long minus) {
    List dayList = new ArrayList<>();
    for (int k = 0; k < dayStartList.size(); k++) {
      Long dayEnd = Long.valueOf(dayStartList.get(k).toString());
      dayList.add(dayEnd + minus);
    }
    return dayList;
  }

  /**
   * 字符串集合去重
   */
  public static List<String> removeRepeatString(List<String> lists) {
    HashSet hash = new HashSet(lists);
    lists.clear();
    lists.addAll(hash);
    return lists;
  }

  /**
   * Long集合去重
   */
  public static List<Long> removeRepeatLong(List<Long> lists) {
    HashSet hash = new HashSet(lists);
    lists.clear();
    lists.addAll(hash);
    return lists;
  }

  /**
   * Long型时间转换成normalFormt
   *
   * @param data
   * @return
   */
  public static String LongToYMD(Long data) {
    SimpleDateFormat sdf = new SimpleDateFormat(normalFormt);
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(data);
    return sdf.format(cal.getTime());
  }

  /**
   * 提醒用时间minute换算转换成normalFormt
   *
   * @param startTime
   * @param endTime
   * @return
   */
  public static String toRemindReMin(Long startTime, Long endTime, int minutes) {
    SimpleDateFormat sdf = new SimpleDateFormat(normalFormt);
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(startTime);
    cal.add(Calendar.MINUTE, -minutes);
    return sdf.format(cal.getTimeInMillis());
  }

  /**
   * 提醒用时间hour换算转换成normalFormt
   *
   * @param startTime
   * @param endTime
   * @return
   */
  public static String toRemindReHr(Long startTime, Long endTime, int hours) {
    SimpleDateFormat sdf = new SimpleDateFormat(normalFormt);
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(startTime);
    cal.add(Calendar.HOUR_OF_DAY, -hours);
    return sdf.format(cal.getTimeInMillis());
  }

  /**
   * 提醒用时间day换算转换成normalFormt
   *
   * @param startTime
   * @param endTime
   * @return
   */
  public static String toRemindReDay(Long startTime, Long endTime, int days) {
    SimpleDateFormat sdf = new SimpleDateFormat(normalFormt);
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(startTime);
    cal.add(Calendar.DATE, -days);
    return sdf.format(cal.getTimeInMillis());
  }

  /**
   * 上班打卡时间规则转换
   *
   * @param
   * @return
   */
  public static String attendanceTimeOn(String time) {
    //time="2017-08-15 02:20:00";
    String date = time.split(" ")[0];
    Integer hour = Integer.valueOf(time.split(" ")[1].split(":")[0]);
    //秒数位拼接
    String secondString = ":00";
    String minuteString = "";
    String hourString = "";
    Integer minute = Integer.valueOf(time.split(":")[1]);
    if (minute>0&&minute <= 30) {
      minute = 30;
      if (hour < 10) {
        hourString = "0" + hour;
      } else {
        hourString = "" + hour;
      }
      minuteString = ":"+minute;
    } else if (minute > 30) {
      hour = hour + 1;
      minuteString = ":00";
      if (hour < 10) {
        hourString = "0" + hour;
      } else {
        hourString = "" + hour;
      }
    }else if(minute==0){
      if (hour < 10) {
        hourString = "0" + hour;
      } else {
        hourString = "" + hour;
      }
      minuteString = ":00";
    }
    String timeResult = date + " " + hourString + minuteString + secondString;
    return timeResult;
  }

  /**
   * 下班打卡时间规则转换
   *
   * @param
   * @return
   */
  public static String attendanceTimeOff(String time) {
    //time="2017-08-15 02:20:00";
    String date = time.split(" ")[0];
    Integer hour = Integer.valueOf(time.split(" ")[1].split(":")[0]);
    //秒数位拼接
    String secondString = ":00";
    String minuteString = "";
    String hourString = "";
    Integer minute = Integer.valueOf(time.split(":")[1]);
    if (minute < 30) {
      minuteString = ":00";
    } else if (minute >= 30) {
      minuteString = ":30";
    }
    if (hour < 10) {
      hourString = "0" + hour;
    } else if (hour >= 24) {
      hourString = "00";
    } else {
      hourString = "" + hour;
    }
    String timeResult = date + " " + hourString + minuteString + secondString;
    return timeResult;
  }

  /**
   * String转Calendar
   */
  public static Calendar toCalendar(String date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar=Calendar.getInstance();
    try {
      Date newDate = sdf.parse(date);
      calendar.setTime(newDate);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return calendar;
  }

  /**
   * Calendar转String
   */
  public static String calendarToString(Calendar calendar) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String dateStr="";
    try {
      dateStr = sdf.format(calendar.getTime());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return dateStr;
  }


  /**
   * Param(YYYY-MM-DD)
   * 获取前一天
   */
  public static String toYesterdayCalendar(String date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar=Calendar.getInstance();
    try {
      Date newDate = sdf.parse(date);
      calendar.setTime(newDate);
      calendar.add(Calendar.DATE, -1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return sdf.format(calendar.getTime());
  }

}
