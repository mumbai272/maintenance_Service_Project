//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.email;


public enum EmailType {
    ADD_USER_HTML_EMAIL("Add user", "html", "Invitation to login into Maintenance application",
            "/velocityTemplate/addUserHtml.vm");

    private String name;

    private String type;
    private String subject;

    private String velocityTempletFile;

    private EmailType(String name, String type,String subject, String velocityTempletFile) {
        this.name = name;
        this.type = type;
        this.subject=subject;
        this.velocityTempletFile = velocityTempletFile;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
    
    public String getSubject() {
        return subject;
    }

    public String getVelocityTempletFile() {
        return velocityTempletFile;
    }


}
