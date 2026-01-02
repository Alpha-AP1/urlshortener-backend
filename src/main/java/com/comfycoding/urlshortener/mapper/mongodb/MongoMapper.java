package com.comfycoding.urlshortener.mapper.mongodb;

import com.comfycoding.urlshortener.dao.mongodb.KeyPoolDao;
import com.comfycoding.urlshortener.dao.mongodb.PlanTypeDao;
import com.comfycoding.urlshortener.dao.mongodb.UrlMapDao;
import com.comfycoding.urlshortener.dao.mongodb.UserDao;
import com.comfycoding.urlshortener.model.PlanType;
import com.comfycoding.urlshortener.model.ShortKeyBean;
import com.comfycoding.urlshortener.model.UrlMapBean;
import com.comfycoding.urlshortener.model.UserBean;

public class MongoMapper {

    public static UserDao mapUserBeanToUserDao(UserBean userBean) {
        UserDao userDao = new UserDao();
        userDao.setUserId(userBean.getUserId());
        userDao.setEmail(userBean.getEmail());
        userDao.setPlanType(userBean.getPlanType().getName());
        userDao.setActiveUrlCount(userBean.getActiveUrlCount());
        return userDao;
    }

    public static UserBean mapUserDaoToUserBean(UserDao userDao) {
        UserBean userBean = new UserBean();
        userBean.setUserId(userDao.getUserId());
        userBean.setEmail(userDao.getEmail());
        userBean.setActiveUrlCount(userDao.getActiveUrlCount());
        PlanType planType = new PlanType(userDao.getPlanType(), 0);
        userBean.setPlanType(planType);
        return userBean;
    }

    public static PlanType mapPlanTypeDaoToPlanType(PlanTypeDao planTypeDao) {
        PlanType planType = new PlanType();
        planType.setName(planTypeDao.getName());
        planType.setMaxUrlCount(planTypeDao.getMaxUrlCreationLimit());
        return planType;
    }

    public static PlanTypeDao mapPlanTypeToPlanTypeDao(PlanType planType) {
        PlanTypeDao planTypeDao= new PlanTypeDao();
        planTypeDao.setName(planType.getName());
        planTypeDao.setMaxUrlCreationLimit(planType.getMaxUrlCount());
        return planTypeDao;
    }

    public static UrlMapDao mapUrlMapBeanToUrlMapDao(UrlMapBean urlMapBean) {
        UrlMapDao urlMapDao = new UrlMapDao();
        urlMapDao.setShortKey(urlMapBean.getShortKey());
        urlMapDao.setActualUrl(urlMapBean.getOriginalUrl());
        urlMapDao.setUserId(urlMapBean.getUserId());
        urlMapDao.setValidTo(urlMapBean.getValidTo());
        urlMapDao.setClickCount(urlMapBean.getClickCount());
        return urlMapDao;
    }
    public static UrlMapBean mapUrlMapDaoToUrlMapBean(UrlMapDao urlMapDao) {
        UrlMapBean urlMapBean = new UrlMapBean();
        urlMapBean.setShortKey(urlMapDao.getShortKey());
        urlMapBean.setActualUrl(urlMapDao.getActualUrl());
        urlMapBean.setUserId(urlMapDao.getUserId());
        urlMapBean.setValidTo(urlMapDao.getValidTo());
        urlMapBean.setClickCount(urlMapDao.getClickCount());
        return urlMapBean;
    }

    public static KeyPoolDao mapShortKeyBeanToKeyPoolDao(ShortKeyBean shortKeyBean) {
        KeyPoolDao keyPoolDao = new KeyPoolDao();
        keyPoolDao.setShortKey(shortKeyBean.getShortKey());
        keyPoolDao.setUsed(shortKeyBean.isUsed());
        keyPoolDao.setCreatedOn(shortKeyBean.getCreatedOn());
        return keyPoolDao;
    }

    public static ShortKeyBean mapKeyPoolDaoToShortKeyBean(KeyPoolDao keyPoolDao) {
        Boolean isUsed = keyPoolDao.getUsed();
        boolean isUsedValue = (isUsed != null) ? isUsed : false;
        return new ShortKeyBean(
                keyPoolDao.getShortKey(),
                isUsedValue,
                keyPoolDao.getCreatedOn()
        );
    }
}
