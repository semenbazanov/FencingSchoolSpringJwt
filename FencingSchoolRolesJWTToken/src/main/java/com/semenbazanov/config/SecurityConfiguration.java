package com.semenbazanov.config;

import com.semenbazanov.security.jwt.JwtConfigurer;
import com.semenbazanov.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/auth").permitAll()
                .antMatchers("/user/all").hasAnyRole("ADMIN")
                .antMatchers("/user").hasAnyRole("ADMIN", "TRAINER", "APPRENTICE")
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/apprentice").hasAnyRole("ADMIN", "APPRENTICE", "TRAINER")
                .antMatchers(HttpMethod.POST, "/apprentice").hasAnyRole("ADMIN", "APPRENTICE", "TRAINER")
                .antMatchers("/apprentice/**").hasAnyRole("ADMIN", "APPRENTICE")
                .antMatchers(HttpMethod.GET, "/trainer").hasAnyRole("ADMIN", "TRAINER", "APPRENTICE")
                .antMatchers("/trainer/**").hasAnyRole("ADMIN", "TRAINER")
                .antMatchers("/trainer_schedule/**").hasAnyRole("ADMIN", "TRAINER")
                .antMatchers(HttpMethod.GET, "/training/**").hasAnyRole("ADMIN", "APPRENTICE", "TRAINER")
                .antMatchers(HttpMethod.POST, "/training/**").hasAnyRole("ADMIN", "APPRENTICE", "TRAINER")
                .antMatchers("/training/**").hasAnyRole("ADMIN", "APPRENTICE")
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
        //.formLogin();
    }
}
