package com.comfycoding.urlshortener.mapper.common;

import com.comfycoding.urlshortener.dto.UserRequestBean;
import com.comfycoding.urlshortener.dto.UserResponseBean;
import com.comfycoding.urlshortener.model.PlanType;
import com.comfycoding.urlshortener.model.UserBean;

import java.util.Objects;

public class UserMapper {
    
    public static UserBean mapUserRequestBeanToUserBean(UserRequestBean requestBean) {
        UserBean userBean = new UserBean();
        userBean.setEmail(requestBean.getEmail());
        // userId will be generated (using email as userId for simplicity)
        userBean.setUserId(requestBean.getEmail());
        // Default plan type for new users
        userBean.setPlanType(new PlanType("FREE", 10));
        userBean.setActiveUrlCount(0);
        return userBean;
    }

    public static UserResponseBean mapUserBeanToUserResponseBean(UserBean userBean) {
        UserResponseBean responseBean = new UserResponseBean();
        responseBean.setUserId(userBean.getUserId());
        responseBean.setEmail(userBean.getEmail());
        if(Objects.nonNull(userBean.getPlanType())) {
            responseBean.setPlanType(userBean.getPlanType().getName());
        }
        responseBean.setActiveUrlCount(userBean.getActiveUrlCount());
        return responseBean;
    }
}
