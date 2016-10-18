//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.Common;


public enum UserAction {
    USER_APPROVAL(1L, "User Approval"),
    GET_REGISTED_USER(2L,"Getting Registered User"),
    ADD_USER(3L,"Adding User");
    

    private Long id;

    private String value;

    private UserAction(Long id, String value) {
        this.id = id;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public static UserAction getStatusOfValue(String value) {
        for (UserAction userAction : UserAction.values()) {
            if (userAction.getValue().equalsIgnoreCase(value)) {
                return userAction;
            }
        }
        return null;
    }
}
