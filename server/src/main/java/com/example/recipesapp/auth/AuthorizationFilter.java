package com.example.recipesapp.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Component
public class AuthorizationFilter implements Filter {

    private IdTokenVerifier idTokenVerifier;
    private UserRepository userRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        this.initializeDependencies(servletRequest);

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String idToken = request.getHeader("Authorization");

        if (idToken == null || !idToken.startsWith("Bearer")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

        idToken = idToken.replace("Bearer ", "");
        final Payload payload;
        try {
            payload = this.idTokenVerifier.verifyToken(idToken);
            if (payload != null) {
                String userId = payload.getSubject();
                if (this.userRepository.findById(userId) == null) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, ""));
            }
        } catch (GeneralSecurityException | InvalidTokenException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    private void initializeDependencies(ServletRequest request) {
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.
                getWebApplicationContext(request.getServletContext());

        idTokenVerifier = webApplicationContext.getBean(IdTokenVerifier.class);
        userRepository = webApplicationContext.getBean(UserRepository.class);
    }
}