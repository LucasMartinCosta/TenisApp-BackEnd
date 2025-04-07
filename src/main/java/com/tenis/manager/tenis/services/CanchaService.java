package com.tenis.manager.tenis.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenis.manager.tenis.entities.Cancha;
import com.tenis.manager.tenis.repositories.CanchaRepository;

@Service
public class CanchaService {

    @Autowired
    private CanchaRepository canchaRepo; 

    //crear metodos CRUD findAll-findbiid-save-update-dalete

    public List <Cancha> findAll (){
        return (List<Cancha>)  this.canchaRepo.findAll();
    }

    public Optional<Cancha> findById (Long id){
        return this.canchaRepo.findById(id);
    }

    public Cancha saveCancha (Cancha dato){
        return this.canchaRepo.save(dato);
    }

    public Cancha updateCancha (Long id, Cancha dato){

        Cancha encontrada = this.canchaRepo.findById(id).get();

        encontrada.setNombre(dato.getNombre());

        return this.canchaRepo.save(encontrada);

    }

    public Cancha deleteCancha (Long id){

        return this.canchaRepo.findById(id)
        .map(cancha -> {
            this.canchaRepo.delete(cancha);
            return cancha;
        })
        .orElseThrow(()-> new RuntimeException("La cancha con ID " + id + "no se encuentra en la BDD"));

    }

}
