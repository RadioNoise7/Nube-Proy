package com.cloud.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.cloud.model.Provider;
import com.cloud.model.request.ProviderRequest;
import com.cloud.service.ProviderService;

@RestController // Metaprogramacion
@RequestMapping("/api")
public class ProviderRest {

    @Autowired
    private ProviderService proveedorService;

    // GET /api/cuentas
    @GetMapping("/proveedores")
    public ResponseEntity<List<Provider>> getProveedores() {
        return ResponseEntity.ok().body(proveedorService.getProveedores());
    }

    @GetMapping("/proveedores/buscar") // RequestParam = Query parameter -> ?llave=valor&llave=valor
    public ResponseEntity<List<Provider>> searchProveedores(@RequestParam("nombre") String nombre) {
        return ResponseEntity.ok().body(proveedorService.buscarProveedores(nombre));
    }

    @PostMapping("/proveedores") // POST api/proveedores
    public ResponseEntity<Provider> postProveedores(@RequestBody @Valid ProviderRequest request) throws URISyntaxException {

        Provider proveedor = proveedorService.crearProveedor(request);

        // 201 Created
        // Header: Location
        return ResponseEntity.created(new URI("/proveedores/" + proveedor.getId())).body(proveedor);
    }

    @PutMapping("/proveedores/{id}") // PUT api/proveedores/{id}
    public ResponseEntity<Provider> editarPorfesor(@PathVariable("id") Integer id, @RequestBody ProviderRequest proveedor) {
        return ResponseEntity.ok().body(proveedorService.actualizarProveedor(id,proveedor));
    }

    @DeleteMapping("/proveedores/{id}")
    public ResponseEntity<Provider> eliminarProveedor(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(proveedorService.eliminarProveedor(id));
    }
    
}