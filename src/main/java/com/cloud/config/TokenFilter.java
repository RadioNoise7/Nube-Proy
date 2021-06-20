package com.cloud.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.cloud.model.User;
import com.cloud.repository.UserRepository;

@Component
public class TokenFilter extends GenericFilterBean {

    private Logger log = LoggerFactory.getLogger(TokenFilter.class);

    @Autowired
    private UserRepository usuarioRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        String authHeader = httpRequest.getHeader(HttpHeaders.AUTHORIZATION); 
        
        if(authHeader == null || authHeader.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        log.info("Header de auth: [{}]", authHeader);

        User user = usuarioRepository.findByToken(authHeader.substring(7));

        //log.info("Usuario: [{}]", user.toString());

        if (user == null) {
            chain.doFilter(request, response);
            return;
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        
        chain.doFilter(request, response);
    }
}