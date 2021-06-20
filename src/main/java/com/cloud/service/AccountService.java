package com.cloud.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.exception.NotFoundException;
import com.cloud.model.Account;
import com.cloud.model.Request.AccountRequest;
import com.cloud.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository cuentaRepository;

    public List<Account> getCuentas() {

        List<Account> cuentas = new LinkedList<>();

        cuentaRepository.findAll().iterator().forEachRemaining(cuentas::add); // SELECT(id, nombre)

        return cuentas;
    }

    /**
     * Este es el metodo que devuelve el cuenta
     * 
     * @param id Integer, id del cuenta
     * @return void
     *
     */
    public Account getCuenta(Integer id) {
        Optional<Account> cuenta = cuentaRepository.findById(id);

        if (!cuenta.isPresent()) {
            throw new NotFoundException();
        }

        return cuenta.get();
    }

    /**
     * Este es el metodo que busca al cuenta por nombre
     * 
     * @param accountname String, nombre del cuenta
     * @return nombre del cuenta
     *
     */
    public List<Account> getAccountByAccountname(String accountname) {
        return cuentaRepository.findByAccountname(accountname);
    }

    /**
     * Este es el metodo que crea un nuevo cuenta
     * 
     * @param request CuentaRequest, el request del formulario de cuenta
     * @return cuenta
     *
     */
    /*
     * @Transactional public Account crearCuenta(CuentaRequest request) {
     * 
     * Account cuenta = new Account();
     * 
     * String token = UUID.randomUUID().toString(); usuario.setToken(token);
     * cuenta.setUsuario(usuario);
     * 
     * usuario = usuarioRepository.save(usuario); cuenta =
     * cuentaRepository.save(cuenta); // INSERT
     * 
     * return cuenta; }
     */

    /**
     * Este es el metodo que actualiza al cuenta
     * 
     * @param id      Integer, id del cuenta
     * @param request CuentaRequest, el request del formulario de cuenta
     * @return cuentaEncontrado
     *
     */
    @Transactional
    public Account actualizarCuenta(Integer id, AccountRequest request) {
        // Validar comentario
        Account foundAccount = getCuenta(id);
        foundAccount.setAccountname(request.getNombre());
        // cuentaEncontrado.setComentario();
        cuentaRepository.save(foundAccount);
        return foundAccount;
    }

    /**
     * Este es el metodo que elimina un cuenta
     * 
     * @param id Intger, id del cuenta
     * @return void
     *
     */
    @Transactional
    public void eliminarCuenta(Integer id) {
        cuentaRepository.deleteById(id);
    }

}
