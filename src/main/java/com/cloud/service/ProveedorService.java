package com.cloud.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cloud.model.Proveedor;
import com.cloud.model.Request.ProveedorRequest;
import com.cloud.repository.ProveedorRepository;

@Service
public class ProveedorService {
    
    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> getProveedores() {

        List<Proveedor> proveedores = new LinkedList<>();

        proveedorRepository.findAll().iterator().forEachRemaining(proveedores::add);

        return proveedores;
    }

    public List<Proveedor> buscarProveedores(String nombre) {
        return proveedorRepository.findByNombreContaining(nombre);
    }

    public Proveedor crearProveedor(ProveedorRequest request) {
        Proveedor proveedor = new Proveedor();

        proveedor.setNombreProveedor(request.getNombre());
        proveedor.setHoras(request.getHoras();
        proveedor = proveedorRepository.save(proveedor);

        return proveedor;
    }

    public Proveedor getProveedor(Integer id) {
        return proveedorRepository.findById(id).get();//findByIdSerializable(id).get();
    }

    public Proveedor actualizarProveedor(Integer id, ProveedorRequest proveedor) {
        Proveedor proveedorAct = getProveedor(id);

        Proveedor proveedorReference = proveedorRepository.findByNombre(proveedorAct.getNombre()).get(0);
        proveedorReference = setProveedorNewValues(proveedorReference, proveedor);
        return proveedorRepository.save(proveedorReference);
    }

    private Proveedor setProveedorNewValues(Proveedor proveedorRef,ProveedorRequest proveedorEdit){
        proveedorRef.setNombreProveedor(proveedorEdit.getNombre());
        proveedorRef.setHoras(proveedorEdit.getHoras());
        return proveedorRef;
    }

    public Proveedor eliminarProveedor(Integer id){
        Proveedor profeEliminar = proveedorRepository.findById(id).get();
        proveedorRepository.deleteById(profeEliminar.getId());
        return profeEliminar;
    }

}
