package com.comfycoding.urlshortener.repositoryimpl.mongodb;

import com.comfycoding.urlshortener.dao.mongodb.KeyPoolDao;
import com.comfycoding.urlshortener.model.ShortKeyBean;
import com.comfycoding.urlshortener.mapper.mongodb.MongoMapper;
import com.comfycoding.urlshortener.repository.KeyPoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MongoKeyPoolRepositoryImpl implements KeyPoolRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public void insertKey(ShortKeyBean shortKey) {
        KeyPoolDao keyPoolDao = MongoMapper.mapShortKeyBeanToKeyPoolDao(shortKey);
        try {
            mongoTemplate.insert(keyPoolDao);
        } catch(DuplicateKeyException e) {
            log.error("Duplicate short key found in key pool for short key: {}", shortKey);
            throw e;
        }
    }

    @Override
    public ShortKeyBean findKey(String shortKey) {
        KeyPoolDao keyPoolDao = mongoTemplate.findById(shortKey, KeyPoolDao.class);
        if(Objects.isNull(keyPoolDao)) {
            log.debug("No short key row found in key pool against short key: {}", shortKey);
            return null;
        }
        return MongoMapper.mapKeyPoolDaoToShortKeyBean(keyPoolDao);
    }

    @Override
    public ShortKeyBean getOneKey() {
        Query query = new Query(Criteria.where("isUsed").is(false));

        Update update = new Update()
                .set("isUsed", true);

        // Atomically: find unused key + mark it used
        KeyPoolDao keyPoolDao = mongoTemplate.findAndModify(
                query,
                update,
                KeyPoolDao.class
        );

        if (keyPoolDao == null) {
            log.warn("No unused short key available in key pool");
            return null;
        }

        return MongoMapper.mapKeyPoolDaoToShortKeyBean(keyPoolDao);
    }

    @Override
    public List<ShortKeyBean> getKeys(int keyCount) {
        List<ShortKeyBean> result = new ArrayList<>(keyCount);

        for (int i = 0; i < keyCount; i++) {
            ShortKeyBean key = getOneKey(); // atomic call
            if (key == null) {
                break;
            }
            result.add(key);
        }

        return result;
    }

    @Override
    public void markShortKeyAsUsed(String shortKey) {
        Query query = new Query(
                Criteria.where("_id").is(shortKey)
                        .and("isUsed").is(false)
        );

        Update update = new Update().set("isUsed", true);

        long modifiedCount = mongoTemplate
                .updateFirst(query, update, KeyPoolDao.class)
                .getModifiedCount();

        if (modifiedCount == 0) {
            log.debug("Short key already used or not found: {}", shortKey);
        }
    }
}
