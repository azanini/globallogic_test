package com.azanini.thenotelist.enums;

public enum Environment {
    DEV(" http://private-f0eea-mobilegllatam.apiary-mock.com"),
    PROD("https://anotherUrl.com");

    Environment(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }
}