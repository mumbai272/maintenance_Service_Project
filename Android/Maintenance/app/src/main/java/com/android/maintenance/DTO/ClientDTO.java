package com.android.maintenance.DTO;

import java.io.Serializable;

/**
 * Created by anand on 15-Sep-16.
 */
public class ClientDTO implements Serializable {

    private Long companyId;
    private Long clientId;
    private String clientName;
    private String description;
    private AddressDTO address;

    public ClientDTO(){

    }

    public ClientDTO(Long companyId,Long clientId, String clientName, String description, AddressDTO address) {
        this.companyId = companyId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.description = description;
        this.address = address;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
}
