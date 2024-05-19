package com.company.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        log.info("LogHandlerInterceptor::preHandle");
        UUID uuid = UUID.randomUUID();
        request.setAttribute("start", System.currentTimeMillis());
        request.setAttribute("request-id", uuid);
        log.warn("{} - calling {}", uuid, request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info("LogHandlerInterceptor::postHandle");
        log.warn("{} - response in {}ms",
                request.getAttribute("request-id"),
                System.currentTimeMillis() - (long) request.getAttribute("start"));
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        log.info("LogHandlerInterceptor::afterCompletion");
        log.warn("{} - completed in {}ms",
                request.getAttribute("request-id"),
                System.currentTimeMillis() - (long) request.getAttribute("start"));
    }
}