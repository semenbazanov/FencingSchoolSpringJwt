package com.semenbazanov.controllers;

import com.semenbazanov.dto.ResponseResult;
import com.semenbazanov.model.User;
import com.semenbazanov.security.jwt.JwtTokenProvider;
import com.semenbazanov.security.jwt.JwtUser;
import com.semenbazanov.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                                    UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResponseResult<String>> login(@RequestParam String username,
                                                        @RequestParam String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            long id = ((JwtUser) authentication.getPrincipal()).getId();
            User user = this.userService.get(id);

            String token = this.jwtTokenProvider.createToken(username, user.getRole());
            return new ResponseEntity<>(new ResponseResult<>(null, token), HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
