package com.espid.estimate.config;

import com.espid.estimate.log.ControllerLogInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer  {

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        /* registry.addInterceptor(new ControllerLogInterceptor()); */
    }
}