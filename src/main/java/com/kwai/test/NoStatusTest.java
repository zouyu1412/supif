package com.kwai.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zouyu <zouyu@kuaishou.com>
 * Created on 2020-06-26
 */
public class NoStatusTest {

    private static final DateFormat format = new SimpleDateFormat("yyyy.MM.dd");

    public String getCurrentDateText() {
        return format.format(new Date());
    }

    public String getCurrentDateTextNoStatus() {
        return new SimpleDateFormat("yyyy.MM.dd").format(new Date());
    }

}
