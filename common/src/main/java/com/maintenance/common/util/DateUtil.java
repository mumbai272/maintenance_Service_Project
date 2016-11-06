//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;


public class DateUtil {
    private static final String dataTimeFormate="dd/MM/yyyy hh:mm:ss";

    public static String formate(Date d, String formate) {
        formate = StringUtils.isNotBlank(formate) ? formate : dataTimeFormate;
        DateFormat formatter = new SimpleDateFormat(formate);
        return formatter.format(d);
    }
    
    public static Calendar today() {      
        return Calendar.getInstance();
    }
            
           

}
