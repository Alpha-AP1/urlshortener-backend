package com.comfycoding.urlshortener.dao.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "plan_type")
public class PlanTypeDao {
    @Id
    private String name;
    private int maxUrlCreationLimit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxUrlCreationLimit() {
        return maxUrlCreationLimit;
    }

    public void setMaxUrlCreationLimit(int maxUrlCreationLimit) {
        this.maxUrlCreationLimit = maxUrlCreationLimit;
    }
}
