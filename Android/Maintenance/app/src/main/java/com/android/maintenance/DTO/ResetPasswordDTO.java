package com.android.maintenance.DTO;

/**
 * Created by anand on 26-Oct-16.
 */
public class ResetPasswordDTO {

   private String emailId;
   private String phoneno;

    public ResetPasswordDTO() {
    }

    public ResetPasswordDTO(String emailId, String phoneno) {
        this.emailId = emailId;
        this.phoneno = phoneno;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
}
