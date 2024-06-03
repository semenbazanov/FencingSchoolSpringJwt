package com.semenbazanov.service;

import com.semenbazanov.model.User;

import java.util.List;

public interface UserService {

    List<User> get();

    User get(long id);
}
