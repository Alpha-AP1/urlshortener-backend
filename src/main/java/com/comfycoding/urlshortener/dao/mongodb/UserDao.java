package com.comfycoding.urlshortener.dao.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class UserDao {
    @Id
    private String userId;
    private String email;
    private String planType;
    private Integer activeUrlCount;

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

    public Integer getActiveUrlCount() {
        return activeUrlCount;
    }

    public void setActiveUrlCount(Integer activeUrlCount) {
        this.activeUrlCount = activeUrlCount;
    }

}
