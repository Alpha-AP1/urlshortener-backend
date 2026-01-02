package com.comfycoding.urlshortener.model;

import java.util.Date;

public class UrlMapBean {
    private String shortKey;
    private String actualUrl;
    private String userId;
    private Date validTo;
    private int clickCount;

    public UrlMapBean() {
    }

    public UrlMapBean(String shortKey, String actualUrl, String userId, Date validTo, int clickCount) {
        this.shortKey = shortKey;
        this.actualUrl = actualUrl;
        this.userId = userId;
        this.validTo = validTo;
        this.clickCount = clickCount;
    }

    public String getShortKey() {
        return shortKey;
    }

    public void setShortKey(String shortKey) {
        this.shortKey = shortKey;
    }

    public String getOriginalUrl() {
        return actualUrl;
    }

    public void setActualUrl(String actualUrl) {
        this.actualUrl = actualUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }
}
