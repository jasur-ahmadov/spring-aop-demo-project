package com.company.config;

import com.company.interceptor.BasicAuthHandlerInterceptor;
import com.company.interceptor.LogHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new BasicAuthHandlerInterceptor())
                .order(1) // order by priority level which should execute first
                .addPathPatterns("/api/users"); // in which urls should this interceptor be used

        registry.addInterceptor(new LogHandlerInterceptor())
                .order(2)
                .addPathPatterns("/api/users/{id}");
    }
}