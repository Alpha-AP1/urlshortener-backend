package com.comfycoding.urlshortener.repositoryimpl.mongodb;

import com.comfycoding.urlshortener.dao.mongodb.PlanTypeDao;
import com.comfycoding.urlshortener.model.PlanType;
import com.comfycoding.urlshortener.mapper.mongodb.MongoMapper;
import com.comfycoding.urlshortener.repository.PlanTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MongoPlanTypeRepositoryImpl implements PlanTypeRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public PlanType getPlanTypeByName(String name) {
        PlanTypeDao planTypeDao = mongoTemplate.findById(name, PlanTypeDao.class);
        if(Objects.isNull(planTypeDao)) {
            log.debug("No plan type found against: {}", name);
        }
        return MongoMapper.mapPlanTypeDaoToPlanType(planTypeDao);
    }

    @Override
    public void addPlanType(PlanType planType) {
        PlanTypeDao planTypeDao = MongoMapper.mapPlanTypeToPlanTypeDao(planType);
        mongoTemplate.save(planTypeDao);
    }
}
