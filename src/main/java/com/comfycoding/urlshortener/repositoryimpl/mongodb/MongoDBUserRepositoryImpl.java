package com.comfycoding.urlshortener.repositoryimpl.mongodb;

import com.comfycoding.urlshortener.dao.mongodb.UserDao;
import com.comfycoding.urlshortener.model.UserBean;
import com.comfycoding.urlshortener.mapper.mongodb.MongoMapper;
import com.comfycoding.urlshortener.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MongoDBUserRepositoryImpl implements UserRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public UserBean saveUser(UserBean userBean) {
        UserDao userDao = MongoMapper.mapUserBeanToUserDao(userBean);
        mongoTemplate.save(userDao);
        return MongoMapper.mapUserDaoToUserBean(userDao);
    }

    @Override
    public UserBean findUserById(String userId) {
        UserDao userDao = mongoTemplate.findById(userId, UserDao.class);
        if (Objects.isNull(userDao)) {
            log.debug("User not found with Id: {}", userId);
            return null;
        }
        return MongoMapper.mapUserDaoToUserBean(userDao);
    }

    @Override
    public UserBean findUserByEmail(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        UserDao userDao = mongoTemplate.findOne(query, UserDao.class);
        if (Objects.isNull(userDao)) {
            log.debug("User not found with email: {}", email);
            return null;
        }
        return MongoMapper.mapUserDaoToUserBean(userDao);
    }

    @Override
    public void deleteUserById(String userId) {
        Query query = new Query(Criteria.where("_id").is(userId));
        long deletedCount = mongoTemplate.remove(query, UserDao.class).getDeletedCount();

        if (deletedCount == 0) {
            log.debug("No user deleted with userId={}", userId);
        }
    }

    @Override
    public UserBean increaseUrlCount(String userId, int increaseBy) {
        Query query = new Query(Criteria.where("_id").is(userId));

        Update update = new Update()
                .inc("activeUrlCount", increaseBy);

        // findAndModify is atomic in MongoDB
        UserDao updatedDao = mongoTemplate.findAndModify(
                query,
                update,
                UserDao.class
        );

        if (updatedDao == null) {
            log.debug("No user found to increase url count for user id={}", userId);
            return null;
        }

        return MongoMapper.mapUserDaoToUserBean(updatedDao);
    }
}
