package com.android.maintenance.DTO;

/**
 * Created by anand on 09-Sep-16.
 */
public class ClientAssetDTO {

    private int companyId;
    private int clientId;
    private String clientName;
    private String description;
    private String address;

    public ClientAssetDTO() {

    }

    public ClientAssetDTO(int companyId, int clientId, String description, String address, String clientName) {
        this.companyId = companyId;
        this.clientId = clientId;
        this.description = description;
        this.address = address;
        this.clientName = clientName;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
