package com.systems.spectro_assignment.responses;

public class TokenResponse {
    private String token;
    private String tokenType = "Bearer";

    public TokenResponse() {}

    public TokenResponse(String token) {
        this.token = token;
    }

    public TokenResponse(String token, String tokenType) {
        this.token = token;
        this.tokenType = tokenType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
