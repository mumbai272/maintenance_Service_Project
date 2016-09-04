//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.common;

/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com>
 * Created on Sep 4, 2016
 */
public enum StatusType {
    NEW(1L, "NEW"), ACTIVE(2L, "ACTIVE"), DELETED(3L, "DELETED");

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

}
