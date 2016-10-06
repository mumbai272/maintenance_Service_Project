package com.android.maintenance.DTO;

/**
 * Created by anand on 20-Sep-16.
 */
public class CompanyDTO {

    private Long companyId;
    private Long clientId;
    private String clientName;
    private String description;
    private AddressDTO address;

    public CompanyDTO(){
        super();
    }

    public CompanyDTO(Long companyId, Long clientId, String clientName, String description,
                      AddressDTO address) {
        super();
        this.companyId = companyId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.description = description;
        if (address != null) {
            this.address = address;
        }
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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
