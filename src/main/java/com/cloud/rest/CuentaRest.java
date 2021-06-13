package com.cloud.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.model.Cuenta;
import com.cloud.model.Request.CuentaRequest;
import com.cloud.service.CuentaService;

@RestController // Metaprogramacion
@RequestMapping("/api")
public class CuentaRest {

    @Autowired
    private CuentaService cuentaService;

    // GET /api/cuentas
    @GetMapping("/cuentas")
    public ResponseEntity<List<Cuenta>> getCuentas() {
        return ResponseEntity.ok().body(cuentaService.getCuentas());
    }

    @GetMapping("/cuentas/{id}")
    public ResponseEntity<Cuenta> getCuenta(@PathVariable Integer id) {
        return ResponseEntity.ok().body(cuentaService.getCuenta(id));
    }

    @GetMapping("/cuentas/buscar") // RequestParam = Query parameter -> ?llave=valor&llave=valor
    public ResponseEntity<List<Cuenta>> searchCuentas(@RequestParam("nombre") String nombre) {
        return ResponseEntity.ok().body(cuentaService.buscarCuentas(nombre));
    }

    // POST /api/cuentas
    @PostMapping("/cuentas")
    public ResponseEntity<Cuenta> postCuentas(@RequestBody @Valid CuentaRequest request) throws URISyntaxException {

        // RequestBody le indica a Java que estamos esperando un request que cumpla con los campos del Objeto CuentaRequest

        Cuenta cuenta = cuentaService.crearCuenta(request);

        // 201 Created
        // Header: Location
        return ResponseEntity.created(new URI("/cuentas/" + cuenta.getId())).body(cuenta);
    }

    @PutMapping("/cuentas/{id}")
    public ResponseEntity<Cuenta> actualizarCuenta(@RequestBody @Valid CuentaRequest request,
            @PathVariable Integer id) {
        return ResponseEntity.ok().body(cuentaService.actualizarCuenta(id, request));
    }

    @DeleteMapping("/cuentas/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Integer id) {
        cuentaService.eliminarCuenta(id);
        return ResponseEntity.ok().build();
    }
}