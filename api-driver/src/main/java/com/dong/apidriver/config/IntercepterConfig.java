package com.dong.apidriver.config;

import com.dong.apidriver.intercepter.JwtIntercepter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class IntercepterConfig implements WebMvcConfigurer {

    @Bean
    public JwtIntercepter jwtIntercepter(){
        return new JwtIntercepter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtIntercepter())
                // 拦截路径
                .addPathPatterns("/**")
                // 不拦截路径
                .excludePathPatterns("/no-noauthTest")
                .excludePathPatterns("/verification-code")
                .excludePathPatterns("/check-code");
    }
}
