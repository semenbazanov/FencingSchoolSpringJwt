package com.semenbazanov.service;

import com.semenbazanov.model.Admin;

public interface AdminService {
    void add(Admin admin);

    Admin get(String login, String password);

    Admin get(long id);

    Admin delete(long id);
}
