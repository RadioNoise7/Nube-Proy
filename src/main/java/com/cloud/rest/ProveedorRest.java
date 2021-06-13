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

import com.cloud.model.Proveedor;
import com.cloud.model.Request.ProveedorRequest;
import com.cloud.service.ProveedorService;

@RestController // Metaprogramacion
@RequestMapping("/api")
public class ProveedorRest {

    @Autowired
    private ProveedorService proveedorService;

    // GET /api/cuentas
    @GetMapping("/proveedores")
    public ResponseEntity<List<Proveedor>> getProveedores() {
        return ResponseEntity.ok().body(proveedorService.getProveedores());
    }

    @GetMapping("/proveedores/buscar") // RequestParam = Query parameter -> ?llave=valor&llave=valor
    public ResponseEntity<List<Proveedor>> searchProveedores(@RequestParam("nombre") String nombre) {
        return ResponseEntity.ok().body(proveedorService.buscarProveedores(nombre));
    }

    @PostMapping("/proveedores") // POST api/proveedores
    public ResponseEntity<Proveedor> postProveedores(@RequestBody @Valid ProveedorRequest request) throws URISyntaxException {

        Proveedor proveedor = proveedorService.crearProveedor(request);

        // 201 Created
        // Header: Location
        return ResponseEntity.created(new URI("/proveedores/" + proveedor.getId())).body(proveedor);
    }

    @PutMapping("/proveedores/{id}") // PUT api/proveedores/{id}
    public ResponseEntity<Proveedor> editarPorfesor(@PathVariable("id") Integer id, @RequestBody ProveedorRequest proveedor) {
        return ResponseEntity.ok().body(proveedorService.actualizarProveedor(id,proveedor));
    }

    @DeleteMapping("/proveedores/{id}")
    public ResponseEntity<Proveedor> eliminarProveedor(@PathVariable("id") Integer id) {
        return ResponseEntity.ok().body(proveedorService.eliminarProveedor(id));
    }
    
}