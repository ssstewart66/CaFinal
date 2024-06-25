//package com.example.c23team2.Config;
//
//import com.example.c23team2.Interceptor.AuthenticationInterceptor;
//import com.example.c23team2.Interceptor.SecurityInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Component
//public class WebAppConfig implements WebMvcConfigurer {
//    @Autowired
//    AuthenticationInterceptor authenticationInterceptor;
//    @Autowired
//    SecurityInterceptor securityInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authenticationInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/login", "/logout", "/resources/**", "/static/**", "/error");
//
//        registry.addInterceptor(securityInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/login", "/logout", "/resources/**", "/static/**", "/error");
//    }
//
//}