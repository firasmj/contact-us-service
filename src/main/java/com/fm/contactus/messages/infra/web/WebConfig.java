package com.fm.contactus.messages.infra.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final TokenAuthInterceptor tokenAuthInterceptor;
    private final String allowedOrigins;

    public WebConfig(TokenAuthInterceptor tokenAuthInterceptor, @Value("${contactus.cors.allowed-origins}") String allowedOrigins) {
        this.tokenAuthInterceptor = tokenAuthInterceptor;
        this.allowedOrigins = allowedOrigins;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenAuthInterceptor)
                .addPathPatterns("/api/messages/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/messages/**")
                .allowedOrigins(allowedOrigins.split(","))
                .allowedMethods("GET", "POST", "OPTIONS")
                .allowedHeaders("X-Project-Token", "Content-Type");
    }
    
}
