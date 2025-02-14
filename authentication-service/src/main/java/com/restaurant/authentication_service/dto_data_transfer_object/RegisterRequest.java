package com.restaurant.authentication_service.dto_data_transfer_object;

public class RegisterRequest {
    private String username;
    private String password;

    public RegisterRequest(){}

    public RegisterRequest(String username, String password) {
        this.username =username;
        this.password =password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
