package com.zouyu.Utils;


import com.google.common.base.Strings;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 时间日期处理工具
 * String -> Date
 * Date -> String
 * 以及生成含有日期的字符串 可以当作保存id用等等。
 * 等格式化处理 
 * **/
public class DateUtil {
    private static final String FORMAT_0 = "yyyy-MM-dd HH:mm:ss";
    private static final String FORMAT_1 = "yyyy-MM-dd";
    private static final String FORMAT_2 = "HH:mm:ss";
    private static final String FORMAT_3 = "yyyyMMddHHmmss";

    //获得含有时间的id 字符串
    public static String getIdByTime(){
        Date now = new Date();
        SimpleDateFormat simple = new SimpleDateFormat(FORMAT_3);
        return simple.format(now);
    }
    //如果参数长度不为为0,则取第一个参数进行格式化，否则取当前日期时间，精确到秒 如:2010-4-15 9:36:38
    public static String toFull(Date... date) {
        SimpleDateFormat simple = new SimpleDateFormat(FORMAT_0);
        if (date!=null && date.length > 0) {
            return simple.format(date[0]);
        }
        return simple.format(new Date());
    }
    /**
     * 如果参数长度不为为0,则取第一个参数进行格式化，<br>
     * 否则取当前日期 如:2010-4-15
     * 
     * @param Date
     *            ... 可变参数
     * @return java.lang.String
     * **/
    public static String toDate(Date... date) {
        SimpleDateFormat simple = new SimpleDateFormat(FORMAT_1);
        if (date.length > 0) {
            return simple.format(date[0]);
        }
        return simple.format(new Date());
    }

    /**
     * 如果参数长度不为为0,则取第一个参数进行格式化，<br>
     * 否则取当前日期时间，精确到秒<br>
     * 如:9:36:38
     * 
     * @param Date
     *            ... 可变参数
     * @return java.lang.String
     * **/
    public static String toTime(Date... date) {
        SimpleDateFormat simple = new SimpleDateFormat(FORMAT_2);
        if (date.length > 0) {
            return simple.format(date[0]);
        }
        return simple.format(new Date());
    }

    /**
     * 根据字符串格式去转换相应格式的日期和时间
     * 
     * @param java
     *            .lang.String 必要参数
     * @return java.util.Date
     * @exception ParseException
     *                如果参数格式不正确会抛出此异常
     * **/
    public static Date reverse2Date(String date) {
        SimpleDateFormat simple = null;
        switch (date.trim().length()) {
        case 19:// 日期+时间
            simple = new SimpleDateFormat(FORMAT_0);
            break;
        case 10:// 仅日期
            simple = new SimpleDateFormat(FORMAT_1);
            break;
        case 8:// 仅时间
            simple = new SimpleDateFormat(FORMAT_2);
            break;
        default:
            break;
        }
        try {
            return simple.parse(date.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将带有时、分、秒格式的日期转化为00:00:00<br>
     * 方便日期推算,格式化后的是yyyy-MM-dd 00:00:00
     * 
     * @param java
     *            .util.Date... date的长度可以为0,即不用给参数
     * @return java.util.Date
     * **/
    public static Date startOfADay(Date... date) {
        SimpleDateFormat simple = new SimpleDateFormat(FORMAT_1);
        Date date_ = date.length == 0 ? new Date() : date[0];// 如果date为null则取当前时间
        String d = simple.format(date_);
        try {
            return simple.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 推算一个月内向前或向后偏移多少天,当然推算年也可以使用
     * 
     * @param date
     *            要被偏移的日期,<br>
     *            amout 偏移量<br>
     *            b 是否先将date格式化为yyyy-MM-dd 00:00:00 即: 是否严格按整天计算
     * @return java.util.Date
     * **/
    public static Date addDayOfMonth(Date date, Integer amount, Boolean b) {
        date = date == null ? new Date() : date;// 如果date为null则取当前日期
        if (b) {
            date = startOfADay(date);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, amount);
        return cal.getTime();
    }

    /**
     * 推算一个小时内向前或向后偏移多少分钟,除了秒、毫秒不可以以外,其他都可以
     * 
     * @param date
     *            要被偏移的日期,<br>
     *            amout 偏移量<br>
     *            b 是否先将date格式化为yyyy-MM-dd HH:mm:00 即: 是否严格按整分钟计算
     * @return java.util.Date
     * **/
    public static Date addMinuteOfHour(Date date, Integer amount, Boolean b) {
        date = date == null ? new Date() : date;// 如果date为null则取当前日期
        if (b) {
            SimpleDateFormat simple = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:00");
            try {
                date = simple.parse(simple.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, amount);
        return cal.getTime();
    }



    public static void main(String[] args) {

      Calendar instance = Calendar.getInstance();
        int year = instance.get(Calendar.YEAR);
        int month = instance.get(Calendar.MONTH)+1;
        int day = instance.get(Calendar.DAY_OF_MONTH);
        System.out.println("-"+year+month+day);

        Map<String,Integer> map = new HashMap<>();
        map.put("s1",1);
        map.put("s2",2);
        map.put("s3",3);
        map.put("s4",4);
        String key1 = map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue)).findFirst().get().getKey();
        String key2 = map.entrySet().stream().sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue())).findFirst().get().getKey();
        String key3 = map.entrySet().stream().max((o1, o2) -> o1.getValue().compareTo(o2.getValue())).get().getKey();
        String key4 = map.entrySet().stream().max((o1, o2) -> o2.getValue().compareTo(o1.getValue())).get().getKey();
        System.out.println(key1);
        System.out.println(key2);
        System.out.println(key3);
        System.out.println(key4);


//        String  pattern = "^[0-9]+$";
//        String  pattern = "^[A-Za-z][0-9]$";
        String  pattern = "^[\u4E00-\u9FA5]$";
//        String  pattern = "^[A-Za-z]{1,2}$";

        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher("含");
        if(matcher.find()){
            System.out.println("Found value"+matcher.group(0));
//            System.out.println("Found value"+matcher.group(1));
        }
    }


}