package com.cloud.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cloud.model.Provider;
import com.cloud.model.Request.ProviderRequest;
import com.cloud.repository.ProviderRepository;

@Service
public class ProviderService {
    
    @Autowired
    private ProviderRepository proveedorRepository;

    public List<Provider> getProveedores() {

        List<Provider> proveedores = new LinkedList<>();

        proveedorRepository.findAll().iterator().forEachRemaining(proveedores::add);

        return proveedores;
    }

    public List<Provider> buscarProveedores(String name) {
        return proveedorRepository.findByProvidernameContaining(name);
    }

    public Provider crearProveedor(ProviderRequest request) {
        Provider provider = new Provider();

        provider.setNombreProveedor(request.getNombre());
        provider = proveedorRepository.save(provider);

        return provider;
    }

    public Provider getProviderById(Integer id) {
        return proveedorRepository.findById(id).get();
    }

    public Provider actualizarProveedor(Integer id, ProviderRequest proveedor) {
        Provider proveedorAct = getProviderById(id);

        Provider proveedorReference = proveedorRepository.findByProvidername(proveedorAct.getNombreProveedor()).get(0);
        proveedorReference = setProveedorNewValues(proveedorReference, proveedor);
        return proveedorRepository.save(proveedorReference);
    }

    private Provider setProveedorNewValues(Provider proveedorRef,ProviderRequest proveedorEdit){
        proveedorRef.setNombreProveedor(proveedorEdit.getNombre());
        //proveedorRef.setHoras(proveedorEdit.getHoras());
        return proveedorRef;
    }

    public Provider eliminarProveedor(Integer id){
        Provider profeEliminar = proveedorRepository.findById(id).get();
        proveedorRepository.deleteById(profeEliminar.getId());
        return profeEliminar;
    }

}
