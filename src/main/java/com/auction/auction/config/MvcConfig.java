package com.auction.auction.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    private static final String API = "api";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(API + "/swagger-ui/")
                .setViewName("forward:" + API + "/swagger-ui/index.html");
    }
}
