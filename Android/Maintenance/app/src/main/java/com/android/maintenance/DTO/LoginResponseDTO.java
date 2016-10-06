package com.android.maintenance.DTO;

/**
 * Created by anand on 20-Sep-16.
 */
public class LoginResponseDTO {
    private UserDTO user;
    private CompanyDTO company;
    private String token;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(UserDTO user, CompanyDTO company, String token) {
        this.user = user;
        this.company = company;
        this.token = token;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
