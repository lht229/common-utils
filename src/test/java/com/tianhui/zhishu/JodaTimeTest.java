package com.tianhui.zhishu;

import org.joda.time.Days;
import org.joda.time.LocalDate;


public class JodaTimeTest {

    public static void main(String[] args) {
      //joda-time
        LocalDate start=new LocalDate(2012, 12,14);
        LocalDate end=new LocalDate(2012, 11, 25);
        int days = Days.daysBetween(start, end).getDays();
        System.err.println(days);
    }
}
