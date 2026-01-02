package com.comfycoding.urlshortener.repositoryimpl.mongodb;

import com.comfycoding.urlshortener.dao.mongodb.UrlMapDao;
import com.comfycoding.urlshortener.model.UrlMapBean;
import com.comfycoding.urlshortener.mapper.mongodb.MongoMapper;
import com.comfycoding.urlshortener.repository.UrlMapRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MongoDBUrlMapRepositoryImpl implements UrlMapRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public void saveUrlMap(UrlMapBean urlMapBean) {
        UrlMapDao urlMapDao = MongoMapper.mapUrlMapBeanToUrlMapDao(urlMapBean);
        mongoTemplate.save(urlMapDao);
    }

    @Override
    public UrlMapBean findUrlMapByShortKey(String shortKey) {
        UrlMapDao urlMapDao = mongoTemplate.findById(shortKey, UrlMapDao.class);
        if(Objects.isNull(urlMapDao)) {
            log.debug("No url mapping found against short key: {}", shortKey);
            return null;
        }
        return MongoMapper.mapUrlMapDaoToUrlMapBean(urlMapDao);
    }

    @Override
    public UrlMapBean increaseUrlClickCount(String shortKey, int increaseBy) {
        Query query = new Query(Criteria.where("_id").is(shortKey));

        Update update = new Update()
                .inc("clickCount", increaseBy);

        // findAndModify is atomic in MongoDB
        UrlMapDao updatedDao = mongoTemplate.findAndModify(
                query,
                update,
                UrlMapDao.class
        );

        if (updatedDao == null) {
            log.debug("No url mapping found to increase click count for shortKey={}", shortKey);
            return null;
        }

        return MongoMapper.mapUrlMapDaoToUrlMapBean(updatedDao);
    }

    @Override
    public void removeUrlMapByShortKey(String shortKey) {
        Query query = new Query(Criteria.where("_id").is(shortKey));
        long deletedCount = mongoTemplate.remove(query, UrlMapDao.class).getDeletedCount();

        if (deletedCount == 0) {
            log.debug("No url mapping is deleted with shortKey={}", shortKey);
        }
    }
}
