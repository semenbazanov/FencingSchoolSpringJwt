package com.semenbazanov.service;

import com.semenbazanov.model.User;
import com.semenbazanov.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> get() {
        return this.userRepository.findAll();
    }

    @Override
    public User get(long id) {
        return this.userRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("User doesn't exist"));
    }
}
