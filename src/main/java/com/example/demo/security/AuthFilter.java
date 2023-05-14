package com.example.demo.security;

import com.example.demo.entity.User;
import com.example.demo.exception.UserNotAuthenticated;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class AuthFilter implements Filter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            String userName = request.getParameter("userName");

            logger.info("User {}", userName);
            logger.info("Logging Request  {} : {}", request.getMethod(), request.getRequestURI());
            if(null != request.getRequestURI() && request.getRequestURI().contains("/image")) {
                Optional<User> user = userRepository.findUserByUserName(userName);
                if(user.isEmpty()) {
                    throw new UserNotFoundException("User is not registered");
                }
                if(!user.get().isAuthenticated()) {
                    throw new UserNotAuthenticated("User is not authenticated, need to log in");
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            Map<String, Object> message = new HashMap<>();
            message.put("message", e.getMessage());
            response.getOutputStream().write(objectMapper.writeValueAsString(message).getBytes());
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
