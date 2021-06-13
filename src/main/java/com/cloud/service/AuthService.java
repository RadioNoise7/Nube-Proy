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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

//import com.cloud.exception.*;
import com.cloud.model.Cuenta;
import com.cloud.model.Usuario;
import com.cloud.model.Comentario;
import com.cloud.model.Carrera;
import com.cloud.model.Video;
import com.cloud.model.Request.CuentaRequest;
import com.cloud.model.Request.UsuarioRequest;
import com.cloud.model.Request.AuthRequest;
import com.cloud.repository.CuentaRepository;
import com.cloud.repository.UsuarioRepository;
import com.cloud.repository.ComentarioRepository;
import com.cloud.repository.VideoRepository;
import com.cloud.exception.NotFoundException;
import com.cloud.exception.UnauthorizedException;

@Service
public class AuthService implements UserDetailsService{
    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
	private AuthenticationManager authenticationManager;

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = usuarioRepository.findByUsuario(username);

        return new User(user.getUsuario(), user.getPassword(), new ArrayList<>());
	}

    @Transactional
    public Cuenta registrarCuenta(AuthRequest request){
        Usuario usuarioCreate = new Usuario();
        usuarioCreate.setUsuario(request.getUsuario());
        usuarioCreate.setPassword(passwordEncoder.encode(request.getPassword()));
        String token = UUID.randomUUID().toString();
        usuarioCreate.setToken(token);

        Usuario userExistente = usuarioRepository.findByUsuario(usuarioCreate.getUsuario());

        if (!(userExistente ==  null)) {
            throw new UnauthorizedException();
        }

        Usuario usuarioSave = usuarioRepository.save(usuarioCreate);
        Cuenta cuenta = new Cuenta();
        cuenta.setNombre(request.getNombre());

        if(request.getComentario()!=null && request.getComentario()>0){
            Comentario comentario = comentarioRepository.findById(request.getComentario()).get();
            cuenta.setComentario(comentario);
        }

        if(request.getCarrera()!=null){
            Carrera carrera = Carrera.valueOf(request.getCarrera());
            cuenta.setCarrera(carrera);
        }

        cuenta.setUsuario(usuarioSave);
        cuenta = cuentaRepository.save(cuenta);
        return cuenta;
    }

    @Transactional
    public String login(UsuarioRequest request){
        Usuario usuario = usuarioRepository.findByUsuario(request.getUsuario());

        if(usuario==null || !passwordEncoder.matches(request.getPassword(), usuario.getPassword())){
            throw new NotFoundException();
        }

        String token = UUID.randomUUID().toString();
        usuario.setToken(token);
        usuario = usuarioRepository.save(usuario);
        return token;
    }

    @Transactional
    public void logout(Integer id){
        Usuario usuario = usuarioRepository.findById(id).get();
        usuario.setToken(null);
        usuarioRepository.save(usuario);
    }

    /* @Transactional
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
	} */
}