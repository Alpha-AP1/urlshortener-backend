package com.comfycoding.urlshortener.services;

import com.comfycoding.urlshortener.model.UserBean;

public interface UserService {
    UserBean addUser(UserBean userBean);
    UserBean findUserById(String userId);
    UserBean findUserByEmail(String email);
    UserBean signInUser(String email);
}
