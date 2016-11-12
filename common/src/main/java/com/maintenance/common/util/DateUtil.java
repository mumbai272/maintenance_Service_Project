//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.common.util;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;


public class DateUtil {
    private static final String dataTimeFormate="dd/MM/yyyy hh:mm:ss";

    public static String formate(Date d, String formate) {
        if(d==null){
            return "";
        }
        formate = StringUtils.isNotBlank(formate) ? formate : dataTimeFormate;
        DateFormat formatter = new SimpleDateFormat(formate);
        return formatter.format(d);
    }
    
    public static Calendar parse(String d, String formate) {
        formate = StringUtils.isNotBlank(formate) ? formate : dataTimeFormate;
        DateFormat formatter = new SimpleDateFormat(formate);
        Date date = null;
        try {
            date = formatter.parse(d);
        } catch (Exception e) {
          throw new RuntimeException("invalid Date formate. Pass as valid formate id"+formate);
        }
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
    
    
    public static Calendar today() {      
        return Calendar.getInstance();
    }

    public static Time parseTime(String time) {   
        if(!time.matches("[0-2][0-3]:[0-5][0-9]:[0-5][0-9]")){
            throw new RuntimeException("invalid formate for time. pass hh:mm:ss");
        }
        Time t = Time.valueOf(time);
        return t;
    }
            
           

}
