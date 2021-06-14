package com.cloud.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.exception.NotFoundException;
import com.cloud.model.Cuenta;
import com.cloud.model.Comentario;
import com.cloud.model.Usuario;
import com.cloud.model.Request.CuentaRequest;
import com.cloud.repository.CuentaRepository;
import com.cloud.repository.UsuarioRepository;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Cuenta> getCuentas() {

        List<Cuenta> cuentas = new LinkedList<>();

        cuentaRepository.findAll().iterator().forEachRemaining(cuentas::add); // SELECT(id, nombre)

        return cuentas;
    }

    /**
    * Este es el metodo que devuelve el cuenta
    * @param id Integer, id del cuenta 
    * @return void
    *
    */
    public Cuenta getCuenta(Integer id) {
        Optional<Cuenta> cuenta = cuentaRepository.findById(id);

        if (!cuenta.isPresent()) {
            throw new NotFoundException();
        }

        return cuenta.get();
    }

    /**
    * Este es el metodo que busca al cuenta por nombre
    * @param nombre String, nombre del cuenta
    * @return nombre del cuenta
    *
    */
    public List<Cuenta> buscarCuentas(String nombre) {
        return cuentaRepository.findByNombreContaining(nombre);
    }

    /**
    * Este es el metodo que crea un nuevo cuenta
    * @param request CuentaRequest, el request del formulario de cuenta
    * @return cuenta
    *
    */
    @Transactional
    public Cuenta crearCuenta(CuentaRequest request) {

        Cuenta cuenta = new Cuenta();

        cuenta.setNombre(request.getNombre());
     

        Usuario usuario = new Usuario();

        usuario.setUsuario(request.getCorreo());
        usuario.setPassword("123");

        String token = UUID.randomUUID().toString();
        usuario.setToken(token);
        cuenta.setUsuario(usuario);

        usuario = usuarioRepository.save(usuario);
        cuenta = cuentaRepository.save(cuenta); // INSERT

        return cuenta;
    }

    /**
    * Este es el metodo que actualiza al cuenta
    * @param id Integer, id del cuenta
    * @param request CuentaRequest, el request del formulario de cuenta
    * @return cuentaEncontrado
    *
    */
    @Transactional
    public Cuenta actualizarCuenta(Integer id, CuentaRequest request) {
        // Validar comentario
        Cuenta cuentaEncontrado = getCuenta(id);
        cuentaEncontrado.setNombre(request.getNombre());
        // cuentaEncontrado.setComentario();
        cuentaRepository.save(cuentaEncontrado);
        return cuentaEncontrado;
    }

    /**
    * Este es el metodo que elimina un cuenta
    * @param id Intger, id del cuenta
    * @return void 
    *
    */
    @Transactional
    public void eliminarCuenta(Integer id) {
        Usuario usuarioEliminar = getCuenta(id).getUsuario();
        // Validar que no existan videos
        usuarioRepository.delete(usuarioEliminar);
        cuentaRepository.deleteById(id);
    }

}
