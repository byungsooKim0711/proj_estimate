package com.espid.estimate.config;

import com.espid.estimate.log.ControllerLogInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer  {

    private final long MAX_AGE_SECONDS = 3600;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        /* registry.addInterceptor(new ControllerLogInterceptor()); */
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS")
            .allowedHeaders("*")
            .maxAge(MAX_AGE_SECONDS);
    }
}