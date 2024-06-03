package com.semenbazanov.controllers;

import com.semenbazanov.dto.ResponseResult;
import com.semenbazanov.model.User;
import com.semenbazanov.security.jwt.JwtUser;
import com.semenbazanov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseResult<List<User>>> get() {
        List<User> users = this.userService.get();
        return new ResponseEntity<>(new ResponseResult<>(null, users), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ResponseResult<User>> get(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            long id = ((JwtUser) authentication.getPrincipal()).getId();
            User user = this.userService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseResult<>("Error. User doesn't exist", null), HttpStatus.BAD_REQUEST);
        }
    }
}
