package com.lz.framework.common.util.date;

import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间工具类
 *
 * @author YY
 */
public class DateUtils {

    /**
     * 时区 - 默认
     */
    public static final String TIME_ZONE_DEFAULT = "GMT+8";

    /**
     * 秒转换成毫秒
     */
    public static final long SECOND_MILLIS = 1000;

    public static final String FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";

    public static final String FORMAT_YEAR_MONTH = "yyyy-MM";

    public static final String FORMAT_YEAR = "yyyy";

    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将 LocalDateTime 转换成 Date
     *
     * @param date LocalDateTime
     * @return LocalDateTime
     */
    public static Date of(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        // 将此日期时间与时区相结合以创建 ZonedDateTime
        ZonedDateTime zonedDateTime = date.atZone(ZoneId.systemDefault());
        // 本地时间线 LocalDateTime 到即时时间线 Instant 时间戳
        Instant instant = zonedDateTime.toInstant();
        // UTC时间(世界协调时间,UTC + 00:00)转北京(北京,UTC + 8:00)时间
        return Date.from(instant);
    }

    /**
     * 将 Date 转换成 LocalDateTime
     *
     * @param date Date
     * @return LocalDateTime
     */
    public static LocalDateTime of(Date date) {
        if (date == null) {
            return null;
        }
        // 转为时间戳
        Instant instant = date.toInstant();
        // UTC时间(世界协调时间,UTC + 00:00)转北京(北京,UTC + 8:00)时间
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static Date addTime(Duration duration) {
        return new Date(System.currentTimeMillis() + duration.toMillis());
    }

    public static boolean isExpired(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(time);
    }

    /**
     * 创建指定时间
     *
     * @param year  年
     * @param mouth 月
     * @param day   日
     * @return 指定时间
     */
    public static Date buildTime(int year, int mouth, int day) {
        return buildTime(year, mouth, day, 0, 0, 0);
    }

    /**
     * 创建指定时间
     *
     * @param year   年
     * @param mouth  月
     * @param day    日
     * @param hour   小时
     * @param minute 分钟
     * @param second 秒
     * @return 指定时间
     */
    public static Date buildTime(int year, int mouth, int day,
                                 int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, mouth - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0); // 一般情况下，都是 0 毫秒
        return calendar.getTime();
    }

    public static Date max(Date a, Date b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        return a.compareTo(b) > 0 ? a : b;
    }

    public static LocalDateTime max(LocalDateTime a, LocalDateTime b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        return a.isAfter(b) ? a : b;
    }

    /**
     * 是否今天
     *
     * @param date 日期
     * @return 是否
     */
    public static boolean isToday(LocalDateTime date) {
        return LocalDateTimeUtil.isSameDay(date, LocalDateTime.now());
    }

    /**
     * 是否昨天
     *
     * @param date 日期
     * @return 是否
     */
    public static boolean isYesterday(LocalDateTime date) {
        return LocalDateTimeUtil.isSameDay(date, LocalDateTime.now().minusDays(1));
    }

