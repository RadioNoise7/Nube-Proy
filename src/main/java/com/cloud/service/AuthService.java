package com.cloud.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import javax.validation.constraints.Null;

import org.springframework.transaction.annotation.Transactional;

//import io.jsonwebtoken.lang.Objects;
import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Seguridad con JWT
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

//import com.cloud.exception.*;
import com.cloud.exception.NotFoundException;
import com.cloud.exception.UnauthorizedException;

import com.cloud.config.JWTTokenUtil;
import com.cloud.model.Account;
import com.cloud.model.User;
import com.cloud.model.Commentary;
import com.cloud.model.Video;
import com.cloud.model.Request.AccountRequest;
import com.cloud.model.Request.LoginRequest;
import com.cloud.model.Request.RegisterRequest;
import com.cloud.model.Request.UserRequest;
import com.cloud.model.Request.AuthRequest;
import com.cloud.repository.AccountRepository;
import com.cloud.repository.UserRepository;
import com.cloud.repository.VideoRepository;

@Service
public class AuthService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
        if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.
            User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}

    @Transactional
    public User register(RegisterRequest request, EmailService emailService){
        User registeredUser = userService.create(request);
        return registeredUser;
    }

    @Transactional
    public User login(LoginRequest request, EmailService emailService, String userAgent,
        JWTTokenUtil jwtTokenUtil, UserDetailsService jwtInMemoryUserDetailsService, AuthenticationManager authenticationManager) throws Exception {
        
        try{
            this.authenticate(request.getUsername(), request.getPassword(), authenticationManager);
            User user = userService.getUserByUsername(request.getUsername());

            final UserDetails userDetails = jwtInMemoryUserDetailsService.loadUserByUsername(request.getUsername());
            user.setToken(jwtTokenUtil.generateToken(userDetails));

            //emailService.sendEmail("Se ha iniciado sesion desde: " +userAgent, user.getEmail(), "Inicio de sesión");
            
            return user;
        } catch (DisabledException e) {
			throw new DisabledException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", e);
		}
    }

    @Transactional
    public void logout(Integer id){
        User usuario = userRepository.findById(id).get();
        usuario.setToken(null);
        userRepository.save(usuario);
    }

    /**
     * Realiza el proceso de autenticar el usuario
     * @param username Identificador del usuario.
     * @param password Contraseña del usuario.
     * @param authenticationManager El administrador de autenticación.
     * @throws Exception
     */
    private void authenticate(String username, String password, AuthenticationManager authenticationManager) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}