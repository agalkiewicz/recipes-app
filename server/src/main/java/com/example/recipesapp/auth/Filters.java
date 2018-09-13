//package com.example.recipesapp.auth;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//
//import java.util.Collections;
//
//@Configuration
//public class Filters {
//
//    private final AuthorizationFilter authorizationFilter;
//
//    @Autowired
//    public Filters(AuthorizationFilter authorizationFilter) {
//        this.authorizationFilter = authorizationFilter;
//    }
//
//    @Bean
//    public FilterRegistrationBean apiRegistrationBean() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(authorizationFilter);
//        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/api/**"));
//        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        return filterRegistrationBean;
//    }
//}
