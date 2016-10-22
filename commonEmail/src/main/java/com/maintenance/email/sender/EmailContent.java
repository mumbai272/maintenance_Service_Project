//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.email.sender;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.maintenance.email.EmailType;


public class EmailContent {

    private EmailType emailType;

    private String from;

    private Set<String> to;

    private String Subject;

    private Set<String> bcc;

    private Set<String> cc;

    private Map model;

    public EmailContent(EmailType emailType, String from, Set<String> to, String subject,
            Set<String> bcc, Set<String> cc, Map model) {
        super();
        this.emailType = emailType;
        this.from = from;
        this.to = to;
        Subject = subject;
        this.bcc = bcc;
        this.cc = cc;
        this.model = model;
    }

    public EmailContent() {
        super();
    }


    public EmailType getEmailType() {
        return emailType;
    }


    public void setEmailType(EmailType emailType) {
        this.emailType = emailType;
    }


    public String getFrom() {
        return from;
    }


    public void setFrom(String from) {
        this.from = from;
    }


    public Set<String> getTo() {
        return to;
    }


    public void setTo(Set<String> to) {
        this.to = to;
    }

    public void addTo(String to) {
        to = to.toLowerCase();
        if (this.to == null) {
            this.to = new HashSet<String>();
        }

        this.to.add(to);
    }

    public String getSubject() {
        return Subject;
    }


    public void setSubject(String subject) {
        Subject = subject;
    }


    public Set<String> getBcc() {
        return bcc;
    }


    public void setBcc(Set<String> bcc) {
        this.bcc = bcc;
    }

    public void addBCc(String bcc) {
        bcc = bcc.toLowerCase();
        if (this.bcc == null) {
            this.bcc = new HashSet<String>();
        }

        this.bcc.add(bcc);
    }

    public Set<String> getCc() {
        return cc;
    }


    public void setCc(Set<String> cc) {
        this.cc = cc;
    }

    public void addCc(String cc) {
        cc = cc.toLowerCase();
        if (this.cc == null) {
            this.cc = new HashSet<String>();
        }
        this.cc.add(cc);
    }


    public Map getModel() {
        return model;
    }


    public void setModel(Map model) {
        this.model = model;
    }

    public void addModel(String key, Serializable value) {
        if (this.model == null) {
            this.model = new HashMap();
        }
        this.model.put(key, value);
    }

    public String[] toArray(Set<String> values) {
        String[] valueArray = new String[values.size()];
        int i = 0;
        for (String value : values) {
            valueArray[i++] = value;
        }
        return valueArray;

    }

}
