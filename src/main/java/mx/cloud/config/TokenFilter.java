package main.java.mx.cloud.config;

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

import mx.uady.cloud.model.Cuenta;
import mx.uady.cloud.repository.CuentaRepository;

@Component
public class TokenFilter extends GenericFilterBean {

    private Logger log = LoggerFactory.getLogger(TokenFilter.class);

    @Autowired
    private CuentaRepository cuentaRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        // Authorization: abc
        // Authorization: def
        String authHeader = httpRequest.getHeader(HttpHeaders.AUTHORIZATION); 
        
        if(authHeader == null || authHeader.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        log.info("Header de auth: [{}]", authHeader);

        Cuenta cuenta = cuentaRepository.findByToken(authHeader);

        log.info("Cuenta: [{}]", cuenta);

        if (cuenta == null) {
            chain.doFilter(request, response);
            return;
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(cuenta, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        
        chain.doFilter(request, response);
    }
}