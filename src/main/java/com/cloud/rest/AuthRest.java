package com.cloud.rest;

import javax.validation.Valid;
import java.net.URISyntaxException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

//JWT
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.cloud.config.JWTTokenUtil;
import com.cloud.config.JwtResponse;
import com.cloud.model.User;
import com.cloud.model.Request.LoginRequest;
import com.cloud.model.Request.RegisterRequest;
import com.cloud.service.AuthService;
import com.cloud.service.EmailService;

@RestController
public class AuthRest {

    @Autowired
    private AuthService authService;

    @Autowired
	private JWTTokenUtil jwtTokenUtil;

    @Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

    @Autowired
	private AuthenticationManager authenticationManager;
    
    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<?> postRegister(@RequestBody @Valid RegisterRequest request)throws Exception {
        
        return ResponseEntity.ok(authService.register(request, emailService));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> postLogin(@RequestBody LoginRequest request, @RequestHeader("user-agent") String userAgent) throws Exception {
        User usuarioLoggeado = authService.login(request, emailService, userAgent, jwtTokenUtil, jwtInMemoryUserDetailsService, authenticationManager);

		return ResponseEntity.ok().body(new JwtResponse(usuarioLoggeado.getToken()));
    }

    @PostMapping("/logout/{id}")
    public ResponseEntity<Void> postLogout(@PathVariable Integer id ) throws URISyntaxException {
        authService.logout(id);
       return ResponseEntity.noContent().build();
    }

    @GetMapping("/self")
    public ResponseEntity<Object> getLoggedUser() {
        return ResponseEntity.status(HttpStatus.OK).body(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

}