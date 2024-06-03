package com.semenbazanov.security;

import com.semenbazanov.model.User;
import com.semenbazanov.repository.UserRepository;
import com.semenbazanov.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findByLogin(s);

        user.orElseThrow(() -> new UsernameNotFoundException("User is not found"));

        return user.map(JwtUser::new).get();
    }
}
