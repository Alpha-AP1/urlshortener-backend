package com.comfycoding.urlshortener.dto;

public class UserResponseBean {
    private String userId;
    private String email;
    private String planType;
    private int activeUrlCount;

    public UserResponseBean() {
    }

    public UserResponseBean(String userId, String email, String planType, int activeUrlCount) {
        this.userId = userId;
        this.email = email;
        this.planType = planType;
        this.activeUrlCount = activeUrlCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public int getActiveUrlCount() {
        return activeUrlCount;
    }

    public void setActiveUrlCount(int activeUrlCount) {
        this.activeUrlCount = activeUrlCount;
    }
}
