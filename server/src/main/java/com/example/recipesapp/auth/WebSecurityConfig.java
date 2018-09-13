package com.example.recipesapp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.Filter;
import java.util.Collections;

@EnableWebSecurity(debug = true) //(debug = true) // when you want to see what filters are applied
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthorizationFilter authorizationFilter;

    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(AuthorizationFilter authorizationFilter,
                             UserDetailsService userDetailsService) {
        this.authorizationFilter = authorizationFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public FilterRegistrationBean apiRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(authorizationFilter);
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/api/**"));
        return filterRegistrationBean;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .httpBasic().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/sign-in").permitAll();
//        http.antMatcher("/api/**").authorizeRequests() //
//                .anyRequest().authenticated() //
//                .and()
//                .addFilter(new AuthorizationFilter(authenticationManager()));

        // Disable CSRF (cross site request forgery)

//        http.csrf().disable().authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/sign-in").permitAll()
//                .antMatchers("/api/**").permitAll()
//                .anyRequest().authenticated();
//
//        http.csrf().disable();
//
//        http.httpBasic()
//                .disable();

// No session will be created or used by spring security
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

// Entry points
        http.authorizeRequests()//
                .antMatchers("/sign-in").permitAll()
                // Disallow everything else..
                .anyRequest().authenticated();

        http.antMatcher("/api/**").authorizeRequests()
                .and()
                .addFilterAfter(new AuthorizationFilter(), BasicAuthenticationFilter.class);

        http.cors();

//        http.authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/sign-in").permitAll()
//                .antMatchers("/api/**").permitAll()
//                .anyRequest().authenticated();
//
//        http.cors().and()
//                .addFilterAfter(new AuthorizationFilter(), FilterSecurityInterceptor.class);
//
//        http.csrf().disable()
//                .httpBasic().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().headers().frameOptions().disable()
//                .and().anonymous().disable();

//        http.csrf().disable().authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/sign-in").permitAll()
//                .antMatchers("/api/**").permitAll()
//                .antMatchers("/").permitAll()
//                .anyRequest().authenticated();
    }

//    @Bean
//    public FilterRegistrationBean restRegistrationBean() {
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
//        filterRegistrationBean.setFilter(restFilter);
//        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/rest/*"));
//        return filterRegistrationBean;
//    }

    @Override
    public void configure(AuthenticationManagerBuilder builder)
            throws Exception {
        builder.userDetailsService(userDetailsService);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("GET");
        configuration.addAllowedHeader("Access-Control-Allow-Origin");
        configuration.addAllowedHeader("Access-Control-Allow-Methods");
        configuration.addAllowedHeader("Access-Control-Allow-Headers");
        configuration.addAllowedHeader("Access-Control-Max-Age");
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
