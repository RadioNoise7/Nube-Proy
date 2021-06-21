package com.cloud.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.exception.NotFoundException;
import com.cloud.model.Role;
import com.cloud.model.User;
import com.cloud.model.Request.RegisterRequest;
import com.cloud.repository.RoleRepository;
import com.cloud.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Integer DEFAULT_PROVIDER_ID = 1;
    private final Integer DEFAULT_ROLE_USER_ID = 2;
    
    @Transactional
    public List<User> getUsers() {
        List<User> users = new LinkedList<>();
        usuarioRepository.findAll().iterator().forEachRemaining(users::add);
        return users;
    }

    public User getUserById(Integer id) {
        Optional<User> opt = usuarioRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new NotFoundException("No se encontró un usuario con id " +id);
    }

    public User getUserByUsername(String username){
        return usuarioRepository.findByUsername(username);
    }

    @Transactional
    public User create(RegisterRequest request) {
        /*
            Temporalmente asignamos por defecto nuestro proveedor como primera
            cuenta y el tipo de rol "Usuario".
        */

        Role role = roleRepository.findById(DEFAULT_ROLE_USER_ID).get();

        User registeredUser = new User();
        registeredUser.setUsername(request.getUsername());
        registeredUser.setPassword(passwordEncoder.encode(request.getPassword()));
        registeredUser.setEmail(request.getEmail());
        registeredUser.setRole(role);
        registeredUser = usuarioRepository.save(registeredUser);
       
        accountService.createAccount(registeredUser, providerService.getProviderById(DEFAULT_PROVIDER_ID), request.getAccountname());
        return registeredUser;
    }

}