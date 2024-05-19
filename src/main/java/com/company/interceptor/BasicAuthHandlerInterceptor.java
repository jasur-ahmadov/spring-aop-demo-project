package com.company.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class BasicAuthHandlerInterceptor implements HandlerInterceptor {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    // --header 'Authorization: Basic YWRtaW46YWRtaW4='

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("BasicAuthHandlerInterceptor::preHandle");
        // will be executed before going into the appropriate method of the controller
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Basic ")) {
            String base64Creds = authHeader.substring("Basic ".length());
            byte[] decodedCreds = Base64.getDecoder().decode(base64Creds);
            String creds = new String(decodedCreds, StandardCharsets.UTF_8); // yields like username:password pattern
            String[] parts = creds.split(":");
            if (USERNAME.equals(parts[0]) && PASSWORD.equals(parts[1])) {
                return true;
            }
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("BasicAuthHandlerInterceptor::postHandle");
        // appropriate method in controller will be executed but won't be sent to the client
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("BasicAuthHandlerInterceptor::afterCompletion");
        // response will be sent to the client and this method will work in the client side
        if (ex != null) {
            log.error("Error occurred at {}", ex.getMessage());
        }
    }
}