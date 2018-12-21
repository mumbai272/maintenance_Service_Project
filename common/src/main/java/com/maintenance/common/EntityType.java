//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.common;


/**
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Sep 4, 2016
 */
public enum EntityType {
    COMPANY(1L, "COMPANY"), USER(2L, "USER");

    private Long id;

    private String value;

    private EntityType(Long id, String value) {
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
