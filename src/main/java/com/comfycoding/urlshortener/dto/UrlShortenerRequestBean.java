package com.comfycoding.urlshortener.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.util.Date;

public class UrlShortenerRequestBean {
    @NotBlank(message = "User ID is required")
    private String userId;
    
    @NotBlank(message = "Actual URL is required")
    private String actualUrl;

    @Future(message = "validTo must be a future date")
    private Date validTo;

    public UrlShortenerRequestBean() {
    }

    public UrlShortenerRequestBean(String userId, String actualUrl, Date validTo) {
        this.userId = userId;
        this.actualUrl = actualUrl;
        this.validTo = validTo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActualUrl() {
        return actualUrl;
    }

    public void setActualUrl(String actualUrl) {
        this.actualUrl = actualUrl;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }
}
