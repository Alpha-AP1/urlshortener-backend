package com.comfycoding.urlshortener.repository;

import com.comfycoding.urlshortener.model.PlanType;

public interface PlanTypeRepository {
    PlanType getPlanTypeByName(String name);
    void addPlanType(PlanType planType);
}
