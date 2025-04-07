package com.tenis.manager.tenis.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenis.manager.tenis.entities.Precio;
import com.tenis.manager.tenis.repositories.PrecioRepository;

@Service
public class precioService {

    @Autowired
    private PrecioRepository precioRepo; 

    public List<Precio> findAll(){
        return (List<Precio>) this.precioRepo.findAll();
    }

    public Optional<Precio> findById (Long id)  {
        return this.precioRepo.findById(id);
    }

    public Precio savePrecio (Precio dato){
        return this.precioRepo.save(dato); 
    }

    public Precio updatePrecio (Long id, Precio nuevoDato){
        Precio encontrado = this.precioRepo.findById(id).get();

        encontrado.setNombre(nuevoDato.getNombre());
        encontrado.setPrecio(nuevoDato.getPrecio());

        return this.precioRepo.save(encontrado);
    }

    public Precio deletPrecio (Long id){

        return this.precioRepo.findById(id)
        .map(precio -> {
            this.precioRepo.delete(precio);
            return precio; 
        })
        .orElseThrow(()-> new RuntimeException("El precio con ID" + id + "no se encuentra en la BDD"));
    }



}
