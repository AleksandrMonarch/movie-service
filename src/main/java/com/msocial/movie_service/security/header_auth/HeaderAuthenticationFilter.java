package com.msocial.movie_service.security.header_auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class HeaderAuthenticationFilter implements Filter {

    private final AuthenticationManager authenticationManager;

    public HeaderAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        var request = (HttpServletRequest) servletRequest;
        String userId = request.getHeader("User-Id");

        if (Objects.nonNull(userId)) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (Objects.isNull(authentication) || !authentication.getName().equals(userId)) {
                log.info(String.format("Try to authenticate user with id: %s", userId));
                authentication = new UsernamePasswordAuthenticationToken(userId, "");
                authentication = authenticationManager.authenticate(authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info(String.format("User with id: %s authenticated", userId));
            }
        }
        filterChain.doFilter(request, servletResponse);
    }
}
