//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.Common;

import org.apache.log4j.Logger;



public class UserContextRetriver {

    private static final Logger logger = Logger.getLogger(UserContextRetriver.class);

    private static final ThreadLocal<UserContext> usercontext = new ThreadLocal<UserContext>();


    public static UserContext getUsercontext() {
        return usercontext.get();
    }

    public static void setUsercontext(UserContext userContext) {
        logger.info("setting userContext for userId: " + userContext.getUserId());
        usercontext.set(userContext);
    }

    public static void remove() {
        logger.info("removing userContext");
        usercontext.remove();
    }

}
