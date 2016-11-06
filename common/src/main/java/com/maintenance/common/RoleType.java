//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.common;


public enum RoleType {
    ADMIN(1L, "admin"), CLIENT_ADMIN(2L, "client_admin"), SERVICE_ENGINEER(3L, "service_engineer"),CLIENT_USER(4L, "client_user");

    private long id;

    private String role;

    private RoleType(long id, String role) {
        this.id = id;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
   public static RoleType getRoleType(long id){
        for (RoleType roleType : RoleType.values()) {
            if(roleType.getId()==id){
                return roleType;
            }
        }
        return null;
    }

}
