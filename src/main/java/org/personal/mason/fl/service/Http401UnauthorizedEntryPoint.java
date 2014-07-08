package org.personal.mason.fl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mason on 7/1/14.
 */

@Component
public class Http401UnauthorizedEntryPoint implements AuthenticationEntryPoint {
    private final Logger logger = LoggerFactory.getLogger(Http401UnauthorizedEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.debug("Pre-authenticated entry point called. Rejecting access");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
    }
}
