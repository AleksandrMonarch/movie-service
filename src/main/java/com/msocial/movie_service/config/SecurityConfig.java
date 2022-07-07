package com.msocial.movie_service.config;

import com.msocial.movie_service.security.header_auth.HeaderAuthenticationFilter;
import com.msocial.movie_service.security.header_auth.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static com.msocial.movie_service.constant.Endpoints.*;
import static org.springframework.http.HttpMethod.*;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public AuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint())
                .and()
                .addFilterBefore(
                        new HeaderAuthenticationFilter(authenticationManager(), restAuthenticationEntryPoint()),
                        BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(GET, API_USER, API_MOVIES, API_RECOMMEND).authenticated()
                .antMatchers(PUT, API_USER_UPDATE, API_FAVORITES_ID).authenticated()
                .antMatchers(DELETE, API_USER_DELETE, API_FAVORITES_ID).authenticated()
                .antMatchers(POST, API_USER_REGISTRATION).permitAll();
    }
}
