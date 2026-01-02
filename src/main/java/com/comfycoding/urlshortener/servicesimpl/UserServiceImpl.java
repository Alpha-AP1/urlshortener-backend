package com.comfycoding.urlshortener.servicesimpl;

import com.comfycoding.urlshortener.model.UserBean;
import com.comfycoding.urlshortener.repository.UserRepository;
import com.comfycoding.urlshortener.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserBean addUser(UserBean requestBean) {
        //Check if user already exits, if yes raise exception
        UserBean userBean = userRepository.findUserByEmail(requestBean.getEmail());
        if(Objects.nonNull(userBean) && Objects.nonNull(userBean.getUserId())) {
            log.debug("User id already exits for user email : {}", requestBean.getEmail());
            throw new IllegalStateException("User already exists, try signing in!");
        }
        log.info("Signing up new user, email id: {}", requestBean.getEmail());
        UserBean savedUser = userRepository.saveUser(requestBean);
        log.info("Successfully signed up user with email: {}", savedUser.getEmail());
        return savedUser;
    }

    @Override
    public UserBean findUserById(String userId) {
        log.debug("Finding user by userId: {}", userId);
        return userRepository.findUserById(userId);
    }

    @Override
    public UserBean findUserByEmail(String email) {
        log.debug("Finding user by email: {}", email);
        return userRepository.findUserByEmail(email);
    }

    @Override
    public UserBean signInUser(String email) {
        log.info("Signing in user with email: {}", email);
        UserBean userBean = userRepository.findUserByEmail(email);
        if (Objects.isNull(userBean)) {
            log.warn("User not found with email: {}", email);
            throw new IllegalStateException("User not found with email: " + email);
        }
        log.info("Successfully signed in user with email: {}", email);
        return userBean;
    }
}
