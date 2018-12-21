//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.common;


/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Sep 5, 2016
 */
public enum MachineTypeEnum {
    MACHINETYPE(1, "MACHINETYPE"),
    MACHINEMODEL(2, "MACHINEMODEL"), 
    MACHINEMAKE(3, "MACHINEMAKE"), 
    MACHINESPARE(4,"MACHINESPARE");

    private Integer id;

    private String value;

   private MachineTypeEnum(Integer id, String value) {
        this.id = id;
        this.value = value;        
    }

    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
    
 }