    /**
     * 获取时间范围,根据时间格式获取时间范围，默认yyyy-MM-dd，年月日，获取这段时间的每一天，
     * 如果是yyyy-MM则获取这段时间每一个月，如果是yyyy则获取这段时间每一个年
     * 支持任意日期格式，根据format中是否包含dd/MM/yyyy来自动判断处理方式
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param format    时间格式
     * @return 时间范围列表
     */
    public static List<String> getDataRange(String startTime, String endTime, String format) {
        // 默认格式为yyyy-MM-dd
        if (format == null || format.isEmpty()) {
            format = FORMAT_YEAR_MONTH_DAY;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        List<String> result = new ArrayList<>();

        // 提取日期部分（处理输入带时间的情况，如 "2026-02-01 00:00:00"）
        String startDateStr = startTime;
        String endDateStr = endTime;
        if (startTime.contains(" ") || startTime.contains("T")) {
            int spaceIndex = startTime.indexOf(" ");
            if (spaceIndex > 0) {
                startDateStr = startTime.substring(0, spaceIndex);
            } else if (startTime.contains("T")) {
                startDateStr = startTime.substring(0, startTime.indexOf("T"));
            }
        }
        if (endTime.contains(" ") || endTime.contains("T")) {
            int spaceIndex = endTime.indexOf(" ");
            if (spaceIndex > 0) {
                endDateStr = endTime.substring(0, spaceIndex);
            } else if (endTime.contains("T")) {
                endDateStr = endTime.substring(0, endTime.indexOf("T"));
            }
        }

        // 根据格式动态判断处理方式（忽略大小写）
        boolean hasDay = format.toLowerCase().contains("dd");
        boolean hasMonth = format.toLowerCase().contains("mm");
        boolean hasYear = format.toLowerCase().contains("yyyy");

        // 只有年份（yyyy），按年获取
        if (!hasDay && !hasMonth && hasYear && format.length() <= 4) {
            // 提取年份部分（处理输入包含月日或时间的情况）
            String startYearStr = startDateStr.length() >= 4 ? startDateStr.substring(0, 4) : startDateStr;
            String endYearStr = endDateStr.length() >= 4 ? endDateStr.substring(0, 4) : endDateStr;
            int startYear = Integer.parseInt(startYearStr);
            int endYear = Integer.parseInt(endYearStr);
            for (int year = startYear; year <= endYear; year++) {
                result.add(String.valueOf(year));
            }
        }
        // 年月格式（yyyy-MM），按月获取
        else if (hasYear && hasMonth && !hasDay) {
            // 提取年月部分（处理输入包含日期或时间的情况）
            String startYearMonthStr = startDateStr.length() >= 7 ? startDateStr.substring(0, 7) : startDateStr;
            String endYearMonthStr = endDateStr.length() >= 7 ? endDateStr.substring(0, 7) : endDateStr;
            // 使用标准小写yyyy-MM格式解析（避免YYYY被识别为周数年）
            DateTimeFormatter yearMonthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
            YearMonth startYearMonth = YearMonth.parse(startYearMonthStr, yearMonthFormatter);
            YearMonth endYearMonth = YearMonth.parse(endYearMonthStr, yearMonthFormatter);
            while (!startYearMonth.isAfter(endYearMonth)) {
                result.add(startYearMonth.format(yearMonthFormatter));
                startYearMonth = startYearMonth.plusMonths(1);
            }
        }
        // 年月日格式（yyyy-MM-dd 或其他含dd的格式），按天获取
        else if (hasDay && hasMonth && hasYear) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(FORMAT_YEAR_MONTH_DAY);
            LocalDate startDate = LocalDate.parse(startDateStr, dateFormatter);
            LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);
            while (!startDate.isAfter(endDate)) {
                result.add(startDate.format(dateFormatter));
                startDate = startDate.plusDays(1);
            }
        }
        // 只有月日格式（MM-dd），按天获取
        else if (hasDay && hasMonth && !hasYear) {
            LocalDate startDate = LocalDate.parse(startDateStr, formatter);
            LocalDate endDate = LocalDate.parse(endDateStr, formatter);
            while (!startDate.isAfter(endDate)) {
                result.add(startDate.format(formatter));
                startDate = startDate.plusDays(1);
            }
        }
        // 只有月份（MM），按月获取
        else if (hasMonth && !hasYear && !hasDay) {
            int startMonth = Integer.parseInt(startDateStr);
            int endMonth = Integer.parseInt(endDateStr);
            for (int month = startMonth; month <= endMonth; month++) {
                result.add(String.format("%02d", month));
            }
        }

        return result;
    }

    /**
     * 格式化时间类型为MySQL需要类型
     *
     * @param type 时间类型
     * @return MySQL需要类型
     */
    public static String formatDateType(String type) {
        if (type == null || type.isEmpty()) {
            return "%Y-%m-%d";
        }
        // 将Java日期格式转换为MySQL日期格式
        return type
                .replace("YYYY", "%Y")
                .replace("yyyy", "%Y")
                .replace("MM", "%m")
                .replace("mm", "%m")
                .replace("dd", "%d")
                .replace("DD", "%d")
                .replace("HH", "%H")
                .replace("mm", "%i")
                .replace("ss", "%s");
    }

    public static void main(String[] args) {
        System.out.println(getDataRange("2023-01-01", "2023-02-31", "YYYY-MM-dd"));
        System.out.println(getDataRange("2023-01", "2024-02", "yyyy-MM"));
        System.out.println(getDataRange("2023", "2024", "yyyy"));

        System.out.println(formatDateType("yyyy-MM-dd"));
        System.out.println(formatDateType("yyyy-MM"));
        System.out.println(formatDateType("yyyy"));
    }
}
