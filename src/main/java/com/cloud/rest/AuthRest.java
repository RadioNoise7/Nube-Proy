package com.cloud.rest;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//JWT
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.http.ResponseEntity;

import com.cloud.config.JwtTokenUtil;
import com.cloud.model.Cuenta;
import com.cloud.model.Usuario;
import com.cloud.model.Request.UsuarioRequest;
import com.cloud.model.Request.AuthRequest;
import com.cloud.model.Request.JwtResponse;
import com.cloud.model.Request.CuentaRequest;
import com.cloud.service.AuthService;
import com.cloud.service.CuentaService;

@RestController
public class AuthRest {

    @Autowired
    private AuthService authService;

    @Autowired
	private JwtTokenUtil jwtTokenUtil;

    @Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

    @Autowired
	private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<Cuenta> postRegister(@RequestBody @Valid AuthRequest request)throws URISyntaxException {
        Cuenta cuenta = authService.registrarCuenta(request);
        return ResponseEntity.created(new URI("cuentas" + cuenta.getId())).body(cuenta);
    }

    @PostMapping("/login")
    public ResponseEntity<String> postLogin(@RequestBody AuthRequest request) throws Exception {
        //String token = authService.login(request);

        authenticate(request.getUsuario(), request.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService.loadUserByUsername(request.getUsuario());
		final String token = jwtTokenUtil.generateToken(userDetails);
        //JwtResponse formatToken = new JwtResponse(token);

		return ResponseEntity.ok(token);
    }

    public void authenticate(String username, String password) throws Exception {
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

    @PostMapping("/logout/{id}")
    public ResponseEntity<Void> postLogout(@PathVariable Integer id ) throws URISyntaxException {
        authService.logout(id);
       return ResponseEntity.noContent().build();
    }

    @GetMapping("/self")
    public ResponseEntity<Usuario> getLoggerdUser() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(usuario);
    }

}