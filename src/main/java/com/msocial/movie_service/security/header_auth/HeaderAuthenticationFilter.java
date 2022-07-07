package com.msocial.movie_service.security.header_auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class HeaderAuthenticationFilter extends GenericFilterBean {

    private final AuthenticationManager authenticationManager;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    public HeaderAuthenticationFilter(AuthenticationManager authenticationManager,
                                      AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        var request = (HttpServletRequest) servletRequest;
        var response = (HttpServletResponse) servletResponse;
        String userId = request.getHeader("User-Id");

        if (Objects.nonNull(userId)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (Objects.isNull(authentication) || !authentication.getName().equals(userId)) {
                log.info(String.format("Try to authenticate user with id: %s", userId));
                try {
                    authentication = new UsernamePasswordAuthenticationToken(userId, "");
                    authentication = authenticationManager.authenticate(authentication);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (AuthenticationException e) {
                    SecurityContextHolder.clearContext();
                    authenticationEntryPoint.commence(request, response, e);
                    return;
                }
                log.info(String.format("User with id: %s authenticated", userId));
            }
        }
        filterChain.doFilter(request, servletResponse);
    }
}
