//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.Common;
/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com>
 * Created on Oct 1, 2016
 */

public class ConfigUtil {
    
    public static final String ADMIN_COMPANY_ID = "ADMIN_COMPANY_ID";



    public static Long getAdminCompanyId(){
        String value=getContainerConfig(ADMIN_COMPANY_ID);
         return Long.parseLong(value);
    }
    
    
    
    protected static String getContainerConfig(String key) {
        String value = System.getProperty(key);

        if(value == null) {
            throw new RuntimeException("Config issue, missing environment variable:"+ key);
        }

        return value;
    }
}
