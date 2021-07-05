package com.cloud.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.exception.ValidationExceptionsHandler;
import com.cloud.model.Account;
import com.cloud.model.request.AccountRequest;
import com.cloud.service.AccountService;

@RestController // Metaprogramacion
@RequestMapping("/api")
public class AccountRest {

    @Autowired
    private AccountService cuentaService;

    // GET /api/cuentas
    @GetMapping("/cuentas")
    public ResponseEntity<List<Account>> getCuentas() {
        return ResponseEntity.ok().body(cuentaService.getCuentas());
    }

    @GetMapping("/cuentas/{id}")
    public ResponseEntity<Account> getCuenta(@PathVariable Integer id) {
        return ResponseEntity.ok().body(cuentaService.getCuenta(id));
    }

    @GetMapping("/cuentas/buscar") // RequestParam = Query parameter -> ?llave=valor&llave=valor
    public ResponseEntity<List<Account>> searchCuentas(@RequestParam("nombre") String nombre) {
        return ResponseEntity.ok().body(cuentaService.getAccountByAccountname(nombre));
    }

    // POST /api/cuentas
    @PostMapping("/cuentas")
    public ResponseEntity<Account> postCuentas(@RequestBody @Valid AccountRequest request) throws URISyntaxException {

        // RequestBody le indica a Java que estamos esperando un request que cumpla con los campos del Objeto CuentaRequest

        Account cuenta = cuentaService.crearCuenta(request);

        // 201 Created
        // Header: Location
        return ResponseEntity.created(new URI("/cuentas/" + cuenta.getId())).body(cuenta);
    }

    @PutMapping("/cuentas/{id}")
    public ResponseEntity<Account> actualizarCuenta(@RequestBody @Valid AccountRequest request,
            @PathVariable Integer id) {
        return ResponseEntity.ok().body(cuentaService.actualizarCuenta(id, request));
    }

    @DeleteMapping("/cuentas/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Integer id) {
        cuentaService.eliminarCuenta(id);
        return ResponseEntity.ok().build();
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> validateExceptions(MethodArgumentNotValidException ex) {
        ValidationExceptionsHandler exHandler = new ValidationExceptionsHandler(ex);
        return exHandler.handleValidationExceptions();
    }
}