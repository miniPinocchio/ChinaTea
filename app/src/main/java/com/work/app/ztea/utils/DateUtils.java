package com.work.app.ztea.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 时间工具
 *
 * @author Sun.bl
 * @version [1.0, 2016/6/23]
 */
public class DateUtils {


    private DateUtils() {
        throw new AssertionError("Utils class");
    }

    public static Date formatTimeToDate(String time) {

        if (TextUtils.isEmpty(time)) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            return dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 调此方法输入所要转换的时间输入例如（"2014-06-14 16:09:00"）返回时间戳
     *
     * @param time
     * @return
     */
    public static String formatDataToTime(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.CHINA);
        Date date;
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            String stf = String.valueOf(l);
            times = stf.substring(0, 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return times;
    }

    /**
     * 获取毫秒数
     * @return
     */
    public static long getMillionTime(String str){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.getDefault());
        try {
            long millionSeconds = sdf.parse(str).getTime();//毫秒
            return millionSeconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String formatTimeToStringNoYear(Date date) {

        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);

    }

    public static String formatTimeToStringYear(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年", Locale.getDefault());
        return dateFormat.format(date);
    }

    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt*1000L);
        res = simpleDateFormat.format(date);
        return res;
    }


    public static String formatTimeToStringMonth(Date date) {

        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM月", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String formatTimeToStringDay(Date date) {

        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("d天", Locale.getDefault());
        return dateFormat.format(date);

    }

    public static String formatTimeToStringMonthDay(Date date) {

        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日", Locale.getDefault());
        return dateFormat.format(date);

    }

    public static String formatTimeToStringMonthDayEnglish(Date date) {

        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd", Locale.getDefault());
        return dateFormat.format(date);

    }
    public static String formatTimeToStringYearMinute(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return dateFormat.format(date);
    }


    public static String formatTimeToStringHourMinute(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String formatTimeToStringYearHourMinute(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String formatTimeToStringYearMonth(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String formatTimeToStringYearMonthThree(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String formatTimeToStringYearMonthDay(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String formatTimeToStringYearMonthDayTwo(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String formatTimeToStringYearMonthTwo(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String formatTimeToStringHour(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String formatTimeToStringMinute(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("mm", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String formatTimeToStringSecond(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static void getDayTime(List<String> dayList){
        dayList.clear();
        dayList.add("今天");
        dayList.add("明天");
    }
    public static void getHourTime(List<String> hourList) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String time =  dateFormat.format(date);
        String mHour = time.substring(0, 2);
        String mMinute = time.substring(3, time.length());
        hourList.clear();

        if ((Integer.parseInt(mMinute)+15) < 59){
            for (int i = 0; i < 24; i++) {
                if (i< Integer.parseInt(mHour)){
                    continue;
                }
                if (i < 10){
                    hourList.add("0"+ String.valueOf(i)+"点");
                    continue;
                }
                hourList.add(String.valueOf(i)+"点");
            }
        }else {
            for (int i = 0; i < 24; i++) {
                if (i< Integer.parseInt(mHour)+1){
                    continue;
                }
                if (i < 10){
                    hourList.add("0"+ String.valueOf(i)+"点");
                    continue;
                }
                hourList.add(String.valueOf(i)+"点");
            }
        }
    }

    public static void getMinuteTime(List<String> minList) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String time =  dateFormat.format(date);
        String mMinute = time.substring(3, time.length());
        minList.clear();

        if ((Integer.parseInt(mMinute)+15) < 59){
            for (int i = 0; i < 60; i++) {
                if (i< Integer.parseInt(mMinute)+15){
                    continue;
                }
                if (i < 10){
                    minList.add("0"+ String.valueOf(i)+"分");
                    continue;
                }

                minList.add(String.valueOf(i)+"分");
            }
        }else {
            for (int i = 0; i < 60; i++) {
                if (i < (Integer.parseInt(mMinute)+15)-60){
                    continue;
                }
                if (i < 10){
                    minList.add("0"+ String.valueOf(i)+"分");
                    continue;
                }
                minList.add(String.valueOf(i)+"分");
            }
        }
    }

    /**
     * 判断预约叫车不能少于15分钟
     * @param time
     * @param hour
     * @param minute
     * @return
     */
    public static boolean isOverTime(String time, String hour, String minute){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String timeDay = DateUtils.formatTimeToStringYearMonthDay(date);
        if (!TextUtils.equals(timeDay,time)){
            return true;
        }
        String timeHour = DateUtils.formatTimeToStringHour(date);
        String timeMinute = DateUtils.formatTimeToStringMinute(date);
        if (Integer.parseInt(hour) > Integer.parseInt(timeHour)){
            if (Integer.parseInt(hour) - Integer.parseInt(timeHour) >= 2){
                return true;
            }
            if (Integer.parseInt(minute) >= 15){
                return true;
            }
            if (Integer.parseInt(minute) + (60 - Integer.parseInt(timeMinute)) >= 15){
                return true;
            }
        }
        if (Integer.parseInt(minute) - Integer.parseInt(timeMinute) >= 15){
            return true;
        }
        return false;
    }

    /**
     * 获取两个日期之间的间隔天数
     * @return
     */
    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 计算相差的小时
     *
     * @param starTime
     * @param endTime
     * @return
     */
    public static String getTimeDifferenceHour(String starTime, String endTime) {
        String timeString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime();
            String string = Long.toString(diff);

            float parseFloat = Float.parseFloat(string);

            float hour1 = parseFloat / (60 * 60 * 1000);

            timeString = Float.toString(hour1);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return timeString;

    }
}
