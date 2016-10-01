//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.user.requestResponse;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserRegistrationApprovalRequest implements Serializable {


    @NotNull
    private Long userId;

    @NotNull
    private Long roleTypeId;

    @NotNull
    private Long clientId;

    
    public Long getUserId() {
        return userId;
    }

    
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    
    public Long getRoleTypeId() {
        return roleTypeId;
    }

    
    public void setRoleTypeId(Long roleTypeId) {
        this.roleTypeId = roleTypeId;
    }

    
    public Long getClientId() {
        return clientId;
    }

    
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }





}
