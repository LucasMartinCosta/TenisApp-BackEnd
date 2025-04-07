package com.tenis.manager.tenis.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenis.manager.tenis.entities.Profesor;
import com.tenis.manager.tenis.repositories.ProfeRepo;

@Service
public class ProfesorService {


    @Autowired
    private ProfeRepo profeRepo; 

     public List <Profesor> findAll (){
        return (List<Profesor>)  this.profeRepo.findAll();
    }

    public Optional<Profesor> findById (Long id){
        return this.profeRepo.findById(id);
    }
 
    public Profesor saveProfesor (Profesor dato){
        return this.profeRepo.save(dato);
    }

    public Profesor updateProfesor (Long id, Profesor dato){

        Profesor encontrado = this.profeRepo.findById(id).get();

        encontrado.setFechaNacimiento(dato.getFechaNacimiento());
        encontrado.setCelular(dato.getCelular());
        encontrado.setNombre(dato.getNombre());

        return this.profeRepo.save(encontrado);

    }

    public Profesor deleteProfesor (Long id){

        return this.profeRepo.findById(id)
        .map(profesor -> {
            this.profeRepo.delete(profesor);
            return profesor;
        })
        .orElseThrow(()-> new RuntimeException("profesor con ID " + id + "no se encuentra en la BDD"));

    }
}
