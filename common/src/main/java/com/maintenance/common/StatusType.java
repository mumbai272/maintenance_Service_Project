//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.common;

/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com>
 * Created on Sep 4, 2016
 */
public enum StatusType {
    NEW(1L, "N"), ACTIVE(2L, "A"), REGISTERED(3L, "R"),DELETED(4L, "D"),REJECTED(5L,"X"), SUBMITTED(6L,"S"),APPROVED(7L,"Aprd"), FINANCE_APPROVED(8L,"fAprd"), READY(9L,"Ready"),DONE(10L,"done");

    private Long id;

    private String value;

    private StatusType(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public static StatusType getStatusOfValue(String value) {
        for (StatusType statusType : StatusType.values()) {
            if (statusType.getValue().equalsIgnoreCase(value)) {
                return statusType;
            }
        }
        return null;
    }

}
