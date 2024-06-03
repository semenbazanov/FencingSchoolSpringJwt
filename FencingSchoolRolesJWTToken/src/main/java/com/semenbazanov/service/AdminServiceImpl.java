package com.semenbazanov.service;

import com.semenbazanov.model.Admin;
import com.semenbazanov.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setAdminRepository(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void add(Admin admin) {
        try {
            admin.setPassword(this.passwordEncoder.encode(admin.getPassword()));
            this.adminRepository.save(admin);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Admin already added");
        }
    }

    @Override
    public Admin get(String login, String password) {
        return this.adminRepository.findAdminByLoginAndPassword(login, password).
                orElseThrow(() -> new IllegalArgumentException("Admin doesn't exist"));
    }

    @Override
    public Admin get(long id) {
        return this.adminRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Admin doesn't exist"));
    }

    @Override
    public Admin delete(long id) {
        Admin admin = this.get(id);
        this.adminRepository.deleteById(id);
        return admin;
    }
}
