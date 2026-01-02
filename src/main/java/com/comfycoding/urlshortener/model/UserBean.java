package com.comfycoding.urlshortener.model;

public class UserBean {
    private String userId;
    private String email;
    private PlanType planType;
    private int activeUrlCount;

    public UserBean() {
    }

    public UserBean(String userId, String email, PlanType planType, int activeUrlCount) {
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

    public PlanType getPlanType() {
        return planType;
    }

    public void setPlanType(PlanType planType) {
        this.planType = planType;
    }

    public int getActiveUrlCount() {
        return activeUrlCount;
    }

    public void setActiveUrlCount(int activeUrlCount) {
        this.activeUrlCount = activeUrlCount;
    }

    public boolean isUrlCreationAllowed() {
        return planType.getMaxUrlCount() > activeUrlCount;
    }
}
