//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.email;


public enum EmailType {
    ADD_USER_HTML_EMAIL("Add user", "html", "/velocityTemplate/addUserHtml.vm");

    private String name;

    private String type;

    private String velocityTempletFile;

    private EmailType(String name, String type, String velocityTempletFile) {
        this.name = name;
        this.type = type;
        this.velocityTempletFile = velocityTempletFile;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getVelocityTempletFile() {
        return velocityTempletFile;
    }


}
