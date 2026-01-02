package com.comfycoding.urlshortener.model;

public class PlanType {
    private String name;
    private int maxUrlCount;

    public PlanType() {
    }

    public PlanType(String name, int maxUrlCount) {
        this.name = name;
        this.maxUrlCount = maxUrlCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxUrlCount() {
        return maxUrlCount;
    }

    public void setMaxUrlCount(int maxUrlCount) {
        this.maxUrlCount = maxUrlCount;
    }
}
