package com.android.maintenance.DTO;

/**
 * Created by anand on 11-Oct-16.
 */
public class ApproveUserDTO {

    private Long userId;
    private Long roleTypeId;
    private Long clientId;

    public ApproveUserDTO(Long userId, Long roleTypeId, Long clientId) {
        this.userId = userId;
        this.roleTypeId = roleTypeId;
        this.clientId = clientId;
    }

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
