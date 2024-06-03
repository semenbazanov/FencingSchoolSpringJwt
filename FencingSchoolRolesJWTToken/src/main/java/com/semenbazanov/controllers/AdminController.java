package com.semenbazanov.controllers;

import com.semenbazanov.dto.ResponseResult;
import com.semenbazanov.model.Admin;
import com.semenbazanov.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<ResponseResult<Admin>> add(@RequestBody Admin admin) {
        try {
            this.adminService.add(admin);
            return new ResponseEntity<>(new ResponseResult<>(null, admin), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseResult<Admin>> get(@RequestParam String login, @RequestParam String password) {
        try {
            Admin admin = this.adminService.get(login, password);
            return new ResponseEntity<>(new ResponseResult<>(null, admin), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseResult<Admin>> get(@PathVariable long id) {
        try {
            Admin admin = this.adminService.get(id);
            return new ResponseEntity<>(new ResponseResult<>(null, admin), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseResult<Admin>> delete(@PathVariable long id) {
        try {
            Admin admin = this.adminService.delete(id);
            return new ResponseEntity<>(new ResponseResult<>(null, admin), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseResult<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
