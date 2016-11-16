package com.android.maintenance.DTO;

import java.io.Serializable;

/**
 * Created by anand on 05-Nov-16.
 */
public class RejectUserDTO implements Serializable {

    private Long userId;
    private String rejectionReason;

    public RejectUserDTO() {
        super();
    }

    public RejectUserDTO(Long userId, String rejectionReason) {
        this.userId = userId;
        this.rejectionReason = rejectionReason;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
