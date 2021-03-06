//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.email;


public enum EmailType {
    ADD_USER_HTML_EMAIL("Add user", "html", "Invitation to login into Maintenance application",
            "/velocityTemplate/addUserHtml.vm"), 
    FORGOT_PASSEORD_EMAIL("forgot password", "html",
            "Forgot password", "/velocityTemplate/forgotUserPasswordHtml.vm"), 
    REGISTRATION_REJECTION( "Registration rejected", "html", "Registration rejected",
            "/velocityTemplate/registrationRejectedHtml.vm"), 
    REGISTRATION_REQUEST("Registration request", "html", "Registration requested",
            "/velocityTemplate/registrationRequestHtml.vm"), 
    ASSET_LOG_CREATED("Asset log created", "html", "Asset log created",
            "/velocityTemplate/assetlogcreatedHtml.vm"),
    ASSET_LOG_ASSIGNED("Asset log Assigned", "html", "Asset log Assigned",
            "/velocityTemplate/assetlogAssignedHtml.vm");

    private String name;

    private String type;

    private String subject;

    private String velocityTempletFile;

    private EmailType(String name, String type, String subject, String velocityTempletFile) {
        this.name = name;
        this.type = type;
        this.subject = subject;
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
