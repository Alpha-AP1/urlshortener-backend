package com.comfycoding.urlshortener.model;

import java.util.Date;

public class ShortKeyBean {
    private String shortKey;
    private boolean isUsed;
    private Date createdOn;

    public ShortKeyBean(String shortKey, boolean isUsed, Date createdOn) {
        this.shortKey = shortKey;
        this.isUsed = isUsed;
        this.createdOn = createdOn;
    }

    public String getShortKey() {
        return shortKey;
    }

    public void setShortKey(String shortKey) {
        this.shortKey = shortKey;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
