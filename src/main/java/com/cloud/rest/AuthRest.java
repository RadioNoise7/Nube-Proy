package com.cloud.rest;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;


//JWT
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.FieldError;

import com.cloud.config.JWTTokenUtil;
import com.cloud.config.JwtResponse;
import com.cloud.model.request.LoginRequest;
import com.cloud.model.request.RegisterRequest;
import com.cloud.service.AuthService;
import com.cloud.service.utility.EmailService;

@RestController
@RequestMapping("/auth")
public class AuthRest {

    @Autowired
	private AuthenticationManager authenticationManager;

    @Autowired
	private JWTTokenUtil jwtTokenUtil;

    @Autowired
	private UserDetailsService userDetailsService;
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> postLogin(@Valid @RequestBody LoginRequest request, @RequestHeader("user-agent") String userAgent) throws Exception {
        String token = authService.login(request, emailService, userAgent, jwtTokenUtil, userDetailsService, authenticationManager);
		return ResponseEntity.ok().body(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> postRegister(@Valid @RequestBody RegisterRequest request)throws Exception {
        return ResponseEntity.ok(authService.register(request, emailService));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> postLogout(@RequestHeader("Authorization") String authToken) throws DataAccessException{
        authService.logout(authToken);
    return ResponseEntity.noContent().build();
    }

    @GetMapping("/self")
    public ResponseEntity<UserDetails> getLoggedUser() {
        return ResponseEntity.status(HttpStatus.OK).body((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}