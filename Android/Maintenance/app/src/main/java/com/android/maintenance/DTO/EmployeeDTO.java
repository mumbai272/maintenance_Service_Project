package com.android.maintenance.DTO;

import java.io.Serializable;

import javax.crypto.SealedObject;

/**
 * Created by anand on 04-Nov-16.
 */
public class EmployeeDTO implements Serializable {
    private String name;
    private String phoneno;
    private String emailId;
    private Long companyId;
    private Long roleTypeId;
    private UserEmploymentDTO employment;

    public EmployeeDTO() {
        super();
    }

    public EmployeeDTO(String name, String phoneno, String emailId, Long companyId, Long roleTypeId, UserEmploymentDTO employment) {
        this.name = name;
        this.phoneno = phoneno;
        this.emailId = emailId;
        this.companyId = companyId;
        this.roleTypeId = roleTypeId;
        this.employment = employment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getRoleTypeId() {
        return roleTypeId;
    }

    public void setRoleTypeId(Long roleTypeId) {
        this.roleTypeId = roleTypeId;
    }

    public UserEmploymentDTO getEmployment() {
        return employment;
    }

    public void setEmployment(UserEmploymentDTO employment) {
        this.employment = employment;
    }
}
