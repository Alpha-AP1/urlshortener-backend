package com.comfycoding.urlshortener.repository;

import com.comfycoding.urlshortener.model.UserBean;

public interface UserRepository {
    UserBean saveUser(UserBean userBean);
    UserBean findUserById(String userId);
    UserBean findUserByEmail(String email);
    void deleteUserById(String userId);
    UserBean increaseUrlCount(String userId, int increaseBy);
}


