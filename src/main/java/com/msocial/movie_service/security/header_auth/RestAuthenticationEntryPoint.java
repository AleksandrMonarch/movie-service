package com.msocial.movie_service.security.header_auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msocial.movie_service.model.dto.DataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String CONTENT_TYPE = "application/json";

    private static final String ERROR_MESSAGE = "User could not authenticate";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.trace(ERROR_MESSAGE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(CONTENT_TYPE);
        objectMapper.writeValue(response.getWriter(),
                new DataResponse<>(false, HttpStatus.UNAUTHORIZED, authException.getMessage()));
    }
}
