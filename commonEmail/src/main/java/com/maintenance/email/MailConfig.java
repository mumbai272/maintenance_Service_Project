//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.email;

import java.util.ResourceBundle;


public class MailConfig {
    private static final ResourceBundle rb = ResourceBundle.getBundle("mail");
    
   public static String getMailFrom(){
      return rb.getString("mail.username");
   }
   
}
