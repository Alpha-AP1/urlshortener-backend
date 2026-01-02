package com.comfycoding.urlshortener.controller;

import com.comfycoding.urlshortener.dto.UserRequestBean;
import com.comfycoding.urlshortener.dto.UserResponseBean;
import com.comfycoding.urlshortener.mapper.common.UserMapper;
import com.comfycoding.urlshortener.model.UserBean;
import com.comfycoding.urlshortener.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<UserResponseBean> signInUser(@Valid @RequestBody UserRequestBean requestBean) {
        UserBean userBean = userService.signInUser(requestBean.getEmail());
        UserResponseBean responseBean = UserMapper.mapUserBeanToUserResponseBean(userBean);
        return ResponseEntity.ok(responseBean);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseBean> signUpUser(@Valid @RequestBody UserRequestBean requestBean) {
        UserBean newUser = UserMapper.mapUserRequestBeanToUserBean(requestBean);
        UserBean savedUser = userService.addUser(newUser);
        UserResponseBean responseBean = UserMapper.mapUserBeanToUserResponseBean(savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBean);
    }
}
